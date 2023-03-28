import {NgModule} from "@angular/core";
import {NewsletterComponent} from "./newsletter.component";
import {ModalModule} from "ngx-bootstrap/modal";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [NewsletterComponent],
  imports: [ModalModule.forRoot(), FormsModule, CommonModule
  ],
  providers: [],
  bootstrap: [],
  exports: [NewsletterComponent]
})
export class NewsletterModule {
}
