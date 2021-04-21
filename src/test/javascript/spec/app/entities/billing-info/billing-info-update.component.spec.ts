import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { BillingInfoUpdateComponent } from 'app/entities/billing-info/billing-info-update.component';
import { BillingInfoService } from 'app/entities/billing-info/billing-info.service';
import { BillingInfo } from 'app/shared/model/billing-info.model';

describe('Component Tests', () => {
  describe('BillingInfo Management Update Component', () => {
    let comp: BillingInfoUpdateComponent;
    let fixture: ComponentFixture<BillingInfoUpdateComponent>;
    let service: BillingInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [BillingInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BillingInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BillingInfo(123);
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
        const entity = new BillingInfo();
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
