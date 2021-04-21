import { Component, Input, OnInit } from '@angular/core';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';
import { OrderService } from 'app/entities/order/order.service';
import { OrderLineService } from 'app/entities/order-line/order-line.service';
import { HttpResponse } from '@angular/common/http';
import { IOrder, Order } from 'app/shared/model/order.model';
import { IOrderLine, OrderLine } from 'app/shared/model/order-line.model';

@Component({
  selector: 'jhi-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.scss'],
})
export class BookCardComponent implements OnInit {
  @Input() book!: IBook | null;

  constructor(private bookService: BookService, private orderService: OrderService, private orderLineService: OrderLineService) {}

  ngOnInit(): void {
    this.bookService.find(this.book!.id).subscribe((res: HttpResponse<IBook>) => {
      this.book = res.body;
    });
  }

  /**
   * @author QCR
   * Event triggered when the use click on the AddToCart button from description page
   */
  onAddToCart(): void {
    // TODO: Get the cart id
    // TODO: Create a orderLine attached to the Order
    this.orderService.findCart(5).subscribe((res: HttpResponse<IOrder>) => {
      const myCart: Order | null = res.body;

      if (myCart != null) {
        const orderLine: IOrderLine = new OrderLine();
        orderLine.bookId = this.book!.id;
        orderLine.quantity = 1;
        orderLine.price = this.book!.price;
        orderLine.orderId = myCart.id;
        alert('Add book id : ' + this.book!.id + ' to cart');
        this.orderLineService.create(orderLine).subscribe((res2: HttpResponse<IOrder>) => {
          const persistentLine: IOrderLine = res2.body!;
          alert('Added to cart : ' + persistentLine.id);
        });
      }
    });
  }
}
