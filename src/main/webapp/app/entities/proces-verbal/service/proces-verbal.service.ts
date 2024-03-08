import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProcesVerbal, NewProcesVerbal } from '../proces-verbal.model';

import { TableLazyLoadEvent } from 'primeng/table';
import { PageableResponse } from 'app/entities/utilizator/service/utilizator.service';
import { PageResponse } from 'app/shared/pageable/page-response';
import { createReuqestFromTableLazyLoadEvent } from 'app/core/request/request-util';

export type PartialUpdateProcesVerbal = Partial<IProcesVerbal> & Pick<IProcesVerbal, 'id'>;

type RestOf<T extends IProcesVerbal | NewProcesVerbal> = Omit<T, 'data' | 'ora'> & {
  data?: string | null;
  ora?: string | null;
};

export type RestProcesVerbal = RestOf<IProcesVerbal>;

export type NewRestProcesVerbal = RestOf<NewProcesVerbal>;

export type PartialUpdateRestProcesVerbal = RestOf<PartialUpdateProcesVerbal>;

export type EntityResponseType = HttpResponse<IProcesVerbal>;
export type EntityArrayResponseType = HttpResponse<IProcesVerbal[]>;

@Injectable({ providedIn: 'root' })
export class ProcesVerbalService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/proces-verbals');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  getList(lazyEvent?: TableLazyLoadEvent): Observable<PageableResponse> {
    let params = createReuqestFromTableLazyLoadEvent(lazyEvent);
    return this.http.get<PageResponse>(this.resourceUrl, { params: params, observe: 'response' });
  }

  create(procesVerbal: NewProcesVerbal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(procesVerbal);
    return this.http
      .post<RestProcesVerbal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(procesVerbal: IProcesVerbal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(procesVerbal);
    return this.http
      .put<RestProcesVerbal>(`${this.resourceUrl}/${this.getProcesVerbalIdentifier(procesVerbal)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(procesVerbal: PartialUpdateProcesVerbal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(procesVerbal);
    return this.http
      .patch<RestProcesVerbal>(`${this.resourceUrl}/${this.getProcesVerbalIdentifier(procesVerbal)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestProcesVerbal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProcesVerbal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProcesVerbalIdentifier(procesVerbal: Pick<IProcesVerbal, 'id'>): string {
    return procesVerbal.id;
  }

  compareProcesVerbal(o1: Pick<IProcesVerbal, 'id'> | null, o2: Pick<IProcesVerbal, 'id'> | null): boolean {
    return o1 && o2 ? this.getProcesVerbalIdentifier(o1) === this.getProcesVerbalIdentifier(o2) : o1 === o2;
  }

  addProcesVerbalToCollectionIfMissing<Type extends Pick<IProcesVerbal, 'id'>>(
    procesVerbalCollection: Type[],
    ...procesVerbalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const procesVerbals: Type[] = procesVerbalsToCheck.filter(isPresent);
    if (procesVerbals.length > 0) {
      const procesVerbalCollectionIdentifiers = procesVerbalCollection.map(
        procesVerbalItem => this.getProcesVerbalIdentifier(procesVerbalItem)!,
      );
      const procesVerbalsToAdd = procesVerbals.filter(procesVerbalItem => {
        const procesVerbalIdentifier = this.getProcesVerbalIdentifier(procesVerbalItem);
        if (procesVerbalCollectionIdentifiers.includes(procesVerbalIdentifier)) {
          return false;
        }
        procesVerbalCollectionIdentifiers.push(procesVerbalIdentifier);
        return true;
      });
      return [...procesVerbalsToAdd, ...procesVerbalCollection];
    }
    return procesVerbalCollection;
  }

  protected convertDateFromClient<T extends IProcesVerbal | NewProcesVerbal | PartialUpdateProcesVerbal>(procesVerbal: T): RestOf<T> {
    return {
      ...procesVerbal,
      data: procesVerbal.data?.format(DATE_FORMAT) ?? null,
      ora: procesVerbal.ora?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProcesVerbal: RestProcesVerbal): IProcesVerbal {
    return {
      ...restProcesVerbal,
      data: restProcesVerbal.data ? dayjs(restProcesVerbal.data) : undefined,
      ora: restProcesVerbal.ora ? dayjs(restProcesVerbal.ora) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProcesVerbal>): HttpResponse<IProcesVerbal> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProcesVerbal[]>): HttpResponse<IProcesVerbal[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
