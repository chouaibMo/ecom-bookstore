import { shoppingCartRoute } from './shopping-cart.route';
import { ShoppingCartComponent } from './shopping-cart.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EcomBookstoreSharedModule } from 'app/shared/shared.module';
import { CartItemComponent } from './cart-item/cart-item.component';

@NgModule({
  imports: [EcomBookstoreSharedModule, RouterModule.forChild(shoppingCartRoute)],
  declarations: [ShoppingCartComponent, CartItemComponent],
  exports: [ShoppingCartComponent, CartItemComponent],
  bootstrap: [ShoppingCartComponent, CartItemComponent],
})
export class EcomBookstoreShoppingCartModule {}
