import {Component, Input} from "@angular/core";
import {BasketItemModel} from "../models/basket-item.model";
import {CartService} from "../services/cart.service";

@Component({
  selector: 'cart-item',
  templateUrl: './cart-item.component.html',
  styles: [`
    .cart-item {
      background-color: #FFF;
      background-clip: border-box;
      border: 1px solid rgba(0, 0, 0, 0.125);
      border-radius: 0.25rem;
      overflow: hidden;
    }

    .cart-image-size {
      height: 3.75rem;
      width: 3.75rem;
      vertical-align: middle;
    }
  `]
})
export class CartItemComponent {
  @Input() item !: BasketItemModel

  constructor(private cartService: CartService) {
  }

  onMinus() {
    this.cartService.removeOne(this.item.productDTO)
  }

  onPlus() {
    this.cartService.add(this.item.productDTO)
  }
}
