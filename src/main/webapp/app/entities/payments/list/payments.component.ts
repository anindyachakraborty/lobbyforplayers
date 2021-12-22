import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayments } from '../payments.model';
import { PaymentsService } from '../service/payments.service';
import { PaymentsDeleteDialogComponent } from '../delete/payments-delete-dialog.component';

@Component({
  selector: 'jhi-payments',
  templateUrl: './payments.component.html',
})
export class PaymentsComponent implements OnInit {
  payments?: IPayments[];
  isLoading = false;

  constructor(protected paymentsService: PaymentsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.paymentsService.query().subscribe(
      (res: HttpResponse<IPayments[]>) => {
        this.isLoading = false;
        this.payments = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPayments): string {
    return item.id!;
  }

  delete(payments: IPayments): void {
    const modalRef = this.modalService.open(PaymentsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.payments = payments;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
