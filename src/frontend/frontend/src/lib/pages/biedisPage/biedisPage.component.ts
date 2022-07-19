import {Component, OnDestroy, OnInit, TemplateRef} from "@angular/core";
import {BackendService} from "../../data-access/service/backend.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {RankingStudent} from "../../data-access/models";
import {Router} from "@angular/router";

@Component({
  selector: 'biedis-page',
  templateUrl: './biedisPage.component.html',
  styleUrls: ['./biedisPage.component.scss']
})

export class BiedisPageComponent implements OnInit, OnDestroy{
  rankingStudents: RankingStudent[] | undefined;
  actualStudent: RankingStudent | undefined;
  modalRef?: BsModalRef;
  level: string = "Beginner";
  levelNumber: number = 1;
  description: Map<number,string[]> = new Map();
  levelNames: string[] = ["Beginner", "Tutor", "Endboss"];
  updateInterval: any;

  constructor(private backendService: BackendService, private modalService: BsModalService, private router: Router) {
  }

  ngOnDestroy() {
    clearInterval( this.updateInterval );
  }

  ngOnInit() {
    this.backendService.getLevel().subscribe(levelNumber => {
      this.levelNumber = levelNumber;
      this.level = this.levelNames[levelNumber-1];
      this.onLevelChange(this.levelNumber);
    });
    this.updateInterval = setInterval(() => this.updateRanking(), 5000);
    this.backendService.getLevel().subscribe(levelNumber => this.levelNumber = levelNumber);
    this.backendService.loadRankingStudents().subscribe(rankingStudents => this.rankingStudents = rankingStudents);
  }

