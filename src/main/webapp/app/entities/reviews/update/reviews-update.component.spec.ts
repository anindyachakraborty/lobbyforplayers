jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ReviewsService } from '../service/reviews.service';
import { IReviews, Reviews } from '../reviews.model';

import { ReviewsUpdateComponent } from './reviews-update.component';

describe('Reviews Management Update Component', () => {
  let comp: ReviewsUpdateComponent;
  let fixture: ComponentFixture<ReviewsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reviewsService: ReviewsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ReviewsUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ReviewsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReviewsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reviewsService = TestBed.inject(ReviewsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const reviews: IReviews = { id: 'CBA' };

      activatedRoute.data = of({ reviews });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(reviews));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Reviews>>();
      const reviews = { id: 'ABC' };
      jest.spyOn(reviewsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reviews });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reviews }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(reviewsService.update).toHaveBeenCalledWith(reviews);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Reviews>>();
      const reviews = new Reviews();
      jest.spyOn(reviewsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reviews });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reviews }));
      saveSubject.complete();

      // THEN
      expect(reviewsService.create).toHaveBeenCalledWith(reviews);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Reviews>>();
      const reviews = { id: 'ABC' };
      jest.spyOn(reviewsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reviews });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reviewsService.update).toHaveBeenCalledWith(reviews);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
