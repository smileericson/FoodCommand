export class Pedido{
    constructor(
        public id:number | null,
        public valorSubtotal: number,
        public taxaServico:number,
        public valorTotal:number,
        public statusPedido:string
    ){}
}

export interface PedidoFormProps{
    pedidoExistente?:Pedido
}