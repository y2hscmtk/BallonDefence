package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


//아이디를 입력하고 캐릭터를 선택하는 창 => 아이디 입력은 게임 끝나고 받는걸로 변경
//사용자의 입력에 맞춰서 게임을 생성한다.
public class SelectPanel extends JPanel {
    private GameFrame parent;//부모를 변수로 저장
   
    
    //홈버튼에 사용알 아이콘
    private ImageIcon homeButtonIcon = new ImageIcon("home.png");
    private ImageIcon homeButtonEnteredIcon = new ImageIcon("homeEntered.png");
    
    private ImageIcon sangsangBugiIcon = new ImageIcon("sangsangbugi.png");  //상상부기 아이콘
    private ImageIcon hansungNyanIicon = new ImageIcon("hansungNyang-i.png"); //한성냥이 아이콘
    private ImageIcon kkokkokkukkuIcon = new ImageIcon("kkokkokkukku.png"); //꼬꼬&꾸꾸 아이콘
    
    //마우스가 올라갔을때 캐릭터 버튼의 변화를 주기 위해
    private ImageIcon enteredBugi = new ImageIcon("sangsangbugiEntered.png");
    private ImageIcon enteredHansungNyangi = new ImageIcon("hansungNyang-iEntered.png");
    private ImageIcon enteredKKKK = new ImageIcon("kkokkokkukkuEntered.png");
    
    //배경 이미지
    private ImageIcon selectBackgroundImageicon = new ImageIcon("selectBackgroundImage.png");
    private Image selectBackgroundImage = selectBackgroundImageicon.getImage();
    
//    private ImageIcon bgImageicon = new ImageIcon("background.png");
//    private Image backgroundPanelImage = bgImageicon.getImage();
//    
    
    private int selectType; //사용자가 선택한 캐릭터 저장
//    private boolean flag = false; //사용자가 캐릭터 선택을 했는지 확인하는 용도
    
    //캐릭터 버튼을 클릭했을때 발생할 이벤트
    //생성자로 선택한 캐릭터 타입을 입력받아서 게임패널을 생성후 패널을 변경한다.
    //기존 버튼 클릭 이벤트를 오버라이딩해서 작성
    private class CharacterSelectButtonClickedEvent extends ButtonClickedEvent{

//    	
    	public CharacterSelectButtonClickedEvent(GameFrame parent,int characterType,ImageIcon enteredIcon,ImageIcon presentIcon) {
    		super(parent,characterType,enteredIcon,presentIcon); //부모생성자에 넘겨줌

    	}
    	
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		GamePanel game = new GamePanel(getType(),parent); //상상부기(코드0)로 게임 생성
    		
    		//현재 진행중인 음악 종료
    		//parent.getMusicThread()
    		parent.getMusic().musicStop(); //음악 중단
    		//음악 중단 사실을 저장
//    		parent.
    		
    		//게임 음악으로 음악 변경 => 현재 음악 플레이 상태 true로 변경해야함(비기닝 패널?)
    		parent.getMusic().changeMusic("gameMusic.wav");
    		
    		parent.setContentPane(game);
    		//parent.swapPanel(GameFrame.BEGINNING_PANEL);//부모의 컨텐트팬을 변경하도록 함수 호출
    	}
    	
    	
//    	@Override //마우스가 컴포넌트 위에 올라갈때의 이벤트
//    	public void mouseEntered(MouseEvent e) {
//    		JLabel label = (JLabel)(e.getComponent()); //이벤트가 발생한 라벨을 가져옴
//    		label.setIcon(enteredIcon); //마우스가 올라갈때의 이미지로 변경
//    	}
//    	
//    	@Override //마우스가 컴포넌트 위를 벗어날때 이벤트
//    	public void mouseExited(MouseEvent e) {
//			JLabel label = (JLabel)(e.getComponent()); //이벤트가 발생한 라벨을 가져옴
//			label.setIcon(presentIcon); //마우스가 올라갈때의 이미지로 변경
//    	}
    	
    }
    
    
    public SelectPanel(GameFrame parent) {
        this.parent = parent;//받은 부모를 전역변수로 저장한다
        setLayout(null); //배치 관리자 제거
        this.setBackground(Color.gray);
        setSize(1500,900);

        //뒤로가기 버튼 => 다시 4가지 메뉴 창으로 되돌아간다.
        //홈 버튼
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
        

  		//캐릭터 버튼 이벤트 => 버튼 클릭 이벤트를 오버라이딩해서 캐릭터 선택하는 부분만 추가로 삽입?
        
        //상상부기 이미지버튼
        JLabel sangsangBugiLabel = new JLabel(sangsangBugiIcon);
        sangsangBugiLabel.setSize(sangsangBugiIcon.getIconWidth(),sangsangBugiIcon.getIconHeight());
        sangsangBugiLabel.setLocation(310, 230);
        sangsangBugiLabel.addMouseListener(new CharacterSelectButtonClickedEvent(parent,0,enteredBugi,sangsangBugiIcon));
        add(sangsangBugiLabel);
        		
        //한성냥이 이미지버튼
        JLabel hansungNyanILabel = new JLabel(hansungNyanIicon);
        hansungNyanILabel.setSize(hansungNyanIicon.getIconWidth(),hansungNyanIicon.getIconHeight());
        hansungNyanILabel.setLocation(610, 230);
        hansungNyanILabel.addMouseListener(new CharacterSelectButtonClickedEvent(parent,1,enteredHansungNyangi,hansungNyanIicon));
        add(hansungNyanILabel);


        //꼬꼬꾸꾸 이미지버튼
        JLabel kkokkokkukkuLabel = new JLabel(kkokkokkukkuIcon);
        kkokkokkukkuLabel.setSize(kkokkokkukkuIcon.getIconWidth(),kkokkokkukkuIcon.getIconHeight());
        kkokkokkukkuLabel.setLocation(910, 230);
        kkokkokkukkuLabel.addMouseListener(new CharacterSelectButtonClickedEvent(parent,2, enteredKKKK, kkokkokkukkuIcon)); 
        add(kkokkokkukkuLabel);


        
        setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //그래픽 컴포넌트 설정
       //배경 이미지
       g.drawImage(selectBackgroundImage, 0, 0,selectBackgroundImageicon.getIconWidth(),selectBackgroundImageicon.getIconHeight(),null); //이미지가 그려지는 시점 알림받지 않기
    }
}
