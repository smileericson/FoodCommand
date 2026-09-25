"use client"



import { Item } from "@/app/types/item";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Menu() {

    const [itens, setItens] = useState<Item[]>([]);

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {

        try {
            const dados = await axios.get<Item[]>("http://localhost:8080/menu");

            setItens(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }

    }

    return (
        <div className="min-h-screen bg-gray-100 p-6">
            <div className="mx-auto max-w-7xl">
                <h1 className="mb-6 text-center text-3xl font-bold text-zinc-900">
                    Cardápio
                </h1>

                <Link href="/menu/novo" className="mb-4 inline-block rounded-lg bg-orange-500 px-4 py-2 text-white hover:bg-orange-600">
                    + Novo Item
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
                                        NOME
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                       DESCRIÇÃO
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        PREÇO
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        STATUS
                                    </th>
                                     <th className="px-4 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                        ACÕES
                                </th>
                                </tr>
                            </thead>

                            <tbody>

                                {itens.map((item) => (
                                    <tr
                                        key={item.id}
                                        className="border-b border-zinc-200 transition hover:bg-orange-50"
                                    >
                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {item.id}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm font-medium text-zinc-900">
                                            {item.nome}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {item.descricao}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {item.preco}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {item.disponivel ? "Sim" : "Não"}
                                        </td>
                                    </tr>
                                ))}

                                {itens.length === 0 &&
                                (
                                    <tr>
                                        <td
                                            colSpan={5}
                                            className="px-6 py-12 text-center text-slate-800"
                                        >
                                            Nenhum item encontrado!
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