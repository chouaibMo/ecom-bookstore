import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomUser } from 'app/shared/model/custom-user.model';
import { CustomUserService } from './custom-user.service';

@Component({
  templateUrl: './custom-user-delete-dialog.component.html',
})
export class CustomUserDeleteDialogComponent {
  customUser?: ICustomUser;

  constructor(
    protected customUserService: CustomUserService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customUserListModification');
      this.activeModal.close();
    });
  }
}
