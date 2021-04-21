import { CustomerCommentService } from '../entities/customer-comment/customer-comment.service';
import { CustomerComment, ICustomerComment } from '../shared/model/customer-comment.model';
import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from '../shared/constants/authority.constants';
import { UserRouteAccessService } from '../core/auth/user-route-access-service';
import { IBook, Book } from '../shared/model/book.model';
import { BookService } from '../entities/book/book.service';
import { BookCommentComponent } from './book-comment.component';

@Injectable({ providedIn: 'root' })
export class BookResolve implements Resolve<IBook> {
  constructor(private service: BookService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBook> | Observable<never> {
    const id = route.params['bookid'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((book: HttpResponse<Book>) => {
          if (book.body) {
            return of(book.body);
          } else {
            // this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Book());
  }
}

@Injectable({ providedIn: 'root' })
export class CustomerCommentResolve implements Resolve<ICustomerComment> {
  constructor(private service: CustomerCommentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerComment> | Observable<never> {
    const id = route.params['bookid'];
    const customerid = route.params['userid'];
    if (id) {
      return this.service.findByCustomerIdByBookId(customerid, id).pipe(
        flatMap((customerComment: HttpResponse<CustomerComment>) => {
          if (customerComment.body) {
            return of(customerComment.body);
          } else {
            console.log('TEST EMPTY');
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerComment());
  }
}

export const bookCommentRoute: Routes = [
  {
    path: 'customer-comment/book/:bookid',
    pathMatch: 'full',
    component: BookCommentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'global.titles.review',
    },
    canActivate: [UserRouteAccessService],
  },
];
