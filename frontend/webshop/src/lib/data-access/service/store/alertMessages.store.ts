import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {BackendService} from "../backend.service";

@Injectable({
  providedIn: 'root'
})
export class AlertMessagesStore {
  alertSubject: Subject<string> = new Subject<string>();

  constructor(private backendService: BackendService) {
    setInterval(() => this.updateAlerts(), 3000);
  }

  addAlertMessage(message: string): void {
    this.alertSubject.next(message);
  }

  updateAlerts() {
    this.backendService.getAlerts().subscribe(alerts => {
      alerts.forEach(alert => this.addAlertMessage(alert));
    });
  }


}
