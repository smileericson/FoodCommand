'use client'

import { abrirAtendimento, adicionarItem, buscarListaPedidos, enviarParaPreparo, fecharConta, marcarPronto, pedirConta } from "@/app/services/pedidoService";
import { buscarListaMesa } from "@/app/services/mesaService";
import { buscarListaCardapio } from "@/app/services/cardapioService";
import { abrirComanda, adicionarItemComanda, atualizarComanda, fecharComanda } from "@/app/redux/slices/pedidoSlice";
import { registrarConsulta } from "@/app/redux/slices/historicoPedidoSlice";
import { RootState } from "@/app/redux/store";
import { Mesa } from "@/app/types/mesa";
import { Cardapios } from "@/app/types/cardapio";
import { Pedido } from "@/app/types/pedido";
import axios from "axios";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

export default function Comanda() {

    const comanda = useSelector((state: RootState) => state.pedido);
    const ultimosConsultados = useSelector((state: RootState) => state.historicoPedido.ultimosConsultados);
    const dispatch = useDispatch();

    const [mesas, setMesas] = useState<Mesa[]>([]);
    const [pedidos, setPedidos] = useState<Pedido[]>([]);
    const [cardapio, setCardapio] = useState<Cardapios[]>([]);
    const [cardapioId, setCardapioId] = useState(0);
    const [quantidade, setQuantidade] = useState(1);

    useEffect(() => {
        carregarDados();
    }, []);

    // Busca as mesas, os pedidos e os itens ativos do cardápio.
    const carregarDados = async () => {
        try {
            setMesas(await buscarListaMesa());
            setPedidos(await buscarListaPedidos());
            setCardapio((await buscarListaCardapio()).filter(item => item.statusCardapio === "ATIVO"));
        } catch (error) {
            if (axios.isAxiosError(error) && error.response?.status === 401) return;
            alert("Erro ao carregar dados!")
        }
    }

    // Mostra a mensagem de erro que o backend devolveu (regra de negócio).
    const mostrarErro = (error: unknown) => {
        if (axios.isAxiosError(error) && error.response?.status === 401) return;
        alert(axios.isAxiosError(error) ? error.response?.data : "Erro ao executar a operação!");
    }

    // Abre o atendimento da mesa e guarda a comanda no Redux.
    const handleAbrirMesa = async (mesa: Mesa) => {
        try {
            var pedido = await abrirAtendimento(mesa.id);
            dispatch(abrirComanda({ pedido: pedido, numeroMesa: mesa.numeroMesa }));
            dispatch(registrarConsulta(pedido.id || 0));
            carregarDados();
        } catch (error) {
            mostrarErro(error);
        }
    }

    // Retoma um pedido em andamento e registra a consulta no histórico do Redux.
    const handleRetomarPedido = (pedido: Pedido) => {
        dispatch(abrirComanda({ pedido: pedido, numeroMesa: null }));
        dispatch(registrarConsulta(pedido.id || 0));
    }

    // Adiciona o item; o backend devolve subtotal, taxa de 10% e total já calculados.
    const handleAdicionarItem = async () => {
        var item = cardapio.find(c => c.id === cardapioId);
        if (!comanda.pedido?.id || !item) {
            alert("Selecione um item do cardápio!");
            return;
        }
        try {
            var pedido = await adicionarItem(comanda.pedido.id, cardapioId, quantidade);
            dispatch(atualizarComanda(pedido));
            dispatch(adicionarItemComanda({ nome: item.nome, quantidade: quantidade, preco: item.preco }));
        } catch (error) {
            mostrarErro(error);
        }
    }

    // Envia o pedido para a cozinha.
    const handleEnviarPreparo = async () => {
        try {
            dispatch(atualizarComanda(await enviarParaPreparo(comanda.pedido?.id || 0)));
        } catch (error) {
            mostrarErro(error);
        }
    }

    // A cozinha informa que o pedido está pronto.
    const handleMarcarPronto = async () => {
        try {
            dispatch(atualizarComanda(await marcarPronto(comanda.pedido?.id || 0)));
        } catch (error) {
            mostrarErro(error);
        }
    }

    // Coloca a mesa aguardando fechamento.
    const handlePedirConta = async () => {
        try {
            dispatch(atualizarComanda(await pedirConta(comanda.pedido?.id || 0)));
        } catch (error) {
            mostrarErro(error);
        }
    }

    // Fecha a conta, limpa a comanda do Redux e a mesa volta a ficar livre.
    const handleFecharConta = async () => {
        try {
            var pedido = await fecharConta(comanda.pedido?.id || 0);
            alert("Conta fechada! Total: R$ " + Number(pedido.valorTotal).toFixed(2));
            dispatch(fecharComanda());
            carregarDados();
        } catch (error) {
            mostrarErro(error);
        }
    }

    const mesasLivres = mesas.filter(mesa => mesa.statusMesa === "LIVRE");

    // Sai da comanda sem fechar a conta (o pedido continua em andamento).
    const handleVoltar = () => {
        dispatch(fecharComanda());
        carregarDados();
    }

    // Pedidos ainda abertos, ordenados pelo último consultado (histórico do Redux).
    const pedidosEmAndamento = pedidos
        .filter(pedido => ["ATIVO", "EM_PREPARO", "PRONTO"].includes(pedido.statusPedido))
        .sort((a, b) => {
            const posicaoA = ultimosConsultados.indexOf(a.id || 0);
            const posicaoB = ultimosConsultados.indexOf(b.id || 0);
            return (posicaoA === -1 ? ultimosConsultados.length : posicaoA) - (posicaoB === -1 ? ultimosConsultados.length : posicaoB);
        });

    return (
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">
            <div className="mx-auto max-w-5xl space-y-6">

                {/* Cabeçalho */}
                <div className="rounded-2xl border border-zinc-200 bg-white p-6 text-center shadow-md">
                    <h1 className="text-2xl font-extrabold text-zinc-900 sm:text-3xl">Comanda</h1>
                    <p className="mt-2 text-sm font-medium text-zinc-600">
                        {comanda.pedido
                            ? (comanda.numeroMesa ? "Mesa " + comanda.numeroMesa + " - " : "") + "Pedido " + comanda.pedido.id + " - " + comanda.pedido.statusPedido
                            : "Escolha uma mesa livre para abrir o atendimento"}
                    </p>
                </div>

                {/* Sem comanda aberta: lista as mesas livres */}
                {!comanda.pedido && (
                    <div className="grid grid-cols-2 gap-4 sm:grid-cols-4">
                        {mesasLivres.map((mesa) => (
                            <button key={mesa.id} onClick={() => handleAbrirMesa(mesa)}
                                className="rounded-2xl border border-zinc-200 bg-white p-6 text-lg font-bold text-zinc-900 shadow-md transition hover:border-orange-500 hover:bg-orange-50">
                                Mesa {mesa.numeroMesa}
                                <span className="block text-sm font-medium text-orange-600">Abrir atendimento</span>
                            </button>
                        ))}
                        {mesasLivres.length === 0 && (
                            <p className="col-span-full text-center font-bold text-zinc-700">Nenhuma mesa livre!</p>
                        )}
                    </div>
                )}

                {/* Sem comanda aberta: pedidos em andamento (último consultado primeiro) */}
                {!comanda.pedido && (
                    <div className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-md">
                        <h2 className="mb-4 text-center text-lg font-bold text-zinc-900">Pedidos em andamento</h2>
                        <div className="grid grid-cols-2 gap-4 sm:grid-cols-4">
                            {pedidosEmAndamento.map((pedido) => (
                                <button key={pedido.id} onClick={() => handleRetomarPedido(pedido)}
                                    className="rounded-2xl border border-zinc-200 bg-white p-4 font-bold text-zinc-900 shadow-md transition hover:border-orange-500 hover:bg-orange-50">
                                    Pedido {pedido.id}
                                    <span className="block text-sm font-medium text-zinc-600">{pedido.statusPedido} - R$ {Number(pedido.valorTotal || 0).toFixed(2)}</span>
                                    <span className="block text-sm font-medium text-orange-600">Continuar atendimento</span>
                                </button>
                            ))}
                            {pedidosEmAndamento.length === 0 && (
                                <p className="col-span-full text-center font-bold text-zinc-700">Nenhum pedido em andamento!</p>
                            )}
                        </div>
                    </div>
                )}

                {/* Com comanda aberta: itens, valores e ações */}
                {comanda.pedido && (
                    <>
                        <div className="flex flex-col gap-3 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md sm:flex-row">
                            <select value={cardapioId} onChange={(e) => setCardapioId(Number(e.target.value))}
                                className="flex-1 rounded-lg border border-zinc-300 px-4 py-3 text-zinc-900">
                                <option value={0}>Selecione o item do cardápio</option>
                                {cardapio.map((item) => (
                                    <option key={item.id} value={item.id || 0}>{item.nome} - R$ {item.preco}</option>
                                ))}
                            </select>
                            <input type="number" min={1} value={quantidade} onChange={(e) => setQuantidade(Number(e.target.value))}
                                className="w-24 rounded-lg border border-zinc-300 px-4 py-3 text-center text-zinc-900" />
                            <button onClick={handleAdicionarItem}
                                className="rounded-lg bg-orange-500 px-5 py-3 font-bold text-white hover:bg-orange-600">
                                Adicionar
                            </button>
                        </div>

                        <div className="overflow-x-auto rounded-2xl border border-zinc-200 bg-white shadow-lg">
                            <table className="w-full border-collapse">
                                <thead>
                                    <tr className="bg-zinc-900">
                                        <th className="px-4 py-4 text-center text-sm font-extrabold uppercase text-orange-500">Item</th>
                                        <th className="px-4 py-4 text-center text-sm font-extrabold uppercase text-orange-500">Quantidade</th>
                                        <th className="px-4 py-4 text-center text-sm font-extrabold uppercase text-orange-500">Preço</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {comanda.itens.map((item, indice) => (
                                        <tr key={indice} className="border-b-2 border-zinc-100">
                                            <td className="px-4 py-4 text-center font-bold text-zinc-900">{item.nome}</td>
                                            <td className="px-4 py-4 text-center font-bold text-zinc-800">{item.quantidade}</td>
                                            <td className="px-4 py-4 text-center font-bold text-zinc-800">R$ {item.preco}</td>
                                        </tr>
                                    ))}
                                    {comanda.itens.length === 0 && (
                                        <tr><td colSpan={3} className="px-4 py-8 text-center font-bold text-zinc-700">{Number(comanda.pedido.valorSubtotal || 0) > 0 ? "Itens lançados antes já estão somados no subtotal." : "Nenhum item adicionado!"}</td></tr>
                                    )}
                                </tbody>
                            </table>
                        </div>

                        {/* Valores calculados pelo backend (somente leitura) */}
                        <div className="grid grid-cols-3 gap-4 rounded-2xl border border-zinc-200 bg-white p-6 text-center shadow-md">
                            <div><span className="block text-sm text-zinc-500">Subtotal</span><span className="text-xl font-bold text-zinc-900">R$ {Number(comanda.pedido.valorSubtotal).toFixed(2)}</span></div>
                            <div><span className="block text-sm text-zinc-500">Taxa de serviço (10%)</span><span className="text-xl font-bold text-zinc-900">R$ {Number(comanda.pedido.taxaServico).toFixed(2)}</span></div>
                            <div><span className="block text-sm text-zinc-500">Total</span><span className="text-xl font-extrabold text-orange-600">R$ {Number(comanda.pedido.valorTotal).toFixed(2)}</span></div>
                        </div>

                        <div className="flex flex-wrap justify-center gap-3">
                            <button onClick={handleEnviarPreparo} className="rounded-lg bg-zinc-900 px-5 py-3 font-bold text-white hover:bg-zinc-700">Enviar para preparo</button>
                            <button onClick={handleMarcarPronto} className="rounded-lg bg-zinc-900 px-5 py-3 font-bold text-white hover:bg-zinc-700">Pronto</button>
                            <button onClick={handlePedirConta} className="rounded-lg bg-zinc-900 px-5 py-3 font-bold text-white hover:bg-zinc-700">Pedir conta</button>
                            <button onClick={handleFecharConta} className="rounded-lg bg-orange-500 px-5 py-3 font-bold text-white hover:bg-orange-600">Fechar conta</button>
                            <button onClick={handleVoltar} className="rounded-lg border border-zinc-300 bg-white px-5 py-3 font-bold text-zinc-700 hover:bg-zinc-100">Voltar</button>
                        </div>
                    </>
                )}

            </div>
        </div>
    )
}
