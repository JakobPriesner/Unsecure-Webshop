import {NgModule} from "@angular/core";
import {PaymentInformationComponent} from "./paymentInformation/paymentInformation.component";
import {OrderProcessComponent} from "./orderProcess.component";
import {FormsModule} from "@angular/forms";
import {RouterModule, Routes} from "@angular/router";
import {OrderItemsComponent} from "./orderItems/orderItems.component";
import {CommonModule} from "@angular/common";
import {SpecifiedItemListModule} from "../../shared/specifiedItemList/specifiedItemList.module";
import {ConfirmationComponent} from "./confirmation/confirmation.component";
import {AlertModule} from "ngx-bootstrap/alert";
import {OrderAddressComponent} from "./orderAddress/orderAddress.component";
import {AddressListModule} from "../../shared/addresses/addressList.module";

const routes: Routes = [
  {path: '', component: OrderProcessComponent}
]

@NgModule({
  declarations: [
    OrderProcessComponent, PaymentInformationComponent, OrderItemsComponent, ConfirmationComponent, OrderAddressComponent
  ],
  imports: [
    FormsModule,
    RouterModule.forChild(routes),
    CommonModule,
    SpecifiedItemListModule,
    AlertModule,
    AddressListModule
  ],
  providers: [],
  bootstrap: [],
  exports: [RouterModule]
})
export class OrderProcessModule {
}
