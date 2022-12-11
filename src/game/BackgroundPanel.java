package game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Image;

//��� �־��ִ� Ŭ����
public class BackgroundPanel extends JLabel {
    private String path;
    private Image image;
    public BackgroundPanel(String path){
        setImage(path);
        setVisible(true);
    }
    public void setImage(String path){
        this.path = path;
        try{
        	//����ο��� �̹��� �ҷ���
            image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage();
            repaint();
        }
        catch (Exception e){
            System.out.println("Failed to import image.");
            e.printStackTrace();
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image,0,0,getWidth(),getHeight(),null);
    }
}
