import {HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {AuthService} from '../services/auth/auth.service';
import {BehaviorSubject, catchError, filter, Observable, switchMap, tap, throwError} from 'rxjs';
import {inject} from '@angular/core';

const isRefreshing$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

export const authTokenInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  const authService: AuthService = inject(AuthService);
  const accessToken: string | null = authService.tokens.accessToken;

  if (!accessToken) {
    return next(req);
  }

  if (isRefreshing$.value) {
    return refreshAndProceed(authService, req, next);
  }

  return next(addAuthHeader(req, accessToken))
    .pipe(
      catchError(error => {
        if (error.status === 401) {
          return refreshAndProceed(authService, req, next);
        }
        return throwError(() => error);
      })
    );
}

const refreshAndProceed = (
  authService: AuthService,
  req: HttpRequest<any>,
  next: HttpHandlerFn
) => {
  if (!isRefreshing$.value) {
    isRefreshing$.next(true);
    return authService.refreshAuthTokens()
      .pipe(
        switchMap(res => {
          return next(addAuthHeader(req, res.accessToken))
            .pipe(
              tap(() => isRefreshing$.next(false))
            );
        })
      )
  }

  if (req.url.includes('refresh')) {
    return next(addAuthHeader(req, authService.tokens.accessToken!));
  }

  return isRefreshing$.pipe(
    filter(isRefreshing => !isRefreshing),
    switchMap(() => {
      return next(addAuthHeader(req, authService.tokens.accessToken!));
    })
  );
}

const addAuthHeader = (req: HttpRequest<any>, accessToken: string) => {
  return req.clone({
    setHeaders: {
      Authorization: `Bearer ${accessToken}`
    }
  });
}
