import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerComment } from 'app/shared/model/customer-comment.model';

@Component({
  selector: 'jhi-customer-comment-detail',
  templateUrl: './customer-comment-detail.component.html',
})
export class CustomerCommentDetailComponent implements OnInit {
  customerComment: ICustomerComment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerComment }) => (this.customerComment = customerComment));
  }

  previousState(): void {
    window.history.back();
  }
}
