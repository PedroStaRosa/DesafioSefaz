package dao;

import java.util.List;

import entity.Usuario;

public interface UsuarioDAO {

	public boolean salvar(Usuario usuario);

	public void remover(String emailUsuario);

	public void alterar(Usuario usuario);

	public Usuario pesquisar(String email);

	public List<Usuario> listarTodos();
	
}
