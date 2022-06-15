package marcet.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.AddressDTO;
import marcet.dto.ProductDTO;
import marcet.dto.UserDTO;
import marcet.model.Address;
import marcet.model.User;
import marcet.repository.AddressRepository;
import marcet.repository.UserRepository;
import org.aspectj.weaver.patterns.ParserException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
/*Класс сервис для работы с адресом*/
@Slf4j
@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /*Конвертация модели в ДТО*/
    public AddressDTO convertToDto(Address address){
        return modelMapper.map(address, AddressDTO.class);
    }

    /*Конвертация ДТО в модель*/
    public Address convertToEntity(AddressDTO addressDTO) throws ParserException {
        return modelMapper.map(addressDTO, Address.class);
    }

    /*Получение списка адресов по имени пользователя*/
    public List<AddressDTO> getAddressByUser(String username) {
        return userRepository
                .findByUsername(username)
                .get()
                .getListAddresses()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()
                );
    }

    /*Добавление адреса пользователю*/
    public void addNewAddress(AddressDTO address, String username) {
        address.setAddressId(null);
        address.setUserId(userRepository.findByUsername(username).get().getUserId());
        Address newAddress = convertToEntity(address);
        addressRepository.save(newAddress);
        address.setAddressId(newAddress.getAddressId());
    }

    /*Поиск адреса по ID*/
    public Address findAddressById(Long addressId) {
        return addressRepository.findById(addressId).get();
    }
}
