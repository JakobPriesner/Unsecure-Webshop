import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {NavbarModule} from "../lib/ui/navbar/navbar.module";
import {FooterModule} from "../lib/ui/footer/footer.module";
import {NavbarItemsModule} from "../lib/ui/navbarItems/navbarItems.module";
import {MessageAlertsModule} from "../lib/ui/messageAlerts/messageAlerts.module";
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {BiedisPageModule} from "../lib/pages/biedisPage/biedisPage.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {SqlInjectionModule} from "../lib/pages/securityVulnerabilityInformation/sqlInjection/sqlInjection.module";
import {
  BlindSqlInjectionModule
} from "../lib/pages/securityVulnerabilityInformation/blindSqlInjection/blindSqlInjection.module";
import {XSSModule} from "../lib/pages/securityVulnerabilityInformation/xss/xss.module";
import {
  InsecureFileUploadModule
} from "../lib/pages/securityVulnerabilityInformation/insecureFileUpload/insecureFileUpload.module";
import {HtmlCommentModule} from "../lib/pages/securityVulnerabilityInformation/htmlComment/htmlComment.module";
import {EmailWithoutAtModule} from "../lib/pages/securityVulnerabilityInformation/emailWithoutAt/emailWithoutAt.module";
import {GuessUserLoginModule} from "../lib/pages/securityVulnerabilityInformation/guessUserLogin/guessUserLogin.module";
import {GuessCouponModule} from "../lib/pages/securityVulnerabilityInformation/guessCoupon/guessCoupon.module";
import {
  LoginBruteForceModule
} from "../lib/pages/securityVulnerabilityInformation/loginBruteForce/loginBruteForce.module";
import {DeleteUserModule} from "../lib/pages/securityVulnerabilityInformation/deleteUser/deleteUser.module";
import {PriceOrderBugModule} from "../lib/pages/securityVulnerabilityInformation/priceOrderBug/priceOrderBug.module";
import {
  PasswordHashingModule
} from "../lib/pages/securityVulnerabilityInformation/passwordHashing/passwordHashing.module";
import {CommentXSSModule} from "../lib/pages/securityVulnerabilityInformation/commentXSS/commentXSS.module";
import {
  SecurityMisconfigurationModule
} from "../lib/pages/securityVulnerabilityInformation/securityMisconfiguration/securityMisconfiguration.module";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NavbarModule,
    FooterModule,
    NavbarItemsModule,
    MessageAlertsModule,
    PaginationModule.forRoot(),
    BiedisPageModule,
    BrowserAnimationsModule,
    SqlInjectionModule,
    BlindSqlInjectionModule,
    XSSModule,
    InsecureFileUploadModule,
    HtmlCommentModule,
    EmailWithoutAtModule,
    GuessUserLoginModule,
    GuessCouponModule,
    LoginBruteForceModule,
    DeleteUserModule,
    PriceOrderBugModule,
    PasswordHashingModule,
    CommentXSSModule,
    SecurityMisconfigurationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
