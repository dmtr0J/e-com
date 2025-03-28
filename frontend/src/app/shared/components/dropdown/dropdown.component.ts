import {Component, EventEmitter, Input, OnInit, Output, signal, WritableSignal} from '@angular/core';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-dropdown',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './dropdown.component.html',
  styleUrl: './dropdown.component.scss'
})
export class DropdownComponent implements OnInit {
  @Input() options: string[] = [];
  @Input() defaultValue: string = '';
  @Input() emptyMessage: string = 'No options available';

  @Output() selectionChange: EventEmitter<string> = new EventEmitter();

  isOpen: WritableSignal<boolean> = signal<boolean>(false);
  selectedValue: WritableSignal<string | null> = signal(null);

  ngOnInit(): void {
    if (this.defaultValue && this.options.includes(this.defaultValue)) {
      this.selectedValue.set(this.defaultValue);
    }
  }

  toggleDropdown(): void {
    this.isOpen.set(!this.isOpen());
  }

  selectOption(option: string): void {
    this.selectedValue.set(option);
    this.isOpen.set(false);
    this.selectionChange.emit(option);
  }
}
