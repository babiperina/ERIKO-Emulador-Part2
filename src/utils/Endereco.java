package utils;

public class Endereco {
	private int tipo;
	private long endereco;

	public Endereco(int tipo) {
		super();
		this.setTipo(tipo);
	}

	public Endereco(int tipo, long l) {
		super();
		this.setTipo(tipo);
		this.setEndereco(l);
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public long getEndereco() {
		return endereco;
	}

	public void setEndereco(long endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Endereco [tipo=" + tipo + ", endereco=" + endereco + "]";
	}
	
}
