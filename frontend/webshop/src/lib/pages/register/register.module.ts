import {NgModule} from "@angular/core";
import {RegisterComponent} from "./register.component";
import {FormsModule} from "@angular/forms";
import {AlertModule} from "ngx-bootstrap/alert";
import {CommonModule} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {path: '', component: RegisterComponent}
]


@NgModule({
  declarations: [RegisterComponent],
  imports: [
    FormsModule,
    AlertModule,
    CommonModule,
    RouterModule.forChild(routes)
  ],
  providers: [],
  bootstrap: [],
  exports: [RegisterComponent]
})

export class RegisterModule {

}
