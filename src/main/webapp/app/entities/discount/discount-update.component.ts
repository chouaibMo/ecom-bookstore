import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDiscount, Discount } from 'app/shared/model/discount.model';
import { DiscountService } from './discount.service';

@Component({
  selector: 'jhi-discount-update',
  templateUrl: './discount-update.component.html',
})
export class DiscountUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    startDate: [null, [Validators.required]],
    endDate: [],
    description: [],
    discountRate: [null, [Validators.required]],
  });

  constructor(protected discountService: DiscountService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discount }) => {
      this.updateForm(discount);
    });
  }

  updateForm(discount: IDiscount): void {
    this.editForm.patchValue({
      id: discount.id,
      startDate: discount.startDate,
      endDate: discount.endDate,
      description: discount.description,
      discountRate: discount.discountRate,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const discount = this.createFromForm();
    if (discount.id !== undefined) {
      this.subscribeToSaveResponse(this.discountService.update(discount));
    } else {
      this.subscribeToSaveResponse(this.discountService.create(discount));
    }
  }

  private createFromForm(): IDiscount {
    return {
      ...new Discount(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      description: this.editForm.get(['description'])!.value,
      discountRate: this.editForm.get(['discountRate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscount>>): void {
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
}
