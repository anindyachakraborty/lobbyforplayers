import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChatsDetailComponent } from './chats-detail.component';

describe('Chats Management Detail Component', () => {
  let comp: ChatsDetailComponent;
  let fixture: ComponentFixture<ChatsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChatsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ chats: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ChatsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ChatsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load chats on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.chats).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
