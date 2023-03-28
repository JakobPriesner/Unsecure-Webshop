import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {ImpressumComponent} from "./impressum.component";
import {CommonModule} from "@angular/common";

const routes: Routes = [
  {path: '', component: ImpressumComponent}
]

@NgModule({
  declarations: [ImpressumComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  providers: [],
  bootstrap: [],
  exports: [ImpressumComponent]
})

export class ImpressumModule {

}
