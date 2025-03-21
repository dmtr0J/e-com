import {Directive, inject, TemplateRef} from '@angular/core';
import {TemplateCaptureDirective} from '../template-capture/template-capture.directive';

@Directive({
  selector: '[appInputPrefixTemplate]'
})
export class InputPrefixDirective extends TemplateCaptureDirective {

}
