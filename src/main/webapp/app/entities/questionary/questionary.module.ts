import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QuestionaryComponent } from './list/questionary.component';
import { QuestionaryDetailComponent } from './detail/questionary-detail.component';
import { QuestionaryUpdateComponent } from './update/questionary-update.component';
import { QuestionaryDeleteDialogComponent } from './delete/questionary-delete-dialog.component';
import { QuestionaryRoutingModule } from './route/questionary-routing.module';

@NgModule({
  imports: [SharedModule, QuestionaryRoutingModule],
  declarations: [QuestionaryComponent, QuestionaryDetailComponent, QuestionaryUpdateComponent, QuestionaryDeleteDialogComponent],
  entryComponents: [QuestionaryDeleteDialogComponent],
})
export class QuestionaryModule {}
