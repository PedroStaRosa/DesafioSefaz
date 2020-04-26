package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import entity.Telefone;
import entity.Usuario;
import util.JpaUtil;

@ManagedBean(name = "UsuarioBean")
@SessionScoped
public class UsuarioBean {

	private Usuario usuario;
	private Usuario UsuarioLogado;
	private Telefone telefone;
	private List<Usuario> listaUsuarios;
	private String emailUsuarioSelecionado;
	private String areaProfissionalSelecionada = "";
	private long idTelefoneSelecionado; // PARA IMPLEMENTAR CASO USUARIO QUERIA EXCLUIR UM TELEFONE DE SUA LISTA( EM
										// DESENV. )

	private UsuarioDAO usuarioDAO;
	private String mensagem;
	private Usuario usuarioExiste; // SERÁ PREENCHIDO NO INSERIR CASO EMAIL SEJA ENCONTRADO, VALIDA EMAIL EXISTENTE

	private static final String INSERIR = "inserirUsuario.xhtml";
	private static final String PESQUISAR = "AdmCarregarUsuarios.xhtml";
	private static final String LISTATELEFONE = "listaTelefone.xhtml";
	private static final String EDITAR = "editarUsuario.xhtml";
	private static final String MEUS_DADOS = "meusDados.xhtml";

	public UsuarioBean() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		this.UsuarioLogado = (Usuario) facesContext.getExternalContext().getSessionMap().get("usuarioLogado");

