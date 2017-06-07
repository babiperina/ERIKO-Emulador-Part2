package componentes;

public class Registrador {

	private int id;

	private final String nome;

	private Long conteudo;

	public Registrador(int id, String nome, long i) {
		this.id = id;
		this.nome = nome;
		this.conteudo = i;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public boolean isEmpty(){
		return conteudo == null;
	}
	
	public long getConteudo() {
		return conteudo;
	}

	public void setConteudo(long conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public String toString() {
		return "Registrador [id=" + id + ", nome=" + nome + ", conteudo=" + conteudo + "]";
	}
	
	

}
