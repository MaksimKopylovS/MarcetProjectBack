import {NgModule} from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http'
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HeaderPanelComponent} from './header/header-panel.component';

import {NotificationService} from './notifications/notification.service';
import {NotificationContainerComponent} from './notifications/notification-container.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './routes';

import {Error404Component} from './errors/404.component';

import {FooterPanelComponent} from './footer/footer-panel.component';
import {CartComponent} from './cart/cart.component';
import {CartItemComponent} from './cart/cart-item.component';
import {AuthInterceptor} from './interceptors/auth.interceptor';
import {OrderComponent} from './order/order.component';
import {OrderItemComponent} from './order/order-item/order-item.component';
import {ProfileComponent} from './profile/profile.component';
import {LoginComponent} from './profile/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RegistrationComponent} from './profile/registration/registration.component';
import {StoreComponent} from './store/store.component';
import {StoreItemComponent} from './store/store-item/store-item.component';
import {StoreItemDetailComponent} from './store/store-item-detail/store-item-detail.component';
import {CategoryComponent} from './store/category/category.component';
import {CategoryItemComponent} from './store/category-item/category-item.component';

import {CartService} from './services/cart.service';
import {OrderService} from './services/order.service';
import {AuthService} from './services/auth.service';
import {AdminPanelService} from './services/admin-panel.service';
import {StoreService} from './services/store.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderPanelComponent,
    FooterPanelComponent,
    CartComponent,
    CartItemComponent,
    StoreComponent,
    StoreItemComponent,
    StoreItemDetailComponent,
    CategoryComponent,
    CategoryItemComponent,
    NotificationContainerComponent,
    Error404Component,
    OrderComponent,
    OrderItemComponent,
    ProfileComponent,
    RegistrationComponent,
    LoginComponent,
  ],
  imports: [
    RouterModule.forRoot(appRoutes),
    NgbModule,
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    NotificationService,
    CartService,
    OrderService,
    AuthService,
    AdminPanelService,
    StoreService
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
