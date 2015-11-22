package GBD.GoldBigDragon_Advanced.Event;

import GBD.GoldBigDragon_Advanced.Util.ETC;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;
import GBD.GoldBigDragon_Advanced.Effect.PacketSender;
import GBD.GoldBigDragon_Advanced.Main.Main;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;

public class Attack
{
	public boolean AttackAble(Player player)
	{
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
		GBD.GoldBigDragon_Advanced.Config.StatConfig stat = new GBD.GoldBigDragon_Advanced.Config.StatConfig();

		YamlManager Config =Event_YC.getNewConfig("config.yml");

	  	YamlManager attacker = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
	  	if(attacker.contains("Player.Name") == false)
	  		stat.CreateNewStats(player);
		
        if(Config.getBoolean("Server.AttackDelay") == true)
        {
        	long time = new GBD.GoldBigDragon_Advanced.Util.ETC().getSec();
        	if(attacker.getLong("Stat.AttackTime") + 2000 >=  time)
        	{
        		if(attacker.getBoolean("Alert.AttackDelay") == true)
        		    new PacketSender().sendActionBar(player, ChatColor.GRAY+""+ChatColor.BOLD+(attacker.getLong("Stat.AttackTime")+2000 - time)/1000 + "초 후에 공격이 가능합니다!");
	     		return false;
        	}
        }
		
		if(player.isOnline()==true)
		{
	    	ETC time = new ETC();
			if(player != null)
			{
			  	if(Event_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
			  		stat.CreateNewStats(player);
				attacker = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		 		attacker.set("Stat.AttackTime", time.getSec()); 
				attacker.saveConfig();
			}
		}
		return true;
	}

	//활 공격시 얼마나 힘을 주었는지 값을 구해주는 메소드//
	public void RangeAttack(EntityShootBowEvent event)
	{
	    YamlManager attacker;
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;

		if(event.getEntityType() == EntityType.PLAYER)
		{
			Player player = (Player) event.getEntity();
		  	attacker = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		  	if(attacker.contains("Player.Name") == false)
		  		new GBD.GoldBigDragon_Advanced.Config.StatConfig().CreateNewStats(player);
			attacker.set("Stat.BowPull", (int)(event.getForce()*100));
			attacker.saveConfig();
			Main.PlayerUseSpell.remove(player);
		}
		return;
	}

	public void AttackRouter(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof LivingEntity)
		{
			if(event.getDamager() instanceof LivingEntity&&event.getCause() != DamageCause.PROJECTILE)
			{
				if(event.getCause() == DamageCause.ENTITY_ATTACK)
				{
					DamageSetter(event,"M",event.getDamager());//MeeleAttack
				}
				else if(event.getCause() == DamageCause.ENTITY_EXPLOSION)
				{
					if (event.getDamager() != null && event.getDamager() instanceof LivingEntity)
					{
						DamageSetter(event,"E_E",event.getDamager());
					}
				}
			}
			else
			{
			   	if(event.getCause() == DamageCause.PROJECTILE)
		    	{
					if (event.getDamager() != null && event.getDamager() instanceof Projectile)
					{
						Projectile projectile = (Projectile) event.getDamager();
						if((projectile.getShooter() != null))
						{
							if(projectile.getType() == EntityType.ARROW)
							{
								DamageSetter(event,"R_A",(Entity) projectile.getShooter());
							}
							if(projectile.getType() == EntityType.FISHING_HOOK)
							{
								DamageSetter(event,"R_FH",(Entity) projectile.getShooter());
							}
						}
					}
		    	}
			}
		}
		return;
	}
	
