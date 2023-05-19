import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";
import {BasketItemModel} from "../models/basket-item.model";
import {ProductModel} from "../models/product.model";
import {UserDtoModel} from "../models/userDto.model";
import {AuthService} from "./auth.service";

const HTTP_HEADERS = new HttpHeaders({
  'Content-Type': 'application/json',
  'Accept': 'application/json'
})

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartEndpoint = "/api/basket"
  private orderUrl = "/api/order"

  private subject = new BehaviorSubject<BasketItemModel[]>([])

  constructor(private httpClient: HttpClient,
              private authService: AuthService,
              private router: Router) {
    this.httpClient.get<BasketItemModel[]>(this.cartEndpoint + '/get-basket')
      .subscribe(
        items => {
          this.subject.next(items)
        },
        err => {
          console.error(err)
        }
      )
  }

  getSubject(): BehaviorSubject<BasketItemModel[]> {
    return this.subject
  }

  createOrder() {
    let user: UserDtoModel = this.authService.userValue

    if (!user) return

    let username = user.userDTO?.username
    let addressId = 1
    if (user.addressDTOList.length !== 0)
      addressId = user.addressDTOList[0].addressId

    if (!username || !addressId) return

    this.httpClient.post(this.orderUrl + '/create',
      {
        userName: username,
        addressId: addressId
      },
      {'headers': HTTP_HEADERS}
    ).subscribe(
      ok => {
        this.get()
        this.router.navigate(['/store'])
      },
      err => console.error(err)
    )
  }

  get() {
    this.httpClient.get<BasketItemModel[]>(this.cartEndpoint + '/get-basket', {
      'headers': HTTP_HEADERS
    }).subscribe(
      items => {
        this.subject.next(items)
      }, err => {
        console.error(err)
      }
    )
  }

  add(product: ProductModel) {
    this.httpClient.post<BasketItemModel[]>(this.cartEndpoint + '/add', product, {
      'headers': HTTP_HEADERS
    }).subscribe(
      items => {
        this.subject.next(items)
      }, err => {
        console.error(err)
      }
    )
  }

  removeOne(product: ProductModel) {
    this.httpClient.post<BasketItemModel[]>(this.cartEndpoint + '/decriment', product, {
      'headers': HTTP_HEADERS
    }).subscribe(
      items => {
        this.subject.next(items)
      }, err => {
        console.error(err)
      }
    )
  }

  remove(product: ProductModel) {
    this.httpClient.post<BasketItemModel[]>(this.cartEndpoint + '/del', product, {
      'headers': HTTP_HEADERS
    }).subscribe(
      items => {
        this.subject.next(items)
      }, err => {
        console.error(err)
      }
    )
  }
}
