import { Moment } from 'moment';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';

export interface IBillingInfo {
  id?: number;
  infoTitle?: string;
  cardNumber?: string;
  cardExpiryDate?: Moment;
  cryptogram?: string;
  email?: string;
  billingMethod?: PaymentMethod;
  customerId?: number;
}

export class BillingInfo implements IBillingInfo {
  constructor(
    public id?: number,
    public infoTitle?: string,
    public cardNumber?: string,
    public cardExpiryDate?: Moment,
    public cryptogram?: string,
    public email?: string,
    public billingMethod?: PaymentMethod,
    public customerId?: number
  ) {}
}
