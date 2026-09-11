export class Pedido{
    constructor(
        private id:number | null,
        private valorSubtotal: number,
        private taxaServico:number,
        private valorTotal:number,
        private status:number
    ){}
}