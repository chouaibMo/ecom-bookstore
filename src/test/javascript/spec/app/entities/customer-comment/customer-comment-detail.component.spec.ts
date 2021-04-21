import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { CustomerCommentDetailComponent } from 'app/entities/customer-comment/customer-comment-detail.component';
import { CustomerComment } from 'app/shared/model/customer-comment.model';

describe('Component Tests', () => {
  describe('CustomerComment Management Detail Component', () => {
    let comp: CustomerCommentDetailComponent;
    let fixture: ComponentFixture<CustomerCommentDetailComponent>;
    const route = ({ data: of({ customerComment: new CustomerComment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookstoreTestModule],
        declarations: [CustomerCommentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomerCommentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerCommentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customerComment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerComment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
