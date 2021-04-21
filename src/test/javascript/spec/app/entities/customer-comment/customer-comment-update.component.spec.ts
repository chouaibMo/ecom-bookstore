import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { CustomerCommentUpdateComponent } from 'app/entities/customer-comment/customer-comment-update.component';
import { CustomerCommentService } from 'app/entities/customer-comment/customer-comment.service';
import { CustomerComment } from 'app/shared/model/customer-comment.model';

describe('Component Tests', () => {
  describe('CustomerComment Management Update Component', () => {
    let comp: CustomerCommentUpdateComponent;
    let fixture: ComponentFixture<CustomerCommentUpdateComponent>;
    let service: CustomerCommentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [CustomerCommentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustomerCommentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerCommentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerCommentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerComment(123);
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
        const entity = new CustomerComment();
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
