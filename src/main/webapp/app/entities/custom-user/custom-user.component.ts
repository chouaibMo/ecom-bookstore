import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomUser } from 'app/shared/model/custom-user.model';
import { CustomUserService } from './custom-user.service';
import { CustomUserDeleteDialogComponent } from './custom-user-delete-dialog.component';

@Component({
  selector: 'jhi-custom-user',
  templateUrl: './custom-user.component.html',
})
export class CustomUserComponent implements OnInit, OnDestroy {
  customUsers?: ICustomUser[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected customUserService: CustomUserService,
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
      this.customUserService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICustomUser[]>) => (this.customUsers = res.body || []));
      return;
    }

    this.customUserService.query().subscribe((res: HttpResponse<ICustomUser[]>) => (this.customUsers = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCustomUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICustomUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCustomUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('customUserListModification', () => this.loadAll());
  }

  delete(customUser: ICustomUser): void {
    const modalRef = this.modalService.open(CustomUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customUser = customUser;
  }
}
