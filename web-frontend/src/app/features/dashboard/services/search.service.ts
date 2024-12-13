import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/service/api.service';
import { SearchRequest } from '../types/search-request.type';
import { AvailableHotel } from '../types/available-hotel.type';

@Injectable({
    providedIn: 'root',
})
export class SearchService extends ApiService {
    constructor(protected override injector: Injector) {
        super(injector);
    }

    path: string = 'contract/available';

    getAvailableHotels(searchRequest: SearchRequest): Observable<AvailableHotel[]> {
        return this.post(this.path, searchRequest);
    }

}
