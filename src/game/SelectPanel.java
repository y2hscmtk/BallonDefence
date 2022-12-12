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
    private ImageIcon bgImageicon = new ImageIcon("background.png");
    private Image selectBackgroundImage = bgImageicon.getImage();
    
//    private ImageIcon bgImageicon = new ImageIcon("background.png");
//    private Image backgroundPanelImage = bgImageicon.getImage();
//    
    
    private int selectType; //사용자가 선택한 캐릭터 저장
//    private boolean flag = false; //사용자가 캐릭터 선택을 했는지 확인하는 용도
    
    //캐릭터 버튼을 클릭했을때 발생할 이벤트
    //생성자로 선택한 캐릭터 타입을 입력받아서 게임패널을 생성후 패널을 변경한다.
    private class CharacterSelectEvent extends MouseAdapter{
    	private int characterType;
    	private ImageIcon enteredIcon; //마우스가 올라갔을때 보여지도록 할 이미지
    	private ImageIcon presentIcon; //컴포넌트의 현재 이미지 아이콘
    	
    	public CharacterSelectEvent(int characterType,ImageIcon enteredIcon,ImageIcon presentIcon) {
    		this.enteredIcon = enteredIcon;
    		this.presentIcon = presentIcon;
    		this.characterType = characterType;
    	}
    	
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		GamePanel game = new GamePanel(characterType,parent); //상상부기(코드0)로 게임 생성
    		
    		//현재 진행중인 음악 종료
    		//parent.getMusicThread()
    		parent.getMusic().musicStop(); //음악 중단
    		
    		parent.setContentPane(game);
    		//parent.swapPanel(GameFrame.BEGINNING_PANEL);//부모의 컨텐트팬을 변경하도록 함수 호출
    	}
    	
    	
    	@Override //마우스가 컴포넌트 위에 올라갈때의 이벤트
    	public void mouseEntered(MouseEvent e) {
    		JLabel label = (JLabel)(e.getComponent()); //이벤트가 발생한 라벨을 가져옴
    		label.setIcon(enteredIcon); //마우스가 올라갈때의 이미지로 변경
    	}
    	
    	@Override //마우스가 컴포넌트 위를 벗어날때 이벤트
    	public void mouseExited(MouseEvent e) {
			JLabel label = (JLabel)(e.getComponent()); //이벤트가 발생한 라벨을 가져옴
			label.setIcon(presentIcon); //마우스가 올라갈때의 이미지로 변경
    	}
    	
    }
    
    
    public SelectPanel(GameFrame parent) {
        this.parent = parent;//받은 부모를 전역변수로 저장한다
        setLayout(null); //배치 관리자 제거
        this.setBackground(Color.gray);
        setSize(1500,900);
        //setBounds(0, 0, 1500,900);
        //System.out.println(this.getParent().getName());
        //뒤로가기 버튼 => 다시 4가지 메뉴 창으로 되돌아간다.
        //홈 버튼
  		JLabel homeButton = new JLabel(homeButtonIcon);
  		homeButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
  		homeButton.setLocation(1360,20);
  		homeButton.addMouseListener(new ButtonClickedEvent(parent,parent.BEGINNING_PANEL,homeButtonEnteredIcon,homeButtonIcon));
  		add(homeButton);
        
  	
  		
        //사용자로부터 입력을 받는 부분
        //텍스트 박스와 레이블 3개가 필요(상상부기, 한성냥이, 꼬꼬&꾸꾸 캐릭터 선택창)
        
//        //아이디를 입력받는 창
//        JTextField id = new JTextField(20); 
//        id.setSize(200,10);
//        id.setLocation(500,500);//임시 위치
//        add(id);
        
        //캐릭터 선택창 => 한 캐릭터가 선택되었다면 다시 클릭하기 전까지, 다른 캐릭터는 선택할수 없도록 해야함
        //캐릭터 선택 이후, 다시 해당 캐릭터를 클릭하였다면 선택정보가 리셋되고, 다른 캐릭터를 선택할수 있도록
        
  		//캐릭터 버튼 이벤트 => 버튼 클릭 이벤트를 오버라이딩해서 캐릭터 선택하는 부분만 추가로 삽입?
        
        //상상부기 이미지버튼
        JLabel sangsangBugiLabel = new JLabel(sangsangBugiIcon);
        sangsangBugiLabel.setSize(sangsangBugiIcon.getIconWidth(),sangsangBugiIcon.getIconHeight());
        sangsangBugiLabel.setLocation(310, 270);
        sangsangBugiLabel.addMouseListener(new CharacterSelectEvent(0,enteredBugi,sangsangBugiIcon));
        add(sangsangBugiLabel);
        		
//        		MouseAdapter() {
//        	@Override
//        	public void mouseClicked(MouseEvent e) {
//        		GamePanel game = new GamePanel(0); //상상부기(코드0)로 게임 생성
//        		parent.setContentPane(game);
//        		//parent.swapPanel(GameFrame.BEGINNING_PANEL);//부모의 컨텐트팬을 변경하도록 함수 호출
//        	}
//        });
        
        
        //한성냥이 이미지버튼
        JLabel hansungNyanILabel = new JLabel(hansungNyanIicon);
        hansungNyanILabel.setSize(hansungNyanIicon.getIconWidth(),hansungNyanIicon.getIconHeight());
        hansungNyanILabel.setLocation(610, 270);
        hansungNyanILabel.addMouseListener(new CharacterSelectEvent(1,enteredHansungNyangi,hansungNyanIicon));
        add(hansungNyanILabel);


        //꼬꼬꾸꾸 이미지버튼
        JLabel kkokkokkukkuLabel = new JLabel(kkokkokkukkuIcon);
        kkokkokkukkuLabel.setSize(kkokkokkukkuIcon.getIconWidth(),kkokkokkukkuIcon.getIconHeight());
        kkokkokkukkuLabel.setLocation(910, 270);
        kkokkokkukkuLabel.addMouseListener(new CharacterSelectEvent(2, enteredKKKK, kkokkokkukkuIcon)); 
        add(kkokkokkukkuLabel);

        //캐릭터를 선택하면 바로 넘어가게 할것인가, 버튼을 눌러 게임을 진행할것인가 고려
//        JButton startButton = new JButton("게임시작"); 
//        startButton.setSize(100,100);
//        startButton.setLocation(800,800);
//        startButton.addMouseListener(new MouseAdapter() {
//        	//버튼을 클릭했을때 사용자가 캐릭터와 아이디를 입력했는지 확인하고,
//        	//입력하지 않았다면 경고창을 띄우고
//        	//입력하였다면 입력정보를 바탕으로 게임을 생성한다.
//        	@Override
//        	public void mouseClicked(MouseEvent e) {
//        		//게임 패널 생성후 해당 패널로 갈아끼우기
//        		
//        		
////        		parent.setContentPane(new );
//        	}
//        });
//        add(startButton);
        
        
        setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //그래픽 컴포넌트 설정
       //배경 이미지
       g.drawImage(selectBackgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //이미지가 그려지는 시점 알림받지 않기
    }
}
