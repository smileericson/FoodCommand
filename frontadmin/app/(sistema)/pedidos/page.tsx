
import { Pedido } from "@/app/types/pedido";
import axios from "axios";
import { useEffect, useState } from "react";

export default function Pedidos(){

    const [pedidos, setPedidos] = useState<Pedido[]>([]);

    useEffect(() => {
        carregarPedidos();
    }, []);

    const carregarPedidos = async () => {

        try {
            const dados = await axios.get<Pedido[]>("http://localhost:8080/pedidos");

            setPedidos(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }


    }
    return(
        <div className="min-h-screen bg-gray-100 p-6">
            <div className="mx-auto max-w-6xl">
                <h1 className="mb-6 text-center text-3xl font-bold text-zinc-900">
                    Meus pedidos
                </h1>

                <link href="usuarios/novo" />

                <div>
                    <div className="overflow-hidden rounded-xl border border-zinc-200 bg-white shadow-md">
                        <table className="w-full border-collapse">
                            <thead>
                                <tr className="bg-zinc-900">
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        Nome
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                <tr className="transition hover:bg-orange-50">
                                    <td className="px-6 py-4 text-center text-sm font-medium text-zinc-800">
                                        pedido01
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    )
}