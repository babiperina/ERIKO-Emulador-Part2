package utils;

public class Endereco {
	private int remetente;
	private int destinatario;
	private int tipo;
	private int endereco;

	public Endereco(int remetente, int destinatario, int tipo) {
		super();
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.setTipo(tipo);
	}

	public Endereco(int remetente, int destinatario, int tipo, int endereco) {
		super();
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.setTipo(tipo);
		this.setEndereco(endereco);
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

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getEndereco() {
		return endereco;
	}

	public void setEndereco(int endereco) {
		this.endereco = endereco;
	}
}
