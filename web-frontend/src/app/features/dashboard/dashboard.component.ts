import { Component } from "@angular/core";
import { LayoutComponent } from "./components/layout/layout.component";

@Component({
    selector: 'feature-dashboard',
    templateUrl: './dashboard.component.html',
    imports: [LayoutComponent]
})
export class DashboardComponent{}