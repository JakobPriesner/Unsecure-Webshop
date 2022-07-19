import {NgModule} from "@angular/core";
import {BiedisPageComponent} from "./biedisPage.component";
import {RouterModule, Routes} from "@angular/router";
import {BsDropdownModule} from "ngx-bootstrap/dropdown";
import {CommonModule} from "@angular/common";
import {AccordionModule} from "ngx-bootstrap/accordion";
import {FormsModule} from "@angular/forms";
import {ModalModule} from "ngx-bootstrap/modal";


const routes: Routes = [
  {path: '', component: BiedisPageComponent}
]

@NgModule({
  declarations: [BiedisPageComponent],
    imports: [RouterModule.forChild(routes), BsDropdownModule, CommonModule, AccordionModule, FormsModule, ModalModule],
  providers: [],
  bootstrap: [],
  exports: [BiedisPageComponent]
})

export class BiedisPageModule {

}
