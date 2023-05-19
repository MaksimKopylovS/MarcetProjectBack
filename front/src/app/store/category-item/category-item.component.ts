import {Component, Input} from '@angular/core';
import {CategoryItemModel} from 'src/app/models/category-item.model';
import {StoreService} from 'src/app/services/store.service';

@Component({
  selector: 'category-item',
  templateUrl: './category-item.component.html',
  styleUrls: ['./category-item.component.css']
})
export class CategoryItemComponent {
  @Input() category !: CategoryItemModel

  constructor(private service: StoreService) {
  }

  handleClick() {
    this.service.updateCategory(this.category.categoryId)
  }
}
