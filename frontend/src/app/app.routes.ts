import { Routes } from '@angular/router';
import {AuthComponent} from './features/auth/auth.component';
import {LoginFormComponent} from './features/auth/login-form/login-form.component';
import {RegisterFormComponent} from './features/auth/register-form/register-form.component';

export const routes: Routes = [
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      { path: 'login', component: LoginFormComponent },
      { path: 'register', component: RegisterFormComponent },
    ]
  },
  // { path: 'login', component: LoginComponent },
  // { path: 'register', component: RegisterComponent },
  // { path: '', component: HomeComponent },
  // {
  //   path: 'profile',
  //   component: ProfileComponent,
  //   canActivate: [authGuard],
  //   data: {
  //     roles: ['ADMIN']
  //   }
  // },
];
