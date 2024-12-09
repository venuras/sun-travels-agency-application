import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";

@Component({
    selector: 'feature-dashboard-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css'],
    imports: [RouterLink]
})
export class SidebarComponent {
    selectedButton: string = 'searchHotels';

    handleClick(button: string) {
        this.selectedButton = button;
    }
}
