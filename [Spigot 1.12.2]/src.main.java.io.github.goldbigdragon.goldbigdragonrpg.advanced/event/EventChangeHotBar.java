package event;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Damageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;

import effect.SoundEffect;

import main.MainServerOption;
import util.YamlLoader;

public class EventChangeHotBar implements Listener
{
	@EventHandler
	public void hotBarMove(PlayerItemHeldEvent event)
	{
		Player player = event.getPlayer();
		if(new corpse.CorpseMain().deathCapture(player,false))
			return;

		util.ETC etc = new util.ETC();
		int newSlot = event.getNewSlot();
		if(player.getInventory().getItem(newSlot) != null)
		{
			if(player.getInventory().getItem(newSlot).hasItemMeta())
			{
				ItemStack item = player.getInventory().getItem(newSlot);
				if(item.getItemMeta().hasLore())
				{
					if(!main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()
					&& item.getItemMeta().getLore().toString().contains("[소비]"))
					{
						int prevSlot = event.getPreviousSlot();

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
							else if(nowlore.contains("환생") && nowlore.contains(" + "))
							{
							  	YamlLoader configYaml = new YamlLoader();
							    configYaml.getConfig("config.yml");
								
								if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
								{
									main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_RealLevel(1);
									main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Level(1);
									main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_EXP(0);
									main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_MaxEXP(100);
									
									SoundEffect.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
									SoundEffect.playSound(player, Sound.ENTITY_FIREWORK_LAUNCH, 1.0F, 1.2F);
									SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.8F);
									effect.SendPacket sendPacket = new effect.SendPacket();
									sendPacket.sendTitle(player,"§e■ [ Rebirth ] ■",  "§e[레벨 및 경험치가 초기화 되었습니다!]", 1, 5, 1);
								}
								else
								{
									player.sendMessage("§c[SYSTEM] : 서버 시스템에 맞지 않아 환생을 할 수 없습니다!");
									SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
								}
							}
						}
						if(health > 0)
						{
							SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 2.0F, 0.8F);
							Damageable damageablePlayer = player;
							if(damageablePlayer.getMaxHealth() < damageablePlayer.getHealth()+health)
								damageablePlayer.setHealth(damageablePlayer.getMaxHealth());
							else
								damageablePlayer.setHealth(damageablePlayer.getHealth() + health);
						}
						if(mana > 0 && MainServerOption.MagicSpellsCatched)
						{
							otherplugins.SpellMain magicspell = new otherplugins.SpellMain();
							magicspell.DrinkManaPotion(player, mana);
							SoundEffect.playSoundLocation(player.getLocation(), Sound.BLOCK_WATER_AMBIENT, 2.0F, 1.9F);
						}
						if(food > 0)
						{
							SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 2.0F, 1.2F);
							if(player.getFoodLevel()+food > 40)
								player.setFoodLevel(40);
							else
								player.setFoodLevel(player.getFoodLevel()+food);
						}
						if(item.getAmount() != 1)
							item.setAmount(item.getAmount()-1);
						else
							player.getInventory().setItem(newSlot, new ItemStack(0));

						effect.SendPacket sendPacket = new effect.SendPacket();
						sendPacket.switchHotbarSlot(player, prevSlot);
					}
					else if(item.getItemMeta().getLore().size() == 4)
					{
						if(item.getItemMeta().getLore().get(3).equals("§e[클릭시 퀵슬롯에서 삭제]"))
						{
							int prevSlot = event.getPreviousSlot();
							
							String categoryName = ChatColor.stripColor(item.getItemMeta().getLore().get(0));
							String skillname = ChatColor.stripColor(item.getItemMeta().getLore().get(1));

						  	YamlLoader configYaml = new YamlLoader();
						  	YamlLoader playerSkillYaml = new YamlLoader();
						  	YamlLoader allSkillYaml = new YamlLoader();
							configYaml.getConfig("config.yml");
							playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
							allSkillYaml.getConfig("Skill/SkillList.yml");
							short playerSkillRank =0;

							if(!configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
								playerSkillRank= (short) playerSkillYaml.getInt("MapleStory."+categoryName+".Skill."+skillname);
							else
								playerSkillRank= (short) playerSkillYaml.getInt("Mabinogi."+categoryName+"."+skillname);
								
							if(playerSkillRank ==0)
							{
								player.sendMessage("§c[스킬] : 해당 스킬은 삭제된 스킬입니다!");
								player.getInventory().setItem(newSlot, new ItemStack(0));
								return;
							}
							effect.SendPacket sendPacket = new effect.SendPacket();
							sendPacket.switchHotbarSlot(player, prevSlot);
							
							String command = allSkillYaml.getString(skillname+".SkillRank."+playerSkillRank+".Command");
							String yamlSpell = allSkillYaml.getString(skillname+".SkillRank."+playerSkillRank+".MagicSpells");
							boolean isConsole = allSkillYaml.getBoolean(skillname+".SkillRank."+playerSkillRank+".Command");
							String affectStat = allSkillYaml.getString(skillname+".SkillRank."+playerSkillRank+".AffectStat");
							String districtWeapon = allSkillYaml.getString(skillname+".SkillRank."+playerSkillRank+".DistrictWeapon");

							MainServerOption.PlayerUseSpell.put(player, affectStat);
							MainServerOption.PlayerlastItem.put(player, player.getInventory().getItem(event.getPreviousSlot()));
							
							boolean useable = false;
							
							if(!districtWeapon.equals("없음"))
							{
								if(player.getInventory().getItem(prevSlot) != null)
								{
									ItemStack prevItem = player.getInventory().getItem(prevSlot);
									if(!player.getInventory().getItem(prevSlot).hasItemMeta())
									{
										int playerItemIDItemStack = prevItem.getTypeId();
										if(districtWeapon.equals("근접 무기"))
										{
											switch(playerItemIDItemStack)
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
												useable = true;
												break;
											}
										}
										else if(districtWeapon.equals("한손 검"))
										{
											switch(playerItemIDItemStack)
											{
												case 267:
												case 268:
												case 272:
												case 276:
												case 283:
												useable = true;
												break;
											}
										}
										else if(districtWeapon.equals("도끼"))
										{
											switch(playerItemIDItemStack)
											{
												case 271:
												case 275:
												case 258:
												case 279:
												case 286:
												useable = true;
												break;
											}
										}
										else if(districtWeapon.equals("낫"))
										{
											switch(playerItemIDItemStack)
											{
												case 290:
												case 291:
												case 292:
												case 293:
												case 294:
												useable = true;
												break;
											}
										}
										else if(districtWeapon.equals("원거리 무기"))
										{
											switch(playerItemIDItemStack)
											{
												case 261:
												case 23:
												useable = true;
												break;
											}
										}
										else if(districtWeapon.equals("활") && playerItemIDItemStack == 261)
										{
											useable = true;
										}
										else if(districtWeapon.equals("석궁") && playerItemIDItemStack == 23)
										{
											useable = true;
										}
										else if(districtWeapon.equals("마법 무기"))
										{
											switch(playerItemIDItemStack)
											{
												case 280:
												case 352:
												case 369:
												useable = true;
												break;
											}
										}
										else if(districtWeapon.equals("원드") && (playerItemIDItemStack == 280 || playerItemIDItemStack==352))
										{
											useable = true;
										}
										else if(districtWeapon.equals("스태프") && playerItemIDItemStack == 369)
										{
											useable = true;
										}
									}
									else
									{
										if(prevItem.getItemMeta().hasLore())
										{
											switch(districtWeapon)
											{
											case "근접 무기":
												if(prevItem.getItemMeta().getLore().toString().contains("[한손 검]")||prevItem.getItemMeta().getLore().toString().contains("[양손 검]")
												||prevItem.getItemMeta().getLore().toString().contains("[도끼]")||prevItem.getItemMeta().getLore().toString().contains("[낫]")
												||prevItem.getItemMeta().getLore().toString().contains("[근접 무기]"))
													useable = true;
												break;
											case "원거리 무기":
												if(prevItem.getItemMeta().getLore().toString().contains("[활]")||prevItem.getItemMeta().getLore().toString().contains("[석궁]")
														||prevItem.getItemMeta().getLore().toString().contains("[원거리 무기]"))
													useable = true;
												break;
											case "마법 무기":
												if(prevItem.getItemMeta().getLore().toString().contains("[원드]")||prevItem.getItemMeta().getLore().toString().contains("[스태프]")
														||prevItem.getItemMeta().getLore().toString().contains("[마법 무기]"))
													useable = true;
												break;
											default:
												if(prevItem.getItemMeta().getLore().toString().contains(districtWeapon))
													useable = true;
												break;
											}
										}
									}
								}
							}
							if(!useable && !districtWeapon.equals("없음"))
							{
								player.sendMessage("§c[스킬] : 현재 무기로는 스킬을 사용할 수 없습니다!");
								player.sendMessage("§c필요 무기 타입 : "+districtWeapon);
								return;
							}
							if(!command.equals("null"))
							{
								if(isConsole)
									Bukkit.getConsoleSender().sendMessage(command);
								else
									player.chat(command);
							}

							if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") &&
									!yamlSpell.equalsIgnoreCase("null"))
							{
								Object[] spells = MagicSpells.spells().toArray();
								Spell spell;
								boolean isExit = false;
								for(int count = 0; count < spells.length;count++)
								{
									spell = (Spell) spells[count];
									if(spell.getName().equals(yamlSpell))
									{
										isExit = true;
										break;
									}
								}
								if(isExit)
								{
									otherplugins.SpellMain magicSpells = new otherplugins.SpellMain();
									magicSpells.CastSpell(player, yamlSpell);
								}
								else
								{
									player.sendMessage("§c[스킬] : MagicSpells플러그인에 해당 스펠이 존재하지 않습니다! 관리자에게 문의하세요!");
									player.sendMessage("§c존재하지 않는 스펠 이름 : §e"+ yamlSpell);
									player.sendMessage("§c존재하지 않는 스펠이 등록된 스킬 : §e"+ skillname +" "+playerSkillRank+"랭크" );
									
									SoundEffect.playSound(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.9F);
								}
							}
						}
						else
						{
							etc.SlotChangedUpdatePlayerHPMP(player, player.getInventory().getItem(event.getNewSlot()));
							hotBarSound(player, (short) player.getInventory().getItem(event.getNewSlot()).getTypeId());
						}
					}
					else
					{
						etc.SlotChangedUpdatePlayerHPMP(player, player.getInventory().getItem(event.getNewSlot()));
						hotBarSound(player, (short) player.getInventory().getItem(event.getNewSlot()).getTypeId());
					}
				}
				else
				{
					etc.SlotChangedUpdatePlayerHPMP(player, player.getInventory().getItem(event.getNewSlot()));
					hotBarSound(player, (short) player.getInventory().getItem(event.getNewSlot()).getTypeId());
				}
			}
			else
			{
				etc.SlotChangedUpdatePlayerHPMP(player, player.getInventory().getItem(event.getNewSlot()));
				hotBarSound(player, (short) player.getInventory().getItem(event.getNewSlot()).getTypeId());
			}
		}
		else
		{
			ItemStack item = new ItemStack(3);
			item.setItemMeta(null);
			etc.SlotChangedUpdatePlayerHPMP(player, item);
			hotBarSound(player, (short) -1);
		}
		return;
	}
	
	public void hotBarSound(Player player,short itemID)
	{
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound())
		{
			
			if(itemID == -1)
				SoundEffect.playSound(player, Sound.BLOCK_WOOD_BUTTON_CLICK_ON,0.8F, 0.5F);
			if(itemID >= 298&&itemID <= 317)
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR,0.9F, 0.5F);
			else if(itemID >= 290&&itemID <= 294)
	  			SoundEffect.playSound(player, Sound.ITEM_HOE_TILL, 0.8F, 1.0F);
			else if(itemID == 46)
				SoundEffect.playSound(player, Sound.ENTITY_TNT_PRIMED,1.5F, 0.8F);
			else if(itemID == 261)
	  			SoundEffect.playSound(player, Sound.ENTITY_ARROW_HIT, 1.0F, 1.0F);
			else if(itemID == 259)
	  			SoundEffect.playSound(player, Sound.ITEM_FLINTANDSTEEL_USE, 1.0F, 1.0F);
			else if(itemID == 256 || itemID == 269 || itemID == 273 || itemID == 277 || itemID == 284)
	  			SoundEffect.playSound(player, Sound.ITEM_SHOVEL_FLATTEN, 0.8F, 1.0F);
			else if(itemID == 257 || itemID == 270 || itemID == 274 || itemID == 278 || itemID == 285)
	  			SoundEffect.playSound(player, Sound.BLOCK_STONE_BREAK, 0.8F, 1.0F);
			else if(itemID == 258 || itemID == 271 || itemID == 275 || itemID == 279 || itemID == 286)
	  			SoundEffect.playSound(player, Sound.BLOCK_WOOD_BREAK, 0.8F, 1.0F);
			else if(itemID == 267 || itemID == 268 || itemID == 272 || itemID == 276 || itemID == 283)
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR,1.0F, 2.0F);
			else if(itemID == 346)
				SoundEffect.playSound(player, Sound.ENTITY_GENERIC_SWIM,1.5F, 1.0F);
			else if(itemID == 359)
				SoundEffect.playSound(player, Sound.ENTITY_SHEEP_SHEAR,1.5F, 1.0F);
			else if(itemID == 368)
				SoundEffect.playSound(player, Sound.ENTITY_ENDERMEN_TELEPORT,1.0F, 1.0F);
			else if(itemID == 373)
				SoundEffect.playSound(player, Sound.ENTITY_GENERIC_DRINK,1.0F, 1.0F);
			else if(itemID == 374)
				SoundEffect.playSound(player, Sound.ITEM_BOTTLE_FILL,1.0F, 1.0F);
			else if(itemID == 437)
				SoundEffect.playSound(player, Sound.ITEM_BOTTLE_FILL_DRAGONBREATH,1.0F, 1.0F);
			else if(itemID == 438)
				SoundEffect.playSound(player, Sound.ENTITY_SPLASH_POTION_BREAK,1.0F, 1.0F);
			else if(itemID == 441)
				SoundEffect.playSound(player, Sound.ENTITY_WITCH_DRINK,1.0F, 1.0F);
			else
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP,0.8F, 1.0F);
		}
		return;
	}
}
