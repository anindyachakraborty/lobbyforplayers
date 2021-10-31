import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBargain } from '../bargain.model';

@Component({
  selector: 'jhi-bargain-detail',
  templateUrl: './bargain-detail.component.html',
})
export class BargainDetailComponent implements OnInit {
  bargain: IBargain | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bargain }) => {
      this.bargain = bargain;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
