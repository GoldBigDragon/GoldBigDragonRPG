package customitem;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import effect.SoundEffect;
import main.Main_ServerOption;
import servertick.ServerTick_Main;
import util.YamlLoader;



public class UseableItem_Main
{
	public void UseAbleItemUse(Player player, String type)
	{
		ItemStack item = player.getInventory().getItemInMainHand();
		if(type.equals("귀환서"))
		{
			if(ServerTick_Main.PlayerTaskList.containsKey(player.getName())==true)
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				new effect.SendPacket().sendActionBar(player, "§c§l[현재 텔레포트를 할 수 없는 상태입니다!]");
				return;
			}
			util.ETC ETC = new util.ETC();
			if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime() >= ETC.getSec())
			{
				player.sendMessage("§c[이동 불가] : §e"+((main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_AttackTime()+15000 - ETC.getSec())/1000)+"§c 초 후에 이동 가능합니다!");
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				return;
			}
			String world = "";
			int X = 0;
			short Y = 0;
			int Z = 0;
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("월드"))
						world = nowlore.split(" : ")[1];
					else if(nowlore.contains("X 좌표"))
						X = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Y 좌표"))
						Y = Short.parseShort(nowlore.split(" : ")[1]);
					else if(nowlore.contains("Z 좌표"))
						Z = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			long UTC= servertick.ServerTick_Main.nowUTC-1;
			servertick.ServerTick_Object STSO = new servertick.ServerTick_Object(UTC, "P_UTS");
			Location loc = player.getLocation();
			STSO.setTick(UTC);//텔레포트 시작 시간
			STSO.setCount(5);//텔레포트 시간
			STSO.setString((byte)0, world+","+X+","+Y+","+Z);//이동 위치 저장
			STSO.setString((byte)1, loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());//현재 위치 저장
			STSO.setString((byte)2, player.getName());//플레이어 이름 저장
			servertick.ServerTick_Main.Schedule.put(UTC, STSO);
			ServerTick_Main.PlayerTaskList.put(player.getName(), ""+UTC);
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
				if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==false)
				{
					if(item.getAmount() != 1)
					{
						item.setAmount(item.getAmount()-1);
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
					}
					else
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
					int TotalStatPoint = configYaml.getInt("DefaultStat.StatPoint")+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint();
					TotalStatPoint += main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() - configYaml.getInt("DefaultStat.STR");
					TotalStatPoint += main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() - configYaml.getInt("DefaultStat.DEX");
					TotalStatPoint += main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() - configYaml.getInt("DefaultStat.INT");
					TotalStatPoint += main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() - configYaml.getInt("DefaultStat.WILL");
					TotalStatPoint += main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() - configYaml.getInt("DefaultStat.LUK");
					main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_STR(configYaml.getInt("DefaultStat.STR"));
					main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_DEX(configYaml.getInt("DefaultStat.DEX"));
					main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_INT(configYaml.getInt("DefaultStat.INT"));
					main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_WILL(configYaml.getInt("DefaultStat.WILL"));
					main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_LUK(configYaml.getInt("DefaultStat.LUK"));
					main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_StatPoint(TotalStatPoint);
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
			
			int StatPoint = 0;
			int SkillPoint = 0;
			int DEF = 0;
			int Protect = 0;
			int MaDEF = 0;
			int MaProtect  = 0;
			int Balance = 0;
			int Critical  = 0;
			int HP  = 0;
			int MP  = 0;
			int STR  = 0;
			int DEX  = 0;
			int INT  = 0;
			int WILL  = 0;
			int LUK  = 0;
			
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("포인트"))
					{
						if(nowlore.contains("스텟"))
							StatPoint = Integer.parseInt(nowlore.split(" : ")[1]);
						if(nowlore.contains("스킬"))
							SkillPoint = Integer.parseInt(nowlore.split(" : ")[1]);
					}
					if(nowlore.contains("방어"))
						if(nowlore.contains("마법"))
							MaDEF = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							DEF = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("보호"))
						if(nowlore.contains("마법"))
							MaProtect = Integer.parseInt(nowlore.split(" : ")[1]);
						else
							Protect = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("밸런스"))
						Balance = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("크리티컬"))
						Critical = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("생명력"))
						HP = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains("마나"))
						MP = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(Main_ServerOption.statSTR))
						STR = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(Main_ServerOption.statDEX))
						DEX = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(Main_ServerOption.statINT))
						INT = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(Main_ServerOption.statWILL))
						WILL = Integer.parseInt(nowlore.split(" : ")[1]);
					if(nowlore.contains(Main_ServerOption.statLUK))
						LUK = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(SkillPoint!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_SkillPoint(SkillPoint);
			if(StatPoint!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(StatPoint);
			if(DEF!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEF(DEF);
			if(Protect!=0)
			main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Protect(Protect);
			if(MaDEF!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_DEF(MaDEF);
			if(MaProtect!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_Protect(MaProtect);
			if(Balance!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Balance(Balance);
			if(Critical!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Critical(Critical);
			if(HP!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxHP(HP);
			if(MP!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxMP(MP);
			if(STR!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(STR);
			if(DEX!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(DEX);
			if(INT!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(INT);
			if(WILL!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(WILL);
			if(LUK!=0)
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(LUK);

			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			
			if(SkillPoint>=0&&StatPoint>=0&&DEF>=0&&Protect>=0&&MaDEF>=0&&MaProtect>=0&&Balance>=0&&Critical>=0&&HP>0
			&&MP>=0&&STR>=0&&DEX>=0&&INT>=0&&WILL>=0&&LUK>0)
			{
				SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 0.8F, 0.5F);
				player.sendMessage("§a§l[      능력치가 상승 하였습니다!      ]");
			}
			else if(SkillPoint<0&&StatPoint<0&&DEF<0&&Protect<0&&MaDEF<0&&MaProtect<0&&Balance<0&&Critical<0&&HP<0
					&&MP<0&&STR<0&&DEX<0&&INT<0&&WILL<0&&LUK<0)
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
			if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
			{
				String Skillname = null;
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
										Skillname = nowlore.replace(" + ", "");
										break;
									}
					}
				}
				if(Skillname == null)
					return;
			  	YamlLoader skillYaml = new YamlLoader();
				skillYaml.getConfig("Skill/SkillList.yml");
				if(skillYaml.contains(Skillname))
				{
					skillYaml.getConfig("Skill/JobList.yml");
					if(skillYaml.contains("Mabinogi.Added."+Skillname)==true)
					{
					  	YamlLoader playerSkillYaml = new YamlLoader();
						playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
						if(playerSkillYaml.contains("Mabinogi."+skillYaml.getString("Mabinogi.Added."+Skillname)+"."+Skillname) == false)
						{
							playerSkillYaml.set("Mabinogi."+skillYaml.getString("Mabinogi.Added."+Skillname)+"."+Skillname, 1);
							playerSkillYaml.saveConfig();
							if(item.getAmount() != 1)
							{
								item.setAmount(item.getAmount()-1);
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
							}
							else
								player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
							SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
							player.sendMessage("§d§l[새로운 스킬을 획득 하였습니다!] §e§l"+ChatColor.UNDERLINE+Skillname);
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
			int Health = 0;
			int Mana = 0;
			int Food = 0;
			for(int counter = 0; counter < item.getItemMeta().getLore().size();counter++)
			{
				String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(counter));
				if(nowlore.contains(" : "))
				{
					if(nowlore.contains("생명력"))
						Health = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("마나"))
						Mana = Integer.parseInt(nowlore.split(" : ")[1]);
					else if(nowlore.contains("포만감"))
						Food = Integer.parseInt(nowlore.split(" : ")[1]);
				}
			}
			if(item.getAmount() != 1)
			{
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
			}
			else
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
			
			if(Health > 0)
			{
				SoundEffect.SL(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 2.0F, 0.8F);
				Damageable Dp = player;
				if(Dp.getMaxHealth() < Dp.getHealth()+Health)
					Dp.setHealth(Dp.getMaxHealth());
				else
					Dp.setHealth(Dp.getHealth() + Health);
			}
			if(Mana >0)
			{
				if(main.Main_ServerOption.MagicSpellsCatched == true)
				{
					otherplugins.SpellMain MG = new otherplugins.SpellMain();
					MG.DrinkManaPotion(player, Mana);
					SoundEffect.SL(player.getLocation(), Sound.BLOCK_WATER_AMBIENT, 2.0F, 1.9F);
				}
			}
			if(Food > 0)
			{
				SoundEffect.SL(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 2.0F, 1.2F);
				if(player.getFoodLevel()+Food > 20)
					player.setFoodLevel(20);
				player.setFoodLevel(player.getFoodLevel()+Food);
			}
		}
		else if(type.equals("돈"))
		{
			int money=Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(1).split(" ")[0]));
			if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money() + money <= 2000000000)
			{
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(money, 0, false);
				if(item.getAmount() != 1)
				{
					item.setAmount(item.getAmount()-1);
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
				}
				else
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(0));
				SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
				player.sendMessage("§a[SYSTEM] : §f§l"+money+" "+Main_ServerOption.money+"§a 입금 완료!");
				player.sendMessage("§7(현재 "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()+ChatColor.stripColor(Main_ServerOption.money)+" 보유중)");
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c[System] : "+Main_ServerOption.money+"§c 을(를) 2000000000(20억)이상 가질 수 없습니다!");
			}
		}
		return;
	}
}
