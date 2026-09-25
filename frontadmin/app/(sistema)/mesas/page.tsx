"use client"

import { Mesa } from "@/app/types/mesa";
import axios from "axios"
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Mesas() {

    const [mesas, setMesas] = useState<Mesa[]>([]);

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {

        try {
            const dados = await axios.get<Mesa[]>("http://localhost:8080/mesa");

            setMesas(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }

    }

    return (
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">

            <div className="mx-auto max-w-7xl">

                {/* Cabeçalho */}
                <div className="mb-6 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md">

                    <div className="flex flex-col items-center justify-between gap-4 sm:flex-row">

                        <h1 className="text-center text-2xl font-bold text-zinc-900 sm:text-3xl">
                            <span className="text-orange-500">
                                Gestão
                            </span>{" "}
                            de Mesas
                        </h1>

                        <Link
                            href="/mesas/novo"
                            className="rounded-lg bg-orange-500 px-5 py-3 text-center font-semibold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95"
                        >
                            + Nova Mesa
                        </Link>

                    </div>

                    {/* Legenda de status */}
                    <div className="mt-6 flex flex-wrap items-center justify-center gap-3 border-t border-zinc-200 pt-5">

                        <span className="text-sm font-semibold text-zinc-700">
                            Status:
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-green-100 px-4 py-2 text-sm font-semibold text-green-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-green-500"></span>
                            Livre
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-red-100 px-4 py-2 text-sm font-semibold text-red-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-red-500"></span>
                            Ocupada
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-orange-100 px-4 py-2 text-sm font-semibold text-orange-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-orange-500"></span>
                            Aguardando fechamento
                        </span>

                    </div>

                </div>

                {/* Tabela */}
                <div className="overflow-x-auto rounded-2xl border border-zinc-200 bg-white shadow-lg">

                    <table className="w-full min-w-[600px] border-collapse">

                        <thead>
                            <tr className="bg-zinc-900">

                                <th className="px-6 py-4 text-center text-sm font-bold uppercase tracking-wider text-orange-500">
                                    ID
                                </th>

                                <th className="px-6 py-4 text-center text-sm font-bold uppercase tracking-wider text-orange-500">
                                    Número
                                </th>

                                <th className="px-6 py-4 text-center text-sm font-bold uppercase tracking-wider text-orange-500">
                                    Status Mesa
                                </th>
                                <th className="px-6 py-4 text-center text-sm font-bold uppercase tracking-wider text-orange-500"> 
                                    Editar </th>

                            </tr>
                        </thead>

                        <tbody>

                            {mesas.map((mesa) => (
                                <tr
                                    key={mesa.id}
                                    className="border-b border-zinc-100 transition duration-200 hover:bg-orange-50"
                                >

                                    <td className="px-6 py-4 text-center text-sm text-zinc-600">
                                        {mesa.id}
                                    </td>

                                    <td className="px-6 py-4 text-center text-sm font-semibold text-zinc-900">
                                        {mesa.numero}
                                    </td>

                                    <td className="px-6 py-4 text-center text-sm font-semibold text-zinc-900">
                                        {mesa.statusMesa}
                                    </td>

                                </tr>
                            ))}

                            {mesas.length === 0 && (
                                <tr>
                                    <td
                                        colSpan={3}
                                        className="px-6 py-12 text-center text-sm font-medium text-slate-800"
                                    >
                                        Nenhuma mesa encontrada!
                                    </td>
                                </tr>
                            )}

                        </tbody>

                    </table>

                </div>

            </div>

        </div>
    )
}