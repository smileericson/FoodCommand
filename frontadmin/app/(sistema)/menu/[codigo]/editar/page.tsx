import Link from "next/link";
import MenuForm from "../../components/MenuForm";


export default function EditarCardapio() {
    return (
        <div>
            <div>
                <div>
                    <div>
                        <span>Editar item cardapio</span>
                        <h1> Preencha os dados para editar o item</h1>
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