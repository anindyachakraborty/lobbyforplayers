import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetails } from '../details.model';
import { DetailsService } from '../service/details.service';

@Component({
  templateUrl: './details-delete-dialog.component.html',
})
export class DetailsDeleteDialogComponent {
  details?: IDetails;

  constructor(protected detailsService: DetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.detailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
