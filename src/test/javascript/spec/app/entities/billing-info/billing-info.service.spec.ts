import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BillingInfoService } from 'app/entities/billing-info/billing-info.service';
import { IBillingInfo, BillingInfo } from 'app/shared/model/billing-info.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';

describe('Service Tests', () => {
  describe('BillingInfo Service', () => {
    let injector: TestBed;
    let service: BillingInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IBillingInfo;
    let expectedResult: IBillingInfo | IBillingInfo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BillingInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BillingInfo(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', PaymentMethod.CREDITCARD);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            cardExpiryDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BillingInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            cardExpiryDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            cardExpiryDate: currentDate,
          },
          returnedFromService
        );

        service.create(new BillingInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BillingInfo', () => {
        const returnedFromService = Object.assign(
          {
            infoTitle: 'BBBBBB',
            cardNumber: 'BBBBBB',
            cardExpiryDate: currentDate.format(DATE_FORMAT),
            cryptogram: 'BBBBBB',
            email: 'BBBBBB',
            billingMethod: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            cardExpiryDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BillingInfo', () => {
        const returnedFromService = Object.assign(
          {
            infoTitle: 'BBBBBB',
            cardNumber: 'BBBBBB',
            cardExpiryDate: currentDate.format(DATE_FORMAT),
            cryptogram: 'BBBBBB',
            email: 'BBBBBB',
            billingMethod: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            cardExpiryDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BillingInfo', () => {
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
