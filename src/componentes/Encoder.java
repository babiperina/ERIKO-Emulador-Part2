package componentes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Constantes;
import utils.InstrucaoCodificada;

public class Encoder {

	long codes[];
	public ArrayList<String> instrucoes = new ArrayList<>();
	public ArrayList<InstrucaoCodificada> instrucoesCodificadas = new ArrayList<>();

	public boolean todasInstrucoesNaES() {
		for (InstrucaoCodificada ic : instrucoesCodificadas) {
			if (!ic.isES())
				return false;
		}
		return true;
	}

	public void run() {
		mostrarInstrucoes();
		// codificar as instruçõess
		codificar();
		mostrarInstrucoesCodificadas();
		// passar as instruções pro e/s de acordo com o tamanho do buffer

	}

	public InstrucaoCodificada mandarInstrucoesParaModuloES() {
		for (int i = 0; i < instrucoesCodificadas.size(); i++) {
			if (!instrucoesCodificadas.get(i).isES()) {
				instrucoesCodificadas.get(i).setES(true);
				return instrucoesCodificadas.get(i);
			}
		}
		return null;
	}

	private void codificar() {
		String instrucaoAtual;
		InstrucaoCodificada codificada;
		long[] code;
		int cont = 1;
		System.out.println("$$$$ Instrução sendo codificada no encoder: ");
		while (instrucoes.size() != 0) {

			instrucaoAtual = instrucoes.get(0);

			if (cont == 1)
				System.out.println("$$$$ Codificação para LONG das instruções:");
			code = encoderInstrucaoToLong(instrucaoAtual);
			for (int i = 0; i < code.length; i++) {
				if (i == 0)
					System.out.print(cont++ + " - ");
				System.out.print(code[i] + " ");
			}
			System.out.println();

			codificada = new InstrucaoCodificada(
					transformarDeLongProCodigoFinal(code, Constantes.SIZE_word, Constantes.WIDTH_barramento));
			instrucoesCodificadas.add(codificada);

			instrucoes.remove(0);
		}
	}

