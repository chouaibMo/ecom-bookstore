import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { CustomUserUpdateComponent } from 'app/entities/custom-user/custom-user-update.component';
import { CustomUserService } from 'app/entities/custom-user/custom-user.service';
import { CustomUser } from 'app/shared/model/custom-user.model';

describe('Component Tests', () => {
  describe('CustomUser Management Update Component', () => {
    let comp: CustomUserUpdateComponent;
    let fixture: ComponentFixture<CustomUserUpdateComponent>;
    let service: CustomUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [CustomUserUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustomUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomUser(123);
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
        const entity = new CustomUser();
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
