import {NgModule} from "@angular/core";
import {BoardComponent} from "./board.component";
import {FrontItemComponent} from "./frontItem/frontItem.component";
import {CommonModule} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";
import {NewsletterModule} from "../../ui/newsletter/newsletter.module";

const routes: Routes = [
  {path: '', component: BoardComponent}
]

@NgModule({
  declarations: [BoardComponent, FrontItemComponent],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        NewsletterModule,
    ],
  providers: [],
  bootstrap: [],
  exports: [BoardComponent]
})

export class BoardModule {

}
