package marcet.service;

import lombok.RequiredArgsConstructor;
import marcet.dto.UserDTO;
import marcet.model.User;
import marcet.repository.UserRepository;
import org.aspectj.weaver.patterns.ParserException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) throws ParserException {
        return modelMapper.map(userDTO, User.class);
    }

    public User getUserFromName(String userName){
           return userRepository.findByUsername(userName).get();
    }
}