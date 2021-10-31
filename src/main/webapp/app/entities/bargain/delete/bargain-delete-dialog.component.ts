import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBargain } from '../bargain.model';
import { BargainService } from '../service/bargain.service';

@Component({
  templateUrl: './bargain-delete-dialog.component.html',
})
export class BargainDeleteDialogComponent {
  bargain?: IBargain;

  constructor(protected bargainService: BargainService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.bargainService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
