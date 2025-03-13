import {inject} from '@angular/core';
import {AuthService} from '../services/auth/auth.service';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import {STORAGE_KEY} from '../util/storage-keys';
import {User} from '../models/user.interface';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): MaybeAsync<GuardResult> => {
  const router: Router = inject(Router);
  return checkAuth(route) ? true : router.navigate(['/login']);
}

const checkAuth = (route: ActivatedRouteSnapshot): boolean => {
  const authService: AuthService = inject(AuthService);
  //const requiredRoles: string[] = route.data['roles'];
  //const profileJson: string | null = localStorage.getItem(STORAGE_KEY.PROFILE);

  if (authService.isAuthenticated) {
    /*if (!profileJson) {
      return false;
    }*/
    //return requiredRoles.includes(JSON.parse(profileJson).role);
    return true;
  } else {
    return false;
  }
}




