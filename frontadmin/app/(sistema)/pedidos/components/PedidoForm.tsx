'use client'

import { Pedido, PedidoFormProps } from "@/app/types/pedido";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";

import { useState } from "react";

export default function PedidoForm({pedidoExistente}:PedidoFormProps) {
    const router = useRouter();

    const [pedido,setPedido] = useState<Pedido>(
        pedidoExistente || new Pedido(null,0,0,0,"")
    );
    const handlerChange = (campo: 'valorSubtotal'|'taxaServico'|'valorTotal',valor:string)=>{
            setPedido(valorAnterior=>
                new Pedido(
                    valorAnterior.id,
                    campo === 'valorSubtotal' ? Number(valor): valorAnterior.valorSubtotal,
                    campo === 'taxaServico' ? Number(valor): valorAnterior.taxaServico,
                    campo === 'valorTotal' ? Number(valor): valorAnterior.valorTotal,
                    valorAnterior.statusPedido
                )
            )
        }
        const handlerSalvar = async (formData : FormData) =>{

    if(pedidoExistente){
        var dadosRetorno = await  
        axios.put<number>('http://localhost:8080/pedidos/'+pedido.id,pedido);

        if(dadosRetorno.status==200){
            alert("Pedido foi salvo com sucesso!");
        }else{
            alert(dadosRetorno.data);

            return;
        }


    }else{
        var dadosRetorno = await  axios.post<number>('http://localhost:8080/pedidos',pedido)

        if(dadosRetorno.status==200){
            alert("Pedido foi salvo com sucesso!");
        }else{
            alert(dadosRetorno.data);

            return;
        }
    
    }

    router.push("/pedidos");

    }
        
    return (
        <form action={handlerSalvar} className="space-y-6">

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    Valor Subtotal
                </label>

                <input
                    name="valorSubtotal"
                    value={pedido.valorSubtotal}
                    required
                    onChange={(e)=>handlerChange('valorSubtotal',e.target.value)}
                    placeholder="1000.00"
                    type="number"
                    step="0.01"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    Taxa de Serviço
                </label>

                <input
                    name="taxaServico"
                    value={pedido.taxaServico}
                    required
                    onChange={(e)=>handlerChange('taxaServico',e.target.value)}
                    placeholder="1000.00"
                    type="number"
                    step="0.01"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    Valor Total
                </label>

                <input
                    name="valorTotal"
                    value={pedido.valorTotal}
                    required
                    onChange={(e)=>handlerChange('valorTotal',e.target.value)}
                    placeholder="1000.00"
                    type="number"
                    step="0.01"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div className="flex flex-col-reverse items-center justify-center gap-3 border-t border-zinc-200 pt-6 sm:flex-row">

                <Link
                    href="/pedidos"
                    className="w-full rounded-lg border border-zinc-300 bg-zinc-100 px-6 py-3 text-center font-semibold text-zinc-700 transition duration-200 hover:border-red-400 hover:bg-red-50 hover:text-red-600 sm:w-auto"
                >
                    Cancelar
                </Link>

                <button
                    className="w-full rounded-lg bg-orange-500 px-6 py-3 font-semibold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95 sm:w-auto"
                >
                    Salvar
                </button>

            </div>

        </form>
    );
}