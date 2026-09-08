import Image from "next/image";

export default function Home() {
  return (
    <div className="bg-zinc-950 text-zinc-200 font-sans antialiased selection:bg-orange-500 selection:text-white min-h-screen">
      {/* Header / Navbar */}
      <header className="fixed top-0 left-0 right-0 z-50 bg-zinc-950/85 backdrop-blur-md border-b border-zinc-800">
        <div className="max-w-7xl mx-auto px-6 h-20 flex items-center justify-between">
          {/* Logo */}
          <div className="flex items-center gap-3">
            <svg className="h-9 w-9" viewBox="0 0 36 36" fill="none" xmlns="http://www.w3.org/2000/svg">
              <rect width="36" height="36" rx="10" fill="#18181B"/>
              <path d="M10 12C10 10.8954 10.8954 10 12 10H24C25.1046 10 26 10.8954 26 12V24C26 25.1046 25.1046 26 24 26H12C10.8954 26 10 25.1046 10 24V12Z" stroke="#F97316" strokeWidth="2"/>
              <path d="M14 15H22M14 19H22M14 23H18" stroke="#F97316" strokeWidth="2" strokeLinecap="round"/>
              <circle cx="23" cy="23" r="2" fill="#F97316"/>
            </svg>
            <span className="text-xl font-extrabold tracking-tight text-white">
              Food<span className="text-orange-500">Command</span>
            </span>
          </div>

          {/* Navegação */}
          <nav className="hidden md:flex items-center gap-8 text-sm font-medium text-zinc-400">
            <a href="#recursos" className="hover:text-orange-400 transition-colors">
              Recursos
            </a>
            <a href="#historia" className="hover:text-orange-400 transition-colors">
              Nossa História
            </a>
            <a href="#beneficios" className="hover:text-orange-400 transition-colors">
              Vantagens
            </a>
          </nav>

          {/* Botão de Login / Acesso */}
          <div className="flex items-center gap-4">
            <a href="/login" className="px-5 py-2.5 text-sm font-semibold text-white bg-orange-600 hover:bg-orange-500 rounded-xl shadow-lg shadow-orange-500/20 transition-all hover:scale-[1.02] active:scale-[0.98]">
              Acessar Sistema
            </a>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="pt-32 pb-20 md:pt-44 md:pb-32 overflow-hidden relative">
        <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[600px] bg-orange-950/20 rounded-full blur-3xl -z-10 pointer-events-none"></div>

        <div className="max-w-7xl mx-auto px-6 text-center">
          <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-orange-950/60 border border-orange-900 text-orange-300 text-xs font-semibold mb-6 animate-pulse">
            <i className="fa-solid fa-bolt text-orange-400"></i> Agilidade extrema para Bares e Restaurantes
          </div>

          <h1 className="text-4xl md:text-6xl font-extrabold tracking-tight text-zinc-100 max-w-4xl mx-auto leading-[1.15] mb-6">
            O comando total do seu restaurante com{' '}
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-orange-400 to-orange-600">
              agilidade e zero erros
            </span>
            .
          </h1>

          <p className="text-lg md:text-xl text-zinc-400 max-w-2xl mx-auto mb-10 leading-relaxed">
            Esqueça o papel rasurado e o atraso nas entregas. O FoodCommand permite que garçons registrem pedidos direto pelo aplicativo, associando instantaneamente cada item à mesa e ao cliente.
          </p>

          <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
            <a href="#recursos" className="w-full sm:w-auto px-8 py-4 text-base font-semibold text-white bg-orange-600 hover:bg-orange-500 rounded-2xl shadow-xl shadow-orange-500/25 transition-all hover:translate-y-[-2px]">
              Conhecer o Sistema
            </a>
            
            <a href="#historia" className="w-full sm:w-auto px-8 py-4 text-base font-semibold text-zinc-200 bg-zinc-900 hover:bg-zinc-800 border border-zinc-800 rounded-2xl shadow-sm transition-all">
              Nossa História
            </a>
          </div>

          {/* Mockup / Preview Visual do App */}
          <div className="mt-16 max-w-4xl mx-auto rounded-2xl border border-zinc-800 bg-zinc-900 p-3 shadow-2xl shadow-black/60">
            <div className="bg-zinc-950 rounded-xl overflow-hidden p-6 md:p-8 text-left border border-zinc-800/80">
              <div className="flex justify-between items-center border-b border-zinc-800 pb-4 mb-4">
                <div className="flex items-center gap-3">
                  <span className="bg-orange-500 text-white font-bold text-xs px-3 py-1 rounded-full">
                    Mesa 08
                  </span>
                  <span className="text-xs text-zinc-400">Garçom: Lucas Santos</span>
                </div>
                <span className="text-xs text-emerald-400 font-semibold flex items-center gap-1.5">
                  <span className="w-2 h-2 rounded-full bg-emerald-400 animate-ping"></span> Pedido Ativo
                </span>
              </div>

              <div className="bg-zinc-900/90 border-l-4 border-orange-500 px-4 py-2.5 rounded-r-lg mb-4 text-sm text-zinc-300">
                Cliente: <strong className="text-white">Rodrigo Silva</strong>
              </div>

              <div className="space-y-3 mb-6">
                <div className="flex justify-between items-center bg-zinc-900 p-3 rounded-lg border border-zinc-800 text-sm">
                  <div className="flex gap-3 items-center">
                    <span className="font-bold text-orange-500">1x</span>
                    <div>
                      <p className="font-semibold text-zinc-100">Hambúrguer Gourmet Double</p>
                      <p className="text-xs text-zinc-500">Sem cebola / Ponto médio</p>
                    </div>
                  </div>
                  <span className="font-bold text-zinc-200">R$ 42,00</span>
                </div>

                <div className="flex justify-between items-center bg-zinc-900 p-3 rounded-lg border border-zinc-800 text-sm">
                  <div className="flex gap-3 items-center">
                    <span className="font-bold text-orange-500">2x</span>
                    <div>
                      <p className="font-semibold text-zinc-100">Suco Natural Laranja 500ml</p>
                      <p className="text-xs text-zinc-500">Com gelo e adoçante</p>
                    </div>
                  </div>
                  <span className="font-bold text-zinc-200">R$ 24,00</span>
                </div>
              </div>

              <div className="flex justify-between items-center pt-2">
                <div>
                  <span className="text-xs text-zinc-400 block">Total da Mesa</span>
                  <span className="text-xl font-black text-orange-500">R$ 66,00</span>
                </div>
                <button className="px-5 py-2.5 bg-white text-zinc-950 font-bold text-xs rounded-xl hover:bg-zinc-200 transition-colors">
                  Enviar para Cozinha
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Seção: Nossa História */}
      <section id="historia" className="py-24 bg-zinc-900 border-y border-zinc-800">
        <div className="max-w-7xl mx-auto px-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
            <div>
              <span className="text-orange-400 text-sm font-bold uppercase tracking-wider">Nossa História</span>
              <h2 className="text-3xl md:text-4xl font-bold text-zinc-100 mt-2 mb-6">
                Desenvolvido para transformar o ritmo do atendimento no salão.
              </h2>
              <p className="text-zinc-400 leading-relaxed mb-4">
                O <strong className="text-zinc-200 font-semibold">FoodCommand</strong> é um aplicativo de gerenciamento de comandas projetado para a rotina intensa de restaurantes, bares e lanchonetes.
              </p>
              <p className="text-zinc-400 leading-relaxed mb-6">
                Com ele, os garçons registram os pedidos instantaneamente direto pelo aplicativo. O sistema associa cada item à mesa e ao cliente de forma individual, eliminando erros na hora do fechamento da conta e acelerando o envio dos pratos para a cozinha.{' '}
                <strong className="text-zinc-200 font-semibold">Assuma o controle total da sua operação.</strong>
              </p>
              <div className="flex items-center gap-4 p-4 rounded-xl bg-orange-950/50 border border-orange-900">
                <div className="text-orange-400 text-2xl">
                  <i className="fa-solid fa-utensils"></i>
                </div>
                <div className="text-sm text-zinc-300">
                  Desenhado para simplificar a divisão de contas e agilizar a rotina do garçom.
                </div>
              </div>
            </div>
            
            <div className="relative">
              <div className="w-full aspect-square rounded-3xl bg-gradient-to-br from-zinc-900 to-orange-950/40 p-8 flex flex-col justify-between border border-zinc-800 shadow-inner">
                <div className="flex justify-between items-center">
                  <span className="text-xs font-bold text-orange-300 bg-zinc-950 px-3 py-1 rounded-full shadow-sm">
                    Foco na Operação
                  </span>
                  <i className="fa-solid fa-quote-right text-orange-800 text-4xl"></i>
                </div>
                <blockquote className="text-xl font-medium text-zinc-200 italic my-auto">
                  &quot;Eliminamos os mal-entendidos nos pedidos e o tempo de espera no atendimento caiu pela metade já no primeiro mês.&quot;
                </blockquote>
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-full bg-orange-600 text-white font-bold flex items-center justify-center">
                    FC
                  </div>
                  <div>
                    <h4 className="text-sm font-bold text-zinc-100">Equipe FoodCommand</h4>
                    <p className="text-xs text-zinc-500">Especialistas em Gestão Gastronômica</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Seção: Recursos Principais */}
      <section id="recursos" className="py-24 bg-zinc-950">
        <div className="max-w-7xl mx-auto px-6">
          <div className="text-center max-w-2xl mx-auto mb-16">
            <span className="text-orange-400 text-sm font-bold uppercase tracking-wider">Recursos Poderosos</span>
            <h2 className="text-3xl font-bold text-zinc-100 mt-2 mb-4">Tudo o que seu estabelecimento precisa</h2>
            <p className="text-zinc-400">Esqueça comandas de papel e correria desnecessária. Um sistema feito para a máxima eficiência do salão à cozinha.</p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {/* Card 1 */}
            <div className="bg-zinc-900 p-8 rounded-2xl border border-zinc-800 shadow-sm hover:shadow-md hover:border-zinc-700 transition-all">
              <div className="w-12 h-12 rounded-xl bg-orange-950/60 text-orange-400 flex items-center justify-center text-xl mb-6">
                <i className="fa-solid fa-mobile-screen-button"></i>
              </div>
              <h3 className="text-lg font-bold text-zinc-100 mb-2">Pedidos pelo Aplicativo</h3>
              <p className="text-zinc-400 text-sm leading-relaxed">
                Garçons lançam os pedidos direto da mesa em tempo real, reduzindo drasticamente o tempo de atendimento por cliente.
              </p>
            </div>

            {/* Card 2 */}
            <div className="bg-zinc-900 p-8 rounded-2xl border border-zinc-800 shadow-sm hover:shadow-md hover:border-zinc-700 transition-all">
              <div className="w-12 h-12 rounded-xl bg-orange-950/60 text-orange-400 flex items-center justify-center text-xl mb-6">
                <i className="fa-solid fa-users"></i>
              </div>
              <h3 className="text-lg font-bold text-zinc-100 mb-2">Associação por Cliente</h3>
              <p className="text-zinc-400 text-sm leading-relaxed">
                Vários clientes na mesma mesa? O app divide e organiza cada consumo individualmente sem complicação.
              </p>
            </div>

            {/* Card 3 */}
            <div className="bg-zinc-900 p-8 rounded-2xl border border-zinc-800 shadow-sm hover:shadow-md hover:border-zinc-700 transition-all">
              <div className="w-12 h-12 rounded-xl bg-orange-950/60 text-orange-400 flex items-center justify-center text-xl mb-6">
                <i className="fa-solid fa-fire-burner"></i>
              </div>
              <h3 className="text-lg font-bold text-zinc-100 mb-2">Envio Direto para Cozinha</h3>
              <p className="text-zinc-400 text-sm leading-relaxed">
                O pedido vai instantaneamente para a tela do KDS ou impressoras da cozinha e bar, sem necessidade de deslocamento.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-zinc-950 text-zinc-500 py-12 border-t border-zinc-800">
        <div className="max-w-7xl mx-auto px-6 flex flex-col md:flex-row items-center justify-between gap-6">
          <div className="flex items-center gap-3">
            <span className="text-lg font-extrabold text-white">
              Food<span className="text-orange-500">Command</span>
            </span>
          </div>

          <p className="text-xs text-zinc-600">
            &copy; 2026 FoodCommand. Todos os direitos reservados. Gestão inteligente de comandas e mesas.
          </p>

          <div className="flex items-center gap-4 text-sm">
            <a href="/login" className="hover:text-zinc-200 transition-colors">
              Termos
            </a>
            <a href="/login" className="hover:text-zinc-200 transition-colors">
              Privacidade
            </a>
            <a href="/login" className="text-orange-400 font-medium hover:underline">
              Fazer Login
            </a>
          </div>
        </div>
      </footer>
    </div>
  );
}