package game;

import javax.swing.*;
import java.awt.*;

//여러 패널을 돌려가며 사용자에게 게임을 조작할수 있는 프레임을 제공
//실질적인 조작은 패널에서 시행함
public class GameFrame extends JFrame {
	//사용자의 버튼 클릭에 따라 패널을 갈아끼며 다양한 메뉴를 볼 수 있도록
    public static final int BEGINNING_PANEL = 0; //시작 패널
    public static final int SELECT_PANEL = 1; //게임 패널
    public static final int RULE_PANEL = 2; //룰 설명 패널
    public static final int EDIT_PANEL = 3; //룰 설명 패널
    public static final int RANKING_PANEL = 4; //랭킹 보기 패널
    
    //swapPanel함수를 통해 패널을 갈아낄수 있도록 하기 위함
    //생성은 GameFrame의 생성자가 불려진 이후
    private SelectPanel selectPanel;
    private BeginningPanel beginningPanel;
    private WordEditPanel wordEditPanel;
    private RankingPanel rankingPanel;
    private RulePanel rulePanel;
    private GamePanel gamePanel;
    
    public GameFrame() {
    	setTitle("BallonDefense");
    	
    	//패널 생성 => 자기 자신을 생성자로 넘겨줘서 부모를 설정해줌
    	selectPanel = new SelectPanel(this);
        beginningPanel = new BeginningPanel(this);
        wordEditPanel = new WordEditPanel(this);
        rankingPanel = new RankingPanel(this);
        rulePanel = new RulePanel(this);
        
        setSize(1500, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x버튼을 눌러 프로그램을 종료하도록
        //프레임이 생성될 위치를 지정(게임 화면 위치)
        Dimension frameSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 - 프레임화면 세로) / 2
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        this.setResizable(false); //크기 조절 불가능하게
        setContentPane(beginningPanel); //컨텐트펜을 시작패널로 설정
        setVisible(true);
    }
    
    //사용자의 버튼 클릭에 따라 컨텐트펜을 다르게 붙여가며 화면을 변화시킨다.
    public void swapPanel(int type){
        //입력받은 변수에 맞춰 패널을 변경한다.
        switch (type){
            case BEGINNING_PANEL:
                setContentPane(beginningPanel);
                break;
            case RULE_PANEL:
                setContentPane(rulePanel);
                break;
            case SELECT_PANEL:
                setContentPane(selectPanel);
                break;
            case EDIT_PANEL:
                setContentPane(wordEditPanel);
                break;
            case RANKING_PANEL:
                setContentPane(rankingPanel);
                break;
        }
    }
}