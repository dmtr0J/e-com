import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthResponse} from './models/auth-response.interface';
import {LoginRequest} from './models/login-request.interface';
import {RegisterRequest} from './models/register-request.interface';
import {environment} from '../../../../environments/environment';
import {AuthTokens} from './models/auth-token.interface';
import {catchError, Observable, tap, throwError} from 'rxjs';
import {Router} from '@angular/router';
import {STORAGE_KEY} from '../../util/storage-keys';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly authBaseUrl: string;

  constructor(private http: HttpClient, private router: Router) {
    this.authBaseUrl = environment.apiUrl + '/auth';
  }

  get tokens(): AuthTokens {
    return {
      accessToken: localStorage.getItem(STORAGE_KEY.ACCESS_TOKEN),
      refreshToken: localStorage.getItem(STORAGE_KEY.REFRESH_TOKEN)
    };
  }

  get isAuthenticated(): boolean {
    return !!localStorage.getItem(STORAGE_KEY.ACCESS_TOKEN) && !!localStorage.getItem(STORAGE_KEY.REFRESH_TOKEN);
  }

  register(payload: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${this.authBaseUrl}/register`,
      payload
    ).pipe(
      tap(res => this.saveTokensFromResponse(res))
    );
  }

  loginWithEmail(payload: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${this.authBaseUrl}/login`,
      payload
    ).pipe(
      tap(res => this.saveTokensFromResponse(res))
    );
  }

  refreshAuthTokens(): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${this.authBaseUrl}/refresh`,
      { refreshToken: this.tokens.refreshToken }
    ).pipe(
      tap(res => this.saveTokensFromResponse(res)),
      catchError(error => {
        // TODO
        // this.logout();
        this.deleteTokens();
        return throwError(() => error);
      })
    );
  }

  // TODO
  // Only logged user can logout
  logout(): void {
    this.http.post(
      `${this.authBaseUrl}/logout`,
      null
    );
    this.deleteTokens();
    //this.router.createUrlTree(['']);
  }

  private saveTokensFromResponse(authTokenResponse: AuthResponse): void {
    localStorage.setItem(STORAGE_KEY.ACCESS_TOKEN, authTokenResponse.accessToken);
    localStorage.setItem(STORAGE_KEY.REFRESH_TOKEN, authTokenResponse.refreshToken);
  }

  private deleteTokens(): void {
    localStorage.removeItem(STORAGE_KEY.ACCESS_TOKEN);
    localStorage.removeItem(STORAGE_KEY.REFRESH_TOKEN);
  }

}
