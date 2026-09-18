import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import Footer from "../components/Footer";

export default function SistemaLayout({ children }) {
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