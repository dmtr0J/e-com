import { Routes } from '@angular/router';
import {AuthComponent} from './features/auth/auth.component';
import {LoginFormComponent} from './features/auth/login-form/login-form.component';
import {RegisterFormComponent} from './features/auth/register-form/register-form.component';
import {HomeComponent} from './features/home/home.component';
import {ShopComponent} from './features/shop/shop.component';
import {ProductCardComponent} from './shared/components/product/product-card/product-card.component';
import {ProductListComponent} from './shared/components/product/product-list/product-list.component';
import {DropdownComponent} from './shared/components/dropdown/dropdown.component';
import {ProfileComponent} from './features/profile/profile.component';
import {authGuard} from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      { path: 'login', component: LoginFormComponent },
      { path: 'register', component: RegisterFormComponent },
    ]
  },
  { path: '', component: HomeComponent },
  { path: 'shop', component: ShopComponent },
  {
    path: 'profile',
    component: ProfileComponent,
    data: {
      roles: ['USER', 'ADMIN']
    },
    canActivate: [authGuard]
  },
];
