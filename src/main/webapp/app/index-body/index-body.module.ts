import { IndexBodyComponent } from './index-body.component';
import { indexBodyRoute } from './index-body.route';

import { NgModule } from '@angular/core';

import { RouterModule } from '@angular/router';
import { EcomBookstoreSharedModule } from '../shared/shared.module';
import { BookItemComponent } from './book-item/book-item.component';

@NgModule({
  imports: [EcomBookstoreSharedModule, RouterModule.forChild(indexBodyRoute)],
  declarations: [BookItemComponent, IndexBodyComponent],
  exports: [BookItemComponent, IndexBodyComponent],
  bootstrap: [BookItemComponent, IndexBodyComponent],
})
export class EcomBookstoreIndexBodyModule {}
