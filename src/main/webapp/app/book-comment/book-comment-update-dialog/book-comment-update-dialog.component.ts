import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';


/**
 * @author QCR 
 * Modal window display a success message for the addition of the comment by a custom user
 */
@Component({
  selector: 'jhi-book-comment-update-dialog',
  templateUrl: './book-comment-update-dialog.component.html',
  styleUrls: ['./book-comment-update-dialog.component.scss'],
})
export class BookCommentUpdateDialogComponent {
  constructor(public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirm(): void {
    this.activeModal.close();
  }
}
