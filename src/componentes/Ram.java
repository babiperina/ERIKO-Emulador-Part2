package componentes;

import java.util.Random;

import principal.Computador;
import utils.Constantes;
import utils.Dado;
import utils.Endereco;
import utils.Sinal;

public class Ram extends Thread {

	Barramento barramento;

	public Ram() {
		barramento = Computador.barramento;
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	public void enviarRespostaES() {
		Random gerador = new Random();

		int numero = gerador.nextInt(10);
		if (numero % 2 == 0) {
			Sinal sinal = new Sinal(Constantes.id_RAM, Constantes.id_ES, Constantes.id_SINAL_OK);
			Dado dado = new Dado(Constantes.id_RAM, Constantes.id_ES, Constantes.id_DADO_VAZIO);
			Endereco endereco = new Endereco(Constantes.id_RAM, Constantes.id_ES, Constantes.id_END_MEM, 1);
			barramento.Enfileirar(sinal, dado, endereco);
		} else {
			Sinal sinal = new Sinal(Constantes.id_RAM, Constantes.id_ES, Constantes.id_SINAL_ERROR);
			Dado dado = new Dado(Constantes.id_RAM, Constantes.id_ES, Constantes.id_DADO_VAZIO);
			Endereco endereco = new Endereco(Constantes.id_RAM, Constantes.id_ES, Constantes.id_END_VAZIO);
			barramento.Enfileirar(sinal, dado, endereco);

		}
	}

}
