package componentes;

import java.util.Arrays;

import principal.Computador;
import utils.Constantes;
import utils.Dado;
import utils.Endereco;
import utils.InstrucaoCodificada;
import utils.Sinal;

public class ES extends Thread {

	private int bufferCIE = -1;
	byte[] buffer = new byte[Constantes.SIZE_e_s_buffer];
	Encoder encoder;
	boolean rodando = true;
	boolean prontoPraIrPraRAM = false;
	private int tamanhoInstrucoes;

	public ES() {
		encoder = Computador.encoder;
		inicializarBuffer();
		tamanhoInstrucoes = Constantes.QTDE_ESP_INST;
	}

	public boolean bufferVazio() {
		for (byte b : buffer) {
			if (b != -1)
				return false;
		}
		return true;
	}

	@Override
	public void run() {
		while (rodando) {
			try {
				Computador.tela.toNaES(true);
				sleep(500);
				puxarInstrucaoDoEncoder();
				Computador.tela.toNaES(false);
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();
	}

	void puxarInstrucaoDoEncoder() {
		Computador.tela.escreverNoConsole("$$$$ Instrução pronta pra ir pra RAM: " + prontoPraIrPraRAM
				+ "(ES.java:55)\n$$$$ CI de escrita do Buffer, posição: " + bufferCIE
				+ "(ES.java:56)\n$$$$ Tamanho do Buffer: " + buffer.length + " (ES.java:57)");
		if (!prontoPraIrPraRAM) {
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
				Computador.tela.escreverNoConsole("$$$$ Instrução colocada no Buffer.(ES.java:86)");
			} else if (bufferCIE == buffer.length || Computador.encoder.todasInstrucoesNaES()) {
				Computador.tela.escreverNoConsole(toString());
				Sinal sinal = new Sinal(Constantes.id_ES, Constantes.id_RAM, Constantes.id_SINAL_ESC);
				Dado dado;
				Endereco endereco = new Endereco(Constantes.id_END_VAZIO);
				if (Computador.encoder.todasInstrucoesNaES()) {
					dado = new Dado(Constantes.id_DADO_QTDE, buffer.length); // mudar
																				// aqui
				} else {
					dado = new Dado(Constantes.id_DADO_QTDE, buffer.length);
				}
				Computador.barramento.Enfileirar(sinal, dado, endereco);
				prontoPraIrPraRAM = true;
				Computador.tela.escreverNoConsole("$$$$ Instrução preparada para ir pra Ram. (ES.java:94)");
			} else {
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

						int cont = 0;
						for (int i = bufferCIE; i < bufferCIE + tamanhoInstrucoes; i++) {
							if (cont < b.length)
								buffer[i] = b[cont++];

						}
						if (bufferCIE == 0)
							bufferCIE = tamanhoInstrucoes;
						else
							bufferCIE += tamanhoInstrucoes;
					}
				}
				Computador.tela.escreverNoConsole("$$$$ Instrução colocada no Buffer. (ES.java:135)");
			}
//			Computador.tela.escreverNoConsole(toString());
		}

	}

	void inicializarBuffer() {
		buffer = new byte[buffer.length];
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
		return "Módulo Entrada e Saída\n  buffer= size: " + buffer.length + " " + Arrays.toString(buffer);
	}

	public boolean mandarInstrucaoPraRam(Endereco e) {
		if (prontoPraIrPraRAM) {
			Sinal sinal = new Sinal(Constantes.id_ES, Constantes.id_RAM, Constantes.id_SINAL_ESC);
			Endereco endereco = e;
			Dado dado = new Dado(Constantes.id_DADO_DADO, buffer);
			Computador.barramento.Enfileirar(sinal, dado, endereco);
			bufferCIE = -1;
			inicializarBuffer();
			prontoPraIrPraRAM = false;
			return true;
		} else {
			return false;
		}

	}

	public void parar() {
		rodando = false;
	}

	public void retornar() {
		rodando = true;
	}
}
