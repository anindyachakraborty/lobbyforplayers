import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IChats, Chats } from '../chats.model';

import { ChatsService } from './chats.service';

describe('Chats Service', () => {
  let service: ChatsService;
  let httpMock: HttpTestingController;
  let elemDefault: IChats;
  let expectedResult: IChats | IChats[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ChatsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      fromUserId: 'AAAAAAA',
      toUserId: 'AAAAAAA',
      timeStamp: currentDate,
      message: 'AAAAAAA',
      language: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          timeStamp: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Chats', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          timeStamp: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          timeStamp: currentDate,
        },
        returnedFromService
      );

      service.create(new Chats()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Chats', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          fromUserId: 'BBBBBB',
          toUserId: 'BBBBBB',
          timeStamp: currentDate.format(DATE_TIME_FORMAT),
          message: 'BBBBBB',
          language: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          timeStamp: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Chats', () => {
      const patchObject = Object.assign(
        {
          timeStamp: currentDate.format(DATE_TIME_FORMAT),
        },
        new Chats()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          timeStamp: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Chats', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          fromUserId: 'BBBBBB',
          toUserId: 'BBBBBB',
          timeStamp: currentDate.format(DATE_TIME_FORMAT),
          message: 'BBBBBB',
          language: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          timeStamp: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Chats', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addChatsToCollectionIfMissing', () => {
      it('should add a Chats to an empty array', () => {
        const chats: IChats = { id: 'ABC' };
        expectedResult = service.addChatsToCollectionIfMissing([], chats);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chats);
      });

      it('should not add a Chats to an array that contains it', () => {
        const chats: IChats = { id: 'ABC' };
        const chatsCollection: IChats[] = [
          {
            ...chats,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addChatsToCollectionIfMissing(chatsCollection, chats);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Chats to an array that doesn't contain it", () => {
        const chats: IChats = { id: 'ABC' };
        const chatsCollection: IChats[] = [{ id: 'CBA' }];
        expectedResult = service.addChatsToCollectionIfMissing(chatsCollection, chats);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chats);
      });

      it('should add only unique Chats to an array', () => {
        const chatsArray: IChats[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '4adf37ce-6514-4c4a-ba0d-ac2bd05881af' }];
        const chatsCollection: IChats[] = [{ id: 'ABC' }];
        expectedResult = service.addChatsToCollectionIfMissing(chatsCollection, ...chatsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const chats: IChats = { id: 'ABC' };
        const chats2: IChats = { id: 'CBA' };
        expectedResult = service.addChatsToCollectionIfMissing([], chats, chats2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chats);
        expect(expectedResult).toContain(chats2);
      });

      it('should accept null and undefined values', () => {
        const chats: IChats = { id: 'ABC' };
        expectedResult = service.addChatsToCollectionIfMissing([], null, chats, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chats);
      });

      it('should return initial array if no Chats is added', () => {
        const chatsCollection: IChats[] = [{ id: 'ABC' }];
        expectedResult = service.addChatsToCollectionIfMissing(chatsCollection, undefined, null);
        expect(expectedResult).toEqual(chatsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
