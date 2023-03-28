import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";
import {AlertModule} from "ngx-bootstrap/alert";
import {CookieService} from "ngx-cookie-service";

const routes: Routes = [
  {path: '', component: LoginComponent}
]

@NgModule({
  declarations: [LoginComponent],
  imports: [
    FormsModule,
    CommonModule,
    RouterModule.forChild(routes),
    AlertModule
  ],
  providers: [CookieService],
  bootstrap: [],
  exports: [LoginComponent]
})

export class LoginModule {

}
