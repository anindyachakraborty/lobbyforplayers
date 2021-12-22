import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'item',
        data: { pageTitle: 'lobbyforplayersApp.item.home.title' },
        loadChildren: () => import('./item/item.module').then(m => m.ItemModule),
      },
      {
        path: 'order',
        data: { pageTitle: 'lobbyforplayersApp.order.home.title' },
        loadChildren: () => import('./order/order.module').then(m => m.OrderModule),
      },
      {
        path: 'bargain',
        data: { pageTitle: 'lobbyforplayersApp.bargain.home.title' },
        loadChildren: () => import('./bargain/bargain.module').then(m => m.BargainModule),
      },
      {
        path: 'details',
        data: { pageTitle: 'lobbyforplayersApp.details.home.title' },
        loadChildren: () => import('./details/details.module').then(m => m.DetailsModule),
      },
      {
        path: 'tags',
        data: { pageTitle: 'lobbyforplayersApp.tags.home.title' },
        loadChildren: () => import('./tags/tags.module').then(m => m.TagsModule),
      },
      {
        path: 'chats',
        data: { pageTitle: 'lobbyforplayersApp.chats.home.title' },
        loadChildren: () => import('./chats/chats.module').then(m => m.ChatsModule),
      },
      {
        path: 'questionary',
        data: { pageTitle: 'lobbyforplayersApp.questionary.home.title' },
        loadChildren: () => import('./questionary/questionary.module').then(m => m.QuestionaryModule),
      },
      {
        path: 'reviews',
        data: { pageTitle: 'lobbyforplayersApp.reviews.home.title' },
        loadChildren: () => import('./reviews/reviews.module').then(m => m.ReviewsModule),
      },
      {
        path: 'payments',
        data: { pageTitle: 'lobbyforplayersApp.payments.home.title' },
        loadChildren: () => import('./payments/payments.module').then(m => m.PaymentsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
