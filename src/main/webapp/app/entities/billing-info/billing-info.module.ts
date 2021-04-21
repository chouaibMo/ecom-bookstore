import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcomBookstoreSharedModule } from 'app/shared/shared.module';
import { BillingInfoComponent } from './billing-info.component';
import { BillingInfoDetailComponent } from './billing-info-detail.component';
import { BillingInfoUpdateComponent } from './billing-info-update.component';
import { BillingInfoDeleteDialogComponent } from './billing-info-delete-dialog.component';
import { billingInfoRoute } from './billing-info.route';

@NgModule({
  imports: [EcomBookstoreSharedModule, RouterModule.forChild(billingInfoRoute)],
  declarations: [BillingInfoComponent, BillingInfoDetailComponent, BillingInfoUpdateComponent, BillingInfoDeleteDialogComponent],
  entryComponents: [BillingInfoDeleteDialogComponent],
})
export class EcomBookstoreBillingInfoModule {}
