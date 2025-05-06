import {inject, Injectable} from '@angular/core';
import {PageResponse} from '../../models/page-response.interface';
import {Product} from '../../models/product.interface';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  http: HttpClient = inject(HttpClient);
  baseProductUrl: string = environment.apiUrl;

  getAllProducts(): Observable<PageResponse<Product>> {
    return this.http.get<PageResponse<Product>>(
      `${environment.apiUrl}/product`
    );
  }
}
