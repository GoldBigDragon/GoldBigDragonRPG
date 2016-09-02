package GoldBigDragon_RPG.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Structure_Board extends GUIutil
{
	public void BoardMainGUI(Player player, String BoardCode, byte page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Board =YC.getNewConfig("Structure/"+BoardCode+".yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED +""+ChatColor.BOLD +""+ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "게시판 : "+(page+1));
		
		if(Board.contains("Post_Number")==false)
		{
			Board.set("Post_Number", 0);
			Board.set("User.1",null);
			Board.set("Notice","null");
			Board.set("OnlyUseOP",false);
			Board.saveConfig();
		}
		int postNumber = Board.getInt("Post_Number");
		
		byte loc=10;
		short count = 0;
		int AllPost = 0;
		
		for(int Post = postNumber; Post >= 0; Post--)
			if(Board.contains("User."+Post+".User")==true)
				AllPost++;
		if(page!=0)
		{
			for(int Post = postNumber; Post >= 0; Post--)
			{
				if(Board.contains("User."+Post+".User")==true)
					count++;
				if(count > 28*page)
				{
					postNumber = Post;
					break;
				}
			}
		}
		count = 0;
		
		for(int Post = postNumber;Post >= 0; Post--)
		{
			if(count>28)
				break;
			if(Board.contains("User."+Post+".User")==true)
			{
				String PostUser = Board.getString("User."+Post+".User");
				String PostTitle = Board.getString("User."+Post+".Title");
				String PostMemo = Board.getString("User."+Post+".Memo");
				long PostUTC = Board.getLong("User."+Post+".UTC");
				
				String PostedTime = new GoldBigDragon_RPG.Util.ETC().getFrom(new GoldBigDragon_RPG.ServerTick.ServerTickMain().nowUTC, PostUTC);

				List<String> Memo = new ArrayList<String>();
				Memo.add("");
				Memo.add(ChatColor.BLUE+"제목 : "+ChatColor.WHITE+PostTitle);
				Memo.add("");
				for(int count2=0;count2<(PostMemo.length()/20)+1;count2++)
				{
					if((count2+1)*20<PostMemo.length())
						Memo.add(ChatColor.WHITE+PostMemo.substring(0+(count2*20), ((count2+1)*20)));
					else
						Memo.add(ChatColor.WHITE+PostMemo.substring(0+(count2*20), PostMemo.length()));
				}
				Memo.add("");
				Memo.add(ChatColor.BLUE+"작성자 : " + ChatColor.WHITE+PostUser);
				Memo.add(ChatColor.BLUE+"게시글 번호 : " + ChatColor.WHITE+Post);
				Memo.add("");
				Memo.add(ChatColor.YELLOW+"[Shift 우 클릭시 떼어내기]");
				Memo.add(ChatColor.BLACK+""+Post);
				Stack2(ChatColor.GOLD+""+ChatColor.BOLD+PostedTime +"전 작성된 게시글", 358,(byte)0,(byte)1,Memo, (byte)loc, inv);
				if(loc==16||loc==25||loc==34||loc==43)
					loc = (byte) (loc+3);
				else
					loc++;
				count=(short) (count+1);
			}
		}
		Stack2(ChatColor.RED + " ", 160,(byte)12,(byte)1,Arrays.asList(BoardCode), (byte)0, inv);
		
		for(byte count2 = 1; count2 < 9; count2++)
			Stack2(ChatColor.RED + " ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);
		for(byte count2 = 44; count2 < 54; count2++)
			Stack2(ChatColor.RED + " ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);
		for(byte count2 = 9; count2 < 45; count2=(byte) (count2+9))
			Stack2(ChatColor.RED + " ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);
		for(byte count2 = 17; count2 < 54; count2=(byte) (count2+9))
			Stack2(ChatColor.RED + " ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);

		if(Board.getString("Notice").compareTo("null")!=0)
		{
			List<String> Memo = new ArrayList<String>();
			for(short count2=0;count2<(Board.getString("Notice").length()/20)+1;count2++)
			{
				if((count2+1)*20<Board.getString("Notice").length())
					Memo.add(ChatColor.WHITE+Board.getString("Notice").substring(0+(count2*20), ((count2+1)*20)));
				else
					Memo.add(ChatColor.WHITE+Board.getString("Notice").substring(0+(count2*20), Board.getString("Notice").length()));
			}
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[게시판 알림]", 321,(byte)0,(byte)1,Memo, (byte)4, inv);
		}
		
		if(AllPost>(28*page)+28)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), (byte)50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), (byte)48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 게시글", 386,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "새로운 게시글을 작성합니다."), (byte)49, inv);
		player.openInventory(inv);
		return;
	}
	
	public void BoardSettingGUI(Player player, String BoardCode)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Board =YC.getNewConfig("Structure/"+BoardCode+".yml");
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +""+ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "게시판 설정");
		
		if(Board.contains("Post_Number")==false)
		{
			Board.set("Post_Number", 0);
			Board.set("User.1",null);
			Board.set("Notice","null");
			Board.set("OnlyUseOP",false);
			Board.saveConfig();
		}
		if(Board.getString("Notice").compareTo("null")==0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[게시판 알림]", 166,(byte)0,(byte)1,Arrays.asList(ChatColor.RED + "[게시판 알림 없음]"), (byte)2, inv);
		else
		{
			List<String> Memo = new ArrayList<String>();
			for(short count2=0;count2<(Board.getString("Notice").length()/20)+1;count2++)
			{
				if((count2+1)*20<Board.getString("Notice").length())
					Memo.add(ChatColor.WHITE+Board.getString("Notice").substring(0+(count2*20), ((count2+1)*20)));
				else
					Memo.add(ChatColor.WHITE+Board.getString("Notice").substring(0+(count2*20), Board.getString("Notice").length()));
			}
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[게시판 알림]", 321,(byte)0,(byte)1,Memo, (byte)2, inv);
		}

		if(Board.getBoolean("OnlyUseOP"))
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[사용 권한]", 137,(byte)0,(byte)1,Arrays.asList(ChatColor.BLUE + "[관리자 전용]"), (byte)4, inv);
		else
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[사용 권한]", 397,(byte)3,(byte)1,Arrays.asList(ChatColor.GREEN + "[전체 이용]"), (byte)4, inv);
			
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[게시글 전체 삭제]", 325,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "게시판에 붙여진 모든 게시글을",ChatColor.GRAY + "삭제합니다."), (byte)6, inv);

		Stack2(BoardCode, 160,(byte)8,(byte)1,null, (byte)1, inv);
		Stack2(BoardCode, 160,(byte)8,(byte)1,null, (byte)3, inv);
		Stack2(BoardCode, 160,(byte)8,(byte)1,null, (byte)5, inv);
		Stack2(BoardCode, 160,(byte)8,(byte)1,null, (byte)7, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), (byte)0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), (byte)8, inv);
		player.openInventory(inv);
	}
	
	
	public void BoardMainGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		byte page =  (byte) (Byte.parseByte(event.getInventory().getTitle().split(" : ")[1])-1);
		
		String Code = event.getInventory().getItem(0).getItemMeta().getLore().get(0);

		switch (event.getSlot())
		{
		case 49://새 게시글
			{
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Board =YC.getNewConfig("Structure/"+Code+".yml");
				if(Board.getBoolean("OnlyUseOP")&&player.isOp()==false)
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[게시판] : 게시글 작성 권한이 없습니다!");
					return;
				}
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.STEP_WOOL, 0.8F, 1.8F);
				u.setTemp(player, "Structure");
				u.setType(player, "Board");
				u.setString(player, (byte)0, "Title");
				u.setString(player, (byte)1, ChatColor.WHITE+"제목 없음");//게시글 제목
				u.setString(player, (byte)2, ChatColor.WHITE+"내용 없음");//게시글 내용
				u.setString(player, (byte)3, Code);//게시판 코드
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[게시판] : 게시글 제목을 입력 해 주세요.");
			}
			return;
		case 48://이전 페이지
			if(event.getCurrentItem().getTypeId()==323)
			{
				s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
				BoardMainGUI(player, Code, (byte) (page-1));
			}
			return;
		case 50://다음 페이지
			if(event.getCurrentItem().getTypeId()==323)
			{
				s.SP(player, Sound.WOOD_CLICK, 0.8F, 1.0F);
				BoardMainGUI(player, Code, (byte) (page+1));
			}
			return;
		}
		if((event.getSlot()>=10&&event.getSlot()<=16)||
		   (event.getSlot()>=19&&event.getSlot()<=25)||
		   (event.getSlot()>=28&&event.getSlot()<=34)||
		   (event.getSlot()>=37&&event.getSlot()<=43))
		{
			if(event.getCurrentItem().getTypeId()!=358)
				return;
			if(event.isRightClick()&&event.isShiftClick())
			{
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Board =YC.getNewConfig("Structure/"+Code+".yml");
				short PostNumber = Short.parseShort(ChatColor.stripColor((event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-1))));
				if(Board.contains("User."+PostNumber))
				{
					if(Board.getString("User."+PostNumber+".User").compareTo(player.getName())==0
						||player.isOp())
					{
						s.SP(player, Sound.SHEEP_SHEAR, 1.0F, 1.5F);
						Board.removeKey("User."+PostNumber);
						Board.saveConfig();
						BoardMainGUI(player, Code, page);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[게시판] : 자신이 작성한 게시글만 삭제할 수 있습니다.");
					}
				}
				else
					BoardMainGUI(player, Code, page);
			}
		}
	}

	public void BoardSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String Code = event.getInventory().getItem(1).getItemMeta().getDisplayName();
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Board =YC.getNewConfig("Structure/"+Code+".yml");
		
		switch (event.getSlot())
		{
		case 0://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GoldBigDragon_RPG.Structure.StructureGUI().StructureListGUI(player, 0);
			return;
		case 8://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;

		case 2://게시판 알림
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			Object_UserData u = new Object_UserData();
			u.setTemp(player, "Structure");
			u.setType(player, "Board");
			u.setString(player, (byte)0, "Notice");
			u.setString(player, (byte)1, Code);//게시판 코드
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN+"[게시판] : 게시판 알림을 입력 해 주세요.");
			return;
		case 4://게시판 권한
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(Board.getBoolean("OnlyUseOP"))
				Board.set("OnlyUseOP", false);
			else
				Board.set("OnlyUseOP", true);
			Board.saveConfig();
			BoardSettingGUI(player, Code);
			return;
		case 6://게시판 비우기
			s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.5F);
			Board.removeKey("User");
			Board.set("Post_Number", 0);
			Board.set("User.1",null);
			Board.saveConfig();
			return;
		}
	}

	
	public String CreateBoard(int LineNumber, String StructureCode, byte Direction)
	{
		switch(Direction)
		{
		case 1://동
		case 3://서
			switch(LineNumber)
			{
			case 0:
				return "/summon ArmorStand ~-0.216 ~0.57 ~-0.20 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 1:
				return "summon ArmorStand ~-0.5 ~1.07 ~-0.14 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 2:
				return "summon ArmorStand ~-0.5 ~1.896 ~-0.14 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 3:
				return "/summon ArmorStand ~-0.216 ~2.288 ~-0.26 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 4:
				return "/summon ArmorStand ~-0.216 ~0.57 ~1.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 5:
				return "summon ArmorStand ~-0.5 ~1.07 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 6:
				return "summon ArmorStand ~-0.5 ~1.896 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 7:
				return "/summon ArmorStand ~-0.216 ~2.288 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			if(LineNumber<=22) //판떼기
			{
				if(LineNumber<=10)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-7)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=13)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-10)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=16)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-13)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=19)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-16)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=22)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-19)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			switch(LineNumber)
			{
			case 23:
				return "/summon ArmorStand ~-0.046 ~2.628 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 24:
				return "/summon ArmorStand ~-0.046 ~2.628 ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 25:
				return "/summon ArmorStand ~-0.046 ~2.628 ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 26:
				return "/summon ArmorStand ~-0.046 ~2.628 ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 27:
				return "/summon ArmorStand ~-0.046 ~2.628 ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 28:
				return "/summon ArmorStand ~-0.046 ~2.628 ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 29:
				return "/summon ArmorStand ~-0.046 ~2.628 ~1.60 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

			case 30:
				return "/summon ArmorStand ~-0.386 ~2.628 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 31:
				return "/summon ArmorStand ~-0.386 ~2.628 ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 32:
				return "/summon ArmorStand ~-0.386 ~2.628 ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 33:
				return "/summon ArmorStand ~-0.386 ~2.628 ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 34:
				return "/summon ArmorStand ~-0.386 ~2.628 ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 35:
				return "/summon ArmorStand ~-0.386 ~2.628 ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 36:
				return "/summon ArmorStand ~-0.386 ~2.628 ~1.60 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 5://남
		case 7://북
			switch(LineNumber)
			{
			case 0:
				return "/summon ArmorStand ~-0.20 ~0.57 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 1:
				return "summon ArmorStand ~-0.14 ~1.07 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 2:
				return "summon ArmorStand ~-0.14 ~1.896 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 3:
				return "/summon ArmorStand ~-0.26 ~2.288 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 4:
				return "/summon ArmorStand ~1.76 ~0.57 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 5:
				return "summon ArmorStand ~1.80 ~1.07 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 6:
				return "summon ArmorStand ~1.80 ~1.896 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:stick,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 7:
				return "/summon ArmorStand ~1.80 ~2.288 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			if(LineNumber<=22) //판떼기
			{
				if(LineNumber<=10)
					return "/summon ArmorStand ~0.08 ~"+(1.268+((LineNumber-7)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=13)
					return "/summon ArmorStand ~0.42 ~"+(1.268+((LineNumber-10)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=16)
					return "/summon ArmorStand ~0.76 ~"+(1.268+((LineNumber-13)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=19)
					return "/summon ArmorStand ~1.10 ~"+(1.268+((LineNumber-16)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=22)
					return "/summon ArmorStand ~1.46 ~"+(1.268+((LineNumber-19)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			switch(LineNumber)
			{
			case 23:
				return "/summon ArmorStand ~-0.28 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 24:
				return "/summon ArmorStand ~0.08 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 25:
				return "/summon ArmorStand ~0.42 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 26:
				return "/summon ArmorStand ~0.76 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 27:
				return "/summon ArmorStand ~1.10 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 28:
				return "/summon ArmorStand ~1.46 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 29:
				return "/summon ArmorStand ~1.60 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

			case 30:
				return "/summon ArmorStand ~-0.28 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 31:
				return "/summon ArmorStand ~0.08 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 32:
				return "/summon ArmorStand ~0.42 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 33:
				return "/summon ArmorStand ~0.76 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 34:
				return "/summon ArmorStand ~1.10 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 35:
				return "/summon ArmorStand ~1.46 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 36:
				return "/summon ArmorStand ~1.60 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		}

		switch(Direction)
		{
			case 1://동
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-36)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-40)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-43)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-46)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-49)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~0.3 ~1.63 ~1.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~0.3 ~1.27 ~1.58 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~0.3 ~1.73 ~1.98 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~0.0 ~0.5 ~0.9 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 3://서
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-36)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-40)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-43)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-46)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-49)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~-0.1 ~1.63 ~1.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~-0.1 ~1.27 ~1.58 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~-0.1 ~1.73 ~1.98 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~-1.0 ~0.5 ~1.1 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 5://남
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~0.08 ~"+(1.268+((LineNumber-36)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~0.42 ~"+(1.268+((LineNumber-40)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~0.76 ~"+(1.268+((LineNumber-43)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~1.10 ~"+(1.268+((LineNumber-46)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~1.46 ~"+(1.268+((LineNumber-49)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~1.08 ~1.63 ~-0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~1.58 ~1.27 ~-0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~1.98 ~1.73 ~-0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~1.0 ~0.5 ~0.5 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 7://북
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~0.08 ~"+(1.268+((LineNumber-36)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~0.42 ~"+(1.268+((LineNumber-40)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~0.76 ~"+(1.268+((LineNumber-43)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~1.10 ~"+(1.268+((LineNumber-46)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~1.46 ~"+(1.268+((LineNumber-49)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:planks,Damage:5,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~1.08 ~1.63 ~-0.70 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~1.58 ~1.27 ~-0.70 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~1.98 ~1.73 ~-0.70 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Equipment:[{id:filled_map,Count:1},{},{},{},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~0.96 ~0.5 ~-0.5 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
		}
		return "null";
	}
}