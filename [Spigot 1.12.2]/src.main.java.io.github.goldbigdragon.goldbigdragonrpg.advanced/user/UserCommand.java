package user;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import effect.SoundEffect;
import main.MainServerOption;
import servertick.ServerTickMain;
import util.YamlLoader;

public class UserCommand
{
	public void onCommand(Player player, String[] args, String string)
	{
		
		switch(string)
		{
		case "수락":
		{
			if(ServerTickMain.PlayerTaskList.containsKey(player.getName()))
			{
				long UTC = Long.parseLong(ServerTickMain.PlayerTaskList.get(player.getName()));
				servertick.ServerTickObject STSO = ServerTickMain.Schedule.get(UTC);
				String taskType = STSO.getType();
				
				switch(taskType)
				{
					case "P_EC"://Player Exchange
					{
						if(STSO.getString((byte)1).equals(player.getName()))
						{
							SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.7F);
							SoundEffect.playSound(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.7F);
							new user.EquipGui().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)0)), Bukkit.getServer().getPlayer(STSO.getString((byte)1)),null,false,false);
							new user.EquipGui().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Bukkit.getServer().getPlayer(STSO.getString((byte)0)),null,false,false);
							ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)0));
							ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)1));
							ServerTickMain.Schedule.remove(UTC);
						}
					}
					break;
				}
			}
		}
		return;
		case "거절":
		{
			if(ServerTickMain.PlayerTaskList.containsKey(player.getName()))
			{
				servertick.ServerTickObject STSO = ServerTickMain.Schedule.get(Long.parseLong(ServerTickMain.PlayerTaskList.get(player.getName())));
				String taskType = STSO.getType();
				switch(taskType)
				{
					case "P_EC"://Player Exchange
					{
						if(STSO.getString((byte)1).equals(player.getName()))
						{
							servertick.ServerTaskPlayer SP = new servertick.ServerTaskPlayer();
							SP.Cancel(STSO.getTick(), (short) 0);
						}
					}
					break;
				}
			}
		}
		return;
		case "돈":
  		{
  			long Money = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
  			if(args.length == 0)
  			{
			 	SoundEffect.playSound(player, org.bukkit.Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
			 	player.sendMessage("§e[현재 소지 금액] §f§l" +Money + " "+MainServerOption.money);
			 	player.sendMessage("§6/돈 꺼내기 [금액]§f 해당 금액 만큼 돈을 아이템으로 꺼냅니다.");
			 	player.sendMessage("§6/돈 랭킹 [닉네임]§f 해당 플레이어의 랭킹을 확인합니다.");
  				if(player.isOp()==true)
	  				player.sendMessage("§b/돈 주기 [금액] [플레이어]§f 해당 금액 만큼 플레이어에게 돈을 줍니니다.§b§l(관리자)");
  			}
  			else if(args.length == 2&&args[0].equals("꺼내기"))
  			{
				try
				{
					if(args[1].length() >= 1 && Integer.parseInt(args[1]) >= 1&& Integer.parseInt(args[1]) <= 100000000)
					{
						if(Money >= Integer.parseInt(args[1]))
						{
							for(int count = 0; count < 36;count++)
							{
								if(player.getInventory().getItem(count)==null)
								{
									int money = Integer.parseInt(args[1]);
									main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * money, 0, false);
									ItemStack Icon;
									if(money <= 50)
										Icon = new MaterialData(MainServerOption.Money1ID, (byte) MainServerOption.Money1DATA).toItemStack();
									else if(money <= 100)
										Icon = new MaterialData(MainServerOption.Money2ID, (byte) MainServerOption.Money2DATA).toItemStack();
									else if(money <= 1000)
										Icon = new MaterialData(MainServerOption.Money3ID, (byte) MainServerOption.Money3DATA).toItemStack();
									else if(money <= 10000)
										Icon = new MaterialData(MainServerOption.Money4ID, (byte) MainServerOption.Money4DATA).toItemStack();
									else if(money <= 50000)
										Icon = new MaterialData(MainServerOption.Money5ID, (byte) MainServerOption.Money5DATA).toItemStack();
									else
										Icon = new MaterialData(MainServerOption.Money6ID, (byte) MainServerOption.Money6DATA).toItemStack();
									Icon.setAmount(1);
									ItemMeta Icon_Meta = Icon.getItemMeta();
									Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
									Icon_Meta.setDisplayName(MainServerOption.money);
									StringBuffer MoneyString = new StringBuffer();
									short Mok = 0;
									if(money==100000000||money==10000000||money==1000000||
										money==100000||money==10000||money==1000||money==100||
										money==10)
									{
										switch(money)
										{
											case 100000000:
												MoneyString.append("1억");
												break;
											case 10000000:
												MoneyString.append("1천만");
												break;
											case 1000000:
												MoneyString.append("1백만");
												break;
											case 100000:
												MoneyString.append("1십만");
												break;
											case 10000:
												MoneyString.append("1만");
												break;
											case 1000:
												MoneyString.append("1천");
												break;
											case 100:
												MoneyString.append("1백");
												break;
											case 10:
												MoneyString.append("1십");
												break;
										}
									}
									else
									{
										if(money >= 10000000)
										{
											Mok = (short) (money / 10000000);
											MoneyString.append(Mok+"천");
											money = money-(Mok*10000000);
										}
										if(money >= 1000000)
										{
											Mok = (short) (money / 1000000);
											MoneyString.append(Mok+"백");
											money = money-(Mok*1000000);
										}
										if(money >= 100000)
										{
											Mok = (short) (money / 100000);
											MoneyString.append(Mok+"십");
											money = money-(Mok*100000);
										}
										if(money >= 10000)
										{
											Mok = (short) (money / 10000);
											MoneyString.append(Mok+"만 ");
											money = money-(Mok*10000);
										}
										else if(Integer.parseInt(args[1]) >= 10000)
										{
											MoneyString.append("만 ");
										}
										if(money >= 1000)
										{
											Mok = (short) (money / 1000);
											MoneyString.append(Mok+"천");
											money = money-(Mok*1000);
										}
										if(money >= 100)
										{
											Mok = (short) (money / 100);
											MoneyString.append(Mok+"백");
											money = money-(Mok*100);
										}
										if(money >= 10)
										{
											Mok = (short) (money / 10);
											MoneyString.append(Mok+"십");
											money = money-(Mok*10);
										}
										if(money >= 1)
										{
											Mok = (short) (money / 1);
											MoneyString.append(Mok);
										}
									}
									Icon_Meta.setLore(Arrays.asList("§e[돈]             §f[일반]","§f§l"+args[1]+" "+MainServerOption.money,"§7("+MoneyString.toString()+" "+ChatColor.stripColor(MainServerOption.money)+")","","§7우 클릭시 내 계좌로","§7입금됩니다."));
									Icon.setItemMeta(Icon_Meta);
									player.getInventory().addItem(Icon);
									SoundEffect.playSound(player, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
									player.sendMessage("§a[System] : §f§l"+args[1]+" "+MainServerOption.money+"§a 을(를) 꺼냈습니다!");
									return;
								}
							}
							SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
							player.sendMessage("§c[System] : 인벤토리 공간이 부족합니다!");
						}
						else
						{
							player.sendMessage("§c[SYSTEM] : 현재 보유 금액을 초과하는 값입니다!");
							SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
						}
					}
					else
					{
						player.sendMessage("§c[SYSTEM] : 최소 §e"+1+"§c, 최대 §e"+100000000+"§c 이하의 숫자를 입력하세요!");
						SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					}
				}
				catch(NumberFormatException e)
				{
					player.sendMessage("§c[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. (§e"+1+"§c ~ §e"+100000000+"§c)");
					SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				}
  			}
  			else if(args.length == 3&&args[0].equals("주기")&&player.isOp())
  			{
  				if(Bukkit.getServer().getPlayer(args[2]) != null)
  				{
  					Player target = Bukkit.getServer().getPlayer(args[2]);
	  				if(target.isOnline())
	  				{
						try
						{
							if(args[1].length() >= 1 && Integer.parseInt(args[1]) >= 1&& Integer.parseInt(args[1]) <= 100000000)
							{
								for(int count = 0; count < 36;count++)
								{
									if(target.getInventory().getItem(count)==null)
									{
										int money = Integer.parseInt(args[1]);
										ItemStack Icon;
										if(money <= 50)
											Icon = new MaterialData(MainServerOption.Money1ID, (byte) MainServerOption.Money1DATA).toItemStack();
										else if(money <= 100)
											Icon = new MaterialData(MainServerOption.Money2ID, (byte) MainServerOption.Money2DATA).toItemStack();
										else if(money <= 1000)
											Icon = new MaterialData(MainServerOption.Money3ID, (byte) MainServerOption.Money3DATA).toItemStack();
										else if(money <= 10000)
											Icon = new MaterialData(MainServerOption.Money4ID, (byte) MainServerOption.Money4DATA).toItemStack();
										else if(money <= 50000)
											Icon = new MaterialData(MainServerOption.Money5ID, (byte) MainServerOption.Money5DATA).toItemStack();
										else
											Icon = new MaterialData(MainServerOption.Money6ID, (byte) MainServerOption.Money6DATA).toItemStack();
										Icon.setAmount(1);
										ItemMeta Icon_Meta = Icon.getItemMeta();
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
										Icon_Meta.setDisplayName(MainServerOption.money);
										StringBuffer MoneyString = new StringBuffer();
										short Mok = 0;
										if(money==100000000||money==10000000||money==1000000||
											money==100000||money==10000||money==1000||money==100||
											money==10)
										{
											switch(money)
											{
												case 100000000:
													MoneyString.append("1억");
													break;
												case 10000000:
													MoneyString.append("1천만");
													break;
												case 1000000:
													MoneyString.append("1백만");
													break;
												case 100000:
													MoneyString.append("1십만");
													break;
												case 10000:
													MoneyString.append("1만");
													break;
												case 1000:
													MoneyString.append("1천");
													break;
												case 100:
													MoneyString.append("1백");
													break;
												case 10:
													MoneyString.append("1십");
													break;
											}
										}
										else
										{
											if(money >= 10000000)
											{
												Mok = (short) (money / 10000000);
												MoneyString.append(Mok+"천");
												money = money-(Mok*10000000);
											}
											if(money >= 1000000)
											{
												Mok = (short) (money / 1000000);
												MoneyString.append(Mok+"백");
												money = money-(Mok*1000000);
											}
											if(money >= 100000)
											{
												Mok = (short) (money / 100000);
												MoneyString.append(Mok+"십");
												money = money-(Mok*100000);
											}
											if(money >= 10000)
											{
												Mok = (short) (money / 10000);
												MoneyString.append(Mok+"만 ");
												money = money-(Mok*10000);
											}
											else if(Integer.parseInt(args[1]) >= 10000)
											{
												MoneyString.append("만 ");
											}
											if(money >= 1000)
											{
												Mok = (short) (money / 1000);
												MoneyString.append(Mok+"천");
												money = money-(Mok*1000);
											}
											if(money >= 100)
											{
												Mok = (short) (money / 100);
												MoneyString.append(Mok+"백");
												money = money-(Mok*100);
											}
											if(money >= 10)
											{
												Mok = (short) (money / 10);
												MoneyString.append(Mok+"십");
												money = money-(Mok*10);
											}
											if(money >= 1)
											{
												Mok = (short) (money / 1);
												MoneyString.append(Mok);
											}
										}
										Icon_Meta.setLore(Arrays.asList("§e[돈]             §f[일반]","§f§l"+args[1]+" "+MainServerOption.money,"§7("+MoneyString.toString()+" "+ChatColor.stripColor(MainServerOption.money)+")","","§7우 클릭시 내 계좌로","§7입금됩니다."));
										Icon.setItemMeta(Icon_Meta);
										target.getInventory().addItem(Icon);
										SoundEffect.playSound(target, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
										target.sendMessage("§a[System] : 관리자로 부터 §f§l"+args[1]+" "+MainServerOption.money+"§a 을(를) 받았습니다!");
										SoundEffect.playSound(player, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
										player.sendMessage("§a[System] : "+target.getName()+"에게 §f§l"+args[1]+" "+MainServerOption.money+"§a 을(를) 주었습니다!");
										return;
									}
								}
								SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
								player.sendMessage("§c[System] : 인벤토리 공간이 부족합니다!");
								return;
							}
							else
							{
								player.sendMessage("§c[SYSTEM] : 최소 §e"+1+"§c, 최대 §e"+100000000+"§c 이하의 숫자를 입력하세요!");
								SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
							}
						}
						catch(NumberFormatException e)
						{
							player.sendMessage("§c[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. (§e"+1+"§c ~ §e"+100000000+"§c)");
							SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
						}
	  				}
	  				else
	  				{
						player.sendMessage("§c[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
						SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  				}
  				}
  				else
  				{
					player.sendMessage("§c[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
					SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
  				}
  			}
  			else if(args[0].equals("랭킹"))
  			{
    		  	YamlLoader YAML = new YamlLoader();
    			YAML.getConfig("Ranking/money.yml");
				if(YAML.contains("Rank")&&YAML.getConfigurationSection("Rank").getKeys(false).size()>0)
				{
	  				if(args.length == 2)
	  				{
	  					if(YAML.contains("NameSet."+args[1]))
	  	  					player.sendMessage("§e§l┼─[§a§l"+ (YAML.getInt("NameSet."+args[1]+".Rank")+1) +"§e§l] §f§l"+args[1]+ " §6("+YAML.getLong("NameSet."+args[1]+".Money")+")");
	  	  				else
	  	  				{
	  						player.sendMessage("§c[SYSTEM] : 해당 플레이어에 대한 자료가 없습니다!");
	  						SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  	  				}
	  				}
	  				else
	  				{
  	  					int rankSize = YAML.getConfigurationSection("Rank").getKeys(false).size();
  	  					if(rankSize > 5)
  	  						rankSize = 5;
  	  					player.sendMessage("§e§l┌────────────[Ranking]────────────┐");
	  	  				for(int count = 0 ; count < rankSize; count++)
	  	  					player.sendMessage("§e§l├─[§a§l"+ (count+1) +"§e§l] §f§l"+YAML.getString("Rank."+count+".Name") + " §6("+YAML.getLong("Rank."+count+".Money")+")");
  	  					player.sendMessage("§e§l└────────────[Ranking]────────────┘");
	  				}
				}
				else
				{
					player.sendMessage("§c[SYSTEM] : 랭킹을 불러 올 수가 없습니다! 잠시 후 다시 시도 해 주시길 바랍니다.");
					SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				}
  			}
  			else
  			{
  				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.8F);
  				player.sendMessage("§6/돈§f 현재 자신이 보유한 금액을 확인합니다.");
  				player.sendMessage("§6/돈 꺼내기 [금액]§f 해당 금액 만큼 돈을 아이템으로 꺼냅니다.");
			 	player.sendMessage("§6/돈 랭킹 [닉네임]§f 해당 플레이어의 랭킹을 확인합니다.");
  				if(player.isOp()==true)
	  				player.sendMessage("§b/돈 주기 [금액] [플레이어]§f 해당 금액 만큼 플레이어에게 돈을 줍니니다.§b§l(관리자)");
  			}
  		}
  		return;
		}
	}
}
