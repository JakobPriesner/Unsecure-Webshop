import {NgModule} from "@angular/core";
import {MessageAlertsComponent} from "./messageAlerts.component";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {ToastNoAnimationModule} from "ngx-toastr";

@NgModule({
  declarations: [MessageAlertsComponent],
  imports: [CommonModule, FormsModule, ToastNoAnimationModule.forRoot(),],
  providers: [],
  bootstrap: [],
  exports: [MessageAlertsComponent]
})

export class MessageAlertsModule {

}
