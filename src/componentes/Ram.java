package componentes;

import java.util.Arrays;
import java.util.Random;

import principal.Computador;
import utils.Constantes;
import utils.Dado;
import utils.Endereco;
import utils.Sinal;

public class Ram extends Thread {

	private byte[] ram = new byte[Constantes.SIZE_ram * 1000];
	private int offset;
	private boolean rodando = true;

	public byte[] getRam() {
		return ram;
	}

	public void setRam(byte[] ram) {
		this.ram = ram;
	}

	public Ram() {
		offset = Constantes.SIZE_e_s_buffer;
		inicializarRam();
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	void inicializarRam() {
		Computador.cpu.setCI(-1);
		for (int i = 0; i < offset; i++) {
			ram[i] = -1;
		}
	}

	@Override
	public void run() {
		while (rodando) {
			try {
				Computador.tela.escreverNoConsole("RAM rodando");
				Computador.tela.toNaRam(true);
				sleep(500);

				Computador.tela.toNaRam(false);
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		super.run();
	}

	public boolean verificarDisponibilidade() {
		if (ramVazia()) {
			Sinal sinal = new Sinal(Constantes.id_RAM, Constantes.id_ES, Constantes.id_SINAL_OK);
			Endereco endereco = new Endereco(Constantes.id_END_MEM, 0);
			Dado dado = new Dado(Constantes.id_DADO_VAZIO);
			Computador.barramento.Enfileirar(sinal, dado, endereco);
			return true;
		}
		return false;
	}

	public boolean colocarInstrucaoNaRam(Endereco e, Dado d) {
		if (ram[(int) e.getEndereco()] == -1) {
			int cont = 0;
			for (int i = (int) e.getEndereco(); i < d.getConteudo().length; i++) {
				ram[i] = d.getConteudo()[cont++];
			}
			Computador.cpu.setCI(0);
			return true;
		}
		return false;
	}

	boolean ramVazia() {
		for (int i = 0; i < offset; i++) {
			if (ram[i] != -1)
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "[Ram=" + Arrays.toString(ram) + "]";
	}

	public void parar() {
		rodando = false;
	}

	public void retornar() {
		rodando = true;
	}

	public boolean mandarInstrucaoPraCpu(Endereco e, Dado d) {
		if (ram[(int) e.getEndereco()] != -1) {
			byte[] dados = new byte[d.getQtde()];
			int cont = 0;
			for (int i = (int) e.getEndereco(); i < e.getEndereco() + d.getQtde(); i++) {
				dados[cont++] = ram[i];
				ram[i] = -1;
			}

			Sinal sinal = new Sinal(Constantes.id_RAM, Constantes.id_CPU, Constantes.id_SINAL_ESC);
			Dado dado = new Dado(Constantes.id_DADO_DADO, dados);
			Endereco endereco = new Endereco(Constantes.id_END_VAZIO);
			Computador.tela.escreverNoConsole("$$$$ RAM <- Barramento -> CPU");
			Computador.barramento.Enfileirar(sinal, dado, endereco);

			if (e.getEndereco() + d.getQtde() == offset) {
				Computador.cpu.setCI(-1);
			} else {
				Computador.cpu.setCI((int) e.getEndereco() + d.getQtde());
			}
			return true;
		}
		return false;
	}

	public long puxarValor(long endereco, int tam) {
		byte[] aux = new byte[tam];
		int cont = 0;
		for (int i = (int) endereco + offset; i < endereco + offset + tam; i++) {
			aux[cont++] = ram[i];
		}
		long compressed = aux[0];
		for (int i = 1; i < aux.length; i++) {
			compressed ^= (aux[i] << 8 * i);
		}
		return compressed;
	}

	public void colocarValor(long endereco, byte[] valores) {
		int cont = 0;
		System.out.println("TO NA RAM: ENDERECO: " + (endereco + offset) + " VALORES: " + Arrays.toString(valores));
		for (int i = (int) endereco + offset - 1; i < endereco + offset + valores.length - 1; i++) {
			ram[i] = valores[cont++];
		}
	}
}
