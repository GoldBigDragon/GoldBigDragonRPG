package battle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import effect.SoundEffect;
import main.Main_ServerOption;
import util.Util_Number;
import util.YamlLoader;



public class Battle_Calculator
{
	//플레이어의 근접최소 공격력을 따 오는 메소드//
	public static int CombatDamageGet(Entity entity, int DefaultDamage, int STR, boolean isMin)
	{
		if(entity != null)
		if(entity.getType() == EntityType.PLAYER)
		{
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null)
			{
				if(item.hasItemMeta())
				{
					if(item.getItemMeta().hasLore())
						if(item.getItemMeta().getLore().toString().contains(main.Main_ServerOption.damage+" : "))
						{
							switch(item.getType())
							{
							case WOOD_SPADE :
							case GOLD_SPADE :
							case WOOD_PICKAXE :
							case GOLD_PICKAXE:
								DefaultDamage -= 2;
								break;
							case STONE_SPADE:
							case STONE_PICKAXE:
								DefaultDamage -= 3;
								break;
							case IRON_SPADE:
							case WOOD_SWORD:
							case GOLD_SWORD:
							case IRON_PICKAXE:
								DefaultDamage -= 4;
								break;
							case DIAMOND_SPADE:
							case STONE_SWORD:
							case DIAMOND_PICKAXE:
								DefaultDamage -= 5;
								break;
							case IRON_SWORD:
								DefaultDamage -= 6;
								break;
							case WOOD_AXE:
							case GOLD_AXE:
							case DIAMOND_AXE:
							case DIAMOND_SWORD:
								DefaultDamage -= 7;
								break;
							case STONE_AXE:
							case IRON_AXE:
								DefaultDamage -= 9;
								break;
							default:
								break;
							}
					}
				}
			}
			STR += getPlayerEquipmentStat((Player)entity, "STR", true, null)[0];
			if(isMin)
				DefaultDamage += getPlayerEquipmentStat((Player)entity, "Damage", true, null)[0];
			else
				DefaultDamage += getPlayerEquipmentStat((Player)entity, "Damage", true, null)[1];
		}
		return returnCombatValue(STR, DefaultDamage, isMin);
	}
	
	//근접 공격력을 따 오는 메소드//
	public static int returnCombatValue(int Stat, int DefaultDamage, boolean isMin)
	{
		if(isMin)
			DefaultDamage += Stat/5;
		else
			DefaultDamage += Stat/3;
		if(DefaultDamage <= 0)
			return 0;
		else
			return DefaultDamage;
	}

	//폭발 공격력을 따 오는 메소드//
	public static int returnExplosionDamageValue(int Stat, int DefaultDamage, boolean isMin)
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
	public static int returnRangeDamageValue(Entity entity, int Stat, int DefaultDamage, boolean isMin)
	{
		if(entity != null)
			if(entity.getType() == EntityType.PLAYER)
			{
				Stat = Stat + getPlayerEquipmentStat((Player)entity, "DEX", false, null)[0];
				if(isMin)
					DefaultDamage = DefaultDamage + getPlayerEquipmentStat((Player)entity, "Damage", false, null)[0];
				else
					DefaultDamage = DefaultDamage + getPlayerEquipmentStat((Player)entity, "Damage", false, null)[1];
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
	public static int MagicSpellsDamageBonus(int Stat)
	{
		int dam=Stat/25;
		if(dam <= 0)
			return 0;
		else
			return dam;
	}
	
    //플레이어의 밸런스를 구하고, 랜덤하게 데미지를 설정 해 주는 메소드//
	public static int damagerand(Entity entity, int min, int max, int player_balance)
	{
		Util_Number num = new Util_Number();
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
	public static int criticalrend(Entity entity, int attacker_luk, int attacker_will, int attacker_damage, int defenser_protect, int attacker_critical)
	{
		Util_Number num = new Util_Number();
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
	public static int getBalance(Entity entity, int DEX, int player_balance)
	{
		int balance = player_balance;
		if(entity!=null)
		if(entity.getType() == EntityType.PLAYER)
		{
			DEX = DEX + getPlayerEquipmentStat((Player)entity, "DEX", false, null)[0];
			balance = balance + getPlayerEquipmentStat((Player)entity, "Balance", false, null)[0];
		}
		balance = balance + (int)DEX/20;
		if (balance > 80) balance = 80;
		if (balance < 0) balance = 1;
		return balance;
	}
	
	//크리티컬율 계산기//
	public static int getCritical(Entity entity, int player_luk, int player_will, int defaultCritical)
	{
		int critical = defaultCritical;
		if(entity!=null)
		if(entity.getType() == EntityType.PLAYER)
			critical = critical + getPlayerEquipmentStat((Player)entity, "Critical", false, null)[0];
		critical = critical + (int)(player_luk/5 + player_will/10);
		return critical;
	}
	
	//마방 계산기//
	public static int getMagicDEF(Entity entity, int player_int)
	{
		int Magic_DEF = 0;
		if(entity.getType() == EntityType.PLAYER)
		{
			player_int = player_int + getPlayerEquipmentStat((Player)entity, "INT", false, null)[0];
			Magic_DEF = Magic_DEF + getPlayerEquipmentStat((Player)entity, "Magic_DEF", false, null)[0];
		}
		Magic_DEF = Magic_DEF + (int)(player_int/20);
		return Magic_DEF;
	}

	//마보 계산기//
	public static int getMagicProtect(Entity entity, int player_int)
	{
		int Magic_Protect = 0;
		if(entity.getType() == EntityType.PLAYER)
		{
			player_int = player_int + getPlayerEquipmentStat((Player)entity, "INT", false, null)[0];
			Magic_Protect = Magic_Protect + getPlayerEquipmentStat((Player)entity, "Magic_Protect", false, null)[0];
		}
		Magic_Protect = Magic_Protect + (int)(player_int/100);
		return Magic_Protect;
	}

	//방어 관통 계산기//
	public static int getDEFcrash(Entity entity, int player_dex)
	{
		int DEFcrash = 0;
		if(entity.getType() == EntityType.PLAYER)
		{
			player_dex = player_dex + getPlayerEquipmentStat((Player)entity, "DEX", false, null)[0];
			DEFcrash = DEFcrash + getPlayerEquipmentStat((Player)entity, "DEFcrash", false, null)[0];
		}
		DEFcrash = DEFcrash + (int)(player_dex/40);
		return DEFcrash;
	}

	//마방 관통 계산기//
	public static int getMagicDEFcrash(Entity entity, int player_int)
	{
		int MagicDEFcrash = 0;
		if(entity.getType() == EntityType.PLAYER)
		{
			player_int = player_int + getPlayerEquipmentStat((Player)entity, "INT", false, null)[0];
			MagicDEFcrash = MagicDEFcrash + getPlayerEquipmentStat((Player)entity, "MagicDEFcrash", false, null)[0];
		}
		MagicDEFcrash = MagicDEFcrash + (int)(player_int/40);
		return MagicDEFcrash;
	}

	public static int[] getPlayerEquipmentStat(Player player, String type, boolean isCombat, ItemStack newSlot)
	{
		int bonus[] = new int[2];
		String Lore[];
		switch(type)
		{
			case "Damage":type = main.Main_ServerOption.damage;break;
			case "DEF":type = "방어";break;
			case "DEFcrash":type = "방어관통";break;
			case "Protect":type = "보호";break;
			case "MagicDamage":type = main.Main_ServerOption.magicDamage;break;
			case "Magic_DEF":type = "마법 방어";break;
			case "MagicDEFcrash":type = "마법 방어관통";break;
			case "Magic_Protect":type = "마법 보호";break;
			case "STR":type = main.Main_ServerOption.statSTR;break;
			case "DEX":type = main.Main_ServerOption.statDEX;break;
			case "INT":type = main.Main_ServerOption.statINT;break;
			case "WILL":type = main.Main_ServerOption.statWILL;break;
			case "LUK":type = main.Main_ServerOption.statLUK;break;
			case "HP":type = "생명력";break;
			case "MP":type = "마나";break;
			case "Critical":type = "크리티컬";break;
			case "Balance":type = "밸런스";break;
			case "Upgrade":type = "업그레이드";break;
			default : break;
		}
		ArrayList<ItemStack> item = new ArrayList<ItemStack>();
		item.add(player.getInventory().getHelmet());
		item.add(player.getInventory().getChestplate());
		item.add(player.getInventory().getLeggings());
		item.add(player.getInventory().getBoots());
		if(newSlot==null)
			item.add(player.getInventory().getItemInMainHand());
		else
		{
			if(newSlot.hasItemMeta()==false)
				newSlot=null;
			else
				item.add(newSlot);
		}
		if(main.Main_ServerOption.dualWeapon)
			item.add(player.getInventory().getItemInOffHand());
		boolean Totaluseable = true;
		for(int counter = 0; counter < item.size(); counter++)
		{
			boolean isCancel = false;
			boolean ExitDurability = true;
			if(item.get(counter) != null)
			{
				if(counter >= 4 && newSlot==null)
				{
					if(counter == 4)
					{
						if(isCombat == false && !(item.get(4).getTypeId() == 261 ||item.get(4).getTypeId() == 262 || item.get(4).getTypeId() == 439|| item.get(4).getTypeId() == 440) && type.equals(main.Main_ServerOption.damage))
							isCancel = true;
					}
					else
					{
						if(isCombat ==false && !(item.get(5).getTypeId() == 261 ||item.get(5).getTypeId() == 262 || item.get(5).getTypeId() == 439|| item.get(5).getTypeId() == 440) && type.equals(main.Main_ServerOption.damage))
							isCancel = true;
						if(isCombat && (item.get(counter).getTypeId()==261) && type.equals(main.Main_ServerOption.damage))
							break;
					}
				}
				if(isCancel==false)
				{
					if(item.get(counter).hasItemMeta() == true)
					{
						if(item.get(counter).getItemMeta().hasLore() == true)
						{
							if(item.get(counter).getItemMeta().getLore().toString().contains(type) == true)
							{
								if(!(item.get(counter).getItemMeta().getLore().toString().contains("[주문서]")||item.get(counter).getItemMeta().getLore().toString().contains("[룬]")||item.get(counter).getItemMeta().getLore().toString().contains("[소비]")))
								{
									boolean useable = true;
									for(int count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
									{
										String nowlore=ChatColor.stripColor(item.get(counter).getItemMeta().getLore().get(count));
										if(nowlore.contains(" : "))
										{
											if(nowlore.contains("직업") == true)
												if(!nowlore.split(" : ")[1].equals(Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getPlayerRootJob()))
													useable = false;
											if(nowlore.contains("최소") == true)
											{
												String[] Resist = nowlore.split(" ");
												if(Resist[Resist.length-3].equals("레벨"))
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
												else if(Resist[Resist.length-3].equals("누적레벨"))
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
												else if(Resist[Resist.length-3].equals(main.Main_ServerOption.statSTR))
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
												else if(Resist[Resist.length-3].equals(main.Main_ServerOption.statDEX))
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
												else if(Resist[Resist.length-3].equals(main.Main_ServerOption.statINT))
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
												else if(Resist[Resist.length-3].equals(main.Main_ServerOption.statWILL))
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
												else if(Resist[Resist.length-3].equals(main.Main_ServerOption.statLUK))
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
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
										for(int count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
										{
											if(item.get(counter).getItemMeta().getLore().get(count).contains(type) == true)
											{
												if(item.get(counter).getItemMeta().getLore().get(count).contains(" : ")||item.get(counter).getItemMeta().getLore().get(count).contains("/"))
												{
													if(ExitDurability == true)
													{
														Lore = ChatColor.stripColor(item.get(counter).getItemMeta().getLore().get(count)).split(" : ");
														if(Lore[0].contains(type))
														{
															if(type.equals(main.Main_ServerOption.statSTR)||type.equals(main.Main_ServerOption.statDEX)||
																type.equals(main.Main_ServerOption.statINT)||type.equals(main.Main_ServerOption.statWILL)||
																type.equals(main.Main_ServerOption.statLUK))
															{
																if(item.get(counter).getItemMeta().getLore().get(count).contains("최소") == false)
																	bonus[0] = bonus[0] + Integer.parseInt(Lore[1]);
															}
															else if(type.equals(main.Main_ServerOption.damage)||type.equals(main.Main_ServerOption.magicDamage)||type.equals("업그레이드"))
															{
																if(type.equals(main.Main_ServerOption.damage))
																{
																	String[] SubLore = Lore[1].split(" ~ ");
																	bonus[0] = bonus[0] + Integer.parseInt(SubLore[0]);
																	bonus[1] = bonus[1] + Integer.parseInt(SubLore[1]);
																}
																else if(type.equals(main.Main_ServerOption.magicDamage)||type.equals("업그레이드"))
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
		}
		if(Totaluseable==false)
			new effect.SendPacket().sendTitleSubTitle(player,"\'§e\'", "\'§c(장비가 제 성능을 하지 못하고 있다!)\'", (byte)1,(byte)1, (byte)1);
		return bonus;
	}
	
	public static void decreaseDurabilityArmor(Player player)
	{
		boolean DurabilityExit = false;
		ArrayList<ItemStack> item = new ArrayList<ItemStack>();
		item.add(player.getInventory().getHelmet());
		item.add(player.getInventory().getChestplate());
		item.add(player.getInventory().getLeggings());
		item.add(player.getInventory().getBoots());
		byte added = 0;
		if(player.getInventory().getItemInMainHand().getTypeId()==442)
		{
			item.add(player.getInventory().getItemInMainHand());
			added = (byte) (added + 1);
		}
		if(main.Main_ServerOption.dualWeapon)
			if(player.getInventory().getItemInOffHand().getTypeId()==442)
			{
				item.add(player.getInventory().getItemInOffHand());
				added = (byte) (added + 2);
			}
		for(int counter = 0; counter < item.size(); counter++)
		{
			if(item.get(counter) != null)
			if(item.get(counter).hasItemMeta() == true)
			{
				if(item.get(counter).getItemMeta().hasLore() == true)
				{
					if(item.get(counter).getItemMeta().getLore().toString().contains("내구도"))
					{
						if(!(item.get(counter).getItemMeta().getLore().toString().contains("[주문서]")||item.get(counter).getItemMeta().getLore().toString().contains("[룬]")||item.get(counter).getItemMeta().getLore().toString().contains("[소비]")))
						{
							for(int count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
							{
								String nowlore=ChatColor.stripColor(item.get(counter).getItemMeta().getLore().get(count));
								if(nowlore.contains(" : "))
								{
									ItemMeta Meta = item.get(counter).getItemMeta();
									if(nowlore.contains(" / "))
									{
										if(Meta.getLore().get(count).contains("내구도") == true)
										{
											String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
											String[] SubLore = Lore[1].split(" / ");
											List<String> PLore = Meta.getLore();
											if((Integer.parseInt(SubLore[0])-1) <= 0)
											{
											  	YamlLoader configYaml = new YamlLoader();
												configYaml.getConfig("config.yml");
												if(configYaml.getBoolean("Server.CustomWeaponBreak"))
												{
													SoundEffect.SP(player, Sound.ENTITY_ITEM_BREAK, 1.2F, 1.0F);
													if(item.get(counter).getItemMeta().hasDisplayName())
														player.sendMessage("§c[장비 파괴] : §e"+item.get(counter).getItemMeta().getDisplayName()+"§c 장비가 파괴되었습니다!");
													else
														player.sendMessage("§c[장비 파괴] : 장비가 파괴되었습니다!");
													if(counter==0)
														player.getInventory().setHelmet(new ItemStack(0));
													else if(counter==1)
														player.getInventory().setChestplate(new ItemStack(0));
													else if(counter==2)
														player.getInventory().setLeggings(new ItemStack(0));
													else if(counter==3)
														player.getInventory().setBoots(new ItemStack(0));
													else if(added==1 && counter==4)
														player.getInventory().setItemInMainHand(new ItemStack(0));
													else if(added==2 && counter==4)
														player.getInventory().setItemInOffHand(new ItemStack(0));
													else if(added==3)
													{
														if(counter==4)
															player.getInventory().setItemInMainHand(new ItemStack(0));
														else
															player.getInventory().setItemInOffHand(new ItemStack(0));
													}
													else
														item.set(counter, new ItemStack(0));
													break;
												}
												else
													PLore.set(count,"§f"+  Lore[0] + " : "+ 0 +" / "+SubLore[1]);
											}
											else
											{
												if((Integer.parseInt(SubLore[0])-1) == 20)
												{
													SoundEffect.SP(player, Sound.BLOCK_ANVIL_USE, 0.8F, 0.5F);
													if(counter==0)
														player.sendMessage("§e[장비 파괴] : 투구의 내구도가 다 닳아 갑니다!");
													else if(counter==1)
														player.sendMessage("§e[장비 파괴] : 흉갑의 내구도가 다 닳아 갑니다!");
													else if(counter==2)
														player.sendMessage("§e[장비 파괴] : 낭갑의 내구도가 다 닳아 갑니다!");
													else if(counter==3)
														player.sendMessage("§e[장비 파괴] : 신발의 내구도가 다 닳아 갑니다!");
													else if(added==1 && counter==4)
														player.sendMessage("§e[장비 파괴] : 주 무기의 내구도가 다 닳아 갑니다!");
													else if(added==2 && counter==4)
														player.sendMessage("§e[장비 파괴] : 보조 무기의 내구도가 다 닳아 갑니다!");
													else if(added==3)
													{
														if(counter==4)
															player.sendMessage("§e[장비 파괴] : 주 무기의 내구도가 다 닳아 갑니다!");
														else
															player.sendMessage("§e[장비 파괴] : 보조 무기의 내구도가 다 닳아 갑니다!");
													}
													else
														player.sendMessage("§e[장비 파괴] : 장비의 내구도가 다 닳아 갑니다!");
												}
												PLore.set(count,"§f"+  Lore[0] + " : "+(Integer.parseInt(SubLore[0])-1) +" / "+SubLore[1]);
												DurabilityExit = true;
											}
											Meta.setLore(PLore);
											item.get(counter).setItemMeta(Meta);
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
			for(int counter = 0; counter < item.size(); counter++)
			{
				if(item.get(counter)!=null)
				if(item.get(counter).hasItemMeta() == true)
				{
					if(item.get(counter).getItemMeta().hasLore() == true)
					{
						if(item.get(counter).getItemMeta().getLore().toString().contains("숙련도"))
						{
							for(int count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
							{
								String nowlore=ChatColor.stripColor(item.get(counter).getItemMeta().getLore().get(count));
								if(nowlore.contains(" : "))
								{
									ItemMeta Meta = item.get(counter).getItemMeta();
									if(Meta.getLore().get(count).contains("숙련도") == true)
									{
										float Proficiency = 0.07F * main.Main_ServerOption.eventProficiency;
										String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
										String[] SubLore = Lore[1].split("%");
										List<String> PLore = Meta.getLore();
										DecimalFormat format = new DecimalFormat(".##");
										String str = format.format((Float.parseFloat(SubLore[0])+Proficiency));
										if(str.charAt(0)=='.')
											str = "0"+str;
										if((Float.parseFloat(SubLore[0])+0.07F) >= 100.0F)
											PLore.set(count,"§f"+  Lore[0] + " : "+ 100.0 +"%§f");
										else
											PLore.set(count,"§f"+  Lore[0] + " : "+ str +"%§f");
										Meta.setLore(PLore);
										item.get(counter).setItemMeta(Meta);
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
	
	public static void decreaseDurabilityWeapon(Player player)
	{
		boolean DurabilityExit = false;
		ArrayList<ItemStack> item = new ArrayList<ItemStack>();
		item.add(player.getInventory().getItemInMainHand());
		if(main.Main_ServerOption.dualWeapon)
			item.add(player.getInventory().getItemInOffHand());
		for(int counter = 0; counter < item.size(); counter++)
		{
			if(item.get(counter)!=null)
			{
				if(counter==1 && (item.get(counter).getTypeId()==442||item.get(counter).getTypeId()==261))
					return;
				if(item.get(counter).hasItemMeta() == true)
				{
					if(item.get(counter).getItemMeta().hasLore() == true)
					{
						if(item.get(counter).getItemMeta().getLore().toString().contains("내구도"))
						{
							if(!(item.get(counter).getItemMeta().getLore().toString().contains("[주문서]")||item.get(counter).getItemMeta().getLore().toString().contains("[룬]")||item.get(counter).getItemMeta().getLore().toString().contains("[소비]")))
							{
								for(int count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
								{
									String nowlore=ChatColor.stripColor(item.get(counter).getItemMeta().getLore().get(count));
									if(nowlore.contains(" : ") && nowlore.contains(" / "))
									{
										ItemMeta Meta = item.get(counter).getItemMeta();
										if(Meta.getLore().get(count).contains("내구도") == true)
										{
											String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
											String[] SubLore = Lore[1].split(" / ");
											List<String> PLore = Meta.getLore();
											if((Integer.parseInt(SubLore[0])-1) <= 0)
											{
											  	YamlLoader configYaml = new YamlLoader();
												configYaml.getConfig("config.yml");
												if(configYaml.getBoolean("Server.CustomWeaponBreak"))
												{
													SoundEffect.SP(player, Sound.ENTITY_ITEM_BREAK, 1.2F, 1.0F);
													if(item.get(counter).getItemMeta().hasDisplayName())
														player.sendMessage("§c[장비 파괴] : §e"+item.get(counter).getItemMeta().getDisplayName()+"§c 장비가 파괴되었습니다!");
													else
														player.sendMessage("§c[장비 파괴] : 장비가 파괴되었습니다!");
													if(counter==0)
														player.getInventory().setItemInMainHand(new ItemStack(0));
													else
														player.getInventory().setItemInOffHand(new ItemStack(0));
													break;
												}
												else
													PLore.set(count, "§f"+ Lore[0] + " : "+ 0 +" / "+SubLore[1]);
											}
											else
											{
												PLore.set(count, "§f"+ Lore[0] + " : "+(Integer.parseInt(SubLore[0])-1) +" / "+SubLore[1]);
												DurabilityExit = true;
											}
											Meta.setLore(PLore);
											item.get(counter).setItemMeta(Meta);
										}
									}
								}
							}
						}
					}
				}
				if(DurabilityExit == true)
				{
					if(item.get(counter).hasItemMeta() == true)
					{
						if(item.get(counter).getItemMeta().hasLore() == true)
						{
							if(item.get(counter).getItemMeta().getLore().toString().contains("숙련도"))
							{
								for(int count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
								{
									String nowlore=ChatColor.stripColor(item.get(counter).getItemMeta().getLore().get(count));
									if(nowlore.contains(" : "))
									{
										ItemMeta Meta = item.get(counter).getItemMeta();
										if(Meta.getLore().get(count).contains("숙련도") == true)
										{
											float Proficiency = 0.07F * main.Main_ServerOption.eventProficiency;
											String[] Lore = ChatColor.stripColor(Meta.getLore().get(count)).split(" : ");
											String[] SubLore = Lore[1].split("%");
											List<String> PLore = Meta.getLore();
											DecimalFormat format = new DecimalFormat(".##");
											String str = format.format((Float.parseFloat(SubLore[0])+Proficiency));
											if(str.charAt(0)=='.')
												str = "0"+str;
											if((Float.parseFloat(SubLore[0])+0.07F) >= 100.0F)
												PLore.set(count,"§f"+  Lore[0] + " : "+ 100.0 +"%§f");
											else
												PLore.set(count,"§f"+  Lore[0] + " : "+ str +"%§f");
											Meta.setLore(PLore);
											item.get(counter).setItemMeta(Meta);
										}
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
}
