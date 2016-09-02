package GoldBigDragon_RPG.Structure;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Structure_CampFire extends GUIutil
{

	public void CampFireMainGUI(Player player, String BoardCode)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Board =YC.getNewConfig("Structure/"+BoardCode+".yml");
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED +""+ChatColor.BOLD +""+ "¸ð´ÚºÒ");
		//Stack2(Display, ID, DATA, Stack, Lore, Loc, inventory);
		player.openInventory(inv);
		return;
	}
	
	public String CreateCampFire(int LineNumber, String StructureCode, byte Direction)
	{
		if(LineNumber <= 36)
			return "/summon ArmorStand ~0 ~0.06 ~0 {CustomName:\""+StructureCode+"\",Invisible:1,ShowArms:1,NoBasePlate:1,NoGravity:1,Rotation:["+(LineNumber*10)+"f,0.0f],HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[40f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
		else if(LineNumber == 37)
			return "/summon ArmorStand ~0 ~0.26 ~0 {CustomName:\""+StructureCode+"\",Invisible:1,ShowArms:1,NoBasePlate:1,NoGravity:1}";
		else if(LineNumber == 38)
			return "/summon ArmorStand ~0 ~0.56 ~0 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,Invisible:1,ShowArms:1,NoBasePlate:1,NoGravity:1}";
		else
			return "null";
	}
}
