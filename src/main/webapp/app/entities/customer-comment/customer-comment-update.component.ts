import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICustomerComment, CustomerComment } from 'app/shared/model/customer-comment.model';
import { CustomerCommentService } from './customer-comment.service';
import { ICustomUser } from 'app/shared/model/custom-user.model';
import { CustomUserService } from 'app/entities/custom-user/custom-user.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';

type SelectableEntity = ICustomUser | IBook;

@Component({
  selector: 'jhi-customer-comment-update',
  templateUrl: './customer-comment-update.component.html',
})
export class CustomerCommentUpdateComponent implements OnInit {
  isSaving = false;
  customusers: ICustomUser[] = [];
  books: IBook[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    comment: [],
    commentDate: [null, [Validators.required]],
    rating: [null, [Validators.required]],
    customerId: [],
    bookId: [],
  });

  constructor(
    protected customerCommentService: CustomerCommentService,
    protected customUserService: CustomUserService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerComment }) => {
      if (!customerComment.id) {
        const today = moment().startOf('day');
        customerComment.commentDate = today;
      }

      this.updateForm(customerComment);

      this.customUserService.query().subscribe((res: HttpResponse<ICustomUser[]>) => (this.customusers = res.body || []));

      this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));
    });
  }

  updateForm(customerComment: ICustomerComment): void {
    this.editForm.patchValue({
      id: customerComment.id,
      title: customerComment.title,
      comment: customerComment.comment,
      commentDate: customerComment.commentDate ? customerComment.commentDate.format(DATE_TIME_FORMAT) : null,
      rating: customerComment.rating,
      customerId: customerComment.customerId,
      bookId: customerComment.bookId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerComment = this.createFromForm();
    if (customerComment.id !== undefined) {
      this.subscribeToSaveResponse(this.customerCommentService.update(customerComment));
    } else {
      this.subscribeToSaveResponse(this.customerCommentService.create(customerComment));
    }
  }

  private createFromForm(): ICustomerComment {
    return {
      ...new CustomerComment(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      commentDate: this.editForm.get(['commentDate'])!.value
        ? moment(this.editForm.get(['commentDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      rating: this.editForm.get(['rating'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
      bookId: this.editForm.get(['bookId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerComment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
