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
            const dados = await axios.get<Pedido[]>("http://localhost:8080/pedidos");

            setPedidos(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }

    }

    return (
        <div className="min-h-screen bg-gray-100 p-6">
            <div className="mx-auto max-w-7xl">
                <h1 className="mb-6 text-center text-3xl font-bold text-zinc-900">
                    Meus pedidos
                </h1>

                <Link href="/pedidos/novo" className="mb-4 inline-block rounded-lg bg-orange-500 px-4 py-2 text-white hover:bg-orange-600">
                    + Novo Pedido
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
                                        ValorSubtotal
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        TaxaServico
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        ValorTotal
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        Status
                                    </th>
                                </tr>
                            </thead>

                            <tbody>

                                {pedidos.map((pedido) => (
                                    <tr
                                        key={pedido.id}
                                        className="border-b border-zinc-200 transition hover:bg-orange-50"
                                    >
                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {pedido.id}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {pedido.valorSubtotal}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm font-medium text-zinc-900">
                                            {pedido.taxaServico}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {pedido.valorTotal}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {pedido.status}
                                        </td>
                                    </tr>
                                ))}

                                {pedidos.length === 0 &&
                                (
                                    <tr>
                                        <td
                                            colSpan={5}
                                            className="px-6 py-12 text-center text-slate-800"
                                        >
                                            Nenhum Pedido encontrado!
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