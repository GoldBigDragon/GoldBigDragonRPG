package battle;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SplashPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import effect.SendPacket;
import effect.SoundEffect;
import monster.MonsterObject;

import org.bukkit.event.entity.EntityShootBowEvent;

public class BattleMain implements Listener
{
	//?�� 공격?�� ?��마나 ?��?�� 주었?���? 값을 구해주는 메소?��//
	@EventHandler
	public void rangeAttack(EntityShootBowEvent event)
	{
		if(event.getEntityType() == EntityType.PLAYER)
		{
			Player player = (Player) event.getEntity();
		  	if(player.isOnline())
		  	{
		  		boolean isExitFire = false;
		  		Object[] entitiList = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 2, 2, 2).toArray();
		  		for(int count=0; count<entitiList.length;count++)
		  		{
		  			if(((Entity)entitiList[count]).getFireTicks()!=0)
			  			if(((Entity)entitiList[count]).getType()==EntityType.ARMOR_STAND)
							if(((Entity)entitiList[count]).getCustomName().contains("모닥불"))
							{
								event.getProjectile().setFireTicks(600);
								event.getProjectile().setCustomName("§c§l[불 화살]");
								event.getProjectile().setCustomNameVisible(true);
							}
		  			if(isExitFire)
		  				break;
		  		}
		  		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_BowPull((byte)(event.getForce()*100));
				main.MainServerOption.PlayerUseSpell.remove(player);
		  	}
		}
		return;
	}

	@EventHandler
	public void attackRouter(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof LivingEntity)
		{
			if(event.getDamager() instanceof LivingEntity&&event.getCause() != DamageCause.PROJECTILE)
			{
				if(event.getCause() == DamageCause.ENTITY_ATTACK)
					damageSetter(event,"M",event.getDamager(), false);//MeeleAttack
				else if(event.getCause() == DamageCause.ENTITY_EXPLOSION)
					if (event.getDamager() != null && event.getDamager() instanceof LivingEntity)
						damageSetter(event,"E_E",event.getDamager(), false);
			}
			else if(event.getCause() == DamageCause.PROJECTILE)
			{
				if (event.getDamager() != null && event.getDamager() instanceof Projectile)
				{
					Projectile projectile = (Projectile) event.getDamager();
					if(projectile.getShooter() != null)
					{
						if(projectile.getShooter() instanceof Player)
						{
							if(projectile.getType() == EntityType.ARROW || projectile.getType() == EntityType.SPECTRAL_ARROW || projectile.getType() == EntityType.TIPPED_ARROW)
							{
								if(projectile.isCustomNameVisible())
								{
									if(projectile.getCustomName().equals("§c§l[불 화살]"))
										damageSetter(event,"R_A_F",(Entity) projectile.getShooter(), true);
									else
										damageSetter(event,"R_A",(Entity) projectile.getShooter(), true);
								}
								else
									damageSetter(event,"R_A",(Entity) projectile.getShooter(), true);
							}
							if(projectile.getType() == EntityType.FISHING_HOOK)
							{
								damageSetter(event,"R_FH",(Entity) projectile.getShooter(), true);
							}
							if(projectile.getType() == EntityType.SNOWBALL)
							{
								damageSetter(event,"R_S",(Entity) projectile.getShooter(), true);
							}
						}
						else
							damageSetter(event,"R_A",(Entity) projectile.getShooter(), true);
					}
				}
			}
			else if(event.getDamager().getType() == EntityType.SPLASH_POTION)
				damageSetter(event,"P",(Entity) ((SplashPotion) event.getDamager()).getShooter(), false);//PotionAttack
		}
		return;
	}
	
	public void damageSetter(EntityDamageByEntityEvent event,String attackType,Entity attacker, boolean isProjectile)
	{
		if(attacker==null||event.getEntity()==null)
			return;
		if(event.getEntity().getType()==EntityType.ARMOR_STAND)
		{
			if(event.getEntity().getCustomName()!=null)
			{
				if(event.getEntity().getCustomName().charAt(0)=='§'&&event.getEntity().getCustomName().charAt(2)=='§')
				{
					if((event.getEntity().getCustomName().charAt(1)=='0'&&event.getEntity().getCustomName().charAt(3)=='l')
					||(event.getEntity().getCustomName().charAt(1)=='c'&&event.getEntity().getCustomName().charAt(3)=='0'))
					if(isProjectile)
						event.getDamager().remove();
					event.setCancelled(true);
					return;
				}
				else
				{
					if(attacker.getType()==EntityType.PLAYER)
						if(!((Player)attacker).isOp())
						{
							String targetArea = null;
							area.AreaMain areaMain = new area.AreaMain();
							if(areaMain.getAreaName(event.getEntity()) != null)
								targetArea = areaMain.getAreaName(event.getEntity())[0];
							if(targetArea != null && !areaMain.getAreaOption(targetArea, (char) 1))
							{
								event.setCancelled(true);
								return;
							}
						}
				}
			}
			else
			{
				if(attacker.getType()==EntityType.PLAYER)
					if(!((Player)attacker).isOp())
					{
						String targetArea = null;
						area.AreaMain areaMain = new area.AreaMain();
						if(areaMain.getAreaName(event.getEntity()) != null)
							targetArea = areaMain.getAreaName(event.getEntity())[0];
						if(targetArea != null && !areaMain.getAreaOption(targetArea, (char) 1))
						{
							event.setCancelled(true);
							return;
						}
					}
			}
		}

		if(attacker.getType()==EntityType.PLAYER&& event.getEntity().getType()==EntityType.PLAYER)
		{
			Player player = (Player) attacker;
			Player target = (Player) event.getEntity();
			if(player.isOnline()&&target.isOnline())
			{
				if(!main.MainServerOption.PVP)
				{
					event.setCancelled(true);
					return;
				}
				if(main.MainServerOption.partyJoiner.containsKey(player)&&main.MainServerOption.partyJoiner.containsKey(target))
					if(main.MainServerOption.partyJoiner.get(player).equals(main.MainServerOption.partyJoiner.get(target)))
					{
						event.setCancelled(true);
						return;
					}
				String attackerArea = null;
				String targetArea = null;

				area.AreaMain areaMain = new area.AreaMain();
				if(areaMain.getAreaName(player) != null)
					attackerArea = areaMain.getAreaName(player)[0];
				if(areaMain.getAreaName(target) != null)
					targetArea = areaMain.getAreaName(target)[0];
				
				if(attackerArea!=null && !areaMain.getAreaOption(attackerArea, (char) 0))
				{
					event.setCancelled(true);
					return;
				}
				else if(targetArea != null && !areaMain.getAreaOption(targetArea, (char) 0))
				{
					event.setCancelled(true);
					return;
				}
			}
		}
	    SendPacket t = new SendPacket();

	    int damage = (int) event.getDamage();
	    if(attacker.getType() == EntityType.PLAYER)
	    {
	    	damage = damage-1;
	    	Player player = (Player)attacker;
	    	if(main.MainServerOption.PlayerUseSpell.containsKey(player))
	    		if(main.MainServerOption.PlayerUseSpell.get(player).equals("us"))
	    		{
	    			if(player.isOnline())
	    				alert(player, event.getEntity(), (int)event.getDamage());
	    			main.MainServerOption.PlayerUseSpell.remove(player);
	    			event.setDamage(event.getDamage());
	    			return;
	    		}
	    }
	    int[] attackerStat = null;

		if (attackType.charAt(0)=='R'||attackType.charAt(0)=='M'||attackType.charAt(0)=='E'||attackType.charAt(0)=='P')//Range Arrow
			attackerStat = getAttackerStats(attacker);
		
	    int[] defenderStat = getDefenderStats(event.getEntity());
			
		if(attackType.charAt(0)=='R')
		{
			if(attackType == "R_S")//?��?��?��
			{
				damage = 0;
			}
			else
			{
				if(attackType.equals("R_A_F"))
					damage = damage+(damage/2);
				damage = BattleCalculator.damagerand(attacker, BattleCalculator.returnRangeDamageValue(attacker, attackerStat[1], damage, true),
						BattleCalculator.returnRangeDamageValue(attacker, attackerStat[1], damage, false), attackerStat[8]);
				if(attacker instanceof Player)
				{
					Player player = (Player)attacker;
					if(player.isOnline())
						damage = (int)((damage* main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_BowPull() )/110);
				}
			}
		}
		else if(attackType.charAt(0)=='M')
		{
			damage = BattleCalculator.damagerand(attacker, BattleCalculator.getCombatDamage(attacker, (int)damage, attackerStat[0], true),
					BattleCalculator.getCombatDamage(attacker, (int)damage, attackerStat[0], false),  attackerStat[8]);

			if(attacker.getType() == EntityType.PLAYER)
			{
				Player player = (Player)attacker;
				if(player.getInventory().getItemInMainHand().getType()==Material.BOW)
					damage=damage/10;
			}
		}
		else if(attackType == "E_E")
			damage = BattleCalculator.damagerand(attacker, BattleCalculator.returnExplosionDamageValue(attackerStat[2], damage, true), BattleCalculator.returnExplosionDamageValue(attackerStat[2], damage, false),  attackerStat[8]);
		else if(attackType == "P")
			damage = BattleCalculator.damagerand(attacker, BattleCalculator.returnCombatValue(attackerStat[2], damage, true), BattleCalculator.returnCombatValue(attackerStat[2], damage, false),  attackerStat[8]);

		
		
		int critdamage = BattleCalculator.criticalrend(attacker, attackerStat[4], attackerStat[1],damage, defenderStat[1],attackerStat[7]);

		if(critdamage != 0)
		{
			damage = (damage+critdamage);
			if(attacker instanceof Player)
			{
				Player player = (Player)attacker;
				if(player.isOnline())
					if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Critical() == true)
						t.sendTitleSubTitle(player, "\'\'", "\'§e크리티컬 히트!\'", (byte)1, (byte)0, (byte)1);
			}
			SoundEffect.SL(event.getEntity().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, (float)0.5, (float)2.0);
			SoundEffect.SL(event.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, (float)0.5, (float)1.7);
		}

		int damageMinus = defenderStat[0]-attackerStat[5]; 
		if(damageMinus <= 0)
			damageMinus = 0;
		
		damage = damage-damageMinus;
		if(defenderStat[1] < 0)
			defenderStat[1] = 0;

		if(damage >= 100)
			damage =(damage*(100-defenderStat[1])/100);
		else if(damage >= 10)
			damage =(damage*((100-defenderStat[1])/10)/10);
		else
			damage =(damage-defenderStat[1]);
		if(damage <= 0 || (100-defenderStat[1])<=0/*보호가 100 이상일 경우*/)
		{
			if(!attackType.equals("R_S"))
			{
				if(attacker instanceof Player)
				{
					Player player = (Player)attacker;
					if(player.isOnline())
						damageCancellMessage(player, event.getEntity());
					if(isProjectile)
						event.getDamager().remove();
				}
				event.setDamage(0.5);
			}
			return;
		}
		event.setDamage(damage);
		
		if(attackType == "E_E")
		{
			Damageable d = (Damageable) event.getEntity();
			if(d.getHealth()<=damage)
				d.setHealth(0);
			else
				d.setHealth(d.getHealth()-damage);
			return;
		}

		if(attacker instanceof Player)
		{
			Player player = (Player)attacker;
			if(player.isOnline())
				alert(player, event.getEntity(), damage);
		}
		return;
	}
	
	public int[] getAttackerStats(Entity entity)
	{
		int attackerStat[] = new int[9];
		for(int count=0;count<9;count++)
			attackerStat[count] = 0;
		
	    if(entity.getType() == EntityType.PLAYER)
	    {
	    	Player player = (Player)entity;
	    	if(player.isOnline())
	    	{
	    		int str = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
	    		int dex = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
	    		int INT = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
	    		int will = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
	    		int luk = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
	    		int defCrash = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEFcrash();
	    		int magicDefCrash = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MagicDEFcrash();
	    		int critical = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Critical();
	    		int balance = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Balance();
	    		
	    		BattleCalculator.decreaseDurabilityWeapon(player);
	    	  	attackerStat[0] = str + BattleCalculator.getPlayerEquipmentStat(player, "STR", false, null)[0];
	    	  	attackerStat[1] = dex + BattleCalculator.getPlayerEquipmentStat(player, "DEX", false, null)[0];
	    	  	attackerStat[2] = INT + BattleCalculator.getPlayerEquipmentStat(player, "INT", false, null)[0];
	    	  	attackerStat[3] = will + BattleCalculator.getPlayerEquipmentStat(player, "WILL", false, null)[0];
	    	  	attackerStat[4] = luk + BattleCalculator.getPlayerEquipmentStat(player, "LUK", false, null)[0];
	    	  	attackerStat[5] = defCrash + BattleCalculator.getPlayerEquipmentStat(player, "DEFcrash", false, null)[0] + BattleCalculator.getDEFcrash(player,attackerStat[1]);
	    	  	attackerStat[6] = magicDefCrash + BattleCalculator.getPlayerEquipmentStat(player, "MagicDEFcrash", false, null)[0];
	    	  	attackerStat[7] = critical;
	    	  	attackerStat[8] = BattleCalculator.getBalance(player, attackerStat[1], balance);
	    	}
	    	else
	    	{
	    		if(entity.getCustomName() != null)
	    		{
	    			String name = entity.getCustomName().toString();
	    			if(main.MainServerOption.MonsterNameMatching.containsKey(name))
	    			{
	    				MonsterObject monsterObject = main.MainServerOption.MonsterList.get(main.MainServerOption.MonsterNameMatching.get(name));
    					attackerStat[0] = monsterObject.getSTR();
    					attackerStat[1] = monsterObject.getDEX();
    					attackerStat[2] = monsterObject.getINT();
    					attackerStat[3] = monsterObject.getWILL();
    					attackerStat[4] = monsterObject.getLUK();
    					attackerStat[5] = 0;
    					attackerStat[6] = 0;
    		    	  	attackerStat[8] = BattleCalculator.getBalance(entity, attackerStat[1], 0);
	    			}
	    		}
	    	}
	    }
	    else
	    {
    		if(entity.getCustomName() != null)
    		{
    			String name = entity.getCustomName().toString();
    			if(main.MainServerOption.MonsterNameMatching.containsKey(name))
    			{
    				MonsterObject monsterObject = main.MainServerOption.MonsterList.get(main.MainServerOption.MonsterNameMatching.get(name));
					attackerStat[0] = monsterObject.getSTR();
					attackerStat[1] = monsterObject.getDEX();
					attackerStat[2] = monsterObject.getINT();
					attackerStat[3] = monsterObject.getWILL();
					attackerStat[4] = monsterObject.getLUK();
					attackerStat[5] = 0;
					attackerStat[6] = 0;
		    	  	attackerStat[8] = BattleCalculator.getBalance(entity, attackerStat[1], 0);
    			}
    		}
	    }
	    return attackerStat;
	}
	
	public int[] getDefenderStats(Entity entity)
	{
		int defenderStat[] = new int[4];
		for(int count=0;count<4;count++)
			defenderStat[count] = 0;
		
	    if(entity.getType() == EntityType.PLAYER)
	    {
	    	Player player = (Player)entity;
	    	if(player.isOnline())
	    	{
	    		BattleCalculator.decreaseDurabilityArmor(player);
	    	  	int def = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEF();
	    	  	int protect = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Protect();
	    	  	int magicDef = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_DEF();
	    	  	int magicProtect = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_Protect();
	    	  	
	    	  	defenderStat[0] = def + BattleCalculator.getPlayerEquipmentStat(player, "DEF", false, null)[0];
	    	  	defenderStat[1] = protect + BattleCalculator.getPlayerEquipmentStat(player, "Protect", false, null)[0];
	    	  	defenderStat[2] = magicDef + BattleCalculator.getPlayerEquipmentStat(player, "Magic_DEF", false, null)[0];
	    	  	defenderStat[3] = magicProtect + BattleCalculator.getPlayerEquipmentStat(player, "Magic_Protect", false, null)[0];
	    	}
	    	else
	    	{
				if(entity.getCustomName() != null)
				{
					String name = entity.getCustomName().toString();
	    			if(main.MainServerOption.MonsterNameMatching.containsKey(name))
	    			{
	    				MonsterObject monsterObject = main.MainServerOption.MonsterList.get(main.MainServerOption.MonsterNameMatching.get(name));
	    				defenderStat[0] = monsterObject.getDEF();
						defenderStat[1] = monsterObject.getPRO();
						defenderStat[2] = monsterObject.getMDEF();
						defenderStat[3] = monsterObject.getMPRO();
	    			}
				}
	    	}
	    }
	    else
	    {
			if(entity.getCustomName() != null)
			{
				String name = entity.getCustomName().toString();
    			if(main.MainServerOption.MonsterNameMatching.containsKey(name))
    			{
    				MonsterObject monsterObject = main.MainServerOption.MonsterList.get(main.MainServerOption.MonsterNameMatching.get(name));
    				defenderStat[0] = monsterObject.getDEF();
					defenderStat[1] = monsterObject.getPRO();
					defenderStat[2] = monsterObject.getMDEF();
					defenderStat[3] = monsterObject.getMPRO();
    			}
			}
	    }
	    return defenderStat;
	}
	
	//?��미�?�? 0?�� ?��?�� ?�� ?��?��?�� ?��?�� 메시�?�? ?��?��주는 메소?��//
	public void damageCancellMessage (Player player, Entity defenser)
	{
		byte a = (byte) new util.UtilNumber().RandomNum(1, 5);
		SoundEffect.SL(defenser.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, (float)1.0, (float)0.7);
	    SendPacket t = new SendPacket();

		if (a == 1) t.sendActionBar(player, "§c§l이 공격은 전혀 통하지 않는다!");
		else if (a == 2) t.sendActionBar(player, "§c§l자세를 흐트릴 수 없다!");
		else if (a == 3) t.sendActionBar(player, "§c§l충격이 분산되었다!");
		else if (a == 4) t.sendActionBar(player, "§c§l이 공격으로는 쓰러뜨릴 수 없을 것 같다!");
		else if (a == 5) t.sendActionBar(player, "§c§l적의 자세를 흐트릴 수 없다!");
		return;
	}

	public void alert (Player player, Entity defenser, int damage)
	{
	    SendPacket t = new SendPacket();

		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage() == true)
			t.sendActionBar(player, "§c§l"+damage + "데미지!");
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth() == true)
		{
			if(defenser instanceof LivingEntity)
			{
				LivingEntity defenser2 = (LivingEntity) defenser;
				Damageable getouter = defenser2;
				int health = (int)getouter.getHealth() - damage;
				int maxHealth = (int)getouter.getMaxHealth();
				StringBuilder healthBar = new StringBuilder("§c");

				if(health <= 0)
					health = 1;

				int percent= (maxHealth/10);
				int healthCount = 0;
				if(percent <=0)
					healthCount = 2;
				else
					healthCount = health/percent;
				
				for(int count = 0; count<healthCount-1;count++)
					healthBar.append("■");
				healthBar.append(ChatColor.DARK_RED);
				healthBar.append("■");
				healthBar.append(ChatColor.BLACK);
				for(int count = 0; count<10-healthCount;count++)
					healthBar.append("■");
				
				String title  = "\'§4§l[ §c§l"+  health  +"§4§l / §c§l"+ maxHealth+"§4§l ]\'";
				t.sendTitleSubTitle(player, healthBar.toString(), title, (byte)0, (byte)0, (byte)1);
			}
		}
		return;
	}
}
