jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PaymentsService } from '../service/payments.service';
import { IPayments, Payments } from '../payments.model';

import { PaymentsUpdateComponent } from './payments-update.component';

describe('Payments Management Update Component', () => {
  let comp: PaymentsUpdateComponent;
  let fixture: ComponentFixture<PaymentsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentsService: PaymentsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentsUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PaymentsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentsService = TestBed.inject(PaymentsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const payments: IPayments = { id: 'CBA' };

      activatedRoute.data = of({ payments });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(payments));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payments>>();
      const payments = { id: 'ABC' };
      jest.spyOn(paymentsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payments });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payments }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentsService.update).toHaveBeenCalledWith(payments);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payments>>();
      const payments = new Payments();
      jest.spyOn(paymentsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payments });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payments }));
      saveSubject.complete();

      // THEN
      expect(paymentsService.create).toHaveBeenCalledWith(payments);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payments>>();
      const payments = { id: 'ABC' };
      jest.spyOn(paymentsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payments });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentsService.update).toHaveBeenCalledWith(payments);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
