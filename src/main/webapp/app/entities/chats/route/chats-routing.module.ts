import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChatsComponent } from '../list/chats.component';
import { ChatsDetailComponent } from '../detail/chats-detail.component';
import { ChatsUpdateComponent } from '../update/chats-update.component';
import { ChatsRoutingResolveService } from './chats-routing-resolve.service';

const chatsRoute: Routes = [
  {
    path: '',
    component: ChatsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChatsDetailComponent,
    resolve: {
      chats: ChatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChatsUpdateComponent,
    resolve: {
      chats: ChatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChatsUpdateComponent,
    resolve: {
      chats: ChatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(chatsRoute)],
  exports: [RouterModule],
})
export class ChatsRoutingModule {}
