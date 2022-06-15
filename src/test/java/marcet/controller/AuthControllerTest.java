package marcet.controller;

import marcet.dto.UserDTO;
import marcet.model.JwtRequest;
import marcet.model.JwtResponse;
import marcet.model.DataUserDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = AuthController.class)
@RunWith(SpringRunner.class)
public class AuthControllerTest {

    @MockBean
    private AuthController authController;

    private UserDTO userDTO;
    private DataUserDTO dataUserDTO;
    private JwtResponse jwtResponse;
    private JwtRequest jwtRequest;

    public AuthControllerTest() {
        jwtResponse = new JwtResponse("token");
        jwtResponse.setToken("tokenRequest");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Username");
        userDTO.setPassword("Pass");
        userDTO.setMail("mail@mail");

        dataUserDTO = new DataUserDTO();
        dataUserDTO.setUserDTO(userDTO);
        dataUserDTO.setJwtResponse(jwtResponse);

        jwtRequest = new JwtRequest();
        jwtRequest.setUsername("UserName");
        jwtResponse.setToken("TokenResposse");
    }

    @Test
    public void authControllerTest() {
        Mockito
                .doReturn(ResponseEntity.ok(dataUserDTO))
                .when(authController)
                .createAuthTocen(jwtRequest);

        Assertions.assertEquals(ResponseEntity.ok(dataUserDTO), authController.createAuthTocen(jwtRequest));
    }

}
