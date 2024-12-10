import { Component, inject, OnInit } from "@angular/core";
import { ApiService } from "../../../../core/service/api.service";
import { ContractService } from "../../services/contract.service";
import { catchError, ignoreElements, Observable, of } from "rxjs";
import { Contract } from "../../../../shared/types/contract.type";
import { ContractDetailsModalComponent } from "../contract-details-modal/contract-details-modal.component";

@Component({
    selector: 'feature-dashboard-search-view',
    templateUrl: './contract-view.component.html',
    styleUrl: './contract-view.component.css',
    imports: [ContractDetailsModalComponent]
})
export class ContractViewComponent implements OnInit {

    selectedContract?:Contract;
    closeRoomDetailsModal() {
        this.isModalOpen = false;
    }

    openRoomDetailsModal(conntract:Contract) {
        this.selectedContract = conntract;
        this.isModalOpen = true;
    }

    contracts: Contract[] = [];

    isModalOpen:boolean = false;

    ngOnInit(): void {
        this.getContracts();
    }

    contractService: ContractService = inject(ContractService);

    getContracts(): void {
        let contracts$: Observable<Contract[]> = this.contractService.getContracts();
        contracts$.pipe(
            catchError((err: { status_code: number, message: string }, caught: Observable<Contract[]>) => {
                return of();
            })
        ).subscribe({
            next: (value) => {
                this.contracts = value;
            }
        });
    }
}