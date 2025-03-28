import {Component, inject, OnInit} from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {ButtonComponent} from '../../button/button.component';
import {Category} from '../../../../core/models/categoty.interface';
import {CategoryService} from '../../../../core/services/category/category.service';
import {ScrollbarDirective} from '../../../../core/directives/scrollbar/scrollbar.directive';

@Component({
  selector: 'app-product-filter',
  imports: [
    NgOptimizedImage,
    ButtonComponent,
    ScrollbarDirective
  ],
  templateUrl: './product-filter.component.html',
  styleUrl: './product-filter.component.scss'
})
export class ProductFilterComponent implements OnInit {
  private categoryService: CategoryService = inject(CategoryService);
  protected rootCategories: Category[] = [];

  protected selectedCategory: Category | null = null;

  ngOnInit(): void {
    this.categoryService.rootCategories().subscribe(
      categories => this.rootCategories = categories
    );
  }

  selectCategory(category: Category): void {
    this.selectedCategory = category;
  }

}
