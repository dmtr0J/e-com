import {Category} from './category.interface';

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  category: Category;
  imageName: string;
}
