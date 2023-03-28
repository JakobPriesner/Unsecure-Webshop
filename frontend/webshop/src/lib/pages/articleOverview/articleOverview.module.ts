import {NgModule} from '@angular/core';
import {ArticleOverviewComponent} from "./articleOverview.component";
import {SpecificationsComponent} from "./specifications/specifications.component";
import {OverviewComponent} from "./overview/overview.component";
import {CommentComponent} from "./comment/comment.component";
import {ImageSectionComponent} from "./imageSection/imageSection.component";
import {FormsModule} from "@angular/forms";
import {ButtonsModule} from 'ngx-bootstrap/buttons';
import {CarouselModule} from 'ngx-bootstrap/carousel';
import {RouterModule, Routes} from "@angular/router";
import {CommonModule} from "@angular/common";
import {ColorCircleModule} from "../../shared/colorCircle/colorCircle.module";

const routes: Routes = [
  {
    path: '', component: ArticleOverviewComponent
  }
];

@NgModule({
  declarations: [ArticleOverviewComponent, SpecificationsComponent, OverviewComponent, ImageSectionComponent, CommentComponent],
  imports: [
    FormsModule,
    CommonModule,
    ColorCircleModule,
    RouterModule.forChild(routes),
    ButtonsModule.forRoot(),
    CarouselModule.forRoot(),
  ],
  providers: [],
  bootstrap: [],
  exports: [ArticleOverviewComponent]
})
export class ArticleOverviewModule {
}
