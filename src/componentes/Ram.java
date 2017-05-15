package componentes;

import principal.Computador;

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
	
}
