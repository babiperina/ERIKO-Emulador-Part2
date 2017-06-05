package utils;

public class Dado {

	private int qtde;
	private int tipo;
	private byte[] conteudo;

	public Dado(int tipo, byte[] conteudo) {
		super();
		this.setTipo(tipo);
		this.conteudo = conteudo;
	}

	public Dado(int tipo, int qtde) {
		super();
		this.setTipo(tipo);
		this.setQtde(qtde);
	}

	public Dado(int tipo) {
		super();
		this.setTipo(tipo);
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
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
