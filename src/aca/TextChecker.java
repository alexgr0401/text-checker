package aca;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class TextChecker {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextChecker window = new TextChecker();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TextChecker() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 479, 324);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTextArea tArea = new JTextArea();
		tArea.setFont(new Font("Dialog", Font.PLAIN, 14));
		tArea.setBounds(31, 97, 273, 141);
		frame.getContentPane().add(tArea);

		JTextArea tBox = new JTextArea();
		tBox.setFont(new Font("Dialog", Font.PLAIN, 14));
		tBox.setBounds(31, 33, 273, 22);
		frame.getContentPane().add(tBox);

		JLabel lbl = new JLabel("Broj reči:");
		lbl.setVerticalAlignment(SwingConstants.TOP);
		lbl.setBounds(31, 246, 56, 18);
		frame.getContentPane().add(lbl);

		JLabel lblResCounter = new JLabel("______");
		lblResCounter.setVerticalAlignment(SwingConstants.BOTTOM);
		lblResCounter.setHorizontalAlignment(SwingConstants.LEFT);
		lblResCounter.setFont(new Font("Dialog", Font.BOLD, 14));
		lblResCounter.setBounds(86, 241, 56, 22);
		frame.getContentPane().add(lblResCounter);

		JButton btnImportText = new JButton("Prikaži tekst");
		btnImportText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				StringBuilder sb = new StringBuilder();

				String filepath = tBox.getText();
				File file = new File(filepath);

				if (tBox.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Niste izabrali fajl", "Greška!", JOptionPane.WARNING_MESSAGE);
				} else if (!file.canRead() || !file.getAbsolutePath().equalsIgnoreCase(filepath)) {
					JOptionPane.showMessageDialog(null, "Pogrešan unos!", "Greška!", JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						Scanner sc = new Scanner(file, "UTF-8");
						while (sc.hasNext()) {
							sb.append(sc.nextLine());
							sb.append("\n");
							tArea.setText(sb.toString());
						}
						sc.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnImportText.setFont(new Font("Dialog", Font.BOLD, 12));
		btnImportText.setBounds(31, 66, 115, 22);
		frame.getContentPane().add(btnImportText);

		JButton btnChooseFile = new JButton("Izaberi fajl");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fc = new JFileChooser();

				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					tBox.setText(fc.getSelectedFile().getPath());
				}
			}
		});
		btnChooseFile.setBounds(314, 33, 117, 22);
		frame.getContentPane().add(btnChooseFile);

		JButton btnFixSpaces = new JButton("Sredi razmake");
		btnFixSpaces.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (tArea.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Niste uneli tekst", "Greška!", JOptionPane.WARNING_MESSAGE);
				} else {
					tArea.setText(procesSpaces(tArea.getText()));
				}
			}
		});
		btnFixSpaces.setFont(new Font("Dialog", Font.BOLD, 12));
		btnFixSpaces.setBounds(314, 97, 117, 22);
		frame.getContentPane().add(btnFixSpaces);

		JButton btnCounter = new JButton("Prebroj reči");
		btnCounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tArea.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Niste uneli tekst", "Greška!", JOptionPane.WARNING_MESSAGE);
				} else {
					lblResCounter.setText(wordCounter(tArea.getText()));
				}
			}
		});
		btnCounter.setBounds(314, 131, 117, 22);
		frame.getContentPane().add(btnCounter);

		JButton btnPalindrom = new JButton("Palindrom?");
		btnPalindrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tArea.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Niste uneli tekst", "Greška!", JOptionPane.WARNING_MESSAGE);
				} else {
					Scanner sc = new Scanner(tArea.getText());
					Integer numRow = 0;
					while (sc.hasNextLine()) {
						String line = sc.nextLine();
						line = line.replaceAll("[^a-zA-Z]", "");

						String reverse = "";
						for (int i = line.length() - 1; i >= 0; i--)
							reverse += line.charAt(i);
						numRow++;

						if (line.isEmpty()) {
							JOptionPane.showMessageDialog(null, +numRow + ". " + "red ne sadrži tekst.");
						} else if (reverse.equalsIgnoreCase(line)) {
							JOptionPane.showMessageDialog(null, +numRow + ". " + "red teksta je palindrom.");
						} else {
							JOptionPane.showMessageDialog(null, +numRow + ". " + "red teksta nije palindrom.");
						}
					}
					sc.close();
				}
			}
		});
		btnPalindrom.setBounds(314, 165, 117, 22);
		frame.getContentPane().add(btnPalindrom);

		JButton btnSave = new JButton("Sačuvaj");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tArea.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Niste uneli tekst", "Greška!", JOptionPane.WARNING_MESSAGE);
				} else {
					String res1 = procesSpaces(tArea.getText());
					String res2 = wordCounter(tArea.getText());
					List<String> res3 = isPalindrom(tArea.getText());

					JFileChooser fc = new JFileChooser();
					if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();

						try {
							FileWriter fw = new FileWriter(file);
							fw.write(res1 + System.lineSeparator());
							fw.write("Tekst sadrži " + res2 + " reči." + System.lineSeparator());
							for (String row : res3) {
								fw.write(row);
							}
							fw.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}

				btnFixSpaces.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tArea.setEditable(false);
						tBox.setEditable(false);
						btnImportText.setEnabled(false);
						btnChooseFile.setEnabled(false);
					}
				});

				btnCounter.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tArea.setEditable(false);
						tBox.setEditable(false);
						btnImportText.setEnabled(false);
						btnChooseFile.setEnabled(false);
					}
				});

				btnPalindrom.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tArea.setEditable(false);
						tBox.setEditable(false);
						btnImportText.setEnabled(false);
						btnChooseFile.setEnabled(false);
					}
				});

				btnSave.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tArea.setEditable(false);
						tBox.setEditable(false);
						btnImportText.setEnabled(false);
						btnChooseFile.setEnabled(false);
					}
				});
			}
		});
		btnSave.setBounds(314, 216, 117, 22);
		frame.getContentPane().add(btnSave);

	}

	public String procesSpaces(String text) {

		StringBuilder sb = new StringBuilder();
		Scanner sc = new Scanner(text);

		while (sc.hasNextLine()) {
			sb.append(sc.nextLine().replaceAll("\\s+", " "));
			sb.append("\n");
		}
		sc.close();
		return sb.toString();
	}

	public String wordCounter(String text) {

		Scanner sc = new Scanner(text);

		Integer numWords = 0;
		Integer numEmptyLines = 0;

		while (sc.hasNextLine()) {
			String line = sc.nextLine().replaceAll("\\s+", " ");
			String[] lines = line.split(" ");

			for (int i = 0; i < lines.length; i++) {
				numWords++;
				if (line.isEmpty()) {
					numEmptyLines++;
				}
			}
		}
		numWords -= numEmptyLines;

		sc.close();
		return numWords.toString();
	}

	public List<String> isPalindrom(String text) {

		List<String> listInfo = new ArrayList<>();

		Scanner sc = new Scanner(text);

		String row;
		Integer numRow = 0;
		String info;

		while (sc.hasNextLine()) {
			row = sc.nextLine();
			row = row.replaceAll("[^a-zA-Z]", "");

			String reverse = "";
			for (int i = row.length() - 1; i >= 0; i--)
				reverse += row.charAt(i);
			numRow++;

			if (row.isEmpty()) {
				info = numRow + ". " + "red ne sadrži tekst.";
				listInfo.add(info + System.lineSeparator());
			} else if (reverse.equalsIgnoreCase(row)) {
				info = numRow + ". " + "red teksta je palindrom.";
				listInfo.add(info + System.lineSeparator());
			} else {
				info = numRow + ". " + "red teksta nije palindrom.";
				listInfo.add(info + System.lineSeparator());
			}
		}
		sc.close();
		return listInfo;
	}
}