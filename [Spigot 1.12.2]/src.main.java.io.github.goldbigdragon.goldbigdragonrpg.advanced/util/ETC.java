package util;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import battle.BattleCalculator;

//�ڹ� ���� ���̺귯�� ��, ��¥ �Լ��� ȣ����.

public class ETC
{
	public Date date = new Date();
	//��¥ �Լ��� ȣ���Ͽ� date�� �� �̸����� �����ϴ� �ܶ�

	public String getFrom(long nowUTC, long prevUTC)
	{
		if(nowUTC-prevUTC<0)
			return "�� �� ����";
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
				pastedTime = year+"��";
			if(month!=0)
				pastedTime = pastedTime+month+"����";
			if(day!=0)
				pastedTime = pastedTime+day+"��";
			if(hour!=0)
				pastedTime = pastedTime+hour+"�ð�";
			if(min!=0)
				pastedTime = pastedTime+min+"��";
			pastedTime = pastedTime+waitingTime+"��";
			return pastedTime;
		}
	}
	
	public String getSchedule(long utc)
	{
		Date a = new Date(utc);
		date.after(a);
		return date.getYear()+"�� "+date.getMonth()+"�� "+date.getDay()+"�� "+
		date.getHours()+"�� "+date.getMinutes()+"��";
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
    
    //���� �ʸ� ��Ÿ���� �޼ҵ�//
    public int getSecond()
    {
    	return date.getSeconds();
    }
    
    //���� ���� ��Ÿ���� �޼ҵ�//
    public int getMin()
    {
    	return date.getMinutes();
    }
    
    //���� �ð��� ��Ÿ���� �޼ҵ�//
    public int getHour()
    {
    	return date.getHours();
    }
    
    //���� ������ ��Ÿ���� �޼ҵ�//
    public int getDay()
    {
    	return date.getDate();
    }
    
    //���� ���� ��Ÿ���� �޼ҵ�//
    public int getMonth()
    {
    	return date.getMonth()+1;
    }

    //���� �⵵�� ��Ÿ���� �޼ҵ�//
    public int getYear()
    {
    	return date.getYear()+1900;
    }
    
    //��/��/�� ���� �����ִ� �޼ҵ�//
    public String getToday()
    {
    	return getYear() + "." + getMonth() + "." + getDay();
    }

    //������ ������ ����� ���� ���ϴ� �޼ҵ�//
    public String Today()
	{
		Calendar Calender = Calendar.getInstance();
		//���� �⵵, ��, ��
		int year = Calender.get ( Calendar.YEAR );
		int month = Calender.get ( Calendar.MONTH ) + 1;
		int date = Calender.get ( Calendar.DATE ) ;
		//���� �ð�(��,��,��)
		int zellerMonth;
		int zellerYear;
		String today = null;
		if(month < 3) { // ������ 3���� ������
		    
		    zellerMonth = month + 12; // �� + 12
		    zellerYear = year - 1; // �� - 1
		}

		else {
			zellerMonth = month;
			zellerYear = year;
		}
		   
		int computation = date + (26 * (zellerMonth + 1)) / 10 + zellerYear + 
		                  zellerYear / 4 + 6 * (zellerYear / 100) +
		                  zellerYear / 400;
		int dayOfWeek = computation % 7;
		
		
		 switch(dayOfWeek) // 0~6���� ��~�ݿ��Ϸ� ǥ��
		    {
		     
		      case 0:
		    	  today = "�����";
		          break;
		      case 1:
		    	  today = "�Ͽ���";
		          break;
		      case 2:
		    	  today = "������";
		          break;
		      case 3:
		    	  today = "ȭ����";
		          break;
		      case 4:
		    	  today = "������";
		          break;
		      case 5:
		    	  today = "����� ";
		          break;
		      case 6:
		    	  today = "�ݿ���";
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
		int bonusHealth = BattleCalculator.getPlayerEquipmentStat(player, "�����", false, null, false)[0];
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
		
		int bonusHealth = BattleCalculator.getPlayerEquipmentStat(player, "�����",  false, newSlot, false)[0];
		int maxHealth = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxHP()+bonusHealth;

		if(maxHealth > 0)
			p.setMaxHealth(maxHealth);
		else
			p.setMaxHealth(1);
		return;
    }
}
