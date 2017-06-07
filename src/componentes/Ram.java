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
				Random gerador = new Random();

				int numero = gerador.nextInt(10);
				if (numero % 2 == 0) {
					inicializarRam();
				}
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
		if (ram[0] == -1) {
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
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Ram [ram=" + Arrays.toString(ram) + "]";
	}

	public void parar() {
		rodando = false;
	}

	public void retornar() {
		rodando = true;
	}
}
