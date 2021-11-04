import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ReviewsService } from '../service/reviews.service';

import { ReviewsComponent } from './reviews.component';

describe('Reviews Management Component', () => {
  let comp: ReviewsComponent;
  let fixture: ComponentFixture<ReviewsComponent>;
  let service: ReviewsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ReviewsComponent],
    })
      .overrideTemplate(ReviewsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReviewsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReviewsService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.reviews?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });
});
