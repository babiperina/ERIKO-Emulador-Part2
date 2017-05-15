package componentes;

import principal.Computador;
import utils.Constantes;
import utils.Sinal;

public class CPU extends Thread {
	
	private Barramento barramento;
	
	public CPU() {
		barramento = Computador.barramento;
	}

	public void enviarSinal() {
		int remetente = Constantes.id_CPU;
		int destinatario = Constantes.id_RAM;
		int tipo = Constantes.id_SINAL_LER;

		Sinal sinal = new Sinal(remetente, destinatario, tipo);
		Computador.barramento.Enfileirar(sinal);
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	@Override
	public void run() {
		enviarSinal();
		super.run();
	}

}
