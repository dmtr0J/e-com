import { Component } from '@angular/core';
import {HeaderComponent} from '../../shared/layouts/header/header.component';
import {HeroComponent} from '../../shared/layouts/hero/hero.component';
import {DropdownComponent} from '../../shared/components/dropdown/dropdown.component';
import {ProductListComponent} from '../../shared/components/product/product-list/product-list.component';

@Component({
  selector: 'app-shop',
  imports: [
    HeaderComponent,
    HeroComponent,
    DropdownComponent,
    ProductListComponent
  ],
  templateUrl: './shop.component.html',
  styleUrl: './shop.component.scss'
})
export class ShopComponent {

}
