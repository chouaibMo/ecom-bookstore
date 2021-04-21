import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderLineService } from 'app/entities/order-line/order-line.service';
import { IOrderLine } from 'app/shared/model/order-line.model';
import { IOrder } from 'app/shared/model/order.model';
/**
 *  Page affichant le panier d'un utilisateur
 */
@Component({
  selector: 'jhi-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss'],
})
export class ShoppingCartComponent implements OnInit {
  order: IOrder | null | undefined;
  orderLines?: IOrderLine[] | null;
  userId!: string;
  test: any;

  constructor(private orderLineService: OrderLineService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.userId = this.activatedRoute.snapshot.params['id'];
    this.activatedRoute.data.subscribe(({ order }) => {
      this.order = order;
      if (this.order !== null && this.order !== undefined) {
        this.orderLineService.findAllByOrderId(order.id).subscribe((res: HttpResponse<IOrderLine[]>) => {
          this.orderLines = res.body;
        });
      }
    });
  }

  /**
   * @author daoliangshu
   *
   * Evenement qui est declenché par le child component (cart-item), lorsque
   * l'utilisateur enleve une entrée du panier.
   *
   * @param orderLineId
   */
  onLineRemoved(orderLineId: number): void {
    for (let i = this.orderLines!.length - 1; i >= 0; i--) {
      if (this.orderLines![i].id! === orderLineId) {
        this.orderLines!.splice(i, 1);
      }
    }
  }

  previousState(): void {
    window.history.back();
  }
}
