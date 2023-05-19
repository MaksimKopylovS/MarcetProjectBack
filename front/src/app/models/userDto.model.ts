import { AddressModel } from "./address.model";
import { JwtResponseModel } from "./jwt-response.model";
import { UserModel } from "./user.model";

export interface UserDtoModel {
    addressDTOList: AddressModel[],
    jwtResponse?: JwtResponseModel,
    userDTO?: UserModel,
}
