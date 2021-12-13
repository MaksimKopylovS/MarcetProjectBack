package marcet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.AddressDTO;
import marcet.service.AddressService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{username}")
    public List<AddressDTO> getAddressesByUsername(@PathVariable String username) {
        return addressService.getAddressByUser(username);
    }

    @PostMapping
    public List<AddressDTO> addNewAddress(@RequestBody AddressDTO address, @RequestParam(name = "username") String username) {
        addressService.addNewAddress(address, username);
        return addressService.getAddressByUser(username);
    }
}
