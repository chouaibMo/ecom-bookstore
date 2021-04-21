import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search, SearchWithPagination } from 'app/shared/util/request-util';
import { IBook } from 'app/shared/model/book.model';

type EntityResponseType = HttpResponse<IBook>;
type EntityArrayResponseType = HttpResponse<IBook[]>;

@Injectable({ providedIn: 'root' })
export class BookService {
  public resourceUrl = SERVER_API_URL + 'api/books';
  public resourceSimpleSearchUrl = SERVER_API_URL + 'api/search/books';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/books';

  constructor(protected http: HttpClient) {}

  create(book: IBook): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(book);
    return this.http
      .post<IBook>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(book: IBook): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(book);
    return this.http
      .put<IBook>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number | undefined): Observable<EntityResponseType> {
    return this.http
      .get<IBook>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBook[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }


  /**
   * @author QCR 
   * Retrieve the count for a given query
   * @param req 
   */
  queryCount(req?: any): Observable<HttpResponse<Long> > {
    const options = createRequestOption(req);
    return this.http
      .get<Long>(this.resourceUrl + "/count", { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBook[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(book: IBook): IBook {
    const copy: IBook = Object.assign({}, book, {
      publicationDate: book.publicationDate && book.publicationDate.isValid() ? book.publicationDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.publicationDate = res.body.publicationDate ? moment(res.body.publicationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((book: IBook) => {
        book.publicationDate = book.publicationDate ? moment(book.publicationDate) : undefined;
      });
    }
    return res;
  }

  /**
   * @author Emez
   * Get all books
   *
   */
  getAllBooks(): Observable<EntityArrayResponseType> {
    return this.http
      .get<IBook[]>(`${this.resourceUrl}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  /**
   * @author CM
   * Get most rated books
   *
   */
  getMostRated(): Observable<EntityArrayResponseType> {
    return this.http
      .get<IBook[]>(`${this.resourceUrl}` + '/most-rated', { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  /**
   * @author CM
   * Get most reviewed books
   *
   */
  getMostReviewed(): Observable<EntityArrayResponseType> {
    return this.http
      .get<IBook[]>(`${this.resourceUrl}` + '/most-reviewed', { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  /**
   * @author CM
   * Get books corresponding to the query
   *
   */
  simpleSearch(req: any): Observable<EntityArrayResponseType> {
    const mySearch: Search = {
      query: req,
    };

    const options = createRequestOption(mySearch);
    return this.http
      .get<IBook[]>(`${this.resourceSimpleSearchUrl}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
}
