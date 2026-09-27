'use client'

import Link from "next/link";
import MenuForm from "../../components/MenuForm";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Menus } from "@/app/types/menus";
import axios from "axios";

export default function EditarCardapio() {
    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    const [menu,setMenu] = useState<Menus|null>(null)
    const router = useRouter();

    useEffect(()=>{

        buscarDados();

    },[]);

    const buscarDados =async() =>{

        const valorMenuBack = await axios.get<Menus>('http://localhost:8080/menu/'+codigo);

        if(valorMenuBack.status==200){
            setMenu(valorMenuBack.data);
        }else{
            router.push("/menu")
        }

    }

    if(!menu) return(<div className="p-8"> Carregando Dados ...</div>)
    return (
        <div className="min-h-screen bg-gray-100 p-4 sm:p-6">
            <div className="mx-auto max-w-7xl space-y-6">

                <div className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-sm sm:p-8">

                    <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">

                        <div className="space-y-2">
                            <span className="text-sm font-bold uppercase tracking-wider text-orange-500">
                                Editar cardápio
                            </span>

                            <h1 className="text-2xl font-extrabold text-zinc-900 sm:text-3xl">
                                Editar item do cardápio
                            </h1>

                            <p className="text-sm text-zinc-500 sm:text-base">
                                Preencha os dados para editar o item.
                            </p>
                        </div>

                        <Link
                            href="/menu"
                            className="inline-flex items-center justify-center rounded-xl border border-zinc-300 bg-white px-5 py-3 text-sm font-bold text-zinc-700 transition hover:border-orange-500 hover:bg-orange-50 hover:text-orange-600"
                        >
                            Voltar ao cardápio
                        </Link>

                    </div>
                </div>

                <div className="overflow-hidden rounded-2xl border border-zinc-200 bg-white shadow-sm">

                    <div className="border-b border-zinc-200 bg-zinc-900 px-6 py-5 sm:px-8">

                        <h2 className="text-xl font-bold text-orange-500">
                            Dados do Cardápio
                        </h2>

                        <p className="mt-2 text-sm text-zinc-300">
                            Preencha as informações abaixo para realizar a edição do item do cardápio.
                        </p>

                    </div>

                    <div className="p-6 sm:p-8">
                        <MenuForm menuExistente={menu} />
                    </div>

                </div>

            </div>
        </div>
    );
}