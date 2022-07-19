import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {AlertMessagesStore} from "../../data-access/service/store/alertMessages.store";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";

@Component({
  selector: 'alert-messages',
  templateUrl: './messageAlerts.component.html',
  styleUrls: ['./messageAlerts.component.scss']
})

export class MessageAlertsComponent implements OnInit {

  @ViewChild("alertSection") alertSectionRef: ElementRef | undefined;

  constructor(private alertStore: AlertMessagesStore, private toast: ToastrService, private routing: Router) {
  }

  ngOnInit(): void {
    this.alertStore.alertSubject.subscribe(alert => {
      let toast = this.toast.success(alert, "Schwachstelle gefunden!");
      toast.onTap.subscribe(() => {
        this.routing.navigateByUrl!('/' + alert);
      })
    });
  }

  nextMessage(): void {
    this.alertStore.addAlertMessage("test");
  }

}
