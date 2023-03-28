import {NgModule} from '@angular/core';

import {FormsModule} from "@angular/forms";
import {ModalModule} from "ngx-bootstrap/modal";
import {UserSettingsComponent} from "./userSettings.component";
import {AlertModule} from "ngx-bootstrap/alert";
import {AddressListModule} from "../../shared/addresses/addressList.module";
import {RouterModule, Routes} from "@angular/router";
import {CommonModule} from "@angular/common";

const routes: Routes = [
  {path: '', component: UserSettingsComponent}
]

@NgModule({
  declarations: [UserSettingsComponent],
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
  exports: [UserSettingsComponent]
})
export class UserSettingsModule {
}
