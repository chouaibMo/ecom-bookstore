export interface IOrderLine {
  id?: number;
  quantity?: number;
  price?: number;
  bookId?: number;
  orderId?: number;
}

export class OrderLine implements IOrderLine {
  constructor(public id?: number, public quantity?: number, public price?: number, public bookId?: number, public orderId?: number) {}
}
