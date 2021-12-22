import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IQuestionary, Questionary } from '../questionary.model';

import { QuestionaryService } from './questionary.service';

describe('Questionary Service', () => {
  let service: QuestionaryService;
  let httpMock: HttpTestingController;
  let elemDefault: IQuestionary;
  let expectedResult: IQuestionary | IQuestionary[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QuestionaryService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      process: 'AAAAAAA',
      questions: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Questionary', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Questionary()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Questionary', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          process: 'BBBBBB',
          questions: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Questionary', () => {
      const patchObject = Object.assign(
        {
          process: 'BBBBBB',
          questions: 'BBBBBB',
        },
        new Questionary()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Questionary', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          process: 'BBBBBB',
          questions: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Questionary', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addQuestionaryToCollectionIfMissing', () => {
      it('should add a Questionary to an empty array', () => {
        const questionary: IQuestionary = { id: 'ABC' };
        expectedResult = service.addQuestionaryToCollectionIfMissing([], questionary);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(questionary);
      });

      it('should not add a Questionary to an array that contains it', () => {
        const questionary: IQuestionary = { id: 'ABC' };
        const questionaryCollection: IQuestionary[] = [
          {
            ...questionary,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addQuestionaryToCollectionIfMissing(questionaryCollection, questionary);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Questionary to an array that doesn't contain it", () => {
        const questionary: IQuestionary = { id: 'ABC' };
        const questionaryCollection: IQuestionary[] = [{ id: 'CBA' }];
        expectedResult = service.addQuestionaryToCollectionIfMissing(questionaryCollection, questionary);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(questionary);
      });

      it('should add only unique Questionary to an array', () => {
        const questionaryArray: IQuestionary[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '50d89879-4fa2-403a-a86a-a27ec10a50f9' }];
        const questionaryCollection: IQuestionary[] = [{ id: 'ABC' }];
        expectedResult = service.addQuestionaryToCollectionIfMissing(questionaryCollection, ...questionaryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const questionary: IQuestionary = { id: 'ABC' };
        const questionary2: IQuestionary = { id: 'CBA' };
        expectedResult = service.addQuestionaryToCollectionIfMissing([], questionary, questionary2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(questionary);
        expect(expectedResult).toContain(questionary2);
      });

      it('should accept null and undefined values', () => {
        const questionary: IQuestionary = { id: 'ABC' };
        expectedResult = service.addQuestionaryToCollectionIfMissing([], null, questionary, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(questionary);
      });

      it('should return initial array if no Questionary is added', () => {
        const questionaryCollection: IQuestionary[] = [{ id: 'ABC' }];
        expectedResult = service.addQuestionaryToCollectionIfMissing(questionaryCollection, undefined, null);
        expect(expectedResult).toEqual(questionaryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
