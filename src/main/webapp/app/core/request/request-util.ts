import { HttpParams } from '@angular/common/http';
import { TableLazyLoadEvent } from 'primeng/table';

export const createRequestOption = (req?: any): HttpParams => {
  let options: HttpParams = new HttpParams();

  if (req) {
    Object.keys(req).forEach(key => {
      if (key !== 'sort' && req[key] !== undefined) {
        for (const value of [].concat(req[key]).filter(v => v !== '')) {
          options = options.append(key, value);
        }
      }
    });

    if (req.sort) {
      req.sort.forEach((val: string) => {
        options = options.append('sort', val);
      });
    }
  }

  return options;
};

export const createReuqestFromTableLazyLoadEvent = (event?: TableLazyLoadEvent): HttpParams => {
  let params = new HttpParams();

  if (!!event) {
    params = params.set('first', '' + event.first);
    params = params.set('rows', '' + event.rows);
    if (!!event.sortField) {
      params = params.set('sortField', '' + event.sortField);
      params = params.set('sortOrder', event.sortOrder === 1 ? 'ASC' : 'DESC');
    }
  }

  return params;
};
