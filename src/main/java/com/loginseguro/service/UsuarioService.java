package com.loginseguro.service;

import com.loginseguro.dto.CadastroUsuarioDTO;
import com.loginseguro.model.Usuario;
import com.loginseguro.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.loginseguro.model.Role;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrar(CadastroUsuarioDTO cadastro) {

        String emailNormalizado = cadastro.getEmail().trim().toLowerCase();

        if (usuarioRepository.existsByEmail(emailNormalizado)) {
            throw new IllegalArgumentException(
                    "Já existe um usuário cadastrado com este e-mail."
            );
        }

        String senhaCriptografada =
                passwordEncoder.encode(cadastro.getSenha());

        Usuario usuario = new Usuario(
                cadastro.getNome().trim(),
                emailNormalizado,
                senhaCriptografada
        );

        usuario.getRoles().add(Role.ROLE_USER);
        usuario.setAtivo(true);

        return usuarioRepository.save(usuario);
    }
}
