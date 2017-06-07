package principal;

import componentes.Barramento;
import componentes.CPU;
import componentes.ES;
import componentes.Encoder;
import componentes.Parser;
import componentes.Ram;

public class Computador extends Thread {

	private static Parser parser = new Parser();
	public static Encoder encoder = new Encoder();
	public static Barramento barramento = new Barramento();
	public static CPU cpu = new CPU();
	public static Ram ram = new Ram();
	public static ES es = new ES();
	public static Tela tela = new Tela();

	public static void parar() {
		barramento.parar();
		es.parar();
		cpu.parar();
		ram.parar();
		encoder.mostrarInstrucoesCodificadas();
	}
	
	public static void init() {
		parser.run();
		encoder.run();
		barramento.start();
		es.start();
		cpu.start();
		ram.start();
	}
}
