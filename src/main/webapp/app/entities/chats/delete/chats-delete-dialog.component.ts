import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChats } from '../chats.model';
import { ChatsService } from '../service/chats.service';

@Component({
  templateUrl: './chats-delete-dialog.component.html',
})
export class ChatsDeleteDialogComponent {
  chats?: IChats;

  constructor(protected chatsService: ChatsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chatsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
