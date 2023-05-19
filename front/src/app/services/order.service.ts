import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {OrderItemModel} from '../models/order-item.model';
import {OrderModel} from '../models/order.model';

const HTTP_HEADERS = new HttpHeaders({'Content-Type': 'application/json'})
const ORDER_URL = '/api/order'

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private orderSubject: BehaviorSubject<OrderModel[]> = new BehaviorSubject<OrderModel[]>([])
  public order: Observable<OrderModel[]>

  constructor(private httpClient: HttpClient) {
    this.order = this.orderSubject.asObservable()
    this.getItems()
  }

  getItems() {
    this.httpClient.get<OrderModel[]>(
      ORDER_URL,
      {headers: HTTP_HEADERS}
    ).subscribe(
      item => {
        this.orderSubject.next(item)
      },
      err => console.error(err)
    )
  }

  getOrder(id: bigint) {
    console.log("Prishloooo " + id)
    return this.httpClient.post<OrderItemModel[]>(
      ORDER_URL + "/show", id, {'headers': HTTP_HEADERS}
    )
  }
}
