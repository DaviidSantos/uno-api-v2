package com.solbs.uno.services;

import com.solbs.uno.entities.Usuario;
import com.solbs.uno.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Método que salva um usuário na base de dados
     * @param usuario Usuário a ser salvo
     * @return Usuário salvo
     */
    @Transactional
    public Usuario salvarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    /**
     * Método que procura todos os usuários na base de dados
     * @return Lista de usuários
     */
    public List<Usuario> procurarTodosUsuarios(){
        return usuarioRepository.findAll();
    }

    /**
     * Método que procura um usuário a partir de seu id na base de dados
     * @param idUsuario Id do usuário a ser procurado
     * @return Usuario
     */
    public Usuario procurarUsuarioPorId(String idUsuario){
        return usuarioRepository.findById(idUsuario).get();
    }

    /**
     * Método que deleta um usuário
     * @param usuario Usuário a ser deletado
     */
    @Transactional
    public void deletarUsuario(Usuario usuario){
        usuarioRepository.delete(usuario);
    }

    /**
     * Método que altera senha de um usuário
     * @param usuario Usuário cuja senha será alterada
     * @param novaSenha Nova senha
     * @return Usuário atualizado
     */
    public Usuario alterarSenha(Usuario usuario, String novaSenha){
        usuario.setSenha(novaSenha);
        return usuarioRepository.save(usuario);
    }
}
