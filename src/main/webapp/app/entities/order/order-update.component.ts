import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { ICustomUser } from 'app/shared/model/custom-user.model';
import { CustomUserService } from 'app/entities/custom-user/custom-user.service';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  customusers: ICustomUser[] = [];

  editForm = this.fb.group({
    id: [],
    orderStatus: [null, [Validators.required]],
    orderDetails: [],
    totalPrice: [null, [Validators.required]],
    orderDate: [],
    paymentDate: [],
    customerId: [],
  });

  constructor(
    protected orderService: OrderService,
    protected customUserService: CustomUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (!order.id) {
        const today = moment().startOf('day');
        order.orderDate = today;
        order.paymentDate = today;
      }

      this.updateForm(order);

      this.customUserService.query().subscribe((res: HttpResponse<ICustomUser[]>) => (this.customusers = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      orderStatus: order.orderStatus,
      orderDetails: order.orderDetails,
      totalPrice: order.totalPrice,
      orderDate: order.orderDate ? order.orderDate.format(DATE_TIME_FORMAT) : null,
      paymentDate: order.paymentDate ? order.paymentDate.format(DATE_TIME_FORMAT) : null,
      customerId: order.customerId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      orderStatus: this.editForm.get(['orderStatus'])!.value,
      orderDetails: this.editForm.get(['orderDetails'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      orderDate: this.editForm.get(['orderDate'])!.value ? moment(this.editForm.get(['orderDate'])!.value, DATE_TIME_FORMAT) : undefined,
      paymentDate: this.editForm.get(['paymentDate'])!.value
        ? moment(this.editForm.get(['paymentDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      customerId: this.editForm.get(['customerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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

  trackById(index: number, item: ICustomUser): any {
    return item.id;
  }
}
