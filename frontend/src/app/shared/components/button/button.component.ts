import {Component, Input} from '@angular/core';
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

  @Input() type: 'primary' | 'rounded' | 'outlined' = 'primary';
  @Input() transparent: boolean = false;

  @Input() fontSize: 'xl' | 'l' | 'm' | 's' | 'xs' = 'm';
  @Input() height: string = '52px';
  @Input() width: string = 'auto';

  get buttonClasses(): string[] {
    return [
      'button',
      `button--${this.type}`,
      this.transparent ? `button--${this.type}--transparent` : '',
      `button--${this.fontSize}`
    ];
  }

  get spanClasses(): string {
    if (this.type === 'outlined') {
      return this.transparent ? 'button--outlined--transparent' : 'button--outlined--text-underline';
    }
    return '';
  }
}
