package servertick;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import effect.SoundEffect;
import util.YamlLoader;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class ServerTask_Player
{
	public void UseTeleportScroll(long UTC)
	{
		
		Player user = Bukkit.getServer().getPlayer(ServerTick_Main.Schedule.get(UTC).getString((byte)2));
		if(user == null)
		{
			ServerTick_Main.Schedule.remove(UTC);
			ServerTick_Main.PlayerTaskList.remove(ServerTick_Main.Schedule.get(UTC).getString((byte)2));
			return;
		}
		else
		{
			Location loc = user.getLocation();
			String[] savedLoc = ServerTick_Main.Schedule.get(UTC).getString((byte)1).split(",");
			boolean isCancel = false;
			if(loc.getWorld().getName().compareTo(savedLoc[0])!=0)
				isCancel = true;
			else if(loc.getBlockX() != Integer.parseInt(savedLoc[1])
					|| loc.getBlockY() != Integer.parseInt(savedLoc[2])
					|| loc.getBlockZ() != Integer.parseInt(savedLoc[3]))
				isCancel = true;
			if(isCancel)
			{
				ServerTick_Main.Schedule.remove(UTC);
				SoundEffect.SP(user, Sound.ITEM_SHIELD_BREAK, 0.6F, 1.4F);
				new effect.SendPacket().sendActionBar(user, "§c§l[텔레포트가 취소되었습니다!]");
				ServerTick_Main.PlayerTaskList.remove(user.getName());
			}
			else
			{
				if(ServerTick_Main.Schedule.get(UTC).getCount() > 0)
				{
					new effect.SendPacket().sendActionBar(user, "§e§l[텔레포트까지 남은 시간 : §f§l" + ServerTick_Main.Schedule.get(UTC).getCount() +"§e§l초]");
					ServerTick_Main.Schedule.get(UTC).setCount(ServerTick_Main.Schedule.get(UTC).getCount()-1);
					ServerTick_Main.Schedule.get(UTC).copyThisScheduleObject(UTC+1000);
				}
				else
				{
					String[] teleportLoc = ServerTick_Main.Schedule.get(UTC).getString((byte)0).split(",");
					user.teleport(new Location(Bukkit.getWorld(teleportLoc[0]), (double)Integer.parseInt(teleportLoc[1]), (double)Integer.parseInt(teleportLoc[2]), (double)Integer.parseInt(teleportLoc[3])));
					SoundEffect.SP(user, Sound.ENTITY_ENDERMEN_TELEPORT, 0.6F, 1.0F);
					ServerTick_Main.PlayerTaskList.remove(user.getName());
				}
				ServerTick_Main.Schedule.remove(UTC);
			}
		}
	}
	
	public void ExChangeTimer(long UTC)
	{
		
		Player caller = Bukkit.getServer().getPlayer(ServerTick_Main.Schedule.get(UTC).getString((byte)0));
		Player target = Bukkit.getServer().getPlayer(ServerTick_Main.Schedule.get(UTC).getString((byte)1));
		if(caller == null)
		{
			Cancel(UTC,(short) 0);
			return;
		}
		else if(target == null)
		{
			Cancel(UTC,(short) 0);
			return;
		}

		if(ServerTick_Main.Schedule.get(UTC).getCount() <= ServerTick_Main.Schedule.get(UTC).getMaxCount())
		{
			effect.SendPacket PS = new effect.SendPacket();
			float fast;
			if(ServerTick_Main.Schedule.get(UTC).getCount() <= 5)
				fast = (float) 0.5;
			else
				fast = (float) (ServerTick_Main.Schedule.get(UTC).getCount()/10.0);
			SoundEffect.SP(caller, Sound.BLOCK_NOTE_PLING, 0.8F, fast);
			SoundEffect.SP(target, Sound.BLOCK_NOTE_PLING, 0.8F, fast);
			PS.sendTitleSubTitle(caller, "\'"+ChatColor.YELLOW+"[교환 신청]"+"\'", "\'"+TimerBar(ServerTick_Main.Schedule.get(UTC).getCount(), 10)+"\'", (byte)1, (byte)0, (byte)1);
			PS.sendTitleSubTitle(target, "\'"+ChatColor.YELLOW+"[교환 신청]"+"\'", "\'"+TimerBar(ServerTick_Main.Schedule.get(UTC).getCount(), 10)+"\'", (byte)1, (byte)0, (byte)1);
			long tick = ServerTick_Main.Schedule.get(UTC).getTick()+1500;
			ServerTick_Main.Schedule.get(UTC).setCount(ServerTick_Main.Schedule.get(UTC).getCount()+1);
			ServerTick_Main.Schedule.get(UTC).setTick(tick);
			ServerTick_Main.PlayerTaskList.put(caller.getName(), ""+tick);
			ServerTick_Main.PlayerTaskList.put(target.getName(), ""+tick);
			ServerTick_Main.Schedule.put(tick, ServerTick_Main.Schedule.get(UTC));
		}
		else
		{
			Cancel(UTC,(short) 0);
			return;
		}
	}
	
	public void Cancel (long UTC, short MessageType)
	{
		Player caller = Bukkit.getServer().getPlayer(ServerTick_Main.Schedule.get(UTC).getString((byte)0));
		Player target = Bukkit.getServer().getPlayer(ServerTick_Main.Schedule.get(UTC).getString((byte)1));
		
		ServerTick_Main.PlayerTaskList.remove(ServerTick_Main.Schedule.get(UTC).getString((byte)0));
		ServerTick_Main.PlayerTaskList.remove(ServerTick_Main.Schedule.get(UTC).getString((byte)1));
		ServerTick_Main.Schedule.remove(UTC);
		
		if(caller != null)
			SendMessage(caller, MessageType);
		if(target != null)
			SendMessage(target, (short) (MessageType+1));
	}
	
	public void SendMessage(Player Receiver, short message)
	{
		
		switch(message)
		{
			case 0 ://교환 신청자 - 교환 취소 메시지
			{
				Receiver.sendMessage(ChatColor.RED+"[교환] : 상대가 교환을 취소하였습니다.");
				SoundEffect.SP(Receiver, Sound.ENTITY_VILLAGER_NO, 1.2F, 1.1F);
			}
			break;
			case 1 ://교환 상대 - 교환 취소 메시지
			{
				Receiver.sendMessage(ChatColor.RED+"[교환] : 교환이 취소되었습니다.");
				SoundEffect.SP(Receiver, Sound.ENTITY_VILLAGER_NO, 1.2F, 1.1F);
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
			for(int count = 0; count <= 100; count++)
				TimerBar = TimerBar + "|";
			return TimerBar;
		}
		else if(MaxWaittingTime/100 >= 1)
		{
			Calculator = PassedSec/(MaxWaittingTime/100);
			for(int count = 0; count <= Calculator; count++)
				TimerBar = TimerBar + "|";
			TimerBar = TimerBar + ChatColor.BLUE;
			for(int count = 0; count <= 100-Calculator; count++)
				TimerBar = TimerBar + "|";
		}
		else if(MaxWaittingTime/10 >= 1)
		{
			Calculator = PassedSec/(MaxWaittingTime/10);
			for(int count = 0; count <= (Calculator*10); count++)
				TimerBar = TimerBar + "|";
			TimerBar = TimerBar + ChatColor.BLUE;
			for(int count = 0; count <= 100-(Calculator*10); count++)
				TimerBar = TimerBar + "|";
		}
		return TimerBar;
	}

	public void Gamble_SlotMachine_Rolling(long UTC)
	{
		ServerTick_Object STSO = ServerTick_Main.Schedule.get(UTC);
		int count = STSO.getCount()+1;
		if(count<STSO.getMaxCount())
		{
			STSO.setCount(count);
			
			short[] ItemID = new short[3];
			for(int counter=0;counter<3;counter++)
			{
				byte randomnum = (byte) new util.Util_Number().RandomNum(0, 5);
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
			new admin.Gamble_GUI().SlotMachine_RollingGUI(STSO.getString((byte)0), ItemID, false, STSO.getString((byte)1));

			if(count<5)
			{
				STSO.setTick(STSO.getTick()+(count*20)+count);
				ServerTick_Main.Schedule.remove(UTC);
				ServerTick_Main.Schedule.put(STSO.getTick()+(count*20)+count,STSO);
			}
			else if(count<10)
			{
				STSO.setTick(STSO.getTick()+(count*25)+count);
				ServerTick_Main.Schedule.remove(UTC);
				ServerTick_Main.Schedule.put(STSO.getTick()+(count*25)+count,STSO);
			}
			else if(count<15)
			{
				STSO.setTick(STSO.getTick()+(count*30)+count);
				ServerTick_Main.Schedule.remove(UTC);
				ServerTick_Main.Schedule.put(STSO.getTick()+(count*30)+count,STSO);
			}
			else
			{
				STSO.setTick(STSO.getTick()+(count*35)+count);
				ServerTick_Main.Schedule.remove(UTC);
				ServerTick_Main.Schedule.put(STSO.getTick()+(count*35)+count,STSO);
			}
		}
		else
		{
			short[] ID = new short[3];
			ID[0] = (short) STSO.getInt((byte)0);
			ID[1] = (short) STSO.getInt((byte)1);
			ID[2] = (short) STSO.getInt((byte)2);
			
			new admin.Gamble_GUI().SlotMachine_RollingGUI(STSO.getString((byte)0), ID , true, STSO.getString((byte)1));
			
			if(Bukkit.getServer().getPlayer(STSO.getString((byte)0)) != null)
			{
				if(Bukkit.getServer().getPlayer(STSO.getString((byte)0)).isOnline()==true)
				{
					Player player = Bukkit.getServer().getPlayer(STSO.getString((byte)0));
					String MachineNumber = STSO.getString((byte)1);
					String Present = null;
					boolean LuckyStar = false;
					YamlLoader GambleConfig = new YamlLoader();
					GambleConfig.getConfig("ETC/SlotMachine.yml");
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
						SoundEffect.SP(player, Sound.ENTITY_IRONGOLEM_HURT, 0.8F, 0.9F);
						player.sendMessage(ChatColor.RED+"[슬롯 머신] : 꽝! 다음 기회에...");
					}
					else
					{
						YamlLoader PresentList = new YamlLoader();
						PresentList.getConfig("Item/GamblePresent.yml");
						String Grade = PresentList.getString(Present+".Grade");
						SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
						if(LuckyStar)
							Bukkit.broadcastMessage(ChatColor.GREEN+"[슬롯 머신] : "+ChatColor.YELLOW+""+ChatColor.BOLD+player.getName()+ChatColor.GREEN+"님께서 "+ChatColor.YELLOW+""+ChatColor.BOLD+Present+" "+Grade+ChatColor.GREEN+" 상품에 당첨되셨습니다!");
						else
							player.sendMessage(ChatColor.GREEN+"[슬롯 머신] : "+ChatColor.YELLOW+""+ChatColor.BOLD+Present+" "+Grade+ChatColor.GREEN+" 상품에 당첨되셨습니다!");

						if(PresentList.contains(Present+".Present"))
						{
							Object[] Presents = PresentList.getConfigurationSection(Present+".Present").getKeys(false).toArray();
							for(int counter=0;counter<Presents.length;counter++)
							{
								ItemStack item = PresentList.getItemStack(Present+".Present."+counter);
								new util.Util_Player().giveItemForce(player, item);
							}
						}
					}
				}
			}
			ServerTick_Main.PlayerTaskList.remove(STSO.getString((byte)0));
			ServerTick_Main.Schedule.remove(UTC);
		}
	}
}