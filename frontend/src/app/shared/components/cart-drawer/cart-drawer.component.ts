import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgClass} from '@angular/common';
import {ButtonComponent} from '../button/button.component';

@Component({
  selector: 'app-cart-drawer',
  imports: [
    NgClass,
    ButtonComponent
  ],
  templateUrl: './cart-drawer.component.html',
  styleUrl: './cart-drawer.component.scss'
})
export class CartDrawerComponent {
  @Input() isOpen: boolean = false;
  @Output() close: EventEmitter<void> = new EventEmitter<void>();

  closeDrawer(): void {
    this.close.emit();
  }

}
