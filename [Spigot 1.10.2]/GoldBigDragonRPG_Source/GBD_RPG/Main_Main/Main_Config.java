package GBD_RPG.Main_Main;

import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;
import net.md_5.bungee.api.ChatColor;

public class Main_Config
{
    public void CheckConfig(YamlController YC)
	{
    	YamlManager Config = YC.getNewConfig("config.yml");
    	if(Config.contains("Version")==false)
    	{
		  	Config.set("Version", "Advanced");
		  	Config.set("Update", 20170527);
		  	Config.set("Server.BroadCastSecond", 30);
		  	Config.set("Server.EntitySpawn", true);
		  	Config.set("Server.PVP", true);
		  	Config.set("Server.AreaSettingWand", 286);
		  	Config.set("Server.Like_The_Mabinogi_Online_Stat_System", false);
		  	Config.set("Server.Max_Drop_Money", 100000);
		  	Config.set("Server.Level_Up_SkillPoint", 1);
		  	Config.set("Server.Level_Up_StatPoint", 10);
		  	Config.set("Server.MonsterSpawnEffect", 0);
		  	Config.set("Server.ChattingDistance", -1);
		  	Config.set("Server.DefaultJob", "초보자");
		  	Config.set("Server.CustomWeaponBreak", true);
			Config.set("Server.AntiExplode", true);
		  	Config.set("Server.PVP", true);
		  	Config.set("MaxStat.Level", 100);
		  	Config.set("MaxStat.Stats", 1500);
		  	Config.set("Event.Multiple_Proficiency_Get", 1);
		  	
		  	Config.set("Server.STR", "체력");
		  	Config.set("Server.DEX", "솜씨");
		  	Config.set("Server.INT", "지력");
		  	Config.set("Server.WILL", "의지");
		  	Config.set("Server.LUK", "행운");
		  	Config.set("Server.MoneyName", ChatColor.GOLD+""+ChatColor.BOLD+"Gold");
		  	Config.set("Server.AntiExplode", true);

		  	Config.set("Death.SystemOn", true);
		  	Config.set("Death.Spawn_Home.SetHealth", "100%");
		  	Config.set("Death.Spawn_Home.PenaltyEXP", "10%");
		  	Config.set("Death.Spawn_Home.PenaltConfigoney", "0%");
		  	Config.set("Death.Spawn_Here.SetHealth", "1%");
		  	Config.set("Death.Spawn_Here.PenaltyEXP", "15%");
		  	Config.set("Death.Spawn_Here.PenaltConfigoney", "10%");
		  	Config.set("Death.Spawn_Help.SetHealth", "1%");
		  	Config.set("Death.Spawn_Help.PenaltyEXP", "5%");
		  	Config.set("Death.Spawn_Help.PenaltConfigoney", "0%");
		  	Config.set("Death.Spawn_Item.SetHealth", "100%");
		  	Config.set("Death.Spawn_Item.PenaltyEXP", "0%");
		  	Config.set("Death.Spawn_Item.PenaltConfigoney", "0%");
		  	Config.set("Death.Track", -1);

		  	Config.set("Quest.AcceptMessage", ChatColor.GREEN+"[퀘스트] : " + ChatColor.YELLOW+"%QuestName%"+ChatColor.GREEN+" 퀘스트를 받았습니다!");
		  	Config.set("Quest.ClearMessage", ChatColor.DARK_AQUA+"[퀘스트] : "+ ChatColor.YELLOW + "%QuestName%"+ChatColor.DARK_AQUA+" 퀘스트를 완료하셨습니다!");

		  	Config.set("NPC.Shaman.BuffCoolTime", 600);
		  	
		  	Config.set("Party.EXPShareDistance", 50);
		  	Config.set("Party.MaxPartyUnit", 8);

		  	Config.set("Event.Multiple_Level_Up_SkillPoint", 1);
		  	Config.set("Event.Multiple_Level_Up_StatPoint", 1);
		  	Config.set("Event.Multiple_EXP_Get", 1);
		  	Config.set("Event.DropChance", 1);
		  	Config.set("Event.Multiple_Proficiency_Get", 1);
		  	
		  	Config.set("DefaultStat.SkillPoint", 0);
		  	Config.set("DefaultStat.StatPoint", 0);
		  	Config.set("DefaultStat.MaxEXP", 100);
		  	Config.set("DefaultStat.HP", 20);
		  	Config.set("DefaultStat.Wond", 0);
		  	Config.set("DefaultStat.MP", 10);
		  	Config.set("DefaultStat.STR", 10);
		  	Config.set("DefaultStat.DEX", 10);
		  	Config.set("DefaultStat.INT", 10);
		  	Config.set("DefaultStat.LUK", 10);
		  	Config.set("DefaultStat.WILL", 10);
		  	Config.set("DefaultStat.DEF", 0);
		  	Config.set("DefaultStat.DEFcrash", 0);
		  	Config.set("DefaultStat.Protect", 0);
		  	Config.set("DefaultStat.Magic_DEF", 0);
		  	Config.set("DefaultStat.MagicDEFcrash", 0);
		  	Config.set("DefaultStat.Magic_Protect", 0);
		  	Config.set("DefaultStat.Balance", 1);
		  	Config.set("DefaultStat.Critical", 1);
		  	
		  	Config.set("MaxStat.Level", 100);
		  	Config.set("MaxStat.Stats", 1500);

		  	Config.set("Getting.Wood.EXP", 1);
		  	Config.set("Getting.Wood.Money", 0);
		  	Config.set("Getting.Coal.EXP", 3);
		  	Config.set("Getting.Coal.Money", 0);
		  	Config.set("Getting.Iron.EXP", 5);
		  	Config.set("Getting.Iron.Money", 0);
		  	Config.set("Getting.Gold.EXP", 10);
		  	Config.set("Getting.Gold.Money", 0);
		  	Config.set("Getting.Diamond.EXP", 50);
		  	Config.set("Getting.Diamond.Money", 0);
		  	Config.set("Getting.Emerald.EXP", 100);
		  	Config.set("Getting.Emerald.Money", 0);
		  	Config.set("Getting.RedStone.EXP", 20);
		  	Config.set("Getting.RedStone.Money", 0);
		  	Config.set("Getting.Lapis.EXP", 20);
		  	Config.set("Getting.Lapis.Money", 0);
		  	Config.set("Getting.NetherQuartz.EXP", 10);
		  	Config.set("Getting.NetherQuartz.Money", 0);
		  	
		  	Config.set("Normal_Monster.ZOMBIE.EXP", 10);
		  	Config.set("Normal_Monster.ZOMBIE.MIN_MONEY", 10);
		  	Config.set("Normal_Monster.ZOMBIE.MAX_MONEY", 20);
		  	Config.set("Normal_Monster.GIANT.EXP", 20);
		  	Config.set("Normal_Monster.GIANT.MIN_MONEY", 40);
		  	Config.set("Normal_Monster.GIANT.MAX_MONEY", 80);
		  	Config.set("Normal_Monster.SKELETON.EXP", 10);
		  	Config.set("Normal_Monster.SKELETON.MIN_MONEY", 10);
		  	Config.set("Normal_Monster.SKELETON.MAX_MONEY", 20);
		  	Config.set("Normal_Monster.NETHER_SKELETON.EXP", 20);
		  	Config.set("Normal_Monster.NETHER_SKELETON.MIN_MONEY", 40);
		  	Config.set("Normal_Monster.NETHER_SKELETON.MAX_MONEY", 80);
		  	Config.set("Normal_Monster.ENDERMAN.EXP", 25);
		  	Config.set("Normal_Monster.ENDERMAN.MIN_MONEY", 60);
		  	Config.set("Normal_Monster.ENDERMAN.MAX_MONEY", 120);
		  	Config.set("Normal_Monster.CREEPER.EXP", 15);
		  	Config.set("Normal_Monster.CREEPER.MIN_MONEY", 15);
		  	Config.set("Normal_Monster.CREEPER.MAX_MONEY", 30);
		  	Config.set("Normal_Monster.CHARGED_CREEPER.EXP", 30);
		  	Config.set("Normal_Monster.CHARGED_CREEPER.MIN_MONEY", 50);
		  	Config.set("Normal_Monster.CHARGED_CREEPER.MAX_MONEY", 100);
		  	Config.set("Normal_Monster.SPIDER.EXP", 8);
		  	Config.set("Normal_Monster.SPIDER.MIN_MONEY", 5);
		  	Config.set("Normal_Monster.SPIDER.MAX_MONEY", 16);
		  	Config.set("Normal_Monster.CAVE_SPIDER.EXP", 8);
		  	Config.set("Normal_Monster.CAVE_SPIDER.MIN_MONEY", 12);
		  	Config.set("Normal_Monster.CAVE_SPIDER.MAX_MONEY", 20);
		  	Config.set("Normal_Monster.SILVERFISH.EXP", 1);
		  	Config.set("Normal_Monster.SILVERFISH.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.SILVERFISH.MAX_MONEY", 3);
		  	Config.set("Normal_Monster.ENDERMITE.EXP", 1);
		  	Config.set("Normal_Monster.ENDERMITE.MIN_MONEY", 3);
		  	Config.set("Normal_Monster.ENDERMITE.MAX_MONEY", 9);
		  	Config.set("Normal_Monster.SLIME_SMALL.EXP", 1);
		  	Config.set("Normal_Monster.SLIME_SMALL.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.SLIME_SMALL.MAX_MONEY", 4);
		  	Config.set("Normal_Monster.SLIME_MIDDLE.EXP", 2);
		  	Config.set("Normal_Monster.SLIME_MIDDLE.MIN_MONEY", 4);
		  	Config.set("Normal_Monster.SLIME_MIDDLE.MAX_MONEY", 8);
		  	Config.set("Normal_Monster.SLIME_BIG.EXP", 4);
		  	Config.set("Normal_Monster.SLIME_BIG.MIN_MONEY", 10);
		  	Config.set("Normal_Monster.SLIME_BIG.MAX_MONEY", 20);
		  	Config.set("Normal_Monster.SLIME_HUGE.EXP", 20);
		  	Config.set("Normal_Monster.SLIME_HUGE.MIN_MONEY", 40);
		  	Config.set("Normal_Monster.SLIME_HUGE.MAX_MONEY", 80);
		  	Config.set("Normal_Monster.MAGMA_CUBE_SMALL.EXP", 2);
		  	Config.set("Normal_Monster.MAGMA_CUBE_SMALL.MIN_MONEY", 2);
		  	Config.set("Normal_Monster.MAGMA_CUBE_SMALL.MAX_MONEY", 5);
		  	Config.set("Normal_Monster.MAGMA_CUBE_MIDDLE.EXP", 3);
		  	Config.set("Normal_Monster.MAGMA_CUBE_MIDDLE.MIN_MONEY", 5);
		  	Config.set("Normal_Monster.MAGMA_CUBE_MIDDLE.MAX_MONEY", 10);
		  	Config.set("Normal_Monster.MAGMA_CUBE_BIG.EXP", 6);
		  	Config.set("Normal_Monster.MAGMA_CUBE_BIG.MIN_MONEY", 15);
		  	Config.set("Normal_Monster.MAGMA_CUBE_BIG.MAX_MONEY", 30);
		  	Config.set("Normal_Monster.MAGMA_CUBE_HUGE.EXP", 35);
		  	Config.set("Normal_Monster.MAGMA_CUBE_HUGE.MIN_MONEY", 60);
		  	Config.set("Normal_Monster.MAGMA_CUBE_HUGE.MAX_MONEY", 100);
		  	Config.set("Normal_Monster.BLAZE.EXP", 25);
		  	Config.set("Normal_Monster.BLAZE.MIN_MONEY", 80);
		  	Config.set("Normal_Monster.BLAZE.MAX_MONEY", 130);
		  	Config.set("Normal_Monster.GHAST.EXP", 25);
		  	Config.set("Normal_Monster.GHAST.MIN_MONEY", 70);
		  	Config.set("Normal_Monster.GHAST.MAX_MONEY", 140);
		  	Config.set("Normal_Monster.PIG_ZOMBIE.EXP", 15);
		  	Config.set("Normal_Monster.PIG_ZOMBIE.MIN_MONEY", 30);
		  	Config.set("Normal_Monster.PIG_ZOMBIE.MAX_MONEY", 45);
		  	Config.set("Normal_Monster.WITCH.EXP", 35);
		  	Config.set("Normal_Monster.WITCH.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.WITCH.MAX_MONEY", 100);
		  	Config.set("Normal_Monster.WITHER.EXP", 150);
		  	Config.set("Normal_Monster.WITHER.MIN_MONEY", 1500);
		  	Config.set("Normal_Monster.WITHER.MAX_MONEY", 2000);
		  	Config.set("Normal_Monster.ENDER_DRAGON.EXP", 500);
		  	Config.set("Normal_Monster.ENDER_DRAGON.MIN_MONEY", 2000);
		  	Config.set("Normal_Monster.ENDER_DRAGON.MAX_MONEY", 7000);
		  	Config.set("Normal_Monster.GUARDIAN.EXP", 20);
		  	Config.set("Normal_Monster.GUARDIAN.MIN_MONEY", 15);
		  	Config.set("Normal_Monster.GUARDIAN.MAX_MONEY", 45);
		  	Config.set("Normal_Monster.SHEEP.EXP", 1);
		  	Config.set("Normal_Monster.SHEEP.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.SHEEP.MAX_MONEY", 1);
		  	Config.set("Normal_Monster.COW.EXP", 1);
		  	Config.set("Normal_Monster.COW.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.COW.MAX_MONEY", 1);
		  	Config.set("Normal_Monster.PIG.EXP", 1);
		  	Config.set("Normal_Monster.PIG.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.PIG.MAX_MONEY", 1);
		  	Config.set("Normal_Monster.HORSE.EXP", 1);
		  	Config.set("Normal_Monster.HORSE.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.HORSE.MAX_MONEY", 1);
		  	Config.set("Normal_Monster.RABBIT.EXP", 1);
		  	Config.set("Normal_Monster.RABBIT.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.RABBIT.MAX_MONEY", 1);
		  	Config.set("Normal_Monster.OCELOT.EXP", 1);
		  	Config.set("Normal_Monster.OCELOT.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.OCELOT.MAX_MONEY", 1);
		  	Config.set("Normal_Monster.WOLF.EXP", 2);
		  	Config.set("Normal_Monster.WOLF.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.WOLF.MAX_MONEY", 8);
		  	Config.set("Normal_Monster.CHICKEN.EXP", 1);
		  	Config.set("Normal_Monster.CHICKEN.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.CHICKEN.MAX_MONEY", 1);
		  	Config.set("Normal_Monster.MUSHROOM_COW.EXP", 1);
		  	Config.set("Normal_Monster.MUSHROOM_COW.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.MUSHROOM_COW.MAX_MONEY", 5);
		  	Config.set("Normal_Monster.BAT.EXP", 1);
		  	Config.set("Normal_Monster.BAT.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.BAT.MAX_MONEY", 1);
		  	Config.set("Normal_Monster.SQUID.EXP", 1);
		  	Config.set("Normal_Monster.SQUID.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.SQUID.MAX_MONEY", 1);
		  	Config.set("Normal_Monster.VILLAGER.EXP", 1);
		  	Config.set("Normal_Monster.VILLAGER.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.VILLAGER.MAX_MONEY", 300);
		  	Config.set("Normal_Monster.SNOWMAN.EXP", 1);
		  	Config.set("Normal_Monster.SNOWMAN.MIN_MONEY", 1);
		  	Config.set("Normal_Monster.SNOWMAN.MAX_MONEY", 10);
		  	Config.set("Normal_Monster.SHULKER.EXP", 35);
		  	Config.set("Normal_Monster.SHULKER.MIN_MONEY", 20);
		  	Config.set("Normal_Monster.SHULKER.MAX_MONEY", 50);
		  	Config.set("Normal_Monster.POLAR_BEAR.EXP", 40);
		  	Config.set("Normal_Monster.POLAR_BEAR.MIN_MONEY", 25);
		  	Config.set("Normal_Monster.POLAR_BEAR.MAX_MONEY", 45);
		  	Config.saveConfig();
    	}

		if(Config.contains("Server.MabinogiMoneySystem"))
			GBD_RPG.Main_Main.Main_ServerOption.MoneySystem = Config.getBoolean("Server.MabinogiMoneySystem");
		if(Config.contains("Server.STR"))
			GBD_RPG.Main_Main.Main_ServerOption.STR = Config.getString("Server.STR");
		if(Config.contains("Server.DEX"))
			GBD_RPG.Main_Main.Main_ServerOption.DEX = Config.getString("Server.DEX");
		if(Config.contains("Server.INT"))
			GBD_RPG.Main_Main.Main_ServerOption.INT = Config.getString("Server.INT");
		if(Config.contains("Server.WILL"))
			GBD_RPG.Main_Main.Main_ServerOption.WILL = Config.getString("Server.WILL");
		if(Config.contains("Server.LUK"))
			GBD_RPG.Main_Main.Main_ServerOption.LUK = Config.getString("Server.LUK");
		if(Config.contains("Server.MoneyName"))
			GBD_RPG.Main_Main.Main_ServerOption.Money = Config.getString("Server.MoneyName");
		if(Config.contains("Server.STR_Lore"))
			GBD_RPG.Main_Main.Main_ServerOption.STR_Lore = Config.getString("Server.STR_Lore");
		if(Config.contains("Server.DEX_Lore"))
			GBD_RPG.Main_Main.Main_ServerOption.DEX_Lore = Config.getString("Server.DEX_Lore");
		if(Config.contains("Server.INT_Lore"))
			GBD_RPG.Main_Main.Main_ServerOption.INT_Lore = Config.getString("Server.INT_Lore");
		if(Config.contains("Server.WILL_Lore"))
			GBD_RPG.Main_Main.Main_ServerOption.WILL_Lore = Config.getString("Server.WILL_Lore");
		if(Config.contains("Server.LUK_Lore"))
			GBD_RPG.Main_Main.Main_ServerOption.LUK_Lore = Config.getString("Server.LUK_Lore");
		if(Config.contains("Server.Money.1.ID"))
			GBD_RPG.Main_Main.Main_ServerOption.Money1ID = (short) Config.getInt("Server.Money.1.ID");
		if(Config.contains("Server.Money.2.ID"))
			GBD_RPG.Main_Main.Main_ServerOption.Money2ID = (short) Config.getInt("Server.Money.2.ID");
		if(Config.contains("Server.Money.3.ID"))
			GBD_RPG.Main_Main.Main_ServerOption.Money3ID = (short) Config.getInt("Server.Money.3.ID");
		if(Config.contains("Server.Money.4.ID"))
			GBD_RPG.Main_Main.Main_ServerOption.Money4ID = (short) Config.getInt("Server.Money.4.ID");
		if(Config.contains("Server.Money.5.ID"))
			GBD_RPG.Main_Main.Main_ServerOption.Money5ID = (short) Config.getInt("Server.Money.5.ID");
		if(Config.contains("Server.Money.6.ID"))
			GBD_RPG.Main_Main.Main_ServerOption.Money6ID = (short) Config.getInt("Server.Money.6.ID");
		if(Config.contains("Server.Money.1.DATA"))
			GBD_RPG.Main_Main.Main_ServerOption.Money1DATA = (byte) Config.getInt("Server.Money.1.DATA");
		if(Config.contains("Server.Money.2.DATA"))
			GBD_RPG.Main_Main.Main_ServerOption.Money2DATA = (byte) Config.getInt("Server.Money.2.DATA");
		if(Config.contains("Server.Money.3.DATA"))
			GBD_RPG.Main_Main.Main_ServerOption.Money3DATA = (byte) Config.getInt("Server.Money.3.DATA");
		if(Config.contains("Server.Money.4.DATA"))
			GBD_RPG.Main_Main.Main_ServerOption.Money4DATA = (byte) Config.getInt("Server.Money.4.DATA");
		if(Config.contains("Server.Money.5.DATA"))
			GBD_RPG.Main_Main.Main_ServerOption.Money5DATA = (byte) Config.getInt("Server.Money.5.DATA");
		if(Config.contains("Server.Money.6.DATA"))
			GBD_RPG.Main_Main.Main_ServerOption.Money6DATA = (byte) Config.getInt("Server.Money.6.DATA");
		if(Config.contains("Server.Damage"))
			GBD_RPG.Main_Main.Main_ServerOption.Damage = Config.getString("Server.Damage");
		if(Config.contains("Server.MagicDamage"))
			GBD_RPG.Main_Main.Main_ServerOption.MagicDamage = Config.getString("Server.MagicDamage");
		if(Config.contains("Server.AntiExplode"))
			GBD_RPG.Main_Main.Main_ServerOption.AntiExplode = Config.getBoolean("Server.AntiExplode");
		if(Config.contains("Death.ReviveItem"))
			GBD_RPG.Main_Main.Main_ServerOption.DeathRevive = Config.getItemStack("Death.ReviveItem");
		if(Config.contains("Death.RescueItem"))
			GBD_RPG.Main_Main.Main_ServerOption.DeathRescue = Config.getItemStack("Death.RescueItem");
		
		
		GBD_RPG.Main_Main.Main_ServerOption.MaxLevel = Config.getInt("MaxStat.Level");
		GBD_RPG.Main_Main.Main_ServerOption.MaxStats = Config.getInt("MaxStat.Stats");
		GBD_RPG.Main_Main.Main_ServerOption.PVP = Config.getBoolean("Server.PVP");
		GBD_RPG.Main_Main.Main_ServerOption.MaxDropMoney = Config.getLong("Server.Max_Drop_Money");
		GBD_RPG.Main_Main.Main_ServerOption.EXPShareDistance = Config.getInt("Party.EXPShareDistance");

		GBD_RPG.Main_Main.Main_ServerOption.Event_SkillPoint = (byte) Config.getInt("Event.Multiple_Level_Up_SkillPoint");
		GBD_RPG.Main_Main.Main_ServerOption.Event_StatPoint = (byte) Config.getInt("Event.Multiple_Level_Up_StatPoint");
		GBD_RPG.Main_Main.Main_ServerOption.Event_DropChance = (byte) Config.getInt("Event.DropChance");
		GBD_RPG.Main_Main.Main_ServerOption.Event_Exp = (byte) Config.getInt("Event.Multiple_EXP_Get");
		GBD_RPG.Main_Main.Main_ServerOption.Event_Proficiency = (byte) Config.getInt("Event.Multiple_Proficiency_Get");
		
		GBD_RPG.Main_Main.Main_ServerOption.LevelUpPerSkillPoint = (byte) Config.getInt("Server.Level_Up_SkillPoint");
		GBD_RPG.Main_Main.Main_ServerOption.LevelUpPerStatPoint = (byte) Config.getInt("Server.Level_Up_StatPoint");
    		
		Config = YC.getNewConfig("MapImageURL.yml");
    	if(Config.contains("GBD.URL") == false)
    	{
    		Config.set("KoreaLanguage(UTF-8)->JavaEntityLanguage", "http://itpro.cz/juniconv/");
		  	Config.set("GBD.URL", "http://cafeptthumb3.phinf.naver.net/20140309_183/dnwndugod642_1394374547812opRrb_PNG/GBD%28classic%29.png?type=w740");
		  	Config.set("GBD.Xcenter", 0);
		  	Config.set("GBD.Ycenter", 0);
		  	Config.set("WhatTheHorrible.URL", "http://cafeptthumb3.phinf.naver.net/20150828_126/dnwndugod642_1440694230353y2Dsp_PNG/%B9%B9%BE%DF%C0%CC%B0%C5_%B9%AB%BC%AD%BF%F6.png?type=w740");
		  	Config.set("WhatTheHorrible.Xcenter", 0);
		  	Config.set("WhatTheHorrible.Ycenter", 0);
		  	Config.set("MinimicAngry.URL", "http://cafeptthumb3.phinf.naver.net/20150828_84/dnwndugod642_1440694230655boUgx_PNG/%B9%CC%B4%CF%B9%CD_%C4%BC%BE%D32.png?type=w740");
		  	Config.set("MinimicAngry.Xcenter", 0);
		  	Config.set("MinimicAngry.Ycenter", 0);
		  	Config.saveConfig();
    	}

    	Config = YC.getNewConfig("Level.yml");
    	if(Config.contains(1+"") == false)
    	{
    		long EXP = 100;
    		for(int level = 1; level < 250; level++)
    		{
    			if(level + ((1.1) * EXP)< 0 ||level + (1.1) * EXP> Long.MAX_VALUE)
    				break;
    			else
    			{
    	    		Config.set(level+"", EXP);
    				EXP = (long) (level + (1.1) * EXP);
    			}
    		}
    		Config.saveConfig();
    	}
	  	return;
	}
}
