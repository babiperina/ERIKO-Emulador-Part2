package principal;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Tela extends JFrame {

	private JPanel contentPane;
	JTextArea console = new JTextArea();
	public static Computador computador = new Computador();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					Computador.tela = frame;
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblConsole = new JLabel("Console:");
		lblConsole.setBounds(10, 11, 77, 14);
		contentPane.add(lblConsole);

		JTextArea areaComponente = new JTextArea();
		areaComponente.setEditable(false);
		areaComponente.setBounds(599, 130, 225, 470);
		areaComponente.setLineWrap(true);
		areaComponente.setWrapStyleWord(true);
		contentPane.add(areaComponente);

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				computador.init();
			}
		});
		btnIniciar.setBounds(275, 11, 89, 23);
		contentPane.add(btnIniciar);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(599, 11, 225, 119);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 3, 0, 0));

		JButton btnCpu = new JButton("CPU");
		btnCpu.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCpu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_1.add(btnCpu);

		JButton btnRam = new JButton("RAM");
		btnRam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnRam);

		JButton btnES = new JButton("ES");
		btnES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				areaComponente.setText(Computador.es.toString());
			}
		});
		btnES.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnES);

		JButton btnEncoder = new JButton("Encoder");
		btnEncoder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				areaComponente.setText(Computador.encoder.toString());
			}
		});
		btnEncoder.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnEncoder);

		console.setEditable(false);
		console.setForeground(new Color(34, 139, 34));
		console.setFont(new Font("Monospaced", Font.PLAIN, 13));
		console.setBackground(Color.BLACK);
		console.setBounds(10, 36, 579, 564);
		console.setLineWrap(true);
		console.setWrapStyleWord(true);
		contentPane.add(console);

		JButton btnParar = new JButton("Parar");
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				computador.parar();
			}
		});
		btnParar.setBounds(368, 11, 89, 23);
		contentPane.add(btnParar);

	}

	public void escreverNoConsole(String texto) {
		if (console.getText() == "")
			console.setText(texto);
		else
			console.setText(console.getText() + "\n" + texto);
	}
}
