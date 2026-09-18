export default function Footer(){

    const anoAtual = new Date().getFullYear();

    return(
        <footer className="border-t border-zinc-700 bg-zinc-900">
            <div className="flex items-center justify-center px-6 py-5">
                <div className="flex flex-wrap items-center justify-center gap-2 text-center text-sm text-zinc-400">
                    <p>&copy;{anoAtual}</p>

                    <span className="font-semibold text-orange-500">
                        FoodCommand
                    </span>

                    <span>
                        Todos os direitos reservados.
                    </span>
                </div>
            </div>
        </footer>
    );
}