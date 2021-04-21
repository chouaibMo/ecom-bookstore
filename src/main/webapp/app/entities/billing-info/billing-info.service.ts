import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBillingInfo } from 'app/shared/model/billing-info.model';

type EntityResponseType = HttpResponse<IBillingInfo>;
type EntityArrayResponseType = HttpResponse<IBillingInfo[]>;

@Injectable({ providedIn: 'root' })
export class BillingInfoService {
  public resourceUrl = SERVER_API_URL + 'api/billing-infos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/billing-infos';

  constructor(protected http: HttpClient) {}

  create(billingInfo: IBillingInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billingInfo);
    return this.http
      .post<IBillingInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(billingInfo: IBillingInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billingInfo);
    return this.http
      .put<IBillingInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBillingInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBillingInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBillingInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(billingInfo: IBillingInfo): IBillingInfo {
    const copy: IBillingInfo = Object.assign({}, billingInfo, {
      cardExpiryDate:
        billingInfo.cardExpiryDate && billingInfo.cardExpiryDate.isValid() ? billingInfo.cardExpiryDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.cardExpiryDate = res.body.cardExpiryDate ? moment(res.body.cardExpiryDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((billingInfo: IBillingInfo) => {
        billingInfo.cardExpiryDate = billingInfo.cardExpiryDate ? moment(billingInfo.cardExpiryDate) : undefined;
      });
    }
    return res;
  }
}
