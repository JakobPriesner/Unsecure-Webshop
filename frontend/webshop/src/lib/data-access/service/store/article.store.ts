import {Injectable} from "@angular/core";
import {BackendService} from "../backend.service";
import {Observable, ReplaySubject} from "rxjs";
import {Article, Commentary} from "../../models";

@Injectable({
  providedIn: 'root'
})

export class ArticleStore {
  // @ts-ignore
  articles: Article[] = [];
  // @ts-ignore
  smallArticles: Article[];
  articleSubject: ReplaySubject<Article[]> = new ReplaySubject<Article[]>(1);
  articleSubjectFrontpage: ReplaySubject<Article[]> = new ReplaySubject<Article[]>(1);

  constructor(private backendService: BackendService) {
  }

  // loadArticles(): ReplaySubject<Article[]> {
  //   if (this.articles.length == 0) {
  //     this.backendService.getArticles().subscribe(articles => {
  //       this.articles = articles;
  //       this.articleSubject.next(articles);
  //     });
  //
  //   } else {
  //     this.articleSubject.next(this.articles);
  //   }
  //
  //   return this.articleSubject;
  //
  // }

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

  postCommentary(commentary: Commentary, articleId: number): ReplaySubject<Article>{
    // let article: Article;
    let index = this.articles.findIndex(i => i.articleNumber==articleId);
    if(index != -1){
      this.articles[index].comments = this.articles[index].comments ?? [];
      this.articles[index].comments!.push(commentary);
    }
    // this.articles.forEach(function (value) {
    //   if(value.articleNumber == articleId){
    //     value.comments?.push(commentary);
    //   }
    // })
    const subject = new ReplaySubject<Article>(1);
    this.backendService.postCommentToArticle(commentary, articleId).subscribe(() => {
      this.backendService.getArticleById(articleId).subscribe(article => subject.next(article));
    });
    return subject;
  }

}
