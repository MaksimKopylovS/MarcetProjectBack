import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ProductModel} from "src/app/models/product.model";
import {CartService} from "src/app/services/cart.service";
import {FileSaverService} from "src/app/services/file-saver.service";
import {StoreService} from "src/app/services/store.service";


@Component({
  templateUrl: "./store-item-detail.component.html"
})
export class StoreItemDetailComponent {
  id !: number;
  item !: ProductModel;
  image!: any

  retrieveResponse: any;
  base64Data: any;
  retrivedImage: any;

  constructor(private storeService: StoreService,
              private cartService: CartService,
              private route: ActivatedRoute,
              private fileService: FileSaverService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.storeService.getItem(this.id).subscribe(item => {
      this.item = item
      this.fileService.downloadFile2(this.item.id).subscribe(
        image => {
          this.retrieveResponse = image;
          this.base64Data = this.retrieveResponse.imageByte;
          this.image = 'data:image/jpeg;base64,' + this.base64Data;
        },
        err => console.error(err)
      )
    })
  }

  add() {
    this.cartService.add(this.item)
  }
}
