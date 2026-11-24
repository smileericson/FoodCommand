import { AuthState } from "@/app/types/auth";
import { Usuario } from "@/app/types/usuario";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";


// Estado inicial: token e usuário ficam somente na store do Redux.
const initialState: AuthState = {
    usuario: null,
    token : ""
}

const authSlice = createSlice({
    name :'auth',
    initialState,
    reducers:{
        // Guarda o token e os dados do usuário logado na store.
        login:( state,action:PayloadAction<{token:string,usuario:Usuario}>) =>{
            state.token = action.payload.token;
            state.usuario = action.payload.usuario;

        },
        // Limpa o token e o usuário da store (o layout passa a renderizar a tela de login).
        logout:( state ) =>{
            state.token = "";
            state.usuario = null;

        },
        // Guarda só o token na store (usado no login, antes de buscar o usuário logado).
        setToken:( state, action:PayloadAction<string> ) =>{
            state.token = action.payload;
        }
    }
});

export const {login,logout,setToken} = authSlice.actions;
export default authSlice.reducer;




