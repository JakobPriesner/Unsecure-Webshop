import {Component, OnInit, TemplateRef} from "@angular/core";
import {BackendService} from "../../data-access/service/backend.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {RankingStudent} from "../../data-access/models";

@Component({
  selector: 'biedis-page',
  templateUrl: './biedisPage.component.html',
  styleUrls: ['./biedisPage.component.scss']
})

export class BiedisPageComponent implements OnInit {


  // @ts-ignore
  rankingStudents: RankingStudent[];
  // @ts-ignore
  actualStudent: RankingStudent;
  modalRef?: BsModalRef;
  level: string = "Beginner";
  levelNumber: number = 1;
  description: Map<number, string[]> = new Map();
  levelNames: string[] = ["Beginner", "Tutor", "Endboss"];

  constructor(private backendService: BackendService, private modalService: BsModalService) {
    this.backendService.getLevel().subscribe(levelNumber => {
      this.levelNumber = levelNumber;
      this.level = this.levelNames[levelNumber - 1];
    });
    this.onLevelChange(this.levelNumber);
  }

  onLevelChange(level: number) {
    switch (level) {
      case 1:
        this.level = "Beginner";
        this.levelNumber = 1;
        this.backendService.setLevel(this.levelNumber).subscribe();
        this.onDescriptionChange(level);
        break;
      case 2:
        this.level = "Tutor";
        this.levelNumber = 2;
        this.backendService.setLevel(this.levelNumber).subscribe();
        this.onDescriptionChange(level);
        break;
      case 3:
        this.level = "Endboss";
        this.levelNumber = 3;
        this.backendService.setLevel(this.levelNumber).subscribe();
        this.onDescriptionChange(level);
        break;
    }
    this.backendService.setLevel(level);
  }

