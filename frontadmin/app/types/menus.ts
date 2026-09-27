// @/app/types/item.ts
export class Menus {
    constructor(
        public id: number |null,
        public nome: string,
        public descricao: string,
        public preco: string,
        public statusMenu: string,
    ) { }
}
export interface MenusFormProps {
    menuExistente?:Menus

}