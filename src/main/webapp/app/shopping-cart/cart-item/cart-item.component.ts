import { BookService } from './../../entities/book/book.service';

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IBook } from 'app/shared/model/book.model';
import { IOrderLine } from 'app/shared/model/order-line.model';
import { HttpResponse } from '@angular/common/http';
import { OrderLineService } from 'app/entities/order-line/order-line.service';
import { OrderService } from 'app/entities/order/order.service';
import { IOrder } from 'app/shared/model/order.model';
import { CategoryService } from 'app/entities/category/category.service';
import * as moment from 'moment';

@Component({
  selector: 'jhi-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.scss'],
})
export class CartItemComponent implements OnInit {
  @Input() orderLine!: IOrderLine;
  @Input() userId!: string;
  @Output() onDestroyEvent = new EventEmitter<number>();
  categoryName : string | undefined

  book!: IBook | null;

  constructor(private bookService: BookService,
              private categoryService : CategoryService,
              private orderLineService: OrderLineService,
              private orderService: OrderService) {}

  ngOnInit(): void {
    const bookId: number = this.orderLine.bookId!;
    this.bookService.find(bookId).subscribe((res: HttpResponse<IBook>) => {
      this.book = res.body;
    });

    if (this.book?.categoryId){
      this.categoryService.find(this.book.categoryId).subscribe(category => {
        if (category) {
          this.categoryName = category.body?.name;
        }
      });
    }
  }

  onRemoveItem(): void {
    const idToRemove: number = this.orderLine.id!;
    const orderLinePrice = this.orderLine.price!;

    // get the cart that contains the item
    this.orderService.findCart(Number(this.userId)).subscribe((response: HttpResponse<IOrder>) => {
      const cart = response.body!;

      // delete the order line
      this.orderLineService.delete(idToRemove).subscribe((res: HttpResponse<IOrderLine>) => {
        const deletedOrderLine: IOrderLine = res.body!;

        if (deletedOrderLine == null) {
          // if delete successful, the update cart total price
          cart.totalPrice = (cart.totalPrice! * 100 - orderLinePrice * 100) / 100;
          cart.orderDate = moment();
          this.orderService.update(cart).subscribe(() => {});
        }
        this.onDestroyEvent.emit(idToRemove);
      });
    });
  }
}
