import {NgModule} from "@angular/core";
import {PaymentMethodsComponent} from "./paymentMethods.component";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  {path: '', component: PaymentMethodsComponent}
]

@NgModule({
  declarations: [PaymentMethodsComponent],
  imports: [RouterModule.forChild(routes)],
  providers: [],
  bootstrap: [],
  exports: [PaymentMethodsComponent]
})

export class PaymentMethodsModule {

}
