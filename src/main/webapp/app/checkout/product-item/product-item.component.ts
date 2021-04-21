import { Component, Input, OnInit } from '@angular/core';
import { IOrderLine } from 'app/shared/model/order-line.model';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.scss'],
})
export class ProductItemComponent implements OnInit {
  @Input() orderLine!: IOrderLine;
  book?: IBook | null;

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    const bookId: number = this.orderLine.bookId!;
    this.bookService.find(bookId).subscribe((res: HttpResponse<IBook>) => {
      this.book = res.body;
    });
  }
}
