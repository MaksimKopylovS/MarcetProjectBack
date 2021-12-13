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
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public User convertyToEntity(UserDTO userDTO) throws ParserException {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    public User getUserFromName (String userName){
           return userRepository.findByUsername(userName).get();
    }
}