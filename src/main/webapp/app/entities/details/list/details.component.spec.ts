import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DetailsService } from '../service/details.service';

import { DetailsComponent } from './details.component';

describe('Details Management Component', () => {
  let comp: DetailsComponent;
  let fixture: ComponentFixture<DetailsComponent>;
  let service: DetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DetailsComponent],
    })
      .overrideTemplate(DetailsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DetailsService);

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
    expect(comp.details?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });
});
