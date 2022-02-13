import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDetails, Details } from '../details.model';
import { DetailsService } from '../service/details.service';

@Component({
  selector: 'jhi-details-update',
  templateUrl: './details-update.component.html',
})
export class DetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    loginName: [null, [Validators.required]],
    password: [null, [Validators.required]],
    lastName: [],
    firstName: [],
    securtiyQuestion: [],
    securityAnswer: [],
    parentalPassword: [],
    firstCdKey: [],
    otherInformation: [],
    enteredDate: [null, [Validators.required]],
    orderDate: [],
  });

  constructor(protected detailsService: DetailsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ details }) => {
      if (details.id === undefined) {
        const today = dayjs().startOf('day');
        details.enteredDate = today;
      }

      this.updateForm(details);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const details = this.createFromForm();
    if (details.id !== undefined) {
      this.subscribeToSaveResponse(this.detailsService.update(details));
    } else {
      this.subscribeToSaveResponse(this.detailsService.create(details));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
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

  protected updateForm(details: IDetails): void {
    this.editForm.patchValue({
      id: details.id,
      loginName: details.loginName,
      password: details.password,
      lastName: details.lastName,
      firstName: details.firstName,
      securtiyQuestion: details.securtiyQuestion,
      securityAnswer: details.securityAnswer,
      parentalPassword: details.parentalPassword,
      firstCdKey: details.firstCdKey,
      otherInformation: details.otherInformation,
      enteredDate: details.enteredDate ? details.enteredDate.format(DATE_TIME_FORMAT) : null,
      orderDate: details.orderDate,
    });
  }

  protected createFromForm(): IDetails {
    return {
      ...new Details(),
      id: this.editForm.get(['id'])!.value,
      loginName: this.editForm.get(['loginName'])!.value,
      password: this.editForm.get(['password'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      securtiyQuestion: this.editForm.get(['securtiyQuestion'])!.value,
      securityAnswer: this.editForm.get(['securityAnswer'])!.value,
      parentalPassword: this.editForm.get(['parentalPassword'])!.value,
      firstCdKey: this.editForm.get(['firstCdKey'])!.value,
      otherInformation: this.editForm.get(['otherInformation'])!.value,
      enteredDate: this.editForm.get(['enteredDate'])!.value
        ? dayjs(this.editForm.get(['enteredDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      orderDate: this.editForm.get(['orderDate'])!.value,
    };
  }
}
