import {ItemListComponent} from "./itemList.component";
import {NgModule} from "@angular/core";
import {PageItemComponent} from "./pageItem/pageItem.component";
import {CommonModule} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";
import {ColorCircleModule} from "../../shared/colorCircle/colorCircle.module";
import {PaginationComponent, PaginationModule} from "ngx-bootstrap/pagination";


const routes: Routes = [
  {path: '', component: ItemListComponent}
]

@NgModule({
  declarations: [ItemListComponent, PageItemComponent],
  imports: [CommonModule, RouterModule, ColorCircleModule, RouterModule.forChild(routes), PaginationModule],
  providers: [],
  bootstrap: [PaginationComponent],
  exports: [ItemListComponent]
})
export class ItemListModule {
}
