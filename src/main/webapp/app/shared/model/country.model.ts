export interface ICountry {
  id?: number;
  country?: string;
}

export class Country implements ICountry {
  constructor(public id?: number, public country?: string) {}
}
