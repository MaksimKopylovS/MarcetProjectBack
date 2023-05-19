import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ProductModel } from 'src/app/models/product.model';
import { AdminPanelService } from 'src/app/services/admin-panel.service';


@Component({
  selector: 'admin-panel-item',
  templateUrl: './admin-panel-item.component.html',
  styleUrls: ['./admin-panel-item.component.css']
})
export class AdminPanelItemComponent {
  @Input() item!: ProductModel

  constructor(private adminPanelService : AdminPanelService,
    private router: Router) {}

  handleEdit() {
    this.router.navigate(['/admin-panel/edit/' + this.item.id])
  }

  handleDelete() {
    this.adminPanelService.deleteItem(this.item);
  }
}
