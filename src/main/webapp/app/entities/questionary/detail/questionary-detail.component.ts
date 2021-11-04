import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionary } from '../questionary.model';

@Component({
  selector: 'jhi-questionary-detail',
  templateUrl: './questionary-detail.component.html',
})
export class QuestionaryDetailComponent implements OnInit {
  questionary: IQuestionary | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionary }) => {
      this.questionary = questionary;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
