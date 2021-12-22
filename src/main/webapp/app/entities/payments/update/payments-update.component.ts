import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPayments, Payments } from '../payments.model';
import { PaymentsService } from '../service/payments.service';

@Component({
  selector: 'jhi-payments-update',
  templateUrl: './payments-update.component.html',
})
export class PaymentsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    buyerUserId: [null, [Validators.required]],
    sellerUserId: [null, [Validators.required]],
    sellerRecieved: [null, [Validators.required]],
    buyerTransactionId: [],
    sellerTransactionId: [],
    orderId: [null, [Validators.required]],
    packageId: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    game: [],
    status: [null, [Validators.required]],
  });

  constructor(protected paymentsService: PaymentsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payments }) => {
      this.updateForm(payments);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payments = this.createFromForm();
    if (payments.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentsService.update(payments));
    } else {
      this.subscribeToSaveResponse(this.paymentsService.create(payments));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayments>>): void {
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

  protected updateForm(payments: IPayments): void {
    this.editForm.patchValue({
      id: payments.id,
      buyerUserId: payments.buyerUserId,
      sellerUserId: payments.sellerUserId,
      sellerRecieved: payments.sellerRecieved,
      buyerTransactionId: payments.buyerTransactionId,
      sellerTransactionId: payments.sellerTransactionId,
      orderId: payments.orderId,
      packageId: payments.packageId,
      amount: payments.amount,
      game: payments.game,
      status: payments.status,
    });
  }

  protected createFromForm(): IPayments {
    return {
      ...new Payments(),
      id: this.editForm.get(['id'])!.value,
      buyerUserId: this.editForm.get(['buyerUserId'])!.value,
      sellerUserId: this.editForm.get(['sellerUserId'])!.value,
      sellerRecieved: this.editForm.get(['sellerRecieved'])!.value,
      buyerTransactionId: this.editForm.get(['buyerTransactionId'])!.value,
      sellerTransactionId: this.editForm.get(['sellerTransactionId'])!.value,
      orderId: this.editForm.get(['orderId'])!.value,
      packageId: this.editForm.get(['packageId'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      game: this.editForm.get(['game'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }
}
