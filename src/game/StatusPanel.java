package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



//캐릭터의 현재 정보를 보여주기 위한 패널
//현재 체력,점수,코인,등을 관리하고 화면에 출력한다.
public class StatusPanel extends JPanel{
	private GameFrame parent;
	
	//캐릭터에 따른 게임 상황을 변경하기 위한 변수들
	private int characterHealth;
	private JLabel healthLabel; 
	
	private int weaponType = 1; //캐릭터의 무기, 사용자가 사용중인 무기가 무엇인지 보이도록,기본무기는 1번무기
	
	//무기에 대한 아이템 코드번호
	private final static int PENCIL = 1;
	private static final int SCISSORS = 2;
	private static final int CHAINSAW = 3;
	
	private int weaponPower = 1; // 무기의 데미지, 기본 무기의 능력치는 1
	
	
	//캐릭터들의 이미지 정보
	private ImageIcon sangsangBugi = new ImageIcon("character0.png");
    private ImageIcon hansungNyangI = new ImageIcon("character1.png");
    private ImageIcon kkukkuKkakka = new ImageIcon("character2.png");
	private ImageIcon selectedCharacater;
	
	//코인 아이콘, 엑스 아이콘
	private ImageIcon coinIcon = new ImageIcon("coinImage.png");
	private ImageIcon xIcon = new ImageIcon("xImage.png");
	//체력바 아이콘(하나당 체력5)
	private ImageIcon healthBarIcon = new ImageIcon("healthBar.jpg");
	//무기 아이콘
	private ImageIcon pencilIcon = new ImageIcon("pencil.png"); //연필 이미지 아이콘
	private ImageIcon chainsawIcon = new ImageIcon("chainsaw.png"); //톱 이미지 아이콘
	private ImageIcon scissorsIcon = new ImageIcon("scissors.png"); //가위 이미지 아이콘
	
	//음악 아이콘
	private ImageIcon soundLabelIcon = new ImageIcon("music.png");
    private ImageIcon soundLabelMuteIcon = new ImageIcon("mute.png");
	
	
	
	//무기 이미지를 저장
	private JLabel weaponLabel; 
	
	
	//게임에 대한 정보
	private int score = 0; //점수를 저장할 변수
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private int coin = 0; //코인을 저장할 변수 => 상점에 이용
	private JLabel coinLabel = new JLabel(Integer.toString(coin));

	
	
	//체력바의 이미지라벨들을 관리하기 위한 벡터 => 캐릭터마다 체력이 다르므로
	private Vector<JLabel> healthBarVector = new Vector<JLabel>();
	private int healthBarIndex=0;
	
	
	
	
	//게임음악을 도중에 키고 끌수있도록 버튼에 달아줄 이벤트
	//음악을 관리하기 위해 기존에 작성한 버튼클릭이벤트를 오버라이딩하여 새로운 기능 작성
    private class SoundButtonClickedEvent extends ButtonClickedEvent{
    	private GameFrame parent;
    	
    	public SoundButtonClickedEvent(GameFrame parent,ImageIcon enteredIcon, ImageIcon presentIcon) {
    		super(parent,enteredIcon, presentIcon); //부모 생성자에 넘겨준다.
    		// TODO Auto-generated constructor stub
    		this.parent = parent;
    	}


    	@Override //마우스가 올라갔을때의 이벤트를 무시하기 위해 오버라이딩
    	public void mouseEntered(MouseEvent e) {
			
    	}
    	
    	
    	@Override //마우스가 클릭되었을때 현재 음악이 작동중인지 확인하여 음악을 키고 끔
    	public void mouseClicked(MouseEvent e) {
    		JLabel label = (JLabel)(e.getComponent()); //이벤트가 발생한 라벨을 가져옴
    		if(parent.isMusicOn()) {//현재 음악이 재생중이라면
    			System.out.println("음악 재생중 -> 중지");
    			label.setIcon(getEnteredIcon()); //x표시로 변경
    			parent.getMusic().musicStop();
    			parent.setMusicOnOff(false); //음악 꺼진상태로 표시
    		}
    			
    		else {//현재 음악이 꺼져있는 상태라면
    			System.out.println("음악 정지 -> 재생");
    			label.setIcon(getPresentIcon()); //음표 표시로 변경
    			parent.getMusic().resumeMusic();
//    			parent.getMusic().start();//멈춘 이후부터 음악재생
    			parent.setMusicOnOff(true); //음악 켜진상태로 표시
    		}
    	}
    }
	
	
	
	//선택한 캐릭터가 무엇인지에 대한 정보
	//캐릭터의 체력 정보
	//캐릭터의 무기 정보, 무기들 이미지
	//캐릭터 점수 정보
	//코인 정보(얼마를 벌었는지)
    
