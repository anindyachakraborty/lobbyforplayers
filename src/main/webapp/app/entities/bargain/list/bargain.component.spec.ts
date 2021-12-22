import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { BargainService } from '../service/bargain.service';

import { BargainComponent } from './bargain.component';

describe('Bargain Management Component', () => {
  let comp: BargainComponent;
  let fixture: ComponentFixture<BargainComponent>;
  let service: BargainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BargainComponent],
    })
      .overrideTemplate(BargainComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BargainComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(BargainService);

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
    expect(comp.bargains?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });
});
