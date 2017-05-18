package principal;

import componentes.Barramento;
import componentes.CPU;
import componentes.ES;
import componentes.Encoder;
import componentes.Parser;
import componentes.Ram;
import utils.Constantes;

public class Computador {

	private static Parser parser = new Parser();
	public static Encoder encoder = new Encoder();
	public static Barramento barramento = new Barramento();
	public static CPU cpu = new CPU();
	public static Ram ram = new Ram();
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
			ram.run();
			if (encoder.todasInstrucoesNaES() && es.bufferVazio() && barramento.dados.isEmpty()) {
				ligado = false;
			}
		}
		System.out.println(barramento.dados.size());
		encoder.mostrarInstrucoesCodificadas(); // teste
		System.out.println(es.toString()); // teste

	}

}
