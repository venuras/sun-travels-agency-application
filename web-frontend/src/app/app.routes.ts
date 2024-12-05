import { Routes } from '@angular/router';
import { SearchViewComponent } from './features/dashboard/components/search-view/search-view.component';
import { ContractViewComponent } from './features/dashboard/components/contract-view/contract-view.component';

export const routes: Routes = [
    {
        path:"",
        pathMatch: "full",
        redirectTo: "dashboard/search"
    },
    {
        path:"dashboard/search",
        component:SearchViewComponent
    },
    {
        path:"dashboard/contracts",
        component:ContractViewComponent
    }
];
