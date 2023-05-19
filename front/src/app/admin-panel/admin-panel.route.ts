import { Routes } from "@angular/router";
import { AdminPanelItemEditComponent } from "./admin-panel-item-edit/admin-panel-item-edit.component";
import { AdminPanelComponent } from "./admin-panel.component";

export const adminPanelRoutes : Routes = [
    {path: '', component : AdminPanelComponent},
    {path: 'edit', component : AdminPanelItemEditComponent},
    {path: 'edit/:id', component : AdminPanelItemEditComponent}
]