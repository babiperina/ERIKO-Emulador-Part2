package utils;

public class Endereco {
	private int remetente;
	private int destinatario;
	private int conteudo;

	public Endereco(int remetente, int destinatario, int conteudo) {
		super();
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.conteudo = conteudo;
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

	public int getConteudo() {
		return conteudo;
	}

	public void setConteudo(int conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public String toString() {
		return "Endereço [remetente=" + remetente + ", destinatario=" + destinatario + ", conteudo=" + conteudo + "]";
	}
}
