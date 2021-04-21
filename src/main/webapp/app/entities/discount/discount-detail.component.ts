import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiscount } from 'app/shared/model/discount.model';

@Component({
  selector: 'jhi-discount-detail',
  templateUrl: './discount-detail.component.html',
})
export class DiscountDetailComponent implements OnInit {
  discount: IDiscount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discount }) => (this.discount = discount));
  }

  previousState(): void {
    window.history.back();
  }
}
