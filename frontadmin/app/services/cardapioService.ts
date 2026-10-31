import { Cardapios } from "../types/cardapio";
import api from "./api";



export async function buscarListaCardapio() : Promise<Cardapios[]> {

    const dados = await api.get<Cardapios[]>("http://localhost:8080/cardapio");
    
    if(dados.status ==200){
        return dados.data;
    }

    return[]
    
}

export async function deletarCardapio(id:number):Promise<number> {

    var dados = await api.delete('http://localhost:8080/cardapio/' + id + '/excluir');
    return dados.status;
}

export async function atualizarStatusCardapio(cardapio:Cardapios):Promise<void> {

          var novoStatus = {};
        if (cardapio.statusCardapio === "ATIVO" ) {
            novoStatus = { statusCardapio: "INATIVO" }
        } else {
            novoStatus = { statusCardapio: "ATIVO" }
        }

        var dadosRetorno = await
            api.patch('http://localhost:8080/cardapio/' + cardapio.id + '/status', novoStatus);

        if (dadosRetorno.status == 200) {
            alert("Atulizado status com sucesso!");
        } else {
            alert(dadosRetorno.data);

            return;
        }
    
}

export async function consultaCardapioPorId (id:Number): Promise<Cardapios>{

     const valorUsuarioBack = await api.get<Cardapios>('http://localhost:8080/cardapio/' + id);
        return valorUsuarioBack.data;
}

export async function atualizarCardapio (cardapio:Cardapios) : Promise<number>{
    var dadosRetorno = await api.put<number>('http://localhost:8080/cardapio/' + cardapio.id, cardapio);
        return dadosRetorno.data;
}

export async function salvarCardapio (cardapio:Cardapios) : Promise<number>{
    var dadosRetorno = await api.post<number>('http://localhost:8080/usuarios/' + cardapio.id, cardapio);
        return dadosRetorno.data;
}

