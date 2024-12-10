import { Component, inject, OnInit } from "@angular/core";
import { ApiService } from "../../../../core/service/api.service";
import { ContractService } from "../../services/contract.service";
import { Observable } from "rxjs";
import { Contract } from "../../../../shared/types/contract.type";

@Component({
    selector:'feature-dashboard-search-view',
    templateUrl:'./contract-view.component.html',
    styleUrl: './contract-view.component.css',
})
export class ContractViewComponent implements OnInit{
    ngOnInit(): void {
        this.getContracts();
    }

    contractService:ContractService = inject(ContractService);

    getContracts():void {
        let contracts$ = this.contractService.getContracts();
        contracts$.subscribe((value) => {
            console.log(value);
        });
    }
}