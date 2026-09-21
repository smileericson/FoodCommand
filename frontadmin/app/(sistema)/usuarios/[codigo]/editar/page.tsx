"use client"

import { useParams } from "next/navigation";
import UsuarioForm from "../../components/UsuarioForm";
import Link from "next/link";


export default function EditarUsuario(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);
    return(
             <div>
                    <div>
                        <div>
                            <h1>
                            <span>
                                Editar Usuário {codigo}
                            </span>
                            </h1>
                            <p>Preencha os dados para editar o usuário</p>
                        </div>
                        <Link href="/usuarios">Voltar para listagem</Link>
                    </div>
                    <div>
                        <UsuarioForm />
                    </div>
                </div>

    );
}