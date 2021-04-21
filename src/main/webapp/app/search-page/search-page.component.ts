import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { IBook } from '../shared/model/book.model';
import { BookService } from '../entities/book/book.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.scss'],
})
export class SearchPageComponent implements OnInit {
  books: IBook[] | null | undefined;
  query: String | null | undefined;

  lastSearchQuery: any | undefined;

  // Page control
  itemsPerPage = 10;
  ngbPaginationPage = 5;
  totalItems = 3333;
  page = 0;
  titleQuery: String | undefined | null;

  constructor(private bookService: BookService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params: Params) => {

      this.query = params['query'];
      let curSearch: any | undefined = this.lastSearchQuery;

      this.ngbPaginationPage = 0;
      if(this.query){
        
        this.titleQuery = this.query;
      }else{
        if(this.titleQuery){
          this.titleQuery = '';
        }
        
      }

      if (!curSearch) {
        curSearch = {
          size: this.itemsPerPage,
          page: this.ngbPaginationPage,
        };
      }

      if (curSearch) {
        curSearch['title.contains'] = this.titleQuery;
        curSearch['page'] = this.ngbPaginationPage;
      }

     

      this.lastSearchQuery = curSearch;

      this.queryCount();
    });

    /*
      this.bookService.simpleSearch(this.query).subscribe((res: HttpResponse<IBook[]>) => {
        this.books = res.body;
      });
      */
  }

  /**
   * Update filter
   * @author QCR
   */
  onFilterApplied(filter: Map<string, any>): void {
    // console.log('FILTER APPLIED :');
    /*
    for (const [key, value] of filter) {
      console.log(key, value);
    }
    */

    const mSearch: any = {
      page: 0,
      size: this.itemsPerPage,
      'title.contains': this.titleQuery,
    };


    const msort: string = filter.get('sortField');
    if (msort) {
      if (msort && msort.indexOf('.') > 1) {
        const _sort : string[] = msort.split('.');
        mSearch["sort"] = [_sort];
      }
    }
    for( const [key, value] of filter){
      if(key !== 'sortField'){
        mSearch[key] = value;
      }
    }
    /*
    if (filter.get('price.lessThan')) mSearch['price.lessThan'] = filter.get('price.lessThan');
    if (filter.get('price.greaterThan')) mSearch['price.greatThan'] = filter.get('price.greatThan');
    if (filter.get('categoryId.equals')) mSearch['categoryId.equals'] = filter.get('categoryId.equals');
    if (filter.get('rating.greaterOrEqualThan')) mSearch['rating.greaterOrEqualThan'] = filter.get('rating.greaterOrEqualThan');
    */
    this.lastSearchQuery = mSearch;
    this.queryCount();
  }

  queryCount(): void {



    this.bookService.queryCount(this.lastSearchQuery).subscribe((bookCount: HttpResponse<Long>) => {
      if (bookCount.body) {

        this.totalItems = Number(bookCount.body);
        this.page = 0; // Reinitialized page index after filter change

        this.bookService.query(this.lastSearchQuery).subscribe((books: HttpResponse<IBook[]>) => {
          if (books.body) {
            // console.log('Retrieve books : ' + books.body.length);
            this.books = books.body;
          } else {
            this.books = null;
          }
        });
      } else {
        this.books = null;
      }
    });
  }

  nextPage(page?: number): void {
    const pageToLoad: number = page || this.page || 1;
    const curSearch = this.lastSearchQuery;

    curSearch['page'] = pageToLoad - 1;
    curSearch['size'] = this.itemsPerPage;

    this.bookService.query(curSearch).subscribe((books: HttpResponse<IBook[]>) => {
      if (books.body) {
        // console.log('Retrieve books : ' + books.body.length);
        this.books = books.body;
      } else {
        // console.log('No book retrieved');
        this.books = null;
      }
    });
  }
}
