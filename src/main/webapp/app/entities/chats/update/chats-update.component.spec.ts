jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ChatsService } from '../service/chats.service';
import { IChats, Chats } from '../chats.model';

import { ChatsUpdateComponent } from './chats-update.component';

describe('Chats Management Update Component', () => {
  let comp: ChatsUpdateComponent;
  let fixture: ComponentFixture<ChatsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let chatsService: ChatsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ChatsUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ChatsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChatsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    chatsService = TestBed.inject(ChatsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const chats: IChats = { id: 'CBA' };

      activatedRoute.data = of({ chats });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(chats));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Chats>>();
      const chats = { id: 'ABC' };
      jest.spyOn(chatsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chats });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chats }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(chatsService.update).toHaveBeenCalledWith(chats);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Chats>>();
      const chats = new Chats();
      jest.spyOn(chatsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chats });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chats }));
      saveSubject.complete();

      // THEN
      expect(chatsService.create).toHaveBeenCalledWith(chats);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Chats>>();
      const chats = { id: 'ABC' };
      jest.spyOn(chatsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chats });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(chatsService.update).toHaveBeenCalledWith(chats);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
