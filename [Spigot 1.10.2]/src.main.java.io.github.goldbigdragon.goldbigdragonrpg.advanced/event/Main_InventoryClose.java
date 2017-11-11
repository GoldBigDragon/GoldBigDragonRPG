package event;

import org.bukkit.event.inventory.InventoryCloseEvent;

public class Main_InventoryClose
{
	public void InventoryCloseRouter(InventoryCloseEvent event, String InventoryCode)
	{
		String UniqueCode = InventoryCode.charAt(1) + "" + InventoryCode.charAt(2);
		String SubjectCode = InventoryCode.charAt(3) + "" + InventoryCode.charAt(4);
		if(UniqueCode.equals("00"))//User 패키지 속의 GUI Click을 관리함.
		    new user._UserGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.equals("01"))//Admin 패키지 속의 GUI Close를 관리함.
		    new admin._AdminGUIManager().closeRouting(event, SubjectCode);
		else if(UniqueCode.equals("02"))//Area 패키지 속의 GUI Close를 관리함.
		    new area._AreaGUIManager().closeRouting(event, SubjectCode);
		else if(UniqueCode.equals("05"))//Quest 패키지 속의 GUI Close를 관리함.
		    new quest._QuestGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.equals("07"))//NPC 패키지 속의 GUI Close를 관리함.
		    new npc._NPCGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.equals("08"))//Monster 패키지 속의 GUI Close를 관리함.
		    new monster._MonsterGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.equals("0a"))//Dungeon 패키지 속의 GUI Close를 관리함.
		    new dungeon._DungeonGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.equals("0d"))//Structure 패키지 속의 GUI Close를 관리함.
		    new structure._StructureGUIManager().CloseRouting(event, SubjectCode);
		else if(UniqueCode.equals("0e"))//Making 패키지 속의 GUI Close를 관리함.
		    new making._MakingGUIManager().CloseRouting(event, SubjectCode);
	}
}
