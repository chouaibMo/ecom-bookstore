import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OrderLineService } from 'app/entities/order-line/order-line.service';
import { IOrderLine } from 'app/shared/model/order-line.model';
import { IOrder } from 'app/shared/model/order.model';

@Component({
  selector: 'jhi-modal-item-list',
  templateUrl: './modal-item-list.component.html',
  styleUrls: ['./modal-item-list.component.scss'],
})
export class ModalItemListComponent implements OnInit {
  @ViewChild('content') content: any;
  @Input() public order!: IOrder;
  closeResult!: string;
  orderLines: IOrderLine[] | undefined;

  constructor(private activeModal: NgbActiveModal, private orderLineService: OrderLineService) {}

  ngOnInit(): void {
    this.orderLineService.findAllByOrderId(this.order.id!).subscribe((res: HttpResponse<IOrderLine[]>) => {
      if (res.body) {
        this.orderLines = res.body;
      }
    });
  }

  onDismiss(reason: String): void {
    this.activeModal.dismiss(reason);
  }
}
