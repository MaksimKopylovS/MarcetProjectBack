import { ProductModel } from "./product.model";

export interface BasketItemModel {
    basketItemId?: number,
    userId?: number,
    productDTO: ProductModel,
    quantity: number
}