import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { AddressDeleteDialogComponent } from './address-delete-dialog.component';

@Component({
  selector: 'jhi-address',
  templateUrl: './address.component.html',
})
export class AddressComponent implements OnInit, OnDestroy {
  addresses?: IAddress[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected addressService: AddressService,
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
      this.addressService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAddress[]>) => (this.addresses = res.body || []));
      return;
    }

    this.addressService.query().subscribe((res: HttpResponse<IAddress[]>) => (this.addresses = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAddresses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAddress): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAddresses(): void {
    this.eventSubscriber = this.eventManager.subscribe('addressListModification', () => this.loadAll());
  }

  delete(address: IAddress): void {
    const modalRef = this.modalService.open(AddressDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.address = address;
  }
}
