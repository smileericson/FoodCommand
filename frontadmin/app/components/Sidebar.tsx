import Link from "next/link";

export default function Sidebar(){

    return(
        <aside className="flex min-h-screen w-64 flex-col bg-zinc-900 text-white shadow-xl">

            <div className="border-b border-zinc-700 px-6 py-6 text-center text-2xl font-bold text-orange-500">
                FoodCommand
            </div>

            <nav className="flex flex-col gap-2 p-4">

                <Link
                    href="/home"
                    className="rounded-lg px-4 py-3 text-center font-medium text-zinc-300 transition duration-200 hover:bg-orange-500 hover:text-white"
                >
                    Home
                </Link>

                <Link
                    href="/usuarios"
                    className="rounded-lg px-4 py-3 text-center font-medium text-zinc-300 transition duration-200 hover:bg-orange-500 hover:text-white"
                >
                    Usuarios
                </Link>

                <Link
                    href="/mesas"
                    className="rounded-lg px-4 py-3 text-center font-medium text-zinc-300 transition duration-200 hover:bg-orange-500 hover:text-white"
                >
                    Mesas
                </Link>

                <Link
                    href="/pedidos"
                    className="rounded-lg px-4 py-3 text-center font-medium text-zinc-300 transition duration-200 hover:bg-orange-500 hover:text-white"
                >
                    Pedidos
                </Link>

            </nav>

        </aside>
    );
}