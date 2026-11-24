import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../redux/slices/authSlice"
import pedidoReducer from "../redux/slices/pedidoSlice"
import historicoPedidoReducer from "../redux/slices/historicoPedidoSlice"



export const store = configureStore({
    reducer:{
        auth: authReducer,
        pedido: pedidoReducer,
        historicoPedido: historicoPedidoReducer
        
    }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispath = typeof store.dispatch;