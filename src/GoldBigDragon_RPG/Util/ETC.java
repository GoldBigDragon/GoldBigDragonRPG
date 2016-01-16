package GoldBigDragon_RPG.Util;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.Main.Main;

//자바 본래 라이브러리 중, 날짜 함수를 호출함.

public class ETC
{
	public Date date = new Date();
	//날짜 함수를 호출하여 date란 새 이름으로 지정하는 단락
	
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
		int year = Calender.get ( Calendar.YEAR );
		int month = Calender.get ( Calendar.MONTH ) + 1 ;
		int date = Calender.get ( Calendar.DATE ) ;
		//현재 시간(시,분,초)
		int hour = Calender.get ( Calendar.HOUR_OF_DAY ) ;
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
		&&Main.MagicSpellsCatched==true)
		{
			OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
			MS.setPlayerMaxAndNowMana(player);
		}
		GoldBigDragon_RPG.Event.Damage d = new GoldBigDragon_RPG.Event.Damage();
		YamlController Main_YC = GoldBigDragon_RPG.Main.Main.YC_2;
		YamlManager PlayerStats  = Main_YC.getNewConfig("Stats/"+player.getUniqueId().toString()+".yml");
		Damageable p = player;
		
		int BonusHealth = d.getPlayerEquipmentStat(player, "생명력")[0];
		int MaxHealth = PlayerStats.getInt("Stat.MAXHP")+BonusHealth;
		if(MaxHealth > 0)
			p.setMaxHealth(MaxHealth);
		return;
    }
    
    public void SlotChangedUpdatePlayerHPMP(Player player, ItemStack newSlot)
    {
		if(Main.MagicSpellsCatched == true)
		{
			OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
			MS.setSlotChangePlayerMaxAndNowMana(player,newSlot);
		}
		GoldBigDragon_RPG.Event.Damage d = new GoldBigDragon_RPG.Event.Damage();
		YamlController Main_YC = GoldBigDragon_RPG.Main.Main.YC_2;
		YamlManager PlayerStats  = Main_YC.getNewConfig("Stats/"+player.getUniqueId().toString()+".yml");
		Damageable p = player;
		
		int BonusHealth = d.getSlotChangedPlayerEquipmentStat(player, "생명력",newSlot)[0];
		int MaxHealth = PlayerStats.getInt("Stat.MAXHP")+BonusHealth;

		if(MaxHealth > 0)
			p.setMaxHealth(MaxHealth);
		return;
    }
}
