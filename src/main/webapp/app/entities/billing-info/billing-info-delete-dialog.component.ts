import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingInfo } from 'app/shared/model/billing-info.model';
import { BillingInfoService } from './billing-info.service';

@Component({
  templateUrl: './billing-info-delete-dialog.component.html',
})
export class BillingInfoDeleteDialogComponent {
  billingInfo?: IBillingInfo;

  constructor(
    protected billingInfoService: BillingInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.billingInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('billingInfoListModification');
      this.activeModal.close();
    });
  }
}
