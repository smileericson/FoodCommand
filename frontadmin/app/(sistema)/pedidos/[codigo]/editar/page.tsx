import Link from "next/link";
import PedidoForm from "../../components/PedidoForm";
import { useParams } from "next/navigation";

export default function EditarPedido(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);
    return(
        <div>
            <div>
                <div>
                    <div>
                        <span>Editar Pedido {codigo}</span>
                        <h1>Prencha os dados para editar o pedidos</h1>
                    </div>
                    <Link href="/pedidos">Voltar para lista de pedidos</Link>
                </div>
                <div>
                    <PedidoForm />
                </div>
            </div>
        </div>
    );
}