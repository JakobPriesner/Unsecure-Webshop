import {NgModule} from "@angular/core";
import {NavbarItemsComponent} from "./navbarItems.component";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  {path: '', component: NavbarItemsComponent}
]

@NgModule({
  declarations: [NavbarItemsComponent],
  imports: [RouterModule, RouterModule.forChild(routes)],
  providers: [],
  bootstrap: [],
  exports: [NavbarItemsComponent]
})

export class NavbarItemsModule {

}