  onDescriptionChange(level: number) {
    switch (level) {
      case 1:
        this.description.set(1, [
          "In der Beschreibung in den User Settings zu finden, gefiltert wird in diesem Level noch keine SQL Befehle.",
          "In dem Nachrichtenbereich im Kontaktformular zu finden, das Fenster schließt sich sobald die Injection durch gelaufen ist. " +
          "Es wird ebenfalls ein SQL Befehl erwartet.",
          "Beim Newsletter und bei dem Kontaktformular einfach eine E-Mail Adresse ohne @ angeben.",
          "Durch die Beschreibung in den User Settings kann man in die Kommentare schreiben, gefiltert wird in diesem Level noch nicht.",
          "In den User Settings kann man nicht nur Bildformate hochladen, sondern auch ganz schlimme andere Dateien.",
          "Auf der Login Seite ist im Html Code, also Quellcode ein Dummyuser versteckt.",
          "In der URL einfach den Betrag über den Queryparam ändern (oder andere Attribute).",
          "Entweder es wird ein Script geschrieben oder es wird durch eine Brute Force Attacke versucht die E-Mail Adresse und das Passwort herauszufinden.",
          "Zu finden - guessCoupon",
          "Man versucht über /id einen User zu löschen, dieser entspricht nicht der gleichen Id wie man selber.",
          "Ist im Kommentar",
          "Beim Registrieren oder beim Einloggen, ploppt ein kleines Fenster auf wenn es die E-Mail Adresse schon gibt. Brute Force Attacke.",
          "Über die SQL Injection zu finden in der Beschreibung in den User Settings zu finden. Dort erhält man einen Hash von einem Dummyuser und zusammen mit der " +
          "E-Mail Adresse kann man sich damit einloggen."
        ],);
        break;
      case 2:
        this.description.set(2, [
          "In der Beschreibung in den User Settings zu finden, gefiltert wird in diesem Level ' und # in SQL Befehlen.",
          "In dem Nachrichtenbereich im Kontaktformular zu finden, das Fenster schließt sich sobald die Injection durch gelaufen ist. " +
          "Es wird ebenfalls ein SQL Befehl erwartet.",
          "Beim Newsletter und bei dem Kontaktformular einfach eine E-Mail Adresse ohne @ angeben.",
          "Durch die Beschreibung in den User Settings kann man in die Kommentare schreiben, gefiltert wird in diesem Level noch nicht.",
          "In den User Settings kann man nicht nur Bildformate hochladen, sondern auch ganz schlimme andere Dateien.",
          "Auf der Login Seite ist im Html Code, also Quellcode ein Dummyuser versteckt.",
          "In der URL einfach den Betrag über den Queryparam ändern (oder andere Attribute).",
          "Entweder es wird ein Script geschrieben oder es wird durch eine Brute Force Attacke versucht die E-Mail Adresse und das Passwort herauszufinden.",
          "Level 2 Zu finden - guessCoupon",
          "Man versucht über /id einen User zu löschen, dieser entspricht nicht der gleichen Id wie man selber.",
          "Ist im Kommentar",
          "Beim Registrieren oder beim Einloggen, ploppt ein kleines Fenster auf wenn es die E-Mail Adresse schon gibt. Brute Force Attacke.",
          "Über die SQL Injection zu finden in der Beschreibung in den User Settings zu finden. Dort erhält man einen Hash von einem Dummyuser und zusammen mit der " +
          "E-Mail Adresse kann man sich damit einloggen."
        ],);
        break;
      case 3:
        this.description.set(3, [
          "In der Beschreibung in den User Settings zu finden, gefiltert wird in diesem Level ', # und 1=1 in SQL Befehlen.",
          "In dem Nachrichtenbereich im Kontaktformular zu finden, das Fenster schließt sich sobald die Injection durch gelaufen ist. " +
          "Es wird ebenfalls ein SQL Befehl erwartet.",
          "Beim Newsletter und bei dem Kontaktformular einfach eine E-Mail Adresse ohne @ angeben.",
          "Durch die Beschreibung in den User Settings kann man in die Kommentare schreiben, gefiltert wird in diesem Level noch nicht.",
          "In den User Settings kann man nicht nur Bildformate hochladen, sondern auch ganz schlimme andere Dateien.",
          "Auf der Login Seite ist im Html Code, also Quellcode ein Dummyuser versteckt.",
          "In der URL einfach den Betrag über den Queryparam ändern (oder andere Attribute).",
          "Entweder es wird ein Script geschrieben oder es wird durch eine Brute Force Attacke versucht die E-Mail Adresse und das Passwort herauszufinden.",
          "Level 3 Zu finden - guessCoupon",
          "Man versucht über /id einen User zu löschen, dieser entspricht nicht der gleichen Id wie man selber.",
          "Ist im Kommentar",
          "Beim Registrieren oder beim Einloggen, ploppt ein kleines Fenster auf wenn es die E-Mail Adresse schon gibt. Brute Force Attacke.",
          "Über die SQL Injection zu finden in der Beschreibung in den User Settings zu finden. Dort erhält man einen Hash von einem Dummyuser und zusammen mit der " +
          "E-Mail Adresse kann man sich damit einloggen."
        ],);
        break;
    }
  }

  getInfoSites(vulnerability: string) {
    // console.log(vulnerability);
    // if(vulnerability == "SQL-Injection"){
    //   this.router.navigateByUrl('/sql_injection');
    // }
    // if(vulnerability == "Blind-Sql-Injection"){
    //   this.router.navigateByUrl('/blind_sql_injection');
    // }
    // if(vulnerability == "E-Mail without @"){
    //   this.router.navigateByUrl('/email_without_at');
    // }
    // if(vulnerability == "XSS - Cross-Site-Scripting"){
    //   this.router.navigateByUrl('/xss');
    // }
    // if(vulnerability == "Profile Picture"){
    //   this.router.navigateByUrl('/profile_picture');
    // }
    // if(vulnerability == "HTML Comment User"){
    //   this.router.navigateByUrl('/html_comment_user');
    // }
    // if(vulnerability == "Price Order Bug") {
    //   this.router.navigateByUrl('/price_order_bug');
    // }
    // if(vulnerability == "Guess User Login") {
    //   this.router.navigateByUrl('/guess_user_login');
    // }
    // if(vulnerability == "Guess Coupon") {
    //   this.router.navigateByUrl('/guess_coupon');
    // }
    // if(vulnerability == "Delete User") {
    //   this.router.navigateByUrl('/delete_user');
    // }
    // if(vulnerability == "Comment XSS - Cross-Site-Scripting") {
    //   this.router.navigateByUrl('/sql_injection');
    // }
    // if(vulnerability == "Look for E-Mail Address") {
    //   this.router.navigateByUrl('/look_for_email_address');
    // }
    // if(vulnerability == "Hash User") {
    //   this.router.navigateByUrl('/hash_user');
    // }
  }