  onLevelChange(level: number){
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

  onDescriptionChange(level: number){
    switch (level) {
      case 1:
        this.description.set(1, [
          "In der Beschreibung in den User Settings zu finden, gefiltert wird in diesem Level noch keine SQL Befehle. Es wird nur überprüft, ob es sich um eine SQL Injection handelt" +
          "Geprüft wird auf 'OR 1=1;#' und 'AND 1=1;#'",

          "In dem Nachrichtenbereich im Kontaktformular zu finden, das Fenster schließt sich sobald die Injection durch gelaufen ist. " +
          "Es wird ebenfalls ein SQL Befehl erwartet. Geprüft wird auf „sleep (“, „benchmark (“, „; wait for delay“ und „; wait for time“. " +
          "Wird die Sicherheitslücke ausgelöst, wird eine 15 Sekunden Pause ausgelöst, wodurch der Angreifer seinen Erfolg bemerkt.",

          "Beim Newsletter und bei dem Kontaktformular einfach eine E-Mail Adresse ohne @ angeben.",

          "Durch die Beschreibung in den User Settings kann man in die Kommentare schreiben, gefiltert wird in diesem Level noch nicht.",

          "In den User Settings kann man nicht nur Bildformate hochladen, sondern auch ganz schlimme andere Dateien. Zum Beispiel: png, jpg, jpeg, tiff, gif und bmp ",

          "Auf der Login Seite ist im Html Code, also Quellcode ein Dummyuser versteckt.",

          "In der URL einfach den Betrag über den Queryparam ändern (oder andere Attribute).",

          "Entweder es wird ein Script geschrieben oder es wird durch eine Brute Force Attacke versucht die E-Mail Adresse und das Passwort herauszufinden. " +
          "Level 1 -> E-Mail: Test1@test.de, Passwort: 123456789 und vom DummyUser das Password: MyPasswordIsSafe",

          "Beim Bestellvorgang einfach einen der folgenden Coupons eingeben: blackfriday, summersale, summer, ausverkauf, neujahr, newyear, winter, biedermann, biedisshop",

          "Man versucht über /api/iser/{id} einen User zu löschen, dieser entspricht nicht der gleichen Id wie man selber. Man muss das mit einer Delete Anfrage über ein API Test Tool machen.",

          "In das Kommentarfeld bei den einzelnen Artikeln einfach ein Skript rein schreiben, gefiltert wird in diesem Level noch nicht.",

          "Eine einfache Brute Force Attacke im Loginbereich um ein Passwort oder eine E-Mail herauszufinden. Über ein Tool oder manuell.",

          "Über die SQL Injection zu finden in der Beschreibung in den User Settings zu finden. Dort erhält man einen Hash von einem Dummyuser und zusammen mit der " +
          "E-Mail Adresse kann man sich damit einloggen.",

          "Auf den Endpunkt /api/backup wird von uns aus nie zu gegriffen, wenn man über einen Port Scanner oder über die Url diesen Endpunkt aufruft, wird die Schwachstelle ausgelöst."
        ],);
        break;
      case 2:
        this.description.set(2, [
          "In der Beschreibung in den User Settings zu finden, gefiltert wird in diesem Level ' und # in SQL Befehlen. Diese werden am Anfang und am Ende des Strings entfernt.",

          "In dem Nachrichtenbereich im Kontaktformular zu finden, das Fenster schließt sich sobald die Injection durch gelaufen ist. " +
          "Es wird ebenfalls ein SQL Befehl erwartet. Geprüft wird auf „sleep (“, „benchmark (“, „; wait for delay“ und „; wait for time“. " +
          "Wird die Sicherheitslücke ausgelöst, wird eine 15 Sekunden Pause ausgelöst, wodurch der Angreifer seinen Erfolg bemerkt.",

          "Beim Newsletter und bei dem Kontaktformular einfach eine E-Mail Adresse ohne @ angeben.",

          "Durch die Beschreibung in den User Settings kann man in die Kommentare schreiben, gefiltert wird nach 'script'. Dies wird aus dem String entfernt.",

          "In den User Settings kann man nicht nur Bildformate hochladen, sondern auch ganz schlimme andere Dateien. Zum Beispiel: png, jpg, jpeg, tiff, gif und bmp ",

          "Auf der Login Seite ist im Html Code, also Quellcode ein Dummyuser versteckt.",

          "In der URL einfach den Betrag über den Queryparam ändern (oder andere Attribute).",

          "Entweder es wird ein Script geschrieben oder es wird durch eine Brute Force Attacke versucht die E-Mail Adresse und das Passwort herauszufinden." +
          "Level 2 -> E-Mail: Test2@User.de, Passwort: test123456789 und vom DummyUser das Password: MyPasswordIsSafe",

          "Beim Bestellvorgang einfach einen der folgenden Coupons eingeben: blackfriday, summersale, summer, ausverkauf, neujahr, newyear, winter, biedermann, biedisshop",

          "Man versucht über /api/iser/{id} einen User zu löschen, dieser entspricht nicht der gleichen Id wie man selber. Man muss das mit einer Delete Anfrage über ein API Test Tool machen.",

          "In das Kommentarfeld bei den einzelnen Artikeln einfach ein Skript rein schreiben, gefiltert wird nach 'script'. Dies wird aus dem String entfernt.",

          "Eine einfache Brute Force Attacke im Loginbereich um ein Passwort oder eine E-Mail herauszufinden. Über ein Tool oder manuell.",

          "Über die SQL Injection zu finden in der Beschreibung in den User Settings zu finden. Dort erhält man einen Hash von einem Dummyuser und zusammen mit der " +
          "E-Mail Adresse kann man sich damit einloggen.",

          "Auf den Endpunkt /api/backup wird von uns aus nie zu gegriffen, wenn man über einen Port Scanner oder über die Url diesen Endpunkt aufruft, wird die Schwachstelle ausgelöst."
        ],);
        break;
      case 3:
        this.description.set(3, [
          "In der Beschreibung in den User Settings zu finden, gefiltert wird in diesem Level ', # und 1=1 in SQL Befehlen. Diese werden am Anfang und am Ende des Strings entfernt." +
          "Zusätzlich wird ohne Veränderung auf 'UNION SELECT email, password FROM user;#' geprüft. Es muss zusätzlich eine Anfrage über ein API Test Tool, wie Postman getätigt werden.",

          "In dem Nachrichtenbereich im Kontaktformular zu finden, das Fenster schließt sich sobald die Injection durch gelaufen ist. " +
          "Es wird ebenfalls ein SQL Befehl erwartet. Geprüft wird auf „sleep (“, „benchmark (“, „; wait for delay“ und „; wait for time“. " +
          "Wird die Sicherheitslücke ausgelöst, wird eine 15 Sekunden Pause ausgelöst, wodurch der Angreifer seinen Erfolg bemerkt.",

          "Beim Newsletter und bei dem Kontaktformular einfach eine E-Mail Adresse ohne @ angeben.",

          "Durch die Beschreibung in den User Settings kann man in die Kommentare schreiben, gefiltert wird zusätzlich zu 'script' auch noch das erste '<', '>' und '\\'. Diese werden aus dem String entfernt.",

          "In den User Settings kann man nicht nur Bildformate hochladen, sondern auch ganz schlimme andere Dateien. Zum Beispiel: png, jpg, jpeg, tiff, gif und bmp ",

          "Auf der Login Seite ist im Html Code, also Quellcode ein Dummyuser versteckt.",

          "In der URL einfach den Betrag über den Queryparam ändern (oder andere Attribute).",

          "Entweder es wird ein Script geschrieben oder es wird durch eine Brute Force Attacke versucht die E-Mail Adresse und das Passwort herauszufinden." +
          "Level 3 -> E-Mail: Test3@TestUser.de, Passwort: 0112358132134 und vom DummyUser das Password: MyPasswordIsSafe",

          "Beim Bestellvorgang einfach einen der folgenden Coupons eingeben: blackfriday, summersale, summer, ausverkauf, neujahr, newyear, winter, biedermann, biedisshop",

          "Man versucht über /api/iser/{id} einen User zu löschen, dieser entspricht nicht der gleichen Id wie man selber. Man muss das mit einer Delete Anfrage über ein API Test Tool machen.",

          "In das Kommentarfeld bei den einzelnen Artikeln einfach ein Skript rein schreiben, gefiltert wird zusätzlich zu 'script' auch noch das erste '<', '>' und '\\'. Diese werden aus dem String entfernt.",

          "Eine einfache Brute Force Attacke im Loginbereich um ein Passwort oder eine E-Mail herauszufinden. Über ein Tool oder manuell.",

          "Über die SQL Injection zu finden in der Beschreibung in den User Settings zu finden. Dort erhält man einen Hash von einem Dummyuser und zusammen mit der " +
          "E-Mail Adresse kann man sich damit einloggen.",

          "Auf den Endpunkt /api/backup wird von uns aus nie zu gegriffen, wenn man über einen Port Scanner oder über die Url diesen Endpunkt aufruft, wird die Schwachstelle ausgelöst."
        ],);
        break;
    }
  }

  getInformationArray(level: number) {
    var information: string[] = [
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
      "Login Brute Force",
      "Hash User",
      "Security Misconfiguration"];

    var boolIdentifier: string[] = [
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
      "loginBruteForce",
      "hashUser",
      "securityMisconfiguration"];

    var routingIdentifier: string[] = [
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
      "/login_brute_force",
      "/hash_user",
      "/security_misconfiguration"
    ]

    let tmpStudent: RankingStudent = this.actualStudent!;
    return this.description.get(level)!.map(function(item, index){
      let identifier = boolIdentifier[index] as keyof RankingStudent;
      return [item, information[index], tmpStudent[identifier], routingIdentifier[index]];
    })
  }

  updateRanking() {
    this.backendService.loadRankingStudents().subscribe(rankingStudents => this.rankingStudents = rankingStudents);
  }

  getSecurityBreaches(rankingStudent: RankingStudent): number{
    let securityBreachCounter: number = 0;
    if(rankingStudent.blindSqlInjection)
      securityBreachCounter++;
    if(rankingStudent.xss)
      securityBreachCounter++;
    if(rankingStudent.profile_picture)
      securityBreachCounter++;
    if(rankingStudent.priceOrderBug)
      securityBreachCounter++;
    if(rankingStudent.loginBruteForce)
      securityBreachCounter++;
    if(rankingStudent.htmlCommentUser)
      securityBreachCounter++;
    if(rankingStudent.guessUserLogin)
      securityBreachCounter++;
    if(rankingStudent.guessCoupon)
      securityBreachCounter++;
    if(rankingStudent.hashUser)
      securityBreachCounter++;
    if(rankingStudent.emailWithoutAt)
      securityBreachCounter++;
    if(rankingStudent.commentXss)
      securityBreachCounter++;
    if(rankingStudent.sqlInjection)
      securityBreachCounter++;
    if(rankingStudent.deleteUser)
      securityBreachCounter++;
    return securityBreachCounter;
  }

  openModal(securityBreaches: TemplateRef<any>, rankingStudent: RankingStudent) {
    if(rankingStudent){
      this.actualStudent = rankingStudent;
    } else {
      this.actualStudent!.ipAddress = "-1";
    }
      this.modalRef = this.modalService.show(securityBreaches, {animated: true});
  }

  onShopReset() {
    this.backendService.shopReset().subscribe();
  }

  onRankingReset() {
    this.backendService.rankingReset().subscribe( () => this.updateRanking());
  }

  onRedirectToFlawDescription( flaw: string ): void {
    this.modalRef?.hide();
    this.router.navigateByUrl!( `/${flaw}` );
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
