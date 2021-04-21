import { CustomerReviewItemComponent } from './../book-details-page/customer-review-item/customer-review-item.component';
import { CustomerReviewListComponent } from './customer-review-list/customer-review-list.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcomBookstoreSharedModule } from '../shared/shared.module';
import { BookDetailsPageComponent } from './book-details-page.component';
import { bookDetailsPageRoute } from './book-details-page.route';
import { EcomBookstoreEntityModule } from '../entities/entity.module';
import { BookDescriptionComponent } from './book-description/book-description.component';

@NgModule({
  imports: [EcomBookstoreSharedModule, EcomBookstoreEntityModule, RouterModule.forChild(bookDetailsPageRoute)],
  declarations: [CustomerReviewListComponent, BookDetailsPageComponent, BookDescriptionComponent, CustomerReviewItemComponent],
  exports: [],
  bootstrap: [BookDescriptionComponent, CustomerReviewListComponent, BookDetailsPageComponent, CustomerReviewItemComponent],
})
export class EcomBookstoreBookDetailsPageModule {}
