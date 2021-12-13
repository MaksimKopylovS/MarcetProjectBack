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
@Slf4j
@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AddressDTO convertToDto(Address address){
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        return addressDTO;
    }

    public Address convertToEntity(AddressDTO addressDTO) throws ParserException {
        Address address = modelMapper.map(addressDTO, Address.class);
        return address;
    }

    public List<AddressDTO> getAddressByUser(String username) {
        User user = userRepository.findByUsername(username).get();
        List<AddressDTO> addressList = user.getListAddresses().stream().map(this::convertToDto).collect(Collectors.toList());
        return addressList;
    }

    public void addNewAddress(AddressDTO address, String username) {
        address.setAddressId(null);
        address.setUserId(userRepository.findByUsername(username).get().getUserId());
        Address newAddress = convertToEntity(address);
        addressRepository.save(newAddress);
        address.setAddressId(newAddress.getAddressId());
    }

    public Address findAddressById(Long addressId) { // LSS поиск адреса по id
        return addressRepository.findById(addressId).get();
    }
}
