package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

//단어 편집 패널
public class WordEditPanel extends JPanel{
	private GameFrame parent;//부모 변수
	private ImageIcon homeButtonIcon = new ImageIcon("home.png");
	private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
	private ImageIcon backgroundIcon = new ImageIcon("wordEditPanelImage.png");
	private Image backgroundImage = backgroundIcon.getImage();
	
	private Vector<String> wordVector = new Vector<String>();
	
	private JList<String> scrollList; 
	private JScrollPane scrollPane;
	
	
	 //배경 이미지
    private ImageIcon bgImageicon = new ImageIcon("background.png");
    private Image backgroundPanelImage = bgImageicon.getImage();
	
	
	public WordEditPanel (GameFrame parent) {
        this.parent = parent;//부모를 입력받아 변수에 저장
        setLayout(null); //배치 관리자 제거
        setBackground(Color.green);
        setSize(1500,900);

      //뒤로가기 버튼 => 다시 4가지 메뉴 창으로 되돌아간다.
        //홈 버튼
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
        
        //words.txt파일 읽어와서 배열 생성
  		Scanner scanner;
  		try {
  			scanner = new Scanner(new FileReader("words.txt"));
  			while(scanner.hasNext()) {
  				//한 라인씩 읽어오기 => words.txt의 한 라인에 한 단어 씩 존재함
  				wordVector.add(scanner.nextLine()); 
  			}
  			scanner.close(); //스캐너 필요없어짐
  		} catch (FileNotFoundException e) {
  			// TODO Auto-generated catch block
  			//파일이 없을경우에 대한 처리 구문
  			e.printStackTrace();
  		}
  		
  		//단어를 입력받기 위한 텍스트 박스와 단어를 입력하기 위한 버튼 생성
  		JTextField tf = new JTextField(10);
  		tf.setSize(450,50);
  		tf.setLocation(500,280);
  		add(tf);
  		
  		JButton addButton = new JButton("입력");
  		addButton.setSize(130,50);
  		addButton.setLocation(970,280);
  		addButton.addMouseListener(new MouseAdapter() {
  			@Override
  			public void mouseClicked(MouseEvent e) {
  				addWord(tf.getText());
  				tf.setText(""); //텍스트 내용 지우기
  			}
  		});
  		add(addButton);
  		
  		
  		//현재 저장된 단어들을 스크롤을 통해 확인할수 있도록하는 창 생성
  		scrollList = new JList<String>(wordVector);
  		scrollPane = new JScrollPane(scrollList);
  		
  		scrollPane.setSize(450,350);
  		scrollPane.setLocation(500,400);
  		add(scrollPane);
  		
        
        setVisible(true);

    }
	
	

	//단어를 매개변수로 받아서 words.txt에 저장
	public void addWord(String word) {
		System.out.println(word +"저장중");
		try{
            File file = new File("words.txt");
            if (!file.exists()) //파일이 없다면 생성
                file.createNewFile();
            FileWriter fw = new FileWriter(file,true); //기존 파일에 이어쓰기
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("\n"+word); //다음줄에 단어 붙이기
            writer.close();
        }catch(IOException e){
        	 e.printStackTrace();
        }
	}
	
	
	 @Override
	    public void paintComponent(Graphics g) {
	       super.paintComponent(g); //그래픽 컴포넌트 설정
	       //배경 이미지
	       g.drawImage(backgroundPanelImage, 0, 0, bgImageicon.getIconWidth(),bgImageicon.getIconHeight(),null); //이미지가 그려지는 시점 알림받지 않기
	    }
	    
}