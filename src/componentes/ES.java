package componentes;

import java.util.Arrays;

import principal.Computador;
import utils.Constantes;
import utils.Dado;
import utils.InstrucaoCodificada;

public class ES extends Thread {

	private int bufferCIE = -1;
	byte[] buffer = new byte[Constantes.SIZE_e_s_buffer];
	Barramento barramento;
	Encoder encoder;
	private int tamanhoInstrucoes;

	public ES() {
		barramento = Computador.barramento;
		encoder = Computador.encoder;
		inicializarBuffer();
		tamanhoInstrucoes = Constantes.QTDE_ESP_INST;
	}

	@Override
	public void run() {
		System.out.println("CI Escrita: " + bufferCIE); // teste
		System.out.println("Tamanho buffer: " + buffer.length); // teste
		puxarInstrucaoDoEncoder();
		System.out.println(toString()); // buffer print teste
		super.run();
	}

	void puxarInstrucaoDoEncoder() {
		if (bufferCIE == -1) {
			InstrucaoCodificada instructionIn = encoder.mandarInstrucoesParaModuloES();
			if (instructionIn != null) {
				byte[] b;
				if (instructionIn.getInstrucaoCodificada()[0] instanceof Long) {
					long[] l = new long[instructionIn.getInstrucaoCodificada().length];
					for (int i = 0; i < l.length; i++) {
						l[i] = (long) instructionIn.getInstrucaoCodificada()[i];
					}
					b = divideBytes(l);
				} else if (instructionIn.getInstrucaoCodificada()[0] instanceof Integer) {
					int[] inti = new int[instructionIn.getInstrucaoCodificada().length];
					for (int i = 0; i < inti.length; i++) {
						inti[i] = (int) instructionIn.getInstrucaoCodificada()[i];
					}
					b = divideBytes(inti);
				} else {
					short[] s = new short[instructionIn.getInstrucaoCodificada().length];
					for (int i = 0; i < s.length; i++) {
						s[i] = (short) instructionIn.getInstrucaoCodificada()[i];
					}
					b = divideBytes(s);
				}

				for (int i = 0; i < b.length; i++) {
					buffer[i] = b[i];
				}
				bufferCIE = tamanhoInstrucoes;
			}
		} else if (bufferCIE == 0) {
			if (buffer[0] == -1) {
				InstrucaoCodificada instructionIn = encoder.mandarInstrucoesParaModuloES();
				if (instructionIn != null) {
					byte[] b;
					if (instructionIn.getInstrucaoCodificada()[0] instanceof Long) {
						long[] l = new long[instructionIn.getInstrucaoCodificada().length];
						for (int i = 0; i < l.length; i++) {
							l[i] = (long) instructionIn.getInstrucaoCodificada()[i];
						}
						b = divideBytes(l);
					} else if (instructionIn.getInstrucaoCodificada()[0] instanceof Integer) {
						int[] inti = new int[instructionIn.getInstrucaoCodificada().length];
						for (int i = 0; i < inti.length; i++) {
							inti[i] = (int) instructionIn.getInstrucaoCodificada()[i];
						}
						b = divideBytes(inti);
					} else {
						short[] s = new short[instructionIn.getInstrucaoCodificada().length];
						for (int i = 0; i < s.length; i++) {
							s[i] = (short) instructionIn.getInstrucaoCodificada()[i];
						}
						b = divideBytes(s);
					}

					for (int i = 0; i < tamanhoInstrucoes; i++) {
						if (i < b.length)
							buffer[i] = b[i];

					}
					bufferCIE = tamanhoInstrucoes;
				}
			}
		} else if (bufferCIE == tamanhoInstrucoes) {
			if (buffer[bufferCIE] == -1) {
				InstrucaoCodificada instructionIn = encoder.mandarInstrucoesParaModuloES();
				if (instructionIn != null) {
					byte[] b;
					if (instructionIn.getInstrucaoCodificada()[0] instanceof Long) {
						long[] l = new long[instructionIn.getInstrucaoCodificada().length];
						for (int i = 0; i < l.length; i++) {
							l[i] = (long) instructionIn.getInstrucaoCodificada()[i];
						}
						b = divideBytes(l);
					} else if (instructionIn.getInstrucaoCodificada()[0] instanceof Integer) {
						int[] inti = new int[instructionIn.getInstrucaoCodificada().length];
						for (int i = 0; i < inti.length; i++) {
							inti[i] = (int) instructionIn.getInstrucaoCodificada()[i];
						}
						b = divideBytes(inti);
					} else {
						short[] s = new short[instructionIn.getInstrucaoCodificada().length];
						for (int i = 0; i < s.length; i++) {
							s[i] = (short) instructionIn.getInstrucaoCodificada()[i];
						}
						b = divideBytes(s);
					}

					for (int i = bufferCIE; i < buffer.length; i++) {
						if(i-bufferCIE < b.length)
						buffer[i] = b[i - bufferCIE];
					}
					bufferCIE = buffer.length;

				}
			}
		} else if (bufferCIE == buffer.length) {
			mandarInstPraRam();
		}
	}

	void mandarInstPraRam() {
		byte instUm[] = new byte[tamanhoInstrucoes];
		byte instDois[] = new byte[tamanhoInstrucoes];
		boolean um = false, dois = false;

		if (buffer[0] != -1) {
			um = true;
			System.out.println("Mandar inst 1 pra Ram");
			for (int i = 0; i < tamanhoInstrucoes; i++) {
				instUm[i] = buffer[i];
				buffer[i] = -1;
			}
			bufferCIE = tamanhoInstrucoes;
		}
		if (buffer[tamanhoInstrucoes] != -1) {
			dois = true;
			System.out.println("Mandar inst 2 pra Ram");
			for (int i = tamanhoInstrucoes; i < buffer.length; i++) {
				instDois[i - tamanhoInstrucoes] = buffer[i];
				buffer[i] = -1;
			}
			bufferCIE = 0;
		}

		Dado dado;
		if (um) {
			dado = new Dado(Constantes.id_ES, Constantes.id_RAM, instUm);
			barramento.Enfileirar(dado);
		}
		if (dois) {
			dado = new Dado(Constantes.id_ES, Constantes.id_RAM, instDois);
			barramento.Enfileirar(dado);
		}

	}

	void inicializarBuffer() {
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = -1;
		}
	}

	public static byte[] divideBytes(long[] arr) {
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(arr.length * 8);
		bb.asLongBuffer().put(arr);
		return bb.array(); // this returns the "raw" array, it's shared and not
							// copied!
	}

	public static byte[] divideBytes(int[] arr) {
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(arr.length * 4);
		bb.asIntBuffer().put(arr);
		return bb.array(); // this returns the "raw" array, it's shared and not
							// copied!
	}

	public static byte[] divideBytes(short[] arr) {
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(arr.length * 2);
		bb.asShortBuffer().put(arr);
		return bb.array(); // this returns the "raw" array, it's shared and not
							// copied!
	}

	@Override
	public String toString() {
		return "ES [buffer=" + Arrays.toString(buffer) + "]";
	}

}
