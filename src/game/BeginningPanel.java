package game;

import javax.swing.JButton;
import javax.swing.JPanel;

//시작 패널, 프레임에 붙임
//게임 타이틀 메뉴들을 보여줄 패널
public class BeginningPanel extends JPanel{
	
	
	public BeginningPanel() {
		
		
		//4개의 버튼을 달고 있는 panel을 생성
		//1. 게임 시작 버튼 
		//=> 버튼을 누르면 프레임의 패널을 선택 패널로 이동, 기존 패널은 프레임에서 제거
		JButton startButton = new JButton();
		startButton.setSize(100,10);
		startButton.setLocation(100,100);
		
		//2. 규칙 설명 버튼
		//=> 버튼을 누르면 프레임의 패널을 규칙설명패널로 이동, 기존 패널은 프레임에서 제거
		
		//3. 단어 편집 버튼
		//=> 버튼을 누르면 프레임의 패널을 단어편집패널로 이동, 기존 패널은 프레임에서 제거
		
		//순위 보기 버튼
		//=> 버튼을 누르면 프레임의 패널을 랭킹패널로 이동, 기존 패널은 프레임에서 제거
	}
	
}
