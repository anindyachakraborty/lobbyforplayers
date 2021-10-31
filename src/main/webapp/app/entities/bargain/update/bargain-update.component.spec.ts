jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BargainService } from '../service/bargain.service';
import { IBargain, Bargain } from '../bargain.model';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';

import { BargainUpdateComponent } from './bargain-update.component';

describe('Bargain Management Update Component', () => {
  let comp: BargainUpdateComponent;
  let fixture: ComponentFixture<BargainUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bargainService: BargainService;
  let itemService: ItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BargainUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(BargainUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BargainUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bargainService = TestBed.inject(BargainService);
    itemService = TestBed.inject(ItemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Item query and add missing value', () => {
      const bargain: IBargain = { id: 456 };
      const item: IItem = { id: 25972 };
      bargain.item = item;

      const itemCollection: IItem[] = [{ id: 66153 }];
      jest.spyOn(itemService, 'query').mockReturnValue(of(new HttpResponse({ body: itemCollection })));
      const additionalItems = [item];
      const expectedCollection: IItem[] = [...additionalItems, ...itemCollection];
      jest.spyOn(itemService, 'addItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bargain });
      comp.ngOnInit();

      expect(itemService.query).toHaveBeenCalled();
      expect(itemService.addItemToCollectionIfMissing).toHaveBeenCalledWith(itemCollection, ...additionalItems);
      expect(comp.itemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bargain: IBargain = { id: 456 };
      const item: IItem = { id: 57805 };
      bargain.item = item;

      activatedRoute.data = of({ bargain });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bargain));
      expect(comp.itemsSharedCollection).toContain(item);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bargain>>();
      const bargain = { id: 123 };
      jest.spyOn(bargainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bargain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bargain }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bargainService.update).toHaveBeenCalledWith(bargain);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bargain>>();
      const bargain = new Bargain();
      jest.spyOn(bargainService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bargain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bargain }));
      saveSubject.complete();

      // THEN
      expect(bargainService.create).toHaveBeenCalledWith(bargain);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bargain>>();
      const bargain = { id: 123 };
      jest.spyOn(bargainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bargain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bargainService.update).toHaveBeenCalledWith(bargain);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackItemById', () => {
      it('Should return tracked Item primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackItemById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
