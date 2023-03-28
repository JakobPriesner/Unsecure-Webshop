import {Component, Input} from "@angular/core";
import {Commentary} from "../../../data-access/models";
import {Router} from "@angular/router";

@Component({
  selector: 'article-overview-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent {

  @Input() comment: Commentary | undefined;

  constructor(private router: Router) {
  }

  getUserName(): string {
    if (this.comment) {
      if (this.comment.firstName && this.comment.lastName) {
        return `${this.comment.firstName} ${this.comment.lastName}`;
      } else {
        return "Unbekannter Nutzer";
      }
    }
    return "";
  }

  redirectToUser( comment: Commentary): void {
    this.router.navigateByUrl!( `/users/${comment.userId}` );
  }


}
