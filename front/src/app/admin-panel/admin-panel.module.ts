import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {AdminPanelItemEditComponent} from "./admin-panel-item-edit/admin-panel-item-edit.component";
import {AdminPanelItemComponent} from "./admin-panel-item/admin-panel-item.component";
import {AdminPanelComponent} from "./admin-panel.component";
import {adminPanelRoutes} from "./admin-panel.route";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(adminPanelRoutes),
    NgbModule
  ],
  declarations: [
    AdminPanelComponent,
    AdminPanelItemComponent,
    AdminPanelItemEditComponent
  ],
  providers: []
})
export class AdminPanelModule {
}
