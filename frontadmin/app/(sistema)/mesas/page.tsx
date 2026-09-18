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
            const dados = await axios.get<Mesa[]>("http://localhost:8080/mesas");

            setMesas(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }

    }

    return (
        <div className="bg-gray-100 p-6">
            <div className="mx-auto max-w-7xl">
                <h1 className="mb-6 text-center text-3xl font-bold text-zinc-900">
                    Gestão de Mesas
                </h1>

                <Link href="/mesas/novo" className="mb-4 inline-block rounded-lg bg-orange-500 px-4 py-2 text-white hover:bg-orange-600">
                    + Nova Mesa
                </Link>

                <div>
                    <div className="overflow-hidden rounded-xl border border-zinc-200 bg-white shadow-md">
                        <table className="w-full border-collapse">
                            <thead>
                                <tr className="bg-zinc-900">
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        ID
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        Número
                                    </th>
                                </tr>
                            </thead>

                            <tbody>

                                {mesas.map((mesa) => (
                                    <tr
                                        key={mesa.id}
                                        className="border-b border-zinc-200 transition hover:bg-orange-50"
                                    >
                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {mesa.id}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm font-medium text-zinc-900">
                                            {mesa.numero}
                                        </td>
                                    </tr>
                                ))}

                                {mesas.length === 0 &&
                                (
                                    <tr>
                                        <td
                                            colSpan={2}
                                            className="px-6 py-12 text-center text-slate-800"
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
        </div>
    )
}