	public Object[] transformarDeLongProCodigoFinal(long code[], int TP, int LB) {
		Object[] instruction = null;
		char type = (code[0] + "").charAt(0);
		short auxS;
		int auxI;
		long auxL;
		switch (LB) {
		case 8:
			switch (TP) {
			case 16:
				switch (type) {
				case '3':
				case '4':
					// add e mov
					instruction = new Short[2];
					auxS = (short) code[0];
					auxS ^= (code[1] << 8);
					instruction[0] = auxS;
					auxS = (short) code[2];
					instruction[1] = auxS;
					break;
				case '5':
					// inc
					instruction = new Short[1];
					auxS = (short) code[0];
					auxS ^= (code[1] << 8);
					instruction[0] = auxS;
					break;
				case '6':
					// imul
					instruction = new Short[2];
					auxS = (short) code[0];
					auxS ^= (code[1] << 8);
					instruction[0] = auxS;
					auxS = (short) code[2];
					auxS ^= (code[3] << 8);
					instruction[1] = auxS;
					break;
				}
				break;
			case 32:
				switch (type) {
				case '3':
				case '4':
					// add e mov
					instruction = new Integer[1];
					auxI = (int) code[0];
					auxI ^= (code[1] << 8);
					auxI ^= (code[2] << 8 * 2);
					instruction[0] = auxI;
					break;
				case '5':
					// inc
					instruction = new Integer[1];
					auxI = (int) code[0];
					auxI ^= (code[1] << 8);
					instruction[0] = auxI;
					break;
				case '6':
					// imul
					instruction = new Integer[1];
					auxI = (int) code[0];
					auxI ^= (code[1] << 8);
					auxI ^= (code[2] << 8 * 2);
					auxI ^= (code[3] << 8 * 3);
					instruction[0] = auxI;
					break;
				}
				break;
			case 64:
				switch (type) {
				case '3':
				case '4':
					// add e mov
					instruction = new Long[1];
					auxL = (long) code[0];
					auxL ^= (code[1] << 8);
					auxL ^= (code[2] << 8 * 2);
					instruction[0] = auxL;
					break;
				case '5':
					// inc
					instruction = new Long[1];
					auxL = (long) code[0];
					auxL ^= (code[1] << 8);
					instruction[0] = auxL;
					break;
				case '6':
					// imul
					instruction = new Long[1];
					auxL = (long) code[0];
					auxL ^= (code[1] << 8);
					auxL ^= (code[2] << 8 * 2);
					auxL ^= (code[3] << 8 * 3);
					instruction[0] = auxL;
					break;
				}
				break;
			}
			break;
		case 16:
			switch (TP) {
			case 16:
				switch (type) {
				case '3':
				case '4':
					// add e mov
					instruction = new Short[3];
					instruction[0] = (short) code[0];
					instruction[1] = (short) code[1];
					instruction[2] = (short) code[2];
					break;
				case '5':
					// inc
					instruction = new Short[2];
					instruction[0] = (short) code[0];
					instruction[1] = (short) code[1];
					break;
				case '6':
					// imul
					instruction = new Short[4];
					instruction[0] = (short) code[0];
					instruction[1] = (short) code[1];
					instruction[2] = (short) code[2];
					instruction[3] = (short) code[3];
					break;
				}
				break;
			case 32:
				switch (type) {
				case '3':
				case '4':
					// add e mov
					instruction = new Integer[2];
					auxI = (int) code[0];
					auxI ^= (code[1] << 8);
					instruction[0] = auxI;
					instruction[1] = (int) code[2];
					break;
				case '5':
					// inc
					instruction = new Integer[1];
					auxI = (int) code[0];
					auxI ^= (code[1] << 8);
					instruction[0] = auxI;
					break;
				case '6':
					// imul
					instruction = new Integer[2];
					auxI = (int) code[0];
					auxI ^= (code[1] << 8);
					instruction[0] = auxI;
					auxI = (int) code[2];
					auxI ^= (code[3] << 8);
					instruction[1] = auxI;
					break;
				}
				break;
			case 64:
				switch (type) {
				case '3':
				case '4':
					// add e mov
					instruction = new Long[1];
					auxL = (long) code[0];
					auxL ^= (code[1] << 8);
					auxL ^= (code[2] << 8 * 2);
					instruction[0] = auxL;
					break;
				case '5':
					// inc
					instruction = new Long[1];
					auxL = (long) code[0];
					auxL ^= (code[1] << 8);
					instruction[0] = auxL;
					break;
				case '6':
					// imul
					instruction = new Long[1];
					auxL = (long) code[0];
					auxL ^= (code[1] << 8);
					auxL ^= (code[2] << 8 * 2);
					auxL ^= (code[3] << 8 * 3);
					instruction[0] = auxL;
					break;
				}
				break;
			}
			break;
		case 32:
			switch (TP) {
			case 16:
				switch (type) {
				case '3':
				case '4':
					// add e mov
					instruction = new Short[6];
					instruction[0] = (short) ((code[0] >> 8 * 0) & 0xFF);
					instruction[1] = (short) ((code[0] >> 8 * 1) & 0xFF);
					instruction[2] = (short) ((code[1] >> 8 * 0) & 0xFF);
					instruction[3] = (short) ((code[1] >> 8 * 1) & 0xFF);
					instruction[4] = (short) ((code[2] >> 8 * 0) & 0xFF);
					instruction[5] = (short) ((code[2] >> 8 * 1) & 0xFF);
					break;
				case '5':
					// inc
					instruction = new Short[4];
					instruction[0] = (short) ((code[0] >> 8 * 0) & 0xFF);
					instruction[1] = (short) ((code[0] >> 8 * 1) & 0xFF);
					instruction[2] = (short) ((code[1] >> 8 * 0) & 0xFF);
					instruction[3] = (short) ((code[1] >> 8 * 1) & 0xFF);
					break;
				case '6':
					// imul
					instruction = new Short[8];
					instruction[0] = (short) ((code[0] >> 8 * 0) & 0xFF);
					instruction[1] = (short) ((code[0] >> 8 * 1) & 0xFF);
					instruction[2] = (short) ((code[1] >> 8 * 0) & 0xFF);
					instruction[3] = (short) ((code[1] >> 8 * 1) & 0xFF);
					instruction[4] = (short) ((code[2] >> 8 * 0) & 0xFF);
					instruction[5] = (short) ((code[2] >> 8 * 1) & 0xFF);
					instruction[6] = (short) ((code[3] >> 8 * 0) & 0xFF);
					instruction[7] = (short) ((code[3] >> 8 * 1) & 0xFF);
					break;
				}
				break;
			case 32:
				switch (type) {
				case '3':
				case '4':
					// add e mov
					instruction = new Integer[3];
					instruction[0] = (int) code[0];
					instruction[1] = (int) code[1];
					instruction[2] = (int) code[2];
					break;
				case '5':
					// inc
					instruction = new Integer[2];
					instruction[0] = (int) code[0];
					instruction[1] = (int) code[1];

					break;
				case '6':
					// imul
					instruction = new Integer[4];
					instruction[0] = (int) code[0];
					instruction[1] = (int) code[1];
					instruction[2] = (int) code[2];
					instruction[3] = (int) code[3];
					break;
				}
				break;
			case 64:
				switch (type) {
				case '3':
				case '4':
					// add e mov
					instruction = new Long[2];
					auxL = (long) code[0];
					auxL ^= (code[1] << 8);
					instruction[0] = auxL;
					instruction[1] = code[2];
					break;
				case '5':
					// inc
					instruction = new Long[1];
					auxL = (long) code[0];
					auxL ^= (code[1] << 8);
					instruction[0] = auxL;
					break;
				case '6':
					// imul
					instruction = new Long[2];
					auxL = (long) code[0];
					auxL ^= (code[1] << 8);
					instruction[0] = auxL;
					auxL = (long) code[2];
					auxL ^= (code[3] << 8);
					instruction[1] = auxL;
					break;
				}
				break;
			}
			break;
		}
		return instruction;
	}

