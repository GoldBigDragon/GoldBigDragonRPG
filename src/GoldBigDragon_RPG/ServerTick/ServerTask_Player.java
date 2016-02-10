package GoldBigDragon_RPG.ServerTick;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

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
			long tick = ServerTickMain.Schedule.get(UTC).getTick()+1500;
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

	public void Gamble_SlotMachine_Rolling(long UTC)
	{
		ServerTickScheduleObject STSO = ServerTickMain.Schedule.get(UTC);
		int count = STSO.getCount()+1;
		if(count<STSO.getMaxCount())
		{
			STSO.setCount(count);
			
			int ItemID[] = new int[3];
			for(int counter=0;counter<3;counter++)
			{
				int randomnum = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 5);
				ItemID[counter]=263;
				switch(randomnum)
				{
				case 0:
					ItemID[counter] = 263;
					break;
				case 1:
					ItemID[counter] = 265;
					break;
				case 2:
					ItemID[counter] = 266;
					break;
				case 3:
					ItemID[counter] = 264;
					break;
				case 4:
					ItemID[counter] = 388;
					break;
				case 5:
					ItemID[counter] = 399;
					break;
				}
			}
			new GoldBigDragon_RPG.GUI.GambleGUI().SlotMachine_RollingGUI(STSO.getString((byte)0), ItemID, false, STSO.getString((byte)1));

			if(count<5)
			{
				STSO.setTick(STSO.getTick()+(count*20)+count);
				ServerTickMain.Schedule.remove(UTC);
				ServerTickMain.Schedule.put(STSO.getTick()+(count*20)+count,STSO);
			}
			else if(count<10)
			{
				STSO.setTick(STSO.getTick()+(count*25)+count);
				ServerTickMain.Schedule.remove(UTC);
				ServerTickMain.Schedule.put(STSO.getTick()+(count*25)+count,STSO);
			}
			else if(count<15)
			{
				STSO.setTick(STSO.getTick()+(count*30)+count);
				ServerTickMain.Schedule.remove(UTC);
				ServerTickMain.Schedule.put(STSO.getTick()+(count*30)+count,STSO);
			}
			else
			{
				STSO.setTick(STSO.getTick()+(count*35)+count);
				ServerTickMain.Schedule.remove(UTC);
				ServerTickMain.Schedule.put(STSO.getTick()+(count*35)+count,STSO);
			}
		}
		else
		{
			int ID[] = new int[3];
			ID[0] = STSO.getInt((byte)0);
			ID[1] = STSO.getInt((byte)1);
			ID[2] = STSO.getInt((byte)2);
			
			new GoldBigDragon_RPG.GUI.GambleGUI().SlotMachine_RollingGUI(STSO.getString((byte)0), ID , true, STSO.getString((byte)1));
			
			if(Bukkit.getServer().getPlayer(STSO.getString((byte)0)) != null)
			{
				if(Bukkit.getServer().getPlayer(STSO.getString((byte)0)).isOnline()==true)
				{
					Player player = Bukkit.getServer().getPlayer(STSO.getString((byte)0));
					String MachineNumber = STSO.getString((byte)1);
					String Present = null;
					boolean LuckyStar = false;
					YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
					YamlManager GambleConfig =YC_2.getNewConfig("ETC/SlotMachine.yml");
					//모두 같을 경우
					if(ID[0]==ID[1]&&ID[1]==ID[2])
					{
						//트리플 코어
						if(ID[0]==263)
							Present = GambleConfig.getString(MachineNumber+".1");
						//트리플 아이언
						else if(ID[0]==265)
							Present = GambleConfig.getString(MachineNumber+".2");
						//트리플 골드
						else if(ID[0]==266)
							Present = GambleConfig.getString(MachineNumber+".3");
						//트리플 다이아몬드
						else if(ID[0]==264)
							Present = GambleConfig.getString(MachineNumber+".4");
						//트리플 에메랄드
						else if(ID[0]==388)
							Present = GambleConfig.getString(MachineNumber+".5");
						//트리플 네더 스타
						else
						{
							Present = GambleConfig.getString(MachineNumber+".6");
							LuckyStar = true;
						}
					}
					//두개가 같을 경우
					else if(ID[0]==ID[1]||ID[1]==ID[2]||ID[0]==ID[2])
					{
						int Slot = 263;
						if(ID[0]==ID[1]||ID[0]==ID[2])
							Slot = ID[0];
						else if(ID[1]==ID[2])
							Slot = ID[1];

						//더블 코어
						if(Slot==263)
							Present = GambleConfig.getString(MachineNumber+".10");
						//더블 아이언
						else if(Slot==265)
							Present = GambleConfig.getString(MachineNumber+".11");
						//더블 골드
						else if(Slot==266)
							Present = GambleConfig.getString(MachineNumber+".12");
						//더블 다이아몬드
						else if(Slot==264)
							Present = GambleConfig.getString(MachineNumber+".13");
						//더블 에메랄드
						else if(Slot==388)
							Present = GambleConfig.getString(MachineNumber+".14");
						//더블 네더 스타
						else
							Present = GambleConfig.getString(MachineNumber+".15");
					}
					//모두 다를 경우
					else
					{
						if(ID[0]==399||ID[1]==399||ID[2]==399)
							Present = GambleConfig.getString(MachineNumber+".9");
						else
							Present = GambleConfig.getString(MachineNumber+".0");
					}
					if(Present.compareTo("null")==0)
					{
						new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.IRONGOLEM_HIT, 0.8F, 0.9F);
						player.sendMessage(ChatColor.RED+"[슬롯 머신] : 꽝! 다음 기회에...");
					}
					else
					{
						YamlManager PresentList  = YC_2.getNewConfig("Item/GamblePresent.yml");
						String Grade = PresentList.getString(Present+".Grade");
						new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.LEVEL_UP, 1.0F, 1.8F);
						if(LuckyStar)
							Bukkit.broadcastMessage(ChatColor.GREEN+"[슬롯 머신] : "+ChatColor.YELLOW+""+ChatColor.BOLD+player.getName()+ChatColor.GREEN+"님께서 "+ChatColor.YELLOW+""+ChatColor.BOLD+Present+" "+Grade+ChatColor.GREEN+" 상품에 당첨되셨습니다!");
						else
							player.sendMessage(ChatColor.GREEN+"[슬롯 머신] : "+ChatColor.YELLOW+""+ChatColor.BOLD+Present+" "+Grade+ChatColor.GREEN+" 상품에 당첨되셨습니다!");

						Object[] Presents = PresentList.getConfigurationSection(Present+".Present").getKeys(false).toArray();
						for(int counter=0;counter<Presents.length;counter++)
						{
							ItemStack item = PresentList.getItemStack(Present+".Present."+counter);
							if(new GoldBigDragon_RPG.ETC.Items().GiveItem(player, item)==false)
							{
								new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED+"[슬롯 머신] : 인벤토리가 부족하여 아이템이 바닥에 떨어집니다!");
								new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
							}
						}
					}
				}
			}
			ServerTickMain.PlayerTaskList.remove(STSO.getString((byte)0));
			ServerTickMain.Schedule.remove(UTC);
		}
	}
}