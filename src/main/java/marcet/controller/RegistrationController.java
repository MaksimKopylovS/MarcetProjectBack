package marcet.controller;

import lombok.RequiredArgsConstructor;
import marcet.dto.UserDTO;
import marcet.model.User;
import marcet.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class RegistrationController {
    private final RegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public ResponseEntity<?> registrationUser(@RequestBody UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        try {
            registrationService.registrationUser(userDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/auth-user")
    public void authUserToMarcetService(@RequestBody User user) {
    }
}
