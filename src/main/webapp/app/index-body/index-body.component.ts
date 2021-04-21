import { Component, OnInit } from '@angular/core';
import { IBook } from 'app/shared/model/book.model';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from 'app/entities/book/book.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-index-body',
  templateUrl: './index-body.component.html',
  styleUrls: ['./index-body.component.scss'],
})
export class IndexBodyComponent implements OnInit {
  mostRated: IBook[] | null | undefined;
  mostReviewed: IBook[] | null | undefined;

  constructor(private bookService: BookService, protected activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    // Get most popular books
    this.bookService.getMostRated().subscribe((res: HttpResponse<IBook[]>) => {
      this.mostRated = res.body;
    });

    this.bookService.getMostReviewed().subscribe((res: HttpResponse<IBook[]>) => {
      this.mostReviewed = res.body;
    });
  }

  previousState(): void {
    window.history.back();
  }
  onKeydown(event: KeyboardEvent): void {
    const target = event.target as HTMLTextAreaElement;
    if (event.key === 'Enter' && target.value !== '') {
      this.router.navigate(['/search/books'], { queryParams: { query: target.value } });
    }
  }
}
