import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from './author.service';
import { AuthorDeleteDialogComponent } from './author-delete-dialog.component';

@Component({
  selector: 'jhi-author',
  templateUrl: './author.component.html',
})
export class AuthorComponent implements OnInit, OnDestroy {
  authors?: IAuthor[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected authorService: AuthorService,
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
      this.authorService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAuthor[]>) => (this.authors = res.body || []));
      return;
    }

    this.authorService.query().subscribe((res: HttpResponse<IAuthor[]>) => (this.authors = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAuthors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAuthor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAuthors(): void {
    this.eventSubscriber = this.eventManager.subscribe('authorListModification', () => this.loadAll());
  }

  delete(author: IAuthor): void {
    const modalRef = this.modalService.open(AuthorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.author = author;
  }
}
