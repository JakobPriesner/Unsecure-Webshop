import {Component, OnInit} from "@angular/core";
import {ArticleStore} from "../../data-access/service/store/article.store";
import {Article} from "../../data-access/models";


@Component({
  selector: 'board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})

export class BoardComponent implements OnInit {

  articles: Article[] | undefined;

  constructor(private articleStore: ArticleStore) {
  }

  ngOnInit() {
    this.articleStore.loadArticlesFrontpage().subscribe(articles => {
      this.articles = articles;
    });

  }
}
