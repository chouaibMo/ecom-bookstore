import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICustomUser, CustomUser } from 'app/shared/model/custom-user.model';
import { CustomUserService } from './custom-user.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

type SelectableEntity = IUser | IOrder;

@Component({
  selector: 'jhi-custom-user-update',
  templateUrl: './custom-user-update.component.html',
})
export class CustomUserUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  carts: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    phoneNumber: [],
    userId: [],
    cartId: [],
  });

  constructor(
    protected customUserService: CustomUserService,
    protected userService: UserService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customUser }) => {
      this.updateForm(customUser);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.orderService
        .query({ filter: 'customuser-is-null' })
        .pipe(
          map((res: HttpResponse<IOrder[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOrder[]) => {
          if (!customUser.cartId) {
            this.carts = resBody;
          } else {
            this.orderService
              .find(customUser.cartId)
              .pipe(
                map((subRes: HttpResponse<IOrder>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOrder[]) => (this.carts = concatRes));
          }
        });
    });
  }

  updateForm(customUser: ICustomUser): void {
    this.editForm.patchValue({
      id: customUser.id,
      phoneNumber: customUser.phoneNumber,
      userId: customUser.userId,
      cartId: customUser.cartId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customUser = this.createFromForm();
    if (customUser.id !== undefined) {
      this.subscribeToSaveResponse(this.customUserService.update(customUser));
    } else {
      this.subscribeToSaveResponse(this.customUserService.create(customUser));
    }
  }

  private createFromForm(): ICustomUser {
    return {
      ...new CustomUser(),
      id: this.editForm.get(['id'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      cartId: this.editForm.get(['cartId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomUser>>): void {
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
