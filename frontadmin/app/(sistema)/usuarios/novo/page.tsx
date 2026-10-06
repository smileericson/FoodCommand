import Link from "next/link";
// Importa o componente Link do Next.js.
// É utilizado para fazer navegação entre páginas da aplicação.

import UsuarioForm from "../components/UsuarioForm";
// Importa o componente reutilizável UsuarioForm.
// É nele que ficam os campos e a lógica de cadastro do usuário.

export default function CadastroUsuario() {
    // Declara o componente CadastroUsuario.
    // Essa função representa a página de cadastro de um novo usuário.

    return (
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">
            {/* Container principal da página.
                As classes acima são apenas Tailwind para estilização. */}

            <div className="mx-auto max-w-5xl">
                {/* Define a largura máxima do conteúdo e centraliza a página. */}

                {/* Cabeçalho do painel */}
                <div className="mb-6 flex flex-col items-center justify-between gap-4 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md sm:flex-row">
                    {/* Área visual do cabeçalho.
                        O Tailwind organiza e estiliza o painel. */}

                    <div className="text-center sm:text-left">

                        <span className="text-sm font-semibold uppercase tracking-wider text-orange-500">
                            Novo Usuário
                        </span>
                        {/* Exibe o título indicando que é um cadastro de novo usuário. */}

                        <h1 className="mt-2 text-xl font-bold text-zinc-900 sm:text-2xl">
                            Preencha os dados para registrar um novo usuário
                        </h1>
                        {/* Exibe a orientação para o usuário preencher o cadastro. */}

                    </div>

                    <Link
                        href="/usuarios"
                        className="rounded-lg border border-zinc-300 bg-zinc-100 px-5 py-3 text-center text-sm font-semibold text-zinc-800 transition duration-200 hover:border-orange-500 hover:bg-orange-50 hover:text-orange-600"
                    >
                        Voltar para listagem
                    </Link>
                    {/* Link utilizado para voltar para a página de listagem de usuários.
                        O href define a rota /usuarios. */}

                </div>

                {/* Formulário */}
                <div className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-lg sm:p-8">
                    {/* Container visual que envolve o formulário. */}

                    <div className="mb-6 border-b border-zinc-200 pb-4 text-center">

                        <h2 className="text-lg font-bold text-zinc-900">
                            Dados do Usuário
                        </h2>
                        {/* Título da seção do formulário. */}

                        <p className="mt-1 text-sm text-zinc-500">
                            Preencha as informações abaixo para realizar o cadastro
                        </p>
                        {/* Texto de orientação para o preenchimento. */}

                    </div>

                    <UsuarioForm />
                    {/* Renderiza o componente UsuarioForm.
                        Como nenhum usuarioExistente é passado,
                        o UsuarioForm entende que está sendo utilizado
                        para cadastrar um novo usuário. */}

                </div>

            </div>

        </div>
    );
}