import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomerComment, CustomerComment } from 'app/shared/model/customer-comment.model';
import { CustomerCommentService } from './customer-comment.service';
import { CustomerCommentComponent } from './customer-comment.component';
import { CustomerCommentDetailComponent } from './customer-comment-detail.component';
import { CustomerCommentUpdateComponent } from './customer-comment-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerCommentResolve implements Resolve<ICustomerComment> {
  constructor(private service: CustomerCommentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerComment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customerComment: HttpResponse<CustomerComment>) => {
          if (customerComment.body) {
            return of(customerComment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerComment());
  }
}

export const customerCommentRoute: Routes = [
  {
    path: '',
    component: CustomerCommentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ecomBookstoreApp.customerComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerCommentDetailComponent,
    resolve: {
      customerComment: CustomerCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.customerComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerCommentUpdateComponent,
    resolve: {
      customerComment: CustomerCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.customerComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerCommentUpdateComponent,
    resolve: {
      customerComment: CustomerCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.customerComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
