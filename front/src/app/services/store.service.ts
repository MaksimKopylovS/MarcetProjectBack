import {HttpClient, HttpParams} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";
import {CategoryItemModel} from "../models/category-item.model";
import {ProductModel} from "../models/product.model";

const CATEGORIES_URL = '/api/category'
const PRODUCTS_URL = '/api/products'

@Injectable()
export class StoreService {
  private productsSubject = new BehaviorSubject<any>([])
  public products: Observable<any>

  constructor(private httpClient: HttpClient) {
    this.products = this.productsSubject.asObservable()
  }

  getAllItems(): Observable<ProductModel[]> {
    return this.httpClient.get<ProductModel[]>(PRODUCTS_URL + '/get-products')
  }

  getItems(pageNum: number, pageSize: number, categoryId?: number) {
    let params = new HttpParams().set('page', pageNum)
    if (pageSize) params = params.set('pageSize', pageSize)
    if (categoryId) params = params.set('id_category', categoryId)
    this.httpClient.get(PRODUCTS_URL + '/get-all', {params: params})
      .subscribe(
        items => {
          this.productsSubject.next(items)
        }
      )
  }

  getItem(id: number): Observable<ProductModel> {
    return this.httpClient.get<ProductModel>(PRODUCTS_URL + '/' + id);
  }

  getCategories(): Observable<CategoryItemModel[]> {
    return this.httpClient.get<CategoryItemModel[]>(CATEGORIES_URL + '/get-all')
  }

  updateCategory(categoryId: number) {
    if (categoryId == -1) this.getItems(1, 12)
    this.getItems(1, 12, categoryId)
  }
}
