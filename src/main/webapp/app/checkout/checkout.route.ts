import { CheckoutComponent } from './checkout.component';

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

@Injectable({ providedIn: 'root' })
export class CheckoutResolve implements Resolve<IOrder> {
  constructor(private service: OrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.findCart(id).pipe(
        flatMap((order: HttpResponse<Order>) => {
          if (order.body) {
            return of(order.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Order());
  }
}

export const checkoutRoute: Routes = [
  {
    path: 'checkout/:id',
    component: CheckoutComponent,
    resolve: {
      order: CheckoutResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'global.titles.checkout',
    },
    canActivate: [UserRouteAccessService],
  },
];
