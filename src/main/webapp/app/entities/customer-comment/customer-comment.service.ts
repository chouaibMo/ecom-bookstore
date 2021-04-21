import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICustomerComment } from 'app/shared/model/customer-comment.model';
import { Observable } from 'rxjs';

type EntityResponseType = HttpResponse<ICustomerComment>;
type EntityArrayResponseType = HttpResponse<ICustomerComment[]>;

@Injectable({ providedIn: 'root' })
export class CustomerCommentService {
  public resourceUrl = SERVER_API_URL + 'api/customer-comments';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/customer-comments';
  public resourcePrefixUrl = SERVER_API_URL + 'api';

  constructor(protected http: HttpClient) {}

  create(customerComment: ICustomerComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerComment);
    return this.http
      .post<ICustomerComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customerComment: ICustomerComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerComment);
    return this.http
      .put<ICustomerComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerComment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerComment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerComment[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(customerComment: ICustomerComment): ICustomerComment {
    const copy: ICustomerComment = Object.assign({}, customerComment, {
      commentDate: customerComment.commentDate && customerComment.commentDate.isValid() ? customerComment.commentDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.commentDate = res.body.commentDate ? moment(res.body.commentDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((customerComment: ICustomerComment) => {
        customerComment.commentDate = customerComment.commentDate ? moment(customerComment.commentDate) : undefined;
      });
    }
    return res;
  }

  findByCustomerIdByBookId(customerId: number, bookId: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerComment>(`${this.resourcePrefixUrl}/user/${customerId}/book/${bookId}/customer-comment`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  /**
   * @author CM
   * Get all reviews corresponding to a specific book
   *
   */
  getbookComments(bookId : number): Observable<EntityArrayResponseType> {
    return this.http
      .get<ICustomerComment[]>(`${this.resourceUrl}` + '/book/'+ bookId, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
}
