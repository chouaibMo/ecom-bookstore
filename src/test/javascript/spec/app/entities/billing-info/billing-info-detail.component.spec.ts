import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { BillingInfoDetailComponent } from 'app/entities/billing-info/billing-info-detail.component';
import { BillingInfo } from 'app/shared/model/billing-info.model';

describe('Component Tests', () => {
  describe('BillingInfo Management Detail Component', () => {
    let comp: BillingInfoDetailComponent;
    let fixture: ComponentFixture<BillingInfoDetailComponent>;
    const route = ({ data: of({ billingInfo: new BillingInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [BillingInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BillingInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillingInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load billingInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.billingInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
