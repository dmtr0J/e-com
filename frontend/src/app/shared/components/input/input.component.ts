import {
  Component,
  contentChild, forwardRef, inject,
  Input, Optional, Self, signal,
  TemplateRef,
  ViewChild, WritableSignal
} from '@angular/core';
import {NgOptimizedImage, NgTemplateOutlet} from '@angular/common';
import {InputSuffixTemplateDirective} from '../../../core/directives/input-suffix-template/input-suffix-template.directive';
import {InputPrefixTemplateDirective} from '../../../core/directives/input-prefix-template/input-prefix-template.directive';
import {
  AbstractControl,
  ControlValueAccessor, FormControl, FormGroup, FormsModule,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR, NgControl,
  ValidationErrors,
  Validator
} from '@angular/forms';

@Component({
  selector: 'app-input',
  imports: [
    NgTemplateOutlet,
    NgOptimizedImage,
    FormsModule,
  ],
  templateUrl: './input.component.html',
  styleUrl: './input.component.scss',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputComponent),
      multi: true
    }
  ]
})
export class InputComponent implements ControlValueAccessor {
  @Input() type: 'text' | 'email' | 'password' = 'text';
  @Input() placeholder: string = '';
  @Input({ required : true }) form!: FormGroup;
  @Input({ required : true }) controlName!: string;

  @ViewChild('passwordSuffix') passwordSuffixTemplate!: TemplateRef<any>;

  prefixTemplate = contentChild(InputPrefixTemplateDirective);
  suffixTemplate = contentChild(InputSuffixTemplateDirective);

  isPasswordHidden: WritableSignal<boolean> = signal<boolean>(true);

  private cachedSuffixTemplate!: TemplateRef<any>;

  protected value: string | null = '';
  protected onChange: (value: string | null) => void = () => {};
  protected onTouched: () => void = () => {};

  get prefixTemplates(): Record<string, TemplateRef<any>> {
    return {};
  }

  get suffixTemplates(): Record<string, TemplateRef<any>> {
    return {
      password: this.passwordSuffixTemplate,
    };
  }

  get formControl() {
    return this.form.get(this.controlName);
  }

  get showError(): boolean {
    return !!this.formControl && (this.formControl?.touched || this.formControl?.dirty);
  }

  getPrefixTemplate(): TemplateRef<any> {
    return this.prefixTemplates[this.type];
  }

  getSuffixTemplate(): TemplateRef<any> {
    if (!this.cachedSuffixTemplate) {
      this.cachedSuffixTemplate = this.suffixTemplates[this.type];
    }
    return this.cachedSuffixTemplate;
  }

  togglePasswordVisibility(): void {
    this.isPasswordHidden.set(!this.isPasswordHidden());
    this.type = this.isPasswordHidden() ? 'password' : 'text';
  }

  writeValue(value: string | null): void {
    this.value = value;
  }

  registerOnChange(fn: (value: string | null) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  onInputChange(event: Event): void {
    this.value = (<HTMLInputElement>event.target).value;
    this.onChange(this.value);
  }

  onBlur(): void {
    this.onTouched();
  }
}
