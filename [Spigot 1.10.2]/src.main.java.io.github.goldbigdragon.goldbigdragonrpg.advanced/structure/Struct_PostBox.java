package structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import effect.SoundEffect;
import main.Main_ServerOption;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;

public class Struct_PostBox extends Util_GUI
{
	public void PostBoxMainGUI(Player player, byte Type)
	{
		YamlLoader PlayerPost = new YamlLoader();
		PlayerPost.getConfig("Post/"+player.getUniqueId().toString()+".yml");
		String UniqueCode = "§0§0§d§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§c§l우편함");
		if(Type==0)//받은 우편
		{
			if(PlayerPost.contains("Recieve")==false)
			{
				PlayerPost.createSection("Recieve");
				PlayerPost.saveConfig();
			}
			Object[] PostList = PlayerPost.getConfigurationSection("Recieve").getKeys(false).toArray();
			byte loc=2;
			for(int count = 0; count < PostList.length;count++)
			{
				if(count>=25)
					break;
				String PostFrom = PlayerPost.getString("Recieve."+PostList[count].toString()+".From");
				String PostTitle = PlayerPost.getString("Recieve."+PostList[count].toString()+".Title");
				String PostMemo = PlayerPost.getString("Recieve."+PostList[count].toString()+".Memo");
				ItemStack PostItem = PlayerPost.getItemStack("Recieve."+PostList[count].toString()+".Item");

				List<String> Memo = new ArrayList<String>();
				Memo.add("");
				Memo.add("§9제목 : §f"+PostTitle);
				Memo.add("");
				for(int count2=0;count2<(PostMemo.length()/20)+1;count2++)
				{
					if((count2+1)*20<PostMemo.length())
						Memo.add("§f"+PostMemo.substring(0+(count2*20), ((count2+1)*20)));
					else
						Memo.add("§f"+PostMemo.substring(0+(count2*20), PostMemo.length()));
				}
				Memo.add("");
				Memo.add("§9보낸 이 : §f"+PostFrom);
				if(PostItem==null)
				{
					Memo.add("§e[좌 클릭시 메시지 삭제]");
					Memo.add("§0"+PostList[count].toString());
					Stack2("§f[메시지]", 358,0,1,Memo, loc, inv);
				}
				else
				{
					int PostValue = PlayerPost.getInt("Recieve."+PostList[count].toString()+".Value");
					ItemMeta PIMeta = PostItem.getItemMeta();
					if(PostItem.hasItemMeta())
					{
						Memo.add("§9대금 청구 : §f"+PostValue);
						Memo.add("§e[좌 클릭시 물품 수령]");
						if(!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[반송]")
						&&!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[물품 회수]")
						&&!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[거래 게시판]")
						&&!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[이벤트]")
						&&!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[시스템]"))
							Memo.add("§c[우 클릭시 물품 반송]");
						Memo.add("§0"+PostList[count].toString());
					}
					else
					{
						Memo.add("§9대금 청구 : §f"+PostValue);
						Memo.add("§e[좌 클릭시 물품 수령]");
						if(!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[반송]")
						&&!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[물품 회수]")
						&&!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[거래 게시판]")
						&&!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[이벤트]")
						&&!PlayerPost.getString("Recieve."+PostList[count]+".From").equals("[시스템]"))
							Memo.add("§c[우 클릭시 물품 반송]");
						Memo.add("§0"+PostList[count].toString());
						PIMeta.setLore(Memo);
					}
					PIMeta.setLore(Memo);
					PostItem.setItemMeta(PIMeta);
					ItemStackStack(PostItem, loc, inv);
				}
				if(loc==6||loc==15||loc==24||loc==33||loc==42)
					loc = (byte) (loc+5);
				else
					loc++;
			}
		}
		else//보낸 우편
		{
			if(PlayerPost.contains("Send")==false)
			{
				PlayerPost.createSection("Send");
				PlayerPost.saveConfig();
			}
			Object[] PostList = PlayerPost.getConfigurationSection("Send").getKeys(false).toArray();
			byte loc=2;
			for(int count = 0; count < PostList.length;count++)
			{
				if(count==25)
					break;
				String PostTo = PlayerPost.getString("Send."+PostList[count].toString()+".To");
				String PostTitle = PlayerPost.getString("Send."+PostList[count].toString()+".Title");
				String PostMemo = PlayerPost.getString("Send."+PostList[count].toString()+".Memo");
				ItemStack PostItem = PlayerPost.getItemStack("Send."+PostList[count].toString()+".Item");
	
				List<String> Memo = new ArrayList<String>();
				Memo.add("");
				Memo.add("§9제목 : §f"+PostTitle);
				Memo.add("");
				for(int count2=0;count2<(PostMemo.length()/20)+1;count2++)
				{
					if((count2+1)*20<PostMemo.length())
						Memo.add("§f"+PostMemo.substring(0+(count2*20), ((count2+1)*20)));
					else
						Memo.add("§f"+PostMemo.substring(0+(count2*20), PostMemo.length()));
				}
				Memo.add("");
				Memo.add("§9받는이 : §f"+PostTo);
				if(PostItem==null)
				{
					Memo.add("§e[좌 클릭시 메시지 전송 취소]");
					Memo.add("§0"+PostList[count].toString());
					Stack2("§f[메시지]", 358,0,1,Memo, loc, inv);
				}
				else
				{
					int PostValue = PlayerPost.getInt("Send."+PostList[count].toString()+".Value");
					ItemMeta PIMeta = PostItem.getItemMeta();
					List<String> PostedItemLore = PIMeta.getLore();
					if(PostItem.hasItemMeta() && PIMeta != null)
					{
						PostedItemLore.add("§9대금 청구 : §f"+PostValue);
						PostedItemLore.add(" ");
						PostedItemLore.add("§e[좌 클릭시 물품 회수]");
						PostedItemLore.add("§0"+PostList[count].toString());
						for(int count2 = 0; count2 < PostedItemLore.size(); count2++)
							Memo.add(PostedItemLore.get(count2));
					}
					else
					{
						Memo.add("§9대금청구 : §f"+PostValue);
						Memo.add(" ");
						Memo.add("§e[좌 클릭시 물품 회수]");
						Memo.add("§0"+PostList[count].toString());
					}
					PIMeta.setLore(Memo);
					PostItem.setItemMeta(PIMeta);
					ItemStackStack(PostItem, loc, inv);
				}
				if(loc==6||loc==15||loc==24||loc==33||loc==42)
					loc = (byte) (loc+5);
				else
					loc++;
			}
		}

		Stack2("§c ", 66,0,1,Arrays.asList(""), 1, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 7, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 10, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 16, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 19, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 25, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 28, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 34, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 37, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 43, inv);

		int id = 166;
		if(Type==0)//받은 우편
			id = 166;
		else
			id = 54;
		Stack2("§f§l[받은 우편]", id,0,1,Arrays.asList("§7받은 우편을 확인합니다."), 0, inv);

		if(Type==0)//받은 우편
			id = 333;
		else
			id = 166;
		Stack2("§f§l[보낸 우편]", id,0,1,Arrays.asList("§7보낸 우편을 확인합니다."), 9, inv);
		Stack2("§f§l[닫기]", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+Type), 26, inv);
		Stack2("§f§l[우편 쓰기]", 386,0,1,Arrays.asList("§7새로운 우편을 보냅니다."), 36, inv);
		player.openInventory(inv);
		return;
	}

	public void ItemPutterGUI(Player player)
	{
		String UniqueCode = "§1§0§d§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§c§l보낼 아이템");
		Stack2("§c ", 166,0,1,null, 0, inv);
		Stack2("§c ", 166,0,1,null, 1, inv);
		Stack2("§c ", 166,0,1,null, 2, inv);
		Stack2("§c ", 166,0,1,null, 3, inv);
		Stack2("§c ", 166,0,1,null, 5, inv);
		Stack2("§c ", 166,0,1,null, 6, inv);
		Stack2("§c ", 166,0,1,null, 7, inv);
		Stack2("§c ", 166,0,1,null, 8, inv);
		player.openInventory(inv);
	}
	
	
	
	
	public void PostBoxMainGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 26)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 0)//수신함
		{
			SoundEffect.SP(player, Sound.BLOCK_CHEST_OPEN, 0.8F, 1.0F);
			PostBoxMainGUI(player, (byte) 0);
		}
		else if(slot == 9)//송신함
		{
			SoundEffect.SP(player, Sound.BLOCK_CHEST_OPEN, 0.8F, 1.0F);
			PostBoxMainGUI(player, (byte) 1);
		}
		else if(slot == 36)//새 우편
		{
			YamlLoader PlayerPost = new YamlLoader();
			PlayerPost.getConfig("Post/"+player.getUniqueId().toString()+".yml");
			if(PlayerPost.contains("Send"))
				if(PlayerPost.getConfigurationSection("Send").getKeys(false).size()<25)
				{
					UserData_Object u = new UserData_Object();
					SoundEffect.SP(player, Sound.BLOCK_CLOTH_STEP, 0.8F, 1.8F);
					u.setTemp(player, "Structure");
					u.setType(player, "Post");
					u.setString(player, (byte)0, "RN");//Reciever Nickname
					u.setString(player, (byte)1, "");//받는이
					u.setString(player, (byte)2, "§f제목 없음");//우편 제목
					u.setString(player, (byte)3, "§f내용 없음");//우편 내용
					u.setBoolean(player, (byte)0, false);//아이템 송부
					u.setInt(player, (byte)0, 0);//대금 청구
					player.closeInventory();
					player.sendMessage("§a[우편] : 받으실 분의 닉네임을 입력 하세요.");
				}
				else
				{
					SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.8F);
					player.sendMessage("§c[우편] : 우편은 최대 25개 까지만 보낼 수 있습니다.");
				}
			else
			{
				UserData_Object u = new UserData_Object();
				SoundEffect.SP(player, Sound.BLOCK_CLOTH_STEP, 0.8F, 1.8F);
				u.setTemp(player, "Structure");
				u.setType(player, "Post");
				u.setString(player, (byte)0, "RN");//Reciever Nickname
				u.setString(player, (byte)1, "");//받는이
				u.setString(player, (byte)2, "§f제목 없음");//우편 제목
				u.setString(player, (byte)3, "§f내용 없음");//우편 내용
				u.setBoolean(player, (byte)0, false);//아이템 송부
				u.setInt(player, (byte)0, 0);//대금 청구
				player.closeInventory();
				player.sendMessage("§a[우편] : 받으실 분의 닉네임을 입력 하세요.");
			}
		}
		else if(slot != 1 && slot != 7 && slot != 10 && slot != 16 && slot != 19 && slot != 25 && slot != 28 && slot != 34 && slot != 37 && slot != 43)
		{
			YamlLoader PlayerPost = new YamlLoader();
			PlayerPost.getConfig("Post/"+player.getUniqueId().toString()+".yml");
			if(event.getCurrentItem().hasItemMeta())
			{
				byte Type = Byte.parseByte(ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1)));
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				long UTC = Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-1)));
				if(Type==0)//수신함
				{
					if(PlayerPost.contains("Recieve."+UTC)==false)
					{
						PostBoxMainGUI(player, Type);
						return;
					}
					String Sender = PlayerPost.getString("Recieve."+UTC+".From");
					if(Sender.equals("[시스템]")|| Sender.equals("[반송]")||
					Sender.equals("[거래 영수증]")||Sender.equals("[거래 게시판]"))
					{
						if(new util.Util_Player().giveItem(player, PlayerPost.getItemStack("Recieve."+UTC+".Item")))
						{
							PlayerPost.removeKey("Recieve."+UTC);
							PlayerPost.saveConfig();
							PostBoxMainGUI(player, Type);
							return;
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage("§c[우편] : 인벤토리 공간이 부족합니다!");
						}
					}
					else
					{
						Sender = Bukkit.getOfflinePlayer(Sender).getUniqueId().toString();
						YamlLoader SenderPost = new YamlLoader();
						SenderPost.getConfig("Post/"+Sender+".yml");
						if(PlayerPost.getItemStack("Recieve."+UTC+".Item")==null)
						{
							PlayerPost.removeKey("Recieve."+UTC);
							PlayerPost.saveConfig();
							SenderPost.removeKey("Send."+UTC);
							SenderPost.saveConfig();
							PostBoxMainGUI(player, Type);
							return;
						}
						else
						{
							if(event.isLeftClick())
							{
								if(PlayerPost.getInt("Recieve."+UTC+".Value")==0)
								{
									if(new util.Util_Player().giveItem(player, PlayerPost.getItemStack("Recieve."+UTC+".Item")))
									{
										PlayerPost.removeKey("Recieve."+UTC);
										PlayerPost.saveConfig();
										SenderPost.removeKey("Send."+UTC);
										SenderPost.saveConfig();
										PostBoxMainGUI(player, Type);
										return;
									}
									else
									{
										SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
										player.sendMessage("§c[우편] : 인벤토리 공간이 부족합니다!");
									}
								}
								else
								{
									if(Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()>=PlayerPost.getInt("Recieve."+UTC+".Value"))
									{
										if(new util.Util_Player().giveItem(player, PlayerPost.getItemStack("Recieve."+UTC+".Item")))
										{
											Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Money(Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money()-PlayerPost.getInt("Recieve."+UTC+".Value"));
											if(Main_ServerOption.PlayerList.containsKey(Sender))
												Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_Money(Main_ServerOption.PlayerList.get(Sender).getStat_Money()+PlayerPost.getInt("Recieve."+UTC+".Value"));
											else
											{
												YamlLoader target = new YamlLoader();
												target.getConfig("Stats/"+Sender+".yml");
												target.set("Stat.Money", target.getLong("Stat.Money")+PlayerPost.getInt("Recieve."+UTC+".Value"));
											}
											Sender = Bukkit.getPlayer(PlayerPost.getString("Recieve."+UTC+".From")).getUniqueId().toString();
											int value = PlayerPost.getInt("Recieve."+UTC+".Value");
											PlayerPost.removeKey("Recieve."+UTC);
											PlayerPost.saveConfig();
											SenderPost.removeKey("Send."+UTC);
											SenderPost.saveConfig();
											PostBoxMainGUI(player, Type);
											SendPost_Server(Sender, "[거래 영수증]", "[은행 입금 완료]", player.getName()+" 님께서 대금 "+value+" "+main.Main_ServerOption.money+"§f 입금하였습니다.", null);
											return;
										}
										else
										{
											SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
											player.sendMessage("§c[우편] : 인벤토리 공간이 부족합니다!");
										}
									}
									else
									{
										SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
										player.sendMessage("§c[우편] : 소지금이 부족합니다!");
									}
								}
							}
							else if(event.isRightClick()&&event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-2).contains("반송")==true)
							{
								if(!PlayerPost.getString("Recieve."+UTC+".From").equals("[반송]"))
								{
									SenderPost.removeKey("Send."+UTC);
									SenderPost.saveConfig();
									Sender = Bukkit.getPlayer(PlayerPost.getString("Recieve."+UTC+".From")).getUniqueId().toString();
									SendPost_Server(Sender, "[반송]", "[물품 반송]", player.getName()+" 님께서 물품을 반송하였습니다.", PlayerPost.getItemStack("Recieve."+UTC+".Item"));
									PlayerPost.removeKey("Recieve."+UTC);
									PlayerPost.saveConfig();
									PostBoxMainGUI(player, Type);
								}
								return;
							}
						}
					}
				}
				else//송신함
				{
					if(PlayerPost.contains("Send."+UTC)==false)
					{
						PostBoxMainGUI(player, Type);
						return;
					}
					String Receiver = PlayerPost.getString("Send."+UTC+".To");
					Receiver = Bukkit.getOfflinePlayer(Receiver).getUniqueId().toString();
					YamlLoader ReceiverPost = new YamlLoader();
					ReceiverPost.getConfig("Post/"+Receiver+".yml");
					if(PlayerPost.getItemStack("Send."+UTC+".Item")==null)
					{
						ReceiverPost.removeKey("Recieve."+UTC);
						ReceiverPost.saveConfig();
						PlayerPost.removeKey("Send."+UTC);
						PlayerPost.saveConfig();
						PostBoxMainGUI(player, Type);
						return;
					}
					else
					{
						if(new util.Util_Player().giveItem(player, PlayerPost.getItemStack("Send."+UTC+".Item")))
						{
							ReceiverPost.removeKey("Recieve."+UTC+".Item");
							ReceiverPost.removeKey("Recieve."+UTC);
							ReceiverPost.saveConfig();
							PlayerPost.removeKey("Send."+UTC);
							PlayerPost.saveConfig();
							PostBoxMainGUI(player, Type);
							return;
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage("§c[우편] : 인벤토리 공간이 부족합니다!");
						}
					}
				}
			}
		}
	}
	
	public void ItemPutterGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		

		if(slot != 4 && event.getCurrentItem().getTypeId()==166)
		{
			if(!event.getClickedInventory().getTitle().equals("container.inventory"))
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.9F);
				event.setCancelled(true);
			}
		}
	}
	

	public void ItemPutterGUIClose(InventoryCloseEvent event)
	{
		ItemStack item = event.getInventory().getItem(4);
		Player player = (Player) event.getPlayer();
		if(item!=null)
		{
			
			UserData_Object u = new UserData_Object();
			u.setItemStack(player, item);
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			u.setString(player, (byte)0, "Value");
			u.setTemp(player,"Structure");
			player.sendMessage("§a[우편] : 우편물 수령을 위한 대금을 입력 하세요.");
		}
		else
			SendPost(player);
	}


	public void SendPost(Player player)
	{
		
		UserData_Object u = new UserData_Object();
		String targetUID = Bukkit.getPlayer(u.getString(player, (byte)1)).getUniqueId().toString();
		YamlLoader TargetPost = new YamlLoader();
		TargetPost.getConfig("Post/"+targetUID+".yml");
		YamlLoader PlayerPost = new YamlLoader();
		PlayerPost.getConfig("Post/"+player.getUniqueId().toString()+".yml");
		if(TargetPost.contains("Recieve")==false)
		{
			TargetPost.createSection("Recieve");
			TargetPost.saveConfig();
		}
		if(TargetPost.contains("Send")==false)
		{
			TargetPost.createSection("Send");
			TargetPost.saveConfig();
		}
		long UTC = new servertick.ServerTick_Main().nowUTC +new util.Util_Number().RandomNum(0, 1200);
		if(TargetPost.getConfigurationSection("Recieve").getKeys(false).size()<25)
		{
			TargetPost.set("Recieve."+UTC+".From", player.getName());
			TargetPost.set("Recieve."+UTC+".Title", u.getString(player, (byte)2));
			TargetPost.set("Recieve."+UTC+".Memo", u.getString(player, (byte)3));
			TargetPost.set("Recieve."+UTC+".Item", u.getItemStack(player));
			TargetPost.set("Recieve."+UTC+".Value", u.getInt(player,(byte)0));
			PlayerPost.set("Send."+UTC+".To", u.getString(player, (byte)1));
			PlayerPost.set("Send."+UTC+".Title", u.getString(player, (byte)2));
			PlayerPost.set("Send."+UTC+".Memo", u.getString(player, (byte)3));
			PlayerPost.set("Send."+UTC+".Item", u.getItemStack(player));
			PlayerPost.set("Send."+UTC+".Value", u.getInt(player, (byte)0));
			TargetPost.saveConfig();
			PlayerPost.saveConfig();
			SoundEffect.SP(player, Sound.BLOCK_CHEST_CLOSE, 1.0F, 1.8F);
			player.sendMessage("§a[우편] : 우편물을 발송하였습니다!");
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
			player.sendMessage("§c[우편] : 해당 플레이어의 우편함이 가득 찼습니다.");
			if(u.getItemStack(player)!=null)
			{
				PlayerPost.set("Recieve."+UTC+".From", "[시스템]");
				PlayerPost.set("Recieve."+UTC+".Title", "[배송 실패]");
				PlayerPost.set("Recieve."+UTC+".Memo", "[상대방의 우편함이 꽉 찼습니다.]");
				PlayerPost.set("Recieve."+UTC+".Item", u.getItemStack(player));
				PlayerPost.saveConfig();
			}
		}
		u.clearAll(player);
		return;
	}

	public void SendPost_Server(String targetUUID, String Name, String Title, String Memo, ItemStack item)
	{
		YamlLoader TargetPost = new YamlLoader();
		TargetPost.getConfig("Post/"+targetUUID+".yml");
		if(TargetPost.contains("Recieve")==false)
		{
			TargetPost.createSection("Recieve");
			TargetPost.saveConfig();
		}
		long UTC = new servertick.ServerTick_Main().nowUTC +new util.Util_Number().RandomNum(0, 1200);
		TargetPost.set("Recieve."+UTC+".From", Name);
		TargetPost.set("Recieve."+UTC+".Title", Title);
		TargetPost.set("Recieve."+UTC+".Memo", Memo);
		TargetPost.set("Recieve."+UTC+".Item", item);
		TargetPost.set("Recieve."+UTC+".Value", 0);
		TargetPost.saveConfig();
		return;
	}


	public String CreatePostBox(int LineNumber, String StructureCode, byte Direction)
	{
		if(LineNumber<=19) //우체통 다리 4개
		{
			if(LineNumber<=4)
				return "/summon ArmorStand ~-0.18 ~"+(0.652+(LineNumber*0.34))+" ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			else if(LineNumber<=9)
				return "/summon ArmorStand ~-0.18 ~"+(0.652+((LineNumber-5)*0.34))+" ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			else if(LineNumber<=14)
				return "/summon ArmorStand ~-0.86 ~"+(0.652+((LineNumber-10)*0.34))+" ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			else if(LineNumber<=19)
				return "/summon ArmorStand ~-0.86 ~"+(0.652+((LineNumber-15)*0.34))+" ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		}

		switch(Direction)
		{
		case 1://동
			switch(LineNumber)
			{
				case 25:
					return "/summon ArmorStand ~-0.86 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 26:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 27:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 3://서
			switch(LineNumber)
			{
				case 25:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 26:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 27:
					return "/summon ArmorStand ~-0.18 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 5://남
			switch(LineNumber)
			{
				case 25:
					return "/summon ArmorStand ~-0.86 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 26:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 27:
					return "/summon ArmorStand ~-0.18 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 7://북
			switch(LineNumber)
			{
				case 25:
					return "/summon ArmorStand ~-0.86 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 26:
					return "/summon ArmorStand ~-0.52 ~1.672 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 27:
					return "/summon ArmorStand ~-0.18 ~1.672 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		}
		
		switch(LineNumber)
		{
		case 20:
			return "/summon ArmorStand ~-0.18 ~2.012 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 21:
			return "/summon ArmorStand ~-0.52 ~2.012 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 22:
			return "/summon ArmorStand ~-0.86 ~2.012 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 23:
			return "/summon ArmorStand ~-0.52 ~2.012 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 24:
			return "/summon ArmorStand ~-0.52 ~2.012 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

		case 28:
			return "/summon ArmorStand ~-0.18 ~1.332 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 29:
			return "/summon ArmorStand ~-0.86 ~1.332 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 30:
			return "/summon ArmorStand ~-0.52 ~1.332 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 31:
			return "/summon ArmorStand ~-0.52 ~1.332 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

		case 32:
			return "/summon ArmorStand ~-0.18 ~0.992 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 33:
			return "/summon ArmorStand ~-0.52 ~0.992 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 34:
			return "/summon ArmorStand ~-0.86 ~0.992 ~-0.62 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 35:
			return "/summon ArmorStand ~-0.52 ~0.992 ~-0.96 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		case 36:
			return "/summon ArmorStand ~-0.52 ~0.992 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stained_hardened_clay,Damage:14,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			
		case 37:
			return "/summon ArmorStand ~-0.28 ~1.332 ~-0.28 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1}";

		}
		return "null";
	}
}
