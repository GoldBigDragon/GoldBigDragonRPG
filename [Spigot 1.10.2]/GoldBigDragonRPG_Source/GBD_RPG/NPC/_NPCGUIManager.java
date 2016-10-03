package GBD_RPG.NPC;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _NPCGUIManager
{
	//NPC GUI Click Unique Number = 07
	//NPC 관련 GUI의 고유 번호는 07입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 NPC 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("00")==0)//NPC 메인 GUI
			new GBD_RPG.NPC.NPC_GUI().MainGUIClick(event, ChatColor.stripColor(event.getInventory().getName()).split("C] ")[1]);
		else if(SubjectCode.compareTo("01")==0)//NPC 상점 목록 GUI
			new GBD_RPG.NPC.NPC_GUI().ShopGUIClick(event, ChatColor.stripColor(event.getInventory().getName()).split("C] ")[1]);
		else if(SubjectCode.compareTo("02")==0)//NPC 대화 GUI
			new GBD_RPG.NPC.NPC_GUI().TalkGUIClick(event, ChatColor.stripColor(event.getInventory().getName()).split("C] ")[1]);
		else if(SubjectCode.compareTo("03")==0)//NPC 모든 퀘스트 목록 GUI
			new GBD_RPG.NPC.NPC_GUI().QuestAddGUIClick(event);
		else if(SubjectCode.compareTo("04")==0)//진행 가능 퀘스트 목록 GUI
			new GBD_RPG.NPC.NPC_GUI().QuestListGUIClick(event);
		else if(SubjectCode.compareTo("05")==0)//NPC 직업 선택 GUI
			new GBD_RPG.NPC.NPC_GUI().NPCjobGUIClick(event, ChatColor.stripColor(event.getInventory().getItem(18).getItemMeta().getLore().get(1)));
		else if(SubjectCode.compareTo("06")==0)//NPC 워프 목록 GUI
			new GBD_RPG.NPC.NPC_GUI().WarpMainGUIClick(event);
		else if(SubjectCode.compareTo("07")==0)//NPC 워프 등록 GUI
			new GBD_RPG.NPC.NPC_GUI().WarperGUIClick(event);
		else if(SubjectCode.compareTo("08")==0)//NPC 개조 목록 GUI
			new GBD_RPG.NPC.NPC_GUI().UpgraderGUIClick(event);
		else if(SubjectCode.compareTo("09")==0)//NPC 개조식 등록 GUI
			new GBD_RPG.NPC.NPC_GUI().SelectUpgradeRecipeGUIClick(event);
		else if(SubjectCode.compareTo("0a")==0)//NPC 룬 세공사 GUI
			new GBD_RPG.NPC.NPC_GUI().RuneEquipGUIClick(event);
		else if(SubjectCode.compareTo("0b")==0)//NPC 대화 목록 GUI
			new GBD_RPG.NPC.NPC_GUI().TalkGUIClick(event);
		else if(SubjectCode.compareTo("0c")==0)//NPC 대화 설정 GUI
			new GBD_RPG.NPC.NPC_GUI().TalkSettingGUIClick(event);
		else if(SubjectCode.compareTo("0d")==0)//NPC 가르칠 스킬 선택 GUI
			new GBD_RPG.NPC.NPC_GUI().AddAbleSkillsGUIClick(event);
		else if(SubjectCode.compareTo("0e")==0)//NPC 물품 수량 설정 GUI
			new GBD_RPG.NPC.NPC_GUI().ItemBuyGuiClick(event);
		else if(SubjectCode.compareTo("0f")==0)//NPC 수리 GUI
			new GBD_RPG.NPC.NPC_GUI().ItemFixGuiClick(event);
		else if(SubjectCode.compareTo("10")==0)//NPC 선물 아이템 목록 GUI
			new GBD_RPG.NPC.NPC_GUI().PresentGuiClick(event);
		else if(SubjectCode.compareTo("11")==0)//NPC 선물 아이템 설정 GUI
			new GBD_RPG.NPC.NPC_GUI().PresentGuiClick(event);
	}
	
	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("0a")==0)//NPC 룬 세공사 GUI
			new GBD_RPG.NPC.NPC_GUI().RuneEquipGUIClose(event);
		else if(SubjectCode.compareTo("11")==0)//NPC 선물 아이템 설정 GUI
			new GBD_RPG.NPC.NPC_GUI().PresentInventoryClose(event);
	}
}
