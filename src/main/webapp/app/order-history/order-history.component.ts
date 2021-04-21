import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { User } from 'app/core/user/user.model';
import { Account } from 'app/core/user/account.model';
import { UserService } from 'app/core/user/user.service';
import { CustomUserService } from 'app/entities/custom-user/custom-user.service';
import { OrderService } from 'app/entities/order/order.service';
import { CustomUser } from 'app/shared/model/custom-user.model';
import { IOrder } from 'app/shared/model/order.model';

@Component({
  selector: 'jhi-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.scss'],
})
export class OrderHistoryComponent implements OnInit {
  account!: Account;
  user!: User;
  customUser!: CustomUser;
  orders: IOrder[] | undefined;
  visibility = false;
  constructor(
    private accountService: AccountService,
    private userService: UserService,
    private customUserService: CustomUserService,
    private orderService: OrderService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
        this.userService.find(account.login).subscribe(user => {
          if (user) {
            this.user = user;
            this.customUserService.findByUserId(this.user.id).subscribe(customUsers => {
              if (customUsers.body) {
                this.customUser = customUsers.body;
                this.orderService.findOrderHistory(this.customUser.id!).subscribe(orders => {
                  if (orders.body) {
                    this.orders = orders.body;
                  }
                });
              }
            });
          }
        });
      }
    });
  }
}
