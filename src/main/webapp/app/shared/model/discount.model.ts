import { Moment } from 'moment';
import { IBook } from 'app/shared/model/book.model';

export interface IDiscount {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  description?: string;
  discountRate?: number;
  books?: IBook[];
}

export class Discount implements IDiscount {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public description?: string,
    public discountRate?: number,
    public books?: IBook[]
  ) {}
}
