package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class TestFrame extends JFrame{
	
	public void TestFrame() {
		System.out.println("새 프레임 생성");
		setSize(200,200);
		setLocation(200,200);
		setVisible(true); //생성된 순간 보이도록 하기
	}
}


public class BeginningPanel extends JPanel {
    private GameFrame parent;//부모 변수
    //배경 이미지
    private ImageIcon bgImageicon = new ImageIcon("background.png");
    private Image backgroundPanelImage = bgImageicon.getImage();
    //게임 시작 버튼(레이블) 이미지
    private ImageIcon selectLabelicon = new ImageIcon("goGame.png");
    //private Image gameStartLabelImage = selectLabelicon.getImage();
    //게임 설명 버튼(레이블) 이미지
    private ImageIcon ruleLabelIcon = new ImageIcon("goRule.png");
    //private Image ruleLabelImage = ruleLabelIcon.getImage();
    //랭킹 보기 버튼(레이블) 이미지
    private ImageIcon langkingLabelIcon = new ImageIcon("goRangking.png");
    //private Image showRangkingLabelImage = langkingLabelIcon.getImage();
    //단어 편집 버튼(레이블) 이미지
    private ImageIcon editLabelIcon = new ImageIcon("goEdit.png");
    //private Image EditButtonImage = editLabelIcon.getImage();
    
    
    TestFrame tf;
    
    
    
    public BeginningPanel(GameFrame parent) {
        this.parent = parent;//부모를 입력받아 변수에 저장
        
        setLayout(null); //배치 관리자 제거
        
        //4개의 버튼을 달고 있는 panel을 생성
        //1. 게임 시작 버튼
        //=> 버튼을 누르면 프레임의 패널을 선택 패널로 이동, 기존 패널은 프레임에서 제거
        JLabel startButtonLabel = new JLabel(selectLabelicon);
        startButtonLabel.setSize(selectLabelicon.getIconWidth(),selectLabelicon.getIconHeight());
        startButtonLabel.setLocation(398, 211);
        startButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.SELECT_PANEL));
        add(startButtonLabel);		
        
        //2. 규칙 설명 버튼
        //=> 버튼을 누르면 프레임의 패널을 규칙설명패널로 이동, 기존 패널은 프레임에서 제거
        JLabel ruleButtonLabel = new JLabel(ruleLabelIcon);
        ruleButtonLabel.setSize(ruleLabelIcon.getIconWidth(),ruleLabelIcon.getIconHeight());
        ruleButtonLabel.setLocation(398, 358);
        ruleButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.RULE_PANEL));
        add(ruleButtonLabel);

        //3. 단어 편집 버튼
        //=> 버튼을 누르면 프레임의 패널을 단어편집패널로 이동, 기존 패널은 프레임에서 제거
        JLabel wordEditButtonLabel = new JLabel(editLabelIcon);
        wordEditButtonLabel.setSize(editLabelIcon.getIconWidth(),editLabelIcon.getIconHeight());
        wordEditButtonLabel.setLocation(398, 498);
        wordEditButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.EDIT_PANEL));
        add(wordEditButtonLabel);

        //4. 순위 보기 버튼
        //=> 버튼을 누르면 프레임의 패널을 랭킹패널로 이동, 기존 패널은 프레임에서 제거
        JLabel showLankingButtonLabel = new JLabel(langkingLabelIcon);
        showLankingButtonLabel.setSize(editLabelIcon.getIconWidth(),editLabelIcon.getIconHeight());
        showLankingButtonLabel.setLocation(398, 643);
        showLankingButtonLabel.addMouseListener(new ButtonClickedEvent(parent, GameFrame.RANKING_PANEL));
        add(showLankingButtonLabel);

        
        //새로운 프레임 생성용 테스트 버튼
        JButton testButton = new JButton("프레임 생성");
        testButton.setSize(500, 100);
        testButton.setLocation(500, 100);
        testButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.out.println("버튼 눌려짐");
        		if(tf==null) {
        			tf = new TestFrame();
        			tf.setVisible(true);
        			tf.setSize(250,100);
        			tf.setLocation(200,200);
        		}
        		else {
        			System.out.println("이미 생성되어있음");
        		}
        		//new TestFrame(); // 버튼을 누른 순간 새로운 프레임이 생성됨
        	}
        });
        //add(testButton);
    }
    
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //그래픽 컴포넌트 설정
       //배경 이미지
       g.drawImage(backgroundPanelImage, 0, 0, this.getWidth(),this.getHeight(),null); //이미지가 그려지는 시점 알림받지 않기
    }
    
    
}