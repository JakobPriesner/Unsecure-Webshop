import {NgModule} from "@angular/core";
import {NavbarItemsComponent} from "./navbarItems.component";
import {RouterModule, Routes} from "@angular/router";
import {ItemListComponent} from "../../pages/ItemList/itemList.component";


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
