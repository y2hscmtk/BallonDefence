package game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	//������
   //�̹��� �ε� => ��θ� �־����� �̹����� �ε���
	private ImageIcon normalIcon = new ImageIcon("normal.png");//�����
	private ImageIcon overIcon = new ImageIcon("over.png");//�����
	private ImageIcon pressIcon = new ImageIcon("press.png");//�����
	private JButton startBtn = new JButton(normalIcon); //��� �������� �̹����� ���� ��ư ���� 
	private JButton pauseBtn = new JButton("pause"); 
	private WordList wordList = new WordList(); 
	private ScorePanel scorePanel = new ScorePanel();
	private EditPanel editPanel = new EditPanel();
	private GamePanel gamePanel = new GamePanel(wordList,scorePanel); //�� ����� ���۷����� �Ű������� ����
   
	public GameFrame() {
		super("�ܾ����");
		setSize(800,600);
      
		makeNenu(); //�޴��� �����
		makeToolBar(); //���� �����
		makeSplitPane();
      
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x��ư�� �������� ���α׷� ����
	}
   

	private void makeNenu() {
		JMenuBar bar = new JMenuBar(); //�޴� �� ����
		//�޴��� ����Ʈ ���� ������ �ƴ� => �����ӿ� �ٿ�����
		this.setJMenuBar(bar); //this = �����ӿ� �޴��� ���̱�
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Eidt");
		JMenu sourceMenu = new JMenu("Source");
      
		bar.add(fileMenu);
		bar.add(editMenu);
		bar.add(sourceMenu);
      
		JMenuItem newItem = new JMenuItem("New");
		JMenuItem openItem = new JMenuItem("Open File");
		JMenuItem exitItem = new JMenuItem("Exit Program");
      
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		//�޴� ������ �и��� �����
		fileMenu.addSeparator(); // �и���
		fileMenu.add(exitItem);
	}
   
	private void makeToolBar() {

		JToolBar tBar = new JToolBar();
      
		startBtn.setPressedIcon(pressIcon);
		startBtn.setRolloverIcon(overIcon);
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = wordList.getWord(); //���Ϳ��� �������� �ܾ� �̱�
				gamePanel.setWord(word);
			}
		});
      
		//��Ŭ���� �������� File Edit �� �޴��� �ؿ� �ִ� ������ => ����
		tBar.add(startBtn);
		tBar.add(pauseBtn);
		tBar.add(new JTextField(20));
		tBar.add(new JLabel("����"));
		tBar.add(new JCheckBox());
      
		Container c = getContentPane();
		c.add(tBar,BorderLayout.NORTH); //���ʿ� ���� �ޱ� => ����Ʈ�� �����̱⶧���� ����Ʈ�濡 �ٿ�����
		//�ڵ��� �����ϱ⶧���� ����Ʈ ���¿����������Ӱ� ������ ��ġ�� �ű�� �ִ�.
      
      
	}
   
	private void makeSplitPane() {
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//������ ���� => ��� ������ ��������(� ����)
		hPane.setDividerLocation(500); // ȭ���� ��𼭺��� ������ ����
      	getContentPane().add(hPane,BorderLayout.CENTER); //����� splitPane ���̱�
      	//=> ȭ���� �ΰ��� ������ ����Ҽ� �ְ� �� => ����̴�
      
      	JSplitPane vPane = new JSplitPane(); //���η� ������ splitPane
      	vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
      	vPane.setDividerLocation(300);
      	//hPane�� �ٿ�����
      	hPane.setRightComponent(vPane); //hPane�� �����ʿ� ���̱�
      	hPane.setLeftComponent(gamePanel);
      
      	//���� �г� ���̱�
      	vPane.setTopComponent(scorePanel);
      	vPane.setBottomComponent(editPanel);
	}
}