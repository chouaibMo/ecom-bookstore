import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { OrderLineService } from 'app/entities/order-line/order-line.service';
import { ActivatedRoute, Router } from '@angular/router';
import { IOrder } from 'app/shared/model/order.model';
import { IOrderLine } from 'app/shared/model/order-line.model';
import { HttpResponse } from '@angular/common/http';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { UserService } from 'app/core/user/user.service';
import { IUser } from 'app/core/user/user.model';
import { OrderService } from 'app/entities/order/order.service';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';
import { CustomUserService } from 'app/entities/custom-user/custom-user.service';
import * as moment from 'moment';

@Component({
  selector: 'jhi-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
})
export class CheckoutComponent implements OnInit {
  user!: IUser;
  account!: Account;
  order!: IOrder;
  orderLines?: IOrderLine[] | null;

  checkoutError = false;
  saveAddress = false;

  checkoutForm = this.formBuilder.group({
    firstName: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
    lastName: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    address: ['', [Validators.required]],
    country: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
    city: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
    zip: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(5)]],
    cardName: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
    cardNo: ['', [Validators.required, Validators.minLength(16), Validators.pattern('^[0-9]+$')]],
    month: ['', [Validators.required]],
    year: ['', [Validators.required]],
    cvv: ['', [Validators.required, Validators.minLength(3), Validators.pattern('^[0-9]+$')]],
  });

  constructor(
    private customUserService: CustomUserService,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private accountService: AccountService,
    private orderService: OrderService,
    private orderLineService: OrderLineService,
    protected activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Retrieving cart content :
    this.activatedRoute.data.subscribe(({ order }) => {
      this.order = order;
      if (this.order !== null && this.order !== undefined) {
        this.orderLineService.findAllByOrderId(order.id).subscribe((res: HttpResponse<IOrderLine[]>) => {
          this.orderLines = res.body;
        });
      }
    });

    // Retrieving account information :
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
        this.userService.find(account.login).subscribe(res => {
          this.user = res;
          this.checkoutForm.get('firstName')?.setValue(this.user.firstName);
          this.checkoutForm.get('lastName')?.setValue(this.user.lastName);
          this.checkoutForm.get('email')?.setValue(this.user.email);
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  processPayment(): void {
    console.log(this.saveAddress);
    console.log(this.checkoutForm?.value);
    if (this.checkoutForm.invalid) {
      this.checkoutError = true;
      return;
    }
    this.checkoutError = false;
    const copy = this.order;
    copy.orderStatus = OrderStatus.CONFIRMED;
    copy.paymentDate = moment();
    this.orderService.update(copy).subscribe(() => {
      this.router.navigate(['/confirmation/' + this.order?.id]);
    });
  }
  onCartClick(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.customUserService.findByUserId(account.id).subscribe(customUser => {
          this.router.navigate(['/shop/cart/' + customUser.body?.id]);
        });
      }
    });
  }
}
