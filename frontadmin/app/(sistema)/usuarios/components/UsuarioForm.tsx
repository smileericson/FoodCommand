'use client'

import { Usuario, UsuarioFormProps } from "@/app/types/usuario";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";


import { useState } from "react";

export default function UsuarioForm({usuarioExistente}:UsuarioFormProps) {
    const router = useRouter();

    const [usuario,setUsuario] = useState<Usuario>(
        usuarioExistente || new Usuario(null,"","","ATIVO","",""));

    const handlerChange = ( campo: 'nome'|'cpf'|'email'|'senha',valor:string) =>{
        setUsuario(valorAnterior =>
            new Usuario(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'email' ? valor : valorAnterior.email,
                valorAnterior.status,
                campo === 'cpf' ? valor : valorAnterior.cpf,
                campo === 'senha' ? valor : valorAnterior.senha
                
            )
        )
    }

    const handlerSalvar = async(formData : FormData) =>{

        if(usuarioExistente){

       var dadosRetorno=await 
       axios.post<number>('http://localhost:8080/usuarios',usuario)
       
       if(dadosRetorno.status==200){
        alert("Usuario foi salvo com sucesso!");
        
       }else{
        alert(dadosRetorno.data);
        return;
       }
    }
    router.push("/usuarios");
    }
    return (
        <form action={handlerSalvar} className="space-y-6">

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    Nome completo:
                </label>

                <input 
                    name="nome"
                    value={usuario.nome}
                    required
                    onChange={(e)=>handlerChange('nome',e.target.value)}
                    placeholder="Ericson Smile"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    CPF:
                </label>

                <input
                    name="CPF"
                     value={usuario.cpf}
                     required
                     onChange={(e)=>handlerChange('cpf',e.target.value)}
                     placeholder="000.000.000-00"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    E-mail:
                </label>

                <input
                    name="email"
                     value={usuario.email}
                     required
                     onChange={(e)=>handlerChange('email',e.target.value)}
                     placeholder="EricsonSmile@gmail.com"
                    type="email"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    Senha:
                </label>

                <input
                    name="senha"
                     value={usuario.senha}
                     required
                     onChange={(e)=>handlerChange('senha',e.target.value)}
                     placeholder="*************"
                    type="password"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div className="flex flex-col-reverse items-center justify-center gap-3 border-t border-zinc-200 pt-6 sm:flex-row">

                <Link
                    href="/usuarios"
                    className="w-full rounded-lg border border-zinc-300 bg-zinc-100 px-6 py-3 text-center font-semibold text-zinc-700 transition duration-200 hover:border-red-400 hover:bg-red-50 hover:text-red-600 sm:w-auto"
                >
                    Cancelar
                </Link>

                <button
                    className="w-full rounded-lg bg-orange-500 px-6 py-3 font-semibold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95 sm:w-auto"
                >
                    Salvar
                </button>

            </div>

        </form>
    );
}