package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BeginningPanel extends JPanel {
    private GameFrame parent;//부모 변수

    public BeginningPanel(GameFrame parent) {
        this.parent = parent;//부모를 입력받아 변수에 저장
        
        setLayout(null); //배치 관리자 제거
        
        
        //4개의 버튼을 달고 있는 panel을 생성
        //1. 게임 시작 버튼
        //=> 버튼을 누르면 프레임의 패널을 선택 패널로 이동, 기존 패널은 프레임에서 제거
        JButton startButton = new JButton("게임 시작");
        startButton.setSize(500, 100);
        startButton.setLocation(500, 200);
        startButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.GAME_PANEL);//부모의 컨텐트팬을 변경하도록 함수 호출
        	}
        });
        add(startButton);

        //2. 규칙 설명 버튼
        //=> 버튼을 누르면 프레임의 패널을 규칙설명패널로 이동, 기존 패널은 프레임에서 제거
        JButton ruleButton = new JButton("규칙 설명");
        ruleButton.setSize(500, 100);
        ruleButton.setLocation(500, 325);
        ruleButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.RULE_PANEL);//부모의 컨텐트팬을 변경하도록 함수 호출
        	}
        });
        add(ruleButton);

        //3. 단어 편집 버튼
        //=> 버튼을 누르면 프레임의 패널을 단어편집패널로 이동, 기존 패널은 프레임에서 제거
        JButton wordEditButton = new JButton("단어 편집");
        wordEditButton.setSize(500, 100);
        wordEditButton.setLocation(500, 450);
        wordEditButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.EDIT_PANEL);//부모의 컨텐트팬을 변경하도록 함수 호출
        	}
        });
        add(wordEditButton);

        //4. 순위 보기 버튼
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