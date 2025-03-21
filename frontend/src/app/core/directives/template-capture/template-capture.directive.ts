import {Directive, inject, TemplateRef} from '@angular/core';

@Directive({
  selector: '[appTemplateCapture]'
})
export class TemplateCaptureDirective {
  template: TemplateRef<any> = inject(TemplateRef);
}
