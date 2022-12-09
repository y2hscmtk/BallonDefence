package game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

public class WordList {
	private Vector<String> wordVector = new Vector<String>();
   
	public WordList() {
		//파일이 없을경우에 대한 예외처리 필요
		Scanner scanner;
		try {
			scanner = new Scanner(new FileReader("words.txt"));
			while(scanner.hasNext()) {
				//한 라인씩 읽어오기 => words.txt의 한 라인에 한 단어 씩 존재함
				wordVector.add(scanner.nextLine()); 
			}
			scanner.close(); //스캐너 필요없어짐
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//파일이 없을경우에 대한 처리 구문
			e.printStackTrace();
		}
   
	}
   
	public String getWord() {
		int index = (int)(Math.random()*wordVector.size());
		return wordVector.get(index);
	}
}