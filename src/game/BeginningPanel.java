package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BeginningPanel extends JPanel {
    private GameFrame parent;//�θ� ����

    public BeginningPanel(GameFrame parent) {
        this.parent = parent;//�θ� �Է¹޾� ������ ����
        
        setLayout(null); //��ġ ������ ����
        
        
        //4���� ��ư�� �ް� �ִ� panel�� ����
        //1. ���� ���� ��ư
        //=> ��ư�� ������ �������� �г��� ���� �гη� �̵�, ���� �г��� �����ӿ��� ����
        JButton startButton = new JButton("���� ����");
        startButton.setSize(500, 100);
        startButton.setLocation(500, 200);
        startButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.GAME_PANEL);//�θ��� ����Ʈ���� �����ϵ��� �Լ� ȣ��
        	}
        });
        add(startButton);

        //2. ��Ģ ���� ��ư
        //=> ��ư�� ������ �������� �г��� ��Ģ�����гη� �̵�, ���� �г��� �����ӿ��� ����
        JButton ruleButton = new JButton("��Ģ ����");
        ruleButton.setSize(500, 100);
        ruleButton.setLocation(500, 325);
        ruleButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.RULE_PANEL);//�θ��� ����Ʈ���� �����ϵ��� �Լ� ȣ��
        	}
        });
        add(ruleButton);

        //3. �ܾ� ���� ��ư
        //=> ��ư�� ������ �������� �г��� �ܾ������гη� �̵�, ���� �г��� �����ӿ��� ����
        JButton wordEditButton = new JButton("�ܾ� ����");
        wordEditButton.setSize(500, 100);
        wordEditButton.setLocation(500, 450);
        wordEditButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.EDIT_PANEL);//�θ��� ����Ʈ���� �����ϵ��� �Լ� ȣ��
        	}
        });
        add(wordEditButton);

        //4. ���� ���� ��ư
        //=> ��ư�� ������ �������� �г��� ��ŷ�гη� �̵�, ���� �г��� �����ӿ��� ����
        JButton showLankingButton = new JButton("��ŷ ����");
        showLankingButton.setSize(500, 100);
        showLankingButton.setLocation(500, 575);
        showLankingButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) { //��ŷ�гη� �̵�
        		parent.swapPanel(GameFrame.RANKING_PANEL);//�θ��� ����Ʈ���� �����ϵ��� �Լ� ȣ��
        	}
        });
        add(showLankingButton);
    }
}