package com.netops.api.controller;

import com.netops.api.dto.AuthenticationDTO;
import com.netops.api.dto.LoginResponseDTO;
import com.netops.api.dto.RegisterDTO;
import com.netops.api.model.Usuario;
import com.netops.api.repository.UsuarioRepository;
import com.netops.api.service.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final com.netops.api.infra.security.TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid AuthenticationDTO auth) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(auth.login(), auth.senha());
        var validate = authenticationManager.authenticate(usernamePassword);
        var usuario = (Usuario) validate.getPrincipal();
        var token = tokenService.generateToken(usuario);
        return new LoginResponseDTO(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO register) {
        if (usuarioRepository.findByLogin(register.login()) != null) {
            throw new UsernameNotFoundException(register.login());
        }
        String password = new BCryptPasswordEncoder().encode(register.senha());
        Usuario usuario = new Usuario();
        usuario.setLogin(register.login());
        usuario.setSenha(password);
        usuario.setRole(register.role());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }


}
