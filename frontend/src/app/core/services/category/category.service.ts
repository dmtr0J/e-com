import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {Category} from '../../models/categoty.interface';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private http: HttpClient = inject(HttpClient);
  private readonly categoryBaseUrl: string;

  constructor() {
    this.categoryBaseUrl = `${environment.apiUrl}/category`;
  }

  rootCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.categoryBaseUrl}/roots`);
  }


}
