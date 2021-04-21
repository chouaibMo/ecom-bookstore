import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerComment } from 'app/shared/model/customer-comment.model';
import { CustomerCommentService } from './customer-comment.service';

@Component({
  templateUrl: './customer-comment-delete-dialog.component.html',
})
export class CustomerCommentDeleteDialogComponent {
  customerComment?: ICustomerComment;

  constructor(
    protected customerCommentService: CustomerCommentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerCommentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customerCommentListModification');
      this.activeModal.close();
    });
  }
}
