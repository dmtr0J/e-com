export interface PageResponse<T> {
  totalElements: number;
  index: number;
  size: number;
  items: T[];
}
