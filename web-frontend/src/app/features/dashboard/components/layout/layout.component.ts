import { Component } from "@angular/core";
import { SidebarComponent } from "../sidebar/sidebar.component";
import { HeaderComponent } from "../header/header.component";
import { RouterOutlet } from "@angular/router";

@Component({
    selector: 'feature-dashboard-layout',
    templateUrl: 'layout.component.html',
    styleUrl: 'layout.component.css',
    imports: [SidebarComponent, HeaderComponent, RouterOutlet]
})
export class LayoutComponent{}