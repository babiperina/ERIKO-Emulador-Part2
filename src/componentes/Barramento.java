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

	private boolean enviar(int i) {
		boolean remover = false;
		if (!dados.isEmpty() && i < dados.size()) {
			Sinal s = sinais.get(i);
			Endereco e = enderecos.get(i);
			Dado d = dados.get(i);

			if (s.getDestinatario() == Constantes.id_RAM) {
				if (s.getRemetente() == Constantes.id_ES) {
					if (e.getTipo() == Constantes.id_END_VAZIO) {
						remover = Computador.ram.verificarDisponibilidade();
					} else {
						remover = Computador.ram.colocarInstrucaoNaRam(e, d);

					}
				} else if (s.getRemetente() == Constantes.id_CPU) {
					if (s.getTipo() == Constantes.id_SINAL_LER) {
						remover = Computador.ram.mandarInstrucaoPraCpu(e, d);
					}
				}
			} else if (s.getDestinatario() == Constantes.id_ES) {
				if (s.getRemetente() == Constantes.id_RAM && s.getTipo() == Constantes.id_SINAL_OK) {
					remover = Computador.es.mandarInstrucaoPraRam(e);
				}
			} else if (s.getDestinatario() == Constantes.id_CPU) {
				if (s.getRemetente() == Constantes.id_RAM && s.getTipo() == Constantes.id_SINAL_ESC) {
					remover = Computador.cpu.decodificar(d.getConteudo());
				}
			}

			if (remover) {
				System.out.println("Antes: " + toString());
				sinais.remove(i);
				dados.remove(i);
				enderecos.remove(i);
				System.out.println("Depois: " + toString());
				return true;
			} else {
				return enviar(++i);
			}
		}
		return false;
	}

	@Override
	public void run() {

		while (rodando) {
			try {
				Computador.tela.toNoBarramento(true);
				sleep(500);
				enviar(0);
				Computador.tela.toNoBarramento(false);
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void parar() {
		rodando = false;
	}

	public int qtdeDado() {
		return dados.size();
	}

	public int qtdeEndereco() {
		return enderecos.size();
	}

	public int qtdeSinal() {
		return sinais.size();
	}

	@Override
	public String toString() {
		return "Barramento [\n\tdados=" + dados + ", \n\tenderecos=" + enderecos + ", \n\tsinais=" + sinais + "]";
	}

}
