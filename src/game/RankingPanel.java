package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class RankingPanel extends JPanel {
	private GameFrame parent;//부모를 변수로 저장
	private ImageIcon homeButtonIcon = new ImageIcon("back.png");
	private ImageIcon icon = new ImageIcon("rankingPanelImage.png");
	private Image backgroundImage = icon.getImage();
  
	public RankingPanel(GameFrame parent) {
		this.parent = parent;//받은 부모를 전역변수로 저장한다
		setLayout(null); //배치 관리자 제거
		this.setBackground(Color.CYAN);
		setSize(1500,900);
		//2. 규칙 설명 버튼
		//=> 버튼을 누르면 프레임의 패널을 규칙설명패널로 이동, 기존 패널은 프레임에서 제거
		JButton backButton = new JButton(homeButtonIcon);
		backButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
		backButton.setLocation(500,325);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.swapPanel(GameFrame.BEGINNING_PANEL);//부모의 컨텐트팬을 변경하기 위해 함수 호출
			}
		});
		
		add(backButton);
		setVisible(true);
	}

	//배경 이미지 그리기
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //그래픽 컴포넌트 설정
       //배경 이미지
       g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //이미지가 그려지는 시점 알림받지 않기
    }
}