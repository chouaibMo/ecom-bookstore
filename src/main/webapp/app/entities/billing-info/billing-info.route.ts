import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBillingInfo, BillingInfo } from 'app/shared/model/billing-info.model';
import { BillingInfoService } from './billing-info.service';
import { BillingInfoComponent } from './billing-info.component';
import { BillingInfoDetailComponent } from './billing-info-detail.component';
import { BillingInfoUpdateComponent } from './billing-info-update.component';

@Injectable({ providedIn: 'root' })
export class BillingInfoResolve implements Resolve<IBillingInfo> {
  constructor(private service: BillingInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBillingInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((billingInfo: HttpResponse<BillingInfo>) => {
          if (billingInfo.body) {
            return of(billingInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BillingInfo());
  }
}

export const billingInfoRoute: Routes = [
  {
    path: '',
    component: BillingInfoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.billingInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BillingInfoDetailComponent,
    resolve: {
      billingInfo: BillingInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.billingInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BillingInfoUpdateComponent,
    resolve: {
      billingInfo: BillingInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.billingInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BillingInfoUpdateComponent,
    resolve: {
      billingInfo: BillingInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.billingInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
