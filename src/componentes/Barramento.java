package componentes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import principal.Computador;
import utils.Constantes;
import utils.Dado;
import utils.Endereco;
import utils.Sinal;

public class Barramento extends Thread {

	private List<Dado> dados = new ArrayList<>();
	private List<Endereco> enderecos = new ArrayList<>();
	private List<Sinal> sinais = new ArrayList<>();
	private boolean rodando = true;

	public boolean Enfileirar(Sinal sinal, Dado dado, Endereco endereco) {
		if (sinal != null && dado != null && endereco != null) {
			sinais.add(sinal);
			dados.add(dado);
			enderecos.add(endereco);
			return true;
		}
		return false;
	}

	private boolean enviar() {
		boolean remover = false;
		if (!dados.isEmpty()) {
			Sinal s = sinais.get(0);
			Endereco e = enderecos.get(0);
			Dado d = dados.get(0);

			if (s.getDestinatario() == Constantes.id_RAM) {
				if (s.getRemetente() == Constantes.id_ES) {
					if (e.getTipo() == Constantes.id_END_VAZIO) {
						remover = Computador.ram.verificarDisponibilidade();
					} else {
						System.out.println(Arrays.toString(d.getConteudo()));
						remover = Computador.ram.colocarInstrucaoNaRam(e, d);

					}
				}
			} else if (s.getDestinatario() == Constantes.id_ES) {
				if (s.getRemetente() == Constantes.id_RAM && s.getTipo() == Constantes.id_SINAL_OK) {
					remover = Computador.es.mandarInstrucaoPraRam(e);
				}
			}
			System.out.println(sinais.get(0).toString());
			System.out.println(enderecos.get(0).toString());
		}
		if (remover) {
			sinais.remove(0);
			dados.remove(0);
			enderecos.remove(0);
			return true;
		} else
			return false;

	}

	@Override
	public void run() {

		while (rodando) {
			try {
				Computador.tela.toNoBarramento(true);
				sleep(1000);
				Computador.tela.escreverNoConsole(enviar() + "");
				Computador.tela.toNoBarramento(false);
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void parar() {
		rodando = false;
	}
}
