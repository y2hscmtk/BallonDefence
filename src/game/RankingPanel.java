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
	private GameFrame parent;//�θ� ������ ����
	private ImageIcon homeButtonIcon = new ImageIcon("back.png");
	private ImageIcon icon = new ImageIcon("rankingPanelImage.png");
	private Image backgroundImage = icon.getImage();
  
	public RankingPanel(GameFrame parent) {
		this.parent = parent;//���� �θ� ���������� �����Ѵ�
		setLayout(null); //��ġ ������ ����
		this.setBackground(Color.CYAN);
		setSize(1500,900);
		//2. ��Ģ ���� ��ư
		//=> ��ư�� ������ �������� �г��� ��Ģ�����гη� �̵�, ���� �г��� �����ӿ��� ����
		JButton backButton = new JButton(homeButtonIcon);
		backButton.setSize(homeButtonIcon.getIconWidth(),homeButtonIcon.getIconHeight());
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

	//��� �̹��� �׸���
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g); //�׷��� ������Ʈ ����
       //��� �̹���
       g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),null); //�̹����� �׷����� ���� �˸����� �ʱ�
    }
}