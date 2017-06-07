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
		offset = Constantes.QTDE_ESP_INST * Constantes.QTDE_INST_BUFFER;
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
		for (int i = 0; i < ram.length; i++) {
			ram[i] = -1;
		}
	}

	@Override
	public void run() {
		while (rodando) {
			try {
				Computador.tela.escreverNoConsole("RAM rodando");
				Computador.tela.toNaRam(true);
				sleep(1000);

				Computador.tela.toNaRam(false);
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		super.run();
	}

	public boolean verificarDisponibilidade() {
		if (ramVazia()) {// mudar ram inst vazia
			Sinal sinal = new Sinal(Constantes.id_RAM, Constantes.id_ES, Constantes.id_SINAL_OK);
			Endereco endereco = new Endereco(Constantes.id_END_MEM, 0);
			Dado dado = new Dado(Constantes.id_DADO_VAZIO);
			Computador.barramento.Enfileirar(sinal, dado, endereco);
			return true;
		}
		return false;
	}

	public boolean colocarInstrucaoNaRam(Endereco e, Dado d) {
		if (ram[e.getEndereco()] == -1) {
			int cont = 0;
			for (int i = e.getEndereco(); i < d.getConteudo().length; i++) {
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

	public void mandarInstrucaoPraCpu(Endereco e, Dado d) {
		// TODO Auto-generated method stub
		
	}
}
