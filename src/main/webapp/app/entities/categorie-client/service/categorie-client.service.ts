import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorieClient, NewCategorieClient } from '../categorie-client.model';

import { TableLazyLoadEvent } from 'primeng/table';
import { PageableResponse } from 'app/entities/utilizator/service/utilizator.service';
import { PageResponse } from 'app/shared/pageable/page-response';
import { createReuqestFromTableLazyLoadEvent } from 'app/core/request/request-util';

export type PartialUpdateCategorieClient = Partial<ICategorieClient> & Pick<ICategorieClient, 'id'>;

export type EntityResponseType = HttpResponse<ICategorieClient>;
export type EntityArrayResponseType = HttpResponse<ICategorieClient[]>;

@Injectable({ providedIn: 'root' })
export class CategorieClientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categorie-clients');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  getList(lazyEvent?: TableLazyLoadEvent): Observable<PageableResponse> {
    let params = createReuqestFromTableLazyLoadEvent(lazyEvent);
    return this.http.get<PageResponse>(this.resourceUrl, { params: params, observe: 'response' });
  }

  create(categorieClient: NewCategorieClient): Observable<EntityResponseType> {
    return this.http.post<ICategorieClient>(this.resourceUrl, categorieClient, { observe: 'response' });
  }

  update(categorieClient: ICategorieClient): Observable<EntityResponseType> {
    return this.http.put<ICategorieClient>(`${this.resourceUrl}/${this.getCategorieClientIdentifier(categorieClient)}`, categorieClient, {
      observe: 'response',
    });
  }

  partialUpdate(categorieClient: PartialUpdateCategorieClient): Observable<EntityResponseType> {
    return this.http.patch<ICategorieClient>(`${this.resourceUrl}/${this.getCategorieClientIdentifier(categorieClient)}`, categorieClient, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICategorieClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieClient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategorieClientIdentifier(categorieClient: Pick<ICategorieClient, 'id'>): string {
    return categorieClient.id;
  }

  compareCategorieClient(o1: Pick<ICategorieClient, 'id'> | null, o2: Pick<ICategorieClient, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategorieClientIdentifier(o1) === this.getCategorieClientIdentifier(o2) : o1 === o2;
  }

  addCategorieClientToCollectionIfMissing<Type extends Pick<ICategorieClient, 'id'>>(
    categorieClientCollection: Type[],
    ...categorieClientsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categorieClients: Type[] = categorieClientsToCheck.filter(isPresent);
    if (categorieClients.length > 0) {
      const categorieClientCollectionIdentifiers = categorieClientCollection.map(
        categorieClientItem => this.getCategorieClientIdentifier(categorieClientItem)!,
      );
      const categorieClientsToAdd = categorieClients.filter(categorieClientItem => {
        const categorieClientIdentifier = this.getCategorieClientIdentifier(categorieClientItem);
        if (categorieClientCollectionIdentifiers.includes(categorieClientIdentifier)) {
          return false;
        }
        categorieClientCollectionIdentifiers.push(categorieClientIdentifier);
        return true;
      });
      return [...categorieClientsToAdd, ...categorieClientCollection];
    }
    return categorieClientCollection;
  }
}
