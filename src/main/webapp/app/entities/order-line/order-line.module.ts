import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcomBookstoreSharedModule } from 'app/shared/shared.module';
import { OrderLineComponent } from './order-line.component';
import { OrderLineDetailComponent } from './order-line-detail.component';
import { OrderLineUpdateComponent } from './order-line-update.component';
import { OrderLineDeleteDialogComponent } from './order-line-delete-dialog.component';
import { orderLineRoute } from './order-line.route';

@NgModule({
  imports: [EcomBookstoreSharedModule, RouterModule.forChild(orderLineRoute)],
  declarations: [OrderLineComponent, OrderLineDetailComponent, OrderLineUpdateComponent, OrderLineDeleteDialogComponent],
  entryComponents: [OrderLineDeleteDialogComponent],
})
export class EcomBookstoreOrderLineModule {}