	public void DamageSetter(EntityDamageByEntityEvent event,String AttackType,Entity Attacker)
	{
	    GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		if(Attacker.getType()==EntityType.PLAYER&&
				event.getEntity().getType()==EntityType.PLAYER)
		{
			Player player = (Player) Attacker;
			Player target = (Player) event.getEntity();
			
			GBD.GoldBigDragon_Advanced.ETC.Area A = new GBD.GoldBigDragon_Advanced.ETC.Area();
			if(Main.PlayerCurrentArea.get(player)==null)
				Main.PlayerCurrentArea.put(player, "null");
			if(Main.PlayerCurrentArea.get(target)==null)
				Main.PlayerCurrentArea.put(target, "null");
			String AttackerArea = A.getAreaName(player);
			String TargetArea = A.getAreaName(target);
			if(AttackerArea != null||TargetArea != null)
			{
				if(A.getAreaOption(TargetArea, (char) 0) == false)
				{
					sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					sound.SP(target, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[영역] : 이곳에서는 PVP가 금지되어 있습니다!");
					target.sendMessage(ChatColor.RED+"[영역] : 이곳에서는 PVP가 금지되어 있습니다!");
					event.setCancelled(true);
					return;
				}
				else if(A.getAreaOption(AttackerArea, (char) 0) == false)
				{
					sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					sound.SP(target, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[영역] : 이곳에서는 PVP가 금지되어 있습니다!");
					target.sendMessage(ChatColor.RED+"[영역] : 이곳에서는 PVP가 금지되어 있습니다!");
					event.setCancelled(true);
					return;
				}
			}
		}
	    YamlManager attacker;
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
	    Damage damage = new Damage();
	    PacketSender t = new PacketSender();

	    int Damage = (int) event.getDamage();
	    if(Attacker.getType() == EntityType.PLAYER)
	    {
	    	Damage = Damage-1;
	    	Player player = (Player)Attacker;
	    	if(Main.PlayerUseSpell.containsKey(player))
	    		if(Main.PlayerUseSpell.get(player).compareTo("us")==0)
	    		{
	    			if(player.isOnline())
	    				Alert(player, event.getEntity(), (int)event.getDamage());
	    			Main.PlayerUseSpell.remove(player);
	    			return;
	    		}
	    }
	    int Attacker_Stat[] = null;

		if (AttackType == "R_A")//Range Arrow
			Attacker_Stat = getAttackerStats(Attacker);
		else if (AttackType == "M"||AttackType == "E_E")
		{
			if(Attacker.getType() == EntityType.PLAYER)
			{
				Player player = (Player)Attacker;
				if(AttackAble(player)==false)
				{
					event.setCancelled(true);
					return;
				}
			}
			Attacker_Stat = getAttackerStats(Attacker);
		}
		
	    int Defender_Stat[] = getDefenderStats(event.getEntity());

		if (AttackType == "R_A")
		{
			Damage = damage.damagerand(Attacker, damage.RangeMinDamageGet(Attacker, (int)Damage, Attacker_Stat[1]),
					damage.RangeMaxDamageGet(Attacker, (int)Damage, Attacker_Stat[1]), Attacker_Stat[8]);
			if(Attacker instanceof Player)
			{
				Player player = (Player)Attacker;
				attacker = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
				if(player.isOnline())
					Damage = (int)((Damage*attacker.getInt("Stat.BowPull"))/100);
			}
		}
		
		if(AttackType == "M")
		{
			Damage = damage.damagerand(Attacker, damage.CombatMinDamageGet(Attacker, (int)Damage, Attacker_Stat[0]),
					damage.CombatMaxDamageGet(Attacker, (int)Damage, Attacker_Stat[0]),  Attacker_Stat[8]);
		}
		
		if(AttackType == "E_E")
		{
			Damage = damage.damagerand(Attacker, damage.ExplosionMinDamageGet(Attacker, (int)Damage, Attacker_Stat[2]),
					damage.ExplosionMaxDamageGet(Attacker, (int)Damage, Attacker_Stat[2]),  Attacker_Stat[8]);
		}
		
		int critdamage = damage.criticalrend(Attacker_Stat[4], Attacker_Stat[1],Damage, Defender_Stat[1],Attacker_Stat[7]);

		if(critdamage != 0)
		{
			Damage = (Damage+critdamage);
			if(Attacker instanceof Player)
			{
				Player player = (Player)Attacker;
				if(player.isOnline())
				{
					attacker = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
					if(attacker.getBoolean("Alert.Critical") == true)
						t.sendTitleSubTitle(player, "\'\'", "\'"+ChatColor.YELLOW + "크리티컬 히트!\'", 1, 0, 1);
				}
			}
			sound.SL(event.getEntity().getLocation(), Sound.ZOMBIE_METAL, (float)0.5, (float)2.0);
			sound.SL(event.getEntity().getLocation(), Sound.EXPLODE, (float)0.5, (float)1.7);
		}

		int DamageMinus = Defender_Stat[0]-Attacker_Stat[5]; 
		if(DamageMinus <= 0)
			DamageMinus = 0;
		
		Damage = Damage-DamageMinus;
		if(Defender_Stat[1] < 0)
			Defender_Stat[1] = 0;
		if(Damage >= 100)
			Damage =(int)((Damage/100)*(100-Defender_Stat[1]));
		else if(Damage >= 10)
			Damage =(int)((Damage/10)*((100-Defender_Stat[1])/10));
		else
			Damage =(int)(Damage-Defender_Stat[1]);
		if(Damage <= 0 || (100-Defender_Stat[1])<=0/*보호가 100 이상일 경우*/)
		{
			if(Attacker instanceof Player)
			{
				Player player = (Player)Attacker;
				if(player.isOnline())
					DamageCancellMessage(player, event.getEntity());
			}
			event.setCancelled(true);
			return;
		}
		event.setDamage(Damage);
		
		if(AttackType == "E_E")
		{
			Damageable d = (Damageable) event.getEntity();
			if(d.getHealth()<=Damage)
				d.setHealth(0);
			else
				d.setHealth(d.getHealth()-Damage);
			return;
		}

		if(Attacker instanceof Player)
		{
			Player player = (Player)Attacker;
			if(player.isOnline())
				Alert(player, event.getEntity(), Damage);
		}
		return;
	}
	
	public int[] getAttackerStats(Entity entity)
	{
		int Attacker_Stat[] = new int[9];
	    YamlManager attacker;
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
	    Damage damage = new Damage();
		
	    if(entity.getType() == EntityType.PLAYER)
	    {
	    	Player player = (Player)entity;
	    	if(player.isOnline())
	    	{
	    	  	attacker = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
	    	  	damage.decreaseDurabilityWeapon(player);
	    	  	Attacker_Stat[0] = attacker.getInt("Stat.STR") + damage.getPlayerEquipmentStat(player, "STR")[0];
	    	  	Attacker_Stat[1] = attacker.getInt("Stat.DEX") + damage.getPlayerEquipmentStat(player, "DEX")[0];
	    	  	Attacker_Stat[2] = attacker.getInt("Stat.INT") + damage.getPlayerEquipmentStat(player, "INT")[0];
	    	  	Attacker_Stat[3] = attacker.getInt("Stat.WILL") + damage.getPlayerEquipmentStat(player, "WILL")[0];
	    	  	Attacker_Stat[4] = attacker.getInt("Stat.LUK") + damage.getPlayerEquipmentStat(player, "LUK")[0];
	    	  	Attacker_Stat[5] = attacker.getInt("Stat.DEFcrash") + damage.getPlayerEquipmentStat(player, "DEFcrash")[0] + damage.getDEFcrash(player,Attacker_Stat[1]);
	    	  	Attacker_Stat[6] = attacker.getInt("Stat.MagicDEFcrash") + damage.getPlayerEquipmentStat(player, "MagicDEFcrash")[0];
	    	  	Attacker_Stat[7] = attacker.getInt("Stat.Critical");
	    	  	Attacker_Stat[8] = damage.getBalance(player, Attacker_Stat[1], attacker.getInt("Stat.Balance"));
	    	}
	    	else
	    	{
	    		if(entity.isCustomNameVisible() == true)
	    		{
	    			String name = ChatColor.stripColor(entity.getCustomName().toString());
	    			attacker  = Event_YC.getNewConfig("Monster/MonsterList.yml");

	    			Object[] monsterlist = attacker.getConfigurationSection("").getKeys(false).toArray();
	    			
	    			for(int count = 0; count < monsterlist.length; count ++)
	    			{
	    				if(attacker.getString(monsterlist[count].toString()+".Name").equals(name) == true)
	    				{
	    					Attacker_Stat[0] = attacker.getInt(monsterlist[count].toString()+".STR");
	    					Attacker_Stat[1] = attacker.getInt(monsterlist[count].toString()+".DEX");
	    					Attacker_Stat[2] = attacker.getInt(monsterlist[count].toString()+".INT");
	    					Attacker_Stat[3] = attacker.getInt(monsterlist[count].toString()+".WILL");
	    					Attacker_Stat[4] = attacker.getInt(monsterlist[count].toString()+".LUK");
	    					Attacker_Stat[5] = attacker.getInt(monsterlist[count].toString()+".DEFcrash");
	    					Attacker_Stat[6] = attacker.getInt(monsterlist[count].toString()+".MagicDEFcrash");
	    		    	  	Attacker_Stat[8] = damage.getBalance(entity, Attacker_Stat[1]);
	    		    	  	break;
	    				}
	    			}
	    		}
	    	}
	    }
	    else
	    {
    		if(entity.isCustomNameVisible() == true)
    		{
    			String name = ChatColor.stripColor(entity.getCustomName().toString());
    			attacker  = Event_YC.getNewConfig("Monster/MonsterList.yml");

    			Object[] monsterlist = attacker.getConfigurationSection("").getKeys(false).toArray();
    			
    			for(int count = 0; count < monsterlist.length; count ++)
    			{
    				if(attacker.getString(monsterlist[count].toString()+".Name").equals(name) == true)
    				{
    					Attacker_Stat[0] = attacker.getInt(monsterlist[count].toString()+".STR");
    					Attacker_Stat[1] = attacker.getInt(monsterlist[count].toString()+".DEX");
    					Attacker_Stat[2] = attacker.getInt(monsterlist[count].toString()+".INT");
    					Attacker_Stat[3] = attacker.getInt(monsterlist[count].toString()+".WILL");
    					Attacker_Stat[4] = attacker.getInt(monsterlist[count].toString()+".LUK");
    					Attacker_Stat[5] = attacker.getInt(monsterlist[count].toString()+".DEFcrash");
    					Attacker_Stat[6] = attacker.getInt(monsterlist[count].toString()+".MagicDEFcrash");
    		    	  	Attacker_Stat[8] = damage.getBalance(entity, Attacker_Stat[1]);
    		    	  	break;
    				}
    			}
    		}
	    }
	    return Attacker_Stat;
	}
	
	public int[] getDefenderStats(Entity entity)
	{
		int Defender_Stat[] = new int[4];
		GBD.GoldBigDragon_Advanced.Config.StatConfig stat = new GBD.GoldBigDragon_Advanced.Config.StatConfig();
	    YamlManager defenser;
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
	    Damage damage = new Damage();
		
	    if(entity.getType() == EntityType.PLAYER)
	    {
	    	Player player = (Player)entity;
	    	if(player.isOnline())
	    	{
	    	  	if(Event_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	    	  		stat.CreateNewStats(player);
	    	  	defenser = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
	    	  	damage.decreaseDurabilityArmor(player);
	    	  	Defender_Stat[0] = defenser.getInt("Stat.DEF") + damage.getPlayerEquipmentStat(player, "DEF")[0];
	    	  	Defender_Stat[1] = defenser.getInt("Stat.Protect") + damage.getPlayerEquipmentStat(player, "Protect")[0];
	    	  	Defender_Stat[2] = defenser.getInt("Stat.Magic_DEF") + damage.getPlayerEquipmentStat(player, "Magic_DEF")[0];
	    	  	Defender_Stat[3] = defenser.getInt("Stat.Magic_Protect") + damage.getPlayerEquipmentStat(player, "Magic_Protect")[0];
	    	}
	    	else
	    	{
				if(entity.isCustomNameVisible() == true)
				{
					String name = ChatColor.stripColor(entity.getCustomName().toString());
					defenser  = Event_YC.getNewConfig("Monster/MonsterList.yml");
		
					Object[] monsterlist = defenser.getConfigurationSection("").getKeys(false).toArray();
					
					for(int count = 0; count < monsterlist.length; count ++)
					{
						if(defenser.getString(monsterlist[count].toString()+".Name").equals(name) == true)
						{
						    Defender_Stat[0] = defenser.getInt(monsterlist[count].toString()+".DEF");
						    Defender_Stat[1] = defenser.getInt(monsterlist[count].toString()+".Protect");
						    Defender_Stat[2] = defenser.getInt("Stat.Magic_DEF");
						    Defender_Stat[3] = defenser.getInt("Stat.Magic_Protect");
						    break;
						}
					}
				}
	    	}
	    }
	    else
	    {
			if(entity.isCustomNameVisible() == true)
			{
				String name = ChatColor.stripColor(entity.getCustomName().toString());
				defenser  = Event_YC.getNewConfig("Monster/MonsterList.yml");
	
				Object[] monsterlist = defenser.getConfigurationSection("").getKeys(false).toArray();
				
				for(int count = 0; count < monsterlist.length; count ++)
				{
					if(defenser.getString(monsterlist[count].toString()+".Name").equals(name) == true)
					{
					    Defender_Stat[0] = defenser.getInt(monsterlist[count].toString()+".DEF");
					    Defender_Stat[1] = defenser.getInt(monsterlist[count].toString()+".Protect");
					    Defender_Stat[2] = defenser.getInt("Stat.Magic_DEF");
					    Defender_Stat[3] = defenser.getInt("Stat.Magic_Protect");
					    break;
					}
				}
			}
	    }
	    return Defender_Stat;
	}
	
	//데미지가 0이 떴을 때 띄우는 랜덤 메시지를 정해주는 메소드//
	public void DamageCancellMessage (Player player, Entity defenser)
	{
		int a = new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(1, 5);
		new GBD.GoldBigDragon_Advanced.Effect.Sound().SL(defenser.getLocation(), Sound.ZOMBIE_METAL, (float)1.0, (float)0.7);
	    PacketSender t = new PacketSender();
	    
		if (a == 1) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "이 공격은 전혀 통하지 않는다!");
		else if (a == 2) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "자세를 흐트릴 수 없다!");
		else if (a == 3) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "충격이 분산되었다!");
		else if (a == 4) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "이 공격으로는 쓰러뜨릴 수 없을 것 같다!");
		else if (a == 5) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "적의 자세를 흐트릴 수 없다!");
		return;
	}

	public void Alert (Player player, Entity defenser, int Damage)
	{
	    YamlManager attacker;
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
		GBD.GoldBigDragon_Advanced.Config.StatConfig stat = new GBD.GoldBigDragon_Advanced.Config.StatConfig();
	    PacketSender t = new PacketSender();

	  	if(Event_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		stat.CreateNewStats(player);
	  	attacker = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
	  	
		if(attacker.getBoolean("Alert.Damage") == true)
			t.sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+Damage + "데미지!");
		if(attacker.getBoolean("Alert.MobHealth") == true)
		{
			String HealthBar = ChatColor.RED+"";
			if(defenser instanceof LivingEntity)
			{
				LivingEntity defenser2 = (LivingEntity) defenser;
				Damageable getouter = (Damageable)defenser2;
				int health = (int)getouter.getHealth() - Damage;
				int Mahealth = (int)getouter.getMaxHealth();
				int Percent =1;

				if(health <= 0)
					health = 1;
				if(Mahealth/100 >= 1)
				{
					Percent= health/(Mahealth/100);
					for(short count = 0; count <= Percent; count++)
						HealthBar = HealthBar + "|";
					HealthBar = HealthBar + ChatColor.DARK_RED;
					for(short count = 0; count <= 100-Percent; count++)
						HealthBar = HealthBar + "|";
				}
				else if(Mahealth/10 >= 1)
				{
					Percent= health/(Mahealth/10);
					for(short count = 0; count <= (Percent*10); count++)
						HealthBar = HealthBar + "|";
					HealthBar = HealthBar + ChatColor.DARK_RED;
					for(short count = 0; count <= 100-(Percent*10); count++)
						HealthBar = HealthBar + "|";
				}
				else if(Mahealth/5 >= 1)
				{
					Percent= health/(Mahealth/5);
					for(short count = 0; count <= (Percent*5); count++)
						HealthBar = HealthBar + "|";
					HealthBar = HealthBar + ChatColor.DARK_RED;
					for(short count = 0; count <= 100-(Percent*5); count++)
						HealthBar = HealthBar + "|";
				}
				else if(Mahealth/2 >= 1)
				{
					Percent= health/(Mahealth/2);
					for(short count = 0; count <= (Percent*50); count++)
						HealthBar = HealthBar + "|";
					HealthBar = HealthBar + ChatColor.DARK_RED;
					for(short count = 0; count <= 100-(Percent*50); count++)
						HealthBar = HealthBar + "|";
				}
				else
				{
					Percent= 1;
					for(short count = 0; count <= 1; count++)
						HealthBar = HealthBar + "|";
					HealthBar = HealthBar + ChatColor.DARK_RED;
					for(short count = 0; count <= 99; count++)
						HealthBar = HealthBar + "|";
				}
				String Title = "";
				Title  = "\'" +ChatColor.DARK_RED + "[ "+ChatColor.RED +  health  +ChatColor.DARK_RED +" / "+ChatColor.RED+ Mahealth+ChatColor.DARK_RED+" ]\'";
				t.sendTitleSubTitle(player,Title, HealthBar, 0, 0, 1);
			}
		}
		return;
	}
}
