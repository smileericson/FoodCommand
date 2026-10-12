export class Mesa {

    constructor(

        public id: number,
        public numeroMesa: number,
        public statusMesa: string

    ) { }

}

export interface MesaFormProps {
    mesaExistente?: Mesa

}