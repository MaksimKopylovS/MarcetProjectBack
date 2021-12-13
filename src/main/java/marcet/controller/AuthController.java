package marcet.controller;

import lombok.RequiredArgsConstructor;
import marcet.bean.JwtTokenUtil;
import marcet.exceptions_handling.MarketError;
import marcet.model.JwtRequest;
import marcet.model.JwtResponse;
import marcet.pojo.DataUserDTO;
import marcet.service.AddressService;
import marcet.service.AuthService;
import marcet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final AddressService addressService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthTocen(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Ошибка авторизации"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = authService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generationToken(userDetails);
        DataUserDTO dataUserDTO = new DataUserDTO(
                new JwtResponse(token),
                userService.convertToDto(userService.getUserFromName(authRequest.getUsername())),
                addressService.getAddressByUser(authRequest.getUsername()));
        return ResponseEntity.ok(dataUserDTO);
    }
}
