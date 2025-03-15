import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../../models/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly userBaseUrl: string;

  constructor(private http: HttpClient) {
    this.userBaseUrl = environment.apiUrl + '/user';
  }

  currentUser(): Observable<User> {
    return this.http.get<User>(`${this.userBaseUrl}/current`);
  }
}
