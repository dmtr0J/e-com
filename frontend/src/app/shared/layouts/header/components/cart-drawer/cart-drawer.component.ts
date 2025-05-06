import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {NgClass, NgOptimizedImage} from '@angular/common';
import {ButtonComponent} from '../../../../components/button/button.component';

@Component({
  selector: 'app-cart-drawer',
  imports: [
    NgClass,
    ButtonComponent,
    NgOptimizedImage,
  ],
  templateUrl: './cart-drawer.component.html',
  styleUrl: './cart-drawer.component.scss'
})
export class CartDrawerComponent {
  @Input() isOpen: boolean = false;
  @Output() close: EventEmitter<void> = new EventEmitter();

  closeDrawer(): void {
    this.close.emit();
  }

}
