import {
  Component,
  contentChild,
  ElementRef,
  Input,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {NgTemplateOutlet} from '@angular/common';
import {InputSuffixTemplateDirective} from '../../../core/directives/input-suffix-template/input-suffix-template.directive';
import {InputPrefixTemplateDirective} from '../../../core/directives/input-prefix-template/input-prefix-template.directive';

@Component({
  selector: 'app-input',
  imports: [
    NgTemplateOutlet,
  ],
  templateUrl: './input.component.html',
  styleUrl: './input.component.scss'
})
export class InputComponent {
  @Input() type: 'text' | 'email' | 'password' = 'text';
  @Input() placeholder: string = '';

  @ViewChild('inputControl') inputControl!: ElementRef;

  @ViewChild('passwordPrefix') passwordPrefixTemplate!: TemplateRef<any>;
  @ViewChild('passwordSuffix') passwordSuffixTemplate!: TemplateRef<any>;

  protected prefixTemplate = contentChild(InputPrefixTemplateDirective);
  protected suffixTemplate = contentChild(InputSuffixTemplateDirective);

  private prefixTemplates(): Record<string, TemplateRef<any>> {
    return {
    };
  }

  private suffixTemplates(): Record<string, TemplateRef<any>> {
    return {
      password: this.passwordSuffixTemplate,
    };
  }

  protected focusInputControl(): void {
    if (document.activeElement !== this.inputControl.nativeElement) {
      this.inputControl.nativeElement.focus();
    }
  }

  getPrefixTemplate(): TemplateRef<any> {
    return this.prefixTemplates()[this.type];
  }

  getSuffixTemplate(): TemplateRef<any> {
    return this.suffixTemplates()[this.type];
  }
}
