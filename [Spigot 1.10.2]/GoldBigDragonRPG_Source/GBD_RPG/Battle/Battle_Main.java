package GBD_RPG.Battle;

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

import GBD_RPG.Effect.Effect_Packet;
import GBD_RPG.Monster.Monster_Object;

import org.bukkit.event.entity.EntityShootBowEvent;

public class Battle_Main implements Listener
{
	//활 공격시 얼마나 힘을 주었는지 값을 구해주는 메소드//
	@EventHandler
	public void RangeAttack(EntityShootBowEvent event)
	{
		if(event.getEntityType() == EntityType.PLAYER)
		{
			Player player = (Player) event.getEntity();
		  	if(player.isOnline())
		  	{
		  		boolean isExitFire = false;
		  		Object[] EntitiList = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 2, 2, 2).toArray();
		  		for(short count=0; count<EntitiList.length;count++)
		  		{
		  			if(((Entity)EntitiList[count]).getFireTicks()!=0)
			  			if(((Entity)EntitiList[count]).getType()==EntityType.ARMOR_STAND)
							if(((Entity)EntitiList[count]).getCustomName().contains("모닥불"))
							{
								event.getProjectile().setFireTicks(600);
								event.getProjectile().setCustomName(ChatColor.RED+""+ChatColor.BOLD+"[불 화살]");
								event.getProjectile().setCustomNameVisible(true);
							}
		  			if(isExitFire)
		  				break;
		  		}
		  		GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_BowPull((byte)(event.getForce()*100));
				GBD_RPG.Main_Main.Main_ServerOption.PlayerUseSpell.remove(player);
		  	}
		}
		return;
	}

	@EventHandler
	public void AttackRouter(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof LivingEntity)
		{
			if(event.getDamager() instanceof LivingEntity&&event.getCause() != DamageCause.PROJECTILE)
			{
				if(event.getCause() == DamageCause.ENTITY_ATTACK)
					DamageSetter(event,"M",event.getDamager(), false);//MeeleAttack
				else if(event.getCause() == DamageCause.ENTITY_EXPLOSION)
					if (event.getDamager() != null && event.getDamager() instanceof LivingEntity)
						DamageSetter(event,"E_E",event.getDamager(), false);
			}
			else if(event.getCause() == DamageCause.PROJECTILE)
			{
				if (event.getDamager() != null && event.getDamager() instanceof Projectile)
				{
					Projectile projectile = (Projectile) event.getDamager();
					if(projectile.getShooter() != null && projectile.getShooter() instanceof Player)
					{
						if(projectile.getType() == EntityType.ARROW || projectile.getType() == EntityType.SPECTRAL_ARROW || projectile.getType() == EntityType.TIPPED_ARROW)
						{
							if(projectile.isCustomNameVisible()==true)
							{
								if(projectile.getCustomName().compareTo(ChatColor.RED+""+ChatColor.BOLD+"[불 화살]")==0)
									DamageSetter(event,"R_A_F",(Entity) projectile.getShooter(), true);
								else
									DamageSetter(event,"R_A",(Entity) projectile.getShooter(), true);
							}
							else
								DamageSetter(event,"R_A",(Entity) projectile.getShooter(), true);
						}
						if(projectile.getType() == EntityType.FISHING_HOOK)
						{
							DamageSetter(event,"R_FH",(Entity) projectile.getShooter(), true);
						}
						if(projectile.getType() == EntityType.SNOWBALL)
						{
							DamageSetter(event,"R_S",(Entity) projectile.getShooter(), true);
						}
					}
				}
			}
			else if(event.getDamager().getType() == EntityType.SPLASH_POTION)
				DamageSetter(event,"P",(Entity) ((SplashPotion) event.getDamager()).getShooter(), false);//PotionAttack
		}
		return;
	}
	
	public void DamageSetter(EntityDamageByEntityEvent event,String AttackType,Entity Attacker, boolean isProjectile)
	{
		if(Attacker==null||event.getEntity()==null)
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
					if(Attacker.getType()==EntityType.PLAYER)
						if(((Player)Attacker).isOp()==false)
						{
							String TargetArea = null;
							GBD_RPG.Area.Area_Main A = new GBD_RPG.Area.Area_Main();
							if(A.getAreaName(event.getEntity()) != null)
								TargetArea = A.getAreaName(event.getEntity())[0];
							if(TargetArea != null && A.getAreaOption(TargetArea, (char) 1) == false)
							{
								event.setCancelled(true);
								return;
							}
						}
				}
			}
			else
			{
				if(Attacker.getType()==EntityType.PLAYER)
					if(((Player)Attacker).isOp()==false)
					{
						String TargetArea = null;
						GBD_RPG.Area.Area_Main A = new GBD_RPG.Area.Area_Main();
						if(A.getAreaName(event.getEntity()) != null)
							TargetArea = A.getAreaName(event.getEntity())[0];
						if(TargetArea != null && A.getAreaOption(TargetArea, (char) 1) == false)
						{
							event.setCancelled(true);
							return;
						}
					}
			}
		}

	    GBD_RPG.Effect.Effect_Sound sound = new GBD_RPG.Effect.Effect_Sound();
		if(Attacker.getType()==EntityType.PLAYER&& event.getEntity().getType()==EntityType.PLAYER)
		{
			Player player = (Player) Attacker;
			Player target = (Player) event.getEntity();
			if(player.isOnline()&&target.isOnline())
			{
				if(GBD_RPG.Main_Main.Main_ServerOption.PVP==false)
				{
					event.setCancelled(true);
					return;
				}
				if(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.containsKey(player)&&GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.containsKey(target))
					if(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player).equals(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(target)))
					{
						event.setCancelled(true);
						return;
					}
				String AttackerArea = null;
				String TargetArea = null;

				GBD_RPG.Area.Area_Main A = new GBD_RPG.Area.Area_Main();
				if(A.getAreaName(player) != null)
					AttackerArea = A.getAreaName(player)[0];
				if(A.getAreaName(target) != null)
					TargetArea = A.getAreaName(target)[0];
				
				if(AttackerArea!=null && A.getAreaOption(AttackerArea, (char) 0) == false)
				{
					event.setCancelled(true);
					return;
				}
				else if(TargetArea != null && A.getAreaOption(TargetArea, (char) 0) == false)
				{
					event.setCancelled(true);
					return;
				}
			}
		}
	    Battle_Calculator damage = new Battle_Calculator();
	    Effect_Packet t = new Effect_Packet();

	    int Damage = (int) event.getDamage();
	    if(Attacker.getType() == EntityType.PLAYER)
	    {
	    	Damage = Damage-1;
	    	Player player = (Player)Attacker;
	    	if(GBD_RPG.Main_Main.Main_ServerOption.PlayerUseSpell.containsKey(player))
	    		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerUseSpell.get(player).compareTo("us")==0)
	    		{
	    			if(player.isOnline())
	    				Alert(player, event.getEntity(), (int)event.getDamage());
	    			GBD_RPG.Main_Main.Main_ServerOption.PlayerUseSpell.remove(player);
	    			event.setDamage(event.getDamage());
	    			return;
	    		}
	    }
	    int Attacker_Stat[] = null;

		if (AttackType == "R_A"||AttackType == "R_A_F"||AttackType == "M"||AttackType == "E_E"||AttackType == "P")//Range Arrow
			Attacker_Stat = getAttackerStats(Attacker);
		
	    int Defender_Stat[] = getDefenderStats(event.getEntity());
			
		if (AttackType == "R_A"||AttackType == "R_A_F")
		{
			if (AttackType == "R_A_F")
				Damage = Damage+(Damage/2);
			Damage = damage.damagerand(Attacker, damage.returnRangeDamageValue(Attacker, Attacker_Stat[1], Damage, true),
					damage.returnRangeDamageValue(Attacker, Attacker_Stat[1], Damage, false), Attacker_Stat[8]);
			if(Attacker instanceof Player)
			{
				Player player = (Player)Attacker;
				if(player.isOnline())
					Damage = (int)((Damage* GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_BowPull() )/110);
			}
		}
		else if(AttackType == "M")
		{
			Damage = damage.damagerand(Attacker, damage.CombatDamageGet(Attacker, (int)Damage, Attacker_Stat[0], true),
					damage.CombatDamageGet(Attacker, (int)Damage, Attacker_Stat[0], false),  Attacker_Stat[8]);

			if(Attacker.getType() == EntityType.PLAYER)
			{
				Player player = (Player)Attacker;
				if(player.getInventory().getItemInMainHand().getType()==Material.BOW)
					Damage=Damage/10;
			}
		}
		else if(AttackType == "E_E")
			Damage = damage.damagerand(Attacker, damage.returnExplosionDamageValue(Attacker_Stat[2], Damage, true), damage.returnExplosionDamageValue(Attacker_Stat[2], Damage, false),  Attacker_Stat[8]);
		else if(AttackType == "P")
			Damage = damage.damagerand(Attacker, damage.returnCombatValue(Attacker_Stat[2], Damage, true), damage.returnCombatValue(Attacker_Stat[2], Damage, false),  Attacker_Stat[8]);
		
		
		int critdamage = damage.criticalrend(Attacker, Attacker_Stat[4], Attacker_Stat[1],Damage, Defender_Stat[1],Attacker_Stat[7]);

		if(critdamage != 0)
		{
			Damage = (Damage+critdamage);
			if(Attacker instanceof Player)
			{
				Player player = (Player)Attacker;
				if(player.isOnline())
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Critical() == true)
						t.sendTitleSubTitle(player, "\'\'", "\'"+ChatColor.YELLOW + "크리티컬 히트!\'", (byte)1, (byte)0, (byte)1);
			}
			sound.SL(event.getEntity().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, (float)0.5, (float)2.0);
			sound.SL(event.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, (float)0.5, (float)1.7);
		}

		int DamageMinus = Defender_Stat[0]-Attacker_Stat[5]; 
		if(DamageMinus <= 0)
			DamageMinus = 0;
		
		Damage = Damage-DamageMinus;
		if(Defender_Stat[1] < 0)
			Defender_Stat[1] = 0;

		if(Damage >= 100)
			Damage =(int)(Damage*(100-Defender_Stat[1])/100);
		else if(Damage >= 10)
			Damage =(int)(Damage*((100-Defender_Stat[1])/10)/10);
		else
			Damage =(int)(Damage-Defender_Stat[1]);
		if(Damage <= 0 || (100-Defender_Stat[1])<=0/*보호가 100 이상일 경우*/)
		{
			if(Attacker instanceof Player)
			{
				Player player = (Player)Attacker;
				if(player.isOnline())
					DamageCancellMessage(player, event.getEntity());
				if(isProjectile)
					event.getDamager().remove();
			}
			event.setDamage(0.5);
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
		for(byte count=0;count<9;count++)
			Attacker_Stat[count] = 0;
	    Battle_Calculator damage = new Battle_Calculator();
		
	    if(entity.getType() == EntityType.PLAYER)
	    {
	    	Player player = (Player)entity;
	    	if(player.isOnline())
	    	{
	    		int STR = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
	    		int DEX = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
	    		int INT = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
	    		int WILL = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
	    		int LUK = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
	    		int DEFcrash = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEFcrash();
	    		int MagicDEFcrash = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MagicDEFcrash();
	    		int Critical = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Critical();
	    		int Balance = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Balance();
	    		
	    	  	damage.decreaseDurabilityWeapon(player);
	    	  	Attacker_Stat[0] = STR + damage.getPlayerEquipmentStat(player, "STR", false, null)[0];
	    	  	Attacker_Stat[1] = DEX + damage.getPlayerEquipmentStat(player, "DEX", false, null)[0];
	    	  	Attacker_Stat[2] = INT + damage.getPlayerEquipmentStat(player, "INT", false, null)[0];
	    	  	Attacker_Stat[3] = WILL + damage.getPlayerEquipmentStat(player, "WILL", false, null)[0];
	    	  	Attacker_Stat[4] = LUK + damage.getPlayerEquipmentStat(player, "LUK", false, null)[0];
	    	  	Attacker_Stat[5] = DEFcrash + damage.getPlayerEquipmentStat(player, "DEFcrash", false, null)[0] + damage.getDEFcrash(player,Attacker_Stat[1]);
	    	  	Attacker_Stat[6] = MagicDEFcrash + damage.getPlayerEquipmentStat(player, "MagicDEFcrash", false, null)[0];
	    	  	Attacker_Stat[7] = Critical;
	    	  	Attacker_Stat[8] = damage.getBalance(player, Attacker_Stat[1], Balance);
	    	}
	    	else
	    	{
	    		if(entity.getCustomName() != null)
	    		{
	    			String name = entity.getCustomName().toString();
	    			if(GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.containsKey(name))
	    			{
	    				Monster_Object OM = GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.get(name));
    					Attacker_Stat[0] = OM.getSTR();
    					Attacker_Stat[1] = OM.getDEX();
    					Attacker_Stat[2] = OM.getINT();
    					Attacker_Stat[3] = OM.getWILL();
    					Attacker_Stat[4] = OM.getLUK();
    					Attacker_Stat[5] = 0;
    					Attacker_Stat[6] = 0;
    		    	  	Attacker_Stat[8] = damage.getBalance(entity, Attacker_Stat[1], 0);
	    			}
	    		}
	    	}
	    }
	    else
	    {
    		if(entity.getCustomName() != null)
    		{
    			String name = entity.getCustomName().toString();
    			if(GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.containsKey(name))
    			{
    				Monster_Object OM = GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.get(name));
					Attacker_Stat[0] = OM.getSTR();
					Attacker_Stat[1] = OM.getDEX();
					Attacker_Stat[2] = OM.getINT();
					Attacker_Stat[3] = OM.getWILL();
					Attacker_Stat[4] = OM.getLUK();
					Attacker_Stat[5] = 0;
					Attacker_Stat[6] = 0;
		    	  	Attacker_Stat[8] = damage.getBalance(entity, Attacker_Stat[1], 0);
    			}
    		}
	    }
	    return Attacker_Stat;
	}
	
	public int[] getDefenderStats(Entity entity)
	{
		int Defender_Stat[] = new int[4];
		for(byte count=0;count<4;count++)
			Defender_Stat[count] = 0;
	    Battle_Calculator damage = new Battle_Calculator();
		
	    if(entity.getType() == EntityType.PLAYER)
	    {
	    	Player player = (Player)entity;
	    	if(player.isOnline())
	    	{
	    	  	damage.decreaseDurabilityArmor(player);
	    	  	int DEF = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEF();
	    	  	int Protect = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Protect();
	    	  	int Magic_DEF = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_DEF();
	    	  	int Magic_Protect = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_Protect();
	    	  	
	    	  	Defender_Stat[0] = DEF + damage.getPlayerEquipmentStat(player, "DEF", false, null)[0];
	    	  	Defender_Stat[1] = Protect + damage.getPlayerEquipmentStat(player, "Protect", false, null)[0];
	    	  	Defender_Stat[2] = Magic_DEF + damage.getPlayerEquipmentStat(player, "Magic_DEF", false, null)[0];
	    	  	Defender_Stat[3] = Magic_Protect + damage.getPlayerEquipmentStat(player, "Magic_Protect", false, null)[0];
	    	}
	    	else
	    	{
				if(entity.getCustomName() != null)
				{
					String name = entity.getCustomName().toString();
	    			if(GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.containsKey(name))
	    			{
	    				Monster_Object OM = GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.get(name));
	    				Defender_Stat[0] = OM.getDEF();
						Defender_Stat[1] = OM.getPRO();
						Defender_Stat[2] = OM.getMDEF();
						Defender_Stat[3] = OM.getMPRO();
	    			}
				}
	    	}
	    }
	    else
	    {
			if(entity.getCustomName() != null)
			{
				String name = entity.getCustomName().toString();
    			if(GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.containsKey(name))
    			{
    				Monster_Object OM = GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.get(name));
    				Defender_Stat[0] = OM.getDEF();
					Defender_Stat[1] = OM.getPRO();
					Defender_Stat[2] = OM.getMDEF();
					Defender_Stat[3] = OM.getMPRO();
    			}
			}
	    }
	    return Defender_Stat;
	}
	
	//데미지가 0이 떴을 때 띄우는 랜덤 메시지를 정해주는 메소드//
	public void DamageCancellMessage (Player player, Entity defenser)
	{
		byte a = (byte) new GBD_RPG.Util.Util_Number().RandomNum(1, 5);
		new GBD_RPG.Effect.Effect_Sound().SL(defenser.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, (float)1.0, (float)0.7);
	    Effect_Packet t = new Effect_Packet();

		if (a == 1) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "이 공격은 전혀 통하지 않는다!");
		else if (a == 2) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "자세를 흐트릴 수 없다!");
		else if (a == 3) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "충격이 분산되었다!");
		else if (a == 4) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "이 공격으로는 쓰러뜨릴 수 없을 것 같다!");
		else if (a == 5) t.sendActionBar(player, ChatColor.RED +""+ChatColor.BOLD+ "적의 자세를 흐트릴 수 없다!");
		return;
	}

	public void Alert (Player player, Entity defenser, int Damage)
	{
	    Effect_Packet t = new Effect_Packet();

		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage() == true)
			t.sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+Damage + "데미지!");
		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth() == true)
		{
			if(defenser instanceof LivingEntity)
			{
				LivingEntity defenser2 = (LivingEntity) defenser;
				Damageable getouter = (Damageable)defenser2;
				int health = (int)getouter.getHealth() - Damage;
				int Mahealth = (int)getouter.getMaxHealth();
				StringBuffer HealthBar = new StringBuffer(ChatColor.RED+"");

				if(health <= 0)
					health = 1;

				int Percent= (int)(Mahealth/10);
				int healthCount = 0;
				if(Percent <=0)
					healthCount = 2;
				else
					healthCount = health/Percent;
				
				for(int count = 0; count<healthCount-1;count++)
					HealthBar.append("■");
				HealthBar.append(ChatColor.DARK_RED);
				HealthBar.append("■");
				HealthBar.append(ChatColor.BLACK);
				for(int count = 0; count<10-healthCount;count++)
					HealthBar.append("■");
				
				String Title  = "\'" +ChatColor.DARK_RED +""+ChatColor.BOLD+"[ "+ChatColor.RED +""+ChatColor.BOLD+  health  +ChatColor.DARK_RED +""+ChatColor.BOLD+" / "+ChatColor.RED +""+ChatColor.BOLD+ Mahealth+ChatColor.DARK_RED +""+ChatColor.BOLD+ " ]\'";
				t.sendTitleSubTitle(player, HealthBar.toString(), Title, (byte)0, (byte)0, (byte)1);
			}
		}
		return;
	}
}
