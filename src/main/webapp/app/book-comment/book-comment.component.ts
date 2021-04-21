import { BookCommentUpdateDialogComponent } from './book-comment-update-dialog/book-comment-update-dialog.component';
import { CustomerComment, ICustomerComment } from './../shared/model/customer-comment.model';
import { UserService } from './../core/user/user.service';
import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { IBook } from 'app/shared/model/book.model';
import { FormBuilder, Validators } from '@angular/forms';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { CustomerCommentService } from 'app/entities/customer-comment/customer-comment.service';
import { JhiAlertService } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { ICustomUser } from 'app/shared/model/custom-user.model';
import { CustomUserService } from 'app/entities/custom-user/custom-user.service';
import { BookService } from 'app/entities/book/book.service';
import { IUser } from 'app/core/user/user.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

/**
 * @author QCR 
 * Component used to add a comment on a book
 */


@Component({
  selector: 'jhi-book-comment',
  templateUrl: './book-comment.component.html',
  styleUrls: ['./book-comment.component.scss'],
})
export class BookCommentComponent implements OnInit {
  [x: string]: any;

  user: IUser | undefined;
  customUser: ICustomUser | undefined | null;
  book: IBook | undefined;
  customerComment: ICustomerComment | undefined;
  isSaving = false;
  rate!: number;

  editForm = this.formBuilder.group({
    title: ['', Validators.required],
    comment: ['', [Validators.required, Validators.minLength(50), Validators.maxLength(150)]],
  });

  constructor(
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private userService: UserService,
    private customUserService: CustomUserService,
    private bookService: BookService,
    private customerCommentService: CustomerCommentService,
    private formBuilder: FormBuilder,
    private jhiAlertService: JhiAlertService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    const bookId = this.activatedRoute.snapshot.params['bookid'];
    const customerId = this.activatedRoute.snapshot.params['userid'];
    console.log(' Params : ' + bookId + ' -- customUser : ' + customerId);
    this.rate = 0;
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.userService.find(account.login).subscribe(user => {
          if (user) {
            this.user = user;
            this.customUserService.findByUserId(this.user.id).subscribe(customUsers => {
              if (customUsers.body) {
                console.log('Custom User found');
                this.customUser = customUsers.body;
                this.bookService.find(bookId).subscribe((book: HttpResponse<IBook>) => {
                  if (book.body) {
                    this.book = book.body;
                    this.customerCommentService
                      .findByCustomerIdByBookId(this.customUser?.id!, this.book.id!)
                      .subscribe((customerComment: HttpResponse<ICustomerComment>) => {
                        if (customerComment.body !== undefined && customerComment !== null) {
                          this.customerComment = customerComment.body!;
                        } else {
                          this.customerComment = new CustomerComment();
                          this.customerComment.bookId = this.book!.id!;
                          this.customerComment.customerId = customerId;
                        }
                        this.updateForm(this.customerComment);
                      });
                  }
                });
              } else {
                console.log('No customUser found');
              }
            });
          }
        });
      }
    });
  }

  updateForm(customerComment: ICustomerComment): void {
    console.log('Updated form with comment = ' + customerComment.title);
    this.editForm.patchValue({
      title: customerComment.title,
      comment: customerComment.comment,
    });
    this.rate = customerComment.rating!;
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    // First check if there is a customUserId and bookId
    if (this.book === undefined || this.book === null || this.customUser === undefined || this.customUser === null) {
      // Error
      return;
    }

    this.isSaving = true;
    const customerComment = this.createFromForm();

    // Check if comment exist for the pair (bookId, customUserId)
    this.customerCommentService.findByCustomerIdByBookId(this.customUser.id!, this.book.id!).subscribe(testCustomerComment => {
      if (!testCustomerComment.body || testCustomerComment.body.id === null) {
        // Creating a new comment
        this.subscribeToSaveResponse(this.customerCommentService.create(customerComment));
      } else {
        // Updating an existing comment
        customerComment.id = testCustomerComment.body.id;
        this.subscribeToSaveResponse(this.customerCommentService.update(customerComment));
      }
    });
  }

  private createFromForm(): ICustomerComment {
    return {
      ...new CustomerComment(),
      title: this.editForm.get(['title'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      rating: this.rate,
      commentDate: moment(new Date()),
      customerId: this.customUser!.id,
      bookId: this.book!.id,
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
    const myModal = this.modalService.open(BookCommentUpdateDialogComponent);
    myModal.result
      .then((result: any) => {
        if (result === true) {
          this.previousState();
        } else {
          this.previousState();
        }
      })
      .catch((result: any) => {
        if (result === true) {
          this.previousState();
        } else {
          this.previousState();
        }
      });
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  public onSetRating(rate: number): void {
    this.rate = rate;
  }
}
