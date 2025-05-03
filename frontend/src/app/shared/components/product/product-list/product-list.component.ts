import {Component, inject, OnInit} from '@angular/core';
import {ProductFilterComponent} from '../product-filter/product-filter.component';
import {ProductCardComponent} from '../product-card/product-card.component';
import {ButtonComponent} from '../../button/button.component';
import {ProductService} from '../../../../core/services/product/product.service';
import {Product} from '../../../../core/models/product.interface';

@Component({
  selector: 'app-product-list',
  imports: [
    ProductFilterComponent,
    ProductCardComponent,
    ButtonComponent
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit {
  productService: ProductService = inject(ProductService);
  products: Product[] = [];
  isLoading: boolean = false;
  error: string | null = null;

  ngOnInit() {
    this.loadAllProducts();
  }

  loadAllProducts() {
    this.isLoading = true;
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data.items;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Failed to load products.';
        console.error(err);
        this.isLoading = false;
      }
    });
  }




}
