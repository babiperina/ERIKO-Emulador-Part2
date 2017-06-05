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
	Barramento barramento;
	Encoder encoder;
	boolean rodando = true;
	boolean prontoPraIrPraRAM = false;
	private int tamanhoInstrucoes;

	public ES() {
		barramento = Computador.barramento;
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
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("CI Escrita: " + bufferCIE); // teste
			// System.out.println("Tamanho buffer: " + buffer.length); // teste
			puxarInstrucaoDoEncoder();
			// System.out.println(toString()); // buffer print teste
		}
		super.run();
	}

	void puxarInstrucaoDoEncoder() {
		System.out.println(
				!prontoPraIrPraRAM + "  " + bufferCIE + "  " + buffer.length + "  " + (bufferCIE == buffer.length));
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
			} else if (bufferCIE == buffer.length) {
				System.out.println("ES Buffer tá cheio de instrução.");
				System.out.println(toString());
				int qtdeDados = Constantes.QTDE_INST_BUFFER * (Constantes.TAM_MAX_INST * 2);
				Sinal sinal = new Sinal(Constantes.id_ES, Constantes.id_RAM, Constantes.id_SINAL_ESC);
				Dado dado = new Dado(Constantes.id_DADO_QTDE, qtdeDados);
				Endereco endereco = new Endereco(Constantes.id_END_VAZIO);
				System.out.println(barramento.Enfileirar(sinal, dado, endereco));
				prontoPraIrPraRAM = true;
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
			}
		}
	}

	void mandarInstPraRam() {
		// byte b[] = new byte[tamanhoInstrucoes];
		// int contAux = 1;
		// for (int i = 1; i <= Constantes.QTDE_INST_BUFFER; i++) {
		// b = new byte[tamanhoInstrucoes];
		// if (bufferCIE == buffer.length) {
		// bufferCIE = 0;
		// }
		// int cont = 0;
		// boolean tem = false;
		// for (int j = bufferCIE; j < bufferCIE + tamanhoInstrucoes; j++) {
		// if (j == bufferCIE) {
		// if (buffer[j] != -1 && buffer[j + 1] != -1) {
		// tem = true;
		// }
		// }
		// b[cont++] = buffer[j];
		// buffer[j] = -1;
		// }
		//
		// if (b[0] != -1 || b[1] != -1) {
		// // System.out.println("$$$$ Mandando dado " + contAux++ + " pro
		// // Barramento.");
		// // Sinal sinal = new Sinal(Constantes.id_ES, Constantes.id_RAM,
		// // Constantes.id_SINAL_ESC);
		// // Dado dado = new Dado(Constantes.id_ES, Constantes.id_RAM,
		// // Constantes.id_DADO_DADO, b);
		// // Endereco endereco = new Endereco(Constantes.id_ES,
		// // Constantes.id_RAM, Constantes.id_END_MEM, 1);
		// // barramento.Enfileirar(sinal, dado, endereco);
		// }
		//
		// bufferCIE += tamanhoInstrucoes;
		// }
		// bufferCIE = 0;

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
		return "Módulo Entrada e Saída\n  buffer= " + Arrays.toString(buffer);
	}

	public boolean mandarInstrucaoPraRam(Endereco e) {
		if (prontoPraIrPraRAM) {
			Sinal sinal = new Sinal(Constantes.id_ES, Constantes.id_RAM, Constantes.id_SINAL_ESC);
			Endereco endereco = e;
			Dado dado = new Dado(Constantes.id_DADO_DADO, buffer);
			prontoPraIrPraRAM = false;
			Computador.barramento.Enfileirar(sinal, dado, endereco);
			bufferCIE = -1;
			inicializarBuffer();
			return true;
		} else {
			return false;
		}

	}
	
	public void parar(){
		rodando = false;
	}

}
