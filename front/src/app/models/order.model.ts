import { AddressModel } from "./address.model";
import { OrderItemModel } from "./order-item.model";
import { UserModel } from "./user.model";

export interface OrderModel{
    order_id: bigint
    user: UserModel
    totalQuantity: number
    totalCost: number,
    address: AddressModel
    orderItems: OrderItemModel[]
}