package GoldBigDragon_RPG.Attack;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import GoldBigDragon_RPG.Effect.PacketSender;
import GoldBigDragon_RPG.Main.Object_Monster;
import net.minecraft.server.v1_8_R3.EntityPotion;

import org.bukkit.event.entity.EntityShootBowEvent;

public class Attack
{
	//활 공격시 얼마나 힘을 주었는지 값을 구해주는 메소드//
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
		  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_BowPull((byte)(event.getForce()*100));
				GoldBigDragon_RPG.Main.ServerOption.PlayerUseSpell.remove(player);
		  	}
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
						if(projectile.getType() == EntityType.ARROW)
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
				DamageSetter(event,"P",(Entity) ((EntityPotion) event.getDamager()).getShooter(), false);//PotionAttack
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
							GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
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
						GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
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

	    GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		if(Attacker.getType()==EntityType.PLAYER&& event.getEntity().getType()==EntityType.PLAYER)
		{
			Player player = (Player) Attacker;
			Player target = (Player) event.getEntity();
			if(player.isOnline()&&target.isOnline())
			{
				if(GoldBigDragon_RPG.Main.ServerOption.PVP==false)
				{
					event.setCancelled(true);
					return;
				}
				if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player)&&GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(target))
					if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player).equals(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(target)))
					{
						event.setCancelled(true);
						return;
					}
				String AttackerArea = null;
				String TargetArea = null;

				GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
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
	    Damage damage = new Damage();
	    PacketSender t = new PacketSender();

	    int Damage = (int) event.getDamage();
	    if(Attacker.getType() == EntityType.PLAYER)
	    {
	    	Damage = Damage-1;
	    	Player player = (Player)Attacker;
	    	if(GoldBigDragon_RPG.Main.ServerOption.PlayerUseSpell.containsKey(player))
	    		if(GoldBigDragon_RPG.Main.ServerOption.PlayerUseSpell.get(player).compareTo("us")==0)
	    		{
	    			if(player.isOnline())
	    				Alert(player, event.getEntity(), (int)event.getDamage());
	    			GoldBigDragon_RPG.Main.ServerOption.PlayerUseSpell.remove(player);
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
					Damage = (int)((Damage* GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_BowPull() )/110);
			}
		}
		else if(AttackType == "M")
		{
			Damage = damage.damagerand(Attacker, damage.CombatMinDamageGet(Attacker, (int)Damage, Attacker_Stat[0]),
					damage.CombatMaxDamageGet(Attacker, (int)Damage, Attacker_Stat[0]),  Attacker_Stat[8]);

			if(Attacker.getType() == EntityType.PLAYER)
			{
				Player player = (Player)Attacker;
				if(player.getItemInHand().getType()==Material.BOW)
					Damage=Damage/10;
			}
		}

		if(AttackType == "E_E")
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
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Critical() == true)
						t.sendTitleSubTitle(player, "\'\'", "\'"+ChatColor.YELLOW + "크리티컬 히트!\'", (byte)1, (byte)0, (byte)1);
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
	    Damage damage = new Damage();
		
	    if(entity.getType() == EntityType.PLAYER)
	    {
	    	Player player = (Player)entity;
	    	if(player.isOnline())
	    	{
	    		int STR = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
	    		int DEX = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
	    		int INT = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
	    		int WILL = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
	    		int LUK = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
	    		int DEFcrash = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEFcrash();
	    		int MagicDEFcrash = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MagicDEFcrash();
	    		int Critical = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Critical();
	    		int Balance = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Balance();
	    		
	    	  	damage.decreaseDurabilityWeapon(player);
	    	  	Attacker_Stat[0] = STR + damage.getPlayerEquipmentStat(player, "STR", null, false)[0];
	    	  	Attacker_Stat[1] = DEX + damage.getPlayerEquipmentStat(player, "DEX", null, false)[0];
	    	  	Attacker_Stat[2] = INT + damage.getPlayerEquipmentStat(player, "INT", null, false)[0];
	    	  	Attacker_Stat[3] = WILL + damage.getPlayerEquipmentStat(player, "WILL", null, false)[0];
	    	  	Attacker_Stat[4] = LUK + damage.getPlayerEquipmentStat(player, "LUK", null, false)[0];
	    	  	Attacker_Stat[5] = DEFcrash + damage.getPlayerEquipmentStat(player, "DEFcrash", null, false)[0] + damage.getDEFcrash(player,Attacker_Stat[1]);
	    	  	Attacker_Stat[6] = MagicDEFcrash + damage.getPlayerEquipmentStat(player, "MagicDEFcrash", null, false)[0];
	    	  	Attacker_Stat[7] = Critical;
	    	  	Attacker_Stat[8] = damage.getBalance(player, Attacker_Stat[1], Balance);
	    	}
	    	else
	    	{
	    		if(entity.getCustomName() != null)
	    		{
	    			String name = entity.getCustomName().toString();
	    			if(GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.containsKey(name))
	    			{
	    				Object_Monster OM = GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.get(name));
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
    			if(GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.containsKey(name))
    			{
    				Object_Monster OM = GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.get(name));
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
	    Damage damage = new Damage();
		
	    if(entity.getType() == EntityType.PLAYER)
	    {
	    	Player player = (Player)entity;
	    	if(player.isOnline())
	    	{
	    	  	damage.decreaseDurabilityArmor(player);
	    	  	int DEF = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEF();
	    	  	int Protect = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Protect();
	    	  	int Magic_DEF = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_DEF();
	    	  	int Magic_Protect = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_Protect();
	    	  	
	    	  	Defender_Stat[0] = DEF + damage.getPlayerEquipmentStat(player, "DEF", null, false)[0];
	    	  	Defender_Stat[1] = Protect + damage.getPlayerEquipmentStat(player, "Protect", null, false)[0];
	    	  	Defender_Stat[2] = Magic_DEF + damage.getPlayerEquipmentStat(player, "Magic_DEF", null, false)[0];
	    	  	Defender_Stat[3] = Magic_Protect + damage.getPlayerEquipmentStat(player, "Magic_Protect", null, false)[0];
	    	}
	    	else
	    	{
				if(entity.getCustomName() != null)
				{
					String name = entity.getCustomName().toString();
	    			if(GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.containsKey(name))
	    			{
	    				Object_Monster OM = GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.get(name));
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
    			if(GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.containsKey(name))
    			{
    				Object_Monster OM = GoldBigDragon_RPG.Main.ServerOption.MonsterList.get(GoldBigDragon_RPG.Main.ServerOption.MonsterNameMatching.get(name));
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
		byte a = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(1, 5);
		new GoldBigDragon_RPG.Effect.Sound().SL(defenser.getLocation(), Sound.ZOMBIE_METAL, (float)1.0, (float)0.7);
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
	    PacketSender t = new PacketSender();

		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage() == true)
			t.sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+Damage + "데미지!");
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth() == true)
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
