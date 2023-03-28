import {NgModule} from "@angular/core";
import {AdminLoginComponent} from "./adminLogin.component";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";
import {AlertModule} from "ngx-bootstrap/alert";

const routes: Routes = [
  {path: '', component: AdminLoginComponent}
]

@NgModule({
  declarations: [AdminLoginComponent],
    imports: [
        FormsModule,
        CommonModule,
        RouterModule.forChild(routes),
        AlertModule
    ],
  providers: [],
  bootstrap: [],
  exports: [AdminLoginComponent]
})

export class AdminLoginModule {

}
