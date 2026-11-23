package br.com.bibliotech.factory;

import br.com.bibliotech.exception.ResourceNotFoundException;
import br.com.bibliotech.model.usuario.TipoUsuario;
import br.com.bibliotech.model.usuario.UsuarioEstudante;
import br.com.bibliotech.model.usuario.UsuarioProfessor;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioFactory {
    public TipoUsuario criar(String codigo) {
        return switch (codigo == null ? "" : codigo.toUpperCase()) {
            case "ESTUDANTE" -> new UsuarioEstudante("ESTUDANTE", "Assinatura Estudante (+2 livros)", 2);
            case "PROFESSOR" -> new UsuarioProfessor("PROFESSOR", "Assinatura Professor (+7 dias, 3x livros)", 7, 3);
            default -> throw new ResourceNotFoundException("Tipo de usuário inválido: " + codigo);
        };
    }
}