import { BookCommentComponent } from './book-comment.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EcomBookstoreSharedModule } from '../shared/shared.module';
import { bookCommentRoute } from './book-comment.route';
import { EcomBookstoreEntityModule } from '../entities/entity.module';
import { EcomBookStoreSearchPageModule } from 'app/search-page/search-page.module';
import { BookCommentUpdateDialogComponent } from './book-comment-update-dialog/book-comment-update-dialog.component';

@NgModule({
  imports: [EcomBookstoreSharedModule, EcomBookstoreEntityModule, EcomBookStoreSearchPageModule, RouterModule.forChild(bookCommentRoute)],
  declarations: [BookCommentComponent, BookCommentUpdateDialogComponent],
  exports: [],
  entryComponents: [BookCommentUpdateDialogComponent],
  bootstrap: [BookCommentComponent, BookCommentUpdateDialogComponent],
})
export class EcomBookstoreBookCommentModule {}
