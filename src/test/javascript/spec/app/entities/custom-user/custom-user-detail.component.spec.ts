import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { CustomUserDetailComponent } from 'app/entities/custom-user/custom-user-detail.component';
import { CustomUser } from 'app/shared/model/custom-user.model';

describe('Component Tests', () => {
  describe('CustomUser Management Detail Component', () => {
    let comp: CustomUserDetailComponent;
    let fixture: ComponentFixture<CustomUserDetailComponent>;
    const route = ({ data: of({ customUser: new CustomUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [CustomUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
