import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from './author.service';

@Component({
  templateUrl: './author-delete-dialog.component.html',
})
export class AuthorDeleteDialogComponent {
  author?: IAuthor;

  constructor(protected authorService: AuthorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.authorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('authorListModification');
      this.activeModal.close();
    });
  }
}
