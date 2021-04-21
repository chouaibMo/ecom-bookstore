import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcomBookstoreSharedModule } from 'app/shared/shared.module';
import { CheckoutComponent } from './checkout.component';
import { checkoutRoute } from './checkout.route';
import { ProductItemComponent } from './product-item/product-item.component';

@NgModule({
  imports: [EcomBookstoreSharedModule, RouterModule.forChild(checkoutRoute)],
  declarations: [CheckoutComponent, ProductItemComponent],
  exports: [CheckoutComponent],
  bootstrap: [CheckoutComponent],
})
export class EcomBookStoreCheckoutModule {}
