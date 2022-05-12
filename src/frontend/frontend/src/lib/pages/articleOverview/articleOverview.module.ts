import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {ArticleOverviewComponent} from "./articleOverview.component";
import {SpecificationsComponent} from "./specifications/specifications.component";
import {OverviewComponent} from "./overview/overview.component";
import {CommentComponent} from "./comment/comment.component";
import {ImageSectionComponent} from "./imageSection/imageSection.component";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [ArticleOverviewComponent, SpecificationsComponent, OverviewComponent, ImageSectionComponent, CommentComponent],
  imports: [
    BrowserModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [],
  exports: [ArticleOverviewComponent]
})
export class ArticleOverviewModule {
}