	//getter함수 작성 => 쓸일 없으면 제거
	public int getHealth() {
		return characterHealth;
	}
	
	public int getScore() {
		return score;
	}
	
	//상점으로 현재 코인정보를 넘겨줌
	public int getCoin() {
		return coin;
	}
	
	//무기의 공격력을 리턴
	public int getWeaponPower() {return weaponPower;}
	
	//선택된 무기로 무기 이미지 변경 => 현재 무기 데미지변경
	public void setWeapon(int selectedItem) {
		weaponType = selectedItem; //입력받은 아이템으로 현재 무기 변경
		//1은 연필, 2는 가위, 3은 톱
		//무기 타입에 맞는 무기 이미지, 무기 데미지 변경
		switch(weaponType) {
		case PENCIL:
			System.out.println("연필로 이미지 변경");
			weaponLabel.setIcon(pencilIcon);
			weaponPower = 1;
			break;
		case SCISSORS: 
			System.out.println("가위로 이미지 변경");
			weaponLabel.setIcon(scissorsIcon);
			repaint();
			weaponPower = 2;
			break;
		case CHAINSAW:
			System.out.println("톱으로 이미지 변경");
			weaponLabel.setIcon(chainsawIcon);
			weaponPower = 3;
			break;
		}
	}
	
	public StatusPanel(int characterType, GameFrame parent) {
		this.parent = parent;
		setLayout(null);
		setSize(500,900);
		
		//점수 보여주기
		add(new JLabel("SCORE"));
		scoreLabel.setSize(300,300);
		scoreLabel.setLocation(0,0);
		//JLabel textLabel = new JLabel("점수");
		//add(textLabel);
		add(scoreLabel);
		
		
		//소리 켜기&끄기 아이콘
		JLabel soundButtonLabel = new JLabel(soundLabelIcon);
        soundButtonLabel.setSize(soundLabelIcon.getIconWidth(),soundLabelIcon.getIconHeight());
        soundButtonLabel.setLocation(20, 20);
        soundButtonLabel.addMouseListener(new SoundButtonClickedEvent(parent,soundLabelMuteIcon,soundLabelIcon));
        		
//        		new ButtonClickedEvent(parent, GameFrame.SELECT_PANEL,soundLabelMuteIcon,soundLabelIcon));
        add(soundButtonLabel);
		

		//코인 아이콘& x아이콘
		JLabel coinImageLabel = new JLabel(coinIcon);
		coinImageLabel.setSize(coinIcon.getIconWidth(),coinIcon.getIconHeight());
		coinImageLabel.setLocation(140,480);
		coinImageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				healthRecovery(30); //누를때마다 30의 체력이 회복
			}
		});
		coinLabel.setSize(300,300);
		coinLabel.setLocation(280,350);
		add(coinLabel);
		
		
		JLabel xImageLabel = new JLabel(xIcon);
		xImageLabel.setSize(xIcon.getIconWidth(),xIcon.getIconHeight());
		xImageLabel.setLocation(200,500);
		add(coinImageLabel);
		add(xImageLabel);
		
		
		
		
		
		
		
		System.out.println("스테이터스 생성자 호출");
		setBackground(Color.GREEN);
		//캐릭터 선택정보를 토대로 띄울 이미지 결정
		switch(characterType) {
		case 0: //상상부기의 경우
			characterHealth = 150; //150의 체력으로 시작
			selectedCharacater = sangsangBugi;
			break;
		case 1: //한성냥이의 경우
			characterHealth = 90; //90의 체력으로 시작
			selectedCharacater = hansungNyangI;
			break;
		case 2: //꾸꾸와 까까의 경우
			characterHealth = 80; //80의 체력으로 시작
			selectedCharacater = kkukkuKkakka;
			break;
		}
		//healthBarIndex = characterHealth/10*2; //체력바의 개수 지정=>벡터 조작시 사용
		int i=0;
//		//체력바 생성 => 체력을 10나누고 2를 곱한만큼 생성=> 체력이 150이면 30개 생성(체력바 1개당 체력5를 의미)
		for(i=0;i<(characterHealth/10*2);i++) {
//			System.out.println("체력바 추가"); 확인용
			JLabel healthBar = new JLabel(healthBarIcon);
			healthBar.setSize(healthBarIcon.getIconWidth(),healthBarIcon.getIconHeight());
			healthBar.setLocation(64+(i*10),420);
			add(healthBar);
			healthBarVector.add(healthBar);
		}
		
		healthBarIndex = 64+(i*10); //가장 오른쪽 위치를 가리키게함
