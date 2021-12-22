import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IQuestionary, Questionary } from '../questionary.model';
import { QuestionaryService } from '../service/questionary.service';

@Component({
  selector: 'jhi-questionary-update',
  templateUrl: './questionary-update.component.html',
})
export class QuestionaryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    process: [null, [Validators.required]],
    questions: [null, [Validators.required]],
  });

  constructor(protected questionaryService: QuestionaryService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionary }) => {
      this.updateForm(questionary);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionary = this.createFromForm();
    if (questionary.id !== undefined) {
      this.subscribeToSaveResponse(this.questionaryService.update(questionary));
    } else {
      this.subscribeToSaveResponse(this.questionaryService.create(questionary));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionary>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(questionary: IQuestionary): void {
    this.editForm.patchValue({
      id: questionary.id,
      process: questionary.process,
      questions: questionary.questions,
    });
  }

  protected createFromForm(): IQuestionary {
    return {
      ...new Questionary(),
      id: this.editForm.get(['id'])!.value,
      process: this.editForm.get(['process'])!.value,
      questions: this.editForm.get(['questions'])!.value,
    };
  }
}
