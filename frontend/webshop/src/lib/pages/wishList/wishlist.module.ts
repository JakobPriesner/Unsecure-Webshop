import {NgModule} from '@angular/core';

import {AccordionModule} from 'ngx-bootstrap/accordion';
import {WishlistComponent} from "./wishlist.component";
import {SpecifiedItemListModule} from "../../shared/specifiedItemList/specifiedItemList.module";
import {ColorCircleModule} from "../../shared/colorCircle/colorCircle.module";
import {RouterModule, Routes} from "@angular/router";
import {CommonModule} from "@angular/common";
import {AlertModule} from "ngx-bootstrap/alert";

const routes: Routes = [
  {path: '', component: WishlistComponent}
]

@NgModule({
  declarations: [
    WishlistComponent
  ],
  imports: [
    AccordionModule.forRoot(),
    SpecifiedItemListModule,
    ColorCircleModule,
    RouterModule.forChild(routes),
    CommonModule,
    AlertModule
  ],
  providers: [],
  bootstrap: [],
  exports: [WishlistComponent]
})
export class WishlistModule {
}
