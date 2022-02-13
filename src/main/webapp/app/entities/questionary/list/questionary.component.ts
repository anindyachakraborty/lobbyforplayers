import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionary } from '../questionary.model';
import { QuestionaryService } from '../service/questionary.service';
import { QuestionaryDeleteDialogComponent } from '../delete/questionary-delete-dialog.component';

@Component({
  selector: 'jhi-questionary',
  templateUrl: './questionary.component.html',
})
export class QuestionaryComponent implements OnInit {
  questionaries?: IQuestionary[];
  isLoading = false;

  constructor(protected questionaryService: QuestionaryService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.questionaryService.query().subscribe({
      next: (res: HttpResponse<IQuestionary[]>) => {
        this.isLoading = false;
        this.questionaries = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IQuestionary): string {
    return item.id!;
  }

  delete(questionary: IQuestionary): void {
    const modalRef = this.modalService.open(QuestionaryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questionary = questionary;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
