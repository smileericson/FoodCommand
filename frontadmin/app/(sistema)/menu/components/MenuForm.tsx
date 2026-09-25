import Link from "next/link";

export default function MenuForm(){
    return(
        <form>
            <div>
                <label>Nome: </label>
                <input/>
            </div>
            <div>
                <label>Descrição: </label>
                <input/>
            </div>
            <div>
                <label>Preço: </label>
                <input/>
            </div>
            <div>
                <Link href="/menu">
                Cancelar</Link>
                <button>
                    Salvar
                </button>
            </div>
        </form>
    );
}