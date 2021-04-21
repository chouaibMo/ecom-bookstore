import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDiscount, Discount } from 'app/shared/model/discount.model';
import { DiscountService } from './discount.service';
import { DiscountComponent } from './discount.component';
import { DiscountDetailComponent } from './discount-detail.component';
import { DiscountUpdateComponent } from './discount-update.component';

@Injectable({ providedIn: 'root' })
export class DiscountResolve implements Resolve<IDiscount> {
  constructor(private service: DiscountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiscount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((discount: HttpResponse<Discount>) => {
          if (discount.body) {
            return of(discount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Discount());
  }
}

export const discountRoute: Routes = [
  {
    path: '',
    component: DiscountComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.discount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiscountDetailComponent,
    resolve: {
      discount: DiscountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.discount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiscountUpdateComponent,
    resolve: {
      discount: DiscountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.discount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiscountUpdateComponent,
    resolve: {
      discount: DiscountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.discount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