	// REFATORAR
	// public Object[] transformarDeLongProCodigoFinal(long code[], int
	// palavraSize) {
	// Object[] instruction = null;
	// char type = (code[0] + "").charAt(0);
	// switch (palavraSize) {
	// case 16:
	// switch (type) {
	// case '3': // mov
	// instruction = new Short[3];
	// instruction[0] = (short) code[0];
	// instruction[1] = (short) code[1];
	// instruction[2] = (short) code[2];
	// break;
	// case '4': // add
	// instruction = new Short[3];
	// instruction[0] = (short) code[0];
	// instruction[1] = (short) code[1];
	// instruction[2] = (short) code[2];
	// break;
	// case '5': // inc
	// instruction = new Short[2];
	// instruction[0] = (short) code[0];
	// instruction[1] = (short) code[1];
	// break;
	// case '6': // imul
	// instruction = new Short[4];
	// instruction[0] = (short) code[0];
	// instruction[1] = (short) code[1];
	// instruction[2] = (short) code[2];
	// instruction[3] = (short) code[3];
	// break;
	// }
	// break;
	// case 32:
	// switch (type) {
	// case '3': // mov
	// instruction = new Integer[3];
	// instruction[0] = (int) code[0];
	// instruction[1] = (int) code[1];
	// instruction[2] = (int) code[2];
	// break;
	// case '4': // add
	// instruction = new Integer[3];
	// instruction[0] = (int) code[0];
	// instruction[1] = (int) code[1];
	// instruction[2] = (int) code[2];
	// break;
	// case '5': // inc
	// instruction = new Integer[2];
	// instruction[0] = (int) code[0];
	// instruction[1] = (int) code[1];
	// break;
	// case '6': // imul
	// instruction = new Integer[4];
	// instruction[0] = (int) code[0];
	// instruction[1] = (int) code[1];
	// instruction[2] = (int) code[2];
	// instruction[3] = (int) code[3];
	// break;
	// }
	// break;
	// case 64:
	// switch (type) {
	// case '3': // mov
	// instruction = new Long[3];
	// instruction[0] = (long) code[0];
	// instruction[1] = (long) code[1];
	// instruction[2] = (long) code[2];
	// break;
	// case '4': // add
	// instruction = new Long[3];
	// instruction[0] = (long) code[0];
	// instruction[1] = (long) code[1];
	// instruction[2] = (long) code[2];
	// break;
	// case '5': // inc
	// instruction = new Long[2];
	// instruction[0] = (long) code[0];
	// instruction[1] = (long) code[1];
	// break;
	// case '6': // imul
	// instruction = new Long[4];
	// instruction[0] = (long) code[0];
	// instruction[1] = (long) code[1];
	// instruction[2] = (long) code[2];
	// instruction[3] = (long) code[3];
	// break;
	// }
	// break;
	//
	// default:
	// System.out.println("error no tamanho da palavra");
	// System.exit(0);
	// break;
	// }
	//
	// return instruction;
	// }

