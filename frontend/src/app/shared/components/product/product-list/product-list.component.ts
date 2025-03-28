import { Component } from '@angular/core';
import {ProductFilterComponent} from '../product-filter/product-filter.component';

@Component({
  selector: 'app-product-list',
  imports: [
    ProductFilterComponent
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent {

}
