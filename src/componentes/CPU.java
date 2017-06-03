package componentes;

import principal.Computador;

public class CPU extends Thread {
	
	private Barramento barramento;
	// Registradores
	// Cache
	
	
	public CPU() {
		setBarramento(Computador.barramento);
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	@Override
	public void run() {
//		enviarSinal();
		super.run();
	}
	
	public void buscar(){
		
	}
	
	public void decodificar(){
		
	}
	
	public void realizar(){
		
	}
	
	public Barramento getBarramento() {
		return barramento;
	}

	public void setBarramento(Barramento barramento) {
		this.barramento = barramento;
	}

}
