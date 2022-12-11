package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

//단어 편집 패널
public class WordEditPanel extends JPanel{
	private GameFrame parent;//부모 변수
	private ImageIcon backgroundIcon = new ImageIcon("wordEditPanelImage.png");
	private Image backgroundImage = backgroundIcon.getImage();
	
	
	public WordEditPanel (GameFrame parent) {
        this.parent = parent;//부모를 입력받아 변수에 저장
        setLayout(null); //배치 관리자 제거
        setBackground(Color.green);
        setSize(1500,900);
        //setBounds(0, 0, 1500,900);
        //순위 보기 버튼
        //=> 버튼을 누르면 프레임의 패널을 랭킹패널로 이동, 기존 패널은 프레임에서 제거
        JButton showLankingButton = new JButton("랭킹 보기");
        showLankingButton.setSize(500, 100);
        showLankingButton.setLocation(500, 575);
        showLankingButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) { //랭킹패널로 이동
        		parent.swapPanel(GameFrame.RANKING_PANEL);//부모의 컨텐트팬을 변경하도록 함수 호출
        	}
        });
        add(showLankingButton);
        

    }
	
	
	//배경 이미지 그리기
  	@Override
  	public void paintComponent(Graphics g) {
  		super.paintComponent(g); //그래픽 컴포넌트 설정
  		//배경 이미지
  		g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //이미지가 그려지는 시점 알림받지 않기
  	}
}