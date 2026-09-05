'use client'

import { useRouter } from "next/navigation";

export default function Login() {
    const router = useRouter();

    const handleLogin = async (formData: FormData) => {

        

        router.push("/home")

    }

    return (
        <div className="flex min-h-screen items-center justify-center bg-gray-100 px-4">
            <div className="w-full max-w-md rounded-2xl border border-zinc-200 bg-zinc-900 p-8 shadow-2xl">

                <div className="mb-8 text-center">
                    <h1 className="text-3xl font-bold text-white">
                        Entrar no sistema
                    </h1>
                    <p className="text-sm text-slate-400">Insira suas credenciais para acessar o painel</p>
                </div>

                
                <form action={handleLogin} className="space-y-4">
                    
                    <div className="mb-5">

                        <label className="mb-2 block text-sm font-medium text-zinc-300">
                            E-mail
                        </label>

                        <input
                            name="email"
                            className="w-full rounded-lg border border-zinc-700 bg-zinc-950 px-4 py-3 text-white outline-none transition placeholder:text-zinc-500 focus:border-orange-500 focus:ring-2 focus:ring-orange-500/20"
                        >
                        </input>

                    </div>

                    <div className="mb-6">

                        <label className="mb-2 block text-sm font-medium text-zinc-300">
                            Senha
                        </label>

                        <input
                            name="senha"
                            className="w-full rounded-lg border border-zinc-700 bg-zinc-950 px-4 py-3 text-white outline-none transition placeholder:text-zinc-500 focus:border-orange-500 focus:ring-2 focus:ring-orange-500/20"
                        >
                        </input>

                    </div>

                    <button
                        type="submit"
                        className="w-full rounded-lg bg-orange-500 px-4 py-3 font-semibold text-white transition duration-200 hover:bg-orange-600 hover:shadow-lg hover:shadow-orange-500/20 active:scale-[0.98]"
                    >
                        Entrar
                    </button>
                </form>

            </div>
        </div>
    );
}