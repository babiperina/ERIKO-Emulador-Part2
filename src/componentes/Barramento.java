package componentes;

import java.awt.Container;
import java.util.concurrent.ConcurrentLinkedQueue;

import principal.Computador;
import utils.Constantes;
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
			// Dado d = dados.peek();
			// fazer algo
			dados.poll();
			return true;
		}
		return false;
	}

	private boolean enviarEndereco() {
		if (enderecos.size() > 0) {
			// Endereco e = enderecos.peek();
			// fazer algo
			enderecos.poll();
			return true;
		}
		return false;
	}

	private boolean enviarSinal() {
		if (sinais.size() > 0) {
			 Sinal s = sinais.peek();
			 if(s.getRemetente() == Constantes.id_ES && s.getDestinatario() == Constantes.id_RAM){
				if(s.getTipo() == Constantes.id_SINAL_ESC){
					System.out.println(s);
					Computador.ram.enviarRespostaES();
				}
			 } else if(s.getRemetente() == Constantes.id_RAM && s.getDestinatario() == Constantes.id_ES){
				 if(s.getTipo() == Constantes.id_SINAL_OK){
					 Computador.es.mandarInstPraRam();
				 }
			 }
			 sinais.poll();
			return true;
		}
		return false;
	}

	@Override
	public void run() {

//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("***************************************");
		System.out.println(dados.size() + "--------- Enviando um dado : " + enviarDado() + " ---------");
		System.out.println(enderecos.size() + "--------- Enviando um endereço : " + enviarEndereco() + " ---------");
		System.out.println(sinais.size() + "--------- Enviando um sinal : " + enviarSinal() + " ---------");

		super.run();
	}

}
