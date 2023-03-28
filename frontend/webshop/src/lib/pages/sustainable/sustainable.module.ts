import {NgModule} from "@angular/core";
import {SustainableComponent} from "./sustainable.component";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  {path: '', component: SustainableComponent}
]

@NgModule({
  declarations: [SustainableComponent],
  imports: [RouterModule.forChild(routes)],
  providers: [],
  bootstrap: [],
  exports: [SustainableComponent]
})

export class SustainableModule {

}
