package principal;

import componentes.Barramento;
import componentes.CPU;
import componentes.ES;
import componentes.Encoder;
import componentes.Parser;
import utils.Constantes;

public class Computador {

	private static Parser parser = new Parser();
	public static Encoder encoder = new Encoder();
	public static Barramento barramento = new Barramento();
	public static CPU cpu = new CPU();
	public static ES es = new ES();

	public static void main(String[] args) {

		System.out.println("Tamanho da palavra: " + Constantes.SIZE_word);
		parser.run();
		encoder.run();
		System.out.println(es.toString());
		for (int i = 0; i < 10; i++) {
			barramento.run();
			es.run();
			cpu.run();
		}
		encoder.mostrarInstrucoesCodificadas(); //teste
		System.out.println(es.toString()); //teste

	}

}
