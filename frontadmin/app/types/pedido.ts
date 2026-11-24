export class Pedido{
    constructor(
        public id:number | null,
        public valorSubtotal: number,
        public taxaServico:number,
        public valorTotal:number,
        public statusPedido:string
    ){}
}

export interface ItemComanda{
    nome:string
    quantidade:number
    preco:string
}

export interface ComandaState{
    pedido:Pedido | null
    numeroMesa:number | null
    itens:ItemComanda[]
}

export interface HistoricoPedidoState{
    ultimosConsultados:number[]
}
