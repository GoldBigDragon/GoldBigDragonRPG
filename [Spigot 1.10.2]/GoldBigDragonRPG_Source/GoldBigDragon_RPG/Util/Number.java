package GoldBigDragon_RPG.Util;

import java.util.Random;
//자바 본래 라이브러리 중, 랜덤 함수를 호출함.

public class Number
{
    public boolean isNumeric(String str)
    //숫자인가를 알아내 주는 메소드
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
	
	//최소 ~ 최대 값 중, 랜덤한 값을 추출하는 메소드//
	public int RandomNum(int min, int max)
    {
		if(min<=max)
			return new Random().nextInt((int) (max-min+1))+min;
		else
			return new Random().nextInt((int) (min-max+1))+max;
    }

    public boolean RandomPercent(double percent)
	{
		if (Math.random() <= percent)
		return true;
		return false;
	}
}
