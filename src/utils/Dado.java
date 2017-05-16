package utils;

public class Dado {

	private int remetente;
	private int destinatario;
	private byte[] conteudo;

	public Dado(int remetente, int destinatario, byte[] conteudo) {
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

}
