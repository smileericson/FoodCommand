export class Pedido{
    constructor(
        public id:number | null,
        public valorSubtotal: number,
        public taxaServico:number,
        public valorTotal:number,
        public status:number
    ){}
}