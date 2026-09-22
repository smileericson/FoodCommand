import Link from "next/link";
import MesaForm from "../../components/MesaForm";
import { useParams } from "next/navigation";


export default function CadastroMesa() {

    const parametro = useParams();
    const codigo = Number(parametro.codigo)

    return (
        <div className="min-h-full bg-gray-100 p-4 sm:p-6">

            <div className="mx-auto max-w-5xl">

                {/* Cabeçalho */}
                <div className="mb-6 flex flex-col items-center justify-between gap-4 rounded-2xl border border-zinc-200 bg-white p-6 shadow-md sm:flex-row">

                    <div className="text-center sm:text-left">

                        <span className="text-sm font-semibold uppercase tracking-wider text-orange-500">
                            Editar Mesa {codigo}
                        </span>

                        <h1 className="mt-2 text-xl font-bold text-zinc-900 sm:text-2xl">
                            Preencha os dados para Editar a mesa
                        </h1>

                    </div>

                    <Link
                        href="/mesas"
                        className="rounded-lg border border-zinc-300 bg-zinc-100 px-5 py-3 text-center text-sm font-semibold text-zinc-800 transition duration-200 hover:border-orange-500 hover:bg-orange-50 hover:text-orange-600"
                    >
                        Voltar para listagem
                    </Link>

                </div>

                {/* Informações do formulário */}
                <div className="rounded-2xl border border-zinc-200 bg-white p-6 shadow-lg sm:p-8">

                    <div className="mb-6 border-b border-zinc-200 pb-4 text-center">

                        <h2 className="text-lg font-bold text-zinc-900">
                            Dados da Mesa
                        </h2>

                        <p className="mt-1 text-sm text-zinc-500">
                            Preencha as informações abaixo para realizar o cadastro da mesa
                        </p>

                    </div>

                    {/* Formulário */}
                    <MesaForm />

                </div>

            </div>

        </div>
    );
}