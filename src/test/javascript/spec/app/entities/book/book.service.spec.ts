import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BookService } from 'app/entities/book/book.service';
import { IBook, Book } from 'app/shared/model/book.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { BookType } from 'app/shared/model/enumerations/book-type.model';

describe('Service Tests', () => {
  describe('Book Service', () => {
    let injector: TestBed;
    let service: BookService;
    let httpMock: HttpTestingController;
    let elemDefault: IBook;
    let expectedResult: IBook | IBook[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BookService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Book(0, 'AAAAAAA', 0, 0, 'AAAAAAA', Language.FR, BookType.AUDIO, 0, currentDate, 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            publicationDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Book', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            publicationDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            publicationDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Book()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Book', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            price: 1,
            rating: 1,
            imageURL: 'BBBBBB',
            language: 'BBBBBB',
            format: 'BBBBBB',
            paperBackQuantity: 1,
            publicationDate: currentDate.format(DATE_FORMAT),
            isbn: 'BBBBBB',
            pages: 1,
            otherDetails: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            publicationDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Book', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            price: 1,
            rating: 1,
            imageURL: 'BBBBBB',
            language: 'BBBBBB',
            format: 'BBBBBB',
            paperBackQuantity: 1,
            publicationDate: currentDate.format(DATE_FORMAT),
            isbn: 'BBBBBB',
            pages: 1,
            otherDetails: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            publicationDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Book', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
