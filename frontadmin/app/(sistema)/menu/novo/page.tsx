import Link from "next/link";
import MenuForm from "../components/MenuForm";

export default function CadastroCardapio() {
    return (
        <div>
            <div>
                <div>
                    <div>
                        <span>Novo item cardapio</span>
                        <h1> Preencha os dados para cadastrar o novo item</h1>
                    </div>
                    <Link href="/menu"></Link>
                </div>
                <div>
                    <div>
                        <h2>Dados do Cardapío</h2>
                        <p>Preencha as informações abaixo para realizar o cadastro do novo item do cardapio</p>
                    </div>
                    <MenuForm />
                </div>
            </div>
        </div>

    );
}