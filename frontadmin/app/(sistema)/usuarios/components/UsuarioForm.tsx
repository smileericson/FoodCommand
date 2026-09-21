import Link from "next/link";

export default function UsuarioForm(){
    return(
        <form>
            <div>
                <label>Nome completo:</label>
                <input name="nome"/>
            </div>
            <div>
                <label>CPF :</label>
                <input name="CPF"/>
            </div>
            <div>
                <label>E-mail :</label>
                <input name="email"/>
            </div>
            <div>
                <label>Senha :</label>
                <input name="senha"/>
            </div>
            <div>
                <div>
                    <Link href="/usuarios">Cancelar</Link>
                    <button>Salvar</button>
                </div>
            </div>
            
        </form>


    );

}