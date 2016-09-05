package GoldBigDragon_RPG.Util;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

//자바 본래 라이브러리 중, 날짜 함수를 호출함.

public class ETC
{
	public Date date = new Date();
	//날짜 함수를 호출하여 date란 새 이름으로 지정하는 단락

	public String getFrom(long nowUTC, long PrevUTC)
	{
		if(nowUTC-PrevUTC<0)
			return "알 수 없음";
		else
		{
			long WaitingTime = (nowUTC-PrevUTC)/1000;
			short year = 0;
			byte month = 0;
			byte day = 0;
			byte hour = 0;
			byte min = 0;
			String pastedTime="";
			if(WaitingTime >= 31536000)
			{
				year = (short) (WaitingTime/31536000);
				WaitingTime = WaitingTime-(31536000*year);
			}
			if(WaitingTime >= 2678400)
			{
				month = (byte) (WaitingTime/2678400);
				WaitingTime = WaitingTime-(2678400*month);
			}
			if(WaitingTime >= 86400)
			{
				day = (byte) (WaitingTime/86400);
				WaitingTime = WaitingTime-(86400*day);
			}
			if(WaitingTime >= 3600)
			{
				hour = (byte) (WaitingTime/3600);
				WaitingTime = WaitingTime-(3600*hour);
			}
			if(WaitingTime >= 60)
			{
				min = (byte) (WaitingTime/60);
				WaitingTime = WaitingTime-(60*min);
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
			pastedTime = pastedTime+WaitingTime+"초";
			return pastedTime;
		}
	}
	
	public String getSchedule(long UTC)
	{
		Date a = new Date(UTC);
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
    	Calendar Calender = Calendar.getInstance();
    	return Calender.getTimeInMillis();
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
		short year = (short)Calender.get ( Calendar.YEAR );
		byte month = (byte)(Calender.get ( Calendar.MONTH ) + 1);
		byte date = (byte)Calender.get ( Calendar.DATE ) ;
		//현재 시간(시,분,초)
		byte hour = (byte)Calender.get ( Calendar.HOUR_OF_DAY ) ;
		int zellerMonth;
		int zellerYear;
		String Today = null;
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
		    	  Today = "토요일";
		          break;
		      case 1:
		    	  Today = "일요일";
		          break;
		      case 2:
		    	  Today = "월요일";
		          break;
		      case 3:
		    	  Today = "화요일";
		          break;
		      case 4:
		    	  Today = "수요일";
		          break;
		      case 5:
		    	  Today = "목요일 ";
		          break;
		      case 6:
		    	  Today = "금요일";
		          break;
		    }   
		 return Today;
		    
	}
	
    public void UpdatePlayerHPMP(Player player)
    {
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true
		&&GoldBigDragon_RPG.Main.ServerOption.MagicSpellsCatched==true)
		{
			OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
			MS.setPlayerMaxAndNowMana(player);
		}
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Damageable p = player;
		int BonusHealth = d.getPlayerEquipmentStat(player, "생명력", null, false)[0];
		int MaxHealth = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxHP()+BonusHealth;
		if(MaxHealth > 0)
			p.setMaxHealth(MaxHealth);
		return;
    }
    
    public void SlotChangedUpdatePlayerHPMP(Player player, ItemStack newSlot, boolean isHotbarChange)
    {
		if(GoldBigDragon_RPG.Main.ServerOption.MagicSpellsCatched == true)
		{
			OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
			MS.setSlotChangePlayerMaxAndNowMana(player, newSlot, isHotbarChange);
		}
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Damageable p = player;

		int BonusHealth = d.getPlayerEquipmentStat(player, "생명력", newSlot, isHotbarChange)[0];
		int MaxHealth = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxHP()+BonusHealth;

		if(MaxHealth > 0)
			p.setMaxHealth(MaxHealth);
		return;
    }
}
