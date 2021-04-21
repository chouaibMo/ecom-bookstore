import { IBook } from 'app/shared/model/book.model';
import { Country } from 'app/shared/model/enumerations/country.model';

export interface IAuthor {
  id?: number;
  name?: string;
  description?: string;
  country?: Country;
  books?: IBook[];
}

export class Author implements IAuthor {
  constructor(public id?: number, public name?: string, public description?: string, public country?: Country, public books?: IBook[]) {}
}
