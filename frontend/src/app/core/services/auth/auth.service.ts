import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthResponse} from './models/auth-response.interface';
import {LoginRequest} from './models/login-request.interface';
import {RegisterRequest} from './models/register-request.interface';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  http = inject(HttpClient);
  authBaseUrl = environment.apiUrl + '/auth';

  register(payload: RegisterRequest) {
    return this.http.post<AuthResponse>(
      `${this.authBaseUrl}/register`,
      payload
    );
  }

  loginWithEmail(payload: LoginRequest) {
    return this.http.post<AuthResponse>(
      `${this.authBaseUrl}/login`,
      payload
    );
  }

  refreshToken(token: string) {
    return this.http.post<AuthResponse>(
      `${this.authBaseUrl}/refresh`,
      token
    );
  }

  logout() {
    return this.http.post(
      `${this.authBaseUrl}/logout`,
      null
    );
  }

}
