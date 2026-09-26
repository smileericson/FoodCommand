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

    const handleDeletarMesa = async (mesa: Mesa) => {

        var dadosRetorno = await
            axios.delete('http://localhost:8080/mesas/' + mesa.id + '/excluir');

        if (dadosRetorno.status == 200) {
            alert("Excluido com sucesso!");

        } else {
            alert(dadosRetorno.data);
            return;
        }

        carregarDados();
    }

    const handleAlterarStatusMesa = async (mesa: Mesa) => {

        var novoStatus = {};

        if (mesa.statusMesa === "LIVRE") {
            novoStatus = { statusMesa: "OCUPADA" }

        } else {
            novoStatus = { statusMesa: "LIVRE" }
        }

        var dadosRetorno = await
            axios.patch('http://localhost:8080/mesas/' + mesa.id + '/status', novoStatus);

        if (dadosRetorno.status == 200) {
            alert("Atulizado status com sucesso!");

        } else {
            alert(dadosRetorno.data);

            return;
        }

        carregarDados();

    }

    return (
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">

            <div className="mx-auto max-w-7xl">

                {/* Cabeçalho */}
                <div className="mb-6 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md">

                    <h1 className="text-center text-2xl font-extrabold text-zinc-900 sm:text-3xl">
                        Gestão de Mesas
                    </h1>

                    <p className="mt-1 text-center text-sm font-medium text-zinc-600">
                        Consulte e gerencie as mesas do restaurante
                    </p>

                    {/* Legenda de status */}
                    <div className="mt-5 flex flex-wrap items-center justify-center gap-3 border-t-2 border-zinc-200 pt-5">

                        <span className="text-sm font-bold text-zinc-800">
                            Status:
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-green-100 px-4 py-2 text-sm font-bold text-green-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-green-500"></span>
                            Livre
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-red-100 px-4 py-2 text-sm font-bold text-red-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-red-500"></span>
                            Ocupada
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-orange-100 px-4 py-2 text-sm font-bold text-orange-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-orange-500"></span>
                            Aguardando fechamento
                        </span>

                    </div>

                </div>

                {/* Botão de nova mesa */}
                <div className="mb-6 flex justify-end">

                    <Link
                        href="/mesas/novo"
                        className="rounded-lg bg-orange-500 px-5 py-3 text-center font-bold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95"
                    >
                        + Nova Mesa
                    </Link>

                </div>

                {/* Tabela de mesas */}
                <div className="overflow-x-auto rounded-2xl border border-zinc-200 bg-white shadow-lg">

                    <table className="w-full min-w-[700px] border-collapse">

                        <thead>
                            <tr className="bg-zinc-900">

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    ID
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Número
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Status Mesa
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Ações
                                </th>

                            </tr>
                        </thead>

                        <tbody>

                            {mesas.map((mesa) => (
                                <tr
                                    key={mesa.id}
                                    className="border-b-2 border-zinc-100 transition duration-200 hover:bg-orange-50"
                                >

                                    {/* ID */}
                                    <td className="px-4 py-5 text-center text-sm font-semibold text-zinc-800 sm:px-6">
                                        {mesa.id}
                                    </td>

                                    {/* Número */}
                                    <td className="px-4 py-5 text-center text-sm font-bold text-zinc-900 sm:px-6">
                                        {mesa.numero}
                                    </td>

                                    {/* Status */}
                                    <td className="px-4 py-5 text-center text-sm font-bold text-zinc-900 sm:px-6">
                                        {mesa.statusMesa}
                                    </td>

                                    {/* Ações */}
                                    <td className="px-4 py-5 text-center text-sm font-bold text-zinc-800 sm:px-6">

                                        <div className="flex flex-col items-center justify-center gap-3">

                                            <Link
                                                href={`/usuarios/${mesa.id}/editar`}
                                                className="font-bold text-orange-600 transition-colors hover:text-orange-800 hover:underline"
                                            >
                                                Editar
                                            </Link>

                                            <button
                                                onClick={() => handleDeletarMesa(mesa)}
                                                className="font-bold text-red-600 transition-colors hover:text-red-800"
                                            >
                                                DELETAR
                                            </button>

                                            <button
                                                onClick={() => handleAlterarStatusMesa(mesa)}
                                                className={`font-bold transition-colors ${
                                                    mesa.statusMesa === 'BLOQUEADO'
                                                        ? 'text-orange-600 hover:text-orange-800'
                                                        : 'text-green-600 hover:text-green-800'
                                                }`}
                                            >
                                                {mesa.statusMesa}
                                            </button>

                                        </div>

                                    </td>

                                </tr>
                            ))}

                            {mesas.length === 0 && (
                                <tr>
                                    <td
                                        colSpan={4}
                                        className="px-6 py-12 text-center text-base font-bold text-zinc-700"
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