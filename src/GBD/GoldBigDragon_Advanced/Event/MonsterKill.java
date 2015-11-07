package GBD.GoldBigDragon_Advanced.Event;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import GBD.GoldBigDragon_Advanced.Effect.PacketSender;
import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class MonsterKill
{
	private Level LV = new Level();
	private ItemDrop ID = new ItemDrop();
	private GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
	private GBD.GoldBigDragon_Advanced.Util.Number N = new GBD.GoldBigDragon_Advanced.Util.Number();
	private GBD.GoldBigDragon_Advanced.Config.StatConfig stat = new GBD.GoldBigDragon_Advanced.Config.StatConfig();

	@EventHandler
	public void MonsterKill(EntityDeathEvent event)
	{
    	if(event.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK ||
    	event.getEntity().getLastDamageCause().getCause() == DamageCause.PROJECTILE)
    	{
    		if (event.getEntity().getKiller() != null)
    		{
				if(Bukkit.getServer().getPlayer(event.getEntity().getKiller().getName()).isOnline() == true)
				{
					Player player = (Player) Bukkit.getServer().getPlayer(event.getEntity().getKiller().getName());
					YamlManager attacker;
					
					YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
				  	if(Event_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
				  		stat.CreateNewStats(player);
				  	attacker = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
				  	
					if(attacker.getBoolean("Alert.MobHealth") == true)
					    new PacketSender().sendTitleSubTitle(player,"\'" +ChatColor.DARK_RED+"[DEAD]"+ "\'", "\'" +ChatColor.DARK_RED+"||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"+ "\'", 0, 0, 1);
					
    				Reward(event,player);
    				Quest(event, player);
					return;
				}
			}
		}
	}

	public void Reward(EntityDeathEvent event, Player player)
	{
		YamlController Monster_YC = GBD.GoldBigDragon_Advanced.Main.Main.Monster_YC;
		YamlManager YM,Config;
	  	if(Monster_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		stat.CreateNewStats(player);

		YM = Monster_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		Config = Monster_YC.getNewConfig("config.yml");
		int amount = 1;
		if(40 <= N.RandomNum(0, 100)*Config.getInt("Event.DropChance"))
		{
			int lucky = YM.getInt("Stat.LUK")/30;
			if(lucky >= 10) lucky =10;
			if(lucky <= 0) lucky = 1;
			if(lucky >= N.RandomNum(0, 100))
			{
				int luckysize = N.RandomNum(0, 100);
				if(luckysize <= 80){player.sendMessage(ChatColor.YELLOW +""+ChatColor.BOLD+ "럭키 피니시!");amount = 2;	sound.SP(player, org.bukkit.Sound.LEVEL_UP, 0.5F, 0.9F);}
				else if(luckysize <= 95){player.sendMessage(ChatColor.YELLOW +""+ChatColor.BOLD+ "빅 럭키 피니시!");amount = 5;	sound.SP(player, org.bukkit.Sound.LEVEL_UP, 0.7F, 1.0F);}
				else{player.sendMessage(ChatColor.YELLOW +""+ChatColor.BOLD+ "휴즈 럭키 피니시!");amount = 20;	sound.SP(player, org.bukkit.Sound.LEVEL_UP, 1.0F, 1.1F);}
			}
		}
		else
			amount = 0;
		if(event.getEntity().isCustomNameVisible() == true)
		{
			String name = ChatColor.stripColor(event.getEntity().getCustomName());
			String name2 = ChatColor.stripColor(event.getEntity().getName());
			YamlManager Monster  = Monster_YC.getNewConfig("Monster/MonsterList.yml");

			Set<String> a = Monster.getConfigurationSection("").getKeys(false);
			Object[] monsterlist =a.toArray();
			for(int count = 0; count < monsterlist.length; count ++)
			{
				if(ChatColor.stripColor(Monster.getString(monsterlist[count].toString()+".Name")).equalsIgnoreCase(ChatColor.stripColor(name)) == true||
						ChatColor.stripColor(Monster.getString(monsterlist[count].toString()+".Name")).equalsIgnoreCase(ChatColor.stripColor(name2)) == true)
				{
					LV.EXPadd(player, Monster.getLong(monsterlist[count].toString()+".EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(),amount* N.RandomNum(Monster.getInt(monsterlist[count].toString()+".MIN_Money"), Monster.getInt(monsterlist[count].toString()+".MAX_Money")));
					return;
				}
			}
		}
		EntityType MobType = event.getEntityType();
		switch(MobType)
		{
			case ZOMBIE :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.ZOMBIE.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.ZOMBIE.MIN_MONEY"), Config.getInt("Normal_Monster.ZOMBIE.MAX_MONEY"))*amount); return;
			case GIANT :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.GIANT.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.GIANT.MIN_MONEY"), Config.getInt("Normal_Monster.GIANT.MAX_MONEY"))*amount); return;
			case SKELETON :
				Skeleton s = (Skeleton)event.getEntity();
				if(s.getSkeletonType() == SkeletonType.NORMAL)
				{
					LV.EXPadd(player, Config.getLong("Normal_Monster.SKELETON.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SKELETON.MIN_MONEY"), Config.getInt("Normal_Monster.SKELETON.MAX_MONEY"))*amount); return;
				}
				else
				{
					LV.EXPadd(player, Config.getLong("Normal_Monster.NETHER_SKELETON.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.NETHER_SKELETON.MIN_MONEY"), Config.getInt("Normal_Monster.NETHER_SKELETON.MAX_MONEY"))*amount); return;
				}
			case ENDERMAN :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.ENDERMAN.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.ENDERMAN.MIN_MONEY"), Config.getInt("Normal_Monster.ENDERMAN.MAX_MONEY"))*amount); return;
			case CREEPER :
				Creeper c = (Creeper)event.getEntity();
				if(c.isPowered() == false)
				{
				 LV.EXPadd(player, Config.getLong("Normal_Monster.CREEPER.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.CREEPER.MIN_MONEY"), Config.getInt("Normal_Monster.CREEPER.MAX_MONEY"))*amount); return;
				}
				else
				{
					LV.EXPadd(player, Config.getLong("Normal_Monster.CHARGED_CREEPER.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.CHARGED_CREEPER.MIN_MONEY"), Config.getInt("Normal_Monster.CHARGED_CREEPER.MAX_MONEY"))*amount); return;
				}
			case SPIDER :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.SPIDER.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SPIDER.MIN_MONEY"), Config.getInt("Normal_Monster.SPIDER.MAX_MONEY"))*amount); return;
			case CAVE_SPIDER :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.CAVE_SPIDER.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.CAVE_SPIDER.MIN_MONEY"), Config.getInt("Normal_Monster.CAVE_SPIDER.MAX_MONEY"))*amount); return;
			case SILVERFISH :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.SILVERFISH.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SILVERFISH.MIN_MONEY"), Config.getInt("Normal_Monster.SILVERFISH.MAX_MONEY"))*amount); return;
			case ENDERMITE :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.ENDERMITE.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.ENDERMITE.MIN_MONEY"), Config.getInt("Normal_Monster.ENDERMITE.MAX_MONEY"))*amount); return;
			case SLIME :
				Slime sl = (Slime)event.getEntity();
				switch(sl.getSize())
				{
					case 1:
					LV.EXPadd(player, Config.getLong("Normal_Monster.SLIME_SMALL.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SLIME_SMALL.MIN_MONEY"), Config.getInt("Normal_Monster.SLIME_SMALL.MAX_MONEY"))*amount); return;
					case 2:
					case 3:
					LV.EXPadd(player, Config.getLong("Normal_Monster.SLIME_MIDDLE.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SLIME_MIDDLE.MIN_MONEY"), Config.getInt("Normal_Monster.SLIME_MIDDLE.MAX_MONEY"))*amount); return;
					case 4:
					LV.EXPadd(player, Config.getLong("Normal_Monster.SLIME_BIG.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SLIME_BIG.MIN_MONEY"), Config.getInt("Normal_Monster.SLIME_BIG.MAX_MONEY"))*amount); return;
					default:
					LV.EXPadd(player, Config.getLong("Normal_Monster.SLIME_HUGE.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SLIME_HUGE.MIN_MONEY"), Config.getInt("Normal_Monster.SLIME_HUGE.MAX_MONEY"))*amount); return;
				}
			case MAGMA_CUBE :
				MagmaCube ma = (MagmaCube)event.getEntity();
				switch(ma.getSize())
				{
					case 1:
					LV.EXPadd(player, Config.getLong("Normal_Monster.MAGMA_CUBE_SMALL.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.MAGMA_CUBE_SMALL.MIN_MONEY"), Config.getInt("Normal_Monster.MAGMA_CUBE_SMALL.MAX_MONEY"))*amount); return;
					case 2:
					case 3:
					LV.EXPadd(player, Config.getLong("Normal_Monster.MAGMA_CUBE_MIDDLE.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.MAGMA_CUBE_MIDDLE.MIN_MONEY"), Config.getInt("Normal_Monster.MAGMA_CUBE_MIDDLE.MAX_MONEY"))*amount); return;
					case 4:
					LV.EXPadd(player, Config.getLong("Normal_Monster.MAGMA_CUBE_BIG.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.MAGMA_CUBE_BIG.MIN_MONEY"), Config.getInt("Normal_Monster.MAGMA_CUBE_BIG.MAX_MONEY"))*amount); return;
					default:
					LV.EXPadd(player, Config.getLong("Normal_Monster.MAGMA_CUBE_HUGE.EXP"), event.getEntity().getLocation());
					ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.MAGMA_CUBE_HUGE.MIN_MONEY"), Config.getInt("Normal_Monster.MAGMA_CUBE_HUGE.MAX_MONEY"))*amount); return;
				}
			case BLAZE :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.BLAZE.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.BLAZE.MIN_MONEY"), Config.getInt("Normal_Monster.BLAZE.MAX_MONEY"))*amount); return;
			case GHAST :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.GHAST.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.GHAST.MIN_MONEY"), Config.getInt("Normal_Monster.GHAST.MAX_MONEY"))*amount); return;
			case PIG_ZOMBIE :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.PIG_ZOMBIE.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.PIG_ZOMBIE.MIN_MONEY"), Config.getInt("Normal_Monster.PIG_ZOMBIE.MAX_MONEY"))*amount); return;
			case WITCH :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.WITCH.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.WITCH.MIN_MONEY"), Config.getInt("Normal_Monster.WITCH.MAX_MONEY"))*amount); return;
			case WITHER :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.WITHER.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.WITHER.MIN_MONEY"), Config.getInt("Normal_Monster.WITHER.MAX_MONEY"))*amount); return;
			case ENDER_DRAGON :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.ENDER_DRAGON.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.ENDER_DRAGON.MIN_MONEY"), Config.getInt("Normal_Monster.ENDER_DRAGON.MAX_MONEY"))*amount); return;
			case GUARDIAN :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.GUARDIAN.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.GUARDIAN.MIN_MONEY"), Config.getInt("Normal_Monster.GUARDIAN.MAX_MONEY"))*amount); return;
			case SHEEP :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.SHEEP.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SHEEP.MIN_MONEY"), Config.getInt("Normal_Monster.SHEEP.MAX_MONEY"))*amount); return;
			case COW :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.COW.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.COW.MIN_MONEY"), Config.getInt("Normal_Monster.COW.MAX_MONEY"))*amount); return;
			case PIG :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.PIG.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.PIG.MIN_MONEY"), Config.getInt("Normal_Monster.PIG.MAX_MONEY"))*amount); return;
			case HORSE :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.HORSE.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.HORSE.MIN_MONEY"), Config.getInt("Normal_Monster.HORSE.MAX_MONEY"))*amount); return;
			case RABBIT :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.RABBIT.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.RABBIT.MIN_MONEY"), Config.getInt("Normal_Monster.RABBIT.MAX_MONEY"))*amount); return;
			case OCELOT :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.OCELOT.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.OCELOT.MIN_MONEY"), Config.getInt("Normal_Monster.OCELOT.MAX_MONEY"))*amount); return;
			case WOLF :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.WOLF.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.WOLF.MIN_MONEY"), Config.getInt("Normal_Monster.WOLF.MAX_MONEY"))*amount); return;
			case CHICKEN :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.CHICKEN.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.CHICKEN.MIN_MONEY"), Config.getInt("Normal_Monster.CHICKEN.MAX_MONEY"))*amount); return;
			case MUSHROOM_COW :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.MUSHROOM_COW.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.MUSHROOM_COW.MIN_MONEY"), Config.getInt("Normal_Monster.MUSHROOM_COW.MAX_MONEY"))*amount); return;
			case BAT :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.BAT.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.BAT.MIN_MONEY"), Config.getInt("Normal_Monster.BAT.MAX_MONEY"))*amount); return;
			case SQUID :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.SQUID.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SQUID.MIN_MONEY"), Config.getInt("Normal_Monster.SQUID.MAX_MONEY"))*amount); return;
			case VILLAGER :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.VILLAGER.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.VILLAGER.MIN_MONEY"), Config.getInt("Normal_Monster.VILLAGER.MAX_MONEY"))*amount); return;
			case SNOWMAN :
				 LV.EXPadd(player, Config.getLong("Normal_Monster.SNOWMAN.EXP"), event.getEntity().getLocation());
				 ID.MoneyDrop(event.getEntity().getLocation(), N.RandomNum(Config.getInt("Normal_Monster.SNOWMAN.MIN_MONEY"), Config.getInt("Normal_Monster.SNOWMAN.MAX_MONEY"))*amount); return;
		}
		return;
	}

	public void Quest(EntityDeathEvent event, Player player)
	{
		YamlController Party_YC = GBD.GoldBigDragon_Advanced.Main.Main.Party_YC;
	  	if(Party_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		stat.CreateNewStats(player);
	  	YamlManager YM = Party_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");

		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager QuestList  = GUI_YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = GUI_YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		if(Main.PartyJoiner.containsKey(player)==false)
		{
			Set<String> b = PlayerQuestList.getConfigurationSection("Started.").getKeys(false);
			Object[] a =b.toArray();
			for(int count = 0; count < b.size(); count++)
			{
				String QuestName = (String) a[count];
				int Flow = PlayerQuestList.getInt("Started."+QuestName+".Flow");
				if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Hunt"))
				{
					if(QuestList.contains(QuestName)==false)
					{
						PlayerQuestList.removeKey("Started."+QuestName);
						PlayerQuestList.saveConfig();
						return;
					}
					Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
					int Finish = 0;
					for(int counter = 0; counter < MobList.length; counter++)
					{
						String QMobName = QuestList.getString(QuestName+".FlowChart."+Flow+".Monster."+counter+".MonsterName");
						int MAX = QuestList.getInt(QuestName+".FlowChart."+Flow+".Monster."+counter+".Amount");
						String KilledName = "null";
						KilledName = event.getEntity().getName();
						if(event.getEntity().isCustomNameVisible() == true)
							KilledName = event.getEntity().getCustomName();
						if(QMobName.equalsIgnoreCase(KilledName) == true && MAX > PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter))
						{
							//퀘스트 진행도 알림//
							PlayerQuestList.set("Started."+QuestName+".Hunt."+counter, PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter)+1);
							PlayerQuestList.saveConfig();
						}
						if(MAX == PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter))
						{
							Finish = Finish+1;
						}
						if(Finish == MobList.length)
						{
							PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
							PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
							PlayerQuestList.removeKey("Started."+QuestName+".Hunt");
							PlayerQuestList.saveConfig();
							GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
							QGUI.QuestTypeRouter(player, QuestName);
							//퀘스트 완료 메시지//
							break;
						}
					}
				}
			}
		}
		else
		{
			Player[] PartyMember = Main.Party.get(Main.PartyJoiner.get(player)).getMember();
			for(int counta = 0; counta < PartyMember.length; counta++)
			{
				player = PartyMember[counta];
				if(event.getEntity().getLocation().getWorld() == player.getLocation().getWorld())
				{

					YamlManager Config = GUI_YC.getNewConfig("config.yml");
					
					if(event.getEntity().getLocation().distance(player.getLocation()) <= Config.getInt("Party.EXPShareDistance"))
					{
						PlayerQuestList  = GUI_YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						
						Set<String> b = PlayerQuestList.getConfigurationSection("Started.").getKeys(false);
						Object[] a =b.toArray();
						for(int count = 0; count < b.size(); count++)
						{
							String QuestName = (String) a[count];
							int Flow = PlayerQuestList.getInt("Started."+QuestName+".Flow");
							if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Hunt"))
							{
								Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
								int Finish = 0;
								for(int counter = 0; counter < MobList.length; counter++)
								{
									String QMobName = QuestList.getString(QuestName+".FlowChart."+Flow+".Monster."+counter+".MonsterName");
									int MAX = QuestList.getInt(QuestName+".FlowChart."+Flow+".Monster."+counter+".Amount");
									String KilledName = "null";
									KilledName = event.getEntity().getName();
									if(event.getEntity().isCustomNameVisible() == true)
										KilledName = event.getEntity().getCustomName();
									if(QMobName.equalsIgnoreCase(KilledName) == true && MAX > PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter))
									{
										//퀘스트 진행도 알림//
										PlayerQuestList.set("Started."+QuestName+".Hunt."+counter, PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter)+1);
										PlayerQuestList.saveConfig();
									}
									if(MAX == PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter))
									{
										Finish = Finish+1;
									}
									if(Finish == MobList.length)
									{
										PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
										PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
										PlayerQuestList.removeKey("Started."+QuestName+".Hunt");
										PlayerQuestList.saveConfig();
										GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
										QGUI.QuestTypeRouter(player, QuestName);
										//퀘스트 완료 메시지//
										break;
									}
								}
							}
						}	
					}
				}
			}
		}
	}
}