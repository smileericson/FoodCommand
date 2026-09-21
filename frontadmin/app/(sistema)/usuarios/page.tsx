
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

    return (
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">

            <div className="mx-auto max-w-7xl">

                <h1 className="mb-8 text-center text-2xl font-bold text-zinc-900 sm:text-3xl">
                    Gestão de usuários
                </h1>

                <div className="mb-6 flex justify-end">
                    <Link
                        href="/usuarios/novo"
                        className="rounded-lg bg-orange-500 px-5 py-3 text-center font-semibold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95"
                    >
                        + Novo Cadastro
                    </Link>
                </div>

                <div>
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
                                    <th>
                                        Acões
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
                                        <td className="px-4 py-4 text-center text-sm font-semibold text-zinc-800 sm:px-6">
                                            <Link href={`/usuarios/${usuario.id}/editar`}>Editar</Link>
                                        </td>

                                    </tr>
                                ))}

                                {usuarios.length === 0 &&
                                (
                                    <tr>
                                        <td
                                            colSpan={5}
                                            className="px-6 py-12 text-center text-sm font-medium text-slate-800"
                                        >
                                            Nenhum usuario encontrado!
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