import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ItemComponent } from './list/item.component';
import { ItemDetailComponent } from './detail/item-detail.component';
import { ItemUpdateComponent } from './update/item-update.component';
import { ItemDeleteDialogComponent } from './delete/item-delete-dialog.component';
import { ItemRoutingModule } from './route/item-routing.module';
import { NouisliderModule } from 'ng2-nouislider';

@NgModule({
  imports: [SharedModule, ItemRoutingModule, NouisliderModule],
  declarations: [ItemComponent, ItemDetailComponent, ItemUpdateComponent, ItemDeleteDialogComponent],
  entryComponents: [ItemDeleteDialogComponent],
})
export class ItemModule {}
