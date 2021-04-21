import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookstoreTestModule } from '../../../test.module';
import { DiscountComponent } from 'app/entities/discount/discount.component';
import { DiscountService } from 'app/entities/discount/discount.service';
import { Discount } from 'app/shared/model/discount.model';

describe('Component Tests', () => {
  describe('Discount Management Component', () => {
    let comp: DiscountComponent;
    let fixture: ComponentFixture<DiscountComponent>;
    let service: DiscountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [DiscountComponent],
      })
        .overrideTemplate(DiscountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DiscountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiscountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Discount(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.discounts && comp.discounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
