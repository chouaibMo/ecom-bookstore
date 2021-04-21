import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EcomBookstoreSharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { confirmationRoute } from './page-confirmation.route';
import { PageConfirmationComponent } from './page-confirmation.component';

@NgModule({
  imports: [EcomBookstoreSharedModule, CommonModule, RouterModule.forChild(confirmationRoute)],
  declarations: [PageConfirmationComponent],
  exports: [PageConfirmationComponent],
  bootstrap: [PageConfirmationComponent],
})
export class EcomBookStorePageConfirmationModule {}
