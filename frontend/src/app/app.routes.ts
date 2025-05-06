import { Routes } from '@angular/router';
import {AuthComponent} from './features/auth/auth.component';
import {LoginFormComponent} from './features/auth/login-form/login-form.component';
import {RegisterFormComponent} from './features/auth/register-form/register-form.component';
import {HomeComponent} from './features/home/home.component';
import {ShopComponent} from './features/shop/shop.component';
import {ProfileComponent} from './features/profile/profile.component';
import {authGuard} from './core/guards/auth.guard';
import {AdminPanelComponent} from './features/admin-panel/admin-panel.component';
import {DashboardComponent} from './features/admin-panel/components/dashboard/dashboard.component';
import {
  AdminProductListComponent
} from './features/admin-panel/components/admin-product-list/admin-product-list.component';
import {AdminOrderListComponent} from './features/admin-panel/components/admin-order-list/admin-order-list.component';

export const routes: Routes = [
  {
    path: 'admin',
    component: AdminPanelComponent,
    data: {
      roles: ['ADMIN']
    },
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'products', component: AdminProductListComponent },
      { path: 'orders', component: AdminOrderListComponent },
    ]
  },
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
