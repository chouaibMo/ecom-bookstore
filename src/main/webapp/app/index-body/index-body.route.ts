import { IndexBodyComponent } from './index-body.component';
import { Routes } from '@angular/router';

/*

@Injectable({ providedIn: 'root' })
export class IndexBodyResolve implements Resolve<IBook> {
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
*/

export const indexBodyRoute: Routes = [
  {
    path: 'home',
    component: IndexBodyComponent,
    /* resolve: {
      book: IndexBodyResolve
    }, */
    data: {
      authorities: [],
      pageTitle: 'home.title',
    },
    /* canActivate: [UserRouteAccessService], */
  },
];
