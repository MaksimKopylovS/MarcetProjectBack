import {Component, OnInit} from "@angular/core";
import {BasketItemModel} from "../models/basket-item.model";
import {CartService} from "../services/cart.service";

@Component({
  templateUrl: './cart.component.html'
})
export class CartComponent implements OnInit {
  cartItems: BasketItemModel[] = []

  constructor(private cartService: CartService) {
  }

  ngOnInit() {
    this.cartService.getSubject().subscribe(
      items => {
        console.log(items)
        this.cartItems = items
      }, err => {
        console.error(err)
      }
    )
  }

  createOrder() {
    this.cartService.createOrder()
  }
}
