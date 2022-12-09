package game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

//아이디를 입력하고 캐릭터를 선택하는 창
public class SelectPanel extends JPanel {
    private GameFrame parent;//부모를 변수로 저장
    private ImageIcon icon = new ImageIcon("back.png");
    
    public SelectPanel(GameFrame parent) {
        this.parent = parent;//받은 부모를 전역변수로 저장한다
        setLayout(null); //배치 관리자 제거
        this.setBackground(Color.gray);
        setSize(1500,900);
        //setBounds(0, 0, 1500,900);

        
        //뒤로가기 버튼 => 다시 4가지 메뉴 창으로 되돌아간다.
        JButton backButton = new JButton(icon);
        backButton.setSize(icon.getIconWidth(),icon.getIconHeight());
        backButton.setLocation(500,325);
        backButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		parent.swapPanel(GameFrame.BEGINNING_PANEL);//부모의 컨텐트팬을 변경하기 위해 함수 호출
        	}
        });
        
        add(backButton);
        setVisible(true);
    }
}
