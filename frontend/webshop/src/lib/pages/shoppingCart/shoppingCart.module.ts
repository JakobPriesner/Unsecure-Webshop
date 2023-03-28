import {NgModule} from '@angular/core';

import {AccordionModule} from 'ngx-bootstrap/accordion';
import {ShoppingCartComponent} from "./shoppingCart.component";
import {SpecifiedItemListModule} from "../../shared/specifiedItemList/specifiedItemList.module";
import {RouterModule, Routes} from "@angular/router";
import {CommonModule} from "@angular/common";
import {AlertModule} from "ngx-bootstrap/alert";


const routes: Routes = [
  {path: '', component: ShoppingCartComponent}
]

@NgModule({
  declarations: [
    ShoppingCartComponent
  ],
    imports: [
        AccordionModule.forRoot(),
        SpecifiedItemListModule,
        RouterModule.forChild(routes),
        CommonModule,
        AlertModule
    ],
  providers: [],
  bootstrap: [],
  exports: [ShoppingCartComponent]
})
export class ShoppingCartModule {
}
