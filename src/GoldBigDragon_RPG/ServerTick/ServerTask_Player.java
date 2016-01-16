package GoldBigDragon_RPG.ServerTick;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class ServerTask_Player
{
	public void ExChangeTimer(long UTC)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player caller = Bukkit.getServer().getPlayer(ServerTickMain.Schedule.get(UTC).getString((byte)0));
		Player target = Bukkit.getServer().getPlayer(ServerTickMain.Schedule.get(UTC).getString((byte)1));
		if(caller == null)
		{
			Cancel(UTC,0);
			return;
		}
		else if(target == null)
		{
			Cancel(UTC,0);
			return;
		}

		if(ServerTickMain.Schedule.get(UTC).getCount() <= ServerTickMain.Schedule.get(UTC).getMaxCount())
		{
			GoldBigDragon_RPG.Effect.PacketSender PS = new GoldBigDragon_RPG.Effect.PacketSender();
			float fast;
			if(ServerTickMain.Schedule.get(UTC).getCount() <= 5)
				fast = (float) 0.5;
			else
				fast = (float) (ServerTickMain.Schedule.get(UTC).getCount()/10.0);
			s.SP(caller, Sound.NOTE_PLING, 0.8F, fast);
			s.SP(target, Sound.NOTE_PLING, 0.8F, fast);
			PS.sendTitleSubTitle(caller, "\'"+ChatColor.YELLOW+"[교환 신청]"+"\'", "\'"+TimerBar(ServerTickMain.Schedule.get(UTC).getCount(), 10)+"\'", 1, 0, 1);
			PS.sendTitleSubTitle(target, "\'"+ChatColor.YELLOW+"[교환 신청]"+"\'", "\'"+TimerBar(ServerTickMain.Schedule.get(UTC).getCount(), 10)+"\'", 1, 0, 1);
			long tick = ServerTickMain.Schedule.get(UTC).getTick()+1000;
			ServerTickMain.Schedule.get(UTC).setCount(ServerTickMain.Schedule.get(UTC).getCount()+1);
			ServerTickMain.Schedule.get(UTC).setTick(tick);
			ServerTickMain.PlayerTaskList.put(caller.getName(), ""+tick);
			ServerTickMain.PlayerTaskList.put(target.getName(), ""+tick);
			ServerTickMain.Schedule.put(tick, ServerTickMain.Schedule.get(UTC));
		}
		else
		{
			Cancel(UTC,0);
			return;
		}
	}
	
	public void Cancel (long UTC, int MessageType)
	{
		Player caller = Bukkit.getServer().getPlayer(ServerTickMain.Schedule.get(UTC).getString((byte)0));
		Player target = Bukkit.getServer().getPlayer(ServerTickMain.Schedule.get(UTC).getString((byte)1));
		
		ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)0));
		ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)1));
		ServerTickMain.Schedule.remove(UTC);
		
		if(caller != null)
			SendMessage(caller, MessageType);
		if(target != null)
			SendMessage(target, MessageType+1);
	}
	
	public void SendMessage(Player Receiver, int message)
	{
		
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		
		switch(message)
		{
			case 0 ://교환 신청자 - 교환 취소 메시지
			{
				Receiver.sendMessage(ChatColor.RED+"[교환] : 상대가 교환을 취소하였습니다.");
				s.SP(Receiver, Sound.VILLAGER_NO, 1.2F, 1.1F);
			}
			break;
			case 1 ://교환 상대 - 교환 취소 메시지
			{
				Receiver.sendMessage(ChatColor.RED+"[교환] : 교환이 취소되었습니다.");
				s.SP(Receiver, Sound.VILLAGER_NO, 1.2F, 1.1F);
			}
			break;
		}
	}
	
	public String TimerBar(int PassedSec, int MaxWaittingTime)
	{
		String TimerBar = ChatColor.DARK_RED+"";
		int Calculator = 0;
		if(PassedSec == MaxWaittingTime)
		{
			for(short count = 0; count <= 100; count++)
				TimerBar = TimerBar + "|";
			return TimerBar;
		}
		else if(MaxWaittingTime/100 >= 1)
		{
			Calculator = PassedSec/(MaxWaittingTime/100);
			for(short count = 0; count <= Calculator; count++)
				TimerBar = TimerBar + "|";
			TimerBar = TimerBar + ChatColor.BLUE;
			for(short count = 0; count <= 100-Calculator; count++)
				TimerBar = TimerBar + "|";
		}
		else if(MaxWaittingTime/10 >= 1)
		{
			Calculator = PassedSec/(MaxWaittingTime/10);
			for(short count = 0; count <= (Calculator*10); count++)
				TimerBar = TimerBar + "|";
			TimerBar = TimerBar + ChatColor.BLUE;
			for(short count = 0; count <= 100-(Calculator*10); count++)
				TimerBar = TimerBar + "|";
		}
		return TimerBar;
	}
}