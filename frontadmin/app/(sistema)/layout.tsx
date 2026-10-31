'use client'

import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import Footer from "../components/Footer";
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";
import { useRouter } from "next/navigation";
import { useEffect } from "react";




export default function SistemaLayout({ children }) {

    const usuario = useSelector((state:RootState)=>state.auth.usuario);

    const router = useRouter();

    useEffect(()=>{
        if(usuario == null){
            router.push("/login");
        }
    })

    return (
        <>
        
            <header></header>
          
            <div className="flex min-h-screen bg-gray-100">

                <Sidebar />

                <div className="flex min-w-0 flex-1 flex-col">

                    <Header />

                    <main className="flex-1 p-6">
                        {children}
                    </main>

                    <Footer />

                </div>

            </div>
        </>
    );
}