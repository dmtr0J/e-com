import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-button',
  imports: [
    NgClass
  ],
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss'
})
export class ButtonComponent {
  @Input() label: string = '';
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
  @Input() style: 'primary' | 'rounded' | 'outlined' = 'primary';
  @Input() transparent: boolean = false;

  get buttonClasses(): string[] {
    return [
      'button',
      `button--${this.style}`,
      this.transparent ? `button--${this.style}--transparent` : ''
    ];
  }

  get spanClasses(): string {
    if (this.style === 'outlined') {
      return this.transparent ? 'button--outlined--transparent' : 'button--outlined--text-underline';
    }
    return '';
  }
}
