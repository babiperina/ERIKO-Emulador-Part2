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
		if (sinais.size() > 0) {
			Sinal s = sinais.peek();
			Dado d = dados.peek();
			Endereco e = enderecos.peek();
			if (s.getRemetente() == Constantes.id_ES && s.getDestinatario() == Constantes.id_RAM) {
				if (s.getTipo() == Constantes.id_SINAL_ESC) {
					if (e.getTipo() == Constantes.id_END_VAZIO) {
						Computador.ram.enviarRespostaES();
					} else {

					}
				}
			} else if (s.getRemetente() == Constantes.id_RAM && s.getDestinatario() == Constantes.id_ES) {
				if (s.getTipo() == Constantes.id_SINAL_OK) {
					Computador.es.mandarInstPraRam();
				} else if (s.getTipo() == Constantes.id_SINAL_ERROR) {

				}
			}
			sinais.poll();
			dados.poll();
			enderecos.peek();
			return true;
		}
		return false;
	}

	@Override
	public void run() {

		if (dados.peek() != null) {
			if (dados.peek().getTipo() == -1 && enderecos.peek().getTipo() == -1 && sinais.peek().getTipo() == -1) {
				// enviarDado();
				// enviarEndereco();
				enviar();
			} else {
				// try {
				// Thread.sleep(1000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				System.out.println("***************************************");
				if (dados.size() > 0) {
					if (dados.peek().getTipo() == -1) {
						System.out.println("R: " + dados.peek().getRemetente() + " D: " + dados.peek().getDestinatario()
								+ " Tipo: VAZIO");
					} else {
						System.out.println("R: " + dados.peek().getRemetente() + " D: " + dados.peek().getDestinatario()
								+ " Tipo: " + dados.peek().getTipo());
					}
				}
				System.out.println(dados.size() + "--------- Enviando um dado  ---------");

				if (enderecos.size() > 0) {
					if (enderecos.peek().getTipo() == -1) {
						System.out.println("R: " + enderecos.peek().getRemetente() + " D: "
								+ enderecos.peek().getDestinatario() + " Tipo: VAZIO");
					} else {
						System.out.println("R: " + enderecos.peek().getRemetente() + " D: "
								+ enderecos.peek().getDestinatario() + " Tipo: " + enderecos.peek().getTipo());
					}
				}
				System.out.println(enderecos.size() + "--------- Enviando um endereço  ---------");

				if (sinais.size() > 0) {
					if (sinais.peek().getTipo() == -1) {
						System.out.println("R: " + sinais.peek().getRemetente() + " D: "
								+ sinais.peek().getDestinatario() + " Tipo: VAZIO");
					} else {
						System.out.println("R: " + sinais.peek().getRemetente() + " D: "
								+ sinais.peek().getDestinatario() + " Tipo: " + sinais.peek().getTipo());
					}
				}
				System.out.println(sinais.size() + "--------- Enviando um sinal  ---------");
				enviar();
			}
			super.run();
		}
	}

}
