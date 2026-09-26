export class Mesa {

    constructor(

        public id: number,
        public numero: number,
        public statusMesa: string

    ) { }

}

export interface MesaFormProps {
    mesaExistente?: Mesa

}