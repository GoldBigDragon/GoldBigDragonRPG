package util;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import battle.BattleCalculator;

//자바 본래 라이브러리 중, 날짜 함수를 호출함.

public class ETC
{
	public Date date = new Date();
	//날짜 함수를 호출하여 date란 새 이름으로 지정하는 단락

	public String getFrom(long nowUTC, long prevUTC)
	{
		if(nowUTC-prevUTC<0)
			return "알 수 없음";
		else
		{
			long waitingTime = (nowUTC-prevUTC)/1000;
			short year = 0;
			byte month = 0;
			byte day = 0;
			byte hour = 0;
			byte min = 0;
			String pastedTime="";
			if(waitingTime >= 31536000)
			{
				year = (short) (waitingTime/31536000);
				waitingTime = waitingTime-(31536000*year);
			}
			if(waitingTime >= 2678400)
			{
				month = (byte) (waitingTime/2678400);
				waitingTime = waitingTime-(2678400*month);
			}
			if(waitingTime >= 86400)
			{
				day = (byte) (waitingTime/86400);
				waitingTime = waitingTime-(86400*day);
			}
			if(waitingTime >= 3600)
			{
				hour = (byte) (waitingTime/3600);
				waitingTime = waitingTime-(3600*hour);
			}
			if(waitingTime >= 60)
			{
				min = (byte) (waitingTime/60);
				waitingTime = waitingTime-(60*min);
			}
			if(year!=0)
				pastedTime = year+"년";
			if(month!=0)
				pastedTime = pastedTime+month+"개월";
			if(day!=0)
				pastedTime = pastedTime+day+"일";
			if(hour!=0)
				pastedTime = pastedTime+hour+"시간";
			if(min!=0)
				pastedTime = pastedTime+min+"분";
			pastedTime = pastedTime+waitingTime+"초";
			return pastedTime;
		}
	}
	
	public String getSchedule(long utc)
	{
		Date a = new Date(utc);
		date.after(a);
		return date.getYear()+"년 "+date.getMonth()+"월 "+date.getDay()+"일 "+
		date.getHours()+"시 "+date.getMinutes()+"분";
	}
	
	public long getNowUTC()
	{
		return date.UTC(date.getYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());
	}
	
    public long getSec()
    {
    	Calendar calender = Calendar.getInstance();
    	return calender.getTimeInMillis();
    }
    
    //현재 초를 나타내는 메소드//
    public int getSecond()
    {
    	return date.getSeconds();
    }
    
    //현재 분을 나타내는 메소드//
    public int getMin()
    {
    	return date.getMinutes();
    }
    
    //현재 시간을 나타내는 메소드//
    public int getHour()
    {
    	return date.getHours();
    }
    
    //현재 요일을 나타내는 메소드//
    public int getDay()
    {
    	return date.getDate();
    }
    
    //현재 월을 나타내는 메소드//
    public int getMonth()
    {
    	return date.getMonth()+1;
    }

    //현재 년도를 나타내는 메소드//
    public int getYear()
    {
    	return date.getYear()+1900;
    }
    
    //년/월/일 까지 구해주는 메소드//
    public String getToday()
    {
    	return getYear() + "." + getMonth() + "." + getDay();
    }

    //제라의 공식을 사용한 요일 구하는 메소드//
    public String Today()
	{
		Calendar Calender = Calendar.getInstance();
		//현재 년도, 월, 일
		int year = Calender.get ( Calendar.YEAR );
		int month = Calender.get ( Calendar.MONTH ) + 1;
		int date = Calender.get ( Calendar.DATE ) ;
		//현재 시간(시,분,초)
		int zellerMonth;
		int zellerYear;
		String today = null;
		if(month < 3) { // 월값이 3보다 작으면
		    
		    zellerMonth = month + 12; // 월 + 12
		    zellerYear = year - 1; // 연 - 1
		}

		else {
			zellerMonth = month;
			zellerYear = year;
		}
		   
		int computation = date + (26 * (zellerMonth + 1)) / 10 + zellerYear + 
		                  zellerYear / 4 + 6 * (zellerYear / 100) +
		                  zellerYear / 400;
		int dayOfWeek = computation % 7;
		
		
		 switch(dayOfWeek) // 0~6까지 토~금요일로 표시
		    {
		     
		      case 0:
		    	  today = "토요일";
		          break;
		      case 1:
		    	  today = "일요일";
		          break;
		      case 2:
		    	  today = "월요일";
		          break;
		      case 3:
		    	  today = "화요일";
		          break;
		      case 4:
		    	  today = "수요일";
		          break;
		      case 5:
		    	  today = "목요일 ";
		          break;
		      case 6:
		    	  today = "금요일";
		          break;
		    }   
		 return today;
		    
	}
	
    public void updatePlayerHPMP(Player player)
    {
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells")
		&&main.MainServerOption.MagicSpellsCatched)
		{
			otherplugins.SpellMain spellMain = new otherplugins.SpellMain();
			spellMain.setPlayerMaxAndNowMana(player);
		}
		Damageable p = player;
		int bonusHealth = BattleCalculator.getPlayerEquipmentStat(player, "생명력", false, null)[0];
		int maxHealth = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxHP()+bonusHealth;
		if(maxHealth > 0)
			p.setMaxHealth(maxHealth);
		else
			p.setMaxHealth(1);
		return;
    }
    
    public void slotChangedUpdatePlayerHPMP(Player player, ItemStack newSlot)
    {
		if(main.MainServerOption.MagicSpellsCatched)
		{
			otherplugins.SpellMain spellMain = new otherplugins.SpellMain();
			spellMain.setSlotChangePlayerMaxAndNowMana(player,newSlot);
		}
		Damageable p = player;
		
		int bonusHealth = BattleCalculator.getPlayerEquipmentStat(player, "생명력",  false, newSlot)[0];
		int maxHealth = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxHP()+bonusHealth;

		if(maxHealth > 0)
			p.setMaxHealth(maxHealth);
		else
			p.setMaxHealth(1);
		return;
    }
}
