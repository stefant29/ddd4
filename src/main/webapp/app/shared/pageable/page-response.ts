import { Pageable } from './pageable';
import { DDDEntitate } from 'app/entities/ddd-entitate';

export interface PageResponse {
  content: DDDEntitate[];
  pageable: Pageable;
  totalElements: number;
  totalPages: number;
  last: boolean;
  numberOfElements: number;
  size: number;
  number: number;
  sort: {
    unsorted: boolean;
    sorted: boolean;
    empty: boolean;
  };
  first: boolean;
  empty: boolean;
}
