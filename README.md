# JAVA Project

##### : 풍선 디펜스(Balloon Defense)



![image-20221215052409748](https://user-images.githubusercontent.com/109474668/208294221-b5afeccb-3f6a-430d-9555-7c2a84701c71.png)




풍선 디펜스는 풍선타워디펜스에서 영감을 받아 제작한 게임이다.

플레이어는 하늘에서 떨어지는 형형색색의 풍선을, 풍선 밑에 적힌 단어를 올바르게 입력하여 터뜨려야 한다.



![rule3](https://user-images.githubusercontent.com/109474668/208294264-c11a016d-b047-4828-b959-dac0f3722095.png)






풍선을 터뜨리지 못하고 풍선이 땅에 떨어지게 되면 체력을 잃게된다.

풍선은 색깔별로 노랑(3) 파랑(2) 빨강(1) 의 체력을 갖는데, 이는 타이핑의 횟수와 비례한다.

게임의 라운드는 총 3라운드로 구성되어있으며, 라운드가 진행될수록 색깔풍선의 등장 확률이 높아지므로 라운드 종료 이후 상점창에서 무기를 강화해야 한다.

상점의 무기는 현재 보유하고 있는 코인을 기준으로 무기를 구매할수있는지 없는지에 따라 보여지며, 체력회복 물약은 상시 판매된다.

![image-20221216170240142](https://user-images.githubusercontent.com/109474668/208294280-15651c45-8892-4634-ad59-6c26264d8798.png)





상점에서 구매 가능한 아이템의 구체적인 정보는 다음과 같다.



![scissors](https://user-images.githubusercontent.com/109474668/208294302-e059e6d8-89a5-4fe7-86ed-24170bc65035.png)

​																	  가위 :  2의 공격력을 갖는다(파랑풍선도 한번에 처치 가능하다.) 1000원





![chainsaw](https://user-images.githubusercontent.com/109474668/208294323-8e1b8578-33b8-4a2b-b0f3-c59867adb9e3.png)





​																	    톱 : 3의 공격력을 갖는다(노랑풍선도 한번에 처치 가능하다.) 3000원





![HealthPositon](https://user-images.githubusercontent.com/109474668/208294329-b39ed62e-31f3-4ea4-94cf-be50192ba923.png)





​																		 물약 : 30의 체력을 회복한다. 300원



풍선의 정보는 다음과 같다. 풍선은 완전히 제거되었을때 경험치와 코인을 획득할수 있다.



![redBalloon](https://user-images.githubusercontent.com/109474668/208294365-b636c5d7-b3ee-44d7-83a2-8fd250acefd0.png)

빨강 풍선 : 가장 기본적인 풍선이다. 1의 체력을 갖는다. 연필(공격력:1)로 한번에 처치 가능하다.  처치시 150점의 경험치와 100코인을 획득한다.


![blueBalloon](https://user-images.githubusercontent.com/109474668/208294368-b5659de1-fdfa-4d3f-ae94-2d1fefd3d7ba.png)

파랑 풍선 : 두번의 공격으로 처치가능한 풍선이다. 2의 체력을 갖는다.  가위(공격력:2)로  한번에 처치 가능하다.   처치시 300점의 경험치와 200코인을 획득한다.


![yellowBalloon](https://user-images.githubusercontent.com/109474668/208294367-1d23f756-365f-44b3-adba-84ff01829ecf.png)



노랑 풍선 : 세번의 공격으로 처치가능한 풍선이다. 3의 체력을 갖는다. 톱(공격력:3)으로 한번에 처치 가능하다.  처치시 600점의 경험치와 300코인을 획득한다.


![starBalloon](https://user-images.githubusercontent.com/109474668/208294366-166a5b7e-a808-4408-bda4-ad01ec32ab6f.png)



스타 풍선:  한 라운드에서 얻을수 있는 경험치를 2배로 한다.  처치시 77점의 경험치와 0코인을 획득한다.  => 처치한 순간 경험치2배가 반영되어 154점을 획득함



![coinBalloon](https://user-images.githubusercontent.com/109474668/208294369-3433b3eb-7c4b-4ca9-ba04-bdc87de5394d.png)



코인 풍선:  처치시 1000코인을 획득한다 . 처치시 77점의 경험치와 1000코인을 획득한다. 모든 라운드에서 3퍼센트의 확률로 등장한다.





라운드마다 구체적인 풍선의 등장확률은 다음과 같다.(코인풍선/스타풍선/빨강/파랑/노랑)

1 라운드 :  (3/5/79/13/0)

2 라운드 :  (3/5/46/27/19)

3 라운드 :  (3/5/28/37/57)

1라운드(10개를 터뜨리면 라운드가 종료된다.) -> 2라운드(20개를 터뜨리면 라운드가 종료된다.) -> 3라운드(30개를 터뜨리면 라운드가 종료된다)



플레이어는 게임을 위해 캐릭터를 선택해야 한다.

캐릭터는 캐릭터마다 특별한 능력을 보유하고 있다. 

![image-20221216165529999](https://user-images.githubusercontent.com/109474668/208294404-275e42ba-54f3-4943-9662-007f2c4a4276.png)

캐릭터의 능력은 다음과 같다.

- 상상부기 : 단단한 등껍질을 가지고 있어 150의 체력으로 시작한다.

- 한성냥이 : 고양이의 동체시력으로 풍선을 느리게 본다(풍선이 느리게 떨어진다.) 90의 체력으로 시작한다.

- 꼬꼬&꾸꾸 : 환상의 커플 효과로 50프로의 확률로 추가타를 가한다. 80의 체력으로 시작한다.

  

캐릭터마다 난이도를 다르게 느낄수 있도록 하였으므로 자신에게 맞는 캐릭터를 선택하여 플레이 해야한다.



게임이 플레이 되는 모습

![image-20221216165846312](https://user-images.githubusercontent.com/109474668/208294412-639dc124-2e1e-46c0-b091-c902ca43a151.png)



게임이 오버된 모습 => 사용자로부터 이름을 입력받아 Score.txt에 저장한다.
![image-20221216165902819](https://user-images.githubusercontent.com/109474668/208294431-5ac4f33c-08cb-41bd-8a44-f4d4a82971e9.png)



게임이 클리어 된 모습 => 사용자로부터 이름을 입력받아 Score.txt에 저장한다.

![image-20221216170007585](https://user-images.githubusercontent.com/109474668/208294437-c94bcc08-00aa-4489-87f4-4f6d205a84eb.png)



플레이어는 입력한 점수와 아이디가 우수한 성적에 들었다면 메인메뉴에서 확인할수 있다.

![image-20221216170104077](https://user-images.githubusercontent.com/109474668/208294445-876dbcab-f958-4a43-b1f0-e4c1f1ceb196.png)



게임중 등장하는 단어들을 추가할수 있다.

![image-20221216170123886](https://user-images.githubusercontent.com/109474668/208294451-4a2aa69b-c159-4da0-b3ff-c92d4a8eb55e.png)


플레이 동영상

![gamePlay](https://user-images.githubusercontent.com/109474668/209203862-d4488834-a2b8-40c2-860c-1dcfd7490036.gif)











