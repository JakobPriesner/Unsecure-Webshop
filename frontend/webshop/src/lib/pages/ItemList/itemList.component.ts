import {Component, OnInit} from '@angular/core';
import {Article} from "../../data-access/models";
import {ArticleStore} from "../../data-access/service/store/article.store";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'itemList',
  templateUrl: './itemList.component.html',
  styleUrls: ['./itemList.component.scss']
})
export class ItemListComponent implements OnInit {

  //@Input articleNumber: number;
  articles: Article[] = [];


  constructor(private articleStore: ArticleStore, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.queryParams.subscribe( params => {
      this.articleStore.searchArticles(params["search"]).subscribe( articles => this.articles = articles );
    });
  }

}
