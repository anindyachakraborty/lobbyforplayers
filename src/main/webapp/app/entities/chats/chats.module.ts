import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChatsComponent } from './list/chats.component';
import { ChatsDetailComponent } from './detail/chats-detail.component';
import { ChatsUpdateComponent } from './update/chats-update.component';
import { ChatsDeleteDialogComponent } from './delete/chats-delete-dialog.component';
import { ChatsRoutingModule } from './route/chats-routing.module';

@NgModule({
  imports: [SharedModule, ChatsRoutingModule],
  declarations: [ChatsComponent, ChatsDetailComponent, ChatsUpdateComponent, ChatsDeleteDialogComponent],
  entryComponents: [ChatsDeleteDialogComponent],
})
export class ChatsModule {}
