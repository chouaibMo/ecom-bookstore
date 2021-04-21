import { ModalItemListComponent } from './../modal-item-list/modal-item-list.component';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { IOrder } from 'app/shared/model/order.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-order-card',
  templateUrl: './order-card.component.html',
  styleUrls: ['./order-card.component.scss'],
})
export class OrderCardComponent implements OnInit, OnDestroy {
  @Input()
  public order!: IOrder;
  private modalRef: NgbModalRef | undefined | null;

  constructor(private modalService: NgbModal) {
    // Usually empty
  }

  ngOnInit(): void {}

  onModalRequest(): void {
    this.modalRef = this.modalService.open(ModalItemListComponent);
    this.modalRef.componentInstance.order = this.order;
    /*
    this.modalRef.result.then((_result) => {
      this.modalRef?.componentInstance.dismissAll();
    }).catch( (_result) => {
      this.modalRef?.componentInstance.dismissAll();
    }); 
    */
  }

  ngOnDestroy(): void {
    if (this.modalRef) {
      this.modalRef.dismiss();
    }
  }
}
