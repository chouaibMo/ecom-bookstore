import { ICustomerComment } from 'app/shared/model/customer-comment.model';
import { Component, Input, OnInit } from '@angular/core';
import { IBook } from 'app/shared/model/book.model';

/*
import { SearchWithPagination } from 'app/shared/util/request-util';
import { flatMap } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { of, EMPTY } from 'rxjs';
*/

/**
 * @author QCR
 * Composant listant les commentaires des utilisateurs sur un livre.
 * Dans la premiere iteration, on va se contenter d'afficher seulement un commentaire.
 */

@Component({
  selector: 'jhi-customer-review-list',
  templateUrl: './customer-review-list.component.html',
  styleUrls: ['./customer-review-list.component.scss'],
})
export class CustomerReviewListComponent implements OnInit {
  @Input() book: IBook | undefined;
  @Input() reviews: ICustomerComment[] | null | undefined;

  constructor() {}

  ngOnInit(): void {
  }
}
