jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DetailsService } from '../service/details.service';
import { IDetails, Details } from '../details.model';

import { DetailsUpdateComponent } from './details-update.component';

describe('Details Management Update Component', () => {
  let comp: DetailsUpdateComponent;
  let fixture: ComponentFixture<DetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detailsService: DetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DetailsUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detailsService = TestBed.inject(DetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const details: IDetails = { id: 456 };

      activatedRoute.data = of({ details });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(details));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Details>>();
      const details = { id: 123 };
      jest.spyOn(detailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ details });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: details }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(detailsService.update).toHaveBeenCalledWith(details);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Details>>();
      const details = new Details();
      jest.spyOn(detailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ details });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: details }));
      saveSubject.complete();

      // THEN
      expect(detailsService.create).toHaveBeenCalledWith(details);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Details>>();
      const details = { id: 123 };
      jest.spyOn(detailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ details });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detailsService.update).toHaveBeenCalledWith(details);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