		this.usuario = new Usuario();
		this.usuario.setTelefones(new ArrayList<Telefone>());
		this.telefone = new Telefone();
		this.listaUsuarios = new ArrayList<Usuario>();
		this.usuarioDAO = new UsuarioDAOImpl();
		this.listaUsuarios = this.usuarioDAO.listarTodos("");
		if (this.listaUsuarios.isEmpty()) {
			addMessage("Lista vazia", "Nenhum usuário cadastrado");
			;
		}
	}

	public void pesquisarTodos() {
		this.emailUsuarioSelecionado = ""; // PERDENDO O VALOR, CASO VENHA DE UMA PESQUISA INDIVIDUAL.
		System.out.println("area" + this.areaProfissionalSelecionada);
		this.listaUsuarios = this.usuarioDAO.listarTodos(this.areaProfissionalSelecionada);
		if (this.listaUsuarios.isEmpty()) {
			addMessageError("Desculpe.", "Não temos nenhum profissional cadastrado nessa área");
		} else {
			addMessage("Lista Carregada com Sucesso", "Sucesso");
		}

	}

	public void pesquisarUsuario() {

		Usuario usuarioPesquisar = this.usuarioDAO.pesquisar(emailUsuarioSelecionado);
		List<Usuario> usuarioSelecionado = new ArrayList<Usuario>();
		usuarioSelecionado.add(usuarioPesquisar);
		this.listaUsuarios = usuarioSelecionado;
		if (usuarioPesquisar == null) {
			addMessageError("Nenhum usuario encontrado", "Não encontrado");
		} else {
			addMessage("Buscar realizada com sucesso", "Sucesso!!");
		}
	}

	public void salvarUsuario() throws IOException {

		if (this.usuarioDAO.salvar(this.usuario)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Sucesso !!!"));
			abrirPesquisarUsuario();

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao inserir !!!"));
		}

	}

	public void removerUsuario() {

		this.usuarioDAO.remover(emailUsuarioSelecionado);
		limpar();
		this.listaUsuarios = this.usuarioDAO.listarTodos("");
		addMessage("* Usuário deletado com sucesso", "Lista recarregada");

	}

	public void editarUsuario() throws IOException {

		Usuario usuarioEditar = this.usuarioDAO.pesquisar(emailUsuarioSelecionado);
		this.usuario = usuarioEditar;
		abrirEditarUsuario();

	}

	public void inserirUsuario() throws IOException {

		this.limparMensagem();
		abrirInserirPessoa();
	}

	public void inserirTelefones() {
		if (!this.validaExisteTelefone(this.telefone)) {

			Telefone novoTelefone = new Telefone();

			novoTelefone.setDdd(this.telefone.getDdd());
			novoTelefone.setNumero(this.telefone.getNumero());
			novoTelefone.setTipo(this.telefone.getTipo());
			novoTelefone.setUsuario(this.usuario);

			this.usuario.getTelefones().add(novoTelefone);

			this.telefone = new Telefone();
		} else {
			addMessageError("Telefone ja cadastrado em sua lista.", "Telefone já cadastrado");
		}
	}

	private boolean validaExisteTelefone(Telefone telefone) {

		boolean retorno = false;
		for (Telefone telefoneLista : this.usuario.getTelefones()) {
			if (telefoneLista.getDdd() == telefone.getDdd() && telefoneLista.getNumero().equals(telefone.getNumero())) {
				retorno = true;
			}
		}
		return retorno;

	}

	public void novoUsuario() throws IOException {
		// VALIDA EMAIL EXISTENTE PARA CADASTRO DE NOVO USUÁRIO.
		usuarioExiste = this.usuarioDAO.pesquisar(this.usuario.getEmail());
		if (usuarioExiste == null) {
			salvarUsuario();
		} else {
			addMessageError("Email já cadastrado.", "Insira um email diferente.");
		}

	}

	public void listaTelefoneCad() throws IOException {
		Usuario usuarioSelecionado = this.usuarioDAO.pesquisar(emailUsuarioSelecionado);
		this.usuario = usuarioSelecionado;
		System.out.println(this.usuario.getNome());
		this.emailUsuarioSelecionado = "";
		// abrirListaTelefone();
	}

	public void limparMensagem() {
		this.mensagem = "";
	}

	public void limpar() {
		this.listaUsuarios = new ArrayList<Usuario>();
		this.usuario = new Usuario();
		this.usuario.setTelefones(new ArrayList<Telefone>());
		this.telefone = new Telefone();
		this.mensagem = "";
		this.emailUsuarioSelecionado = "";
	}

	// ADICIONA MENSAGENS
	public void addMessage(String titulo, String detalhes) {

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, detalhes);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	public void addMessageError(String titulo, String detalhes) {

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, detalhes);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	// DIRECIONAR PAGINAS
	public void abrirPesquisarUsuario() throws IOException {
		this.limpar();
		this.listaUsuarios = this.usuarioDAO.listarTodos(this.areaProfissionalSelecionada);
		FacesContext.getCurrentInstance().getExternalContext().redirect(PESQUISAR);
	}

	public void abrirInserirPessoa() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(INSERIR);
	}

	public void abrirEditarUsuario() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(EDITAR);
	}

	public void abrirListaTelefone() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(LISTATELEFONE);
	}

	public void abrirMeusDados() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(MEUS_DADOS);
	}

	// GETTERS AND SETTERS
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public String getEmailUsuarioSelecionado() {
		return emailUsuarioSelecionado;
	}

	public void setEmailUsuarioSelecionado(String emailUsuarioSelecionado) {
		this.emailUsuarioSelecionado = emailUsuarioSelecionado;
	}

	public long getIdTelefoneSelecionado() {
		return idTelefoneSelecionado;
	}

	public void setIdTelefoneSelecionado(long idTelefoneSelecionado) {
		this.idTelefoneSelecionado = idTelefoneSelecionado;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Usuario getUsuarioLogado() {
		return UsuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		UsuarioLogado = usuarioLogado;
	}

	public String getAreaProfissionalSelecionada() {
		return areaProfissionalSelecionada;
	}

	public void setAreaProfissionalSelecionada(String areaProfissionalSelecionada) {
		this.areaProfissionalSelecionada = areaProfissionalSelecionada;
	}
}
