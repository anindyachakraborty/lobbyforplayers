import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QuestionaryDetailComponent } from './questionary-detail.component';

describe('Questionary Management Detail Component', () => {
  let comp: QuestionaryDetailComponent;
  let fixture: ComponentFixture<QuestionaryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuestionaryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ questionary: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(QuestionaryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QuestionaryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load questionary on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.questionary).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
