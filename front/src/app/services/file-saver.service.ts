import {HttpClient, HttpEvent, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ImageModel} from '../models/image.model';
import {ProductModel} from "../models/product.model";

const IMAGE_URL: string = '/api/admin'
const GET_IMAGE_URL: string = '/api/products/getimage'

@Injectable({
  providedIn: 'root'
})
export class FileSaverService {

  constructor(private httpClient: HttpClient) {
  }

  uploadFile(fileName: string, file: File): Observable<ImageModel> {
    let uploadData = new FormData()
    uploadData.append('myFile', file, fileName)

    return this.httpClient.put<ImageModel>(IMAGE_URL, uploadData)
  }

  downloadFile(fileId?: ProductModel): Observable<ImageModel> {
    let httpParams = new HttpParams()
    return this.httpClient.put<ImageModel>(GET_IMAGE_URL, fileId, {
      params: httpParams
    })
  }

  downloadFile2(id?: number) {
    let httpParams = new HttpParams()
    return this.httpClient.get(GET_IMAGE_URL + "/" + id, {
      params: httpParams
    })
  }

  upload(formData: any): Observable<HttpEvent<string[]>> {
    let httpParams = new HttpParams();
    let arrayProduct = new Array();
    arrayProduct.push(formData)
    return this.httpClient.post<string[]>(IMAGE_URL + '/create', arrayProduct, {
      reportProgress: true,
      observe: 'events',
      params: httpParams
    })
  }

  uploadT(product: ProductModel, formData: FormData): Observable<HttpEvent<string[]>> {
    let httpParams = new HttpParams();
    formData.append("product", JSON.stringify(product))
    return this.httpClient.post<string[]>(IMAGE_URL + '/create', formData, {
      reportProgress: true,
      observe: 'events',
      params: httpParams
    })
  }

  editProduct(product: ProductModel, formData: FormData): Observable<HttpEvent<string[]>> {
    let httpParams = new HttpParams();
    formData.append("product", JSON.stringify(product))
    return this.httpClient.post<string[]>(IMAGE_URL + '/edit', formData, {
      reportProgress: true,
      observe: 'events',
      params: httpParams
    })
  }
}
