import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/service/api.service';
import { Contract } from '../../../shared/types/contract.type';

@Injectable({
    providedIn: 'root',
})
export class ContractService extends ApiService {
    constructor(protected override injector: Injector) {
        super(injector);
    }

    path: string = 'contract';

    getContracts(): Observable<Contract[]> {
        return this.get(this.path);
    }

    createContract(contract: Contract): Observable<Contract> {
        return this.post(this.path, contract);
    }

    deleteContract(contractId: number): Observable<void> {
        return this.delete(this.path, { "contractId": contractId });
    }
}
