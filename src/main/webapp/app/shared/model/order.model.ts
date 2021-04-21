import { Moment } from 'moment';
import { IOrderLine } from 'app/shared/model/order-line.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

export interface IOrder {
  id?: number;
  orderStatus?: OrderStatus;
  orderDetails?: string;
  totalPrice?: number;
  orderDate?: Moment;
  paymentDate?: Moment;
  orderLines?: IOrderLine[];
  customerId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderStatus?: OrderStatus,
    public orderDetails?: string,
    public totalPrice?: number,
    public orderDate?: Moment,
    public paymentDate?: Moment,
    public orderLines?: IOrderLine[],
    public customerId?: number
  ) {}
}
