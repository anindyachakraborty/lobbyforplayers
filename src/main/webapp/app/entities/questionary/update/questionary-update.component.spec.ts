jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { QuestionaryService } from '../service/questionary.service';
import { IQuestionary, Questionary } from '../questionary.model';

import { QuestionaryUpdateComponent } from './questionary-update.component';

describe('Questionary Management Update Component', () => {
  let comp: QuestionaryUpdateComponent;
  let fixture: ComponentFixture<QuestionaryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let questionaryService: QuestionaryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [QuestionaryUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(QuestionaryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QuestionaryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    questionaryService = TestBed.inject(QuestionaryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const questionary: IQuestionary = { id: 'CBA' };

      activatedRoute.data = of({ questionary });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(questionary));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Questionary>>();
      const questionary = { id: 'ABC' };
      jest.spyOn(questionaryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ questionary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: questionary }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(questionaryService.update).toHaveBeenCalledWith(questionary);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Questionary>>();
      const questionary = new Questionary();
      jest.spyOn(questionaryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ questionary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: questionary }));
      saveSubject.complete();

      // THEN
      expect(questionaryService.create).toHaveBeenCalledWith(questionary);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Questionary>>();
      const questionary = { id: 'ABC' };
      jest.spyOn(questionaryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ questionary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(questionaryService.update).toHaveBeenCalledWith(questionary);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
