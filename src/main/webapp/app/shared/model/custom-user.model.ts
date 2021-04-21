import { IBillingInfo } from 'app/shared/model/billing-info.model';
import { IAddress } from 'app/shared/model/address.model';

export interface ICustomUser {
  id?: number;
  phoneNumber?: string;
  userId?: number;
  cartId?: number;
  billingInfos?: IBillingInfo[];
  addresses?: IAddress[];
}

export class CustomUser implements ICustomUser {
  constructor(
    public id?: number,
    public phoneNumber?: string,
    public userId?: number,
    public cartId?: number,
    public billingInfos?: IBillingInfo[],
    public addresses?: IAddress[]
  ) {}
}
