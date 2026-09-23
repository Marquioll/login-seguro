package com.loginseguro.service;

import com.loginseguro.model.Usuario;
import com.loginseguro.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        String emailNormalizado = email.trim().toLowerCase();

        Usuario usuario = usuarioRepository
                .findByEmail(emailNormalizado)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuário não encontrado."
                        )
                );

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(usuario.getRoles().toArray(new String[0]))
                .disabled(!usuario.isAtivo())
                .build();
    }
}
