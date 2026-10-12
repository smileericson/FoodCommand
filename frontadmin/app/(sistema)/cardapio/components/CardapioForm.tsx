'use client'



import { CardapioFormProps, Cardapios } from "@/app/types/cardapio";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function CardapioForm({cardapioExistente}:CardapioFormProps) {
     const router = useRouter();

    const [ cardapio,setCardapio ] = useState<Cardapios>(
        cardapioExistente ||
        new Cardapios(null,"","","","ATIVO")
    );

const handlerChange = (campo: 'nome' | 'descricao' | 'preco' | 'disponivel',valor: string | number) => {
    setCardapio(valorAnterior =>
        new Cardapios(
            valorAnterior.id,
            campo === 'nome' ? String(valor) : valorAnterior.nome,
            campo === 'descricao' ? String(valor) : valorAnterior.descricao,
            campo === 'preco' ? String(valor) : valorAnterior.preco,
            valorAnterior.statusCardapio
        )

    )

}

    const handlerSalvar = async (formData : FormData) =>{
    if(cardapioExistente){
        var dadosRetorno = await  
        axios.put<Number>('http://localhost:8080/cardapio/'+cardapio.id,cardapio);

        if(dadosRetorno.status==200){
            alert("Usuário foi salvo com sucesso!");
        }else{
            alert(dadosRetorno.data);

            return;
        }


    }else{
        var dadosRetorno = await  axios.post<Number>('http://localhost:8080/cardapio',cardapio)

        if(dadosRetorno.status==200){
            alert("Usuário foi salvo com sucesso!");
        }else{
            alert(dadosRetorno.data);

            return;
        }
    
    }

    router.push("/cardapio");

    }

    return (
        <form action={handlerSalvar} className="space-y-6">

            {/* Nome */}
            <div>
                <label className="mb-2 block text-center text-sm font-bold text-zinc-800">
                    Nome:
                </label>

                <input
                    name="nome" 
                    value={cardapio.nome}
                    required
                    onChange={(e)=> handlerChange('nome',e.target.value)}
                    placeholder="PF-xxx"  
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center font-medium text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            {/* Descrição */}
            <div>
                <label className="mb-2 block text-center text-sm font-bold text-zinc-800">
                    Descrição:
                </label>

                <input
                    name="descricao" 
                    value={cardapio.descricao}
                    required
                    onChange={(e)=> handlerChange('descricao',e.target.value)}
                    placeholder="...."  
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center font-medium text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            {/* Preço */}
            <div>
                <label className="mb-2 block text-center text-sm font-bold text-zinc-800">
                    Preço:
                </label>

                <input
                    name="preco" 
                    value={cardapio.preco || "" }
                    required
                    onChange={(e)=> handlerChange('preco',e.target.value)}
                    placeholder="R$1000.00" 
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center font-medium text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            {/* Botões */}
            <div className="flex flex-col-reverse items-center justify-center gap-4 border-t-2 border-zinc-200 pt-6 sm:flex-row">

                <Link
                    href="/cardapio"
                    className="w-full rounded-lg border border-zinc-300 bg-zinc-100 px-6 py-3 text-center font-bold text-zinc-700 transition duration-200 hover:border-red-300 hover:bg-red-50 hover:text-red-600 sm:w-auto"
                >
                    Cancelar
                </Link>

                <button
                    className="w-full rounded-lg bg-orange-500 px-8 py-3 font-bold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95 sm:w-auto"
                >
                    Salvar
                </button>

            </div>

        </form>
    );
}