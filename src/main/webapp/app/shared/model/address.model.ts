import { Country } from 'app/shared/model/enumerations/country.model';

export interface IAddress {
  id?: number;
  address?: string;
  city?: string;
  zipCode?: string;
  country?: Country;
  customerId?: number;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public address?: string,
    public city?: string,
    public zipCode?: string,
    public country?: Country,
    public customerId?: number
  ) {}
}
