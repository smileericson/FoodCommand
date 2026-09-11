"use client"

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
        <div className="min-h-screen bg-gray-100 p-6">
            <div className="mx-auto max-w-7xl">
                <h1 className="mb-6 text-center text-3xl font-bold text-zinc-900">
                    Gestão de usuários
                </h1>

                <link href="usuarios/novo" />

                <div>
                    <div className="overflow-hidden rounded-xl border border-zinc-200 bg-white shadow-md">
                        <table className="w-full border-collapse">
                            <thead>
                                <tr className="bg-zinc-900">
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        ID
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        Nome
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        CPF
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        E-mail
                                    </th>
                                    <th className="px-6 py-4 text-center text-sm font-semibold text-orange-500">
                                        Status
                                    </th>
                                </tr>
                            </thead>

                            <tbody>

                                {usuarios.map((usuario) => (
                                    <tr
                                        key={usuario.id}
                                        className="border-b border-zinc-200 transition hover:bg-orange-50"
                                    >
                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {usuario.id}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm font-medium text-zinc-900">
                                            {usuario.nome}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {usuario.cpf}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm text-zinc-800">
                                            {usuario.email}
                                        </td>

                                        <td className="px-6 py-4 text-center text-sm font-medium text-zinc-800">
                                            {usuario.status}
                                        </td>
                                    </tr>
                                ))}

                                { usuarios.length ===0 &&
                                (
                                    <tr>
                                        <td
                                            colSpan={5}
                                            className="px-6 py-12 text-center text-slate-800"
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