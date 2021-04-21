import { BookDetailsPageComponent } from './book-details-page.component';
import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { IBook, Book } from '../../app/shared/model/book.model';
import { BookService } from '../entities/book/book.service';

@Injectable({ providedIn: 'root' })
export class BookResolve implements Resolve<IBook> {
  constructor(private service: BookService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBook> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((book: HttpResponse<Book>) => {
          if (book.body) {
            return of(book.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Book());
  }
}

export const bookDetailsPageRoute: Routes = [
  {
    path: '',
    component: BookDetailsPageComponent,
    resolve: {},
    data: {
      authorities: [],
      pageTitle: 'ecomBookstoreApp.book.home.title',
    },
    // canActivate: [UserRouteAccessService],
  },
  {
    path: 'shop/book/:id/view',
    pathMatch: 'full',
    component: BookDetailsPageComponent,
    resolve: {
      book: BookResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'ecomBookstoreApp.book.detail.title',
    },
    // canActivate: [UserRouteAccessService],
  },
];
