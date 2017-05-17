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
	public static boolean ligado = true;

	public static void main(String[] args) {

		System.out.println("Tamanho da palavra: " + Constantes.SIZE_word);
		parser.run();
		encoder.run();
		System.out.println(es.toString());
		while (ligado) {
			barramento.run();
			es.run();
			cpu.run();
			if (encoder.todasInstrucoesNaES() && es.bufferVazio()) {
				ligado = false;
			}
		}
		encoder.mostrarInstrucoesCodificadas(); // teste
		System.out.println(es.toString()); // teste

	}

}
