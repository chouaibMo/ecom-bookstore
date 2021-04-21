import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcomBookstoreIndexBodyModule } from 'app/index-body/index-body.module';
import { SearchPageComponent } from './search-page.component';
import { searchPageRoute } from './search-page.route';

import { EcomBookstoreSharedModule } from '../shared/shared.module';
import { BookCardComponent } from './book-card/book-card.component';
import { SearchFilterComponent } from './search-filter/search-filter.component';
import { NgxSliderModule } from '@angular-slider/ngx-slider';

@NgModule({
  imports: [EcomBookstoreSharedModule, NgxSliderModule, EcomBookstoreIndexBodyModule, RouterModule.forChild(searchPageRoute)],
  declarations: [SearchPageComponent, BookCardComponent, SearchFilterComponent],
  exports: [SearchPageComponent, BookCardComponent],
  bootstrap: [SearchPageComponent, BookCardComponent, SearchFilterComponent],
})
export class EcomBookStoreSearchPageModule {}
