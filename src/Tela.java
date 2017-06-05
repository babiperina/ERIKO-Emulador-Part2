import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class Tela extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
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
		lblConsole.setBounds(10, 11, 46, 14);
		contentPane.add(lblConsole);
		
		JButton btnIniciar = new JButton("Iniciar");
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
		btnES.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnES);
		
		JButton btnEncoder = new JButton("Encoder");
		btnEncoder.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnEncoder);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(599, 130, 225, 470);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setForeground(new Color(0, 128, 0));
		textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea_1.setEditable(false);
		textArea_1.setBackground(Color.BLACK);
		textArea_1.setBounds(10, 36, 579, 564);
		contentPane.add(textArea_1);
	}
}