//		int x = healthBarVector.get(healthBarVector.size()-1).getX();
//		int y = healthBarVector.get(healthBarVector.size()-1).getY();
//		lastHealthBarPosition.x = x;
//		lastHealthBarPosition.y = y;
		//오른쪽 끝의 체력바의 위치 저장 => 체력추가 등에 사용하기 위해
		
		
		//체력 텍스트 표시
		healthLabel = new JLabel(Integer.toString(characterHealth));
		healthLabel.setSize(200,200);
		healthLabel.setLocation(65,500);
		add(healthLabel);
//		JLabel  = new JLabel("체력 : "+Integer.toString(characterHealth));
//		health.setSize(200,200);
//		health.setLocation(65,500);
//		add(health);

		//캐릭터 아이콘
		JLabel character = new JLabel(selectedCharacater);
		character.setSize(selectedCharacater.getIconWidth(),selectedCharacater.getIconHeight());
		character.setLocation(65,140);
		add(character);
		
		
		//무기 아이콘 보이게
		//초기 무기 설정 => 초기 무기는 연필
		weaponLabel = weaponLabel = new JLabel(pencilIcon);
		weaponLabel.setSize(pencilIcon.getIconWidth(),pencilIcon.getIconHeight());
		weaponLabel.setLocation(65,560);
		add(weaponLabel);
		
		
		
		
		
		
		
		
		setVisible(true);
	}
	
	//캐릭터에게 데미지를 가하는 메소드 
	//=> 풍선이 떨어질때 10의 데미지를 가한다.
	public void getDamage(int damage) {
		//데미지 % 5만큼의 체력바를 없애야함 
		//=> 남아있는 체력바가 5인데,10을 깎을경우에 대한예외처리 필요
		System.out.println("현재 체력"+characterHealth);
		System.out.println("체력바 체력"+healthBarVector.size()*5);
		//가장 끝에 있는 체력바 라벨에 대한 참조를 가져와서 visible을 fasle로 변경
		//오른쪽 끝의 체력바 라벨에 대한 참조를 가져옴
		//데미지만큼 지워야 하므로 반복문 이용
		for(int i=0;i<damage/5;i++) {
			if(characterHealth<=5)
				break; //체력이 5일때 10의 데미지를 주는 경우에 대해서
			//현재 체력바의 가장 오른쪽 체력바라벨의 위치를 저장해둔다.
			JLabel finalHealthBar = healthBarVector.get(healthBarVector.size()-1); 
			finalHealthBar.setVisible(false); //안보이게 만들고
			healthBarVector.remove(healthBarVector.size()-1); //벡터에서도 제거
			//지워지고 난뒤, 가장 오른쪽 끝의 체력바라벨의 위치를 가져온다.
			healthBarIndex = healthBarVector.get(healthBarVector.size()-1).getX()+10;
		}
//		//characterHealth/10*2 => 체력바의 개수
//		healthBarVector.remove((characterHealth/10*2));
//		
		characterHealth -= damage;
		//변경된 체력 표시
		healthLabel.setText(Integer.toString(characterHealth));
	}
	
	//캐릭터의 체력을 더한다.
	public void healthRecovery(int pHealth) {
		//플러스된 체력수만큼 체력바를 더해준다.
		int i=0;
		for(i=0;i<(pHealth/10*2);i++) {
//			System.out.println("체력바 추가");
			JLabel healthBar = new JLabel(healthBarIcon);
			healthBar.setSize(healthBarIcon.getIconWidth(),healthBarIcon.getIconHeight());
			//이전 체력바의 x좌표를 얻을방법 고안
			healthBar.setLocation(healthBarIndex+i*10,420);
			add(healthBar);
			repaint(); //그려진것이 보여지도록
			healthBarVector.add(healthBar);
		}
		//x좌표 값 변경
		healthBarIndex = healthBarIndex+(i-1)*10;
		
		characterHealth += pHealth;
		//변경된 체력 표시
		healthLabel.setText(Integer.toString(characterHealth));
	}
	
	
	//점수를 추가하는 메소드
	public void plusScore(int pScore) {
		score += pScore; //점수 추가
		//변경된 점수 표시
		System.out.println(pScore+"점 추가");
		scoreLabel.setText(Integer.toString(score));
	}	
	
	
	//코인을 추가하는 메소드
	public void plusCoin(int pCoin) {
		coin += pCoin; //점수 추가
		//변경된 코인 표시
		System.out.println(pCoin+"원 추가");
		coinLabel.setText(Integer.toString(coin));
	}
	
	
	//코인을 빼는 메소드 => 물건구매
	public void minusCoin(int mCoin) {
		System.out.println(mCoin +"차감");
		coin -= mCoin; //점수 추가
		//변경된 코인 표시
		coinLabel.setText(Integer.toString(coin));
	}
	
	
	
}
