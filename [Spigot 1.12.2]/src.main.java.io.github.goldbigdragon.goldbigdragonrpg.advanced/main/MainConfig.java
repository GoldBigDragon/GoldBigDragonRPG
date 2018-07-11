package main;

import util.YamlLoader;

public class MainConfig
{
    public void checkConfig()
	{
    	YamlLoader configYaml = new YamlLoader();
    	configYaml.getConfig("config.yml");
    	
    	if(!configYaml.contains("Version"))
    	{
		  	configYaml.set("Version", "Advanced");
		  	configYaml.set("Update", 20180712);
		  	configYaml.set("Server.BroadCastSecond", 30);
		  	configYaml.set("Server.EntitySpawn", true);
		  	configYaml.set("Server.PVP", true);
		  	configYaml.set("Server.AreaSettingWand", 286);
		  	configYaml.set("Server.Like_The_Mabinogi_Online_Stat_System", false);
		  	configYaml.set("Server.Max_Drop_Money", 100000);
		  	configYaml.set("Server.Level_Up_SkillPoint", 1);
		  	configYaml.set("Server.Level_Up_StatPoint", 10);
		  	configYaml.set("Server.MonsterSpawnEffect", 0);
		  	configYaml.set("Server.ChattingDistance", -1);
		  	configYaml.set("Server.DefaultJob", "초보자");
		  	configYaml.set("Server.CustomWeaponBreak", true);
			configYaml.set("Server.AntiExplode", true);
		  	configYaml.set("Server.PVP", true);
		  	configYaml.set("Server.LeftHandWeaponDamageEnable", true);
		  	configYaml.set("Server.RemoveMonsterDefaultDrops", false);
		  	configYaml.set("MaxStat.Level", 100);
		  	configYaml.set("MaxStat.STR", 1500);
		  	configYaml.set("MaxStat.DEX", 1500);
		  	configYaml.set("MaxStat.INT", 1500);
		  	configYaml.set("MaxStat.WILL", 1500);
		  	configYaml.set("MaxStat.LUK", 1500);
		  	configYaml.set("Event.Multiple_Proficiency_Get", 1);
		  	
		  	configYaml.set("Server.STR", "체력");
		  	configYaml.set("Server.DEX", "솜씨");
		  	configYaml.set("Server.INT", "지력");
		  	configYaml.set("Server.WILL", "의지");
		  	configYaml.set("Server.LUK", "행운");
		  	configYaml.set("Server.MoneyName", "§6§lGold");
		  	configYaml.set("Server.AntiExplode", true);

		  	configYaml.set("Death.SystemOn", true);
		  	configYaml.set("Death.Spawn_Home.SetHealth", "100%");
		  	configYaml.set("Death.Spawn_Home.PenaltyEXP", "10%");
		  	configYaml.set("Death.Spawn_Home.PenaltConfigoney", "0%");
		  	configYaml.set("Death.Spawn_Here.SetHealth", "1%");
		  	configYaml.set("Death.Spawn_Here.PenaltyEXP", "15%");
		  	configYaml.set("Death.Spawn_Here.PenaltConfigoney", "10%");
		  	configYaml.set("Death.Spawn_Help.SetHealth", "1%");
		  	configYaml.set("Death.Spawn_Help.PenaltyEXP", "5%");
		  	configYaml.set("Death.Spawn_Help.PenaltConfigoney", "0%");
		  	configYaml.set("Death.Spawn_Item.SetHealth", "100%");
		  	configYaml.set("Death.Spawn_Item.PenaltyEXP", "0%");
		  	configYaml.set("Death.Spawn_Item.PenaltConfigoney", "0%");
		  	configYaml.set("Death.Track", -1);

		  	configYaml.set("Quest.AcceptMessage", "§a[퀘스트] : §e%QuestName%§a 퀘스트를 받았습니다!");
		  	configYaml.set("Quest.ClearMessage", "§3[퀘스트] : §e%QuestName%§3 퀘스트를 완료하셨습니다!");

		  	configYaml.set("NPC.Shaman.BuffCoolTime", 600);
		  	
		  	configYaml.set("Party.EXPShareDistance", 50);
		  	configYaml.set("Party.MaxPartyUnit", 8);

		  	configYaml.set("Event.Multiple_Level_Up_SkillPoint", 1);
		  	configYaml.set("Event.Multiple_Level_Up_StatPoint", 1);
		  	configYaml.set("Event.Multiple_EXP_Get", 1);
		  	configYaml.set("Event.DropChance", 1);
		  	configYaml.set("Event.Multiple_Proficiency_Get", 1);
		  	
		  	configYaml.set("DefaultStat.SkillPoint", 0);
		  	configYaml.set("DefaultStat.StatPoint", 0);
		  	configYaml.set("DefaultStat.MaxEXP", 100);
		  	configYaml.set("DefaultStat.HP", 20);
		  	configYaml.set("DefaultStat.Wond", 0);
		  	configYaml.set("DefaultStat.MP", 10);
		  	configYaml.set("DefaultStat.STR", 10);
		  	configYaml.set("DefaultStat.DEX", 10);
		  	configYaml.set("DefaultStat.INT", 10);
		  	configYaml.set("DefaultStat.LUK", 10);
		  	configYaml.set("DefaultStat.WILL", 10);
		  	configYaml.set("DefaultStat.DEF", 0);
		  	configYaml.set("DefaultStat.DEFcrash", 0);
		  	configYaml.set("DefaultStat.Protect", 0);
		  	configYaml.set("DefaultStat.Magic_DEF", 0);
		  	configYaml.set("DefaultStat.MagicDEFcrash", 0);
		  	configYaml.set("DefaultStat.Magic_Protect", 0);
		  	configYaml.set("DefaultStat.Balance", 1);
		  	configYaml.set("DefaultStat.Critical", 1);
		  	
		  	configYaml.set("MaxStat.Level", 100);
		  	configYaml.set("MaxStat.Stats", 1500);

		  	configYaml.set("Getting.Wood.EXP", 1);
		  	configYaml.set("Getting.Wood.Money", 0);
		  	configYaml.set("Getting.Coal.EXP", 3);
		  	configYaml.set("Getting.Coal.Money", 0);
		  	configYaml.set("Getting.Iron.EXP", 5);
		  	configYaml.set("Getting.Iron.Money", 0);
		  	configYaml.set("Getting.Gold.EXP", 10);
		  	configYaml.set("Getting.Gold.Money", 0);
		  	configYaml.set("Getting.Diamond.EXP", 50);
		  	configYaml.set("Getting.Diamond.Money", 0);
		  	configYaml.set("Getting.Emerald.EXP", 100);
		  	configYaml.set("Getting.Emerald.Money", 0);
		  	configYaml.set("Getting.RedStone.EXP", 20);
		  	configYaml.set("Getting.RedStone.Money", 0);
		  	configYaml.set("Getting.Lapis.EXP", 20);
		  	configYaml.set("Getting.Lapis.Money", 0);
		  	configYaml.set("Getting.NetherQuartz.EXP", 10);
		  	configYaml.set("Getting.NetherQuartz.Money", 0);

		  	configYaml.set("Getting.Wheat.EXP", 1);
		  	configYaml.set("Getting.Wheat.Money", 0);
		  	configYaml.set("Getting.Sugarcane.EXP", 1);
		  	configYaml.set("Getting.Sugarcane.Money", 0);
		  	configYaml.set("Getting.Cocoa.EXP", 2);
		  	configYaml.set("Getting.Cocoa.Money", 0);
		  	configYaml.set("Getting.NetherWart.EXP", 2);
		  	configYaml.set("Getting.NetherWart.Money", 0);
		  	configYaml.set("Getting.Carrot.EXP", 3);
		  	configYaml.set("Getting.Carrot.Money", 0);
		  	configYaml.set("Getting.Potato.EXP", 3);
		  	configYaml.set("Getting.Potato.Money", 0);
		  	configYaml.set("Getting.Beetroot.EXP", 5);
		  	configYaml.set("Getting.Beetroot.Money", 0);
		  	configYaml.set("Getting.Pumpkin.EXP", 10);
		  	configYaml.set("Getting.Pumpkin.Money", 0);
		  	configYaml.set("Getting.Melon.EXP", 10);
		  	configYaml.set("Getting.Melon.Money", 0);
		  	configYaml.set("Getting.ChorusFlower.EXP", 50);
		  	configYaml.set("Getting.ChorusFlower.Money", 0);
		  	
		  	configYaml.set("Normal_Monster.ZOMBIE.EXP", 10);
		  	configYaml.set("Normal_Monster.ZOMBIE.MIN_MONEY", 10);
		  	configYaml.set("Normal_Monster.ZOMBIE.MAX_MONEY", 20);
		  	configYaml.set("Normal_Monster.GIANT.EXP", 20);
		  	configYaml.set("Normal_Monster.GIANT.MIN_MONEY", 40);
		  	configYaml.set("Normal_Monster.GIANT.MAX_MONEY", 80);
		  	configYaml.set("Normal_Monster.SKELETON.EXP", 10);
		  	configYaml.set("Normal_Monster.SKELETON.MIN_MONEY", 10);
		  	configYaml.set("Normal_Monster.SKELETON.MAX_MONEY", 20);
		  	configYaml.set("Normal_Monster.WITHER_SKELETON.EXP", 20);
		  	configYaml.set("Normal_Monster.WITHER_SKELETON.MIN_MONEY", 40);
		  	configYaml.set("Normal_Monster.WITHER_SKELETON.MAX_MONEY", 80);
		  	configYaml.set("Normal_Monster.ENDERMAN.EXP", 25);
		  	configYaml.set("Normal_Monster.ENDERMAN.MIN_MONEY", 60);
		  	configYaml.set("Normal_Monster.ENDERMAN.MAX_MONEY", 120);
		  	configYaml.set("Normal_Monster.CREEPER.EXP", 15);
		  	configYaml.set("Normal_Monster.CREEPER.MIN_MONEY", 15);
		  	configYaml.set("Normal_Monster.CREEPER.MAX_MONEY", 30);
		  	configYaml.set("Normal_Monster.CHARGED_CREEPER.EXP", 30);
		  	configYaml.set("Normal_Monster.CHARGED_CREEPER.MIN_MONEY", 50);
		  	configYaml.set("Normal_Monster.CHARGED_CREEPER.MAX_MONEY", 100);
		  	configYaml.set("Normal_Monster.SPIDER.EXP", 8);
		  	configYaml.set("Normal_Monster.SPIDER.MIN_MONEY", 5);
		  	configYaml.set("Normal_Monster.SPIDER.MAX_MONEY", 16);
		  	configYaml.set("Normal_Monster.CAVE_SPIDER.EXP", 8);
		  	configYaml.set("Normal_Monster.CAVE_SPIDER.MIN_MONEY", 12);
		  	configYaml.set("Normal_Monster.CAVE_SPIDER.MAX_MONEY", 20);
		  	configYaml.set("Normal_Monster.SILVERFISH.EXP", 1);
		  	configYaml.set("Normal_Monster.SILVERFISH.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.SILVERFISH.MAX_MONEY", 3);
		  	configYaml.set("Normal_Monster.ENDERMITE.EXP", 1);
		  	configYaml.set("Normal_Monster.ENDERMITE.MIN_MONEY", 3);
		  	configYaml.set("Normal_Monster.ENDERMITE.MAX_MONEY", 9);
		  	configYaml.set("Normal_Monster.SLIME_SMALL.EXP", 1);
		  	configYaml.set("Normal_Monster.SLIME_SMALL.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.SLIME_SMALL.MAX_MONEY", 4);
		  	configYaml.set("Normal_Monster.SLIME_MIDDLE.EXP", 2);
		  	configYaml.set("Normal_Monster.SLIME_MIDDLE.MIN_MONEY", 4);
		  	configYaml.set("Normal_Monster.SLIME_MIDDLE.MAX_MONEY", 8);
		  	configYaml.set("Normal_Monster.SLIME_BIG.EXP", 4);
		  	configYaml.set("Normal_Monster.SLIME_BIG.MIN_MONEY", 10);
		  	configYaml.set("Normal_Monster.SLIME_BIG.MAX_MONEY", 20);
		  	configYaml.set("Normal_Monster.SLIME_HUGE.EXP", 20);
		  	configYaml.set("Normal_Monster.SLIME_HUGE.MIN_MONEY", 40);
		  	configYaml.set("Normal_Monster.SLIME_HUGE.MAX_MONEY", 80);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_SMALL.EXP", 2);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_SMALL.MIN_MONEY", 2);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_SMALL.MAX_MONEY", 5);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_MIDDLE.EXP", 3);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_MIDDLE.MIN_MONEY", 5);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_MIDDLE.MAX_MONEY", 10);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_BIG.EXP", 6);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_BIG.MIN_MONEY", 15);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_BIG.MAX_MONEY", 30);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_HUGE.EXP", 35);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_HUGE.MIN_MONEY", 60);
		  	configYaml.set("Normal_Monster.MAGMA_CUBE_HUGE.MAX_MONEY", 100);
		  	configYaml.set("Normal_Monster.BLAZE.EXP", 25);
		  	configYaml.set("Normal_Monster.BLAZE.MIN_MONEY", 80);
		  	configYaml.set("Normal_Monster.BLAZE.MAX_MONEY", 130);
		  	configYaml.set("Normal_Monster.GHAST.EXP", 25);
		  	configYaml.set("Normal_Monster.GHAST.MIN_MONEY", 70);
		  	configYaml.set("Normal_Monster.GHAST.MAX_MONEY", 140);
		  	configYaml.set("Normal_Monster.PIG_ZOMBIE.EXP", 15);
		  	configYaml.set("Normal_Monster.PIG_ZOMBIE.MIN_MONEY", 30);
		  	configYaml.set("Normal_Monster.PIG_ZOMBIE.MAX_MONEY", 45);
		  	configYaml.set("Normal_Monster.WITCH.EXP", 35);
		  	configYaml.set("Normal_Monster.WITCH.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.WITCH.MAX_MONEY", 100);
		  	configYaml.set("Normal_Monster.WITHER.EXP", 150);
		  	configYaml.set("Normal_Monster.WITHER.MIN_MONEY", 1500);
		  	configYaml.set("Normal_Monster.WITHER.MAX_MONEY", 2000);
		  	configYaml.set("Normal_Monster.ENDER_DRAGON.EXP", 500);
		  	configYaml.set("Normal_Monster.ENDER_DRAGON.MIN_MONEY", 2000);
		  	configYaml.set("Normal_Monster.ENDER_DRAGON.MAX_MONEY", 7000);
		  	configYaml.set("Normal_Monster.GUARDIAN.EXP", 20);
		  	configYaml.set("Normal_Monster.GUARDIAN.MIN_MONEY", 15);
		  	configYaml.set("Normal_Monster.GUARDIAN.MAX_MONEY", 45);
		  	configYaml.set("Normal_Monster.SHEEP.EXP", 1);
		  	configYaml.set("Normal_Monster.SHEEP.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.SHEEP.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.COW.EXP", 1);
		  	configYaml.set("Normal_Monster.COW.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.COW.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.PIG.EXP", 1);
		  	configYaml.set("Normal_Monster.PIG.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.PIG.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.HORSE.EXP", 1);
		  	configYaml.set("Normal_Monster.HORSE.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.HORSE.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.RABBIT.EXP", 1);
		  	configYaml.set("Normal_Monster.RABBIT.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.RABBIT.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.OCELOT.EXP", 1);
		  	configYaml.set("Normal_Monster.OCELOT.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.OCELOT.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.WOLF.EXP", 2);
		  	configYaml.set("Normal_Monster.WOLF.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.WOLF.MAX_MONEY", 8);
		  	configYaml.set("Normal_Monster.CHICKEN.EXP", 1);
		  	configYaml.set("Normal_Monster.CHICKEN.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.CHICKEN.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.MUSHROOM_COW.EXP", 1);
		  	configYaml.set("Normal_Monster.MUSHROOM_COW.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.MUSHROOM_COW.MAX_MONEY", 5);
		  	configYaml.set("Normal_Monster.BAT.EXP", 1);
		  	configYaml.set("Normal_Monster.BAT.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.BAT.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.SQUID.EXP", 1);
		  	configYaml.set("Normal_Monster.SQUID.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.SQUID.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.VILLAGER.EXP", 1);
		  	configYaml.set("Normal_Monster.VILLAGER.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.VILLAGER.MAX_MONEY", 300);
		  	configYaml.set("Normal_Monster.SNOWMAN.EXP", 1);
		  	configYaml.set("Normal_Monster.SNOWMAN.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.SNOWMAN.MAX_MONEY", 10);
		  	configYaml.set("Normal_Monster.SHULKER.EXP", 35);
		  	configYaml.set("Normal_Monster.SHULKER.MIN_MONEY", 20);
		  	configYaml.set("Normal_Monster.SHULKER.MAX_MONEY", 50);
		  	configYaml.set("Normal_Monster.POLAR_BEAR.EXP", 40);
		  	configYaml.set("Normal_Monster.POLAR_BEAR.MIN_MONEY", 25);
		  	configYaml.set("Normal_Monster.POLAR_BEAR.MAX_MONEY", 45);

		  	configYaml.set("Normal_Monster.ELDER_GUARDIAN.EXP", 60);
		  	configYaml.set("Normal_Monster.ELDER_GUARDIAN.MIN_MONEY", 100);
		  	configYaml.set("Normal_Monster.ELDER_GUARDIAN.MAX_MONEY", 300);
		  	configYaml.set("Normal_Monster.HUSK.EXP", 15);
		  	configYaml.set("Normal_Monster.HUSK.MIN_MONEY", 15);
		  	configYaml.set("Normal_Monster.HUSK.MAX_MONEY", 40);
		  	configYaml.set("Normal_Monster.STRAY.EXP", 15);
		  	configYaml.set("Normal_Monster.STRAY.MIN_MONEY", 30);
		  	configYaml.set("Normal_Monster.STRAY.MAX_MONEY", 50);
		  	configYaml.set("Normal_Monster.ZOMBIE_VILLAGER.EXP", 10);
		  	configYaml.set("Normal_Monster.ZOMBIE_VILLAGER.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.ZOMBIE_VILLAGER.MAX_MONEY", 250);
		  	configYaml.set("Normal_Monster.EVOKER.EXP", 45);
		  	configYaml.set("Normal_Monster.EVOKER.MIN_MONEY", 20);
		  	configYaml.set("Normal_Monster.EVOKER.MAX_MONEY", 150);
		  	configYaml.set("Normal_Monster.VEX.EXP", 10);
		  	configYaml.set("Normal_Monster.VEX.MIN_MONEY", 0);
		  	configYaml.set("Normal_Monster.VEX.MAX_MONEY", 20);
		  	configYaml.set("Normal_Monster.VINDICATOR.EXP", 50);
		  	configYaml.set("Normal_Monster.VINDICATOR.MIN_MONEY", 0);
		  	configYaml.set("Normal_Monster.VINDICATOR.MAX_MONEY", 250);
		  	configYaml.set("Normal_Monster.SKELETON_HORSE.EXP", 100);
		  	configYaml.set("Normal_Monster.SKELETON_HORSE.MIN_MONEY", 0);
		  	configYaml.set("Normal_Monster.SKELETON_HORSE.MAX_MONEY", 5);
		  	configYaml.set("Normal_Monster.ZOMBIE_HORSE.EXP", 100);
		  	configYaml.set("Normal_Monster.ZOMBIE_HORSE.MIN_MONEY", 0);
		  	configYaml.set("Normal_Monster.ZOMBIE_HORSE.MAX_MONEY", 5);
		  	configYaml.set("Normal_Monster.DONKEY.EXP", 1);
		  	configYaml.set("Normal_Monster.DONKEY.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.DONKEY.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.MULE.EXP", 1);
		  	configYaml.set("Normal_Monster.MULE.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.MULE.MAX_MONEY", 1);
		  	configYaml.set("Normal_Monster.LLAMA.EXP", 1);
		  	configYaml.set("Normal_Monster.LLAMA.MIN_MONEY", 1);
		  	configYaml.set("Normal_Monster.LLAMA.MAX_MONEY", 3);
    	}

    	if(configYaml.getLong("Update") < 20180707)
    	{
		  	configYaml.set("Getting.Wheat.EXP", 1);
		  	configYaml.set("Getting.Wheat.Money", 0);
		  	configYaml.set("Getting.Sugarcane.EXP", 1);
		  	configYaml.set("Getting.Sugarcane.Money", 0);
		  	configYaml.set("Getting.Cocoa.EXP", 2);
		  	configYaml.set("Getting.Cocoa.Money", 0);
		  	configYaml.set("Getting.NetherWart.EXP", 2);
		  	configYaml.set("Getting.NetherWart.Money", 0);
		  	configYaml.set("Getting.Carrot.EXP", 3);
		  	configYaml.set("Getting.Carrot.Money", 0);
		  	configYaml.set("Getting.Potato.EXP", 3);
		  	configYaml.set("Getting.Potato.Money", 0);
		  	configYaml.set("Getting.Beetroot.EXP", 5);
		  	configYaml.set("Getting.Beetroot.Money", 0);
		  	configYaml.set("Getting.Pumpkin.EXP", 10);
		  	configYaml.set("Getting.Pumpkin.Money", 0);
		  	configYaml.set("Getting.Melon.EXP", 10);
		  	configYaml.set("Getting.Melon.Money", 0);
		  	configYaml.set("Getting.ChorusFlower.EXP", 50);
		  	configYaml.set("Getting.ChorusFlower.Money", 0);
    	}
    	else if(configYaml.getLong("Update") < 20180712)
    	  	configYaml.set("Server.RemoveMonsterDefaultDrops", false);
    	
    	

	  	configYaml.saveConfig();
	  	
		if(configYaml.contains("Server.MabinogiMoneySystem"))
			main.MainServerOption.MoneySystem = configYaml.getBoolean("Server.MabinogiMoneySystem");
		if(configYaml.contains("Server.STR"))
			main.MainServerOption.statSTR = configYaml.getString("Server.STR");
		if(configYaml.contains("Server.DEX"))
			main.MainServerOption.statDEX = configYaml.getString("Server.DEX");
		if(configYaml.contains("Server.INT"))
			main.MainServerOption.statINT = configYaml.getString("Server.INT");
		if(configYaml.contains("Server.WILL"))
			main.MainServerOption.statWILL = configYaml.getString("Server.WILL");
		if(configYaml.contains("Server.LUK"))
			main.MainServerOption.statLUK = configYaml.getString("Server.LUK");
		if(configYaml.contains("Server.MoneyName"))
			main.MainServerOption.money = configYaml.getString("Server.MoneyName");
		if(configYaml.contains("Server.STR_Lore"))
			main.MainServerOption.STR_Lore = configYaml.getString("Server.STR_Lore");
		if(configYaml.contains("Server.DEX_Lore"))
			main.MainServerOption.DEX_Lore = configYaml.getString("Server.DEX_Lore");
		if(configYaml.contains("Server.INT_Lore"))
			main.MainServerOption.INT_Lore = configYaml.getString("Server.INT_Lore");
		if(configYaml.contains("Server.WILL_Lore"))
			main.MainServerOption.WILL_Lore = configYaml.getString("Server.WILL_Lore");
		if(configYaml.contains("Server.LUK_Lore"))
			main.MainServerOption.LUK_Lore = configYaml.getString("Server.LUK_Lore");
		if(configYaml.contains("Server.Money.1.ID"))
			main.MainServerOption.Money1ID = (short) configYaml.getInt("Server.Money.1.ID");
		if(configYaml.contains("Server.Money.2.ID"))
			main.MainServerOption.Money2ID = (short) configYaml.getInt("Server.Money.2.ID");
		if(configYaml.contains("Server.Money.3.ID"))
			main.MainServerOption.Money3ID = (short) configYaml.getInt("Server.Money.3.ID");
		if(configYaml.contains("Server.Money.4.ID"))
			main.MainServerOption.Money4ID = (short) configYaml.getInt("Server.Money.4.ID");
		if(configYaml.contains("Server.Money.5.ID"))
			main.MainServerOption.Money5ID = (short) configYaml.getInt("Server.Money.5.ID");
		if(configYaml.contains("Server.Money.6.ID"))
			main.MainServerOption.Money6ID = (short) configYaml.getInt("Server.Money.6.ID");
		if(configYaml.contains("Server.Money.1.DATA"))
			main.MainServerOption.Money1DATA = (byte) configYaml.getInt("Server.Money.1.DATA");
		if(configYaml.contains("Server.Money.2.DATA"))
			main.MainServerOption.Money2DATA = (byte) configYaml.getInt("Server.Money.2.DATA");
		if(configYaml.contains("Server.Money.3.DATA"))
			main.MainServerOption.Money3DATA = (byte) configYaml.getInt("Server.Money.3.DATA");
		if(configYaml.contains("Server.Money.4.DATA"))
			main.MainServerOption.Money4DATA = (byte) configYaml.getInt("Server.Money.4.DATA");
		if(configYaml.contains("Server.Money.5.DATA"))
			main.MainServerOption.Money5DATA = (byte) configYaml.getInt("Server.Money.5.DATA");
		if(configYaml.contains("Server.Money.6.DATA"))
			main.MainServerOption.Money6DATA = (byte) configYaml.getInt("Server.Money.6.DATA");
		if(configYaml.contains("Server.Damage"))
			main.MainServerOption.damage = configYaml.getString("Server.Damage");
		if(configYaml.contains("Server.MagicDamage"))
			main.MainServerOption.magicDamage = configYaml.getString("Server.MagicDamage");
		if(configYaml.contains("Server.AntiExplode"))
			main.MainServerOption.AntiExplode = configYaml.getBoolean("Server.AntiExplode");
		if(configYaml.contains("Death.ReviveItem"))
			main.MainServerOption.DeathRevive = configYaml.getItemStack("Death.ReviveItem");
		if(configYaml.contains("Death.RescueItem"))
			main.MainServerOption.DeathRescue = configYaml.getItemStack("Death.RescueItem");
		if(configYaml.contains("MaxStat.Stats"))
		{
			int maxStat = configYaml.getInt("MaxStat.Stats");
			configYaml.set("MaxStat.STR", maxStat);
			configYaml.set("MaxStat.DEX", maxStat);
			configYaml.set("MaxStat.INT", maxStat);
			configYaml.set("MaxStat.WILL", maxStat);
			configYaml.set("MaxStat.LUK", maxStat);
			configYaml.removeKey("MaxStat.Stats");
			configYaml.saveConfig();
		}
		
		
		main.MainServerOption.maxLevel = configYaml.getInt("MaxStat.Level");
		main.MainServerOption.maxSTR = configYaml.getInt("MaxStat.STR");
		main.MainServerOption.maxDEX = configYaml.getInt("MaxStat.DEX");
		main.MainServerOption.maxINT = configYaml.getInt("MaxStat.INT");
		main.MainServerOption.maxWILL = configYaml.getInt("MaxStat.WILL");
		main.MainServerOption.maxLUK = configYaml.getInt("MaxStat.LUK");
		main.MainServerOption.PVP = configYaml.getBoolean("Server.PVP");
		main.MainServerOption.maxDropMoney = configYaml.getLong("Server.Max_Drop_Money");
		main.MainServerOption.expShareDistance = configYaml.getInt("Party.EXPShareDistance");
		main.MainServerOption.removeMonsterDefaultDrops = configYaml.getBoolean("Server.RemoveMonsterDefaultDrops");

		main.MainServerOption.eventSkillPoint = (byte) configYaml.getInt("Event.Multiple_Level_Up_SkillPoint");
		main.MainServerOption.eventStatPoint = (byte) configYaml.getInt("Event.Multiple_Level_Up_StatPoint");
		main.MainServerOption.eventDropChance = (byte) configYaml.getInt("Event.DropChance");
		main.MainServerOption.eventExp = (byte) configYaml.getInt("Event.Multiple_EXP_Get");
		main.MainServerOption.eventProficiency = (byte) configYaml.getInt("Event.Multiple_Proficiency_Get");
		
		main.MainServerOption.levelUpPerSkillPoint = (byte) configYaml.getInt("Server.Level_Up_SkillPoint");
		main.MainServerOption.levelUpPerStatPoint = (byte) configYaml.getInt("Server.Level_Up_StatPoint");
		
		main.MainServerOption.dualWeapon = configYaml.getBoolean("Server.LeftHandWeaponDamageEnable");
		
		configYaml.getConfig("MapImageURL.yml");
    	if(!configYaml.contains("GBD.URL"))
    	{
    		configYaml.set("KoreaLanguage(UTF-8)->JavaEntityLanguage", "http://itpro.cz/juniconv/");
		  	configYaml.set("GBD.URL", "http://cafeptthumb3.phinf.naver.net/20140309_183/dnwndugod642_1394374547812opRrb_PNG/GBD%28classic%29.png?type=w740");
		  	configYaml.set("GBD.Xcenter", 0);
		  	configYaml.set("GBD.Ycenter", 0);
		  	configYaml.set("WhatTheHorrible.URL", "http://cafeptthumb3.phinf.naver.net/20150828_126/dnwndugod642_1440694230353y2Dsp_PNG/%B9%B9%BE%DF%C0%CC%B0%C5_%B9%AB%BC%AD%BF%F6.png?type=w740");
		  	configYaml.set("WhatTheHorrible.Xcenter", 0);
		  	configYaml.set("WhatTheHorrible.Ycenter", 0);
		  	configYaml.set("MinimicAngry.URL", "http://cafeptthumb3.phinf.naver.net/20150828_84/dnwndugod642_1440694230655boUgx_PNG/%B9%CC%B4%CF%B9%CD_%C4%BC%BE%D32.png?type=w740");
		  	configYaml.set("MinimicAngry.Xcenter", 0);
		  	configYaml.set("MinimicAngry.Ycenter", 0);
		  	configYaml.saveConfig();
    	}

    	configYaml.getConfig("Level.yml");
    	if(!configYaml.contains(1+""))
    	{
    		long EXP = 100;
    		for(int level = 1; level < 250; level++)
    		{
    			if(level + ((1.1) * EXP)< 0 ||level + (1.1) * EXP> Long.MAX_VALUE)
    				break;
    			else
    			{
    	    		configYaml.set(level+"", EXP);
    				EXP = (long) (level + (1.1) * EXP);
    			}
    		}
    		configYaml.saveConfig();
    	}
    	
    	configYaml.getConfig("LevelUpPerBonusStat.yml");
    	if(!configYaml.contains("level"))
    	{
    		for(int level = 1; level < 250; level++)
    		{
	    		configYaml.set("level."+level+".HP", 1);
	    		configYaml.set("level."+level+".MP", 1);
	    		configYaml.set("level."+level+".STR", 0);
	    		configYaml.set("level."+level+".DEX", 0);
	    		configYaml.set("level."+level+".INT", 0);
	    		configYaml.set("level."+level+".WILL", 0);
	    		configYaml.set("level."+level+".LUK", 0);
	    		configYaml.set("level."+level+".Balance", 0);
	    		configYaml.set("level."+level+".Critical", 0);
	    		configYaml.set("level."+level+".Defense", 0);
	    		configYaml.set("level."+level+".DefenseCrash", 0);
	    		configYaml.set("level."+level+".Protect", 0);
	    		configYaml.set("level."+level+".MagicDefense", 0);
	    		configYaml.set("level."+level+".MagicDefenseCrash", 0);
	    		configYaml.set("level."+level+".MagicProtect", 0);
    		}
    		configYaml.saveConfig();
    	}
	  	return;
	}
}
