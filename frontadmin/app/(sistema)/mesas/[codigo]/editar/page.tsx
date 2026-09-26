"use client"

import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import axios from "axios";
import { Mesa } from "@/app/types/mesa";
import MesaForm from "../../components/MesaForm";

export default function EditarMesa() {

    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    const [mesa, setMesa] = useState<Mesa | null>(null);
    const router = useRouter();

    useEffect(() => {
        buscarDados();
    }, []);

    const buscarDados = async () => {

             const valorMesaBack = await axios.get<Mesa>("http://localhost:8080/mesa/" + codigo);

            if(valorMesaBack.status==200){
                setMesa(valorMesaBack.data);
            }else{
                router.push("/mesas")
            }
    }

            if(!mesa) return(<div className="p-8"> Carregando Dados ...</div>)
                
    return (
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">

            <div className="mx-auto max-w-5xl">

                {/* Cabeçalho */}
                <div className="mb-6 flex flex-col items-center justify-between gap-4 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md sm:flex-row">

                    <div className="text-center sm:text-left">

                        <span className="text-sm font-semibold uppercase tracking-wider text-orange-500">
                            Editar Mesa {codigo}
                        </span>

                        <h1 className="mt-2 text-xl font-bold text-zinc-900 sm:text-2xl">
                            Preencha os dados para editar a mesa
                        </h1>

                    </div>

                    <Link
                        href="/mesas"
                        className="rounded-lg border border-zinc-300 bg-zinc-100 px-5 py-3 text-center text-sm font-semibold text-zinc-800 transition duration-200 hover:border-orange-500 hover:bg-orange-50 hover:text-orange-600"
                    >
                        Voltar para listagem
                    </Link>

                </div>

                {/* Informações do formulário */}
                <div className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-lg sm:p-8">

                    <div className="mb-6 border-b border-zinc-200 pb-4 text-center">

                        <h2 className="text-lg font-bold text-zinc-900">
                            Dados da Mesa
                        </h2>

                        <p className="mt-1 text-sm text-zinc-500">
                            Altere as informações abaixo para atualizar a mesa
                        </p>

                    </div>

                    {/* Formulário */}
                    {mesa && (
                        <MesaForm mesaExistente={mesa} />
                    )}

                </div>

            </div>

        </div>
    );
}