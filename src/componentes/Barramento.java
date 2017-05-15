package componentes;

import java.util.concurrent.ConcurrentLinkedQueue;

import utils.Dado;
import utils.Endereco;
import utils.Sinal;

public class Barramento extends Thread {

	public ConcurrentLinkedQueue<Dado> dados = new ConcurrentLinkedQueue<>();
	public ConcurrentLinkedQueue<Endereco> enderecos = new ConcurrentLinkedQueue<>();
	public ConcurrentLinkedQueue<Sinal> sinais = new ConcurrentLinkedQueue<>();

	public boolean Enfileirar(Object conteudo) {
		if (conteudo instanceof Dado) {
			dados.add((Dado) conteudo);
			return true;
		} else if (conteudo instanceof Endereco) {
			enderecos.add((Endereco) conteudo);
			return true;
		} else if (conteudo instanceof Sinal) {
			sinais.add((Sinal) conteudo);
			return true;
		}
		return false;

	}

	private boolean enviarDado() {
		if (dados.size() > 0) {
			Dado d = dados.peek();
			// fazer algo
			dados.poll();
			return true;
		}
		return false;
	}

	private boolean enviarEndereco() {
		if (enderecos.size() > 0) {
			Endereco e = enderecos.peek();
			// fazer algo
			enderecos.poll();
			return true;
		}
		return false;
	}

	private boolean enviarSinal() {
		if (sinais.size() > 0) {
			Sinal s = sinais.peek();
			// fazer algo
			sinais.poll();
			return true;
		}
		return false;
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	@Override
	public void run() {

		System.out.println("***************************************");
		System.out.println("--------- Enviando um dado : " + enviarDado() + " ---------");
		System.out.println("--------- Enviando um endereço : " + enviarEndereco() + " ---------");
		System.out.println("--------- Enviando um sinal : " + enviarSinal() + " ---------");

		super.run();
	}

}
