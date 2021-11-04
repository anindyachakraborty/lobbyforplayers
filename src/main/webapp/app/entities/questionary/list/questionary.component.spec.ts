import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { QuestionaryService } from '../service/questionary.service';

import { QuestionaryComponent } from './questionary.component';

describe('Questionary Management Component', () => {
  let comp: QuestionaryComponent;
  let fixture: ComponentFixture<QuestionaryComponent>;
  let service: QuestionaryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [QuestionaryComponent],
    })
      .overrideTemplate(QuestionaryComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QuestionaryComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(QuestionaryService);

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
    expect(comp.questionaries?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });
});
