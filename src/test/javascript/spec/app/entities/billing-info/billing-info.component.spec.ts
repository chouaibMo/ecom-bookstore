import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookstoreTestModule } from '../../../test.module';
import { BillingInfoComponent } from 'app/entities/billing-info/billing-info.component';
import { BillingInfoService } from 'app/entities/billing-info/billing-info.service';
import { BillingInfo } from 'app/shared/model/billing-info.model';

describe('Component Tests', () => {
  describe('BillingInfo Management Component', () => {
    let comp: BillingInfoComponent;
    let fixture: ComponentFixture<BillingInfoComponent>;
    let service: BillingInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [BillingInfoComponent],
      })
        .overrideTemplate(BillingInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BillingInfo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.billingInfos && comp.billingInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
