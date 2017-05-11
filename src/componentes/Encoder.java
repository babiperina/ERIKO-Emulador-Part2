package componentes;

import java.util.ArrayList;

import utils.InstrucaoCodificada;

public class Encoder {

	long codes[];
	public ArrayList<String> instrucoes = new ArrayList<>();
	public ArrayList<InstrucaoCodificada> instrucoesCodificadas = new ArrayList<>();

	public void run() {
		mostrarInstrucoes();
		// codificar as instruçõess
		codificar();
		mostrarInstrucoesCodificadas();
		// passar as instruções pro e/s de acordo com o tamanho do buffer
		mandarInstrucoesParaModuloES();

	}

	private void mandarInstrucoesParaModuloES() {
		// TODO Auto-generated method stub

	}

	private void codificar() {
		String instrucaoAtual;
		while (instrucoes.size() != 0) {
			instrucaoAtual = instrucoes.get(0);
			
			
			
			instrucoes.remove(0);	
		}
	}

	public void mostrarInstrucoesCodificadas() {
		if (instrucoesCodificadas.size() == 0)
			System.out.println("$$$$ Não há instruções codificadas no encoder.");
		else
			System.out.println("$$$$ Instruções codificadas no encoder:");

		int cont = 1;
		for (InstrucaoCodificada instrucao : instrucoesCodificadas) {
			System.out.println(cont++ + " - " + instrucao);
		}
	}
	
	public void mostrarInstrucoes() {
		if (instrucoes.size() == 0)
			System.out.println("$$$$ Não há instruções no encoder.");
		else
			System.out.println("$$$$ Instruções no encoder:");

		int cont = 1;
		for (String instrucao : instrucoes) {
			System.out.println(cont++ + " - " + instrucao);
		}
	}

}
