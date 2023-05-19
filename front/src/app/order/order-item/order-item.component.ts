import {Component, Input, OnInit} from '@angular/core';
import {OrderModel} from 'src/app/models/order.model';
import {OrderService} from "../../services/order.service";
import {ProductModel} from "../../models/product.model";

@Component({
  selector: 'order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.css']
})
export class OrderItemComponent implements OnInit {
  @Input() item!: OrderModel
  productModel!: ProductModel[];

  constructor(private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.orderService.getOrder(this.item.order_id).subscribe((data: any) => {
      this.productModel = data.productDTO;
    })
  }

}
