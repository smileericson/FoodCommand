"use client"

import Link from "next/link";
import { Usuario } from "@/app/types/usuario";
import axios from "axios"
import { useEffect, useState } from "react";

export default function Usuarios() {

    const [usuarios, setUsuarios] = useState<Usuario[]>([]);

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {

        try {
            const dados = await axios.get<Usuario[]>("http://localhost:8080/usuarios");

            setUsuarios(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }

    }

    const handleDeletarUsuario = async (usuario: Usuario) => {
        var dadosRetorno = await
            axios.delete('http://localhost:8080/usuarios/' + usuario.id + '/excluir');

        if (dadosRetorno.status == 200) {
            alert("Excluido com sucesso!");

        } else {
            alert(dadosRetorno.data);
            return;
        }
        carregarDados
    }

    const handleAlterarStatusUsuario = async (usuario: Usuario) => {

        var novoStatus = {};

        if (usuario.status === "ATIVO") {
            novoStatus = { status: "BLOQUEADO" }
        } else {
            novoStatus = { status: "ATIVO" }
        }

        var dadosRetorno = await
            axios.patch('http://localhost:8080/usuarios/' + usuario.id + '/status', novoStatus);

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

                    <h1 className="text-center text-2xl font-bold text-zinc-900 sm:text-3xl">
                        Gestão de usuários
                    </h1>

                    <p className="mt-1 text-center text-sm text-zinc-500">
                        Preencha as informações do cadastro do GARÇOM
                    </p>

                    {/* Legenda de status */}
                    <div className="mt-5 flex flex-wrap items-center justify-center gap-3 border-t border-zinc-200 pt-5">

                        <span className="text-sm font-semibold text-zinc-700">
                            Status:
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-green-100 px-4 py-2 text-sm font-semibold text-green-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-green-500"></span>
                            Ativo
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-yellow-100 px-4 py-2 text-sm font-semibold text-yellow-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-yellow-500"></span>
                            Bloqueado
                        </span>

                        <span className="flex items-center gap-2 rounded-full bg-zinc-200 px-4 py-2 text-sm font-semibold text-zinc-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-zinc-500"></span>
                            Excluído
                        </span>

                    </div>

                </div>

                {/* Botão de novo cadastro */}
                <div className="mb-6 flex justify-end">

                    <Link
                        href="/usuarios/novo"
                        className="rounded-lg bg-orange-500 px-5 py-3 text-center font-semibold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95"
                    >
                        + Novo Cadastro
                    </Link>

                </div>

                {/* Tabela */}
                <div className="overflow-x-auto rounded-2xl border border-zinc-200 bg-white shadow-lg">

                    <table className="w-full min-w-[700px] border-collapse">

                        <thead>
                            <tr className="bg-zinc-900">

                                <th className="px-4 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    ID
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Nome
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    CPF
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    E-mail
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Status
                                </th>

                                <th className="px-4 py-4 text-center text-xs font-bold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Ações
                                </th>

                            </tr>
                        </thead>

                        <tbody>

                            {usuarios.map((usuario) => (
                                <tr
                                    key={usuario.id}
                                    className="border-b border-zinc-100 transition duration-200 hover:bg-orange-50"
                                >

                                    <td className="px-4 py-4 text-center text-sm text-zinc-600 sm:px-6">
                                        {usuario.id}
                                    </td>

                                    <td className="px-4 py-4 text-center text-sm font-semibold text-zinc-900 sm:px-6">
                                        {usuario.nome}
                                    </td>

                                    <td className="px-4 py-4 text-center text-sm text-zinc-600 sm:px-6">
                                        {usuario.cpf}
                                    </td>

                                    <td className="px-4 py-4 text-center text-sm text-zinc-600 sm:px-6">
                                        {usuario.email}
                                    </td>

                                    <td className="px-4 py-4 text-center text-sm font-semibold text-zinc-800 sm:px-6">
                                        {usuario.status}
                                    </td>

                                    {/* Ações alinhadas verticalmente */}
                                    <td className="px-6 py-4 text-center text-sm font-medium text-slate-800">

                                        <div className="flex flex-col items-center gap-2">

                                            <Link
                                                href={`/usuarios/${usuario.id}/editar`}
                                                className="font-medium text-orange-600 transition-colors hover:text-orange-800 hover:underline"
                                            >
                                                Editar
                                            </Link>

                                            <button
                                                onClick={() => handleDeletarUsuario(usuario)}
                                                className="font-medium text-red-600 transition-colors hover:text-red-800"
                                            >
                                                DELETAR
                                            </button>

                                            <button
                                                onClick={() => handleAlterarStatusUsuario(usuario)}
                                                className={`font-medium transition-colors ${
                                                    usuario.status === 'BLOQUEADO'
                                                        ? 'text-orange-600 hover:text-orange-800'
                                                        : 'text-green-600 hover:text-green-800'
                                                }`}
                                            >
                                                {usuario.status}
                                            </button>

                                        </div>

                                    </td>

                                </tr>
                            ))}

                            {usuarios.length === 0 && (
                                <tr>
                                    <td
                                        colSpan={6}
                                        className="px-6 py-12 text-center text-sm font-medium text-slate-800"
                                    >
                                        Nenhum usuário encontrado!
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