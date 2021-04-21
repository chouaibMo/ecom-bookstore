import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IOrderLine } from 'app/shared/model/order-line.model';

type EntityResponseType = HttpResponse<IOrderLine>;
type EntityArrayResponseType = HttpResponse<IOrderLine[]>;

@Injectable({ providedIn: 'root' })
export class OrderLineService {
  public resourceUrl = SERVER_API_URL + 'api/order-lines';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/order-lines';

  constructor(protected http: HttpClient) {}

  create(orderLine: IOrderLine): Observable<EntityResponseType> {
    return this.http.post<IOrderLine>(this.resourceUrl, orderLine, { observe: 'response' });
  }

  update(orderLine: IOrderLine): Observable<EntityResponseType> {
    return this.http.put<IOrderLine>(this.resourceUrl, orderLine, { observe: 'response' });
  }

  /**
   * @author QCR
   * Recuperation de lignes de commandes a partir du numero de commande
   * @param orderid
   */
  findAllByOrderId(orderid: number): Observable<EntityArrayResponseType> {
    return this.http.get<IOrderLine[]>(`${this.resourceUrl}/orderid/${orderid}`, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderLine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderLine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderLine[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
