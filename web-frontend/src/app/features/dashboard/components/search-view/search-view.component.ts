import { Component, inject } from "@angular/core";
import { FormControl, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { SearchRequest } from "../../types/search-request.type";
import { SearchService } from "../../services/search.service";
import { catchError, Observable, of } from "rxjs";
import { AvailableHotel } from "../../types/available-hotel.type";

@Component({
    selector: 'feature-dashboard-search-view',
    templateUrl: './search-view.component.html',
    styleUrls: ['./search-view.component.css', './search-view-search-results.styles.css'],
    imports: [ReactiveFormsModule]
})
export class SearchViewComponent {

    searchService: SearchService = inject(SearchService);

    today: string = (new Date(Date.now())).toISOString().split('T')[0];

    currentCombinationIndex: number = 0;

    searchResults: AvailableHotel[] = [];
    checkInDateAndNoOfNightsFormGroup: FormGroup = new FormGroup({
        checkInDate: new FormControl(),
        noOfNights: new FormControl()
    });

    roomCountAndAdultCountCombinationsFormGroups: { index: number; formGroup: FormGroup }[] = [
        {
            index: 0, formGroup: new FormGroup({
                roomCount: new FormControl(),
                adultCount: new FormControl(),
            })
        }
    ];


    addCombination = () => {
        this.currentCombinationIndex++;
        this.roomCountAndAdultCountCombinationsFormGroups.push(
            {
                index: this.currentCombinationIndex, formGroup: new FormGroup({
                    roomCount: new FormControl(),
                    adultCount: new FormControl(),
                })
            }
        );
    };

    removeCombination = (index: number) => {
        this.roomCountAndAdultCountCombinationsFormGroups = this.roomCountAndAdultCountCombinationsFormGroups.filter((combination) => combination.index !== index);
    }

    private formatToSearchRequest(): SearchRequest {
        let searchRequest: SearchRequest = {
            checkInDate: this.checkInDateAndNoOfNightsFormGroup.value.checkInDate,
            noOfNights: this.checkInDateAndNoOfNightsFormGroup.value.noOfNights,
            roomCountWithNoOfAdults: this.roomCountAndAdultCountCombinationsFormGroups.map((combMap) => {
                let formGroup: FormGroup = combMap.formGroup;
                return {
                    roomCount: formGroup.value.roomCount,
                    adultCount: formGroup.value.adultCount,
                }
            })
        };

        return searchRequest;
    }

    handleSubmit() {
        let searchRequest: SearchRequest = this.formatToSearchRequest();
        let result$: Observable<AvailableHotel[]> = this.searchService.getAvailableHotels(searchRequest);
        result$.pipe(catchError((err) => {
            alert('An unexpected error occurred while fetching search results');
            return of();
        })).subscribe(value => this.searchResults = value);
    }

}