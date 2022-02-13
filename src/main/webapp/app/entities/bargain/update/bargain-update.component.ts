import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBargain, Bargain } from '../bargain.model';
import { BargainService } from '../service/bargain.service';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';

@Component({
  selector: 'jhi-bargain-update',
  templateUrl: './bargain-update.component.html',
})
export class BargainUpdateComponent implements OnInit {
  isSaving = false;

  itemsSharedCollection: IItem[] = [];

  editForm = this.fb.group({
    id: [],
    bargainPrice: [null, [Validators.required]],
    itemId: [null, [Validators.required]],
    sellerApproved: [null, [Validators.required]],
    buyerApproved: [null, [Validators.required]],
    sellerId: [null, [Validators.required]],
    buyerId: [null, [Validators.required]],
    item: [],
  });

  constructor(
    protected bargainService: BargainService,
    protected itemService: ItemService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bargain }) => {
      this.updateForm(bargain);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bargain = this.createFromForm();
    if (bargain.id !== undefined) {
      this.subscribeToSaveResponse(this.bargainService.update(bargain));
    } else {
      this.subscribeToSaveResponse(this.bargainService.create(bargain));
    }
  }

  trackItemById(index: number, item: IItem): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBargain>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
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

  protected updateForm(bargain: IBargain): void {
    this.editForm.patchValue({
      id: bargain.id,
      bargainPrice: bargain.bargainPrice,
      itemId: bargain.itemId,
      sellerApproved: bargain.sellerApproved,
      buyerApproved: bargain.buyerApproved,
      sellerId: bargain.sellerId,
      buyerId: bargain.buyerId,
      item: bargain.item,
    });

    this.itemsSharedCollection = this.itemService.addItemToCollectionIfMissing(this.itemsSharedCollection, bargain.item);
  }

  protected loadRelationshipsOptions(): void {
    this.itemService
      .query()
      .pipe(map((res: HttpResponse<IItem[]>) => res.body ?? []))
      .pipe(map((items: IItem[]) => this.itemService.addItemToCollectionIfMissing(items, this.editForm.get('item')!.value)))
      .subscribe((items: IItem[]) => (this.itemsSharedCollection = items));
  }

  protected createFromForm(): IBargain {
    return {
      ...new Bargain(),
      id: this.editForm.get(['id'])!.value,
      bargainPrice: this.editForm.get(['bargainPrice'])!.value,
      itemId: this.editForm.get(['itemId'])!.value,
      sellerApproved: this.editForm.get(['sellerApproved'])!.value,
      buyerApproved: this.editForm.get(['buyerApproved'])!.value,
      sellerId: this.editForm.get(['sellerId'])!.value,
      buyerId: this.editForm.get(['buyerId'])!.value,
      item: this.editForm.get(['item'])!.value,
    };
  }
}
