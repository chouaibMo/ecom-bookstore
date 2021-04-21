import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookstoreTestModule } from '../../../test.module';
import { OrderLineComponent } from 'app/entities/order-line/order-line.component';
import { OrderLineService } from 'app/entities/order-line/order-line.service';
import { OrderLine } from 'app/shared/model/order-line.model';

describe('Component Tests', () => {
  describe('OrderLine Management Component', () => {
    let comp: OrderLineComponent;
    let fixture: ComponentFixture<OrderLineComponent>;
    let service: OrderLineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [OrderLineComponent],
      })
        .overrideTemplate(OrderLineComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderLineComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderLineService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderLine(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderLines && comp.orderLines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
