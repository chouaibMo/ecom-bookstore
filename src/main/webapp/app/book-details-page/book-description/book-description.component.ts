import { Component, Input, OnInit } from '@angular/core';
import { IBook } from 'app/shared/model/book.model';
import { HttpResponse } from '@angular/common/http';
import { IOrder } from 'app/shared/model/order.model';
import { IOrderLine, OrderLine } from 'app/shared/model/order-line.model';
import { OrderService } from 'app/entities/order/order.service';
import { OrderLineService } from 'app/entities/order-line/order-line.service';
import { CustomUserService } from 'app/entities/custom-user/custom-user.service';
import { AccountService } from 'app/core/auth/account.service';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from 'app/entities/category/category.service';
import { ICustomerComment } from 'app/shared/model/customer-comment.model';
import * as moment from 'moment';

@Component({
  selector: 'jhi-book-description',
  templateUrl: './book-description.component.html',
  styleUrls: ['./book-description.component.scss'],
})
export class BookDescriptionComponent implements OnInit {
  @Input() book: IBook | null | undefined;
  @Input() reviews: ICustomerComment[] | null | undefined;
  account!: Account;
  userId!: number;
  categoryName: string | undefined ;
  starsChecked : number | undefined
  starsBlack : number | undefined

  constructor(
    private customUserService: CustomUserService,
    private accountService: AccountService,
    private orderService: OrderService,
    private categoryService: CategoryService,
    private orderLineService: OrderLineService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    if(this.book?.rating){
      this.starsChecked = Math.floor(this.book.rating)
      this.starsBlack = 5 - this.starsChecked
    }

    this.accountService.identity().subscribe(account => {
      if (account) {
        this.customUserService.findByUserId(account.id).subscribe(customUser => {
          if (customUser.body?.id) {
            this.userId = customUser.body?.id;
          }
        });
      }
    });
    if (this.book?.categoryId){
      this.categoryService.find(this.book.categoryId).subscribe(category => {
        if (category) {
          this.categoryName = category.body?.name;
        }
      });
    }
  }


  counter(i: number | undefined): Array<any>{
    return new Array(i);
  }

  /**
   * @author Emez
   * Event triggered when the user clicks on the AddToCart button from description page
   *
   * adds an order line of a given quantity to the user's cart or updates the quantity of an existing order line in the user's cart
   * and updates the price of the shopping cart after either operation
   */
  addToCart(qty: string): void {
    const quantity = Number.parseInt(qty, 10);
    if (quantity <= 0) {
      alert('Enter a valid quantity');
      return;
    }

    this.orderService.findCart(this.userId).subscribe(
      (res: HttpResponse<IOrder>) => {
        const myCart: IOrder | null = res.body;

        if (myCart != null) {
          myCart.orderDate = moment();
          // custom user has an Order IN_CART
          // create the OrderLine & initialise some attributes
          const orderLine: IOrderLine = new OrderLine();
          orderLine.orderId = myCart.id;
          orderLine.bookId = this.book!.id;
          orderLine.quantity = quantity;
          orderLine.price = orderLine.quantity * this.book!.price!;

          // get the order lines in myCart
          this.orderLineService.findAllByOrderId(myCart.id!).subscribe((lines: HttpResponse<IOrderLine[]>) => {
            const myCartOrderLines: IOrderLine[] = lines.body!;

            if (this.orderLineAlreadyInCart(orderLine, myCartOrderLines)) {
              // then update the OrderLine
              const existingOrderLine = this.getExistingOrderLine(orderLine, myCartOrderLines);
              if (existingOrderLine != null) {
                const existingOrderLinePrice = existingOrderLine.price!;
                orderLine.id = existingOrderLine.id;
                this.orderLineService.update(orderLine).subscribe((updatedOrderLine: HttpResponse<IOrderLine>) => {
                  const persistentLine: IOrderLine = updatedOrderLine.body!;

                  myCart.totalPrice! = (myCart.totalPrice! * 100 - existingOrderLinePrice * 100 + persistentLine.price! * 100) / 100;
                  this.orderService.update(myCart).subscribe(() => {});
                });
              }
            } else {
              // then create the OrderLine
              this.orderLineService.create(orderLine).subscribe((addedOrderLine: HttpResponse<IOrderLine>) => {
                const persistentLine: IOrderLine = addedOrderLine.body!;
                alert('Item added to your cart successfully');

                // update the price of the shopping cart
                // multiply by 100, and finally divide by 100 to handle float
                myCart.totalPrice! = (myCart.totalPrice! * 100 + persistentLine.price! * 100) / 100;
                this.orderService.update(myCart).subscribe(() => {});
              });
            }
          });
        }
      },
      () => {
        console.log('Error in add to cart subscribe');
      },
      () => {
        console.log('Add to cart complete'); /* this.updateTotalPriceOfOrder(3); */
      }
    );
  }

  /**
   * @author Emez
   * @param orderLine: the OrderLine to be added to cart
   * @param orderLines: the OrderLines already in the shopping cart
   * @return true if the book in orderLine is already in one of the orderLines
   * @return false otherwise
   */
  orderLineAlreadyInCart(orderLine: IOrderLine, orderLines: IOrderLine[]): boolean {
    for (let i = 0, len = orderLines.length; i < len; i++) {
      if (orderLine.bookId === orderLines[i].bookId) return true;
    }
    return false;
  }

  /**
   * @author Emez
   * @param orderLine: the OrderLine to be updated in the cart
   * @param orderLines: the OrderLines already in the shopping cart
   * @return the orderLine with the same book as the orderLine to be updated or null if not found
   */
  getExistingOrderLine(orderLine: IOrderLine, orderLines: IOrderLine[]): IOrderLine | null {
    for (let i = 0, len = orderLines.length; i < len; i++) {
      if (orderLine.bookId === orderLines[i].bookId) return orderLines[i];
    }
    return null;
  }
}
