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
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;

public class Tela extends JFrame {

	private JPanel contentPane;
	static JTextArea console = new JTextArea();
	static JTextArea areaComponente = new JTextArea();
	static JButton btnCpu = new JButton("CPU");
	public static Computador computador = new Computador();
	JCheckBox chckbxAutoscroll = new JCheckBox("AutoScroll");
	JButton btnRam = new JButton("RAM");
	JPanel panelBar = new JPanel();
	JPanel panelCpu = new JPanel();
	JPanel panelRam = new JPanel();
	JPanel panelEs = new JPanel();

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
		setTitle("Desenvolvido por Bárbara Perina e Heitor Lopes.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 650);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblConsole = new JLabel("Console:");
		lblConsole.setBounds(10, 11, 77, 14);
		contentPane.add(lblConsole);

		JScrollPane scrollPaneAC = new JScrollPane(areaComponente);
		scrollPaneAC.setBounds(799, 137, 225, 339);
		contentPane.add(scrollPaneAC);

		JButton btnParar = new JButton("Parar");
		btnParar.setEnabled(false);
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				computador.init();
				btnIniciar.setEnabled(false);
				btnParar.setEnabled(true);
			}
		});

		areaComponente.setEditable(false);
		areaComponente.setBounds(799, 137, 225, 23);
		areaComponente.setLineWrap(true);
		areaComponente.setWrapStyleWord(true);
		btnIniciar.setBounds(518, 11, 89, 23);
		contentPane.add(btnIniciar);

		JScrollPane scrollPane = new JScrollPane(console);
		scrollPane.setBounds(10, 36, 779, 564);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(799, 11, 225, 119);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 3, 0, 0));

		btnCpu.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCpu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_1.add(btnCpu);

		JButton btnRam = new JButton("RAM");
		btnRam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				areaComponente.setText(Computador.ram.toString());
				areaComponente.setCaretPosition(0);
			}
		});
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
		console.setForeground(new Color(50, 205, 50));
		console.setFont(new Font("Monospaced", Font.PLAIN, 13));
		console.setBackground(Color.BLACK);
		console.setBounds(10, 36, 554, 564);
		console.setLineWrap(true);
		console.setWrapStyleWord(true);

		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				computador.parar();
				btnIniciar.setEnabled(false);
				btnParar.setEnabled(false);
			}
		});
		btnParar.setBounds(607, 11, 89, 23);
		contentPane.add(btnParar);
		chckbxAutoscroll.setSelected(true);

		chckbxAutoscroll.setBounds(702, 11, 89, 23);
		contentPane.add(chckbxAutoscroll);

		JPanel panel = new JPanel();
		panel.setBounds(799, 480, 225, 120);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(4, 2, 0, 0));

		JLabel lblNewLabel_1 = new JLabel("Barramento");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1);

		panel.add(panelBar);

		JLabel lblNewLabel_2 = new JLabel("Cpu");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_2);
		panel.add(panelCpu);

		JLabel lblNewLabel_4 = new JLabel("Ram");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_4);
		panel.add(panelRam);

		JLabel lblNewLabel_5 = new JLabel("M\u00F3dulo ES");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_5);
		panel.add(panelEs);

	}

	public void escreverNoConsole(String texto) {
		console.setText(console.getText() + "\n" + texto);
		scrollAcompanhar(chckbxAutoscroll.isSelected());
	}

	public void scrollAcompanhar(boolean acompanha) {
		if (acompanha)
			console.setCaretPosition(console.getText().length());

	}

	public void toNaES(boolean to) {
		if (to) {
			panelEs.setBackground(new Color(102, 153, 255));
		} else {
			panelEs.setBackground(new Color(240, 240, 240));
		}
	}

	public void toNoBarramento(boolean to) {
		if (to) {
			panelBar.setBackground(new Color(153, 255, 153));
		} else {
			panelBar.setBackground(new Color(240, 240, 240));
		}
	}

	public void toNaCpu(boolean to) {
		if (to) {
			panelCpu.setBackground(new Color(0, 51, 153));
		} else {
			panelCpu.setBackground(new Color(240, 240, 240));
		}
	}

	public void toNaRam(boolean to) {
		if (to) {
			panelRam.setBackground(new Color(153, 51, 51));
		} else {
			panelRam.setBackground(new Color(240, 240, 240));
		}
	}
}
