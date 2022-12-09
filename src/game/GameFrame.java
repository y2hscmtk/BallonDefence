package game;

import javax.swing.*;
import java.awt.*;

//���� �г��� �������� ����ڿ��� ������ �����Ҽ� �ִ� �������� ����
public class GameFrame extends JFrame {
    public static final int BEGINNING_PANEL = 0; //���� �г�
    public static final int GAME_PANEL = 1; //���� �г�
    public static final int RULE_PANEL = 2; //�� ���� �г�
    public static final int EDIT_PANEL = 3; //�� ���� �г�
    public static final int RANKING_PANEL = 4; //��ŷ ���� �г�
    
    private GamePanel gamePanel;
    private BeginningPanel beginningPanel;
    private WordEditPanel wordEditPanel;
    private RankingPanel rankingPanel;
    private RulePanel rulePanel;
    
    public GameFrame() {
    	//�г� ���� => �ڱ� �ڽ��� �����ڷ� �Ѱ��༭ �θ� ��������
        gamePanel = new GamePanel(this);
        beginningPanel = new BeginningPanel(this);
        wordEditPanel = new WordEditPanel(this);
        rankingPanel = new RankingPanel(this);
        rulePanel = new RulePanel(this);
        
        setSize(1500, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x��ư�� ���� ���α׷��� �����ϵ���
        //������ ������ ��ġ�� ����
        Dimension frameSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // (�����ȭ�� ���� - ������ȭ�� ����) / 2, (�����ȭ�� ���� - ������ȭ�� ����) / 2
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        this.setResizable(false); //ũ�� ���� �Ұ����ϰ�
        setContentPane(beginningPanel); //����Ʈ���� �����гη� ����
        setVisible(true);
    }
    
    //���� �����츦 �����ϴ� �ڵ�
    public void swapPanel(int type){
        //�Է¹��� ������ ������ ������ Ÿ���� �����Ѵ�
        switch (type){
            case BEGINNING_PANEL:
                setContentPane(beginningPanel);//���� ����Ǿ� �ִ� beginningPanel�� ���� ����Ʈ�� ����
                break;
            case RULE_PANEL:
                setContentPane(rulePanel);//���� ����Ǿ� �ִ� gamePanel���� ���� ����Ʈ�� ����
                break;
            case GAME_PANEL:
                setContentPane(gamePanel);//���� ����Ǿ� �ִ� gamePanel���� ���� ����Ʈ�� ����
                break;
            case EDIT_PANEL:
                setContentPane(wordEditPanel);//���� ����Ǿ� �ִ� gamePanel���� ���� ����Ʈ�� ����
                break;
            case RANKING_PANEL:
                setContentPane(rankingPanel);//���� ����Ǿ� �ִ� gamePanel���� ���� ����Ʈ�� ����
                break;
        }
    }
}