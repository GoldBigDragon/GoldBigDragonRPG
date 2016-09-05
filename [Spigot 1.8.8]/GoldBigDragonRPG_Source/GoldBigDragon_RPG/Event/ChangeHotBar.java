package GoldBigDragon_RPG.Event;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Damageable;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class ChangeHotBar
{
	public void HotBarMove(PlayerItemHeldEvent event)
	{
		Player player = event.getPlayer();
		if(new GoldBigDragon_RPG.Effect.Corpse().DeathCapture(player,false))
			return;
			
		byte NewSlot = (byte)event.getNewSlot();
		if(player.getInventory().getItem(NewSlot) != null)
		{
			if(player.getInventory().getItem(NewSlot).hasItemMeta() == true)
			{
				ItemStack item = player.getInventory().getItem(NewSlot);
				if(item.getItemMeta().hasLore() == true)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()==false
							&&item.getItemMeta().getLore().toString().contains("[소비]"))
					{
						byte PrevSlot = (byte)event.getPreviousSlot();

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
							else if(nowlore.contains("환생"))
							{
								if(nowlore.contains( " + ")==true)
								{
								  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								    YamlManager Config = YC.getNewConfig("config.yml");
									GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
									if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
									{
										GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_RealLevel(1);
										GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Level(1);
										GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_EXP(0);
										GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_MaxEXP(100);
										
										s.SP(player, Sound.LEVEL_UP, 1.0F, 0.5F);
										s.SP(player, Sound.FIREWORK_LAUNCH, 1.0F, 1.2F);
										s.SP(player, Sound.ORB_PICKUP, 1.0F, 0.8F);
										GoldBigDragon_RPG.Effect.PacketSender PS = new GoldBigDragon_RPG.Effect.PacketSender();
										PS.sendTitleSubTitle(player,"\'"+ChatColor.YELLOW+"■ [ Rebirth ] ■"+"\'",  "\'"+ChatColor.YELLOW+"[레벨 및 경험치가 초기화 되었습니다!]"+"\'", (byte)1, (byte)5, (byte)1);
									}
									else
									{
										player.sendMessage(ChatColor.RED + "[SYSTEM] : 서버 시스템에 맞지 않아 환생을 할 수 없습니다!");
										s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
									}
								}
							}
						}
						GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
						if(Health > 0)
						{
							sound.SL(player.getLocation(), Sound.DRINK, 2.0F, 0.8F);
							Damageable Dp = player;
							if(Dp.getMaxHealth() < Dp.getHealth()+Health)
								Dp.setHealth(Dp.getMaxHealth());
							else
								Dp.setHealth(Dp.getHealth() + Health);
						}
						if(Mana >0)
						{
							if(ServerOption.MagicSpellsCatched == true)
							{
								OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
								MS.DrinkManaPotion(player, Mana);
								sound.SL(player.getLocation(), Sound.WATER, 2.0F, 1.9F);
							}
						}
						if(Food > 0)
						{
							sound.SL(player.getLocation(), Sound.EAT, 2.0F, 1.2F);
							if(player.getFoodLevel()+Food > 40)
								player.setFoodLevel(40);
							else
								player.setFoodLevel(player.getFoodLevel()+Food);
						}
						if(item.getAmount() != 1)
							item.setAmount(item.getAmount()-1);
						else
							player.getInventory().setItem(NewSlot, new ItemStack(0));

						GoldBigDragon_RPG.Effect.PacketSender PS = new GoldBigDragon_RPG.Effect.PacketSender();
						PS.changeItemSlot(player, PrevSlot);
						
						
					}
					else if(item.getItemMeta().getLore().size() == 4)
					{
						if(item.getItemMeta().getLore().get(3).equals(ChatColor.YELLOW + "[클릭시 퀵슬롯에서 삭제]"))
						{
							byte PrevSlot = (byte)event.getPreviousSlot();
							
							String CategoryName = ChatColor.stripColor(item.getItemMeta().getLore().get(0));
							String Skillname = ChatColor.stripColor(item.getItemMeta().getLore().get(1));

						  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
							YamlManager Config = YC.getNewConfig("config.yml");
							YamlManager PlayerSkillList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
							YamlManager AllSkillList  = YC.getNewConfig("Skill/SkillList.yml");
							short PlayerSkillRank =0;

							if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
								PlayerSkillRank= (short) PlayerSkillList.getInt("MapleStory."+CategoryName+".Skill."+Skillname);
							else
								PlayerSkillRank= (short) PlayerSkillList.getInt("Mabinogi."+CategoryName+"."+Skillname);
								
							if(PlayerSkillRank ==0)
							{
								player.sendMessage(ChatColor.RED + "[스킬] : 해당 스킬은 삭제된 스킬입니다!");
								player.getInventory().setItem(NewSlot, new ItemStack(0));
								return;
							}
							GoldBigDragon_RPG.Effect.PacketSender PS = new GoldBigDragon_RPG.Effect.PacketSender();
							PS.changeItemSlot(player, PrevSlot);
							
							String Command = AllSkillList.getString(Skillname+".SkillRank."+PlayerSkillRank+".Command");
							String Spell = AllSkillList.getString(Skillname+".SkillRank."+PlayerSkillRank+".MagicSpells");
							boolean isConsole = AllSkillList.getBoolean(Skillname+".SkillRank."+PlayerSkillRank+".Command");
							String AffectStat = AllSkillList.getString(Skillname+".SkillRank."+PlayerSkillRank+".AffectStat");
							String DistrictWeapon = AllSkillList.getString(Skillname+".SkillRank."+PlayerSkillRank+".DistrictWeapon");

							ServerOption.PlayerUseSpell.put(player, AffectStat);
							ServerOption.PlayerlastItem.put(player, player.getInventory().getItem(event.getPreviousSlot()));
							
							boolean Useable = false;
							
							if(DistrictWeapon.equalsIgnoreCase("없음")==false)
							{
								if(player.getInventory().getItem(PrevSlot) != null)
								{
									ItemStack PrevItem = player.getInventory().getItem(PrevSlot);
									if(player.getInventory().getItem(PrevSlot).hasItemMeta() == false)
									{
										short PlayerItemIDItemStack = (short) PrevItem.getTypeId();
										switch(DistrictWeapon)
										{
										case "근접 무기":
										{	switch(PlayerItemIDItemStack)
											{
												case 267:
												case 268:
												case 272:
												case 276:
												case 283:
												case 271:
												case 275:
												case 258:
												case 279:
												case 286:
												case 290:
												case 291:
												case 292:
												case 293:
												case 294:
												Useable = true;
												break;
											}
										}	break;
										case "한손 검":
										{	switch(PlayerItemIDItemStack)
											{
												case 267:
												case 268:
												case 272:
												case 276:
												case 283:
												Useable = true;
												break;
											}
										}	break;
										case "도끼":
										{	switch(PlayerItemIDItemStack)
											{
												case 271:
												case 275:
												case 258:
												case 279:
												case 286:
												Useable = true;
												break;
											}
										}	break;
										case "낫":
										{	switch(PlayerItemIDItemStack)
											{
												case 290:
												case 291:
												case 292:
												case 293:
												case 294:
												Useable = true;
												break;
											}
										}	break;
										case "원거리 무기":
										{	switch(PlayerItemIDItemStack)
											{
												case 261:
												case 23:
												Useable = true;
												break;
											}
										}	break;
										case "활":
										{	if(PlayerItemIDItemStack == 261)
												Useable = true;
										}	break;
										case "석궁":
										{	if(PlayerItemIDItemStack == 23)
												Useable = true;
										}	break;
										case "마법 무기":
										{	switch(PlayerItemIDItemStack)
											{
												case 280:
												case 352:
												case 369:
												Useable = true;
												break;
											}
										}	break;
										case "원드":
										{	if(PlayerItemIDItemStack == 280 || PlayerItemIDItemStack==352)
												Useable = true;
										}	break;
										case "스태프":
										{	if(PlayerItemIDItemStack == 369)
												Useable = true;
										}	break;
										}
									}
									else
									{
										if(PrevItem.getItemMeta().hasLore() == true)
										{
											switch(DistrictWeapon)
											{
											case "근접 무기":
												if(PrevItem.getItemMeta().getLore().toString().contains("[한손 검]")||PrevItem.getItemMeta().getLore().toString().contains("[양손 검]")
												||PrevItem.getItemMeta().getLore().toString().contains("[도끼]")||PrevItem.getItemMeta().getLore().toString().contains("[낫]")
												||PrevItem.getItemMeta().getLore().toString().contains("[근접 무기]"))
													Useable = true;
												break;
											case "원거리 무기":
												if(PrevItem.getItemMeta().getLore().toString().contains("[활]")||PrevItem.getItemMeta().getLore().toString().contains("[석궁]")
														||PrevItem.getItemMeta().getLore().toString().contains("[원거리 무기]"))
													Useable = true;
												break;
											case "마법 무기":
												if(PrevItem.getItemMeta().getLore().toString().contains("[원드]")||PrevItem.getItemMeta().getLore().toString().contains("[스태프]")
														||PrevItem.getItemMeta().getLore().toString().contains("[마법 무기]"))
													Useable = true;
												break;
											default:
												if(PrevItem.getItemMeta().getLore().toString().contains(DistrictWeapon))
													Useable = true;
												break;
											}
										}
									}
								}
							}
							if(Useable == false && DistrictWeapon.equalsIgnoreCase("없음")==false)
							{
								player.sendMessage(ChatColor.RED + "[스킬] : 현재 무기로는 스킬을 사용할 수 없습니다!");
								player.sendMessage(ChatColor.RED + "필요 무기 타입 : "+DistrictWeapon);
								return;
							}
							if(Command.equalsIgnoreCase("null") == false)
							{
								if(isConsole == true)
									Bukkit.getConsoleSender().sendMessage(Command);
								else
									player.chat(Command);
							}

							if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
							{
								if(Spell.equalsIgnoreCase("null") == false)
								{
									Object[] spells = MagicSpells.spells().toArray();
									Spell spell;
									boolean isExit = false;
									for(short count = 0; count < spells.length;count++)
									{
										spell = (Spell) spells[count];
										if(spell.getName().equals(Spell))
										{
											isExit = true;
											break;
										}
									}
									if(isExit == true)
									{
										OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
										MS.CastSpell(player, Spell);
									}
									else
									{
										player.sendMessage(ChatColor.RED+"[스킬] : MagicSpells플러그인에 해당 스펠이 존재하지 않습니다! 관리자에게 문의하세요!");
										player.sendMessage(ChatColor.RED+"존재하지 않는 스펠 이름 : " + ChatColor.YELLOW + Spell);
										player.sendMessage(ChatColor.RED+"존재하지 않는 스펠이 등록된 스킬 : " + ChatColor.YELLOW + Skillname +" "+PlayerSkillRank+"랭크" );
										GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
										s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.9F);
									}
								}
							}
						}
						else
						{
							GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
							ETC.SlotChangedUpdatePlayerHPMP(player, player.getInventory().getItem(event.getNewSlot()), true);
							HotBarSound(player, (short) player.getInventory().getItem(event.getNewSlot()).getTypeId());
						}
					}
					else
					{
						GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
						ETC.SlotChangedUpdatePlayerHPMP(player, player.getInventory().getItem(event.getNewSlot()), true);
						HotBarSound(player, (short) player.getInventory().getItem(event.getNewSlot()).getTypeId());
					}
				}
				else
				{
					GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
					ETC.SlotChangedUpdatePlayerHPMP(player, player.getInventory().getItem(event.getNewSlot()), true);
					HotBarSound(player, (short) player.getInventory().getItem(event.getNewSlot()).getTypeId());
				}
			}
			else
			{
				GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
				ETC.SlotChangedUpdatePlayerHPMP(player, player.getInventory().getItem(event.getNewSlot()), true);
				HotBarSound(player, (short) player.getInventory().getItem(event.getNewSlot()).getTypeId());
			}
		}
		else
		{
			GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
			ETC.SlotChangedUpdatePlayerHPMP(player,  player.getInventory().getItem(NewSlot), true);
			HotBarSound(player, (short) -1);
		}
		return;
	}
	
	public void HotBarSound(Player player,short itemID)
	{
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound())
		{
			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			if(itemID == -1)
				s.SP(player, Sound.WOOD_CLICK,0.8F, 0.5F);
			if(itemID >= 298&&itemID <= 317)
				s.SP(player, Sound.HORSE_ARMOR,0.9F, 0.5F);
			else if(itemID >= 290&&itemID <= 294)
	  			s.SP(player, Sound.STEP_GRAVEL, 0.8F, 1.0F);
			else if(itemID == 46)
				s.SP(player, Sound.FUSE,1.5F, 0.8F);
			else if(itemID == 261)
	  			s.SP(player, Sound.ARROW_HIT, 1.0F, 1.0F);
			else if(itemID == 259)
	  			s.SP(player, Sound.FIRE_IGNITE, 1.0F, 1.0F);
			else if(itemID == 256 || itemID == 269 || itemID == 273 || itemID == 277 || itemID == 284)
	  			s.SP(player, Sound.DIG_GRAVEL, 0.8F, 1.0F);
			else if(itemID == 257 || itemID == 270 || itemID == 274 || itemID == 278 || itemID == 285)
	  			s.SP(player, Sound.STEP_WOOD, 0.8F, 1.0F);
			else if(itemID == 258 || itemID == 271 || itemID == 275 || itemID == 279 || itemID == 286)
	  			s.SP(player, Sound.STEP_STONE, 0.8F, 1.0F);
			else if(itemID == 267 || itemID == 268 || itemID == 272 || itemID == 276 || itemID == 283)
				s.SP(player, Sound.HORSE_ARMOR,1.0F, 2.0F);
			else if(itemID == 346)
				s.SP(player, Sound.SWIM,1.5F, 1.0F);
			else if(itemID == 359)
				s.SP(player, Sound.SHEEP_SHEAR,1.5F, 1.0F);
			else if(itemID == 368)
				s.SP(player, Sound.ENDERMAN_TELEPORT,1.0F, 1.0F);
			else if(itemID == 373)
				s.SP(player, Sound.DRINK,1.0F, 1.0F);
			else if(itemID == 438)
				s.SP(player, Sound.GLASS,1.0F, 1.0F);
			else
				s.SP(player, Sound.ITEM_PICKUP,0.8F, 1.0F);
		}
		return;
	}
}
