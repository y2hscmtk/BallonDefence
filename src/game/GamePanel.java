package game;

import javax.swing.JPanel;

//������ �����ϴ� ����
//ǳ���� �޸� �ܾ���� �����ϰ� �ϴÿ��� ��������
//�˸��� �ؽ�Ʈ�� �Է��ϸ� �ش� ǳ���� ������.

//�����ʿ��� ����ڰ� ������ ĳ������ �̹�����
//����, ĳ������ ü��, ������� ���⸦ �����ִ� �г��� ����
//���ʿ��� ������ �����ϴ� �г��� ����.
public class GamePanel extends JPanel{
	//ĳ����
	private int characterType; //����ڰ� ������ ĳ���Ͱ� ���������� ����
	//ĳ������ ü��
	private int characterHealth = 100; //�⺻ ü���� 100
	//ĳ������ ����
	private int weaponType; //����ڰ� ������� ���Ⱑ �������� ���̵���	
	//������ ������
	private int weaponPower = 1; //�⺻ ������ �ɷ�ġ�� 1
	//ǳ���� �������� �ӵ� => ĳ���� Ư���� ���� �޶���
	private int ballonSpeed = 10;//�ð��� 10px�� �������°��� �⺻�ӵ�
	//�ٲٱ� ���õɰ�쿡 true�� ����
	private boolean luckyChance = false; //�ٲٲ����� Ư��, true��� ����Ȯ���� ǳ���� �ϳ� �� ��Ʈ��
	
	//ǳ���� �������� ������
	
	//�� ���尡 ������ ������ ǳ������ ��� ���ŵǾ����

	public GamePanel(int characterType) {
		setLayout(null); //ǳ���� �������� ��ġ�� �������� �����ϱ� ����
		//������ �����Ҷ��� ĳ���� ������ �Է¹޾Ƽ� ����
		this.characterType = characterType;
		
		//���õ� ĳ���Ϳ� ���� ���ӳ��̵� ����
		//0���� ���α�, 1���� �Ѽ�����, 3���� �ٲٿͱ��
		switch(characterType) {
		case 0:
			characterHealth = 150; //���α��� ĳ���� Ư��; ü���� ����(�ܴ��ϴ�)
			break;
		case 1:
			ballonSpeed = 7; //�Ѽ������� ĳ���� Ư��; ǳ���� �����Զ�������(��ü�÷�)
			break;
		case 2:
			luckyChance = true; //�ٲٱ���� ĳ���� Ư��; ����Ȯ��~
			break;
		}
		
		//�׽�Ʈ Ȯ�ο�
		System.out.println("���õ� ĳ���� : " + characterType + "");
		//ǳ���� �����ϰ� �����ϴ� ������ ����
		
		//ǳ���� �������� �ϴ� ������ ����
		
		
	}
	
}
