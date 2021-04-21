import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBillingInfo } from 'app/shared/model/billing-info.model';
import { BillingInfoService } from './billing-info.service';
import { BillingInfoDeleteDialogComponent } from './billing-info-delete-dialog.component';

@Component({
  selector: 'jhi-billing-info',
  templateUrl: './billing-info.component.html',
})
export class BillingInfoComponent implements OnInit, OnDestroy {
  billingInfos?: IBillingInfo[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected billingInfoService: BillingInfoService,
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
      this.billingInfoService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBillingInfo[]>) => (this.billingInfos = res.body || []));
      return;
    }

    this.billingInfoService.query().subscribe((res: HttpResponse<IBillingInfo[]>) => (this.billingInfos = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBillingInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBillingInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBillingInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('billingInfoListModification', () => this.loadAll());
  }

  delete(billingInfo: IBillingInfo): void {
    const modalRef = this.modalService.open(BillingInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.billingInfo = billingInfo;
  }
}
