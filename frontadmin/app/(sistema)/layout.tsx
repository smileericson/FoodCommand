
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import Footer from "../components/Footer";


// Componente de layout principal do sistema.
// O children representa o conteúdo da página que será exibido dentro do layout.
export default function SistemaLayout({ children }) {
    return (
        <>

            {/* // Elemento header vazio. */}
            <header></header>


            {/* // Container principal do sistema. */}
            {/* // flex organiza os elementos lado a lado. */}
            {/* // min-h-screen faz o layout ocupar pelo menos toda a altura da tela. */}
            {/* // bg-gray-100 define a cor de fundo. */}
            <div className="flex min-h-screen bg-gray-100">


                {/* // Menu lateral do sistema. */}
                <Sidebar />


                {/* // Área principal ao lado da Sidebar. */}
                {/* // flex-1 faz essa área ocupar o espaço restante. */}
                {/* // flex-col organiza Header, conteúdo e Footer verticalmente. */}
                <div className="flex min-w-0 flex-1 flex-col">


                    {/* // Cabeçalho do sistema. */}
                    <Header />


                    {/* // Área onde será exibido o conteúdo das páginas. */}
                    {/* // children representa o conteúdo da página atual. */}
                    <main className="flex-1 p-6">
                        {children}
                    </main>


                    {/* // Rodapé do sistema. */}
                    <Footer />


                </div>

            </div>
        </>
    );
}