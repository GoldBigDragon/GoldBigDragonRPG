package npc;

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
		if(SubjectCode.equals("00"))//NPC 메인 GUI
			new npc.NpcGui().MainGUIClick(event, ChatColor.stripColor(event.getInventory().getName()).split("C] ")[1]);
		else if(SubjectCode.equals("01"))//NPC 상점 목록 GUI
			new npc.NpcGui().ShopGUIClick(event, ChatColor.stripColor(event.getInventory().getName()).split("C] ")[1]);
		else if(SubjectCode.equals("02"))//NPC 대화 GUI
			new npc.NpcGui().TalkGUIClick(event, ChatColor.stripColor(event.getInventory().getName()).split("C] ")[1]);
		else if(SubjectCode.equals("03"))//NPC 모든 퀘스트 목록 GUI
			new npc.NpcGui().QuestAddGUIClick(event);
		else if(SubjectCode.equals("04"))//진행 가능 퀘스트 목록 GUI
			new npc.NpcGui().QuestListGUIClick(event);
		else if(SubjectCode.equals("05"))//NPC 직업 선택 GUI
			new npc.NpcGui().NPCjobGUIClick(event, ChatColor.stripColor(event.getInventory().getItem(18).getItemMeta().getLore().get(1)));
		else if(SubjectCode.equals("06"))//NPC 워프 목록 GUI
			new npc.NpcGui().WarpMainGUIClick(event);
		else if(SubjectCode.equals("07"))//NPC 워프 등록 GUI
			new npc.NpcGui().WarperGUIClick(event);
		else if(SubjectCode.equals("08"))//NPC 개조 목록 GUI
			new npc.NpcGui().UpgraderGUIClick(event);
		else if(SubjectCode.equals("09"))//NPC 개조식 등록 GUI
			new npc.NpcGui().SelectUpgradeRecipeGUIClick(event);
		else if(SubjectCode.equals("0a"))//NPC 룬 세공사 GUI
			new npc.NpcGui().RuneEquipGUIClick(event);
		else if(SubjectCode.equals("0b"))//NPC 대화 목록 GUI
			new npc.NpcGui().TalkGUIClick(event);
		else if(SubjectCode.equals("0c"))//NPC 대화 설정 GUI
			new npc.NpcGui().TalkSettingGUIClick(event);
		else if(SubjectCode.equals("0d"))//NPC 가르칠 스킬 선택 GUI
			new npc.NpcGui().AddAbleSkillsGUIClick(event);
		else if(SubjectCode.equals("0e"))//NPC 물품 수량 설정 GUI
			new npc.NpcGui().ItemBuyGuiClick(event);
		else if(SubjectCode.equals("0f"))//NPC 수리 GUI
			new npc.NpcGui().ItemFixGuiClick(event);
		else if(SubjectCode.equals("10"))//NPC 선물 아이템 목록 GUI
			new npc.NpcGui().PresentGuiClick(event);
		else if(SubjectCode.equals("11"))//NPC 선물 아이템 설정 GUI
			new npc.NpcGui().PresentGuiClick(event);
	}
	
	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("0a"))//NPC 룬 세공사 GUI
			new npc.NpcGui().RuneEquipGUIClose(event);
		else if(SubjectCode.equals("11"))//NPC 선물 아이템 설정 GUI
			new npc.NpcGui().PresentInventoryClose(event);
	}
}
