package event;

import org.bukkit.event.inventory.InventoryClickEvent;

public class EventInventoryClick
{
	public void InventoryClickRouter(InventoryClickEvent event, String InventoryCode)
	{
		String UniqueCode = InventoryCode.charAt(1) + "" + InventoryCode.charAt(2);
		String SubjectCode = InventoryCode.charAt(3) + "" + InventoryCode.charAt(4);
		if(UniqueCode.equals("00"))//User 패키지 속의 GUI Click을 관리함.
		    new user._UserGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("01"))//Admin 패키지 속의 GUI Click을 관리함.
		    new admin._AdminGUIManager().clickRouting(event, SubjectCode);
		else if(UniqueCode.equals("02"))//Area 패키지 속의 GUI Click을 관리함.
		    new area._AreaGUIManager().clickRouting(event, SubjectCode);
		else if(UniqueCode.equals("03"))//CustomItem 패키지 속의 GUI Click을 관리함.
		    new customitem._ItemGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("04"))//Party 패키지 속의 GUI Click을 관리함.
		    new party._PartyGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("05"))//Quest 패키지 속의 GUI Click을 관리함.
		    new quest._QuestGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("06"))//Job 패키지 속의 GUI Click을 관리함.
		    new job._JobGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("07"))//NPC 패키지 속의 GUI Click을 관리함.
		    new npc._NPCGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("08"))//Monster 패키지 속의 GUI Click을 관리함.
		    new monster._MonsterGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("09"))//Corpse 패키지 속의 GUI Click을 관리함.
		    new corpse._CorpseGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("0a"))//Dungeon 패키지 속의 GUI Click을 관리함.
		    new dungeon._DungeonGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("0b"))//Skill 패키지 속의 GUI Click을 관리함.
		    new skill._SkillGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("0c"))//Warp 패키지 속의 GUI Click을 관리함.
		    new warp._WarpGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("0d"))//Structure 패키지 속의 GUI Click을 관리함.
		    new structure._StructureGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.equals("0e"))//Making 패키지 속의 GUI Click을 관리함.
		    new making._MakingGUIManager().ClickRouting(event, SubjectCode);
	}
}