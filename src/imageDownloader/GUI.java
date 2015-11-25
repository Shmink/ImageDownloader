package imageDownloader;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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

import javax.swing.DefaultComboBoxModel;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GUI {

	private JFrame frmFileDownloader;
	private JTextField URLtextBox;
	private JTextField filePathBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmFileDownloader.setVisible(true);
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
	public static String path = null; 
	public static String url = null;
	Grabber grab = new Grabber();
	private JTable table;
	//Table variables
	String[] nameTheColoumns = {"File", "Status"};
	String[][] data;
	JLabel lblNoFiles;
	
	private void initialize() {
		frmFileDownloader = new JFrame();
		frmFileDownloader.setTitle("File Downloader");
		frmFileDownloader.setBounds(100, 100, 571, 412);
		frmFileDownloader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFileDownloader.getContentPane().setLayout(null);
		
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
		
		SwingUtilities.updateComponentTreeUI(frmFileDownloader);
		
		lblNoFiles = new JLabel("No files :(");
		lblNoFiles.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 16));
		lblNoFiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoFiles.setBounds(70, 156, 200, 50);
		frmFileDownloader.getContentPane().add(lblNoFiles);
		
		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblUrl.setBounds(10, 11, 46, 14);
		frmFileDownloader.getContentPane().add(lblUrl);
		
		URLtextBox = new JTextField();
		URLtextBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		URLtextBox.setBounds(38, 8, 306, 20);
		frmFileDownloader.getContentPane().add(URLtextBox);
		URLtextBox.setColumns(10);
		
		JButton btnFetchImages = new JButton("Fetch files");
		btnFetchImages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(URLtextBox.getText().contains("http"))
				{
					Grabber.filenameArray.clear();
					url = URLtextBox.getText();
					tableMaker();
				}
				else
					JOptionPane.showMessageDialog(null, "The URL text field isn't correct.");
			}
		});
		btnFetchImages.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnFetchImages.setBounds(357, 6, 188, 23);
		frmFileDownloader.getContentPane().add(btnFetchImages);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(357, 40, 188, 2);
		frmFileDownloader.getContentPane().add(separator);
		
		JLabel lblImages = new JLabel("Filter file types: ");
		lblImages.setHorizontalAlignment(SwingConstants.CENTER);
		lblImages.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblImages.setBounds(357, 51, 188, 14);
		frmFileDownloader.getContentPane().add(lblImages);
		
		JComboBox fileTypeComboBox = new JComboBox();
		fileTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"All", ".jpg", ".png", ".gif", ".zip", ".pdf"}));
		fileTypeComboBox.setSelectedIndex(0);
		fileTypeComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		fileTypeComboBox.setBounds(357, 76, 188, 20);
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
		dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 
		fileTypeComboBox.setRenderer(dlcr); 
		frmFileDownloader.getContentPane().add(fileTypeComboBox);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(357, 103, 188, 2);
		frmFileDownloader.getContentPane().add(separator_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(Color.BLUE);
		progressBar.setBounds(357, 262, 188, 23);
		frmFileDownloader.getContentPane().add(progressBar);
		
		JButton btnDownload = new JButton("Download files");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(URLtextBox.getText().contains("http") && filePathBox.getText().length() > 0)
				{
					url = URLtextBox.getText();
					path = filePathBox.getText();
					
					grab.run();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Some important fields are empty.");
				}
			}
		});
		btnDownload.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnDownload.setBounds(357, 296, 188, 66);
		frmFileDownloader.getContentPane().add(btnDownload);
		
		JLabel lblChooseFileDestination = new JLabel("Choose file destination:");
		lblChooseFileDestination.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblChooseFileDestination.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseFileDestination.setBounds(357, 116, 188, 14);
		frmFileDownloader.getContentPane().add(lblChooseFileDestination);
		
		filePathBox = new JTextField();
		filePathBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		filePathBox.setEditable(false);
		filePathBox.setBounds(357, 141, 188, 20);
		frmFileDownloader.getContentPane().add(filePathBox);
		filePathBox.setColumns(10);
		
		JButton btnChooseFolder = new JButton("Choose folder...");
		btnChooseFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				promptForFile();
				filePathBox.setText(path); 
			}
		});
		btnChooseFolder.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnChooseFolder.setBounds(357, 171, 188, 23);
		frmFileDownloader.getContentPane().add(btnChooseFolder);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(357, 199, 188, 2);
		frmFileDownloader.getContentPane().add(separator_2);
		
		JLabel lblThreads = new JLabel("Threads:");
		lblThreads.setHorizontalAlignment(SwingConstants.CENTER);
		lblThreads.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblThreads.setBounds(357, 205, 188, 14);
		frmFileDownloader.getContentPane().add(lblThreads);
		
		JComboBox threadsComboBox = new JComboBox();
		threadsComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8"}));
		threadsComboBox.setSelectedIndex(0);
		threadsComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		threadsComboBox.setBounds(357, 225, 188, 23);
		DefaultListCellRenderer dlcr2 = new DefaultListCellRenderer(); 
		dlcr2.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 
		threadsComboBox.setRenderer(dlcr2); 
		frmFileDownloader.getContentPane().add(threadsComboBox);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(357, 254, 188, 2);
		frmFileDownloader.getContentPane().add(separator_3);
		
		table = new JTable();
		table.setBackground(Color.LIGHT_GRAY);
		//table.setModel(new DefaultTableModel(data, nameTheColoumns));
		table.setBounds(10, 36, 334, 326);
		frmFileDownloader.getContentPane().add(table);
		
		
	}
	
	public void tableMaker()
	{
		grab.updateFileNames();
		int amountOfImages = Grabber.filenameArray.size();
		
		if(amountOfImages > 0)
		{
			lblNoFiles.setText("");
			table.setBackground(Color.WHITE);
		}
		
		data = new String[Grabber.filenameArray.size()][nameTheColoumns.length];
		for (int i = 0; i < amountOfImages; i++) 
		{
			String fileName = Grabber.filenameArray.get(i);
			String status = "Queued";
			System.out.println("in the tableMaker method: " + fileName);
			String[] details = {fileName, status};
			
			data[i] = details;
		}
		table.setModel(new DefaultTableModel(data, nameTheColoumns));
	}
	
	/**
	 * promptForFile is called when you click the 'Add attachment' button.
	 * It opens up a standard JFileChooser and will then save the file name
	 * and file path in variables to be used later.
	 * @return String - File name
	 */
	public void promptForFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		int returnVal = fileChooser.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) 
			path = fileChooser.getSelectedFile().getAbsolutePath();
		else
			path = "";
	}
}
