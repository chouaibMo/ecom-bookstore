import { ICategory } from './../../shared/model/category.model';
import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { BookService } from 'app/entities/book/book.service';
import { CategoryService } from 'app/entities/category/category.service';
import { IBook } from 'app/shared/model/book.model';
import { IOrderLine } from 'app/shared/model/order-line.model';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-order-item-card',
  templateUrl: './order-item-card.component.html',
  styleUrls: ['./order-item-card.component.scss'],
})
export class OrderItemCardComponent implements OnInit {
  @Input() orderLine: IOrderLine | undefined;
  book: IBook | undefined;
  category: ICategory | undefined;
  constructor(private router: Router, private bookService: BookService, private categoryService: CategoryService) {}

  ngOnInit(): void {
    if (this.orderLine) {
      this.bookService.find(this.orderLine.bookId).subscribe((res: HttpResponse<IBook>) => {
        if (res.body) {
          this.book = res.body;
          this.categoryService.find(this.book.categoryId!).subscribe((category: HttpResponse<ICategory>) => {
            if (category.body) {
              this.category = category.body;
            }
          });
        }
      });
    }
  }
}
