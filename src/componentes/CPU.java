package componentes;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

import principal.Computador;
import utils.Constantes;
import utils.Dado;
import utils.Endereco;
import utils.Sinal;

public class CPU extends Thread {

	private Registrador A = new Registrador(Constantes.VALUE_register_A, "A", 0);
	private Registrador B = new Registrador(Constantes.VALUE_register_B, "B", 0);
	private Registrador C = new Registrador(Constantes.VALUE_register_C, "C", 0);
	private Registrador D = new Registrador(Constantes.VALUE_register_D, "D", 0);
	private Registrador CI = new Registrador(Constantes.VALUE_register_CI, "CI", -1);
	private Cache cache;
	private byte[] instrucaoEmByte = new byte[1];
	private boolean rodando = true;
	private boolean pediu = false;
	int contadorInstrucoesExecutadas = 0;
	// Registradores
	// Cache

	public CPU() {
	}

	@Override
	public void run() {
		while (rodando) {
			try {
				Computador.tela.escreverNoConsole("CPU Rodando");
				Computador.tela.toNaCpu(true);
				sleep(500);
				buscar();
				if (contadorInstrucoesExecutadas == Computador.encoder.instrucoesCodificadas.size()) {
					Computador.parar();
				}
				Computador.tela.toNaCpu(false);
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();
	}

	public void buscar() {
		if (CI.getConteudo() != -1 && !pediu) {
			Sinal sinal = new Sinal(Constantes.id_CPU, Constantes.id_RAM, Constantes.id_SINAL_LER);
			Dado dado = new Dado(Constantes.id_DADO_QTDE, Constantes.QTDE_ESP_INST);
			Endereco endereco = new Endereco(Constantes.id_END_MEM, CI.getConteudo());
			Computador.tela.escreverNoConsole("$$$$ CPU <- Barramento -> RAM");
			Computador.barramento.Enfileirar(sinal, dado, endereco);
			pediu = true;
		}
	}

	public boolean decodificar(byte[] instrucao) {
		if (instrucaoEmByte.length == 1) {
			Computador.tela.escreverNoConsole("$$$$ Instrução entrou na Cpu");
			instrucaoEmByte = instrucao;
			decoder(instrucao);
			pediu = false;
			return true;
		}
		return false;
	}

	public void decoder(byte[] dados) {
		ByteBuffer bb;
		bb = ByteBuffer.wrap(dados);
		boolean ok = false;

		switch (Constantes.SIZE_word) {
		case 16:
			ShortBuffer sb = bb.asShortBuffer();
			short[] shortArray = new short[dados.length / 2];
			sb.get(shortArray);
			executeShort(shortArray);
			ok = true;
			break;

		case 32:
			IntBuffer ib = bb.asIntBuffer();
			int[] intArray = new int[dados.length / 4];
			ib.get(intArray);
			ok = true;
			executeInt(intArray);
			break;

		case 64:
			LongBuffer lb = bb.asLongBuffer();
			long[] longArray = new long[dados.length / 8];
			lb.get(longArray);
			ok = true;
			executeLong(longArray);
			break;
		}
	}

	public void executeShort(short[] instrucao) {
		contadorInstrucoesExecutadas++;
		System.out.println("EXECUTE SHORT");
		long[] aux = Computador.encoder.instrucoesCodificadas.get(contadorInstrucoesExecutadas).getInstLong();

		long type = (long) aux[0];
		if (type == Constantes.VALUE_mov_m_from_i || type == Constantes.VALUE_mov_m_from_m
				|| type == Constantes.VALUE_mov_m_from_r || type == Constantes.VALUE_mov_r_from_i
				|| type == Constantes.VALUE_mov_r_from_m || type == Constantes.VALUE_mov_r_from_r) {
			executeMov(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_add_m_from_i || type == Constantes.VALUE_add_m_from_m
				|| type == Constantes.VALUE_add_m_from_r || type == Constantes.VALUE_add_r_from_i
				|| type == Constantes.VALUE_add_r_from_m || type == Constantes.VALUE_add_r_from_r) {
			executeAdd(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_inc_m || type == Constantes.VALUE_inc_r) {
			executeInc(type, instrucao[1]);
		} else {
			executeImul(type, instrucao[1], instrucao[2], instrucao[3]);
		}

		instrucaoEmByte = new byte[1];
	}

	public void executeInt(int[] instrucao) {
		System.out.println("EXECUTE INT");
		long[] aux = Computador.encoder.instrucoesCodificadas.get(contadorInstrucoesExecutadas).getInstLong();

		long type = (long) aux[0];
		if (type == Constantes.VALUE_mov_m_from_i || type == Constantes.VALUE_mov_m_from_m
				|| type == Constantes.VALUE_mov_m_from_r || type == Constantes.VALUE_mov_r_from_i
				|| type == Constantes.VALUE_mov_r_from_m || type == Constantes.VALUE_mov_r_from_r) {
			executeMov(type, aux[1], aux[2]);
		} else if (type == Constantes.VALUE_add_m_from_i || type == Constantes.VALUE_add_m_from_m
				|| type == Constantes.VALUE_add_m_from_r || type == Constantes.VALUE_add_r_from_i
				|| type == Constantes.VALUE_add_r_from_m || type == Constantes.VALUE_add_r_from_r) {
			executeAdd(type, aux[1], aux[2]);
		} else if (type == Constantes.VALUE_inc_m || type == Constantes.VALUE_inc_r) {
			executeInc(type, aux[1]);
		} else {
			executeImul(type, aux[1], aux[2], aux[3]);
		}

		instrucaoEmByte = new byte[1];
		contadorInstrucoesExecutadas++;
	}

	public void executeLong(long[] instrucao) {
		contadorInstrucoesExecutadas++;
		long[] aux = Computador.encoder.instrucoesCodificadas.get(contadorInstrucoesExecutadas).getInstLong();

		long type = (long) aux[0];
		if (type == Constantes.VALUE_mov_m_from_i || type == Constantes.VALUE_mov_m_from_m
				|| type == Constantes.VALUE_mov_m_from_r || type == Constantes.VALUE_mov_r_from_i
				|| type == Constantes.VALUE_mov_r_from_m || type == Constantes.VALUE_mov_r_from_r) {
			executeMov(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_add_m_from_i || type == Constantes.VALUE_add_m_from_m
				|| type == Constantes.VALUE_add_m_from_r || type == Constantes.VALUE_add_r_from_i
				|| type == Constantes.VALUE_add_r_from_m || type == Constantes.VALUE_add_r_from_r) {
			executeAdd(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_inc_m || type == Constantes.VALUE_inc_r) {
			executeInc(type, instrucao[1]);
		} else {
			executeImul(type, instrucao[1], instrucao[2], instrucao[3]);
		}

		instrucaoEmByte = new byte[1];
	}

	public void executeMov(long type, long x, long y) {
		long a = x;
		long b = y;
		if (type == Constantes.VALUE_mov_m_from_i) {
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_mov_m_from_m) {
			b = pegarValorDaMemoria(a);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_mov_m_from_r) {
			b = pegarValorDoRegistrador(b);
			insertValorMemoria(a, b);
			System.out.println("OOOOOOOOOOOOI");
		} else if (type == Constantes.VALUE_mov_r_from_r) {
			b = pegarValorDoRegistrador(b);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_mov_r_from_m) {
			b = pegarValorDaMemoria(b);
			insertValorRegistrador(a, b);
		} else {
			// Constantes.VALUE_mov_r_from_i
			insertValorRegistrador(a, b);
		}

	}

	public void executeAdd(long type, long x, long y) {
		long a = x;
		long b = y;
		if (type == Constantes.VALUE_add_m_from_i) {
			b = pegarValorDaMemoria(a) + b;
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_add_m_from_m) {
			b = pegarValorDaMemoria(a) + pegarValorDaMemoria(b);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_add_m_from_r) {
			b = pegarValorDaMemoria(a) + pegarValorDoRegistrador(b);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_add_r_from_r) {
			b = pegarValorDoRegistrador(a) + pegarValorDoRegistrador(b);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_add_r_from_m) {
			b = pegarValorDoRegistrador(a) + pegarValorDaMemoria(b);
			insertValorRegistrador(a, b);
		} else {
			// Constantes.VALUE_add_r_from_i
			b = pegarValorDoRegistrador(a) + b;
			insertValorRegistrador(a, b);
		}
	}

	public void executeInc(long type, long x) {
		long a = x;
		long b;
		if (type == Constantes.VALUE_inc_m) {
			b = pegarValorDaMemoria(a) + 1;
			insertValorMemoria(a, b);
		} else {
			// Constantes.VALUE_inc_r
			System.out.println("VALORRRRRRRRRRRRRRR " + a);
			b = pegarValorDoRegistrador(a) + 1;
			insertValorRegistrador(a, b);
		}
	}

	public void executeImul(long type, long x, long y, long z) {
		long a = x;
		long b = y;
		long c = z;

		if (type == Constantes.VALUE_imul_r_r_r) {
			b = pegarValorDoRegistrador(b) * pegarValorDoRegistrador(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_r_m) {
			b = pegarValorDoRegistrador(b) * pegarValorDaMemoria(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_r_i) {
			b = pegarValorDoRegistrador(b) + c;
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_m_r) {
			b = pegarValorDaMemoria(b) * pegarValorDoRegistrador(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_m_m) {
			b = pegarValorDaMemoria(b) * pegarValorDaMemoria(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_m_i) {
			b = pegarValorDaMemoria(b) * c;
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_i_r) {
			b = b * pegarValorDoRegistrador(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_i_m) {
			b = b * pegarValorDaMemoria(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_i_i) {
			b = b * c;
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_m_r_r) {
			b = pegarValorDoRegistrador(b) * pegarValorDoRegistrador(c);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_r_m) {
			b = pegarValorDoRegistrador(b) * pegarValorDaMemoria(c);
			System.out.println("EOQQQQQQQQQQQQQ" + b);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_r_i) {
			b = pegarValorDoRegistrador(b) * c;
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_m_r) {
			b = pegarValorDaMemoria(b) * pegarValorDoRegistrador(c);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_m_m) {
			b = pegarValorDaMemoria(b) * pegarValorDaMemoria(c);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_m_i) {
			b = pegarValorDaMemoria(b) * c;
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_i_r) {
			b = b * pegarValorDoRegistrador(c);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_i_m) {
			b = b * pegarValorDaMemoria(c);
			insertValorMemoria(a, b);
		} else {
			// Constantes.VALUE_imul_m_i_i;
			b = b * c;
			insertValorMemoria(a, b);
		}
	}

	public void insertValorMemoria(long endereco, long valor) {
		System.out.println("ENDERECO: " + endereco + "VALOR: " + valor);
		byte[] valores = new byte[Constantes.QTDE_ESP_INST];
		for (int i = 0; i < valores.length; i++) {
			valores[i] = (byte) ((valor >> 8 * i) & 0xFFFF);
		}
		System.out.println("ENDERECO: " + endereco + "VALORES: " + valor);
		Computador.ram.colocarValor(endereco, valores);
	}

	public long pegarValorDaMemoria(long endereco) {
		return Computador.ram.puxarValor(endereco, Constantes.TAM_MAX_INST);
	}

	public long pegarValorDoRegistrador(long registrador) {
		if (registrador == A.getId()) {
			return A.getConteudo();
		} else if (registrador == B.getId()) {
			return B.getConteudo();
		} else if (registrador == C.getId()) {
			return C.getConteudo();
		} else {
			return D.getConteudo();
		}
	}

	public void insertValorRegistrador(long registrador, long valor) {
		if (registrador == A.getId()) {
			A.setConteudo(valor);
		} else if (registrador == B.getId()) {
			B.setConteudo(valor);
		} else if (registrador == C.getId()) {
			C.setConteudo(valor);
		} else {
			D.setConteudo(valor);
		}
	}

	public Registrador getA() {
		return A;
	}

	public void setA(Registrador a) {
		A = a;
	}

	public Registrador getB() {
		return B;
	}

	public void setB(Registrador b) {
		B = b;
	}

	public Registrador getC() {
		return C;
	}

	public void setC(Registrador c) {
		C = c;
	}

	public Registrador getD() {
		return D;
	}

	public void setD(Registrador d) {
		D = d;
	}

	public long getCI() {
		return CI.getConteudo();
	}

	public void setCI(int cI) {
		CI.setConteudo(cI);
	}

	public void parar() {
		rodando = false;

	}

	public void retornar() {
		rodando = true;
	}

	@Override
	public String toString() {
		return "CPU [\nA=" + A + ", \nB=" + B + ", \nC=" + C + ", \nD=" + D + ", \nCI=" + CI + ", \ncache=" + cache
				+ ", \ninstrucaoEmByte=" + Arrays.toString(instrucaoEmByte) + ", \nrodando=" + rodando + ", \npediu="
				+ pediu + ", \ncontadorInstrucoesExecutadas=" + contadorInstrucoesExecutadas + "]";
	}

}
