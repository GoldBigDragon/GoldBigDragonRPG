package GoldBigDragon_RPG.Structure;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.Main.UserDataObject;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;
import net.md_5.bungee.api.ChatColor;

public class Action
{
	public void PlayerChatrouter(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
		UserDataObject u = new UserDataObject();
		if(u.getType(player).compareTo("Post")==0)
			PostChatting(event);
		else if(u.getType(player).compareTo("Board")==0)
			BoardChatting(event);
		else if(u.getType(player).compareTo("TradeBoard")==0)
			TradeBoardChatting(event);
		else if(u.getType(player).compareTo("CampFire")==0)
			CampFireChatting(event);
		
	}
	
	
	private void PostChatting(PlayerChatEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		UserDataObject u = new UserDataObject();
		String Message = ChatColor.stripColor(event.getMessage());
		//Reciever NickName
		if(u.getString(player,(byte)0).compareTo("RN")==0)
		{
			if(Message.compareTo(ChatColor.stripColor(player.getName()))==0)
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
				player.sendMessage(ChatColor.RED+"[우편] : 자기 자신에게는 보낼 수 없습니다!");
				return;
			}
				
			if(Bukkit.getOfflinePlayer(Message)!=null)
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				u.setString(player, (byte)0, "Title");
				u.setString(player, (byte)1, Message);
				u.setTemp(player,"Structure");
				player.sendMessage(ChatColor.GREEN+"[우편] : 우편 제목을 입력 하세요.");
			}
			else
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
				player.sendMessage(ChatColor.RED+"[우편] : 해당 닉네임을 가진 플레이어가 없습니다!");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("Title")==0)
		{
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setString(player, (byte)0, "Memo");
			u.setString(player, (byte)2, event.getMessage());
			player.sendMessage(ChatColor.GREEN+"[우편] : 우편 내용을 입력 하세요.");
			u.setTemp(player,"Structure");
		}
		else if(u.getString(player,(byte)0).compareTo("Memo")==0)
		{
			s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.5F);
			u.setString(player, (byte)3,ChatColor.WHITE+event.getMessage());
			u.setItemStack(player, null);
			new Structure_PostBox().ItemPutterGUI(player);
		}
		else if(u.getString(player,(byte)0).compareTo("Value")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 2000000))
			{
				u.setInt(player, (byte)0, Integer.parseInt(event.getMessage()));
				new GoldBigDragon_RPG.Structure.Structure_PostBox().SendPost(player);
			}
			else
			{
				player.sendMessage(ChatColor.GREEN+"[우편] : 우편물 수령을 위한 대금을 입력 하세요.");
				u.setTemp(player,"Structure");
			}
		}
	}

	private void BoardChatting(PlayerChatEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		UserDataObject u = new UserDataObject();
		String Message = ChatColor.stripColor(event.getMessage());
		
		//Board_PostTitle
		if(u.getString(player,(byte)0).compareTo("Title")==0)
		{
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setString(player, (byte)0, "Memo");
			u.setString(player, (byte)1, ChatColor.WHITE+event.getMessage());
			player.sendMessage(ChatColor.GREEN+"[게시판] : 게시글 내용을 입력 하세요.");
			u.setTemp(player,"Structure");
		}
		else if(u.getString(player,(byte)0).compareTo("Memo")==0)
		{
			s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.5F);
			u.setString(player, (byte)2,ChatColor.WHITE+event.getMessage());
			YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
			YamlManager Board =YC_2.getNewConfig("Structure/"+u.getString(player, (byte)3)+".yml");
			Board.set("User."+Board.getInt("Post_Number")+".User", player.getName());
			Board.set("User."+Board.getInt("Post_Number")+".Title", u.getString(player, (byte)1));
			Board.set("User."+Board.getInt("Post_Number")+".Memo", u.getString(player, (byte)2));
			Board.set("User."+Board.getInt("Post_Number")+".UTC", new GoldBigDragon_RPG.ServerTick.ServerTickMain().nowUTC);
			Board.set("Post_Number", Board.getInt("Post_Number")+1);
			Board.saveConfig();
			new Structure_Board().BoardMainGUI(player, u.getString(player, (byte)3), 0);
			u.clearAll(player);
		}
		else if(u.getString(player,(byte)0).compareTo("Notice")==0)
		{
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setString(player, (byte)2,ChatColor.WHITE+event.getMessage());
			YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
			YamlManager Board =YC_2.getNewConfig("Structure/"+u.getString(player, (byte)1)+".yml");
			Board.set("Notice", event.getMessage());
			Board.saveConfig();
			new Structure_Board().BoardSettingGUI(player, u.getString(player, (byte)1));
			u.clearAll(player);
		}
	}
	
	private void TradeBoardChatting(PlayerChatEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		UserDataObject u = new UserDataObject();
		String Message = ChatColor.stripColor(event.getMessage());
		
		if(u.getString(player,(byte)0).compareTo("Notice")==0)
		{
			if(askOX(Message, player)==1)
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				if(u.getInt(player, (byte)0)==1)//거래 타입이 판매일 경우
				{
					new Structure_TradeBoard().SelectSellItemGUI(player);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 판매할 아이템을 선택하세요.");
				}
				else if(u.getInt(player, (byte)0)==3)//거래 타입이 구매일 경우
				{
					new Structure_TradeBoard().SelectBuyItemGUI(player);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 구매할 아이템을 선택하세요.");
				}
				else //거래 타입이 교환일 경우
				{
					new Structure_TradeBoard().SelectExchangeItem_YouGUI(player);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 상대방이 나에게 줘야하는 아이템을 선택하세요.");
				}
				u.setString(player, (byte)0, "SelectItem");
			}
			else if(Message.compareTo("아니오")==0||Message.compareTo("x")==0
				||Message.compareTo("X")==0)
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 물품 등록이 취소되었습니다.");
				u.clearAll(player);
				return;
			}
		}
		else if(u.getString(player,(byte)0).compareTo("SetNeedAmount")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 1, 1000))
			{
				if(u.getInt(player, (byte)0)==5)
				{
					u.setInt(player, (byte) 2, Integer.parseInt(event.getMessage()));
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 당신은 무엇을 주실건가요?");
					u.setString(player, (byte)0, "SetMyItem");
					new Structure_TradeBoard().SelectExchangeItem_MyGUI(player);
					return;
				}
				u.setInt(player, (byte) 2, Integer.parseInt(event.getMessage()));
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 1개당 얼마에 구매 하실건가요? (0 ~ 2백만)");
				u.setString(player, (byte)0, "SetPrice");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("SetPrice")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, 2000000))
			{
				u.setInt(player, (byte)1, Integer.parseInt(event.getMessage()));

				//판매할 경우
				if(u.getInt(player, (byte)0)==3)
				{
					YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
					YamlManager Board =YC_2.getNewConfig("Structure/UserShopBoard.yml");
					YamlManager Pstat =YC_2.getNewConfig("Stats/"+player.getUniqueId().toString()+".yml");
					if(Pstat.getLong("Stat.Money")<Board.getInt("RegisterCommission"))
					{
						u.clearAll(player);
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 등록 수수료가 부족합니다! 재 등록 해 주세요!");
						return;
					}
					ItemStack itemAmountOne = u.getItemStack(player);
					String ItemName = u.getItemStack(player).getType().name()+"%d%"+itemAmountOne.getData().getData();
					if(u.getItemStack(player).hasItemMeta())
						if(u.getItemStack(player).getItemMeta().hasDisplayName())
							ItemName = u.getItemStack(player).getItemMeta().getDisplayName()+"%d%"+itemAmountOne.getData().getData();

					ItemName = ItemName.replace(":","");
					ItemName = ItemName.replace(".","");
					ItemName = ItemName.replace("[","");
					ItemName = ItemName.replace("]","");
					if(u.getItemStack(player).hasItemMeta())
					{
						if(u.getItemStack(player).getItemMeta().hasLore())
							ItemName = ItemName+u.getItemStack(player).getItemMeta().getLore().toString().length();
						else
							ItemName = ItemName+0;
					}
					else
						ItemName = ItemName+0;
					if(Board.contains("Sell."+ItemName+"."+player.getUniqueId().toString()))
					{
						u.clearAll(player);
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 동일 상품을 이미 등록하셨습니다!");
						return;
					}
					if(Board.contains("Sell."+ItemName)==false)
						Board.set("Sell."+ItemName+".Item", itemAmountOne);
					Board.set("SellRegistered", Board.getInt("SellRegistered")+1);
					Board.set("Sell."+ItemName+"."+player.getUniqueId().toString()+".Name", player.getName());
					Board.set("Sell."+ItemName+"."+player.getUniqueId().toString()+".Price", u.getInt(player, (byte)1));
					Board.set("Sell."+ItemName+"."+player.getUniqueId().toString()+".Amount", u.getInt(player,(byte)2));
					Board.saveConfig();
					

					Pstat.set("Stat.Money", Pstat.getLong("Stat.Money")-Board.getInt("RegisterCommission"));
					Pstat.saveConfig();
					YamlManager USRL =YC_2.getNewConfig("Structure/UserShopRegisterList.yml");
					USRL.set(player.getUniqueId().toString(), USRL.getInt(player.getUniqueId().toString())+1);
					USRL.saveConfig();
					u.clearAll(player);
					s.SP(player, Sound.CHEST_OPEN, 1.0F, 1.8F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 등록이 완료되었습니다!");
					return;
				}
				for(int count = 0; count < player.getInventory().getSize(); count++)
				{
					if(player.getInventory().getItem(count)!=null)
						if(player.getInventory().getItem(count).equals(u.getItemStack(player)))
						{
							YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
							YamlManager Board =YC_2.getNewConfig("Structure/UserShopBoard.yml");
							YamlManager Pstat =YC_2.getNewConfig("Stats/"+player.getUniqueId().toString()+".yml");
							if(Pstat.getLong("Stat.Money")<Board.getInt("RegisterCommission"))
							{
								u.clearAll(player);
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
								player.sendMessage(ChatColor.RED+"[거래 게시판] : 등록 수수료가 부족합니다! 재 등록 해 주세요!");
								return;
							}
							//구매할 경우
							if(u.getInt(player, (byte)0)==1)
							{
								ItemStack itemAmountOne = u.getItemStack(player);
								String ItemName = u.getItemStack(player).getType().name()+"%d%"+itemAmountOne.getData().getData();
								if(u.getItemStack(player).hasItemMeta())
									if(u.getItemStack(player).getItemMeta().hasDisplayName())
										ItemName = u.getItemStack(player).getItemMeta().getDisplayName()+"%d%"+itemAmountOne.getData().getData();

								ItemName = ItemName.replace(":","");
								ItemName = ItemName.replace(".","");
								ItemName = ItemName.replace("[","");
								ItemName = ItemName.replace("]","");
								if(u.getItemStack(player).hasItemMeta())
								{
									if(u.getItemStack(player).getItemMeta().hasLore())
										ItemName = ItemName+u.getItemStack(player).getItemMeta().getLore().toString().length();
									else
										ItemName = ItemName+0;
								}
								else
									ItemName = ItemName+0;
								itemAmountOne.setAmount(1);
								if(Board.contains("Buy."+ItemName+"."+player.getUniqueId().toString()))
								{
									u.clearAll(player);
									s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
									player.sendMessage(ChatColor.RED+"[거래 게시판] : 동일 상품을 이미 등록하셨습니다!");
									return;
								}
								if(Board.contains("Buy."+ItemName)==false)
									Board.set("Buy."+ItemName+".Item", itemAmountOne);
								Board.set("BuyRegistered", Board.getInt("BuyRegistered")+1);
								Board.set("Buy."+ItemName+"."+player.getUniqueId().toString()+".Name", player.getName());
								Board.set("Buy."+ItemName+"."+player.getUniqueId().toString()+".Price", u.getInt(player, (byte)1));
								Board.set("Buy."+ItemName+"."+player.getUniqueId().toString()+".Amount", u.getItemStack(player).getAmount());
								Board.saveConfig();
							}
							Pstat.set("Stat.Money", Pstat.getLong("Stat.Money")-Board.getInt("RegisterCommission"));
							Pstat.saveConfig();
							YamlManager USRL =YC_2.getNewConfig("Structure/UserShopRegisterList.yml");
							USRL.set(player.getUniqueId().toString(), USRL.getInt(player.getUniqueId().toString())+1);
							USRL.saveConfig();
							player.getInventory().setItem(count, null);
							u.clearAll(player);
							s.SP(player, Sound.CHEST_OPEN, 1.0F, 1.8F);
							player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 등록이 완료되었습니다!");
							return;
						}
				}
				u.clearAll(player);
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.RED+"[거래 게시판] : 등록할 아이템이 없습니다! 재 등록 해 주세요!");
			}
		}
		else if(u.getString(player,(byte)0).compareTo("TradeBoardSetting")==0)
		{
			String SettingType = u.getString(player, (byte)1);
			YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
			YamlManager Board =YC_2.getNewConfig("Structure/UserShopBoard.yml");
			if(SettingType.compareTo("SellCommission")==0)
			{
				if(isIntMinMax(event.getMessage(), player, 0, 100))
					Board.set(SettingType, Integer.parseInt(event.getMessage()));
			}
			else if(SettingType.compareTo("RegisterCommission")==0)
			{
				if(isIntMinMax(event.getMessage(), player, 0, 2000000))
					Board.set(SettingType, Integer.parseInt(event.getMessage()));
			}
			else if(SettingType.compareTo("LimitPerPlayer")==0)
			{
				if(isIntMinMax(event.getMessage(), player, 1, 100))
					Board.set(SettingType, Integer.parseInt(event.getMessage()));
			}
			Board.saveConfig();
			new GoldBigDragon_RPG.Structure.Structure_TradeBoard().TradeBoardSettingGUI(player);
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.clearAll(player);
		}
		else if(u.getString(player,(byte)0).compareTo("BuyTrade")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, u.getInt(player, (byte)0)))
			{
				int needAmount = Integer.parseInt(ChatColor.stripColor(event.getMessage()));
				if(needAmount==0)
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 거래가 취소되었습니다.");
					u.clearAll(player);
					return;
				}
				else
				{
					YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
					YamlManager PlayerStat =YC_2.getNewConfig("Stats/"+player.getUniqueId().toString()+".yml");
					YamlManager Board =YC_2.getNewConfig("Structure/UserShopBoard.yml");
					int ExitAmount = Board.getInt("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount");
					if(Board.contains("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2))==false)
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 거래 정보가 바뀌었습니다! 재 시도 해 주시길 바랍니다!");
						u.clearAll(player);
						return;
					}
					else if(ExitAmount<needAmount)
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 거래 정보가 바뀌었습니다! 재 시도 해 주시길 바랍니다!");
						u.clearAll(player);
						return;
					}
					else
					{
						int Price = Board.getInt("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Price");
						if(Price*needAmount <= PlayerStat.getLong("Stat.Money"))
						{
							Price = Price*needAmount;
							ItemStack item = Board.getItemStack("Buy."+u.getString(player, (byte)1)+".Item");
							int SellCommission = Board.getInt("SellCommission");
							int MinusSellCommission = (int)((Price/100.0) * SellCommission);
							Player Target = Bukkit.getPlayer(Board.getString("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Name"));
							
							if(ExitAmount-needAmount ==0)
							{
								if(Board.getConfigurationSection("Buy."+u.getString(player, (byte)1)).getKeys(false).size()==2)
									Board.removeKey("Buy."+u.getString(player, (byte)1));
								else
									Board.removeKey("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2));
								Board.set("BuyRegistered", Board.getInt("BuyRegistered")-1);
								YamlManager USRL =YC_2.getNewConfig("Structure/UserShopRegisterList.yml");
								if(USRL.contains(player.getUniqueId().toString())==true)
								{
									USRL.set(Target.getUniqueId().toString(), USRL.getInt(Target.getUniqueId().toString())-1);
									USRL.saveConfig();
								}
							}
							else
								Board.set("Buy."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount", ExitAmount-needAmount);
							Board.saveConfig();
							PlayerStat.set("Stat.Money",PlayerStat.getLong("Stat.Money")-Price);
							PlayerStat.saveConfig();
							
							YamlManager TargetStat =YC_2.getNewConfig("Stats/"+u.getString(player, (byte)2)+".yml");
							if(TargetStat.getLong("Stat.Money") + Price-MinusSellCommission >= 2000000000)
								TargetStat.set("Stat.Money",2000000000);
							else
								TargetStat.set("Stat.Money",TargetStat.getLong("Stat.Money")+(Price-MinusSellCommission));
							TargetStat.saveConfig();
							new GoldBigDragon_RPG.Structure.Structure_PostBox().SendPost_Server(u.getString(player, (byte)2), "[거래 게시판]", "[물품 판매 영수증]", 
							player.getName()+"님께서 ["+u.getString(player, (byte)3)+"] 아이템을 "+needAmount+"개 구매하여 "+Price+" "+ChatColor.WHITE+ChatColor.stripColor(GoldBigDragon_RPG.Main.ServerOption.Money)+ChatColor.WHITE+"의 이익을 냈으며, "
							+MinusSellCommission+" "+ChatColor.WHITE+ChatColor.stripColor(GoldBigDragon_RPG.Main.ServerOption.Money)+" 만큼의 수수료를 지불하였습니다.", null);
							if(Target!=null)
								if(Target.isOnline())
								{
									s.SP(Target, Sound.VILLAGER_YES, 1.0F, 1.0F);
									new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(Target, "\'§3[거래 성사]\'","\'§3거래 게시판에 등록한 물품이 판매되었습니다.\'", 1, 3, 1);
								}
							if(needAmount!=1)
								item.setAmount(needAmount);
							if(new GoldBigDragon_RPG.ETC.Items().GiveItem(player, item)==false)
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.YELLOW+"[거래 게시판] : 인벤토리가 부족하여 우편함으로 아이템을 발송하였습니다!");
								new GoldBigDragon_RPG.Structure.Structure_PostBox().SendPost_Server(player.getUniqueId().toString(), "[거래 게시판]", "[인벤토리 부족]", "거래 이후 인벤토리가 부족하여 우편으로 아이템이 배송되었습니다.", item);
							}
						}
						else
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.0F);
							player.sendMessage(ChatColor.RED+"[거래 게시판] : 소지금이 부족합니다!");
							player.sendMessage(ChatColor.RED+"[필요 금액 : "+(Price*needAmount)+" "+GoldBigDragon_RPG.Main.ServerOption.Money+ChatColor.RED+"]");
							player.sendMessage(ChatColor.RED+"[소지 금액 : "+PlayerStat.getLong("Stat.Money")+" "+GoldBigDragon_RPG.Main.ServerOption.Money+ChatColor.RED+"]");
						}
						u.clearAll(player);
						return;
					}
				}
			}
		}
		else if(u.getString(player,(byte)0).compareTo("SellTrade")==0)
		{
			if(isIntMinMax(event.getMessage(), player, 0, u.getInt(player, (byte)0)))
			{
				int needAmount = Integer.parseInt(ChatColor.stripColor(event.getMessage()));
				if(needAmount==0)
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					player.sendMessage(ChatColor.GREEN+"[거래 게시판] : 거래가 취소되었습니다.");
					u.clearAll(player);
					return;
				}
				else
				{
					YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
					YamlManager Board =YC_2.getNewConfig("Structure/UserShopBoard.yml");
					Player Target = Bukkit.getPlayer(Board.getString("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Name"));
					int itemcount = 0;
					ItemStack BoardAddedItem = Board.getItemStack("Sell."+u.getString(player, (byte)1)+".Item");
					for(int count=0; count<player.getInventory().getSize();count++)
					{
						ItemStack PlayerHave = player.getInventory().getItem(count);
						if(PlayerHave!=null)
						{
							int amount = PlayerHave.getAmount();
							PlayerHave.setAmount(1);
							if(PlayerHave.equals(BoardAddedItem))
								itemcount= itemcount+amount;
							PlayerHave.setAmount(amount);
						}
					}
					if(itemcount<needAmount)
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 물건을 충분히 가지고 있지 않습니다!");
						u.clearAll(player);
						return;
					}
					if(needAmount > Board.getInt("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount"))
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 거래 정보가 바뀌었습니다! 재 시도 해 주시길 바랍니다!");
						u.clearAll(player);
						return;
					}
					int rawprice = Board.getInt("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Price");
					long price = rawprice*needAmount;
					YamlManager TargetStat =YC_2.getNewConfig("Stats/"+u.getString(player, (byte)2)+".yml");
					if(TargetStat.getLong("Stat.Money")<price)
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[거래 게시판] : 판매자의 계좌 잔고가 부족하여 거래가 취소되었습니다!");
						u.clearAll(player);
						return;
					}
					YamlManager PlayerStat =YC_2.getNewConfig("Stats/"+player.getUniqueId().toString()+".yml");
					if(PlayerStat.getLong("Stat.Money")+price>=2000000000)
						PlayerStat.set("Stat.Money",2000000000);
					else
						PlayerStat.set("Stat.Money", PlayerStat.getLong("Stat.Money")+price);
					TargetStat.set("Stat.Money", TargetStat.getLong("Stat.Money")-price);
					PlayerStat.saveConfig();
					TargetStat.saveConfig();
					
					if(Board.getInt("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount")-needAmount ==0)
					{
						if(Board.getConfigurationSection("Sell."+u.getString(player, (byte)1)).getKeys(false).size()==2)
							Board.removeKey("Sell."+u.getString(player, (byte)1));
						else
							Board.removeKey("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2));
						Board.set("SellRegistered", Board.getInt("SellRegistered")-1);
						YamlManager USRL =YC_2.getNewConfig("Structure/UserShopRegisterList.yml");
						if(USRL.contains(player.getUniqueId().toString())==true)
						{
							USRL.set(Target.getUniqueId().toString(), USRL.getInt(Target.getUniqueId().toString())-1);
							USRL.saveConfig();
						}
					}
					else
						Board.set("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount", Board.getInt("Sell."+u.getString(player, (byte)1)+"."+u.getString(player, (byte)2)+".Amount")-needAmount);
					Board.saveConfig();
					if(needAmount>64)
					{
						int pack = needAmount/64;
						int trash = needAmount-(pack*64);
						BoardAddedItem.setAmount(64);
						for(int count=0;count<pack;count++)
							new GoldBigDragon_RPG.Structure.Structure_PostBox().SendPost_Server(u.getString(player, (byte)2), "[거래 게시판]", "[물품 구매 영수증]",
									player.getName()+"님께서 ["+u.getString(player, (byte)3)+"] 아이템을 64개 판매하여 "+(rawprice*64)+" "+ChatColor.stripColor(GoldBigDragon_RPG.Main.ServerOption.Money)+ChatColor.WHITE+"의 지출을 했습니다.", BoardAddedItem);
						if(trash!=0)
						{
							BoardAddedItem.setAmount(trash);
							new GoldBigDragon_RPG.Structure.Structure_PostBox().SendPost_Server(u.getString(player, (byte)2), "[거래 게시판]", "[물품 구매 영수증]",
									player.getName()+"님께서 ["+u.getString(player, (byte)3)+"] 아이템을 "+trash+"개 판매하여 "+(rawprice*trash)+" "+ChatColor.stripColor(GoldBigDragon_RPG.Main.ServerOption.Money)+ChatColor.WHITE+"의 지출을 했습니다.", BoardAddedItem);
						}
					}
					else
					{
						BoardAddedItem.setAmount(needAmount);
						new GoldBigDragon_RPG.Structure.Structure_PostBox().SendPost_Server(u.getString(player, (byte)2), "[거래 게시판]", "[물품 구매 영수증]",
								player.getName()+"님께서 ["+u.getString(player, (byte)3)+"] 아이템을 "+needAmount+"개 판매하여 "+price+" "+ChatColor.stripColor(GoldBigDragon_RPG.Main.ServerOption.Money)+ChatColor.WHITE+"의 지출을 했습니다.", BoardAddedItem);
					}
					if(Target!=null)
						if(Target.isOnline())
						{
							s.SP(Target, Sound.VILLAGER_YES, 1.0F, 1.0F);
							new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(Target, "\'§3[거래 성사]\'","\'§3거래 게시판에 의뢰한 물품이 도착하였습니다.\'", 1, 3, 1);
						}
					
					BoardAddedItem.setAmount(1);
					for(int count=0; count<player.getInventory().getSize();count++)
					{
						ItemStack PlayerHave = player.getInventory().getItem(count);
						if(PlayerHave!=null)
						{
							int amount = PlayerHave.getAmount();
							PlayerHave.setAmount(1);
							if(PlayerHave.equals(BoardAddedItem))
							{
								if(amount > needAmount)
								{
									PlayerHave.setAmount(amount-needAmount);
									player.getInventory().setItem(count, PlayerHave);
								}
								else
									player.getInventory().setItem(count, null);
								needAmount = needAmount - amount;
							}
							if(needAmount<=0)
								break;
						}
					}
					//플레이어 인벤토리에서 판매한 물품 삭제하기
				}
			}
		}//SellTrade 끝
	}
	
	private void CampFireChatting(PlayerChatEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = event.getPlayer();
		UserDataObject u = new UserDataObject();
		String Message = ChatColor.stripColor(event.getMessage());
		//Reciever NickName
		if(u.getString(player,(byte)0).compareTo("RN")==0)
		{
			if(Message.compareTo(ChatColor.stripColor(player.getName()))==0)
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				u.clearAll(player);
				player.sendMessage(ChatColor.RED+"[우편] : 자기 자신에게는 보낼 수 없습니다!");
				return;
			}
		}
	}
	
	private boolean isIntMinMax(String message,Player player, int Min, int Max)
	{
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		try
		{
			if(message.split(" ").length <= 1 && Integer.parseInt(message) >= Min&& Integer.parseInt(message) <= Max)
				return true;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 "+ChatColor.YELLOW +""+Min+ChatColor.RED+", 최대 "+ChatColor.YELLOW+""+Max+ChatColor.RED+" 이하의 숫자를 입력하세요!");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. ("+ChatColor.YELLOW +""+Min+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+Max+ChatColor.RED+")");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		}
		return false;
	}

	private byte askOX(String message,Player player)
	{
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		if(message.split(" ").length <= 1)
		{
			if(message.compareTo("x")==0||message.compareTo("X")==0||message.compareTo("아니오")==0)
				return 0;
			else if(message.compareTo("o")==0||message.compareTo("O")==0||message.compareTo("네")==0)
				return 1;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : [네/O] 혹은 [아니오/X]를 입력 해 주세요!");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			
		}
		else
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : [네/O] 혹은 [아니오/X]를 입력 해 주세요!");
			sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		}
		return -1;
	}
}
