import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBillingInfo, BillingInfo } from 'app/shared/model/billing-info.model';
import { BillingInfoService } from './billing-info.service';
import { ICustomUser } from 'app/shared/model/custom-user.model';
import { CustomUserService } from 'app/entities/custom-user/custom-user.service';

@Component({
  selector: 'jhi-billing-info-update',
  templateUrl: './billing-info-update.component.html',
})
export class BillingInfoUpdateComponent implements OnInit {
  isSaving = false;
  customusers: ICustomUser[] = [];
  cardExpiryDateDp: any;

  editForm = this.fb.group({
    id: [],
    infoTitle: [],
    cardNumber: [],
    cardExpiryDate: [],
    cryptogram: [],
    email: [],
    billingMethod: [null, [Validators.required]],
    customerId: [],
  });

  constructor(
    protected billingInfoService: BillingInfoService,
    protected customUserService: CustomUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billingInfo }) => {
      this.updateForm(billingInfo);

      this.customUserService.query().subscribe((res: HttpResponse<ICustomUser[]>) => (this.customusers = res.body || []));
    });
  }

  updateForm(billingInfo: IBillingInfo): void {
    this.editForm.patchValue({
      id: billingInfo.id,
      infoTitle: billingInfo.infoTitle,
      cardNumber: billingInfo.cardNumber,
      cardExpiryDate: billingInfo.cardExpiryDate,
      cryptogram: billingInfo.cryptogram,
      email: billingInfo.email,
      billingMethod: billingInfo.billingMethod,
      customerId: billingInfo.customerId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const billingInfo = this.createFromForm();
    if (billingInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.billingInfoService.update(billingInfo));
    } else {
      this.subscribeToSaveResponse(this.billingInfoService.create(billingInfo));
    }
  }

  private createFromForm(): IBillingInfo {
    return {
      ...new BillingInfo(),
      id: this.editForm.get(['id'])!.value,
      infoTitle: this.editForm.get(['infoTitle'])!.value,
      cardNumber: this.editForm.get(['cardNumber'])!.value,
      cardExpiryDate: this.editForm.get(['cardExpiryDate'])!.value,
      cryptogram: this.editForm.get(['cryptogram'])!.value,
      email: this.editForm.get(['email'])!.value,
      billingMethod: this.editForm.get(['billingMethod'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillingInfo>>): void {
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
