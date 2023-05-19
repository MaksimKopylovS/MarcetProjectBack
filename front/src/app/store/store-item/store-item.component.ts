import {Component, Input, OnInit} from '@angular/core';
import {ProductModel} from 'src/app/models/product.model';
import {NotificationService} from 'src/app/notifications/notification.service';
import {CartService} from 'src/app/services/cart.service';
import {FileSaverService} from 'src/app/services/file-saver.service';

@Component({
  selector: 'store-item',
  templateUrl: './store-item.component.html',
  styleUrls: ['./store-item.component.css']
})
export class StoreItemComponent implements OnInit {
  @Input() item !: ProductModel
  image!: any
  imageTest!: string;

  retrieveResponse: any;
  base64Data: any;
  retrivedImage: any;

  constructor(
    private notificationService: NotificationService,
    private fileService: FileSaverService,
    private cartService: CartService) {
  }

  ngOnInit() {
    this.fileService.downloadFile2(this.item.id).subscribe(
      res => {
        this.retrieveResponse = res;
        this.base64Data = this.retrieveResponse.imageByte;
        this.image = 'data:image/jpeg;base64,' + this.base64Data;
      },
    )

    if (!this.item) {
      console.error('No file')
      return
    }
    if (!this.item.photoUrl) {
      return
    }
  }

  buyButtonClickHandler() {
    this.cartService.add(this.item)
    this.notificationService.showSuccess(this.item.title + ' added');
  }
}
