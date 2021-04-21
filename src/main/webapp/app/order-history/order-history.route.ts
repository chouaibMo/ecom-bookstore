import { OrderHistoryComponent } from './order-history.component';
import { Authority } from '../../app/shared/constants/authority.constants';
import { UserRouteAccessService } from '../../app/core/auth/user-route-access-service';
import { Routes } from '@angular/router';

export const orderHistoryRoute: Routes = [
  {
    path: 'order-history',
    component: OrderHistoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'global.titles.orders',
    },
    canActivate: [UserRouteAccessService],
  },
];
