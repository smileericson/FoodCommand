import Link from "next/link"

export default function MesaForm() {
    return (
        <form className="space-y-6">

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    Número da Mesa
                </label>

                <input
                    name="numero"
                    type="number"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div className="flex flex-col-reverse items-center justify-center gap-3 border-t border-zinc-200 pt-6 sm:flex-row">

                <Link
                    href="/mesas"
                    className="w-full rounded-lg border border-zinc-300 bg-zinc-100 px-6 py-3 text-center font-semibold text-zinc-700 transition duration-200 hover:border-red-400 hover:bg-red-50 hover:text-red-600 sm:w-auto"
                >
                    Cancelar
                </Link>

                <button
                    className="w-full rounded-lg bg-orange-500 px-6 py-3 font-semibold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95 sm:w-auto"
                >
                    Salvar
                </button>

            </div>

        </form>
    );
}