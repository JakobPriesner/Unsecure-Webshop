import {Component, TemplateRef} from '@angular/core';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {UserStore} from "../../data-access/service/store/user.store";
import {Nletter} from "../../data-access/models/nletter";

@Component({
  selector: 'newsletter',
  templateUrl: './newsletter.component.html',
  styleUrls: ['./newsletter.component.css']
})
export class NewsletterComponent {// implements OnInit{
  modalRef?: BsModalRef;

  nletter: Nletter = {email: ''};

  // @ts-ignore
  // @ViewChild('newsletter') newsletterModal: TemplateRef<any>;

  constructor(private modalService: BsModalService, private userStore: UserStore) {
  }

  openModal(newsletter: TemplateRef<any>) {
    this.modalRef = this.modalService.show(newsletter, {animated: true, keyboard: true});
  }


  onSubscribeToNewsletter(): void {
    this.userStore.subscribeNewsletter(this.nletter).subscribe(() => this.modalRef?.hide());
  }


}


