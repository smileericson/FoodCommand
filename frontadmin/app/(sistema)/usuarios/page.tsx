"use client"

// Importa o componente Link do Next.js para criar navegação entre páginas sem recarregar a aplicação.
import Link from "next/link";

// Importa o tipo/classe Usuario utilizado para representar os usuários.
import { Usuario } from "@/app/types/usuario";

// Importa o Axios para realizar requisições HTTP ao backend.
import axios from "axios"

// Importa os Hooks useEffect e useState do React.
import { useEffect, useState } from "react";


// Componente principal da página de usuários.
export default function Usuarios() {

    // Cria o estado usuarios, que armazenará a lista de usuários.
    // O estado começa com um array vazio.
    const [usuarios, setUsuarios] = useState<Usuario[]>([]);


    // Executa o código quando o componente é carregado.
    // O [] significa que será executado somente uma vez na montagem do componente.
    useEffect(() => {
        carregarDados();
    }, []);


    // Função responsável por buscar os usuários no backend.
    const carregarDados = async () => {

        try {

            // Faz uma requisição GET para buscar os usuários.
            // <Usuario[]> indica que esperamos receber uma lista de objetos Usuario.
            const dados = await axios.get<Usuario[]>("http://localhost:8080/usuarios");

            // Atualiza o estado com os dados recebidos do backend.
            setUsuarios(dados.data);

        } catch (error) {

            // Caso aconteça algum erro na requisição, mostra uma mensagem.
            alert("Erro ao carregar dados!")
        }

    }


    // Função responsável por excluir um usuário.
    // Recebe como parâmetro o usuário que será excluído.
    const handleDeletarUsuario = async (usuario: Usuario) => {

        // Envia uma requisição DELETE para o backend.
        // O ID do usuário é utilizado para identificar qual usuário será excluído.
        var dadosRetorno = await
            axios.delete('http://localhost:8080/usuarios/' + usuario.id + '/excluir');


        // Verifica se o backend retornou o status HTTP 200.
        if (dadosRetorno.status == 200) {

            // Informa que a exclusão foi realizada com sucesso.
            alert("Excluido com sucesso!");

        } else {

            // Caso o retorno não seja 200, mostra a mensagem recebida do backend.
            alert(dadosRetorno.data);

            // Interrompe a execução da função.
            return;
        }

        // Depois da exclusão, busca novamente os dados para atualizar a tabela.
        carregarDados();
    }


    // Função responsável por alterar o status do usuário.
    const handleAlterarStatusUsuario = async (usuario: Usuario) => {

        // Cria uma variável para armazenar o novo status.
        var novoStatus = {};


        // Verifica o status atual do usuário.
        if (usuario.status === "ATIVO") {

            // Se o usuário estiver ATIVO, o novo status será BLOQUEADO.
            novoStatus = { status: "BLOQUEADO" }

        } else {

            // Caso contrário, o novo status será ATIVO.
            novoStatus = { status: "ATIVO" }
        }


        // Envia uma requisição PATCH para alterar o status do usuário.
        // O ID identifica o usuário e novoStatus contém o novo status.
        var dadosRetorno = await
            axios.patch('http://localhost:8080/usuarios/' + usuario.id + '/status', novoStatus);


        // Verifica se o backend retornou o status HTTP 200.
        if (dadosRetorno.status == 200) {

            // Informa que o status foi atualizado.
            alert("Atulizado status com sucesso!");

        } else {

            // Caso contrário, mostra a mensagem retornada pelo backend.
            alert(dadosRetorno.data);

            // Interrompe a execução da função.
            return;
        }


        // Depois da alteração, busca novamente os usuários para atualizar a tabela.
        carregarDados();

    }


    // Retorna a estrutura visual da página.
    return (
        // Container principal da página.
        // As classes Tailwind são responsáveis somente pela aparência e espaçamento.
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">

            {/* // Container que limita a largura máxima da página. */}
            <div className="mx-auto max-w-7xl">

                {/* Cabeçalho da página */}
                <div className="mb-6 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md">

                    {/* // Título principal da página. */}
                    <h1 className="text-center text-2xl font-extrabold text-zinc-900 sm:text-3xl">
                        Gestão de usuários
                    </h1>

                    {/* // Texto auxiliar abaixo do título. */}
                    <p className="mt-1 text-center text-sm font-medium text-zinc-600">
                        Preencha as informações do cadastro do GARÇOM
                    </p>


                    {/* Legenda de status */}
                    {/* // Apresenta visualmente os possíveis status dos usuários. */}
                    <div className="mt-5 flex flex-wrap items-center justify-center gap-3 border-t-2 border-zinc-200 pt-5">

                        {/* // Texto que identifica a legenda. */}
                        <span className="text-sm font-bold text-zinc-800">
                            Status:
                        </span>

                        {/* // Indicação visual do status ATIVO. */}
                        <span className="flex items-center gap-2 rounded-full bg-green-100 px-4 py-2 text-sm font-bold text-green-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-green-500"></span>
                            Ativo
                        </span>

                        {/* // Indicação visual do status BLOQUEADO. */}
                        <span className="flex items-center gap-2 rounded-full bg-yellow-100 px-4 py-2 text-sm font-bold text-yellow-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-yellow-500"></span>
                            Bloqueado
                        </span>

                        {/* // Indicação visual do status EXCLUÍDO. */}
                        <span className="flex items-center gap-2 rounded-full bg-zinc-200 px-4 py-2 text-sm font-bold text-zinc-700">
                            <span className="h-2.5 w-2.5 rounded-full bg-zinc-500"></span>
                            Excluído
                        </span>

                    </div>

                </div>


                {/* Botão de novo cadastro */}
                {/* // Link utilizado para acessar a página de criação de um novo usuário. */}
                <div className="mb-6 flex justify-end">

                    <Link
                        href="/usuarios/novo"
                        className="rounded-lg bg-orange-500 px-5 py-3 text-center font-bold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95"
                    >
                        + Novo Cadastro
                    </Link>

                </div>


                {/* Tabela */}
                {/* // Área que contém a tabela de usuários. */}
                <div className="overflow-x-auto rounded-2xl border border-zinc-200 bg-white shadow-lg">

                    {/* // Define a tabela que apresentará os usuários. */}
                    <table className="w-full min-w-[700px] border-collapse">

                        {/* // Cabeçalho da tabela. */}
                        <thead>

                            {/* // Linha que contém os títulos das colunas. */}
                            <tr className="bg-zinc-900">

                                {/* // Coluna que apresenta o ID do usuário. */}
                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    ID
                                </th>

                                {/* // Coluna que apresenta o nome do usuário. */}
                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Nome
                                </th>

                                {/* // Coluna que apresenta o CPF do usuário. */}
                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    CPF
                                </th>

                                {/* // Coluna que apresenta o e-mail do usuário. */}
                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    E-mail
                                </th>

                                {/* // Coluna que apresenta o status do usuário. */}
                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Status
                                </th>

                                {/* // Coluna que apresenta as ações disponíveis para o usuário. */}
                                <th className="px-4 py-4 text-center text-xs font-extrabold uppercase tracking-wider text-orange-500 sm:px-6 sm:text-sm">
                                    Ações
                                </th>

                            </tr>
                        </thead>


                        {/* // Corpo da tabela. */}
                        <tbody>


                            {/* // Percorre a lista de usuários e cria uma linha para cada usuário. */}
                            {usuarios.map((usuario) => (

                                <tr

                                    // Define uma chave única para cada linha utilizando o ID do usuário.
                                    key={usuario.id}

                                    className="border-b-2 border-zinc-100 transition duration-200 hover:bg-orange-50"
                                >

                                    {/* // Exibe o ID do usuário. */}
                                    <td className="px-4 py-5 text-center text-sm font-semibold text-zinc-800 sm:px-6">
                                        {usuario.id}
                                    </td>

                                    {/* // Exibe o nome do usuário. */}
                                    <td className="px-4 py-5 text-center text-sm font-bold text-zinc-900 sm:px-6">
                                        {usuario.nome}
                                    </td>

                                    {/* // Exibe o CPF do usuário. */}
                                    <td className="px-4 py-5 text-center text-sm font-semibold text-zinc-800 sm:px-6">
                                        {usuario.cpf}
                                    </td>

                                    {/* // Exibe o e-mail do usuário. */}
                                    <td className="px-4 py-5 text-center text-sm font-semibold text-zinc-800 sm:px-6">
                                        {usuario.email}
                                    </td>

                                    {/* // Exibe o status atual do usuário. */}
                                    <td className="px-4 py-5 text-center text-sm font-bold text-zinc-900 sm:px-6">
                                        {usuario.status}
                                    </td>


                                    {/* Ações alinhadas verticalmente */}
                                    {/* // Área que contém as ações de cada usuário. */}
                                    <td className="px-6 py-5 text-center text-sm font-bold text-zinc-800">

                                        {/* // Organiza os botões verticalmente. */}
                                        <div className="flex flex-col items-center justify-center gap-3">


                                            {/* // Link responsável por levar para a edição do usuário. */}
                                            <Link
                                                href={`/usuarios/${usuario.id}/editar`}
                                                className="font-bold text-orange-600 transition-colors hover:text-orange-800 hover:underline"
                                            >
                                                Editar
                                            </Link>


                                            {/* // Botão responsável por excluir o usuário. */}
                                            <button
                                                onClick={() => handleDeletarUsuario(usuario)}
                                                className="font-bold text-red-600 transition-colors hover:text-red-800"
                                            >
                                                DELETAR
                                            </button>


                                            {/* // Botão responsável por alterar o status do usuário. */}
                                            <button
                                                onClick={() => handleAlterarStatusUsuario(usuario)}
                                                className={`font-bold transition-colors ${
                                                    // Se o status for BLOQUEADO, utiliza a aparência amarela.
                                                    usuario.status === 'BLOQUEADO'
                                                        ? 'text-yellow-600 hover:text-yellow-800'

                                                        // Se o status for EXCLUIDO, utiliza a aparência cinza.
                                                        :usuario.status === 'EXCLUIDO'
                                                        ? 'text-gray-600 hover:text-gray-800'

                                                        // Para os demais casos, utiliza a aparência verde.
                                                        : 'text-green-600 hover:text-green-800'
                                                }`}
                                            >
                                                {usuario.status}
                                            </button>

                                        </div>

                                    </td>

                                </tr>
                            ))}


                            {/* // Verifica se a lista de usuários está vazia. */}
                            {/* // Se estiver, mostra uma mensagem no lugar dos registros. */}
                            {usuarios.length === 0 && (
                                <tr>

                                    {/* // colSpan={6} faz a célula ocupar as 6 colunas da tabela. */}
                                    <td
                                        colSpan={6}
                                        className="px-6 py-12 text-center text-base font-bold text-zinc-700"
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