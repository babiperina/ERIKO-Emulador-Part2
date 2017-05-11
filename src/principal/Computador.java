package principal;

import componentes.Encoder;
import componentes.Parser;

public class Computador {

	private static Parser parser = new Parser();
	public static Encoder encoder = new Encoder();

	public static void main(String[] args) {

		parser.run();
		encoder.run();
		
	}

}