  getInformationArray(level: number) {
    let information: string[] = [
      "SQL-Injection",
      "Blind-Sql-Injection",
      "E-Mail without @",
      "XSS - Cross-Site-Scripting",
      "Profile Picture",
      "HTML Comment User",
      "Price Order Bug",
      "Guess User Login",
      "Guess Coupon",
      "Delete User",
      "Comment XSS - Cross-Site-Scripting",
      "Look for E-Mail Address",
      "Hash User"];

    let boolIdentifier: string[] = [
      "sqlInjection",
      "blindSqlInjection",
      "emailWithoutAt",
      "xss",
      "profile_picture",
      "htmlCommentUser",
      "priceOrderBug",
      "guessUserLogin",
      "guessCoupon",
      "deleteUser",
      "commentXss",
      "lookForEmailAddress",
      "hashUser"];

    let routingIdentifier: string[] = [
      "/sql_injection",
      "/blind_sql_injection",
      "/email_without_at",
      "/xss",
      "/profile_picture",
      "/html_comment_user",
      "/price_order_bug",
      "/guess_user_login",
      "/guess_coupon",
      "/delete_user",
      "/sql_injection",
      "/look_for_email_address",
      "/hash_user"
    ]

    let tmpStudent: RankingStudent = this.actualStudent;

    return this.description.get(level)!.map(function (item, index) {
      let identifier = boolIdentifier[index] as keyof RankingStudent;
      return [item, information[index], tmpStudent[identifier], routingIdentifier[index]];
    })
  }

  ngOnInit() {
    this.backendService.getLevel().subscribe(levelNumber => this.levelNumber = levelNumber);
    this.backendService.loadRankingStudents().subscribe(rankingStudents => this.rankingStudents = rankingStudents);
    this.reloadRanking();
  }

  reloadRanking() {
    setInterval(function () {
    }, 10000);
    this.backendService.loadRankingStudents().subscribe(rankingStudents => this.rankingStudents = rankingStudents);
  }

  getSecurityBreaches(rankingStudent: RankingStudent): number {
    let securityBreachCounter: number = 0;
    if (rankingStudent.blindSqlInjection)
      securityBreachCounter++;
    if (rankingStudent.xss)
      securityBreachCounter++;
    if (rankingStudent.profile_picture)
      securityBreachCounter++;
    if (rankingStudent.priceOrderBug)
      securityBreachCounter++;
    if (rankingStudent.lookForEmailAddress)
      securityBreachCounter++;
    if (rankingStudent.htmlCommentUser)
      securityBreachCounter++;
    if (rankingStudent.guessUserLogin)
      securityBreachCounter++;
    if (rankingStudent.guessCoupon)
      securityBreachCounter++;
    if (rankingStudent.hashUser)
      securityBreachCounter++;
    if (rankingStudent.emailWithoutAt)
      securityBreachCounter++;
    if (rankingStudent.commentXss)
      securityBreachCounter++;
    if (rankingStudent.sqlInjection)
      securityBreachCounter++;
    if (rankingStudent.deleteUser)
      securityBreachCounter++;
    return securityBreachCounter;
  }

  openModal(securityBreaches: TemplateRef<any>, rankingStudent: RankingStudent) {
    if (rankingStudent) {
      this.actualStudent = rankingStudent;
    } else {
      this.actualStudent.ipAddress = "-1";
    }
    this.modalRef = this.modalService.show(securityBreaches, {animated: true});
  }

  onShopReset() {
    this.backendService.shopReset();
  }

  onRankingReset() {
    this.backendService.rankingReset();
  }

  getLevelClass(): string {
    switch (this.levelNumber) {
      case 1:
        return "fas fa-fish";
      case 2:
        return "fas fa-cat";
      case 3:
        return "fas fa-dragon";
      default:
        return "";
    }

  }

}
