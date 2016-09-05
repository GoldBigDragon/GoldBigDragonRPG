package GoldBigDragon_RPG.Attack;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Util.Number;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Damage
{
	//플레이어의 근접최소 공격력을 따 오는 메소드//
	public int CombatMinDamageGet(Entity entity, int DefaultDamage, int STR)
	{
		if(entity != null)
		if(entity.getType() == EntityType.PLAYER)
		{
			Player player = (Player) entity;
			if(player.getItemInHand().hasItemMeta() == true)
			{
				if(player.getItemInHand().getItemMeta().hasLore() == true)
				if(player.getItemInHand().getItemMeta().getLore().toString().contains(GoldBigDragon_RPG.Main.ServerOption.Damage+" : ") == true)
				{
					switch(player.getItemInHand().getType())
					{
					case WOOD_SPADE :
					case GOLD_SPADE :
						DefaultDamage = DefaultDamage -1;
						break;
					case WOOD_PICKAXE :
					case GOLD_PICKAXE:
					case STONE_SPADE:
						DefaultDamage = DefaultDamage -2;
						break;
					case WOOD_AXE:
					case GOLD_AXE:
					case STONE_PICKAXE:
					case IRON_SPADE:
						DefaultDamage = DefaultDamage -3;
						break;
					case WOOD_SWORD:
					case GOLD_SWORD:
					case STONE_AXE:
					case IRON_PICKAXE:
					case DIAMOND_SPADE:
						DefaultDamage = DefaultDamage -4;
						break;
					case STONE_SWORD:
					case IRON_AXE:
					case DIAMOND_PICKAXE:
						DefaultDamage = DefaultDamage -5;
						break;
					case IRON_SWORD:
					case DIAMOND_AXE:
						DefaultDamage = DefaultDamage -6;
						break;
					case DIAMOND_SWORD:
						DefaultDamage = DefaultDamage -7;
						break;
					}
				}
			}
			STR = STR + getPlayerEquipmentStat((Player)entity, "STR", null, false)[0];
			DefaultDamage = DefaultDamage + getPlayerEquipmentStat((Player)entity, "Damage", null, false)[0];
		}
		return returnCombatValue(STR, DefaultDamage, true);
	}

	//플레이어의 근접최대 공격력을 따 오는 메소드//
	public int CombatMaxDamageGet(Entity entity, int DefaultDamage, int STR)
	{
		if(entity != null)
		if(entity.getType() == EntityType.PLAYER)
		{
			Player player = (Player) entity;
			if(player.getItemInHand().hasItemMeta() == true)
			{
				if(player.getItemInHand().getItemMeta().hasLore() == true)
				if(player.getItemInHand().getItemMeta().getLore().toString().contains(GoldBigDragon_RPG.Main.ServerOption.Damage+" : ") == true)
				{
					switch(player.getItemInHand().getType())
					{
					case WOOD_SPADE :
					case GOLD_SPADE :
						DefaultDamage = DefaultDamage -1;
						break;
					case WOOD_PICKAXE :
					case GOLD_PICKAXE:
					case STONE_SPADE:
						DefaultDamage = DefaultDamage -2;
						break;
					case WOOD_AXE:
					case GOLD_AXE:
					case STONE_PICKAXE:
					case IRON_SPADE:
						DefaultDamage = DefaultDamage -3;
						break;
					case WOOD_SWORD:
					case GOLD_SWORD:
					case STONE_AXE:
					case IRON_PICKAXE:
					case DIAMOND_SPADE:
						DefaultDamage = DefaultDamage -4;
						break;
					case STONE_SWORD:
					case IRON_AXE:
					case DIAMOND_PICKAXE:
						DefaultDamage = DefaultDamage -5;
						break;
					case IRON_SWORD:
					case DIAMOND_AXE:
						DefaultDamage = DefaultDamage -6;
						break;
					case DIAMOND_SWORD:
						DefaultDamage = DefaultDamage -7;
						break;
					}
				}
			}
			STR =  STR + getPlayerEquipmentStat((Player)entity, "STR", null, false)[0];
			DefaultDamage = DefaultDamage + getPlayerEquipmentStat((Player)entity, "Damage", null, false)[1];
		}
		return returnCombatValue(STR, DefaultDamage, false);
	}
	
	//근접 공격력을 따 오는 메소드//
	public int returnCombatValue(int Stat, int DefaultDamage, boolean isMin)
	{
		int dam=0;
		if(isMin)
			dam = ((Stat/5) + DefaultDamage);
		else
			dam=((Stat/3) + DefaultDamage);
		if(dam <= 0)
			return 0;
		else
			return dam;
	}

	//폭발 공격력을 따 오는 메소드//
	public int returnExplosionDamageValue(int Stat, int DefaultDamage, boolean isMin)
	{
		int dam=0;
		if(isMin)
			dam = (Stat/4)+DefaultDamage;
		else
			dam = (int) ((Stat/2.5)+DefaultDamage);
		if(dam <= 0)
			return 1;
		else
			return dam;
	}
	
	//원거리 공격력을 따 오는 메소드//
	public int returnRangeDamageValue(Entity entity, int Stat, int DefaultDamage, boolean isMin)
	{
		if(entity != null)
			if(entity.getType() == EntityType.PLAYER)
			{
				Stat = Stat + getPlayerEquipmentStat((Player)entity, "DEX", null, false)[0];
				if(isMin)
					DefaultDamage = DefaultDamage + getPlayerEquipmentStat((Player)entity, "Damage", null, false)[0];
				else
					DefaultDamage = DefaultDamage + getPlayerEquipmentStat((Player)entity, "Damage", null, false)[1];
			}
		int dam=0;
		if(isMin)
			dam = ((Stat/5) + DefaultDamage);
		else
			dam = ((Stat/3)+DefaultDamage);
		if(dam <= 0)
			return 1;
		else
			return dam;
	}
	
	//매직스펠 MP/HP 스텟에 따른 대미지 보너스
	public int MagicSpellsDamageBonus(int Stat)
	{
		int dam=Stat/25;
		if(dam <= 0)
			return 0;
		else
			return dam;
	}
	
    //플레이어의 밸런스를 구하고, 랜덤하게 데미지를 설정 해 주는 메소드//
	public int damagerand(Entity entity, int min, int max, int player_balance)
	{
		Number num = new Number();
		if(min > max)
		{
			int temp = max;
			max = min;
			min = temp;
		}
		if (num.RandomNum(1, 100) <= player_balance)
			return num.RandomNum(num.RandomNum(min, max), max);
		else
		{
			max = (int) (max/2);
			if(max <= min)
				max=min;
			return num.RandomNum(min, max);
		}
	}

	//플레이어의 크리티컬 확률을 계산하고, 크리티컬 여부를 설정하는 메소드//
	public int criticalrend(Entity entity, int attacker_luk, int attacker_will, int attacker_damage, int defenser_protect, int attacker_critical)
	{
		Number num = new Number();
		int critical;
		if((int)defenser_protect/2 <= 1) 
			critical= getCritical(entity, attacker_luk, attacker_will,attacker_critical);
		else
			critical= (int)(getCritical(entity, attacker_luk, attacker_will,attacker_critical)/100)*(100-(defenser_protect/2));
		if (critical > 90)
			critical = 90;
		if (critical < 2)
			critical = 2;
		int getcritical = (int) num.RandomNum(0, 100);
		if (getcritical <= critical)
			return (int)(attacker_damage/2);
		else
			return 0;
	}

	//밸런스 계산기//
	public int getBalance(Entity entity, int DEX, int player_balance)
	{
		int balance = player_balance;
		if(entity!=null)
		if(entity.getType() == EntityType.PLAYER)
		{
			DEX = DEX + getPlayerEquipmentStat((Player)entity, "DEX", null, false)[0];
			balance = balance + getPlayerEquipmentStat((Player)entity, "Balance", null, false)[0];
		}
		balance = balance + (int)DEX/20;
		if (balance > 80) balance = 80;
		if (balance < 0) balance = 1;
		return balance;
	}
	
	//크리티컬율 계산기//
	public int getCritical(Entity entity, int player_luk, int player_will, int defaultCritical)
	{
		int critical = defaultCritical;
		if(entity!=null)
		if(entity.getType() == EntityType.PLAYER)
			critical = critical + getPlayerEquipmentStat((Player)entity, "Critical", null, false)[0];
		critical = critical + (int)(player_luk/5 + player_will/10);
		return critical;
	}
	
	//마방 계산기//
	public int getMagicDEF(Entity entity, int player_int)
	{
		int Magic_DEF = 0;
		if(entity.getType() == EntityType.PLAYER)
		{
			player_int = player_int + getPlayerEquipmentStat((Player)entity, "INT", null, false)[0];
			Magic_DEF = Magic_DEF + getPlayerEquipmentStat((Player)entity, "Magic_DEF", null, false)[0];
		}
		Magic_DEF = Magic_DEF + (int)(player_int/20);
		return Magic_DEF;
	}

	//마보 계산기//
	public int getMagicProtect(Entity entity, int player_int)
	{
		int Magic_Protect = 0;
		if(entity.getType() == EntityType.PLAYER)
		{
			player_int = player_int + getPlayerEquipmentStat((Player)entity, "INT", null, false)[0];
			Magic_Protect = Magic_Protect + getPlayerEquipmentStat((Player)entity, "Magic_Protect", null, false)[0];
		}
		Magic_Protect = Magic_Protect + (int)(player_int/100);
		return Magic_Protect;
	}

	//방어 관통 계산기//
	public int getDEFcrash(Entity entity, int player_dex)
	{
		int DEFcrash = 0;
		if(entity.getType() == EntityType.PLAYER)
		{
			player_dex = player_dex + getPlayerEquipmentStat((Player)entity, "DEX", null, false)[0];
			DEFcrash = DEFcrash + getPlayerEquipmentStat((Player)entity, "DEFcrash", null, false)[0];
		}
		DEFcrash = DEFcrash + (int)(player_dex/40);
		return DEFcrash;
	}

	//마방 관통 계산기//
	public int getMagicDEFcrash(Entity entity, int player_int)
	{
		int MagicDEFcrash = 0;
		if(entity.getType() == EntityType.PLAYER)
		{
			player_int = player_int + getPlayerEquipmentStat((Player)entity, "INT", null, false)[0];
			MagicDEFcrash = MagicDEFcrash + getPlayerEquipmentStat((Player)entity, "MagicDEFcrash", null, false)[0];
		}
		MagicDEFcrash = MagicDEFcrash + (int)(player_int/40);
		return MagicDEFcrash;
	}

	public int[] getPlayerEquipmentStat(Player player, String type ,ItemStack newSlot, boolean isHotBarChange)
	{
		int bonus[] = new int[2];
		String Lore[];
		switch(type)
		{
			case "Damage":type = GoldBigDragon_RPG.Main.ServerOption.Damage;break;
			case "DEF":type = "방어";break;
			case "DEFcrash":type = "방어관통";break;
			case "Protect":type = "보호";break;
			case "MagicDamage":type = GoldBigDragon_RPG.Main.ServerOption.MagicDamage;break;
			case "Magic_DEF":type = "마법 방어";break;
			case "MagicDEFcrash":type = "마법 방어관통";break;
			case "Magic_Protect":type = "마법 보호";break;
			case "STR":type = GoldBigDragon_RPG.Main.ServerOption.STR;break;
			case "DEX":type = GoldBigDragon_RPG.Main.ServerOption.DEX;break;
			case "INT":type = GoldBigDragon_RPG.Main.ServerOption.INT;break;
			case "WILL":type = GoldBigDragon_RPG.Main.ServerOption.WILL;break;
			case "LUK":type = GoldBigDragon_RPG.Main.ServerOption.LUK;break;
			case "HP":type = "생명력";break;
			case "MP":type = "마나";break;
			case "Critical":type = "크리티컬";break;
			case "Balance":type = "밸런스";break;
			case "Upgrade":type = "업그레이드";break;
			default : break;
		}
		ItemStack item[] = player.getInventory().getArmorContents();
		boolean Totaluseable = true;
		for(byte counter = 0; counter < item.length; counter++)
		{
			boolean ExitDurability = true;
			if(item[counter] != null)
			{
				if(item[counter].hasItemMeta() == true)
				{
					if(item[counter].getItemMeta().hasLore() == true)
					{
						if(item[counter].getItemMeta().getLore().toString().contains(type) == true)
						{
							if(!(item[counter].getItemMeta().getLore().toString().contains("[주문서]")||item[counter].getItemMeta().getLore().toString().contains("[룬]")||item[counter].getItemMeta().getLore().toString().contains("[소비]")))
							{
								boolean useable = true;
								for(byte count = 0; count < item[counter].getItemMeta().getLore().size(); count++)
								{
									String nowlore=ChatColor.stripColor(item[counter].getItemMeta().getLore().get(count));
									if(nowlore.contains(" : "))
									{
										if(nowlore.contains("직업") == true)
											if(nowlore.split(" : ")[1].compareTo(ServerOption.PlayerList.get(player.getUniqueId().toString()).getPlayerRootJob())!=0)
												useable = false;
										if(nowlore.contains("최소") == true)
										{
											String[] Resist = nowlore.split(" ");
											if(Resist[Resist.length-3].compareTo("레벨")==0)
												useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
											else if(Resist[Resist.length-3].compareTo("누적레벨")==0)
												useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
											else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.STR)==0)
												useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
											else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.DEX)==0)
												useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
											else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.INT)==0)
												useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
											else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.WILL)==0)
												useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
											else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.LUK)==0)
												useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
										}
										if(useable==false)
										{
											Totaluseable = false;
											break;
										}
										if(nowlore.contains("내구도") == true)
										{
											String[] Lore2 = nowlore.split(" : ");
											String[] SubLore = Lore2[1].split(" / ");
											if(Integer.parseInt(SubLore[0]) <= 0)
											{
												ExitDurability = false;
												break;
											}
										}
									}
								}
								if(useable)
								{
									for(byte count = 0; count < item[counter].getItemMeta().getLore().size(); count++)
									{
										if(item[counter].getItemMeta().getLore().get(count).contains(type) == true)
										{
											if(item[counter].getItemMeta().getLore().get(count).contains(" : ")||item[counter].getItemMeta().getLore().get(count).contains("/"))
											{
												if(ExitDurability == true)
												{
													Lore = ChatColor.stripColor(item[counter].getItemMeta().getLore().get(count)).split(" : ");
													if(Lore[0].contains(type))
													{
														if(type.compareTo(GoldBigDragon_RPG.Main.ServerOption.STR)==0||type.compareTo(GoldBigDragon_RPG.Main.ServerOption.DEX)==0||
															type.compareTo(GoldBigDragon_RPG.Main.ServerOption.INT)==0||type.compareTo(GoldBigDragon_RPG.Main.ServerOption.WILL)==0||
															type.compareTo(GoldBigDragon_RPG.Main.ServerOption.LUK)==0)
														{
															if(item[counter].getItemMeta().getLore().get(count).contains("최소") == false)
																bonus[0] = bonus[0] + Integer.parseInt(Lore[1]);
														}
														else if(type.compareTo(GoldBigDragon_RPG.Main.ServerOption.Damage)==0||type.compareTo(GoldBigDragon_RPG.Main.ServerOption.MagicDamage)==0||type.compareTo("업그레이드")==0)
														{
															if(type.compareTo(GoldBigDragon_RPG.Main.ServerOption.Damage)==0)
															{
																String[] SubLore = Lore[1].split(" ~ ");
																bonus[0] = bonus[0] + Integer.parseInt(SubLore[0]);
																bonus[1] = bonus[1] + Integer.parseInt(SubLore[1]);
															}
															else if(type.compareTo(GoldBigDragon_RPG.Main.ServerOption.MagicDamage)==0||type.compareTo("업그레이드")==0)
															{
																String[] SubLore = Lore[1].split(" ~ ");
																bonus[0] = bonus[0] + Integer.parseInt(SubLore[0]);
																bonus[1] = bonus[1] + Integer.parseInt(SubLore[1]);
															}
														}
														else
															bonus[0] = bonus[0] + Integer.parseInt(Lore[1]);
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
			}
		}
		if(newSlot == null)
		{
			if(isHotBarChange)
				item[0] = null;
			else
				item[0] = player.getItemInHand();
		}
		else
			item[0] = newSlot;
		boolean ExitDurability = true;
		if(item[0] !=null &&item[0].getType()!=Material.AIR)
		{
			if(item[0].hasItemMeta() == true)
			{
				if(item[0].getItemMeta().hasLore() == true)
				{
					if(item[0].getItemMeta().getLore().toString().contains(type) == true)
					{
						if(!(item[0].getItemMeta().getLore().toString().contains("[주문서]")||item[0].getItemMeta().getLore().toString().contains("[룬]")||item[0].getItemMeta().getLore().toString().contains("[소비]")))
						{
							boolean useable = true;
							for(byte count = 0; count < item[0].getItemMeta().getLore().size(); count++)
							{
								String nowlore=ChatColor.stripColor(item[0].getItemMeta().getLore().get(count));
								if(nowlore.contains(" : "))
								{
									if(nowlore.contains("직업") == true)
										if(nowlore.split(" : ")[1].compareTo(ServerOption.PlayerList.get(player.getUniqueId().toString()).getPlayerRootJob())!=0)
											useable = false;
									if(nowlore.contains("최소") == true)
									{
										String[] Resist = nowlore.split(" ");
										if(Resist[Resist.length-3].compareTo("레벨")==0)
											useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
										else if(Resist[Resist.length-3].compareTo("누적레벨")==0)
											useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
										else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.STR)==0)
											useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
										else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.DEX)==0)
											useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
										else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.INT)==0)
											useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
										else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.WILL)==0)
											useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
										else if(Resist[Resist.length-3].compareTo(GoldBigDragon_RPG.Main.ServerOption.LUK)==0)
											useable = Integer.parseInt(Resist[Resist.length-1]) <= ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
									}
									if(useable==false)
									{
										Totaluseable = false;
										break;
									}
									if(nowlore.contains("내구도") == true)
									{
										String[] Lore2 = nowlore.split(" : ");
										String[] SubLore = Lore2[1].split(" / ");
										if(Integer.parseInt(SubLore[0]) <= 0)
										{
											ExitDurability = false;
											break;
										}
									}
								}
							}
							if(useable)
							{
								for(byte count = 0; count < item[0].getItemMeta().getLore().size(); count++)
								{
									if(item[0].getItemMeta().getLore().get(count).contains(type) == true)
									{
										if(item[0].getItemMeta().getLore().get(count).contains(" : ")||item[0].getItemMeta().getLore().get(count).contains("/"))
										{
											if(ExitDurability == true)
											{
												Lore = ChatColor.stripColor(item[0].getItemMeta().getLore().get(count)).split(" : ");
												if(Lore[0].contains(type))
												{
													if(type.compareTo(GoldBigDragon_RPG.Main.ServerOption.STR)==0||type.compareTo(GoldBigDragon_RPG.Main.ServerOption.DEX)==0||
														type.compareTo(GoldBigDragon_RPG.Main.ServerOption.INT)==0||type.compareTo(GoldBigDragon_RPG.Main.ServerOption.WILL)==0||
														type.compareTo(GoldBigDragon_RPG.Main.ServerOption.LUK)==0)
													{
														if(item[0].getItemMeta().getLore().get(count).contains("최소") == false)
															bonus[0] = bonus[0] + Integer.parseInt(Lore[1]);
													}
													else if(type.compareTo(GoldBigDragon_RPG.Main.ServerOption.Damage)==0||type.compareTo(GoldBigDragon_RPG.Main.ServerOption.MagicDamage)==0||type.compareTo("업그레이드")==0)
													{
														if(type.compareTo(GoldBigDragon_RPG.Main.ServerOption.Damage)==0)
														{
															String[] SubLore = Lore[1].split(" ~ ");
															bonus[0] = bonus[0] + Integer.parseInt(SubLore[0]);
															bonus[1] = bonus[1] + Integer.parseInt(SubLore[1]);
														}
														else if(type.compareTo(GoldBigDragon_RPG.Main.ServerOption.MagicDamage)==0||type.compareTo("업그레이드")==0)
														{
															String[] SubLore = Lore[1].split(" ~ ");
															bonus[0] = bonus[0] + Integer.parseInt(SubLore[0]);
															bonus[1] = bonus[1] + Integer.parseInt(SubLore[1]);
														}
													}
													else
														bonus[0] = bonus[0] + Integer.parseInt(Lore[1]);
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
		}
		if(Totaluseable==false)
			new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(player,"\'§e\'", "\'§c(장비가 제 성능을 하지 못하고 있다!)\'", (byte)1,(byte)1, (byte)1);
		return bonus;
	}

	public int getPlayerEquipmentDurability(Player player, String part)
	{
		int durability = -1;
		String Lore[];
		ItemStack item = null;
		switch(part)
		{
			case "머리":item = player.getInventory().getHelmet();break;
			case "가슴":item = player.getInventory().getChestplate();break;
			case "다리":item = player.getInventory().getLeggings();break;
			case "부츠":item = player.getInventory().getBoots();break;
			case "손":item = player.getItemInHand();break;
			default : break;
		}
		if(item.hasItemMeta() == true)
		{
			if(item.getItemMeta().hasLore() == true)
			{
				if(!(item.getItemMeta().getLore().toString().contains("[주문서]")||item.getItemMeta().getLore().toString().contains("[룬]")||item.getItemMeta().getLore().toString().contains("[소비]")))
				{
					for(byte count = 0; count < item.getItemMeta().getLore().size(); count++)
					{
						String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(count));
						if(nowlore.contains(" : ")&&nowlore.contains(" / "))
						{
							if(item.getItemMeta().getLore().get(count).contains("내구도") == true)
							{
								item.setDurability((short) (item.getDurability()-1));
								Lore = nowlore.split(" : ");
								durability = Integer.parseInt(Lore[0].split(" / ")[0]);
							}
						}
					}
				}
			}
		}
		return durability;
	}

	public void decreaseDurabilityArmor(Player player)
	{
		boolean DurabilityExit = false;
		ItemStack item[] = player.getInventory().getArmorContents();
		for(byte counter = 0; counter < item.length; counter++)
		{
			if(item[counter].hasItemMeta() == true)
			{
				if(item[counter].getItemMeta().hasLore() == true)
				{
					if(item[counter].getItemMeta().getLore().toString().contains("내구도"))
					{
						if(!(item[counter].getItemMeta().getLore().toString().contains("[주문서]")||item[counter].getItemMeta().getLore().toString().contains("[룬]")||item[counter].getItemMeta().getLore().toString().contains("[소비]")))
						{
							for(byte count = 0; count < item[counter].getItemMeta().getLore().size(); count++)
							{
								String nowlore=ChatColor.stripColor(item[counter].getItemMeta().getLore().get(count));
								if(nowlore.contains(" : "))
								{
									ItemMeta Meta = item[counter].getItemMeta();
									if(nowlore.contains(" / "))
									{
										if(Meta.getLore().get(count).contains("내구도") == true)
										{
											String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
											String[] SubLore = Lore[1].split(" / ");
											List<String> PLore = Meta.getLore();
											if((Integer.parseInt(SubLore[0])-1) <= 0)
											{
											  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
												YamlManager Config =YC.getNewConfig("config.yml");
												if(Config.getBoolean("Server.CustomWeaponBreak"))
												{
													new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.ITEM_BREAK, 1.2F, 1.0F);
													if(item[counter].getItemMeta().hasDisplayName())
														player.sendMessage(ChatColor.RED+"[장비 파괴] : "+ ChatColor.YELLOW+item[counter].getItemMeta().getDisplayName()+ChatColor.RED+ " 장비가 파괴되었습니다!");
													else
														player.sendMessage(ChatColor.RED+"[장비 파괴] : 장비가 파괴되었습니다!");
													item[counter] = new ItemStack(0);
													player.getInventory().setArmorContents(item);
													break;
												}
												else
													PLore.set(count,ChatColor.WHITE +  Lore[0] + " : "+ 0 +" / "+SubLore[1]);
											}
											else
											{
												PLore.set(count,ChatColor.WHITE +  Lore[0] + " : "+(Integer.parseInt(SubLore[0])-1) +" / "+SubLore[1]);
												DurabilityExit = true;
											}
											Meta.setLore(PLore);
											item[counter].setItemMeta(Meta);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(DurabilityExit == true)
		{
			for(byte counter = 0; counter < item.length; counter++)
			{
				if(item[counter].hasItemMeta() == true)
				{
					if(item[counter].getItemMeta().hasLore() == true)
					{
						if(item[counter].getItemMeta().getLore().toString().contains("숙련도"))
						{
							for(byte count = 0; count < item[counter].getItemMeta().getLore().size(); count++)
							{
								String nowlore=ChatColor.stripColor(item[counter].getItemMeta().getLore().get(count));
								if(nowlore.contains(" : "))
								{
									ItemMeta Meta = item[counter].getItemMeta();
									if(Meta.getLore().get(count).contains("숙련도") == true)
									{
										float Proficiency = 0.07F * GoldBigDragon_RPG.Main.ServerOption.Event_Proficiency;
										String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
										String[] SubLore = Lore[1].split("%");
										List<String> PLore = Meta.getLore();
										DecimalFormat format = new DecimalFormat(".##");
										String str = format.format((Float.parseFloat(SubLore[0])+Proficiency));
										if(str.charAt(0)=='.')
											str = "0"+str;
										if((Float.parseFloat(SubLore[0])+0.07F) >= 100.0F)
											PLore.set(count,ChatColor.WHITE +  Lore[0] + " : "+ 100.0 +"%"+ChatColor.WHITE);
										else
											PLore.set(count,ChatColor.WHITE +  Lore[0] + " : "+ str +"%"+ChatColor.WHITE);
										Meta.setLore(PLore);
										item[counter].setItemMeta(Meta);
									}
								}
							}
						}
					}
				}
			}
		}
		return;
	}
	
	public void decreaseDurabilityWeapon(Player player)
	{
		boolean DurabilityExit = false;
		ItemStack item = player.getInventory().getItemInHand();
		if(item.hasItemMeta() == true)
		{
			if(item.getItemMeta().hasLore() == true)
			{
				if(item.getItemMeta().getLore().toString().contains("내구도"))
				{
					if(!(item.getItemMeta().getLore().toString().contains("[주문서]")||item.getItemMeta().getLore().toString().contains("[룬]")||item.getItemMeta().getLore().toString().contains("[소비]")))
					{
						for(byte count = 0; count < item.getItemMeta().getLore().size(); count++)
						{
							String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(count));
							if(nowlore.contains(" : ")&&nowlore.contains(" / "))
							{
								ItemMeta Meta = item.getItemMeta();
								if(Meta.getLore().get(count).contains("내구도") == true)
								{
									String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
									String[] SubLore = Lore[1].split(" / ");
									List<String> PLore = Meta.getLore();
									if((Integer.parseInt(SubLore[0])-1) <= 0)
									{
									  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
										YamlManager Config =YC.getNewConfig("config.yml");
										if(Config.getBoolean("Server.CustomWeaponBreak"))
										{
											new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.ITEM_BREAK, 1.2F, 1.0F);
											if(item.getItemMeta().hasDisplayName())
												player.sendMessage(ChatColor.RED+"[장비 파괴] : "+ ChatColor.YELLOW+item.getItemMeta().getDisplayName()+ChatColor.RED+ " 장비가 파괴되었습니다!");
											else
												player.sendMessage(ChatColor.RED+"[장비 파괴] : 장비가 파괴되었습니다!");
											player.setItemInHand(new ItemStack(0));
											break;
										}
										else
											PLore.set(count, ChatColor.WHITE + Lore[0] + " : "+ 0 +" / "+SubLore[1]);
									}
									else
									{
										PLore.set(count, ChatColor.WHITE + Lore[0] + " : "+(Integer.parseInt(SubLore[0])-1) +" / "+SubLore[1]);
										DurabilityExit = true;
									}
									Meta.setLore(PLore);
									item.setItemMeta(Meta);
								}
							}
						}
					}
				}
			}
		}
		if(DurabilityExit == true)
		{
			if(item.hasItemMeta() == true)
			{
				if(item.getItemMeta().hasLore() == true)
				{
					if(item.getItemMeta().getLore().toString().contains("숙련도"))
					{
						for(byte count = 0; count < item.getItemMeta().getLore().size(); count++)
						{
							String nowlore=ChatColor.stripColor(item.getItemMeta().getLore().get(count));
							if(nowlore.contains(" : "))
							{
								ItemMeta Meta = item.getItemMeta();
								if(Meta.getLore().get(count).contains("숙련도") == true)
								{
									float Proficiency = 0.07F * GoldBigDragon_RPG.Main.ServerOption.Event_Proficiency;
									String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
									String[] SubLore = Lore[1].split("%");
									List<String> PLore = Meta.getLore();
									DecimalFormat format = new DecimalFormat(".##");
									String str = format.format((Float.parseFloat(SubLore[0])+Proficiency));
									if(str.charAt(0)=='.')
										str = "0"+str;
									if((Float.parseFloat(SubLore[0])+0.07F) >= 100.0F)
										PLore.set(count,ChatColor.WHITE +  Lore[0] + " : "+ 100.0 +"%"+ChatColor.WHITE);
									else
										PLore.set(count,ChatColor.WHITE +  Lore[0] + " : "+ str +"%"+ChatColor.WHITE);
									Meta.setLore(PLore);
									item.setItemMeta(Meta);
								}
							}
						}
					}
				}
			}
		}
		return;
	}
}
