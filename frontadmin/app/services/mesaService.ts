import { Mesa } from "../types/mesa";
import api from "./api";



export async function buscarListaMesa() : Promise<Mesa[]> {

    const dados = await api.get<Mesa[]>("/mesa");
    
    if(dados.status ==200){
        return dados.data;
    }

    return[]
    
}

export async function deletarMesa(id:number):Promise<number> {

    var dados = await api.delete('/mesa/' + id + '/excluir');
    return dados.status;
}

export async function atualizarStatusMesa(mesa:Mesa):Promise<void> {

        var novoStatus = {};

        if (mesa.statusMesa === "LIVRE") {
            novoStatus = { statusMesa: "OCUPADA" };
        }else if(mesa.statusMesa === "OCUPADA"){
            novoStatus = { statusMesa:"AGUARDANDO_FECHAMENTO"}
        } else {
            novoStatus = { statusMesa: "LIVRE" }
        }

        var dadosRetorno = await
            api.patch('/mesa/' + mesa.id + '/status', novoStatus);

        if (dadosRetorno.status == 200) {
            alert("Atulizado status com sucesso!");

        } else {
            alert(dadosRetorno.data);

            return;
        }
    
}

export async function consultaMesaPorId (id:Number): Promise<Mesa>{

     const valorUsuarioBack = await api.get<Mesa>('/mesa/' + id);
        return valorUsuarioBack.data;
}

export async function atualizarMesa (mesa:Mesa) : Promise<number>{
    var dadosRetorno = await api.put<number>('/mesa/' + mesa.id, mesa);
        return dadosRetorno.data;
}

export async function salvarMesa (mesa:Mesa) : Promise<number>{
    var dadosRetorno = await api.post<number>('/mesa', mesa);
        return dadosRetorno.data;
}