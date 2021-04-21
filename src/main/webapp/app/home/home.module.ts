import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcomBookstoreSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { EcomBookstoreIndexBodyModule } from 'app/index-body/index-body.module';

@NgModule({
  imports: [EcomBookstoreSharedModule, RouterModule.forChild([HOME_ROUTE]), EcomBookstoreIndexBodyModule],
  declarations: [HomeComponent],
})
export class EcomBookstoreHomeModule {}
