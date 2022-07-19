import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticationGuard as AuthGuard} from "../lib/data-access/service/canActivateGuards";
import {AdminAuthenticationGuard as AdminAuthGuard} from "../lib/data-access/service/canActivateGuards/adminAuthentication.guard";

// @ts-ignore
const routes: Routes = [
  {
    path: 'index',
    loadChildren: () => import('../lib/pages/board/board.module').then(m => m.BoardModule),
  },
  {
    path: 'articles/:id',
    loadChildren: () => import('../lib/pages/articleOverview/articleOverview.module').then(m => m.ArticleOverviewModule)
  },

  {path: 'articles', loadChildren: () => import('../lib/pages/ItemList/itemList.module').then(m => m.ItemListModule)},
  {
    path: 'contact',
    loadChildren: () => import('../lib/pages/contactform/contactform.module').then(m => m.ContactformModule)
  },
  {
    path: 'orders',
    loadChildren: () => import('../lib/pages/orderList/orderList.module').then(m => m.OrderListModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'checkout',
    loadChildren: () => import('../lib/pages/orderProcess/orderProcess.module').then(m => m.OrderProcessModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'cart',
    loadChildren: () => import('../lib/pages/shoppingCart/shoppingCart.module').then(m => m.ShoppingCartModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'user',
    loadChildren: () => import('../lib/pages/userSettings/userSettings.module').then(m => m.UserSettingsModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'wishlist',
    loadChildren: () => import('../lib/pages/wishList/wishlist.module').then(m => m.WishlistModule),
    canActivate: [AuthGuard]
  },
  {path: 'login', loadChildren: () => import('../lib/pages/login/login.module').then(m => m.LoginModule)},
  {
    path: 'adminLogin',
    loadChildren: () => import('../lib/pages/adminLogin/adminLogin.module').then(m => m.AdminLoginModule)
  },
  {
    path: 'admin',
    loadChildren: () => import('../lib/pages/biedisPage/biedisPage.module').then(m => m.BiedisPageModule),
    canActivate: [AdminAuthGuard]
  },
  {path: 'register', loadChildren: () => import('../lib/pages/register/register.module').then(m => m.RegisterModule)},
  {
    path: 'sustainable',
    loadChildren: () => import('../lib/pages/sustainable/sustainable.module').then(m => m.SustainableModule)
  },
  {
    path: 'impressum',
    loadChildren: () => import('../lib/pages/impressum/impressum.module').then(m => m.ImpressumModule)
  },
  {path: 'aboutUs', loadChildren: () => import('../lib/pages/aboutUs/aboutUs.module').then(m => m.AboutUsModule)},
  {
    path: 'paymentMethods',
    loadChildren: () => import('../lib/pages/paymentMethods/paymentMethods.module').then(m => m.PaymentMethodsModule)
  },
  {
    path: 'sql_injection',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/sqlInjection/sqlInjection.module').then(m => m.SqlInjectionModule)
  },
  {
    path: 'blind_sql_injection',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/blindSqlInjection/blindSqlInjection.module').then(m => m.BlindSqlInjectionModule)
  },
  {
    path: 'xss',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/xss/xss.module').then(m => m.XSSModule)
  },
  {
    path: 'profile_picture',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/insecureFileUpload/insecureFileUpload.module').then(m => m.InsecureFileUploadModule)
  },
  {
    path: 'html_comment_user',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/htmlComment/htmlComment.module').then(m => m.HtmlCommentModule)
  },
  {
    path: 'email_without_at',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/emailWithoutAt/emailWithoutAt.module').then(m => m.EmailWithoutAtModule)
  },
  {
    path: 'guess_user_login',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/guessUserLogin/guessUserLogin.module').then(m => m.GuessUserLoginModule)
  },
  {
    path: 'guess_coupon',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/guessCoupon/guessCoupon.module').then(m => m.GuessCouponModule)
  },
  {
    path: 'login_brute_force',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/loginBruteForce/loginBruteForce.module').then(m => m.LoginBruteForceModule)
  },
  {
    path: 'delete_user',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/deleteUser/deleteUser.module').then(m => m.DeleteUserModule)
  },
  {
    path: 'price_order_bug',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/priceOrderBug/priceOrderBug.module').then(m => m.PriceOrderBugModule)
  },
  {
    path: 'hash_user',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/passwordHashing/passwordHashing.module').then(m => m.PasswordHashingModule)
  },
  {
    path: 'comment_xss',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/commentXSS/commentXSS.module').then(m => m.CommentXSSModule)
  },
  {
    path: 'security_misconfiguration',
    loadChildren: () => import('../lib/pages/securityVulnerabilityInformation/securityMisconfiguration/securityMisconfiguration.module').then(m => m.SecurityMisconfigurationModule)
  },
  {
    path: 'users/:id',
    loadChildren: () => import('../lib/pages/foreignUser/foreignUser.module').then(m => m.ForeignUserModule)
  },
  {path: '', redirectTo: 'index', pathMatch: 'full'},
  {path: '**', redirectTo: 'index'},
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {enableTracing: false})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
