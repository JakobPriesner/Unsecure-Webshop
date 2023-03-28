import {NgModule} from "@angular/core";
import {AboutUsComponent} from "./aboutUs.component";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  {path: '', component: AboutUsComponent}
]

@NgModule({
  declarations: [AboutUsComponent],
  imports: [RouterModule.forChild(routes)],
  providers: [],
  bootstrap: [],
  exports: [AboutUsComponent]
})

export class AboutUsModule {

}
