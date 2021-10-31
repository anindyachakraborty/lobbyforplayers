import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITags, Tags } from '../tags.model';

import { TagsService } from './tags.service';

describe('Tags Service', () => {
  let service: TagsService;
  let httpMock: HttpTestingController;
  let elemDefault: ITags;
  let expectedResult: ITags | ITags[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TagsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      tag: 'AAAAAAA',
      language: 'AAAAAAA',
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

    it('should create a Tags', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Tags()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Tags', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          tag: 'BBBBBB',
          language: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Tags', () => {
      const patchObject = Object.assign(
        {
          language: 'BBBBBB',
        },
        new Tags()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Tags', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          tag: 'BBBBBB',
          language: 'BBBBBB',
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

    it('should delete a Tags', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTagsToCollectionIfMissing', () => {
      it('should add a Tags to an empty array', () => {
        const tags: ITags = { id: 'ABC' };
        expectedResult = service.addTagsToCollectionIfMissing([], tags);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tags);
      });

      it('should not add a Tags to an array that contains it', () => {
        const tags: ITags = { id: 'ABC' };
        const tagsCollection: ITags[] = [
          {
            ...tags,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addTagsToCollectionIfMissing(tagsCollection, tags);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Tags to an array that doesn't contain it", () => {
        const tags: ITags = { id: 'ABC' };
        const tagsCollection: ITags[] = [{ id: 'CBA' }];
        expectedResult = service.addTagsToCollectionIfMissing(tagsCollection, tags);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tags);
      });

      it('should add only unique Tags to an array', () => {
        const tagsArray: ITags[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '0c2caaf5-2fed-4c80-b5f8-75306fcf615f' }];
        const tagsCollection: ITags[] = [{ id: 'ABC' }];
        expectedResult = service.addTagsToCollectionIfMissing(tagsCollection, ...tagsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tags: ITags = { id: 'ABC' };
        const tags2: ITags = { id: 'CBA' };
        expectedResult = service.addTagsToCollectionIfMissing([], tags, tags2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tags);
        expect(expectedResult).toContain(tags2);
      });

      it('should accept null and undefined values', () => {
        const tags: ITags = { id: 'ABC' };
        expectedResult = service.addTagsToCollectionIfMissing([], null, tags, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tags);
      });

      it('should return initial array if no Tags is added', () => {
        const tagsCollection: ITags[] = [{ id: 'ABC' }];
        expectedResult = service.addTagsToCollectionIfMissing(tagsCollection, undefined, null);
        expect(expectedResult).toEqual(tagsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
