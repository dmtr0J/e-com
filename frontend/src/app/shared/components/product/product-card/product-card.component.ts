import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnInit,
  ViewChild,
  viewChild
} from '@angular/core';
import {ButtonComponent} from '../../button/button.component';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-product-card',
  imports: [
    ButtonComponent,
    NgOptimizedImage
  ],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.scss'
})
export class ProductCardComponent implements AfterViewInit {

  @Input() title: string = 'title';
  @Input() price: string = '$199.00';
  @Input() description: string = 'description';

  @ViewChild('imageContainer') imageContainer!: ElementRef;
  @ViewChild(ButtonComponent, { read: ElementRef }) cardButton!: ElementRef;

  ngAfterViewInit(): void {
    this.updateButtonPosition();
  }

  updateButtonPosition(): void {
    if (this.imageContainer && this.cardButton) {
      const imageContainerHeight: number = this.imageContainer.nativeElement.offsetHeight;
      const cardButtonElement = this.cardButton.nativeElement;

      cardButtonElement.style.top = `${imageContainerHeight - (16 + cardButtonElement.offsetHeight)}px`;
    }
  }

}