	public void mostrarInstrucoesCodificadas() {
		if (instrucoesCodificadas.size() == 0)
			System.out.println("$$$$ Não há instruções codificadas no encoder.");
		else
			System.out.println("$$$$ Instruções codificadas para LONG/INT/SHORT no encoder:");

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

	public long[] encoderInstrucaoToLong(String instrucao) {
		long code[] = { 0 };
		Pattern p1 = Pattern.compile(Constantes.RE_add_mov);
		Pattern p2 = Pattern.compile(Constantes.RE_inc);
		Pattern p3 = Pattern.compile(Constantes.RE_imul);
		Matcher type1, type2, type3;

		type1 = p1.matcher(instrucao);
		type2 = p2.matcher(instrucao);
		type3 = p3.matcher(instrucao);

		if (type1.matches() || type2.matches() || type3.matches()) {
			Pattern r = Pattern.compile(Constantes.RE_register);
			Pattern m = Pattern.compile(Constantes.RE_memory);

			if (type1.matches()) {
				String type, x, y;
				type = type1.group(1);
				x = type1.group(2);
				y = type1.group(3);

				if (type.equalsIgnoreCase("mov")) {
					code = encoderMovInstruction(r, m, x, y);
				} else {
					code = encoderAddInstruction(r, m, x, y);
				}
				// System.out.println("Codificada em longs: " + code[0] + " " +
				// code[1] + " " + code[2]);
			} else if (type2.matches()) {
				String x = type2.group(2);

				code = encoderIncInstruction(r, x);
				// System.out.println("Instruçãoo codificada em longs: " +
				// code[0] + " " + code[1]);
			} else {
				String x, y, z;
				x = type3.group(2);
				y = type3.group(3);
				z = type3.group(4);
				code = encoderImulInstruction(r, m, x, y, z);
				// System.out.println(
				// "Instrução codificada em longs: " + code[0] + " " + code[1]
				// + " " + code[2] + " " + code[3]);
			}

		}

		return code;

	}

	long[] encoderMovInstruction(Pattern r, Pattern m, String x, String y) {
		long code[] = new long[3];
		Matcher matcher;

		matcher = r.matcher(x);
		if (matcher.matches()) {
			// mov register
			code[1] = encoderRegister(x);
			matcher = r.matcher(y);
			if (matcher.matches()) {
				// mov register, register
				code[0] = Constantes.VALUE_mov_r_from_r;
				code[2] = encoderRegister(y);
			} else {
				matcher = m.matcher(y);
				if (matcher.matches()) {
					// mov register, memory
					code[0] = Constantes.VALUE_mov_r_from_m;
					code[2] = encoderMemory(y);
				} else {
					// mov register, immediate
					code[0] = Constantes.VALUE_mov_r_from_i;
					code[2] = Long.valueOf(y).longValue();
				}
			}

		} else {
			// mov memory
			code[1] = encoderMemory(x);
			matcher = r.matcher(y);
			if (matcher.matches()) {
				// mov memory, register
				code[0] = Constantes.VALUE_mov_m_from_r;
				code[2] = encoderRegister(y);
			} else {
				matcher = m.matcher(y);
				if (!matcher.matches()) {
					// mov memory, immediate
					code[0] = Constantes.VALUE_mov_m_from_i;
					y = y.substring(2);
					code[2] = Long.parseLong(y);
				} else {
					// mov memory, memory
					code[0] = Constantes.VALUE_mov_m_from_m;
					code[2] = encoderMemory(y);
				}
			}
		}
		return code;
	}

	long[] encoderAddInstruction(Pattern r, Pattern m, String x, String y) {
		long code[] = new long[3];
		Matcher matcher;

		matcher = r.matcher(x);
		if (matcher.matches()) {
			// add register
			code[1] = encoderRegister(x);
			matcher = r.matcher(y);
			if (matcher.matches()) {
				// add register, register
				code[0] = Constantes.VALUE_add_r_from_r;
				code[2] = encoderRegister(y);
			} else {
				matcher = m.matcher(y);
				if (matcher.matches()) {
					// add register, memory
					code[0] = Constantes.VALUE_add_r_from_m;
					code[2] = encoderMemory(y);
				} else {
					// add register, immediate
					code[0] = Constantes.VALUE_add_r_from_i;
					code[2] = Long.valueOf(y).longValue();
				}
			}

		} else {
			// add memory
			code[1] = encoderMemory(x);
			matcher = r.matcher(y);
			if (matcher.matches()) {
				// add memory, register
				code[0] = Constantes.VALUE_add_m_from_r;
				code[2] = encoderRegister(y);
			} else {
				matcher = m.matcher(y);
				if (!matcher.matches()) {
					// add memory, immediate
					code[0] = Constantes.VALUE_add_m_from_i;
					code[2] = Long.parseLong(y);
				} else {
					// add memory, memory
					code[0] = Constantes.VALUE_add_m_from_m;
					code[2] = encoderMemory(y);
				}
			}
		}
		return code;
	}

	long[] encoderIncInstruction(Pattern r, String x) {
		long code[] = new long[2];
		Matcher matcher = r.matcher(x);
		if (matcher.matches()) {
			// inc register
			code[0] = Constantes.VALUE_inc_r;
			code[1] = encoderRegister(x);
		} else {
			// inc memory
			code[0] = Constantes.VALUE_inc_m;
			code[1] = encoderMemory(x);
		}
		return code;
	}

	long[] encoderImulInstruction(Pattern r, Pattern m, String x, String y, String z) {
		long[] code = new long[4];
		Matcher matcher;

		String c = "6";
		matcher = r.matcher(x);
		if (matcher.matches()) {
			c += "1";
			code[1] = encoderRegister(x);
		} else {
			c += "2";
			code[1] = encoderMemory(x);
		}

		matcher = r.matcher(y);
		if (matcher.matches()) {
			c += "1";
			code[2] = encoderRegister(y);
		} else {
			matcher = m.matcher(y);
			if (matcher.matches()) {
				c += "2";
				code[2] = encoderMemory(y);
			} else {
				c += "3";
				code[2] = Long.parseLong(y);
			}
		}

		matcher = r.matcher(z);
		if (matcher.matches()) {
			c += "1";
			code[3] = encoderRegister(z);
		} else {
			matcher = m.matcher(z);
			if (matcher.matches()) {
				c += "2";
				code[3] = encoderMemory(z);
			} else {
				c += "3";
				code[3] = Long.parseLong(z);
			}
		}

		code[0] = Long.parseLong(c);
		return code;
	}

	long encoderRegister(String register) {
		if (register.equalsIgnoreCase("a")) {
			return Constantes.VALUE_register_A;
		} else if (register.equalsIgnoreCase("b")) {
			return Constantes.VALUE_register_B;
		} else if (register.equalsIgnoreCase("c")) {
			return Constantes.VALUE_register_C;
		} else {
			return Constantes.VALUE_register_D;
		}

	}

	long encoderMemory(String memory) {
		return Long.parseLong(memory.substring(2), 16);
	}

}
