import {Address} from "./address";
import {Payment} from "./payment";
import {SpecifiedItem} from "./specifiedItem";
import {Coupon} from "./coupon";

export interface Order {
  orderNumber?: number;
  date?: string;
  specifiedItems?: SpecifiedItem[];
  amount?: number;
  address?: Address;
  payment?: Payment;
  coupon?: Coupon;
}
