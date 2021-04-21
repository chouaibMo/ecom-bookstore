import { SearchPageComponent } from './search-page.component';

import { Injectable } from '@angular/core';

import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';


import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

import { BookService } from '../entities/book/book.service';


@Injectable({ providedIn: 'root' })
export class GetQueryResolve implements Resolve<String> {
  constructor(private service: BookService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): String {
    const query: String | null = route.queryParamMap.get('query');
    if(!query)return "";
    return query;
  }
}

export const searchPageRoute: Routes = [
  {
    path: 'search/books',
    component: SearchPageComponent,
    resolve: {
      query: GetQueryResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'ecomBookstoreApp.book.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
