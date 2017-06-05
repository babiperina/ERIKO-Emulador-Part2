package componentes;

import java.util.Random;

import principal.Computador;
import utils.Constantes;
import utils.Dado;
import utils.Endereco;
import utils.Sinal;

public class Ram extends Thread {

	Barramento barramento;
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
		barramento = Computador.barramento;
		offset = Constantes.QTDE_ESP_INST * Constantes.QTDE_INST_BUFFER;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public void run() {
		while (rodando) {
			System.out.println("RAM rodando");
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();
	}

	public void enviarRespostaES() {
		// Random gerador = new Random();
		//
		// int numero = gerador.nextInt(10);
		// if (numero % 2 == 0) {
		// Sinal sinal = new Sinal(Constantes.id_RAM, Constantes.id_ES,
		// Constantes.id_SINAL_OK);
		// Dado dado = new Dado(Constantes.id_RAM, Constantes.id_ES,
		// Constantes.id_DADO_VAZIO);
		// Endereco endereco = new Endereco(Constantes.id_RAM, Constantes.id_ES,
		// Constantes.id_END_MEM, 1);
		// barramento.Enfileirar(sinal, dado, endereco);
		// } else {
		// Sinal sinal = new Sinal(Constantes.id_RAM, Constantes.id_ES,
		// Constantes.id_SINAL_ERROR);
		// Dado dado = new Dado(Constantes.id_RAM, Constantes.id_ES,
		// Constantes.id_DADO_VAZIO);
		// Endereco endereco = new Endereco(Constantes.id_RAM, Constantes.id_ES,
		// Constantes.id_END_VAZIO);
		// barramento.Enfileirar(sinal, dado, endereco);
		//
		// }
	}

	public boolean verificarDisponibilidade() {
		Random gerador = new Random();

		int numero = gerador.nextInt(10);

		if (numero % 2 == 0) {
			Sinal sinal = new Sinal(Constantes.id_RAM, Constantes.id_ES, Constantes.id_SINAL_OK);
			Endereco endereco = new Endereco(Constantes.id_END_MEM, 0);
			Dado dado = new Dado(Constantes.id_DADO_VAZIO);
			barramento.Enfileirar(sinal, dado, endereco);
			return true;
		}
		return false;
	}

	public void colocarInstrucaoNaRam(Endereco e, Dado d) {
		int cont = 0;
		for (int i = e.getEndereco(); i < offset; i++) {
			ram[i] = d.getConteudo()[cont++];
		}
	}

	public void parar() {
		rodando = false;
	}
}
