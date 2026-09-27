export default function Home() {
    return (
        <div className="flex h-full items-center justify-center bg-gray-100 px-4">
            <h1 className="text-center text-2xl font-bold text-zinc-900 md:text-4xl">
                Bem-vindo ao sistema de gestão de restaurantes!
                <br/>
                <span className="ml-3 inline text-orange-500">
                    FoodCommand
                </span>
            </h1>
        </div>
    );
}