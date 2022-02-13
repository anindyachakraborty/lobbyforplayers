import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetails } from '../details.model';
import { DetailsService } from '../service/details.service';
import { DetailsDeleteDialogComponent } from '../delete/details-delete-dialog.component';

@Component({
  selector: 'jhi-details',
  templateUrl: './details.component.html',
})
export class DetailsComponent implements OnInit {
  details?: IDetails[];
  isLoading = false;

  constructor(protected detailsService: DetailsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.detailsService.query().subscribe({
      next: (res: HttpResponse<IDetails[]>) => {
        this.isLoading = false;
        this.details = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDetails): string {
    return item.id!;
  }

  delete(details: IDetails): void {
    const modalRef = this.modalService.open(DetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.details = details;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
