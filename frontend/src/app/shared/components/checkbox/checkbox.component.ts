import {Component, forwardRef, Host, inject, Input, Optional} from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR, NgControl} from '@angular/forms';

@Component({
  selector: 'app-checkbox',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './checkbox.component.html',
  styleUrl: './checkbox.component.scss',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CheckboxComponent),
      multi: true
    }
  ]
})
export class CheckboxComponent implements ControlValueAccessor {
  @Input() checked: boolean = false;
  @Input({ required : true }) form!: FormGroup;
  @Input({ required : true }) controlName!: string;

  onChange: (value: boolean) => void = () => {};
  onTouched: () => void = () => {};

  isTouched: boolean = false;

  get formControl() {
    return this.form.get(this.controlName);
  }

  get isValid(): boolean {
    return !!this.formControl?.valid;
  }

  toggleCheckbox(): void {
    this.checked = !this.checked;
    this.isTouched = true;
    this.onChange(this.checked);
    this.onTouched();
  }

  writeValue(value: boolean): void {
    this.checked = value;
  }

  registerOnChange(fn: (value: boolean) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }
}
