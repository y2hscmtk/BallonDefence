package game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

//��Ģ�� �������ִ� �г�
public class RulePanel extends JPanel {
	private GameFrame parent;//�θ� ������ ����
    private ImageIcon icon = new ImageIcon("back.png");
    
    public RulePanel(GameFrame parent) {
        this.parent = parent;//���� �θ� ���������� �����Ѵ�
        setLayout(null); //��ġ ������ ����
        this.setBackground(Color.white);
        setSize(1500,900);
        //setBounds(0, 0, 1500,900);
        //2. ��Ģ ���� ��ư
        //=> ��ư�� ������ �������� �г��� ��Ģ�����гη� �̵�, ���� �г��� �����ӿ��� ����
        JButton backButton = new JButton(icon);
        backButton.setSize(icon.getIconWidth(),icon.getIconHeight());
        backButton.setLocation(500,325);
        backButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.BEGINNING_PANEL);//�θ��� ����Ʈ���� �����ϱ� ���� �Լ� ȣ��
        	}
        });
        
        add(backButton);
        setVisible(true);
    }
}

