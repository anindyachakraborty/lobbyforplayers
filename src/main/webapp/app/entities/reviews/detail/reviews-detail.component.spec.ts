import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReviewsDetailComponent } from './reviews-detail.component';

describe('Reviews Management Detail Component', () => {
  let comp: ReviewsDetailComponent;
  let fixture: ComponentFixture<ReviewsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReviewsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ reviews: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ReviewsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReviewsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load reviews on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.reviews).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
