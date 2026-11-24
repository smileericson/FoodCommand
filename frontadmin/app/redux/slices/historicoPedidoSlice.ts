import { HistoricoPedidoState } from "@/app/types/pedido";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";


// Estado inicial: nenhum pedido consultado.
const initialState: HistoricoPedidoState = {
    ultimosConsultados: []
}

const historicoPedidoSlice = createSlice({
    name :'historicoPedido',
    initialState,
    reducers:{
        // Coloca o pedido consultado no topo da lista (o mais recente fica primeiro).
        registrarConsulta:( state,action:PayloadAction<number>) =>{
            state.ultimosConsultados = state.ultimosConsultados.filter(id => id !== action.payload);
            state.ultimosConsultados.unshift(action.payload);
        },
        // Limpa o histórico (usuário saiu).
        limparHistorico:( state ) =>{
            state.ultimosConsultados = [];
        }
    }
});

export const {registrarConsulta,limparHistorico} = historicoPedidoSlice.actions;
export default historicoPedidoSlice.reducer;
