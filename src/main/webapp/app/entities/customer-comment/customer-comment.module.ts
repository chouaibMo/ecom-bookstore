import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcomBookstoreSharedModule } from 'app/shared/shared.module';
import { CustomerCommentComponent } from './customer-comment.component';
import { CustomerCommentDetailComponent } from './customer-comment-detail.component';
import { CustomerCommentUpdateComponent } from './customer-comment-update.component';
import { CustomerCommentDeleteDialogComponent } from './customer-comment-delete-dialog.component';
import { customerCommentRoute } from './customer-comment.route';

@NgModule({
  imports: [EcomBookstoreSharedModule, RouterModule.forChild(customerCommentRoute)],
  declarations: [
    CustomerCommentComponent,
    CustomerCommentDetailComponent,
    CustomerCommentUpdateComponent,
    CustomerCommentDeleteDialogComponent,
  ],
  entryComponents: [CustomerCommentDeleteDialogComponent],
  exports: [CustomerCommentComponent],
})
export class EcomBookstoreCustomerCommentModule {}
