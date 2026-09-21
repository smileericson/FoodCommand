import Link from "next/link";
import UsuarioForm from "../components/UsuarioForm";

export default function CadastroUsuario(){
    return(

        <div>
            <div>
                <div>
                    <span>
                        Novo Usuário
                    </span>
                    <h1>Preencha os dados para registrar um novo usuário</h1>
                </div>
                <Link href="/usuarios">Voltar para listagem</Link>
            </div>
            <div>
                <UsuarioForm />
            </div>
        </div>

    );
}