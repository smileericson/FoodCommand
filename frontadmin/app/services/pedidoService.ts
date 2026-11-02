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

export async function atualizarStatusUsuario(pedido:Pedido):Promise<void> {

        var novoStatus = {};

        if (pedido.statusPedido === "ATIVO") {
            novoStatus = { statusPedido: "CANCELADO" }
        } else {
            novoStatus = { statusPedido: "ATIVO" }
        }

        var dadosRetorno = await
            api.patch('/pedidos/' + pedido.id + '/status', novoStatus);

        if (dadosRetorno.status == 200) {
            alert("Atulizado status com sucesso!");

        } else {
            alert(dadosRetorno.data);

            return;
        }
    
}

export async function consultaPedidoPorId (id:Number): Promise<Pedido>{

     const valorUsuarioBack = await api.get<Pedido>('/pedidos/' + id);
        return valorUsuarioBack.data;
}

export async function atualizarPedido (pedido:Pedido) : Promise<number>{
    var dadosRetorno = await api.put<number>('/pedidos/' + pedido.id, pedido);
        return dadosRetorno.data;
}

export async function salvarPedido (pedido:Pedido) : Promise<number>{
    var dadosRetorno = await api.post<number>('/pedidos' , pedido);
        return dadosRetorno.data;
}