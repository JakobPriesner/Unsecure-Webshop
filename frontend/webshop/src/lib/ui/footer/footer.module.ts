import {NgModule} from "@angular/core";
import {FooterComponent} from "./footer.component";
import {AppRoutingModule} from "../../../app/app-routing.module";
import {CommonModule} from "@angular/common";
import {NewsletterModule} from "../newsletter/newsletter.module";

@NgModule({
  declarations: [FooterComponent],
  imports: [
    AppRoutingModule, CommonModule, NewsletterModule
  ],
  providers: [],
  bootstrap: [],
  exports: [FooterComponent]
})

export class FooterModule {

}
