export default function Header() {

    return (
        <header className="border-b border-zinc-200 bg-zinc-900 shadow-md">
            <div className="flex items-center justify-between px-6 py-4">

                <div className="flex items-center gap-3">

                    <div className="flex h-10 w-10 items-center justify-center rounded-full bg-orange-500">

                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            className="h-6 w-6 text-white"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                            strokeWidth="2"
                        >
                            <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2" />
                            <circle cx="12" cy="7" r="4" />
                        </svg>

                    </div>

                    <span className="text-center text-sm font-semibold text-white md:text-base">
                        Usuario Ericson Smile
                    </span>

                </div>

                <button
                    className="rounded-lg bg-orange-500 px-5 py-2 font-semibold text-white transition duration-200 hover:bg-orange-600 hover:shadow-lg hover:shadow-orange-500/20 active:scale-95"
                >
                    Sair
                </button>

            </div>
        </header>
    );
}