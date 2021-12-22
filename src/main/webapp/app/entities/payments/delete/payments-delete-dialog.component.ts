import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayments } from '../payments.model';
import { PaymentsService } from '../service/payments.service';

@Component({
  templateUrl: './payments-delete-dialog.component.html',
})
export class PaymentsDeleteDialogComponent {
  payments?: IPayments;

  constructor(protected paymentsService: PaymentsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.paymentsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
