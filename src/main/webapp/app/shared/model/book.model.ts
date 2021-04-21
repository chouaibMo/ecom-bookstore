import { Moment } from 'moment';
import { IAuthor } from 'app/shared/model/author.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { BookType } from 'app/shared/model/enumerations/book-type.model';

export interface IBook {
  id?: number;
  title?: string;
  price?: number;
  rating?: number;
  imageURL?: string;
  language?: Language;
  format?: BookType;
  paperBackQuantity?: number;
  publicationDate?: Moment;
  isbn?: string;
  pages?: number;
  otherDetails?: string;
  categoryId?: number;
  authors?: IAuthor[];
  discountId?: number;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public title?: string,
    public price?: number,
    public rating?: number,
    public imageURL?: string,
    public language?: Language,
    public format?: BookType,
    public paperBackQuantity?: number,
    public publicationDate?: Moment,
    public isbn?: string,
    public pages?: number,
    public otherDetails?: string,
    public categoryId?: number,
    public authors?: IAuthor[],
    public discountId?: number
  ) {}
}
