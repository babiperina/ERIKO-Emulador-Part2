package utils;

public class Dado {

	private int remetente;
	private int destinatario;
	private int qtde;
	private int tipo;
	private byte[] conteudo;

	public Dado(int remetente, int destinatario, int tipo, byte[] conteudo) {
		super();
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.setTipo(tipo);
		this.conteudo = conteudo;
	}

	public Dado(int remetente, int destinatario, int tipo, int qtde) {
		super();
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.setTipo(tipo);
		this.setQtde(qtde);
	}

	public Dado(int remetente, int destinatario, int tipo) {
		super();
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.setTipo(tipo);
	}

	public int getRemetente() {
		return remetente;
	}

	public void setRemetente(int remetente) {
		this.remetente = remetente;
	}

	public int getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(int destinatario) {
		this.destinatario = destinatario;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public String toString() {
		return "Dado [remetente=" + remetente + ", destinatario=" + destinatario + ", conteudo=" + conteudo + "]";
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
