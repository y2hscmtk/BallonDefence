package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
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
	
	//버튼 아이콘

	private	ImageIcon inputButtonIcon = new ImageIcon("inputButton.png");
	private	ImageIcon inputButtonEnterdIcon = new ImageIcon("inputButtonEntered.png");
	
	
	
//	 //배경 이미지
//    private ImageIcon bgImageicon = new ImageIcon("background.png");
//    private Image backgroundPanelImage = bgImageicon.getImage();
//	
    
    //입력버튼 누를때의 이벤트 오버라이딩
    private class InputButtonClickedEvent extends ButtonClickedEvent{
    	private JTextField tf;
    	
    	
		public InputButtonClickedEvent(JTextField tf,GameFrame parent, ImageIcon enteredIcon, ImageIcon presentIcon) {
			super(parent, enteredIcon, presentIcon);
			this.tf = tf; //텍스트 영역에 대한 참고 가져오기
			// TODO Auto-generated constructor stub
		}
		
		
		@Override //마우스 클릭이벤트
		public void mouseClicked(MouseEvent e) {
			//단어 저장
			addWord(tf.getText()); //해당 단어를 저장한다
			tf.setText(""); //내용을 지운다.
		}
    	
    	
    }
    
    
	
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
  		tf.setFont(new Font("Gothic",Font.BOLD,30));
  		tf.setSize(550,50);
  		tf.setLocation(380,230);
  		add(tf);
  		
  		
  		//입력버튼 칸
  		InputButtonClickedEvent inputButtonClickedEvent = new InputButtonClickedEvent(tf,parent,inputButtonEnterdIcon,inputButtonIcon);
  		
  		
  		JLabel inputButton = new JLabel(inputButtonIcon);
  		inputButton.setSize(inputButtonIcon.getIconWidth(),inputButtonIcon.getIconHeight());
  		inputButton.setLocation(950,210);
  		inputButton.addMouseListener(inputButtonClickedEvent); //이벤트 달기
  		add(inputButton);
  		
  		
  		//현재 저장된 단어들을 스크롤을 통해 확인할수 있도록하는 창 생성
  		scrollList = new JList<String>(wordVector);
  		scrollPane = new JScrollPane(scrollList);
  		
  		scrollPane.setSize(700,400);
  		scrollPane.setLocation(380,315);
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
	       g.drawImage(backgroundImage, 0, 0,  backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight(),null); //이미지가 그려지는 시점 알림받지 않기
	    }
	    
}