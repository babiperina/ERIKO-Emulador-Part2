package componentes;

import java.util.Arrays;

import principal.Computador;
import utils.Constantes;
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
		puxarInstrucaoDoEncoder();
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
			System.out.println("RAGNAROK!!!!!!!!!!!!!!!!!!");
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
						buffer[i] = b[i - bufferCIE];
					}
					bufferCIE = 0;
				}
			}
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
