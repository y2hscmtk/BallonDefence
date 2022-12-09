package game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

//���̵� �Է��ϰ� ĳ���͸� �����ϴ� â
public class SelectPanel extends JPanel {
    private GameFrame parent;//�θ� ������ ����
    private ImageIcon icon = new ImageIcon("back.png");
    
    public SelectPanel(GameFrame parent) {
        this.parent = parent;//���� �θ� ���������� �����Ѵ�
        setLayout(null); //��ġ ������ ����
        this.setBackground(Color.gray);
        setSize(1500,900);
        //setBounds(0, 0, 1500,900);

        
        //�ڷΰ��� ��ư => �ٽ� 4���� �޴� â���� �ǵ��ư���.
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
