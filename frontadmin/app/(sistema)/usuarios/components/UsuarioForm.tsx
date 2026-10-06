'use client'

// Importa o tipo Usuario e as propriedades esperadas pelo componente UsuarioForm.
import { Usuario, UsuarioFormProps } from "@/app/types/usuario";

// Axios é utilizado para fazer as requisições HTTP para o backend.
import axios from "axios";

// Link permite navegar entre páginas do Next.js.
import Link from "next/link";

// useRouter permite realizar navegação através do código.
import { useRouter } from "next/navigation";

// useState permite criar e controlar um estado dentro do componente.
import { useState } from "react";

// Componente responsável pelo formulário de cadastro e edição de usuário.
// Recebe um usuário existente para que o formulário possa ser utilizado na edição.
export default function UsuarioForm({ usuarioExistente }: UsuarioFormProps) {

    // Cria o router para poder redirecionar o usuário depois de salvar.
    const router = useRouter();

    // Cria o estado usuario.
    // Se usuarioExistente existir, significa que estamos editando um usuário
    // e seus dados serão utilizados para preencher o formulário.
    // Caso contrário, cria um novo Usuario com valores iniciais.
    const [usuario, setUsuario] = useState<Usuario>(
        usuarioExistente || new Usuario(null, "", "", "ATIVO", "", ""));

    // Função responsável por alterar os dados do usuário.
    // "campo" informa qual propriedade será alterada.
    // "valor" contém o novo valor digitado pelo usuário.
    const handlerChange = (campo: 'nome' | 'cpf' | 'email' | 'senha', valor: string) => {

        // Atualiza o estado do usuário.
        // valorAnterior representa os dados que já estavam armazenados.
        // Um novo objeto Usuario é criado mantendo os dados anteriores
        // e alterando somente o campo que foi informado.
        setUsuario(valorAnterior =>
            new Usuario(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'email' ? valor : valorAnterior.email,
                valorAnterior.status,
                campo === 'cpf' ? valor : valorAnterior.cpf,
                campo === 'senha' ? valor : valorAnterior.senha

            )
        )
    }

    // Função executada quando o formulário é enviado.
    // async permite trabalhar com requisições assíncronas.
    // formData recebe os dados enviados pelo formulário.
    const handlerSalvar = async (formData: FormData) => {

        // Verifica se usuarioExistente existe.
        // Se existir, estamos no modo de edição.
        if (usuarioExistente) {

            // PUT é utilizado para atualizar o usuário existente.
            // O ID do usuário é colocado na URL.
            // O segundo parâmetro "usuario" é enviado como corpo
            // da requisição para o backend.
            var dadosRetorno = await
                axios.put<number>('http://localhost:8080/usuarios/' + usuario.id, usuario);

            // Verifica se o backend retornou HTTP 200.
            if (dadosRetorno.status == 200) {

                // Mostra uma mensagem informando que o usuário foi salvo.
                alert("Usuario foi salvo com sucesso!");

            } else {

                // Caso ocorra outro status, mostra a informação retornada.
                alert(dadosRetorno.data);
                return;
            }

        } else {

            // Se usuarioExistente não existir, significa que estamos
            // cadastrando um novo usuário.
            // POST é utilizado para enviar o novo usuário ao backend.
            var dadosRetorno = await axios.post<number>('http://localhost:8080/usuarios', usuario)

            // Verifica se o cadastro foi realizado com HTTP 200.
            if (dadosRetorno.status == 200) {

                alert("Usuário foi salvo com sucesso!");

            } else {

                // Mostra a mensagem retornada pelo backend em caso de erro.
                alert(dadosRetorno.data);

                return;
            }
        }

        // Depois de salvar com sucesso, volta para a listagem de usuários.
        router.push("/usuarios");
    }

    return (

        // Formulário HTML do React.
        // action={handlerSalvar} informa que, ao enviar o formulário,
        // a função handlerSalvar será executada.
        // As classes Tailwind abaixo cuidam somente do espaçamento
        // entre os elementos do formulário.
        <form action={handlerSalvar} className="space-y-6">

            {/* // Cada div abaixo representa um campo do formulário. */}
            {/* // O Tailwind é utilizado apenas para organizar e estilizar */}
            {/* // visualmente os elementos. */}
            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    Nome completo:
                </label>

                {/* // Campo utilizado para informar o nome. */}
                {/* // value={usuario.nome} mantém o campo sincronizado */}
                {/* // com o estado usuario. */}
                {/* // onChange chama handlerChange sempre que o usuário digita. */}
                <input
                    name="nome"
                    value={usuario.nome}
                    required
                    onChange={(e) => handlerChange('nome', e.target.value)}
                    placeholder="Ericson Smile"

                    // Tailwind:
                    // w-full → ocupa toda a largura disponível.
                    // border → cria a borda.
                    // px/py → espaçamento interno.
                    // text-center → centraliza o texto.
                    // focus → altera a aparência quando o campo recebe foco.
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    CPF:
                </label>

                {/* // Campo para informar o CPF. */}
                {/* // O valor é controlado pelo estado usuario.cpf. */}
                <input
                    name="CPF"
                    value={usuario.cpf}
                    required
                    onChange={(e) => handlerChange('cpf', e.target.value)}
                    placeholder="000.000.000-00"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    E-mail:
                </label>

                {/* // Campo para o e-mail. */}
                {/* // type="email" informa ao navegador que o campo */}
                {/* // deve receber um endereço de e-mail. */}
                <input
                    name="email"
                    value={usuario.email}
                    required
                    onChange={(e) => handlerChange('email', e.target.value)}
                    placeholder="EricsonSmile@gmail.com"
                    type="email"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            <div>
                <label className="mb-2 block text-center text-sm font-semibold text-zinc-700">
                    Senha:
                </label>

                {/* // Campo de senha. */}
                {/* // type="password" faz com que os caracteres digitados */}
                {/* // sejam ocultados visualmente. */}
                <input
                    name="senha"
                    value={usuario.senha}
                    required
                    onChange={(e) => handlerChange('senha', e.target.value)}
                    placeholder="*************"
                    type="password"
                    className="w-full rounded-lg border border-zinc-300 bg-white px-4 py-3 text-center text-zinc-900 shadow-sm outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-200"
                />
            </div>

            {/* // Área dos botões. */}
            {/* // O Tailwind organiza os botões de forma responsiva: */}
            {/* // em telas menores ficam em coluna; */}
            {/* // em telas maiores ficam lado a lado. */}
            <div className="flex flex-col-reverse items-center justify-center gap-3 border-t border-zinc-200 pt-6 sm:flex-row">

                {/* // Link para cancelar a operação e voltar para a listagem. */}
                <Link
                    href="/usuarios"

                    // Tailwind responsável pela aparência do botão:
                    // borda, fundo, espaçamento, texto, hover e responsividade.
                    className="w-full rounded-lg border border-zinc-300 bg-zinc-100 px-6 py-3 text-center font-semibold text-zinc-700 transition duration-200 hover:border-red-400 hover:bg-red-50 hover:text-red-600 sm:w-auto"
                >
                    Cancelar
                </Link>

                {/* // Botão responsável por enviar o formulário. */}
                {/* // Como o formulário possui action=handlerSalvar, */}
                {/* // clicar em Salvar executará handlerSalvar. */}
                <button

                    // Tailwind estiliza o botão:
                    // fundo laranja, texto branco, sombra,
                    // efeito ao passar o mouse e ao clicar.
                    className="w-full rounded-lg bg-orange-500 px-6 py-3 font-semibold text-white shadow-md transition duration-200 hover:bg-orange-600 hover:shadow-lg active:scale-95 sm:w-auto"
                >
                    Salvar
                </button>

            </div>

        </form>
    );
}