import { Component, EventEmitter, Output } from "@angular/core";
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { ContractDetail } from "../../../../shared/types/conntract-detail.type";
import { Contract } from "../../../../shared/types/contract.type";

@Component({
    selector: 'feature-add-contract-modal',
    templateUrl: './add-contract-modal.component.html',
    styleUrl: './add-contract-modal.component.css',
    imports: [ReactiveFormsModule],
})
export class AddContractModalComponent {
    @Output() close = new EventEmitter<void>();
    @Output() submit = new EventEmitter<Contract>();

    minDate: string = new Date().toISOString().split('T')[0];
    currentRoomTypesIndex: number = 0;

    contractForm = new FormGroup({
        hotelName: new FormControl('', [Validators.required, Validators.maxLength(100)]),
        contractValidFrom: new FormControl('', [Validators.required]),
        contractValidTill: new FormControl('', [Validators.required]),
        markup: new FormControl(15.0, [Validators.required, Validators.min(0), Validators.max(100)]),
    });

    contractDetailsForms: { index: number; formGroup: FormGroup }[] = [
        {
            index: 0,
            formGroup: new FormGroup({
                roomType: new FormControl('', [Validators.required, Validators.maxLength(50)]),
                roomPrice: new FormControl(0.0, [Validators.required, Validators.min(0)]),
                roomCount: new FormControl(1, [Validators.required, Validators.min(1)]),
                maxAdultCount: new FormControl(1, [Validators.required, Validators.min(1), Validators.max(10)]),
            }),
        }
    ];

    addContractDetail(): void {
        this.currentRoomTypesIndex++;
        this.contractDetailsForms.push({
            index: this.currentRoomTypesIndex,
            formGroup: new FormGroup({
                roomType: new FormControl('', [Validators.required, Validators.maxLength(50)]),
                roomPrice: new FormControl(0.0, [Validators.required, Validators.min(0)]),
                roomCount: new FormControl(1, [Validators.required, Validators.min(1)]),
                maxAdultCount: new FormControl(1, [Validators.required, Validators.min(1), Validators.max(10)]),
            }),
        });
    }

    removeRoomType(index: number): void {
        this.contractDetailsForms = this.contractDetailsForms.filter(map => map.index !== index);
    }

    handleSubmit(): void {
        if (this.contractForm.invalid || this.contractDetailsForms.some(formMap => formMap.formGroup.invalid)) {
            console.error("Validation errors in forms");
            return;
        }

        const contract: Contract = this.contractFormToObjectConverter(
            this.contractForm,
            this.contractDetailsForms.map(map => map.formGroup)
        );
        this.submit.emit(contract);
        this.close.emit();
    }

    closeModal(): void {
        this.close.emit();
    }

    private contractDetailFormToObjectConverter(contractDetailFormGroup: FormGroup): ContractDetail {
        return {
            roomType: contractDetailFormGroup.value.roomType,
            roomPrice: contractDetailFormGroup.value.roomPrice,
            roomCount: contractDetailFormGroup.value.roomCount,
            maxAdultCount: contractDetailFormGroup.value.maxAdultCount,
        };
    }

    private contractFormToObjectConverter(contractForm: FormGroup, contractDetailsForms: FormGroup[]): Contract {
        const contractDetails: ContractDetail[] = contractDetailsForms.map(
            detailForm => this.contractDetailFormToObjectConverter(detailForm)
        );

        return {
            hotelName: contractForm.value.hotelName,
            markup: contractForm.value.markup,
            contractValidFrom: contractForm.value.contractValidFrom,
            contractValidTill: contractForm.value.contractValidTill,
            contractDetails: contractDetails,
        };
    }
}
