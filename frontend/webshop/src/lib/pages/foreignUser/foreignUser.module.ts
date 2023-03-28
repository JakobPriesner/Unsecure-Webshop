import {NgModule} from '@angular/core';

import {FormsModule} from "@angular/forms";
import {ModalModule} from "ngx-bootstrap/modal";
import {AlertModule} from "ngx-bootstrap/alert";
import {AddressListModule} from "../../shared/addresses/addressList.module";
import {RouterModule, Routes} from "@angular/router";
import {CommonModule} from "@angular/common";
import {ForeignUserComponent} from "./foreignUser.component";

const routes: Routes = [
  {path: '', component: ForeignUserComponent}
]

@NgModule({
  declarations: [ForeignUserComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule.forRoot(),
    AlertModule.forRoot(),
    AddressListModule,
    RouterModule.forChild(routes)
  ],
  providers: [],
  bootstrap: [],
  exports: [ForeignUserComponent]
})
export class ForeignUserModule {
}
