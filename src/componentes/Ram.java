package componentes;

import principal.Computador;
import utils.Constantes;
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
		Sinal sinal = new Sinal(Constantes.id_RAM, Constantes.id_ES, Constantes.id_SINAL_OK);
		barramento.Enfileirar(sinal);
	}
	
}
