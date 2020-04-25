package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entity.Usuario;
import sun.net.www.content.image.jpeg;
import util.JpaUtil;

public class UsuarioDAOImpl implements UsuarioDAO {

	// EntityManagar, faz a conexão com o banco.
	// A conexão com o banco so será realizada quando requisitada, apos a transação
	// a mesma é fechada.

	private EntityManager ent;
	private EntityTransaction tx;

	public UsuarioDAOImpl() {
	}

	@Override
	public boolean salvar(Usuario usuario) {
		//METODO "SALVAR" FARÁ O PAPEL DE INSERT E UPDATE, ATRAVES DO " MERGE ".
		try {
			this.ent = JpaUtil.getEntityManager();
			tx = ent.getTransaction();
			tx.begin();
			/*
			 * verifica que o objeto caso não exista, inseri senão, edita.
			 */
			ent.merge(usuario);
			tx.commit();
			return true;

		} catch (Exception e) {
			if (ent.isOpen()) {
				tx.rollback();
			}
		} finally {
			if (ent.isOpen()) {
				ent.close();
			}
		}
		return false;
	}

	@Override
	public void remover(String emailUsuario) {

		try {
			this.ent = JpaUtil.getEntityManager();
			Usuario usuarioRemover = ent.find(Usuario.class, emailUsuario);
			tx = ent.getTransaction();
			tx.begin();
			ent.remove(usuarioRemover);
			tx.commit();

		} catch (Exception e) {
			if (ent.isOpen()) {
				tx.rollback();
			}
		} finally {
			if (ent.isOpen()) {
				ent.close();
			}
		}
	}

	@Override
	public void alterar(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario pesquisar(String email) {

		Usuario usuario = new Usuario();
		try {
			this.ent = JpaUtil.getEntityManager();
			tx = ent.getTransaction();
			tx.begin();
			usuario = ent.find(Usuario.class, email);

		} catch (Exception e) {
			if (ent.isOpen()) {
				tx.rollback();
			}
		} finally {
			if (ent.isOpen()) {
				ent.close();
			}
		}

		return usuario;
	}

	public List<Usuario> listarTodos() {

		List<Usuario> usuarioList = new ArrayList<Usuario>();

		try {
			this.ent = JpaUtil.getEntityManager();
			tx = ent.getTransaction();
			tx.begin();
			usuarioList = ent.createQuery("From Usuario u").getResultList();

		} catch (Exception e) {
			if (ent.isOpen()) {
				tx.rollback();
			}
		} finally {
			if (ent.isOpen()) {
				ent.close();
			}
		}

		return usuarioList;

	}

}
