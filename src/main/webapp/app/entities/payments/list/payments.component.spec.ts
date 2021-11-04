import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PaymentsService } from '../service/payments.service';

import { PaymentsComponent } from './payments.component';

describe('Payments Management Component', () => {
  let comp: PaymentsComponent;
  let fixture: ComponentFixture<PaymentsComponent>;
  let service: PaymentsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentsComponent],
    })
      .overrideTemplate(PaymentsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentsService);

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
    expect(comp.payments?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });
});
