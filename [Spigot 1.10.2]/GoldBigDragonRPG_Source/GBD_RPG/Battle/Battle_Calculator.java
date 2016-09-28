package GBD_RPG.Battle;

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

import GBD_RPG.Main_Main.Main_ServerOption;
import GBD_RPG.Util.Util_Number;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Battle_Calculator
{
	//플레이어의 근접최소 공격력을 따 오는 메소드//
	public int CombatDamageGet(Entity entity, int DefaultDamage, int STR, boolean isMin)
	{
		if(entity != null)
		if(entity.getType() == EntityType.PLAYER)
		{
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null)
			{
				if(item.hasItemMeta() == true)
				{
					if(item.getItemMeta().hasLore() == true)
						if(item.getItemMeta().getLore().toString().contains(GBD_RPG.Main_Main.Main_ServerOption.Damage+" : ") == true)
						{
							switch(item.getType())
							{
							case WOOD_SPADE :
							case GOLD_SPADE :
							case WOOD_PICKAXE :
							case GOLD_PICKAXE:
								DefaultDamage = DefaultDamage - 2;
								break;
							case STONE_SPADE:
							case STONE_PICKAXE:
								DefaultDamage = DefaultDamage -3;
								break;
							case IRON_SPADE:
							case WOOD_SWORD:
							case GOLD_SWORD:
							case IRON_PICKAXE:
								DefaultDamage = DefaultDamage -4;
								break;
							case DIAMOND_SPADE:
							case STONE_SWORD:
							case DIAMOND_PICKAXE:
								DefaultDamage = DefaultDamage -5;
								break;
							case IRON_SWORD:
								DefaultDamage = DefaultDamage -6;
								break;
							case WOOD_AXE:
							case GOLD_AXE:
							case DIAMOND_AXE:
							case DIAMOND_SWORD:
								DefaultDamage = DefaultDamage -7;
								break;
							case STONE_AXE:
							case IRON_AXE:
								DefaultDamage = DefaultDamage -9;
								break;
							}
					}
				}
			}
			STR = STR + getPlayerEquipmentStat((Player)entity, "STR", true, null)[0];
			if(isMin)
				DefaultDamage = DefaultDamage + getPlayerEquipmentStat((Player)entity, "Damage", true, null)[0];
			else
				DefaultDamage = DefaultDamage + getPlayerEquipmentStat((Player)entity, "Damage", true, null)[1];
		}
		return returnCombatValue(STR, DefaultDamage, true);
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
	public int criticalrend(Entity entity, int attacker_luk, int attacker_will, int attacker_damage, int defenser_protect, int attacker_critical)
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
	public int getBalance(Entity entity, int DEX, int player_balance)
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
	public int getCritical(Entity entity, int player_luk, int player_will, int defaultCritical)
	{
		int critical = defaultCritical;
		if(entity!=null)
		if(entity.getType() == EntityType.PLAYER)
			critical = critical + getPlayerEquipmentStat((Player)entity, "Critical", false, null)[0];
		critical = critical + (int)(player_luk/5 + player_will/10);
		return critical;
	}
	
	//마방 계산기//
	public int getMagicDEF(Entity entity, int player_int)
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
	public int getMagicProtect(Entity entity, int player_int)
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
	public int getDEFcrash(Entity entity, int player_dex)
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
	public int getMagicDEFcrash(Entity entity, int player_int)
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

	public int[] getPlayerEquipmentStat(Player player, String type, boolean isCombat, ItemStack newSlot)
	{
		int bonus[] = new int[2];
		String Lore[];
		switch(type)
		{
			case "Damage":type = GBD_RPG.Main_Main.Main_ServerOption.Damage;break;
			case "DEF":type = "방어";break;
			case "DEFcrash":type = "방어관통";break;
			case "Protect":type = "보호";break;
			case "MagicDamage":type = GBD_RPG.Main_Main.Main_ServerOption.MagicDamage;break;
			case "Magic_DEF":type = "마법 방어";break;
			case "MagicDEFcrash":type = "마법 방어관통";break;
			case "Magic_Protect":type = "마법 보호";break;
			case "STR":type = GBD_RPG.Main_Main.Main_ServerOption.STR;break;
			case "DEX":type = GBD_RPG.Main_Main.Main_ServerOption.DEX;break;
			case "INT":type = GBD_RPG.Main_Main.Main_ServerOption.INT;break;
			case "WILL":type = GBD_RPG.Main_Main.Main_ServerOption.WILL;break;
			case "LUK":type = GBD_RPG.Main_Main.Main_ServerOption.LUK;break;
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
		item.add(player.getInventory().getItemInOffHand());
		boolean Totaluseable = true;
		for(byte counter = 0; counter < item.size(); counter++)
		{
			boolean isCancel = false;
			boolean ExitDurability = true;
			if(item.get(counter) != null)
			{
				if(counter >= 4 && newSlot==null)
				{
					if(counter == 4)
					{
						if(isCombat == false && !(item.get(4).getTypeId() == 261 ||item.get(4).getTypeId() == 262 || item.get(4).getTypeId() == 439|| item.get(4).getTypeId() == 440) && type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.Damage)==0)
							isCancel = true;
					}
					else
					{
						if(isCombat ==false && !(item.get(5).getTypeId() == 261 ||item.get(5).getTypeId() == 262 || item.get(5).getTypeId() == 439|| item.get(5).getTypeId() == 440) && type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.Damage)==0)
							isCancel = true;
						if(isCombat && (item.get(counter).getTypeId()==261) && type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.Damage)==0)
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
									for(byte count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
									{
										String nowlore=ChatColor.stripColor(item.get(counter).getItemMeta().getLore().get(count));
										if(nowlore.contains(" : "))
										{
											if(nowlore.contains("직업") == true)
												if(nowlore.split(" : ")[1].compareTo(Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getPlayerRootJob())!=0)
													useable = false;
											if(nowlore.contains("최소") == true)
											{
												String[] Resist = nowlore.split(" ");
												if(Resist[Resist.length-3].compareTo("레벨")==0)
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
												else if(Resist[Resist.length-3].compareTo("누적레벨")==0)
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
												else if(Resist[Resist.length-3].compareTo(GBD_RPG.Main_Main.Main_ServerOption.STR)==0)
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
												else if(Resist[Resist.length-3].compareTo(GBD_RPG.Main_Main.Main_ServerOption.DEX)==0)
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
												else if(Resist[Resist.length-3].compareTo(GBD_RPG.Main_Main.Main_ServerOption.INT)==0)
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
												else if(Resist[Resist.length-3].compareTo(GBD_RPG.Main_Main.Main_ServerOption.WILL)==0)
													useable = Integer.parseInt(Resist[Resist.length-1]) <= Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
												else if(Resist[Resist.length-3].compareTo(GBD_RPG.Main_Main.Main_ServerOption.LUK)==0)
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
										for(byte count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
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
															if(type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.STR)==0||type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.DEX)==0||
																type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.INT)==0||type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.WILL)==0||
																type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.LUK)==0)
															{
																if(item.get(counter).getItemMeta().getLore().get(count).contains("최소") == false)
																	bonus[0] = bonus[0] + Integer.parseInt(Lore[1]);
															}
															else if(type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.Damage)==0||type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.MagicDamage)==0||type.compareTo("업그레이드")==0)
															{
																if(type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.Damage)==0)
																{
																	String[] SubLore = Lore[1].split(" ~ ");
																	bonus[0] = bonus[0] + Integer.parseInt(SubLore[0]);
																	bonus[1] = bonus[1] + Integer.parseInt(SubLore[1]);
																}
																else if(type.compareTo(GBD_RPG.Main_Main.Main_ServerOption.MagicDamage)==0||type.compareTo("업그레이드")==0)
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
			new GBD_RPG.Effect.Effect_Packet().sendTitleSubTitle(player,"\'§e\'", "\'§c(장비가 제 성능을 하지 못하고 있다!)\'", (byte)1,(byte)1, (byte)1);
		return bonus;
	}
	
	public void decreaseDurabilityArmor(Player player)
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
		if(player.getInventory().getItemInOffHand().getTypeId()==442)
		{
			item.add(player.getInventory().getItemInOffHand());
			added = (byte) (added + 2);
		}
		for(byte counter = 0; counter < item.size(); counter++)
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
							for(byte count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
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
											  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
												YamlManager Config =YC.getNewConfig("config.yml");
												if(Config.getBoolean("Server.CustomWeaponBreak"))
												{
													new GBD_RPG.Effect.Effect_Sound().SP(player, Sound.ENTITY_ITEM_BREAK, 1.2F, 1.0F);
													if(item.get(counter).getItemMeta().hasDisplayName())
														player.sendMessage(ChatColor.RED+"[장비 파괴] : "+ ChatColor.YELLOW+item.get(counter).getItemMeta().getDisplayName()+ChatColor.RED+ " 장비가 파괴되었습니다!");
													else
														player.sendMessage(ChatColor.RED+"[장비 파괴] : 장비가 파괴되었습니다!");
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
													PLore.set(count,ChatColor.WHITE +  Lore[0] + " : "+ 0 +" / "+SubLore[1]);
											}
											else
											{
												if((Integer.parseInt(SubLore[0])-1) == 20)
												{
													new GBD_RPG.Effect.Effect_Sound().SP(player, Sound.BLOCK_ANVIL_USE, 0.8F, 0.5F);
													if(counter==0)
														player.sendMessage(ChatColor.YELLOW+"[장비 파괴] : 투구의 내구도가 다 닳아 갑니다!");
													else if(counter==1)
														player.sendMessage(ChatColor.YELLOW+"[장비 파괴] : 흉갑의 내구도가 다 닳아 갑니다!");
													else if(counter==2)
														player.sendMessage(ChatColor.YELLOW+"[장비 파괴] : 낭갑의 내구도가 다 닳아 갑니다!");
													else if(counter==3)
														player.sendMessage(ChatColor.YELLOW+"[장비 파괴] : 신발의 내구도가 다 닳아 갑니다!");
													else if(added==1 && counter==4)
														player.sendMessage(ChatColor.YELLOW+"[장비 파괴] : 주 무기의 내구도가 다 닳아 갑니다!");
													else if(added==2 && counter==4)
														player.sendMessage(ChatColor.YELLOW+"[장비 파괴] : 보조 무기의 내구도가 다 닳아 갑니다!");
													else if(added==3)
													{
														if(counter==4)
															player.sendMessage(ChatColor.YELLOW+"[장비 파괴] : 주 무기의 내구도가 다 닳아 갑니다!");
														else
															player.sendMessage(ChatColor.YELLOW+"[장비 파괴] : 보조 무기의 내구도가 다 닳아 갑니다!");
													}
													else
														player.sendMessage(ChatColor.YELLOW+"[장비 파괴] : 장비의 내구도가 다 닳아 갑니다!");
												}
												PLore.set(count,ChatColor.WHITE +  Lore[0] + " : "+(Integer.parseInt(SubLore[0])-1) +" / "+SubLore[1]);
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
			for(byte counter = 0; counter < item.size(); counter++)
			{
				if(item.get(counter)!=null)
				if(item.get(counter).hasItemMeta() == true)
				{
					if(item.get(counter).getItemMeta().hasLore() == true)
					{
						if(item.get(counter).getItemMeta().getLore().toString().contains("숙련도"))
						{
							for(byte count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
							{
								String nowlore=ChatColor.stripColor(item.get(counter).getItemMeta().getLore().get(count));
								if(nowlore.contains(" : "))
								{
									ItemMeta Meta = item.get(counter).getItemMeta();
									if(Meta.getLore().get(count).contains("숙련도") == true)
									{
										float Proficiency = 0.07F * GBD_RPG.Main_Main.Main_ServerOption.Event_Proficiency;
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
	
	public void decreaseDurabilityWeapon(Player player)
	{
		boolean DurabilityExit = false;
		ArrayList<ItemStack> item = new ArrayList<ItemStack>();
		item.add(player.getInventory().getItemInMainHand());
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
								for(byte count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
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
											  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
												YamlManager Config =YC.getNewConfig("config.yml");
												if(Config.getBoolean("Server.CustomWeaponBreak"))
												{
													new GBD_RPG.Effect.Effect_Sound().SP(player, Sound.ENTITY_ITEM_BREAK, 1.2F, 1.0F);
													if(item.get(counter).getItemMeta().hasDisplayName())
														player.sendMessage(ChatColor.RED+"[장비 파괴] : "+ ChatColor.YELLOW+item.get(counter).getItemMeta().getDisplayName()+ChatColor.RED+ " 장비가 파괴되었습니다!");
													else
														player.sendMessage(ChatColor.RED+"[장비 파괴] : 장비가 파괴되었습니다!");
													if(counter==0)
														player.getInventory().setItemInMainHand(new ItemStack(0));
													else
														player.getInventory().setItemInOffHand(new ItemStack(0));
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
								for(byte count = 0; count < item.get(counter).getItemMeta().getLore().size(); count++)
								{
									String nowlore=ChatColor.stripColor(item.get(counter).getItemMeta().getLore().get(count));
									if(nowlore.contains(" : "))
									{
										ItemMeta Meta = item.get(counter).getItemMeta();
										if(Meta.getLore().get(count).contains("숙련도") == true)
										{
											float Proficiency = 0.07F * GBD_RPG.Main_Main.Main_ServerOption.Event_Proficiency;
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
