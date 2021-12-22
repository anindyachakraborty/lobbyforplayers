import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BargainComponent } from './list/bargain.component';
import { BargainDetailComponent } from './detail/bargain-detail.component';
import { BargainUpdateComponent } from './update/bargain-update.component';
import { BargainDeleteDialogComponent } from './delete/bargain-delete-dialog.component';
import { BargainRoutingModule } from './route/bargain-routing.module';

@NgModule({
  imports: [SharedModule, BargainRoutingModule],
  declarations: [BargainComponent, BargainDetailComponent, BargainUpdateComponent, BargainDeleteDialogComponent],
  entryComponents: [BargainDeleteDialogComponent],
})
export class BargainModule {}
