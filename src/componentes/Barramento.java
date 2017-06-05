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
	// quebrar em 3 estruturas de dados
	public ConcurrentLinkedQueue<Endereco> enderecos = new ConcurrentLinkedQueue<>();
	public ConcurrentLinkedQueue<Sinal> sinais = new ConcurrentLinkedQueue<>();

	public boolean Enfileirar(Sinal sinal, Dado dado, Endereco endereco) {
		if (sinal != null && dado != null && endereco != null) {
			sinais.add(sinal);
			dados.add(dado);
			enderecos.add(endereco);
			return true;
		}
		return false;
	}

	// private boolean enviarDado() {
	// if (dados.size() > 0) {
	// // Dado d = dados.peek();
	// // fazer algo
	// dados.poll();
	// return true;
	// }
	// return false;
	// }

	// private boolean enviarEndereco() {
	// if (enderecos.size() > 0) {
	// // Endereco e = enderecos.peek();
	// // fazer algo
	// enderecos.poll();
	// return true;
	// }
	// return false;
	// }

	private boolean enviar() {
		boolean remover = false;
		if (!dados.isEmpty()) {
			Sinal s = sinais.peek();
			Endereco e = enderecos.peek();
			Dado d = dados.peek();

			if (s.getDestinatario() == Constantes.id_RAM) {
				if (s.getRemetente() == Constantes.id_ES) {
					if (e.getTipo() == Constantes.id_END_VAZIO) {
						System.out.println("Barramento 1");
						remover = Computador.ram.verificarDisponibilidade();
					} else {
						System.out.println("Barramento 2");
						Computador.ram.colocarInstrucaoNaRam(e, d);
						remover = true;
					}
				}
			} else if (s.getDestinatario() == Constantes.id_ES) {
				if (s.getRemetente() == Constantes.id_RAM) {
					System.out.println("Barramento 3");
					remover = Computador.es.mandarInstrucaoPraRam(e);
				}
			}
		}

		if (remover) {
			sinais.poll();
			dados.poll();
			enderecos.poll();
			return true;
		} else
			return false;

	}

	@Override
	public void run() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(enviar());

	}

}
