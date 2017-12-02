package customitem;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import effect.SoundEffect;
import main.MainServerOption;
import servertick.ServerTickMain;
import util.YamlLoader;



public class UseableItemMain
{
	public void useAbleItemUse(Player player, String type)
	{
		ItemStack item = player.getInventory().getItemInMainHand();
		if(type.equals("귀환서"))
		{
			if(ServerTickMain.PlayerTaskList.containsKey(player.getName())==true)
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				new effect.SendPacket().sendActionBar(player, "§c§l[현재 텔레포트를 할 수 없는 상태입니다!]", false);
				return;
			}
			util.ETC etc = new util.ETC();
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime() >= etc.getSec())
			{
				player.sendMessage("§c[이동 불가] : §e"+((main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime()+15000 - etc.getSec())/1000)+"§c 초 후에 이동 가능합니다!");
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				return;
			}
			String world = "";
			int x = 0;
			short y = 0;
			int z = 0;
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("월드"))
						world = nowlore.split(" : ")[1];
					else if(nowlore.contains("X 좌표"))
						x = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Y 좌표"))
						y = Short.parseShort(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Z 좌표"))
						z = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			long utc= servertick.ServerTickMain.nowUTC-1;
			servertick.ServerTickObject STSO = new servertick.ServerTickObject(utc, "P_UTS");
			Location loc = player.getLocation();
			STSO.setTick(utc);//텔레포트 시작 시간
			STSO.setCount(5);//텔레포트 시간
			STSO.setString((byte)0, world+","+x+","+y+","+z);//이동 위치 저장
			STSO.setString((byte)1, loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());//현재 위치 저장
			STSO.setString((byte)2, player.getName());//플레이어 이름 저장
			servertick.ServerTickMain.Schedule.put(utc, STSO);
			ServerTickMain.PlayerTaskList.put(player.getName(), ""+utc);
			new effect.PottionBuff().givePotionEffect(player, PotionEffectType.CONFUSION, 8, 255);
			SoundEffect.SP(player, Sound.BLOCK_CLOTH_BREAK, 0.7F, 0.5F);
			SoundEffect.SP(player, Sound.BLOCK_PORTAL_TRAVEL, 0.6F, 1.4F);
		}
		else if(type.equals("주문서"))
		{
			if(item.getItemMeta().getDisplayName().equals("§2§3§4§3§3§l[스텟 초기화 주문서]"))
			{
			  	YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				if(!configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
				{
					if(item.getAmount() != 1)
					{
						item.setAmount(item.getAmount()-1);
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
					}
					else
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
					int totalStatPoint = configYaml.getInt("DefaultStat.StatPoint")+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint();
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() - configYaml.getInt("DefaultStat.STR");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() - configYaml.getInt("DefaultStat.DEX");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() - configYaml.getInt("DefaultStat.INT");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() - configYaml.getInt("DefaultStat.WILL");
					totalStatPoint += main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() - configYaml.getInt("DefaultStat.LUK");
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_STR(configYaml.getInt("DefaultStat.STR"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_DEX(configYaml.getInt("DefaultStat.DEX"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_INT(configYaml.getInt("DefaultStat.INT"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_WILL(configYaml.getInt("DefaultStat.WILL"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_LUK(configYaml.getInt("DefaultStat.LUK"));
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_StatPoint(totalStatPoint);
					SoundEffect.SP(player, Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.2F, 0.5F);
					player.sendMessage("§e§l[SYSTEM] : 스텟이 초기화되었습니다!");
				}
				else
				{
					SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[System] : 메이플 스토리 시스템일 경우만 사용 가능합니다!");
				}
				return;
			}
			
			int statPoint = 0;
			int skillPoint = 0;
			int def = 0;
			int protect = 0;
			int magicDef = 0;
			int magicProtect  = 0;
			int balance = 0;
			int critical  = 0;
			int hp  = 0;
			int mp  = 0;
			int str  = 0;
			int dex  = 0;
			int INT  = 0;
			int will  = 0;
			int luk  = 0;
			
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("포인트"))
					{
						if(nowlore.contains("스텟"))
							statPoint = Integer.parseInt(nowlore.split(" : ")[1]);
						if(nowlore.contains("스킬"))
							skillPoint = Integer.parseInt(nowlore.split(" : ")[1]);
					}
					if(nowlore.contains("방어"))
						if(nowlore.contains("마법"))
							magicDef = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							def = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("보호"))
						if(nowlore.contains("마법"))
							magicProtect = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							protect = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("밸런스"))
						balance = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("크리티컬"))
						critical = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("생명력"))
						hp = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("마나"))
						mp = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statSTR))
						str = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statDEX))
						dex = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statINT))
						INT = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statWILL))
						will = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(MainServerOption.statLUK))
						luk = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(skillPoint!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_SkillPoint(skillPoint);
			if(statPoint!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(statPoint);
			if(def!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEF(def);
			if(protect!=0)
			main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Protect(protect);
			if(magicDef!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_DEF(magicDef);
			if(magicProtect!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_Protect(magicProtect);
			if(balance!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Balance(balance);
			if(critical!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Critical(critical);
			if(hp!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxHP(hp);
			if(mp!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxMP(mp);
			if(str!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(str);
			if(dex!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(dex);
			if(INT!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(INT);
			if(will!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(will);
			if(luk!=0)
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(luk);

			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			
			if(skillPoint>=0&&statPoint>=0&&def>=0&&protect>=0&&magicDef>=0&&magicProtect>=0&&balance>=0&&critical>=0&&hp>0
			&&mp>=0&&str>=0&&dex>=0&&INT>=0&&will>=0&&luk>0)
			{
				SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 0.8F, 0.5F);
				player.sendMessage("§a§l[      능력치가 상승 하였습니다!      ]");
			}
			else if(skillPoint<0&&statPoint<0&&def<0&&protect<0&&magicDef<0&&magicProtect<0&&balance<0&&critical<0&&hp<0
					&&mp<0&&str<0&&dex<0&&INT<0&&will<0&&luk<0)
			{
				SoundEffect.SP(player, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.8F, 0.5F);
				player.sendMessage("§c§l[      능력치가 감소 하였습니다!      ]");
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.5F);
				player.sendMessage("§e§l[      능력치에 변화가 생겼습니다!      ]");
			}
		}
		else if(type.equals("스킬북"))
		{
		  	YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
			{
				String skillName = null;
				for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
				{
					String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
					if(nowlore.contains("[우"))
					{
						if(nowlore.contains("클릭시"))
							if(nowlore.contains("아래"))
								if(nowlore.contains("스킬"))
									if(nowlore.contains("획득]"))
									{
										nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter+1));
										skillName = nowlore.replace(" + ", "");
										break;
									}
					}
				}
				if(skillName == null)
					return;
			  	YamlLoader skillYaml = new YamlLoader();
				skillYaml.getConfig("Skill/SkillList.yml");
				if(skillYaml.contains(skillName))
				{
					skillYaml.getConfig("Skill/JobList.yml");
					if(skillYaml.contains("Mabinogi.Added."+skillName))
					{
					  	YamlLoader playerSkillYaml = new YamlLoader();
						playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
						if(!playerSkillYaml.contains("Mabinogi."+skillYaml.getString("Mabinogi.Added."+skillName)+"."+skillName))
						{
							playerSkillYaml.set("Mabinogi."+skillYaml.getString("Mabinogi.Added."+skillName)+"."+skillName, 1);
							playerSkillYaml.saveConfig();
							if(item.getAmount() != 1)
							{
								item.setAmount(item.getAmount()-1);
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
							}
							else
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
							SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
							player.sendMessage("§d§l[새로운 스킬을 획득 하였습니다!] §e§l"+ChatColor.UNDERLINE+skillName);
							return;
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage("§c§l[         당신은 이미 해당 스킬을 알고 있습니다!         ]");
							return;
						}
					}
					else
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c§l[해당 스킬은 어느 카테고리에도 존재하지 않습니다! 관리자에게 문의하세요!]");
						return;
					}
				}
				else
				{
					SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c§l[서버에 해당 스킬이 존재하지 않습니다! 관리자에게 문의하세요!]");
					return;
				}
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c§l[   서버 시스템이 '마비노기'일 경우만 사용 가능합니다!   ]");
				return;
			}
		}
		else if(type.equals("소비"))
		{
			int health = 0;
			int mana = 0;
			int food = 0;
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("생명력"))
						health = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("마나"))
						mana = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("포만감"))
						food = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			
			if(health > 0)
			{
				SoundEffect.SL(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 2.0F, 0.8F);
				Damageable damageable = player;
				if(damageable.getMaxHealth() < damageable.getHealth()+health)
					damageable.setHealth(damageable.getMaxHealth());
				else
					damageable.setHealth(damageable.getHealth() + health);
			}
			if(mana >0)
			{
				if(main.MainServerOption.MagicSpellsCatched)
				{
					otherplugins.SpellMain spellMain = new otherplugins.SpellMain();
					spellMain.DrinkManaPotion(player, mana);
					SoundEffect.SL(player.getLocation(), Sound.BLOCK_WATER_AMBIENT, 2.0F, 1.9F);
				}
			}
			if(food > 0)
			{
				SoundEffect.SL(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 2.0F, 1.2F);
				if(player.getFoodLevel()+food > 20)
					player.setFoodLevel(20);
				player.setFoodLevel(player.getFoodLevel()+food);
			}
		}
		else if(type.equals("돈"))
		{
			int money=Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(1).split(" ")[0]));
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + money <= 2000000000)
			{
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(money, 0, false);
				if(item.getAmount() != 1)
				{
					item.setAmount(item.getAmount()-1);
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
				}
				else
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
				SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
				player.sendMessage("§a[SYSTEM] : §f§l"+money+" "+MainServerOption.money+"§a 입금 완료!");
				player.sendMessage("§7(현재 "+main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+ChatColor.stripColor(MainServerOption.money)+" 보유중)");
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c[System] : "+MainServerOption.money+"§c 을(를) 2000000000(20억)이상 가질 수 없습니다!");
			}
		}
		return;
	}
}
