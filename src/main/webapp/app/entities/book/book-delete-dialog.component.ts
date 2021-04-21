import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBook } from 'app/shared/model/book.model';
import { BookService } from './book.service';

@Component({
  templateUrl: './book-delete-dialog.component.html',
})
export class BookDeleteDialogComponent {
  book?: IBook;

  constructor(protected bookService: BookService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bookListModification');
      this.activeModal.close();
    });
  }
}
