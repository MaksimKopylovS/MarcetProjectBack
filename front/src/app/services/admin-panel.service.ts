import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {ProductModel} from '../models/product.model';
import {StoreService} from './store.service';

const ADMIN_ITEMS = '/api/admin'
const HTTP_HEADERS = new HttpHeaders({'Content-Type': 'application/json'})

@Injectable({
  providedIn: 'root'
})
export class AdminPanelService {
  private itemsSubject = new BehaviorSubject<any>([]);

  private productsSubjectTest = new Array();
  public products: Observable<any>

  constructor(private storeService: StoreService, private httpClient: HttpClient) {
    this.products = this.itemsSubject.asObservable()
  }

  getItemsSubject(): BehaviorSubject<ProductModel[]> {
    this.getItems()
    return this.itemsSubject
  }

  getItemsPaginations(pageNum: number, pageSize?: number, categoryId?: number) {
    let params = new HttpParams().set('page', pageNum)
    if (pageSize) params = params.set('pageSize', pageSize)
    if (categoryId) params = params.set('id_category', categoryId)

    this.httpClient.get(ADMIN_ITEMS + '/get-all', {params: params})
      .subscribe(
        items => {
          this.itemsSubject.next(items)
        }
      )
  }

  getItems() {
    this.httpClient.get<ProductModel[]>(ADMIN_ITEMS + '/get-all')
      .subscribe(ok => {
        this.itemsSubject.next(ok)
        return this.itemsSubject;
      })
  }

  getItemsTest(pageNum: number, pageSize: number, categoryId?: number) {
    let params = new HttpParams().set('page', pageNum)
    if (pageSize) params = params.set('pageSize', pageSize)
    if (categoryId) params = params.set('id_category', categoryId)

    this.httpClient.get(ADMIN_ITEMS + '/get-all', {params: params})
      .subscribe((data: any) => {
        this.productsSubjectTest = new Array(data.content)
      })
  }

  getItem(id: number): Observable<ProductModel> {
    return this.storeService.getItem(id);
  }

  deleteItem(item: ProductModel) {
    this.httpClient.delete(ADMIN_ITEMS + '/' + item.id, {'headers': HTTP_HEADERS})
      .subscribe(ok => {
        this.getItems()
      })
  }

  editItem(item: ProductModel) {
    this.httpClient.put(ADMIN_ITEMS + '/create', item, {'headers': HTTP_HEADERS})
      .subscribe(ok => {
        this.getItems()
      })
  }

  createItem(formData: FormData): Observable<string[]> {
    return this.httpClient.post<string[]>(ADMIN_ITEMS + '/create', formData, {'headers': HTTP_HEADERS});
  }
}
