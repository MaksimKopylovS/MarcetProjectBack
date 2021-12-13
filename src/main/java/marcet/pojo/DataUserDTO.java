package marcet.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import marcet.dto.AddressDTO;
import marcet.dto.UserDTO;
import marcet.model.Address;
import marcet.model.JwtResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataUserDTO {

    private JwtResponse jwtResponse;
    private UserDTO userDTO;
    private List<AddressDTO> addressDTOList;
}
