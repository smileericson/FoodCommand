'use client'

import axios from "axios";
import { store } from "../redux/store";
import { logout } from "../redux/slices/authSlice";
import { fecharComanda } from "../redux/slices/pedidoSlice";
import { limparHistorico } from "../redux/slices/historicoPedidoSlice";


const api = axios.create({
    baseURL:'http://localhost:8080'
});

// Interceptor de requisição: busca o token na store do Redux e injeta no cabeçalho de todas as chamadas.
api.interceptors.request.use(
    (config)=>{

        const token = store.getState().auth.token;

        if(token){
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    }
);

// Interceptor de resposta: se a API devolver 401 (token expirado/inválido), despacha o logout no Redux
// e o layout do sistema renderiza a tela de login.
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            store.dispatch(logout());
            store.dispatch(fecharComanda());
            store.dispatch(limparHistorico());
        }
        return Promise.reject(error);
    }
);

export default api;