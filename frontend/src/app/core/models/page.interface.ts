export interface Page<T> {
  totalElements: number;
  index: number;
  size: number;
  items: T[];
}
