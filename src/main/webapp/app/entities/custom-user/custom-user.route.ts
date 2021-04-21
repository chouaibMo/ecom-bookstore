import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomUser, CustomUser } from 'app/shared/model/custom-user.model';
import { CustomUserService } from './custom-user.service';
import { CustomUserComponent } from './custom-user.component';
import { CustomUserDetailComponent } from './custom-user-detail.component';
import { CustomUserUpdateComponent } from './custom-user-update.component';

@Injectable({ providedIn: 'root' })
export class CustomUserResolve implements Resolve<ICustomUser> {
  constructor(private service: CustomUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customUser: HttpResponse<CustomUser>) => {
          if (customUser.body) {
            return of(customUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomUser());
  }
}

export const customUserRoute: Routes = [
  {
    path: '',
    component: CustomUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.customUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomUserDetailComponent,
    resolve: {
      customUser: CustomUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.customUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomUserUpdateComponent,
    resolve: {
      customUser: CustomUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.customUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomUserUpdateComponent,
    resolve: {
      customUser: CustomUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecomBookstoreApp.customUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
