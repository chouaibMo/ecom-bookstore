import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.EcomBookstoreCategoryModule),
      },
      {
        path: 'author',
        loadChildren: () => import('./author/author.module').then(m => m.EcomBookstoreAuthorModule),
      },
      {
        path: 'book',
        loadChildren: () => import('./book/book.module').then(m => m.EcomBookstoreBookModule),
      },
      {
        path: 'discount',
        loadChildren: () => import('./discount/discount.module').then(m => m.EcomBookstoreDiscountModule),
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.EcomBookstoreOrderModule),
      },
      {
        path: 'order-line',
        loadChildren: () => import('./order-line/order-line.module').then(m => m.EcomBookstoreOrderLineModule),
      },
      {
        path: 'customer-comment',
        loadChildren: () => import('./customer-comment/customer-comment.module').then(m => m.EcomBookstoreCustomerCommentModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.EcomBookstoreAddressModule),
      },
      {
        path: 'billing-info',
        loadChildren: () => import('./billing-info/billing-info.module').then(m => m.EcomBookstoreBillingInfoModule),
      },
      {
        path: 'custom-user',
        loadChildren: () => import('./custom-user/custom-user.module').then(m => m.EcomBookstoreCustomUserModule),
      },
      {
        path: 'billing-info',
        loadChildren: () => import('./billing-info/billing-info.module').then(m => m.EcomBookstoreBillingInfoModule),
      },
      {
        path: 'custom-user',
        loadChildren: () => import('./custom-user/custom-user.module').then(m => m.EcomBookstoreCustomUserModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EcomBookstoreEntityModule {}
