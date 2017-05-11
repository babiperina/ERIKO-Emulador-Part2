package componentes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import principal.Computador;
import utils.Constantes;

public class Parser {

	private ArrayList<String> instrucoes = new ArrayList<>();

	public Parser() {
		lerArquivoComInstrucoes();
	}
	
	public void run(){
		mostrarInstrucoes();
		passarInstrucoesProEncoder();
	}

	public void passarInstrucoesProEncoder() {
		System.out.println("$$$$ Informações sendo passadas do parser para o encoder.");
		Computador.encoder.instrucoes = instrucoes;
		instrucoes = new ArrayList<>();
	}

	public void mostrarInstrucoes() {
		if (instrucoes.size() == 0)
			System.out.println("$$$$ Não há instruções no parser.");
		else
			System.out.println("$$$$ Instruções no parser:");

		int cont = 1;
		for (String instrucao : instrucoes) {
			System.out.println(cont++ + " - " + instrucao);
		}
	}

	private void lerArquivoComInstrucoes() {
		File f = new File(Constantes.ARQUIVO_DE_TEXTO);

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			Pattern p1 = Pattern.compile(Constantes.RE_add_mov);
			Pattern p2 = Pattern.compile(Constantes.RE_inc);
			Pattern p3 = Pattern.compile(Constantes.RE_imul);
			Matcher m1, m2, m3;

			String linha;
			int cont = 0;
			while ((linha = br.readLine()) != null) {
				cont++;
				m1 = p1.matcher(linha);
				m2 = p2.matcher(linha);
				m3 = p3.matcher(linha);
				if (m1.matches() || m2.matches() || m3.matches()) {
					instrucoes.add(linha);
				} else {
					System.err.println("Erro na linha " + cont);
					System.exit(0);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
