import { ComandaState, ItemComanda, Pedido } from "@/app/types/pedido";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";


// Estado inicial: nenhuma comanda aberta.
const initialState: ComandaState = {
    pedido: null,
    numeroMesa: null,
    itens: []
}

const pedidoSlice = createSlice({
    name :'pedido',
    initialState,
    reducers:{
        // Guarda o pedido aberto e a mesa que está sendo atendida.
        abrirComanda:( state,action:PayloadAction<{pedido:Pedido,numeroMesa:number | null}>) =>{
            state.pedido = action.payload.pedido;
            state.numeroMesa = action.payload.numeroMesa;
            state.itens = [];
        },
        // Atualiza valores e status com o que o backend devolveu.
        atualizarComanda:( state,action:PayloadAction<Pedido>) =>{
            state.pedido = action.payload;
        },
        // Coloca o prato na lista de itens da comanda.
        adicionarItemComanda:( state,action:PayloadAction<ItemComanda>) =>{
            state.itens.push(action.payload);
        },
        // Limpa a comanda (conta fechada ou usuário saiu).
        fecharComanda:( state ) =>{
            state.pedido = null;
            state.numeroMesa = null;
            state.itens = [];
        }
    }
});

export const {abrirComanda,atualizarComanda,adicionarItemComanda,fecharComanda} = pedidoSlice.actions;
export default pedidoSlice.reducer;
