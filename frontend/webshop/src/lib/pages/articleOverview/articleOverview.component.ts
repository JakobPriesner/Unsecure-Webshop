import {Component, OnInit} from "@angular/core";
import {ArticleStore} from "../../data-access/service/store/article.store";
import {Article, Commentary, SpecifiedItem} from "../../data-access/models";
import {ActivatedRoute, Router} from "@angular/router";
import {ShoppingCartStore} from "../../data-access/service/store/shoppingCart.store";
import {WishlistStore} from "../../data-access/service/store/wishlist.store";
import {CommentComponent} from "./comment/comment.component";

@Component({
  selector: 'article-overview',
  templateUrl: './articleOverview.component.html',
  styleUrls: ['./articleOverview.component.scss']
})
export class ArticleOverviewComponent implements OnInit {
  // @ts-ignore
  article: Article = undefined;

  // @ts-ignore
  specifiedItem: SpecifiedItem;

  comment: string = "";

  showComment: boolean = false;

  constructor(private articleStore: ArticleStore, private route: ActivatedRoute, private routing: Router, private cartStore: ShoppingCartStore, private wishListStore: WishlistStore) {
  }

  ngOnInit() {
    let articleNumber: number = this.route.snapshot.params['id'];
    this.articleStore.loadArticleById(articleNumber).subscribe(article => {
      this.article = article;
      this.specifiedItem = {articleNumber: this.article.articleNumber, quantity: 1, gbSize: 128, color: 'red'};
    });
  }

  onUpdateQuantity(value: number): void {
    this.specifiedItem.quantity = value;
  }

  onUpdateGbSize(value: number): void {
    this.specifiedItem.gbSize = value;
  }

  onUpdateColor(value: string): void {
    this.specifiedItem.color = value;
  }

  onAddToShoppingCart(): void {
    this.cartStore.addItem(this.specifiedItem).subscribe();
    this.routing.navigateByUrl('/cart');
  }

  onAddToWishList(): void {
    this.wishListStore.addItem(this.specifiedItem).subscribe();
    this.routing.navigateByUrl('/wishlist');
  }

  //Hier mal gucken wie man des verbessern kann
  openCommentField(){
    // if(!this.flag) {
    //   let row = document.createElement('div');
    //   row.className = 'form-outline';
    //   row.innerHTML = `
    //     <textarea [(ngModel)]="comment" class="form-control" id="textAreaExample" rows="4"></textarea>
    //     <button (click)="addComment()" class="btn btn-primary btn-block fa-lg mb-3 mt-2"
    //                         >Speichern</button>
    //     <button (click)="stopComment()" class="btn btn-primary btn-block fa-lg mb-3 mt-2"
    //                         >Beenden</button>`;
    //   this.flag = true;
    //   console.log(this.comment);
    //   // @ts-ignore
    //   document.querySelector('.inputField').appendChild(row);
    // }
  }

  addComment(){
    let commentary: Commentary = {commentText: this.comment};
    let articleNumber: number = this.route.snapshot.params['id'];
    console.log(articleNumber + "addComment articlenumber" + this.article.articleNumber);
    this.articleStore.postCommentary(commentary, articleNumber).subscribe(article => {
      this.article = article
      console.log(article)
    });
    this.showComment = false;
    this.comment = "";
  }

  stopComment(){
    this.showComment = false;
    this.comment = "";
  }

}
