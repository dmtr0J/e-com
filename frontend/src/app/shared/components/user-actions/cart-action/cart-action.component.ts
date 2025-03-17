import {Component, signal, WritableSignal} from '@angular/core';
import {NgClass, NgOptimizedImage} from '@angular/common';
import {CartDrawerComponent} from '../../cart-drawer/cart-drawer.component';

@Component({
  selector: 'app-cart-action',
  imports: [
    NgOptimizedImage,
    NgClass,
    CartDrawerComponent
  ],
  templateUrl: './cart-action.component.html',
  styleUrl: './cart-action.component.scss'
})
export class CartActionComponent {
  cartItemCount = signal(0);
  isCartOpen = signal(false);

  get displayCount(): string {
    return this.cartItemCount() > 9 ? '9+' : this.cartItemCount().toString();
  }

  toggleCart(): void {
    this.isCartOpen.set(!this.isCartOpen());
  }

}
