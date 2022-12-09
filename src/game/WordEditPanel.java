package game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class WordEditPanel extends JPanel{
	private GameFrame parent;//부모 변수
	
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
}