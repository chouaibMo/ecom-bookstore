import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { OrderLineUpdateComponent } from 'app/entities/order-line/order-line-update.component';
import { OrderLineService } from 'app/entities/order-line/order-line.service';
import { OrderLine } from 'app/shared/model/order-line.model';

describe('Component Tests', () => {
  describe('OrderLine Management Update Component', () => {
    let comp: OrderLineUpdateComponent;
    let fixture: ComponentFixture<OrderLineUpdateComponent>;
    let service: OrderLineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [OrderLineUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrderLineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderLineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderLineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderLine(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderLine();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
