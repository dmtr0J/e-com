import {Directive, inject, TemplateRef} from '@angular/core';
import {TemplateCaptureDirective} from '../template-capture/template-capture.directive';

@Directive({
  selector: '[appInputSuffixTemplate]'
})
export class InputSuffixDirective extends TemplateCaptureDirective {
}
