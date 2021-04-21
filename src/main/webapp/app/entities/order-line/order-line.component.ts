import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderLine } from 'app/shared/model/order-line.model';
import { OrderLineService } from './order-line.service';
import { OrderLineDeleteDialogComponent } from './order-line-delete-dialog.component';

@Component({
  selector: 'jhi-order-line',
  templateUrl: './order-line.component.html',
})
export class OrderLineComponent implements OnInit, OnDestroy {
  orderLines?: IOrderLine[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected orderLineService: OrderLineService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.orderLineService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IOrderLine[]>) => (this.orderLines = res.body || []));
      return;
    }

    this.orderLineService.query().subscribe((res: HttpResponse<IOrderLine[]>) => (this.orderLines = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrderLines();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrderLine): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrderLines(): void {
    this.eventSubscriber = this.eventManager.subscribe('orderLineListModification', () => this.loadAll());
  }

  delete(orderLine: IOrderLine): void {
    const modalRef = this.modalService.open(OrderLineDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderLine = orderLine;
  }
}
