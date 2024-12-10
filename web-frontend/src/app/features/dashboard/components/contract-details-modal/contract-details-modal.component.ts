import { Component, EventEmitter, Input, Output } from "@angular/core";
import { Contract } from "../../../../shared/types/contract.type";

@Component({
    selector: 'feature-contract-details-modal',
    templateUrl: './contract-details-modal.component.html',
    styleUrls: ['./contract-details-modal.component.css'],
})
export class ContractDetailsModalComponent {
    closeModal() {
        this.close.emit();
    }
    @Input() contract!: Contract;
    @Output() close = new EventEmitter<void>();
}
