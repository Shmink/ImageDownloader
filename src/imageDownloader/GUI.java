package imageDownloader;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField filePathBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	String path;
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 571, 412);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Make the UI look like that of Windows, Java default is ugly.
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		SwingUtilities.updateComponentTreeUI(frame);
		
		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblUrl.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(lblUrl);
		
		textField = new JTextField();
		textField.setFont(new Font("Consolas", Font.PLAIN, 11));
		textField.setBounds(38, 8, 309, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnFetchImages = new JButton("Fetch Images");
		btnFetchImages.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnFetchImages.setBounds(357, 6, 188, 23);
		frame.getContentPane().add(btnFetchImages);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 334, 326);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(357, 40, 188, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblImages = new JLabel("Filter types: ");
		lblImages.setHorizontalAlignment(SwingConstants.CENTER);
		lblImages.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblImages.setBounds(357, 51, 188, 14);
		frame.getContentPane().add(lblImages);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		comboBox.setBounds(357, 76, 188, 20);
		frame.getContentPane().add(comboBox);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(357, 103, 188, 2);
		frame.getContentPane().add(separator_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(357, 305, 188, 23);
		frame.getContentPane().add(progressBar);
		
		JButton btnNewButton = new JButton("Download Images");
		btnNewButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnNewButton.setBounds(357, 339, 188, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblChooseFileDestination = new JLabel("Choose file destination:");
		lblChooseFileDestination.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblChooseFileDestination.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseFileDestination.setBounds(357, 116, 188, 14);
		frame.getContentPane().add(lblChooseFileDestination);
		
		filePathBox = new JTextField(path);
		filePathBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		filePathBox.setEditable(false);
		filePathBox.setBounds(357, 141, 188, 20);
		frame.getContentPane().add(filePathBox);
		filePathBox.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Choose folder...");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				promptForFile();
			}
		});
		btnNewButton_1.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnNewButton_1.setBounds(357, 171, 188, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(357, 199, 188, 2);
		frame.getContentPane().add(separator_2);
	}
	
	/**
	 * promptForFile is called when you click the 'Add attachment' button.
	 * It opens up a standard JFileChooser and will then save the file name
	 * and file path in variables to be used later.
	 * @return String - File name
	 */
	public String promptForFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			System.out.print(path);
			path = fileChooser.getSelectedFile().getAbsolutePath();
		  	return path;
		}
		else
		{
			return "";
		}
	}
}
