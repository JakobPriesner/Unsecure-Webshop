import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Address, Article, Commentary, Contact, Coupon, Order, SpecifiedItem, User} from "../models";
import {RankingStudent} from "../models/rankingStudent";
import {JsonObject} from "@angular/compiler-cli/ngcc/src/utils";
import {CookieService} from "ngx-cookie-service";
import {UuidService} from "./uuidService";
import {Statics} from "./statics";
import {Nletter} from "../models/nletter";

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  sessionKey: string = "";
  uuid: string = "";
  ip: string = "";

  constructor(private httpClient: HttpClient, private cookieService: CookieService, private uuidService: UuidService) {
    this.uuidService.uuid.subscribe(uuid => this.uuid = uuid);
    this.sessionKey = this.cookieService.get('sessionKey').replace('sessionKey=', '');
  }

  getAlerts(): Observable<string[]> {
    return this.httpClient.get<string[]>(Statics.url + 'flaws', {headers: this.getHeader()});
  }

  getArticles(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(Statics.url + 'articles', {headers: this.getHeader()});
  }

  getArticleById(articleNumber: number): Observable<Article> {
    return this.httpClient.get<Article>(Statics.url + 'articles/' + articleNumber, {headers: this.getHeader()});
  }

  getArticlesFrontpage(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(Statics.url + 'articles?page=1&specifications=false', {headers: this.getHeader()});
  }

  loadOrders(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(Statics.url + 'orders', {headers: this.getHeader()});
  }

  loadOrdersWithFullBody(orderNumber: number): Observable<Order> {
    return this.httpClient.get<Order>(Statics.url + 'orders/' + orderNumber, {headers: this.getHeader()});
  }

  getImageById(id: number): Observable<any> {
    return this.httpClient.get(Statics.url + 'pictures/' + id, {responseType: 'text', headers: this.getHeader()});
  }

  postOrder(order: Order): Observable<string> {
    // @ts-ignore
    return this.httpClient.post<string>(Statics.url + 'orders/', {...order}, {
      observe: "response",
      headers: this.getHeader()
    }).pipe(
      map(resp => {
        return resp.headers.get("Location")
      })
    );
  }

  createAddress(address: Address): Observable<Address> {
    let addressPayload = {
      "name": address.name,
      "country": address.country,
      "address": address.address,
      "address2": address.address2,
      "zipCode": address.zipCode,
      "city": address.city
    };
    return this.httpClient.post<Address>(Statics.url + 'users/addresses', addressPayload, {headers: this.getHeader()});
  }

  loadAddressById(id: number): Observable<Address> {
    return this.httpClient.get<Address>(Statics.url + 'users/addresses/' + id, {headers: this.getHeader()});
  }

  loadAllAddresses(): Observable<Address[]> {
    return this.httpClient.get<Address[]>(Statics.url + 'users/addresses', {headers: this.getHeader()});
  }

  updateAddress(address: Address): Observable<any> {
    let addressPayload = {
      ...address
    };
    return this.httpClient.put(Statics.url + 'users/addresses/' + address.id, addressPayload, {headers: this.getHeader()});
  }

  loadShoppingCart(): Observable<SpecifiedItem[]> {
    return this.httpClient.get<SpecifiedItem[]>(Statics.url + 'cart/items', {headers: this.getHeader()})
  }

  updateItemList(itemList: SpecifiedItem[]): Observable<SpecifiedItem[]> {
    let itemsPayload = {items: itemList};
    return this.httpClient.put<SpecifiedItem[]>(Statics.url + 'cart/items', itemsPayload, {headers: this.getHeader()})
  }

  updateShoppingCartItem(item: SpecifiedItem): Observable<SpecifiedItem> {
    let itemPayload = {...item};
    return this.httpClient.put<SpecifiedItem>(Statics.url + 'cart/items/' + item.id, itemPayload, {headers: this.getHeader()})
  }

  deleteItem(itemId: number): Observable<any> {
    return this.httpClient.delete<any>(Statics.url + 'cart/items/' + itemId, {headers: this.getHeader()});
  }

  addItemToShoppingCart(item: SpecifiedItem): Observable<any> {
    return this.httpClient.post(Statics.url + 'cart/items/', {...item}, {headers: this.getHeader()});
  }

  getCoupon(coupon: string): Observable<Coupon> {
    return this.httpClient.get<Coupon>(Statics.url + 'coupons/' + coupon, {headers: this.getHeader()});
  }

  loadWishList(): Observable<SpecifiedItem[]> {
    return this.httpClient.get<SpecifiedItem[]>(Statics.url + 'wishlist/items', {headers: this.getHeader()})
  }

  // updateWishList(specifiedItems: SpecifiedItem[]): Observable<SpecifiedItem[]> {
  //   let itemsPayload = {items: specifiedItems};
  //   return this.httpClient.put<SpecifiedItem[]>(Statics.url + 'wishlist/items', itemsPayload)
  // }

  addItemToWishList(item: SpecifiedItem): Observable<any> {
    return this.httpClient.post<any>(Statics.url + 'wishlist/items', {...item}, {headers: this.getHeader()});
  }

  updateWishListItem(item: SpecifiedItem): Observable<SpecifiedItem> {
    let itemPayload = {...item};
    return this.httpClient.put<SpecifiedItem>(Statics.url + 'wishlist/items/' + item.id, itemPayload, {headers: this.getHeader()})
  }

  deleteWishListItem(itemId: number): Observable<any[]> {
    return this.httpClient.delete<any>(Statics.url + 'wishlist/items/' + itemId, {headers: this.getHeader()});
  }

  deleteWishList(): Observable<any> {
    return this.httpClient.delete<any>(Statics.url + 'wishlist/items', {headers: this.getHeader()});
  }

  postContact(contact: Contact): Observable<Contact> {
    return this.httpClient.post<Contact>(Statics.url + 'contact', {...contact}, {headers: this.getHeader()});
  }

  updateUser(userPayload: JsonObject): Observable<User> {
    return this.httpClient.put<User>(Statics.url + 'users', userPayload, {headers: this.getHeader()});
  }

  getNewsletterStatus(): Observable<boolean> {
    return this.httpClient.get<boolean>(Statics.url + 'users/newsletter', {headers: this.getHeader()});
  }

  postNewsletter(newsLetter: Nletter): Observable<any> {
    return this.httpClient.post<any>(Statics.url + 'users/newsletter', {...newsLetter}, {headers: this.getHeader()});
  }

  deleteNewsletter(): Observable<any> {
    return this.httpClient.delete(Statics.url + 'users/newsletter', {headers: this.getHeader()});
  }

  loadUser(): Observable<User> {
    return this.httpClient.get<User>(Statics.url + 'users', {headers: this.getHeader()});
  }

  loadUserById(id: number): Observable<User> {
    return this.httpClient.get<User>(Statics.url + 'users/' + id, {headers: this.getHeader()});
  }

  searchArticles(searchInput: string): Observable<Article[]> {
    return this.httpClient.get<Article[]>(Statics.url + "articles?search=" + searchInput, {headers: this.getHeader()});
  }

  setLevel(level: number): Observable<any> {
    return this.httpClient.post<any>(Statics.url + 'admin/interface?level=' + level, {}, {headers: this.getHeader()});
  }

  getLevel(): Observable<number> {
    return this.httpClient.get<number>(Statics.url + 'admin/level', {headers: this.getHeader()});
  }

  loadRankingStudents(): Observable<RankingStudent[]> {
    return this.httpClient.get<RankingStudent[]>(Statics.url + 'admin/interface', {headers: this.getHeader()});
  }

  shopReset(): Observable<any> {
    return this.httpClient.put(Statics.url + 'admin/interface', {}, {headers: this.getHeader()});
  }

  rankingReset(): Observable<any> {
    return this.httpClient.delete(Statics.url + 'admin/interface', {headers: this.getHeader()});
  }

  getHeader(): HttpHeaders {
    if (this.uuid != '' && this.sessionKey != '') {
      return new HttpHeaders({"sessionid": this.sessionKey, "uuid": this.uuid});
    } else if (this.uuid != '') {
      return new HttpHeaders({"uuid": this.uuid});
    } else {
      return new HttpHeaders();
    }
  }

  postCommentToArticle(commentary: Commentary, articleId: number): Observable<any>{
    return this.httpClient.post(Statics.url + 'comments?articleId=' + articleId, {...commentary},{headers: this.getHeader()} );
  }

}
