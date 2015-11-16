package GBD.GoldBigDragon_Advanced.Config;

import net.md_5.bungee.api.ChatColor;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class configConfig
{
    static YamlManager YM;
    public void CreateNewConfig(YamlController YC)
	{
    	YM = YC.getNewConfig("config.yml");
    	if(YM.contains("Version") == false)
    	{
		  	YM.set("Version", "Advanced");
		  	YM.set("Update", 20151116);
		  	YM.set("Server.BroadCastSecond", 30);
		  	YM.set("Server.EntitySpawn", true);
		  	YM.set("Server.AttackDelay", false);
		  	YM.set("Server.AreaSettingWand", 286);
		  	YM.set("Server.Like_The_Mabinogi_Online_Stat_System", false);
		  	YM.set("Server.Max_Drop_Money", 100000);
		  	YM.set("Server.Level_Up_SkillPoint", 1);
		  	YM.set("Server.Level_Up_StatPoint", 10);
		  	YM.set("Server.MonsterSpawnEffect", 0);
		  	YM.set("Server.MaxLevel", 200);
		  	YM.set("Server.ChattingDistance", -1);
		  	YM.set("Server.DefaultJob", "초보자");
		  	YM.set("Server.CustomWeaponBreak", true);
		  	
		  	YM.set("Server.STR", "체력");
		  	YM.set("Server.DEX", "솜씨");
		  	YM.set("Server.INT", "지력");
		  	YM.set("Server.WILL", "의지");
		  	YM.set("Server.LUK", "행운");
		  	YM.set("Server.MoneyName", ChatColor.GOLD+""+ChatColor.BOLD+"Gold");
		  	
		  	YM.set("Death.PenaltyEXP", "10%");
		  	YM.set("Death.PenaltyMoney", "2%");
		  	

		  	YM.set("Quest.AcceptMessage", ChatColor.GREEN+"[퀘스트] : " + ChatColor.YELLOW+"%QuestName%"+ChatColor.GREEN+" 퀘스트를 받았습니다!");
		  	YM.set("Quest.ClearMessage", ChatColor.DARK_AQUA+"[퀘스트] : "+ ChatColor.YELLOW + "%QuestName%"+ChatColor.DARK_AQUA+" 퀘스트를 완료하셨습니다!");

		  	YM.set("NPC.Shaman.BuffCoolTime", 600);
		  	
		  	YM.set("Party.EXPShareDistance", 50);
		  	YM.set("Party.MaxPartyUnit", 8);

		  	YM.set("Event.Multiple_Level_Up_SkillPoint", 1);
		  	YM.set("Event.Multiple_Level_Up_StatPoint", 1);
		  	YM.set("Event.Multiple_EXP_Get", 1);
		  	YM.set("Event.DropChance", 1);
		  	
		  	YM.set("DefaultStat.SkillPoint", 0);
		  	YM.set("DefaultStat.StatPoint", 0);
		  	YM.set("DefaultStat.MaxEXP", 100);
		  	YM.set("DefaultStat.HP", 20);
		  	YM.set("DefaultStat.Wond", 0);
		  	YM.set("DefaultStat.MP", 10);
		  	YM.set("DefaultStat.STR", 10);
		  	YM.set("DefaultStat.DEX", 10);
		  	YM.set("DefaultStat.INT", 10);
		  	YM.set("DefaultStat.LUK", 10);
		  	YM.set("DefaultStat.WILL", 10);
		  	YM.set("DefaultStat.DEF", 0);
		  	YM.set("DefaultStat.DEFcrash", 0);
		  	YM.set("DefaultStat.Protect", 0);
		  	YM.set("DefaultStat.Magic_DEF", 0);
		  	YM.set("DefaultStat.MagicDEFcrash", 0);
		  	YM.set("DefaultStat.Magic_Protect", 0);
		  	YM.set("DefaultStat.Balance", 1);
		  	YM.set("DefaultStat.Critical", 1);

		  	YM.set("Getting.Wood.EXP", 1);
		  	YM.set("Getting.Wood.Money", 0);
		  	YM.set("Getting.Coal.EXP", 3);
		  	YM.set("Getting.Coal.Money", 0);
		  	YM.set("Getting.Iron.EXP", 5);
		  	YM.set("Getting.Iron.Money", 0);
		  	YM.set("Getting.Gold.EXP", 10);
		  	YM.set("Getting.Gold.Money", 0);
		  	YM.set("Getting.Diamond.EXP", 50);
		  	YM.set("Getting.Diamond.Money", 0);
		  	YM.set("Getting.Emerald.EXP", 100);
		  	YM.set("Getting.Emerald.Money", 0);
		  	YM.set("Getting.RedStone.EXP", 20);
		  	YM.set("Getting.RedStone.Money", 0);
		  	YM.set("Getting.Lapis.EXP", 20);
		  	YM.set("Getting.Lapis.Money", 0);
		  	YM.set("Getting.NetherQuartz.EXP", 10);
		  	YM.set("Getting.NetherQuartz.Money", 0);
		  	
		  	YM.set("Normal_Monster.ZOMBIE.EXP", 10);
		  	YM.set("Normal_Monster.ZOMBIE.MIN_MONEY", 10);
		  	YM.set("Normal_Monster.ZOMBIE.MAX_MONEY", 20);
		  	YM.set("Normal_Monster.GIANT.EXP", 20);
		  	YM.set("Normal_Monster.GIANT.MIN_MONEY", 40);
		  	YM.set("Normal_Monster.GIANT.MAX_MONEY", 80);
		  	YM.set("Normal_Monster.SKELETON.EXP", 10);
		  	YM.set("Normal_Monster.SKELETON.MIN_MONEY", 10);
		  	YM.set("Normal_Monster.SKELETON.MAX_MONEY", 20);
		  	YM.set("Normal_Monster.NETHER_SKELETON.EXP", 20);
		  	YM.set("Normal_Monster.NETHER_SKELETON.MIN_MONEY", 40);
		  	YM.set("Normal_Monster.NETHER_SKELETON.MAX_MONEY", 80);
		  	YM.set("Normal_Monster.ENDERMAN.EXP", 25);
		  	YM.set("Normal_Monster.ENDERMAN.MIN_MONEY", 60);
		  	YM.set("Normal_Monster.ENDERMAN.MAX_MONEY", 120);
		  	YM.set("Normal_Monster.CREEPER.EXP", 15);
		  	YM.set("Normal_Monster.CREEPER.MIN_MONEY", 15);
		  	YM.set("Normal_Monster.CREEPER.MAX_MONEY", 30);
		  	YM.set("Normal_Monster.CHARGED_CREEPER.EXP", 30);
		  	YM.set("Normal_Monster.CHARGED_CREEPER.MIN_MONEY", 50);
		  	YM.set("Normal_Monster.CHARGED_CREEPER.MAX_MONEY", 100);
		  	YM.set("Normal_Monster.SPIDER.EXP", 8);
		  	YM.set("Normal_Monster.SPIDER.MIN_MONEY", 5);
		  	YM.set("Normal_Monster.SPIDER.MAX_MONEY", 16);
		  	YM.set("Normal_Monster.CAVE_SPIDER.EXP", 8);
		  	YM.set("Normal_Monster.CAVE_SPIDER.MIN_MONEY", 12);
		  	YM.set("Normal_Monster.CAVE_SPIDER.MAX_MONEY", 20);
		  	YM.set("Normal_Monster.SILVERFISH.EXP", 1);
		  	YM.set("Normal_Monster.SILVERFISH.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.SILVERFISH.MAX_MONEY", 3);
		  	YM.set("Normal_Monster.ENDERMITE.EXP", 1);
		  	YM.set("Normal_Monster.ENDERMITE.MIN_MONEY", 3);
		  	YM.set("Normal_Monster.ENDERMITE.MAX_MONEY", 9);
		  	YM.set("Normal_Monster.SLIME_SMALL.EXP", 1);
		  	YM.set("Normal_Monster.SLIME_SMALL.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.SLIME_SMALL.MAX_MONEY", 4);
		  	YM.set("Normal_Monster.SLIME_MIDDLE.EXP", 2);
		  	YM.set("Normal_Monster.SLIME_MIDDLE.MIN_MONEY", 4);
		  	YM.set("Normal_Monster.SLIME_MIDDLE.MAX_MONEY", 8);
		  	YM.set("Normal_Monster.SLIME_BIG.EXP", 4);
		  	YM.set("Normal_Monster.SLIME_BIG.MIN_MONEY", 10);
		  	YM.set("Normal_Monster.SLIME_BIG.MAX_MONEY", 20);
		  	YM.set("Normal_Monster.SLIME_HUGE.EXP", 20);
		  	YM.set("Normal_Monster.SLIME_HUGE.MIN_MONEY", 40);
		  	YM.set("Normal_Monster.SLIME_HUGE.MAX_MONEY", 80);
		  	YM.set("Normal_Monster.MAGMA_CUBE_SMALL.EXP", 2);
		  	YM.set("Normal_Monster.MAGMA_CUBE_SMALL.MIN_MONEY", 2);
		  	YM.set("Normal_Monster.MAGMA_CUBE_SMALL.MAX_MONEY", 5);
		  	YM.set("Normal_Monster.MAGMA_CUBE_MIDDLE.EXP", 3);
		  	YM.set("Normal_Monster.MAGMA_CUBE_MIDDLE.MIN_MONEY", 5);
		  	YM.set("Normal_Monster.MAGMA_CUBE_MIDDLE.MAX_MONEY", 10);
		  	YM.set("Normal_Monster.MAGMA_CUBE_BIG.EXP", 6);
		  	YM.set("Normal_Monster.MAGMA_CUBE_BIG.MIN_MONEY", 15);
		  	YM.set("Normal_Monster.MAGMA_CUBE_BIG.MAX_MONEY", 30);
		  	YM.set("Normal_Monster.MAGMA_CUBE_HUGE.EXP", 35);
		  	YM.set("Normal_Monster.MAGMA_CUBE_HUGE.MIN_MONEY", 60);
		  	YM.set("Normal_Monster.MAGMA_CUBE_HUGE.MAX_MONEY", 100);
		  	YM.set("Normal_Monster.BLAZE.EXP", 25);
		  	YM.set("Normal_Monster.BLAZE.MIN_MONEY", 80);
		  	YM.set("Normal_Monster.BLAZE.MAX_MONEY", 130);
		  	YM.set("Normal_Monster.GHAST.EXP", 25);
		  	YM.set("Normal_Monster.GHAST.MIN_MONEY", 70);
		  	YM.set("Normal_Monster.GHAST.MAX_MONEY", 140);
		  	YM.set("Normal_Monster.PIG_ZOMBIE.EXP", 15);
		  	YM.set("Normal_Monster.PIG_ZOMBIE.MIN_MONEY", 30);
		  	YM.set("Normal_Monster.PIG_ZOMBIE.MAX_MONEY", 45);
		  	YM.set("Normal_Monster.WITCH.EXP", 35);
		  	YM.set("Normal_Monster.WITCH.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.WITCH.MAX_MONEY", 100);
		  	YM.set("Normal_Monster.WITHER.EXP", 150);
		  	YM.set("Normal_Monster.WITHER.MIN_MONEY", 1500);
		  	YM.set("Normal_Monster.WITHER.MAX_MONEY", 2000);
		  	YM.set("Normal_Monster.ENDER_DRAGON.EXP", 500);
		  	YM.set("Normal_Monster.ENDER_DRAGON.MIN_MONEY", 2000);
		  	YM.set("Normal_Monster.ENDER_DRAGON.MAX_MONEY", 7000);
		  	YM.set("Normal_Monster.GUARDIAN.EXP", 20);
		  	YM.set("Normal_Monster.GUARDIAN.MIN_MONEY", 15);
		  	YM.set("Normal_Monster.GUARDIAN.MAX_MONEY", 45);
		  	YM.set("Normal_Monster.SHEEP.EXP", 1);
		  	YM.set("Normal_Monster.SHEEP.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.SHEEP.MAX_MONEY", 1);
		  	YM.set("Normal_Monster.COW.EXP", 1);
		  	YM.set("Normal_Monster.COW.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.COW.MAX_MONEY", 1);
		  	YM.set("Normal_Monster.PIG.EXP", 1);
		  	YM.set("Normal_Monster.PIG.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.PIG.MAX_MONEY", 1);
		  	YM.set("Normal_Monster.HORSE.EXP", 1);
		  	YM.set("Normal_Monster.HORSE.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.HORSE.MAX_MONEY", 1);
		  	YM.set("Normal_Monster.RABBIT.EXP", 1);
		  	YM.set("Normal_Monster.RABBIT.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.RABBIT.MAX_MONEY", 1);
		  	YM.set("Normal_Monster.OCELOT.EXP", 1);
		  	YM.set("Normal_Monster.OCELOT.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.OCELOT.MAX_MONEY", 1);
		  	YM.set("Normal_Monster.WOLF.EXP", 2);
		  	YM.set("Normal_Monster.WOLF.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.WOLF.MAX_MONEY", 8);
		  	YM.set("Normal_Monster.CHICKEN.EXP", 1);
		  	YM.set("Normal_Monster.CHICKEN.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.CHICKEN.MAX_MONEY", 1);
		  	YM.set("Normal_Monster.MUSHROOM_COW.EXP", 1);
		  	YM.set("Normal_Monster.MUSHROOM_COW.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.MUSHROOM_COW.MAX_MONEY", 5);
		  	YM.set("Normal_Monster.BAT.EXP", 1);
		  	YM.set("Normal_Monster.BAT.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.BAT.MAX_MONEY", 1);
		  	YM.set("Normal_Monster.SQUID.EXP", 1);
		  	YM.set("Normal_Monster.SQUID.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.SQUID.MAX_MONEY", 1);
		  	YM.set("Normal_Monster.VILLAGER.EXP", 1);
		  	YM.set("Normal_Monster.VILLAGER.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.VILLAGER.MAX_MONEY", 300);
		  	YM.set("Normal_Monster.SNOWMAN.EXP", 1);
		  	YM.set("Normal_Monster.SNOWMAN.MIN_MONEY", 1);
		  	YM.set("Normal_Monster.SNOWMAN.MAX_MONEY", 300);
		  	
		  	YM.saveConfig();
    	}
	  	return;
	}
    public void CreateMapImageConfig(YamlController YC)
	{
    	YM = YC.getNewConfig("MapImageURL.yml");
    	if(YM.contains("GBD.URL") == false)
    	{
    		YM.set("KoreaLanguage(UTF-8)->JavaEntityLanguage", "http://itpro.cz/juniconv/");
		  	YM.set("GBD.URL", "http://cafeptthumb3.phinf.naver.net/20140309_183/dnwndugod642_1394374547812opRrb_PNG/GBD%28classic%29.png?type=w740");
		  	YM.set("GBD.Xcenter", 0);
		  	YM.set("GBD.Ycenter", 0);
		  	YM.set("WhatTheHorrible.URL", "http://cafeptthumb3.phinf.naver.net/20150828_126/dnwndugod642_1440694230353y2Dsp_PNG/%B9%B9%BE%DF%C0%CC%B0%C5_%B9%AB%BC%AD%BF%F6.png?type=w740");
		  	YM.set("WhatTheHorrible.Xcenter", 0);
		  	YM.set("WhatTheHorrible.Ycenter", 0);
		  	YM.set("MinimicAngry.URL", "http://cafeptthumb3.phinf.naver.net/20150828_84/dnwndugod642_1440694230655boUgx_PNG/%B9%CC%B4%CF%B9%CD_%C4%BC%BE%D32.png?type=w740");
		  	YM.set("MinimicAngry.Xcenter", 0);
		  	YM.set("MinimicAngry.Ycenter", 0);
		  	YM.saveConfig();
    	}
	  	return;
	}
}
