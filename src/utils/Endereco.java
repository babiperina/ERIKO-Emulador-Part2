package utils;

public class Endereco {
	private int tipo;
	private int endereco;

	public Endereco(int tipo) {
		super();
		this.setTipo(tipo);
	}

	public Endereco(int tipo, int endereco) {
		super();
		this.setTipo(tipo);
		this.setEndereco(endereco);
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

	@Override
	public String toString() {
		return "Endereco [tipo=" + tipo + ", endereco=" + endereco + "]";
	}
	
}
