'use client'

import { Menus } from "@/app/types/menus";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Menu() {

    const [menu, setMenu] = useState<Menus[]>([]);

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {

        try {
            const dados = await axios.get<Menus[]>("http://localhost:8080/menu");

            setMenu(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }

    }
    const handleDeletarUsuario = async (menu: Menus) => {

        var dadosRetorno = await
            axios.delete('http://localhost:8080/menu/' + menu.id + '/excluir');

        if (dadosRetorno.status == 200) {
            alert("Excluido com sucesso!");
        } else {
            alert(dadosRetorno.data);

            return;
        }

        carregarDados();

    }

    const handleAlterarStatusUsuario = async (menu: Menus) => {


        var novoStatus = {};
        if (menu.statusMenu === "ATIVO" ) {
            novoStatus = { statusMenu: "INATIVO" }
        } else {
            novoStatus = { statusMenu: "ATIVO" }
        }

        var dadosRetorno = await
            axios.patch('http://localhost:8080/menu/' + menu.id + '/status', novoStatus);

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
                        Gestão do Cardápio
                    </h1>

                    <p className="mt-2 text-center text-sm font-medium text-zinc-600">
                        Consulte e gerencie os itens do cardápio do restaurante
                    </p>

                    {/* Legenda de status */}
                    <div className="mt-5 flex flex-wrap items-center justify-center gap-3 border-t-2 border-zinc-200 pt-5">

                        <span className="text-sm font-bold text-zinc-800">
                            Status:
                        </span>

                        {/* Ativo */}
                        <span className="flex items-center gap-2 rounded-full bg-green-100 px-4 py-2 text-sm font-bold text-green-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-green-500"></span>
                            ATIVO
                        </span>

                        {/* Inativo */}
                        <span className="flex items-center gap-2 rounded-full bg-yellow-100 px-4 py-2 text-sm font-bold text-yellow-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-yellow-500"></span>
                            INATIVO
                        </span>

                        {/* Excluído */}
                        <span className="flex items-center gap-2 rounded-full bg-zinc-200 px-4 py-2 text-sm font-bold text-zinc-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-zinc-500"></span>
                            EXCLUÍDO
                        </span>

                    </div>

                </div>

                {/* Botão de novo item */}
                <div className="mb-6 flex justify-end">

                    <Link
                        href="/menu/novo"
                        className="rounded-lg bg-orange-500 px-5 py-3 text-center font-bold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95"
                    >
                        + Novo Item
                    </Link>

                </div>

                {/* Tabela do cardápio */}
                <div className="overflow-x-auto rounded-2xl border border-zinc-200 bg-white shadow-lg">

                    <table className="w-full min-w-[900px] border-collapse">

                        <thead>
                            <tr className="bg-zinc-900">

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    ID
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Nome
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Descrição
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Preço
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Status
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Ações
                                </th>

                            </tr>
                        </thead>

                        <tbody>

                            {menu.map((menu) => (
                                <tr
                                    key={menu.id}
                                    className="border-b-2 border-zinc-100 transition duration-200 hover:bg-orange-50"
                                >

                                    {/* ID */}
                                    <td className="px-4 py-5 text-center text-sm font-semibold text-zinc-800 sm:px-6">
                                        {menu.id}
                                    </td>

                                    {/* Nome */}
                                    <td className="px-4 py-5 text-center text-sm font-bold text-zinc-900 sm:px-6">
                                        {menu.nome}
                                    </td>

                                    {/* Descrição */}
                                    <td className="px-4 py-5 text-center text-sm font-medium text-zinc-800 sm:px-6">
                                        {menu.descricao}
                                    </td>

                                    {/* Preço */}
                                    <td className="px-4 py-5 text-center text-sm font-bold text-zinc-900 sm:px-6">
                                        {menu.preco}
                                    </td>

                                    {/* Status */}
                                    <td className="px-4 py-5 text-center text-sm font-bold text-zinc-800 sm:px-6">
                                        {menu.statusMenu}
                                    </td>

                                    {/* Ações */}
                                    <td className="px-4 py-5 text-center text-sm font-bold text-zinc-800 sm:px-6">

                                        <div className="flex flex-col items-center justify-center gap-3">

                                            <Link
                                                href={`/menu/${menu.id}/editar`}
                                                className="font-bold text-orange-600 transition-colors hover:text-orange-800 hover:underline"
                                            >
                                                Editar
                                            </Link>
                                            <button
                                                onClick={() => handleDeletarUsuario(menu)}
                                                className="font-bold text-red-600 transition-colors hover:text-red-800"
                                            >
                                                DELETAR
                                            </button>

                                            <button
                                                onClick={() => handleAlterarStatusUsuario(menu)}
                                                className={`font-bold transition-colors ${menu.statusMenu === 'INATIVO'
                                                        ? 'text-orange-600 hover:text-orange-800'
                                                        : menu.statusMenu === 'EXCLUIDO'
                                                            ? 'text-black-600 hover:text-black-800'
                                                            : 'text-green-600 hover:text-green-800'
                                                    }`}
                                            >
                                                {menu.statusMenu}
                                            </button>

                                        </div>

                                    </td>

                                </tr>
                            ))}

                            {menu.length === 0 && (
                                <tr>
                                    <td
                                        colSpan={6}
                                        className="px-6 py-12 text-center text-base font-bold text-zinc-700"
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
    )
}