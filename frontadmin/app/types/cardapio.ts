// @/app/types/item.ts
export class Cardapios {
    constructor(
        public id: number |null,
        public nome: string,
        public descricao: string,
        public preco: string,
        public statusCardapio: string,
    ) { }
}
export interface CardapioFormProps {
    cardapioExistente?:Cardapios

}