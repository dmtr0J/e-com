import {Directive, ElementRef, inject, Input, OnInit, Renderer2} from '@angular/core';

@Directive({
  selector: '[appScrollbar]'
})
export class ScrollbarDirective implements OnInit {
  @Input() scrollbarMaxHeight: string = '300px';
  @Input() overflowType: string = 'auto';

  private el: ElementRef = inject(ElementRef);
  private renderer: Renderer2 = inject(Renderer2);

  ngOnInit(): void {
    this.renderer.setStyle(this.el.nativeElement, 'overflow', this.overflowType);
    this.renderer.setStyle(this.el.nativeElement, 'max-height', this.scrollbarMaxHeight);
  }

}
