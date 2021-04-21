import { Moment } from 'moment';

export interface ICustomerComment {
  id?: number;
  title?: string;
  comment?: string;
  commentDate?: Moment;
  rating?: number;
  customerId?: number;
  bookId?: number;
}

export class CustomerComment implements ICustomerComment {
  constructor(
    public id?: number,
    public title?: string,
    public comment?: string,
    public commentDate?: Moment,
    public rating?: number,
    public customerId?: number,
    public bookId?: number
  ) {}
}
