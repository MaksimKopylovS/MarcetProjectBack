import {Component, OnInit} from '@angular/core';
import {OrderModel} from '../models/order.model';
import {OrderService} from '../services/order.service';

@Component({
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  items!: OrderModel[]
  orderModel!: OrderModel;

  constructor(private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.orderService.getItems
    this.orderService.order.subscribe(
      items => {
        this.items = items
      }
    )
  }
}
