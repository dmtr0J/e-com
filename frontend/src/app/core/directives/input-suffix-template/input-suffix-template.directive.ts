import {Directive, inject, TemplateRef} from '@angular/core';

@Directive({
  selector: '[appInputSuffixTemplate]'
})
export class InputSuffixTemplateDirective {
  template: TemplateRef<any> = inject(TemplateRef);
}
