// @/app/types/item.ts
export class Item {
    constructor(
        public id: number,
        public nome: string,
        public descricao: string,
        public preco: number,
        public disponivel: boolean,
    ) { }
}