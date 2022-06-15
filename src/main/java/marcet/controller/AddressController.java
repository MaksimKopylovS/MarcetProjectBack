package marcet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.AddressDTO;
import marcet.service.AddressService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/*Контролер обработки адресов пользователя*/
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    /*получение списка адресов по имени пользователя*/
    @GetMapping("/{username}")
    public List<AddressDTO> getAddressesByUsername(@PathVariable String username) {
        return addressService.getAddressByUser(username);
    }

    /*Добавление адреса по имени пользователя */
    @PostMapping
    public List<AddressDTO> addNewAddress(@RequestBody AddressDTO address, @RequestParam(name = "username") String username) {
        addressService.addNewAddress(address, username);
        return addressService.getAddressByUser(username);
    }
}
