import Link from "next/link";
import MenuForm from "../components/MenuForm";

export default function CadastroMenu() {
    return (
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">

            <div className="mx-auto max-w-5xl">

                {/* Cabeçalho */}
                <div className="mb-6 flex flex-col items-center justify-between gap-4 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md sm:flex-row">

                    <div className="text-center sm:text-left">

                        <span className="text-sm font-bold uppercase tracking-wider text-orange-500">
                            Novo item do cardápio
                        </span>

                        <h1 className="mt-2 text-xl font-extrabold text-zinc-900 sm:text-2xl">
                            Preencha os dados para cadastrar o novo item
                        </h1>

                    </div>

                    <Link
                        href="/menu"
                        className="rounded-lg border border-zinc-300 bg-zinc-100 px-5 py-3 text-center text-sm font-bold text-zinc-800 transition duration-200 hover:border-orange-500 hover:bg-orange-50 hover:text-orange-600"
                    >
                        Voltar para o cardápio
                    </Link>

                </div>

                {/* Formulário */}
                <div className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-lg sm:p-8">

                    <div className="mb-6 border-b-2 border-zinc-200 pb-4 text-center">

                        <h2 className="text-lg font-extrabold text-zinc-900">
                            Dados do Cardápio
                        </h2>

                        <p className="mt-2 text-sm font-medium text-zinc-600">
                            Preencha as informações abaixo para realizar o cadastro do novo item do cardápio
                        </p>

                    </div>

                    <MenuForm />

                </div>

            </div>

        </div>
    );
}