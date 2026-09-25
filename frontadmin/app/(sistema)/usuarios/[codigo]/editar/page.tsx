"use client"

import { useParams } from "next/navigation";
import UsuarioForm from "../../components/UsuarioForm";
import Link from "next/link";
import { useEffect, useState } from "react";
import { Usuario } from "@/app/types/usuario";
import axios from "axios";
import { useRouter } from "next/router";


export default function EditarUsuario() {

    const parametro = useParams();
    const codigo = Number(parametro.codigo);

    const [usuario, setUsuario] = useState<Usuario|null>(null)
    const router = useRouter();

    useEffect(()=>{

    },[]);

    const buscarDados=async()=>{

        const valorUsuarioBack = await axios.get<Usuario>('http://localhost:8080/usuarios/'+codigo);

        if(valorUsuarioBack.status==200){
            setUsuario(valorUsuarioBack.data);
         }
         router.push("/usuarios")
    }

    if(!usuario)return(<div className="p-8">Carregando Dados...</div>)

    return (
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">

            <div className="mx-auto max-w-5xl">

                {/* Cabeçalho */}
                <div className="mb-6 flex flex-col items-center justify-between gap-4 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md sm:flex-row">

                    <div className="text-center sm:text-left">

                        <span className="text-sm font-semibold uppercase tracking-wider text-orange-500">
                            Editar Usuário {codigo}
                        </span>

                        <h1 className="mt-2 text-xl font-bold text-zinc-900 sm:text-2xl">
                            Preencha os dados para editar o usuário
                        </h1>

                    </div>

                    <Link
                        href="/usuarios"
                        className="rounded-lg border border-zinc-300 bg-zinc-100 px-5 py-3 text-center text-sm font-semibold text-zinc-800 transition duration-200 hover:border-orange-500 hover:bg-orange-50 hover:text-orange-600"
                    >
                        Voltar para listagem
                    </Link>

                </div>

                {/* Formulário */}
                <div className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-lg sm:p-8">

                    <UsuarioForm usuarioExistente={usuario} />

                </div>

            </div>

        </div>
    );
}