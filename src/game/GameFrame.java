package game;

import javax.swing.*;
import java.awt.*;

//여러 패널을 돌려가며 사용자에게 게임을 조작할수 있는 프레임을 제공
public class GameFrame extends JFrame {
    public static final int BEGINNING_PANEL = 0; //시작 패널
    public static final int GAME_PANEL = 1; //게임 패널
    public static final int RULE_PANEL = 2; //룰 설명 패널
    public static final int EDIT_PANEL = 3; //룰 설명 패널
    public static final int RANKING_PANEL = 4; //랭킹 보기 패널
    
    private GamePanel gamePanel;
    private BeginningPanel beginningPanel;
    private WordEditPanel wordEditPanel;
    private RankingPanel rankingPanel;
    private RulePanel rulePanel;
    
    public GameFrame() {
    	//패널 생성 => 자기 자신을 생성자로 넘겨줘서 부모를 설정해줌
        gamePanel = new GamePanel(this);
        beginningPanel = new BeginningPanel(this);
        wordEditPanel = new WordEditPanel(this);
        rankingPanel = new RankingPanel(this);
        rulePanel = new RulePanel(this);
        
        setSize(1500, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x버튼을 눌러 프로그램을 종료하도록
        //게임이 생성될 위치를 지정
        Dimension frameSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 - 프레임화면 세로) / 2
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        this.setResizable(false); //크기 조절 불가능하게
        setContentPane(beginningPanel); //컨텐트펜을 시작패널로 설정
        setVisible(true);
    }
    
    //현재 윈도우를 변경하는 코드
    public void swapPanel(int type){
        //입력받은 변수로 보여줄 윈도우 타입을 설정한다
        switch (type){
            case BEGINNING_PANEL:
                setContentPane(beginningPanel);//현재 저장되어 있는 beginningPanel로 현재 컨텐트팬 설정
                break;
            case RULE_PANEL:
                setContentPane(rulePanel);//현재 저장되어 있는 gamePanel으로 현재 컨텐트팬 설정
                break;
            case GAME_PANEL:
                setContentPane(gamePanel);//현재 저장되어 있는 gamePanel으로 현재 컨텐트팬 설정
                break;
            case EDIT_PANEL:
                setContentPane(wordEditPanel);//현재 저장되어 있는 gamePanel으로 현재 컨텐트팬 설정
                break;
            case RANKING_PANEL:
                setContentPane(rankingPanel);//현재 저장되어 있는 gamePanel으로 현재 컨텐트팬 설정
                break;
        }
    }
}