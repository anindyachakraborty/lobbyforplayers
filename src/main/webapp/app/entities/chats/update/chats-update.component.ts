import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IChats, Chats } from '../chats.model';
import { ChatsService } from '../service/chats.service';

@Component({
  selector: 'jhi-chats-update',
  templateUrl: './chats-update.component.html',
})
export class ChatsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fromUserId: [null, [Validators.required]],
    toUserId: [null, [Validators.required]],
    timeStamp: [null, [Validators.required]],
    message: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(256)]],
    language: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]],
  });

  constructor(protected chatsService: ChatsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chats }) => {
      if (chats.id === undefined) {
        const today = dayjs().startOf('day');
        chats.timeStamp = today;
      }

      this.updateForm(chats);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chats = this.createFromForm();
    if (chats.id !== undefined) {
      this.subscribeToSaveResponse(this.chatsService.update(chats));
    } else {
      this.subscribeToSaveResponse(this.chatsService.create(chats));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChats>>): void {
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

  protected updateForm(chats: IChats): void {
    this.editForm.patchValue({
      id: chats.id,
      fromUserId: chats.fromUserId,
      toUserId: chats.toUserId,
      timeStamp: chats.timeStamp ? chats.timeStamp.format(DATE_TIME_FORMAT) : null,
      message: chats.message,
      language: chats.language,
    });
  }

  protected createFromForm(): IChats {
    return {
      ...new Chats(),
      id: this.editForm.get(['id'])!.value,
      fromUserId: this.editForm.get(['fromUserId'])!.value,
      toUserId: this.editForm.get(['toUserId'])!.value,
      timeStamp: this.editForm.get(['timeStamp'])!.value ? dayjs(this.editForm.get(['timeStamp'])!.value, DATE_TIME_FORMAT) : undefined,
      message: this.editForm.get(['message'])!.value,
      language: this.editForm.get(['language'])!.value,
    };
  }
}
