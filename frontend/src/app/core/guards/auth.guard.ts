import {inject} from '@angular/core';
import {AuthService} from '../services/auth/auth.service';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  GuardResult,
  MaybeAsync,
  Router,
} from '@angular/router';
import {jwtDecode} from 'jwt-decode';
import {JwtPayload} from '../models/jwt-payload.interface';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot
): MaybeAsync<GuardResult> => {
  const router: Router = inject(Router);
  return checkAuth(route) ? true : router.navigate(['/auth/login']);
}

const checkAuth = (route: ActivatedRouteSnapshot): boolean => {
  const authService: AuthService = inject(AuthService);
  const requiredRoles: string[] = route.data['roles'];
  const accessToken: string | null = authService.tokens.accessToken;

  if (authService.isAuthenticated) {
    if (accessToken) {
      const role: string | undefined = getRoleFromAccessToken(accessToken);
      return role ? requiredRoles.includes(role) : false;
    } else {
      return false;
    }
  } else {
    return false;
  }
}

const getRoleFromAccessToken = (accessToken: string) => {
  return jwtDecode<JwtPayload>(accessToken).role;
}




