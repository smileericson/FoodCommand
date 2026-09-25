"use client"

import { Pedido } from "@/app/types/pedido";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Pedidos() {

    const [pedidos, setPedidos] = useState<Pedido[]>([]);

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {

        try {
            const dados = await axios.get<Pedido[]>("http://localhost:8080/pedido");

            setPedidos(dados.data);
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

                        <div className="text-center sm:text-left">

                            <span className="text-sm font-semibold uppercase tracking-wider text-orange-500">
                                Gestão
                            </span>

                            <h1 className="mt-2 text-2xl font-bold text-zinc-900 sm:text-3xl">
                                Meus Pedidos
                            </h1>

                            <p className="mt-1 text-sm text-zinc-500">
                                Consulte e acompanhe os pedidos realizados
                            </p>

                        </div>

                        <Link
                            href="/pedidos/novo"
                            className="rounded-lg bg-orange-500 px-5 py-3 text-center font-semibold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95"
                        >
                            + Novo Pedido
                        </Link>

                    </div>

                    {/* Legenda de status */}
                    <div className="mt-6 flex flex-wrap items-center justify-center gap-3 border-t border-zinc-200 pt-5">

                        <span className="text-sm font-semibold text-zinc-700">
                            Status:
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-green-100 px-4 py-2 text-sm font-semibold text-green-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-green-500"></span>
                            Ativo
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-red-100 px-4 py-2 text-sm font-semibold text-red-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-red-500"></span>
                            Cancelado
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-zinc-200 px-4 py-2 text-sm font-semibold text-zinc-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-zinc-500"></span>
                            Excluído
                        </span>

                    </div>

                </div>

                {/* Tabela de pedidos */}
                <div className="overflow-x-auto rounded-2xl border border-zinc-200 bg-white shadow-lg">

                    <table className="w-full min-w-[900px] border-collapse">

                        <thead>
                            <tr className="bg-zinc-900">

                                <th className="px-6 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:text-sm">
                                    ID
                                </th>

                                <th className="px-6 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:text-sm">
                                    Valor Subtotal
                                </th>

                                <th className="px-6 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:text-sm">
                                    Taxa Serviço
                                </th>

                                <th className="px-6 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:text-sm">
                                    Valor Total
                                </th>

                                <th className="px-6 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:text-sm">
                                    Status
                                </th>

                                <th className="px-6 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:text-sm">
                                    Ações
                                </th>

                            </tr>
                        </thead>

                        <tbody>

                            {pedidos.map((pedido) => (
                                <tr
                                    key={pedido.id}
                                    className="border-b border-zinc-100 transition duration-200 hover:bg-orange-50"
                                >

                                    <td className="px-6 py-4 text-center text-sm text-zinc-600">
                                        {pedido.id}
                                    </td>

                                    <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                        {pedido.valorSubtotal}
                                    </td>

                                    <td className="px-6 py-4 text-center text-sm font-medium text-zinc-900">
                                        {pedido.taxaServico}
                                    </td>

                                    <td className="px-6 py-4 text-center text-sm font-semibold text-zinc-900">
                                        {pedido.valorTotal}
                                    </td>

                                    <td className="px-6 py-4 text-center text-sm font-semibold text-zinc-800">
                                        {pedido.status}
                                    </td>

                                    <td className="px-6 py-4 text-center text-sm font-semibold text-zinc-800">
                                        —
                                    </td>

                                </tr>
                            ))}

                            {pedidos.length === 0 && (
                                <tr>
                                    <td
                                        colSpan={6}
                                        className="px-6 py-12 text-center text-sm font-medium text-slate-800"
                                    >
                                        Nenhum pedido encontrado!
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