import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionary } from '../questionary.model';
import { QuestionaryService } from '../service/questionary.service';

@Component({
  templateUrl: './questionary-delete-dialog.component.html',
})
export class QuestionaryDeleteDialogComponent {
  questionary?: IQuestionary;

  constructor(protected questionaryService: QuestionaryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.questionaryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
