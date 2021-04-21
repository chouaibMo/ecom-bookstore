import { orderHistoryRoute } from './order-history.route';
import { OrderHistoryComponent } from './order-history.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EcomBookstoreSharedModule } from 'app/shared/shared.module';
import { OrderCardComponent } from './order-card/order-card.component';
import { ModalItemListComponent } from './modal-item-list/modal-item-list.component';
import { OrderItemCardComponent } from './order-item-card/order-item-card.component';

@NgModule({
  imports: [EcomBookstoreSharedModule, RouterModule.forChild(orderHistoryRoute)],
  declarations: [OrderHistoryComponent, OrderCardComponent, ModalItemListComponent, OrderItemCardComponent],
  exports: [OrderHistoryComponent],
  entryComponents: [ModalItemListComponent],
  bootstrap: [OrderHistoryComponent, ModalItemListComponent, OrderItemCardComponent],
})
export class EcomBookstoreOrderHistoryModule {}
