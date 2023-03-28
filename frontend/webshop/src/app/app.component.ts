import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Bidermanns Shop';

  constructor(private route: ActivatedRoute) {
  }

  showNavbarItems(): boolean {
    return this.route.snapshot.firstChild?.routeConfig?.path == "index";
  }
}
