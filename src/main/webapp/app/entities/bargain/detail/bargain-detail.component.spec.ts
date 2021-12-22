import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BargainDetailComponent } from './bargain-detail.component';

describe('Bargain Management Detail Component', () => {
  let comp: BargainDetailComponent;
  let fixture: ComponentFixture<BargainDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BargainDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bargain: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(BargainDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BargainDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bargain on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bargain).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
