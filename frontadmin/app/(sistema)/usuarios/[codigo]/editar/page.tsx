"use client"

import { useParams, useRouter } from "next/navigation";
import UsuarioForm from "../../components/UsuarioForm";
import Link from "next/link";
import { useEffect, useState } from "react";
import { Usuario } from "@/app/types/usuario";
import consultaUsuarioPorId from "@/app/services/usuarioService";


export default function EditarUsuario() {


    const parametro = useParams();
    const codigo = Number(parametro.codigo);

    const [usuario, setUsuario] = useState<Usuario | null>(null)

    const router = useRouter();
    useEffect(() => {

        buscarDados();

    }, []);

    const buscarDados = async () => {

        const valorUsuarioBack = await consultaUsuarioPorId(codigo);

        if (valorUsuarioBack) {

            setUsuario(valorUsuarioBack);

        } else {

            router.push("/usuarios")
        }
    }


    if (!usuario) return (<div className="p-8">Carregando Dados...</div>)

    return (

        <div className="min-h-full bg-gray-100 p-4 sm:p-6">

            <div className="mx-auto max-w-5xl">


                {/* 
                    =================================================
                    CABEÇALHO
                    =================================================

                    Este bloco é apenas a estrutura visual do cabeçalho.

                    O Tailwind define:
                    - espaçamento;
                    - fundo branco;
                    - bordas;
                    - sombra;
                    - cantos arredondados;
                    - alinhamento;
                    - comportamento responsivo.

                    A lógica da página não está aqui.
                */}

                {/* Cabeçalho */}
                <div className="mb-6 flex flex-col items-center justify-between gap-4 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md sm:flex-row">


                    {/* // Área que contém o título e a identificação */}
                    {/* // da página de edição. */}
                    <div className="text-center sm:text-left">


                        {/* // Texto pequeno utilizado como identificação */}
                        {/* // da operação que está sendo realizada. */}
                        {/* // {codigo} é JavaScript dentro do JSX. */}
                        {/* // Ele mostra o ID recebido pela URL. */}
                        <span className="text-sm font-semibold uppercase tracking-wider text-orange-500">

                            Editar Usuário {codigo}

                        </span>


                        {/* // Título principal da página. */}
                        <h1 className="mt-2 text-xl font-bold text-zinc-900 sm:text-2xl">

                            Preencha os dados para editar o usuário

                        </h1>

                    </div>


                    {/* // Link do Next.js para voltar à listagem. */}
                    {/* // href define para qual rota o usuário será enviado. */}
                    {/* // As classes className são somente Tailwind, */}
                    {/* // responsáveis pela aparência do botão. */}
                    <Link
                        href="/usuarios"
                        className="rounded-lg border border-zinc-300 bg-zinc-100 px-5 py-3 text-center text-sm font-semibold text-zinc-800 transition duration-200 hover:border-orange-500 hover:bg-orange-50 hover:text-orange-600"
                    >
                        Voltar para listagem
                    </Link>

                </div>


                {/* 
                    =================================================
                    FORMULÁRIO
                    =================================================

                    Este bloco também utiliza Tailwind somente
                    para criar o visual de um cartão branco,
                    com borda, espaçamento, sombra e cantos arredondados.
                */}

                {/* Formulário */}
                <div className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-lg sm:p-8">


                    {/* // Componente UsuarioForm. */}
                    {/* // Aqui acontece uma parte importante da comunicação */}
                    {/* // entre os componentes. */}
                    {/* // usuarioExistente recebe o usuário que acabamos */}
                    {/* // de buscar no backend. */}
                    {/* // O UsuarioForm poderá utilizar esses dados */}
                    {/* // para preencher os campos do formulário. */}
                    <UsuarioForm usuarioExistente={usuario} />

                </div>

            </div>

        </div>
    );
}