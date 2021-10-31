jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ItemService } from '../service/item.service';
import { IItem, Item } from '../item.model';
import { IOrder } from 'app/entities/order/order.model';
import { OrderService } from 'app/entities/order/service/order.service';
import { ITags } from 'app/entities/tags/tags.model';
import { TagsService } from 'app/entities/tags/service/tags.service';

import { ItemUpdateComponent } from './item-update.component';

describe('Item Management Update Component', () => {
  let comp: ItemUpdateComponent;
  let fixture: ComponentFixture<ItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let itemService: ItemService;
  let orderService: OrderService;
  let tagsService: TagsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ItemUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    itemService = TestBed.inject(ItemService);
    orderService = TestBed.inject(OrderService);
    tagsService = TestBed.inject(TagsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call order query and add missing value', () => {
      const item: IItem = { id: 'CBA' };
      const order: IOrder = { id: '45eecd66-0a04-4b6a-a29c-480f124a31df' };
      item.order = order;

      const orderCollection: IOrder[] = [{ id: '4093b527-8654-443a-8676-59612def9d3f' }];
      jest.spyOn(orderService, 'query').mockReturnValue(of(new HttpResponse({ body: orderCollection })));
      const expectedCollection: IOrder[] = [order, ...orderCollection];
      jest.spyOn(orderService, 'addOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ item });
      comp.ngOnInit();

      expect(orderService.query).toHaveBeenCalled();
      expect(orderService.addOrderToCollectionIfMissing).toHaveBeenCalledWith(orderCollection, order);
      expect(comp.ordersCollection).toEqual(expectedCollection);
    });

    it('Should call Tags query and add missing value', () => {
      const item: IItem = { id: 'CBA' };
      const tags: ITags[] = [{ id: '0b3efd4d-3ecd-4310-8fb3-9903d88ee024' }];
      item.tags = tags;

      const tagsCollection: ITags[] = [{ id: 'e6a665cb-2733-4b99-9c6d-5bf6b2cc62e6' }];
      jest.spyOn(tagsService, 'query').mockReturnValue(of(new HttpResponse({ body: tagsCollection })));
      const additionalTags = [...tags];
      const expectedCollection: ITags[] = [...additionalTags, ...tagsCollection];
      jest.spyOn(tagsService, 'addTagsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ item });
      comp.ngOnInit();

      expect(tagsService.query).toHaveBeenCalled();
      expect(tagsService.addTagsToCollectionIfMissing).toHaveBeenCalledWith(tagsCollection, ...additionalTags);
      expect(comp.tagsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const item: IItem = { id: 'CBA' };
      const order: IOrder = { id: 'dc1066ea-e649-41b4-b199-e9284b9678f3' };
      item.order = order;
      const tags: ITags = { id: 'fbf37d61-97a6-42bf-92ee-04ca9df89525' };
      item.tags = [tags];

      activatedRoute.data = of({ item });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(item));
      expect(comp.ordersCollection).toContain(order);
      expect(comp.tagsSharedCollection).toContain(tags);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Item>>();
      const item = { id: 'ABC' };
      jest.spyOn(itemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ item });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: item }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(itemService.update).toHaveBeenCalledWith(item);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Item>>();
      const item = new Item();
      jest.spyOn(itemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ item });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: item }));
      saveSubject.complete();

      // THEN
      expect(itemService.create).toHaveBeenCalledWith(item);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Item>>();
      const item = { id: 'ABC' };
      jest.spyOn(itemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ item });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(itemService.update).toHaveBeenCalledWith(item);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackOrderById', () => {
      it('Should return tracked Order primary key', () => {
        const entity = { id: 'ABC' };
        const trackResult = comp.trackOrderById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTagsById', () => {
      it('Should return tracked Tags primary key', () => {
        const entity = { id: 'ABC' };
        const trackResult = comp.trackTagsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedTags', () => {
      it('Should return option if no Tags is selected', () => {
        const option = { id: 'ABC' };
        const result = comp.getSelectedTags(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Tags for according option', () => {
        const option = { id: 'ABC' };
        const selected = { id: 'ABC' };
        const selected2 = { id: 'CBA' };
        const result = comp.getSelectedTags(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Tags is not selected', () => {
        const option = { id: 'ABC' };
        const selected = { id: 'CBA' };
        const result = comp.getSelectedTags(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
