import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBargain } from '../bargain.model';
import { BargainService } from '../service/bargain.service';
import { BargainDeleteDialogComponent } from '../delete/bargain-delete-dialog.component';

@Component({
  selector: 'jhi-bargain',
  templateUrl: './bargain.component.html',
})
export class BargainComponent implements OnInit {
  bargains?: IBargain[];
  isLoading = false;

  constructor(protected bargainService: BargainService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.bargainService.query().subscribe({
      next: (res: HttpResponse<IBargain[]>) => {
        this.isLoading = false;
        this.bargains = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IBargain): string {
    return item.id!;
  }

  delete(bargain: IBargain): void {
    const modalRef = this.modalService.open(BargainDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bargain = bargain;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
