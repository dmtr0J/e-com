import { Component } from '@angular/core';
import {LogoComponent} from '../../components/logo/logo.component';
import {NgOptimizedImage} from '@angular/common';
import {CartActionComponent} from '../../components/user-actions/cart-action/cart-action.component';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-header',
  imports: [
    LogoComponent,
    NgOptimizedImage,
    CartActionComponent,
    FormsModule,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
}
