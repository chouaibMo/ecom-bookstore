import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderLine } from 'app/shared/model/order-line.model';
import { OrderLineService } from './order-line.service';

@Component({
  templateUrl: './order-line-delete-dialog.component.html',
})
export class OrderLineDeleteDialogComponent {
  orderLine?: IOrderLine;

  constructor(protected orderLineService: OrderLineService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderLineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderLineListModification');
      this.activeModal.close();
    });
  }
}
