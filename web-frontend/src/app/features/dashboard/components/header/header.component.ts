import { Component } from "@angular/core";
import { NgOptimizedImage } from "@angular/common";
@Component({
    selector:'feature-dashboard-header',
    templateUrl:'./header.component.html',
    styleUrl: './header.component.css',
    imports: [NgOptimizedImage]
})
export class HeaderComponent{
    logoUrl: string = "sun-travels-logo.jpg"
}