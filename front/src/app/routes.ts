import {Routes} from "@angular/router";
import {Error404Component} from "./errors/404.component";
import {CartComponent} from "./cart/cart.component";
import {OrderComponent} from "./order/order.component";
import {StoreComponent} from "./store/store.component";
import {StoreItemDetailComponent} from "./store/store-item-detail/store-item-detail.component";

export const appRoutes: Routes = [
  {path: '', redirectTo: '/store', pathMatch: 'full'},
  {path: 'cart', component: CartComponent},
  {path: 'store', component: StoreComponent},
  {path: 'order', component: OrderComponent},
  {path: 'store/:id', component: StoreItemDetailComponent},
  {
    path: 'admin-panel',
    loadChildren: () => import('./admin-panel/admin-panel.module')
      .then(m => m.AdminPanelModule)
  },
  {path: '404', component: Error404Component}
]
