import {NgModule} from '@angular/core';

import {AccordionModule} from 'ngx-bootstrap/accordion';
import {SpecifiedItemComponent} from "./specifiedItem/specifiedItem.component";
import {SpecifiedItemListComponent} from "./specifiedItemList.component";
import {DeleteButtonComponent} from "../deleteButton/deleteButton.component";
import {FormsModule} from "@angular/forms";
import {ColorCircleModule} from "../colorCircle/colorCircle.module";
import {CommonModule} from "@angular/common";


@NgModule({
  declarations: [
    SpecifiedItemComponent, SpecifiedItemListComponent, DeleteButtonComponent
  ],
  imports: [
    CommonModule,
    AccordionModule.forRoot(),
    FormsModule,
    ColorCircleModule
  ],
  providers: [],
  bootstrap: [],
  exports: [SpecifiedItemListComponent]
})
export class SpecifiedItemListModule {
}
