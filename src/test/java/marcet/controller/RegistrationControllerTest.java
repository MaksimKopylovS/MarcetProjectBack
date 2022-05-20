package marcet.controller;

import marcet.MarcetServiceApplication;
import marcet.dto.UserDTO;
import marcet.service.RegistrationService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MarcetServiceApplication.class)
@RunWith(SpringRunner.class)
public class RegistrationControllerTest {

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RegistrationService registrationService;

    private UserDTO userDTO;

    public RegistrationControllerTest(){
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Username");
        userDTO.setPassword("Pass");
        userDTO.setMail("mail@mail");
    }

    @Test
    public void registrationUserTest(){

        Mockito
                .doNothing()
                .when(registrationService)
                .registrationUser(userDTO);

        Assertions.assertEquals(ResponseEntity.ok(userDTO), registrationController.registrationUser(userDTO));
    }


}
