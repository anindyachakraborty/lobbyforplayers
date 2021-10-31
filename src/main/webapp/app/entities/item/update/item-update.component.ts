import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IItem, Item } from '../item.model';
import { ItemService } from '../service/item.service';
import { IOrder } from 'app/entities/order/order.model';
import { OrderService } from 'app/entities/order/service/order.service';
import { ITags } from 'app/entities/tags/tags.model';
import { TagsService } from 'app/entities/tags/service/tags.service';

@Component({
  selector: 'jhi-item-update',
  templateUrl: './item-update.component.html',
})
export class ItemUpdateComponent implements OnInit {
  isSaving = false;

  ordersCollection: IOrder[] = [];
  tagsSharedCollection: ITags[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required, Validators.maxLength(256)]],
    views: [null, [Validators.required]],
    sellerName: [null, [Validators.required]],
    sellerId: [null, [Validators.required]],
    listedFlag: [null, [Validators.required]],
    price: [null, [Validators.required]],
    picturesPath: [null, [Validators.maxLength(256)]],
    level: [],
    fixedPrice: [null, [Validators.required]],
    gameName: [null, [Validators.required]],
    language: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]],
    order: [],
    tags: [],
  });

  constructor(
    protected itemService: ItemService,
    protected orderService: OrderService,
    protected tagsService: TagsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ item }) => {
      this.updateForm(item);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const item = this.createFromForm();
    if (item.id !== undefined) {
      this.subscribeToSaveResponse(this.itemService.update(item));
    } else {
      this.subscribeToSaveResponse(this.itemService.create(item));
    }
  }

  trackOrderById(index: number, item: IOrder): number {
    return item.id!;
  }

  trackTagsById(index: number, item: ITags): number {
    return item.id!;
  }

  getSelectedTags(option: ITags, selectedVals?: ITags[]): ITags {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(item: IItem): void {
    this.editForm.patchValue({
      id: item.id,
      description: item.description,
      views: item.views,
      sellerName: item.sellerName,
      sellerId: item.sellerId,
      listedFlag: item.listedFlag,
      price: item.price,
      picturesPath: item.picturesPath,
      level: item.level,
      fixedPrice: item.fixedPrice,
      gameName: item.gameName,
      language: item.language,
      order: item.order,
      tags: item.tags,
    });

    this.ordersCollection = this.orderService.addOrderToCollectionIfMissing(this.ordersCollection, item.order);
    this.tagsSharedCollection = this.tagsService.addTagsToCollectionIfMissing(this.tagsSharedCollection, ...(item.tags ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.orderService
      .query({ filter: 'item-is-null' })
      .pipe(map((res: HttpResponse<IOrder[]>) => res.body ?? []))
      .pipe(map((orders: IOrder[]) => this.orderService.addOrderToCollectionIfMissing(orders, this.editForm.get('order')!.value)))
      .subscribe((orders: IOrder[]) => (this.ordersCollection = orders));

    this.tagsService
      .query()
      .pipe(map((res: HttpResponse<ITags[]>) => res.body ?? []))
      .pipe(map((tags: ITags[]) => this.tagsService.addTagsToCollectionIfMissing(tags, ...(this.editForm.get('tags')!.value ?? []))))
      .subscribe((tags: ITags[]) => (this.tagsSharedCollection = tags));
  }

  protected createFromForm(): IItem {
    return {
      ...new Item(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      views: this.editForm.get(['views'])!.value,
      sellerName: this.editForm.get(['sellerName'])!.value,
      sellerId: this.editForm.get(['sellerId'])!.value,
      listedFlag: this.editForm.get(['listedFlag'])!.value,
      price: this.editForm.get(['price'])!.value,
      picturesPath: this.editForm.get(['picturesPath'])!.value,
      level: this.editForm.get(['level'])!.value,
      fixedPrice: this.editForm.get(['fixedPrice'])!.value,
      gameName: this.editForm.get(['gameName'])!.value,
      language: this.editForm.get(['language'])!.value,
      order: this.editForm.get(['order'])!.value,
      tags: this.editForm.get(['tags'])!.value,
    };
  }
}
