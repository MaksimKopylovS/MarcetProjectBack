package marcet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.UserDTO;
import marcet.model.User;
import marcet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/*Сервис для регистрации пользователя*/
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public void registrationUser(UserDTO userDTO) {
        log.info("Логин - {}, Пароль - {},  Почта - {}", userDTO.getUsername(), userDTO.getPassword(), userDTO.getMail());
        User user = userService.convertyToEntity(userDTO);
        userRepository.save(user);
    }
}
