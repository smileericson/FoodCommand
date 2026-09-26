"use client"

import { Mesa, MesaFormProps } from "@/app/types/mesa";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function MesaForm({ mesaExistente }: MesaFormProps) {

    const router = useRouter();

    const [mesa, setMesa] = useState<Mesa>(
        mesaExistente || new Mesa(0, 0, "LIVRE")
    );

    const handlerChange = (campo: 'numero', valor: string) => {
        setMesa(valorAnterior =>
            new Mesa(
                valorAnterior.id,
                campo === 'numero' ? Number(valor) : valorAnterior.numero,
                valorAnterior.statusMesa
            )
        )
    }

    const handlerSalvar = async (formData: FormData) => {


        if (mesaExistente) {

            var dadosRetorno = await axios.put<number>("http://localhost:8080/mesa/" + mesa.id, mesa);
            if (dadosRetorno.status == 200) {
                alert("Mesa salva com sucesso!");
            } else {
                alert(dadosRetorno.data);

                return;
            }

        } else {

            var dadosRetorno = await axios.post<number>('http://localhost:8080/mesa', mesa)

            if (dadosRetorno.status == 200) {
                alert("A mesa foi salva com sucesso!");
            } else {
                alert(dadosRetorno.data);

                return;
            }

        }

        router.push("/mesas");


    }
        return (
            <form action={handlerSalvar} className="space-y-6">

                {/* Número da Mesa */}
                <div>

                    <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                        Número da Mesa
                    </label>

                    <input
                        name="numero"
                        type="number"
                        value={mesa.numero}
                        required
                        onChange={(e)=> handlerChange('numero',e.target.value)}
                        placeholder="000"
                        className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                    />

                </div>

            
                {/* Botões */}
                <div className="flex flex-col-reverse items-center justify-center gap-3 border-t border-zinc-200 pt-6 sm:flex-row">

                    <Link
                        href="/mesas"
                        className="w-full rounded-lg border border-zinc-300 bg-zinc-100 px-6 py-3 text-center font-semibold text-zinc-700 transition duration-200 hover:border-red-400 hover:bg-red-50 hover:text-red-600 sm:w-auto"
                    >
                        Cancelar
                    </Link>

                    <button
                        type="submit"
                        className="w-full rounded-lg bg-orange-500 px-6 py-3 font-semibold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95 sm:w-auto"
                    >
                        Salvar
                    </button>

                </div>

            </form>
        );
    }