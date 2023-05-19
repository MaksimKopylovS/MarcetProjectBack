import {Component, OnInit} from '@angular/core';
import {StoreService} from '../services/store.service';

@Component({
  selector: 'store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {
  items!: any
  pageNum = 1
  pageSize = 12
  totalElems!: number

  constructor(private storeService: StoreService) {
  }

  ngOnInit(): void {
    this.storeService.products.subscribe(items => {
      this.items = items.content
      // console.log(this.items)
      this.totalElems = items.totalElements;
    })
    this.onPageChange(this.pageNum)
  }

  public onPageChange(pageNum: number): void {
    this.storeService.getItems(pageNum, this.pageSize)
  }
}
