import { Pedido } from "../types/pedido";
import api from "./api";



export async function buscarListaPedidos() : Promise<Pedido[]> {

    const dados = await api.get<Pedido[]>("/pedidos");
    
    if(dados.status ==200){
        return dados.data;
    }

    return[]
    
}

export async function deletarPedido(id:number):Promise<number> {

    var dados = await api.delete('/pedidos/' + id + '/excluir');
    return dados.status;
}

export async function abrirAtendimento(mesaId:number) : Promise<Pedido>{
    var dadosRetorno = await api.post<Pedido>('/pedidos/mesa/' + mesaId + '/abrir');
        return dadosRetorno.data;
}

export async function adicionarItem(pedidoId:number, cardapioId:number, quantidade:number) : Promise<Pedido>{
    var dadosRetorno = await api.post<Pedido>('/pedidos/' + pedidoId + '/itens',
        { cardapioId: cardapioId, quantidade: quantidade });
        return dadosRetorno.data;
}

export async function enviarParaPreparo(pedidoId:number) : Promise<Pedido>{
    var dadosRetorno = await api.patch<Pedido>('/pedidos/' + pedidoId + '/enviar-preparo');
        return dadosRetorno.data;
}

export async function marcarPronto(pedidoId:number) : Promise<Pedido>{
    var dadosRetorno = await api.patch<Pedido>('/pedidos/' + pedidoId + '/pronto');
        return dadosRetorno.data;
}

export async function pedirConta(pedidoId:number) : Promise<Pedido>{
    var dadosRetorno = await api.patch<Pedido>('/pedidos/' + pedidoId + '/pedir-conta');
        return dadosRetorno.data;
}

export async function fecharConta(pedidoId:number) : Promise<Pedido>{
    var dadosRetorno = await api.patch<Pedido>('/pedidos/' + pedidoId + '/fechar-conta');
        return dadosRetorno.data;
}
