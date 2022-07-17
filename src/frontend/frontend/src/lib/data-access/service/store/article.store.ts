import {Injectable} from "@angular/core";
import {BackendService} from "../backend.service";
import {ReplaySubject} from "rxjs";
import {Article} from "../../models";

@Injectable({
  providedIn: 'root'
})

export class ArticleStore {

  articles: Article[] = [];
  smallArticles: Article[] | undefined;
  articleSubject: ReplaySubject<Article[]> = new ReplaySubject<Article[]>(1);
  articleSubjectFrontpage: ReplaySubject<Article[]> = new ReplaySubject<Article[]>(1);

  constructor(private backendService: BackendService) {
  }

  loadArticlesFrontpage(): ReplaySubject<Article[]> {
    if (!this.smallArticles) {
      this.backendService.getArticlesFrontpage().subscribe(articles => {
        this.smallArticles = articles;
        this.articleSubjectFrontpage.next(articles);
      })
    } else {
      this.articleSubjectFrontpage.next(this.smallArticles);
    }
    return this.articleSubjectFrontpage;
  }

  loadArticleById(id: number): ReplaySubject<Article> {
    const articleSubject: ReplaySubject<Article> = new ReplaySubject<Article>(1);
    let index: number = this.articles.findIndex(a => a.articleNumber === id);
    if (index === -1) {
      this.backendService.getArticleById(id).subscribe(article => {
        articleSubject.next(article);
      })
    } else {
      articleSubject.next(this.articles[index]);
    }
    return articleSubject;
  }

  searchArticles(searchInput: string): ReplaySubject<Article[]> {
    const articleSearchSubject: ReplaySubject<Article[]> = new ReplaySubject<Article[]>(1);
    this.backendService.searchArticles(searchInput).subscribe(articles => {
      articleSearchSubject.next(articles);
    });
    return articleSearchSubject;
  }

}
