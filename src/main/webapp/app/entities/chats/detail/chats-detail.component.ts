import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChats } from '../chats.model';

@Component({
  selector: 'jhi-chats-detail',
  templateUrl: './chats-detail.component.html',
})
export class ChatsDetailComponent implements OnInit {
  chats: IChats | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chats }) => {
      this.chats = chats;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
