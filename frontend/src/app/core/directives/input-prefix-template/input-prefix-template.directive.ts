import {Directive, inject, TemplateRef} from '@angular/core';

@Directive({
  selector: '[appInputPrefixTemplate]'
})
export class InputPrefixTemplateDirective {
  template: TemplateRef<any> = inject(TemplateRef);
}
