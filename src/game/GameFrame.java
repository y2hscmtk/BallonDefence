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
	//수정함
   //이미지 로딩 => 경로명만 주어지면 이미지를 로드함
	private ImageIcon normalIcon = new ImageIcon("normal.png");//상대경로
	private ImageIcon overIcon = new ImageIcon("over.png");//상대경로
	private ImageIcon pressIcon = new ImageIcon("press.png");//상대경로
	private JButton startBtn = new JButton(normalIcon); //노멀 아이콘을 이미지로 가진 버튼 생성 
	private JButton pauseBtn = new JButton("pause"); 
	private WordList wordList = new WordList(); 
	private ScorePanel scorePanel = new ScorePanel();
	private EditPanel editPanel = new EditPanel();
	private GamePanel gamePanel = new GamePanel(wordList,scorePanel); //각 페널의 레퍼랜스를 매개변수로 보냄
   
	public GameFrame() {
		super("단어게임");
		setSize(800,600);
      
		makeNenu(); //메뉴바 만들기
		makeToolBar(); //툴바 만들기
		makeSplitPane();
      
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x버튼을 눌렀을때 프로그램 종료
	}
   

	private void makeNenu() {
		JMenuBar bar = new JMenuBar(); //메뉴 바 생성
		//메뉴는 컨텐트 펜의 영역이 아님 => 프레임에 붙여야함
		this.setJMenuBar(bar); //this = 프레임에 메뉴바 붙이기
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
		//메뉴 아이템 분리선 만들기
		fileMenu.addSeparator(); // 분리선
		fileMenu.add(exitItem);
	}
   
	private void makeToolBar() {

		JToolBar tBar = new JToolBar();
      
		startBtn.setPressedIcon(pressIcon);
		startBtn.setRolloverIcon(overIcon);
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = wordList.getWord(); //벡터에서 무작위로 단어 뽑기
				gamePanel.setWord(word);
			}
		});
      
		//이클립스 기준으로 File Edit 등 메뉴바 밑에 있는 아이콘 => 툴바
		tBar.add(startBtn);
		tBar.add(pauseBtn);
		tBar.add(new JTextField(20));
		tBar.add(new JLabel("점심"));
		tBar.add(new JCheckBox());
      
		Container c = getContentPane();
		c.add(tBar,BorderLayout.NORTH); //북쪽에 툴바 달기 => 컨텐트펜 영역이기때문에 컨텐트펜에 붙여야함
		//핸들이 존재하기때문에 디폴트 상태에서는자유롭게 툴바의 위치를 옮길수 있다.
      
      
	}
   
	private void makeSplitPane() {
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//방향을 설정 => 어떻게 영역을 분할할지(어떤 방향)
		hPane.setDividerLocation(500); // 화면을 어디서부터 나눌지 정함
      	getContentPane().add(hPane,BorderLayout.CENTER); //가운데에 splitPane 붙이기
      	//=> 화면을 두개로 나누어 사용할수 있게 함 => 디바이더
      
      	JSplitPane vPane = new JSplitPane(); //가로로 나누는 splitPane
      	vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
      	vPane.setDividerLocation(300);
      	//hPane에 붙여야함
      	hPane.setRightComponent(vPane); //hPane의 오른쪽에 붙이기
      	hPane.setLeftComponent(gamePanel);
      
      	//점수 패널 붙이기
      	vPane.setTopComponent(scorePanel);
      	vPane.setBottomComponent(editPanel);
	}
}