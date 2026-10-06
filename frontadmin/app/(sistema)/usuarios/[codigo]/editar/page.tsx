"use client"
// Informa ao Next.js que este componente será executado no lado do cliente.
// É necessário porque utilizamos recursos do React como useState e useEffect,
// além de hooks de navegação como useParams e useRouter.


// Importa os hooks do Next.js responsáveis por trabalhar com:
// useParams → pegar parâmetros presentes na URL.
// useRouter → realizar navegação através do código.
import { useParams, useRouter } from "next/navigation";

// Importa o formulário reutilizável de usuário.
// Esse componente será responsável pelos campos e pela operação de edição.
import UsuarioForm from "../../components/UsuarioForm";

// Link do Next.js utilizado para navegação entre páginas.
import Link from "next/link";

// Hooks do React:
// useEffect → executar uma ação quando o componente é carregado.
// useState → armazenar dados que podem mudar na tela.
import { useEffect, useState } from "react";

// Importa o tipo/classe Usuario utilizado para tipar os dados.
import { Usuario } from "@/app/types/usuario";

// Axios será utilizado para fazer a requisição HTTP ao backend.
import axios from "axios";



// Componente responsável pela página de edição de usuário.
export default function EditarUsuario() {


    // useParams() pega os parâmetros presentes na URL.
    //
    // Exemplo:
    // /usuarios/editar/5
    //
    // Nesse caso, "codigo" pode receber o valor 5.
    const parametro = useParams();


    // O parâmetro recebido pela URL pode ser tratado como string.
    // Number() converte esse valor para número.
    //
    // Exemplo:
    // "5" → 5
    const codigo = Number(parametro.codigo);


    // Estado utilizado para armazenar o usuário que será editado.
    // Usuario → quando existe um usuário carregado.
    // null → enquanto ainda não temos os dados.
    // Inicialmente o usuário é null porque os dados ainda serão
    // buscados no backend.(usuario será carregado no backend)
    const [usuario, setUsuario] = useState<Usuario | null>(null)


    // Cria uma instância do router.
    // Será utilizada para navegar para outra página através do código.
    const router = useRouter();


    // useEffect executa uma função quando o componente é carregado.
    // Nesse caso, quando a página de edição abre,
    // precisamos buscar os dados do usuário no backend.
    useEffect(() => {

        buscarDados();

    }, []);


    // Função responsável por buscar os dados do usuário no backend.
    // async/await permite trabalhar com a requisição de forma assíncrona.
    const buscarDados = async () => {


        // Axios realiza uma requisição GET para o backend.
        // O código do usuário é concatenado na URL.
        // Exemplo:
        // codigo = 5
        // Resultado:
        // http://localhost:8080/usuarios/5
        // <Usuario> informa ao TypeScript o tipo esperado
        // para os dados retornados pelo backend.
        const valorUsuarioBack = await axios.get<Usuario>(
            'http://localhost:8080/usuarios/' + codigo
        );


        // Verifica o status HTTP retornado pelo backend.
        // 200 significa que a requisição foi realizada com sucesso.
        if (valorUsuarioBack.status == 200) {

            // Pega os dados retornados pelo backend
            // e coloca no estado "usuario".
            // A partir desse momento, o formulário recebe
            // os dados do usuário que será editado.
            setUsuario(valorUsuarioBack.data);

        } else {

            // Caso a resposta não seja 200,
            // redireciona o usuário para a listagem.
            router.push("/usuarios")
        }
    }


    // Enquanto usuario for null, significa que os dados ainda
    // não foram carregados.
    // Nesse momento mostramos uma mensagem na tela.
    if (!usuario) return (<div className="p-8">Carregando Dados...</div>)


    // Depois que o usuário foi carregado,
    // a página principal é renderizada.
    return (
        // =====================================================
        // TAILWIND CSS
        // =====================================================
        // Este bloco utiliza classes Tailwind para criar:
        // - fundo cinza;
        // - espaçamento interno;
        // - comportamento responsivo.
        //
        // O Tailwind é responsável apenas pela aparência/layout.
        // Ele não interfere na lógica de busca ou edição do usuário.
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">


            {/* // Container principal da página. */}
            {/* // As classes Tailwind definem: */}
            {/* // - largura máxima; */}
            {/* // - centralização; */}
            {/* // - adaptação para diferentes tamanhos de tela. */}
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