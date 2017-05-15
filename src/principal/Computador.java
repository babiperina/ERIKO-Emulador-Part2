package principal;

import componentes.Barramento;
import componentes.CPU;
import componentes.Encoder;
import componentes.Parser;
import utils.Constantes;

public class Computador {

	private static Parser parser = new Parser();
	public static Encoder encoder = new Encoder();
	public static Barramento barramento = new Barramento();
	public static CPU cpu = new CPU();

	public static void main(String[] args) {

		System.out.println("Tamanho da palavra: " + Constantes.SIZE_word);
		parser.run();
		encoder.run();
		for (int i = 0; i < 10; i++) {
			barramento.run();
			cpu.run();
		}
	}

}
