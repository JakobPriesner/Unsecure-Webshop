import {NgModule} from "@angular/core";
import {NavbarComponent} from "./navbar.component";
import {AppRoutingModule} from "../../../app/app-routing.module";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [NavbarComponent],
  imports: [
    AppRoutingModule, CommonModule, FormsModule
  ],
  providers: [],
  bootstrap: [],
  exports: [NavbarComponent]
})

export class NavbarModule {
}
