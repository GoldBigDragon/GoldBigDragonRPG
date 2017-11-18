package npc;

import org.bukkit.entity.Player;

import util.YamlLoader;




public class NpcConfig
{
    public void PlayerNPCconfig(Player player, String NPCuuid)
	{
	  	YamlLoader npcScriptYaml = new YamlLoader();
    	if(npcScriptYaml.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
    	{
    		npcScriptYaml.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    	  	npcScriptYaml.set(NPCuuid+".love", 0);
    	  	npcScriptYaml.set(NPCuuid+".Career", 0);
    	  	npcScriptYaml.saveConfig();
    	}
		npcScriptYaml.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    	if(npcScriptYaml.contains(NPCuuid) == false)
    	{
    		npcScriptYaml.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
    	  	npcScriptYaml.set(NPCuuid+".love", 0);
    	  	npcScriptYaml.set(NPCuuid+".Career", 0);
    	  	npcScriptYaml.saveConfig();
    	}
	}
    public void NPCNPCconfig(String NPCuuid)
	{
	  	YamlLoader npcScriptYaml = new YamlLoader();
    	if(npcScriptYaml.isExit("NPC/NPCData/"+NPCuuid+".yml")==false)
    	{
    		npcScriptYaml.getConfig("NPC/NPCData/"+NPCuuid+".yml");
    	  	npcScriptYaml.set("NPCuuid", "NPC's uuid");
    	  	npcScriptYaml.set("KoreaLanguage(UTF-8)->JavaEntityLanguage", "http://itpro.cz/juniconv/");
    	  	npcScriptYaml.set("NatureTalk.1.love", 0);
    	  	npcScriptYaml.set("NatureTalk.1.Script", "§f안녕, §e%player%?");
    	  	npcScriptYaml.set("NatureTalk.2.love", 0);
    	  	npcScriptYaml.set("NatureTalk.2.Script", "§f띄워 쓰기는%enter%§f이렇게 하지.");
    	  	npcScriptYaml.set("NatureTalk.3.love", 0);
    	  	npcScriptYaml.set("NatureTalk.3.Script", "§1색깔은 §4이렇게 §f넣을 수 있어!");
    	  	npcScriptYaml.set("NearByNEWS.1.love", 0);
    	  	npcScriptYaml.set("NearByNEWS.1.Script", "§f너희집에서 어제 다이아몬드를 본 것 같은데...");
    	  	npcScriptYaml.set("NearByNEWS.2.love", 0);
    	  	npcScriptYaml.set("NearByNEWS.2.Script", "§f조심하는게 좋아.%enter%§f어제 §4히로빈 §f이 이 근처에 있었거든...");
    	  	npcScriptYaml.set("NearByNEWS.3.love", 0);
    	  	npcScriptYaml.set("NearByNEWS.3.Script", "§f음...");
    	  	npcScriptYaml.set("AboutSkills.1.love", 0);
    	  	npcScriptYaml.set("AboutSkills.1.giveSkill", "null");
    	  	npcScriptYaml.set("AboutSkills.1.AlreadyGetScript", "null");
    	  	npcScriptYaml.set("AboutSkills.1.Script", "§f나는 §e채광 스킬§f이 있지!%enter%§f뭐? 너도 있다고?");
    	  	npcScriptYaml.set("AboutSkills.2.love", 0);
    	  	npcScriptYaml.set("AboutSkills.2.giveSkill", "null");
    	  	npcScriptYaml.set("AboutSkills.2.AlreadyGetScript", "null");
    	  	npcScriptYaml.set("AboutSkills.2.Script", "§f달리기는 너의 건강에도 좋지만%enter%§f생존에도 좋지.");
    	  	npcScriptYaml.set("AboutSkills.3.love", 0);
    	  	npcScriptYaml.set("AboutSkills.3.giveSkill", "null");
    	  	npcScriptYaml.set("AboutSkills.3.AlreadyGetScript", "null");
    	  	npcScriptYaml.set("AboutSkills.3.Script", "§f너에게 가르쳐 줄만한%enter%§f기술이 없는것 같은데...");

    	  	npcScriptYaml.createSection("Shop.Sell");
    	  	npcScriptYaml.createSection("Shop.Buy");
    	  	npcScriptYaml.set("Quest.0", null);
    	  	
    	  	npcScriptYaml.saveConfig();
    	}
	}
}
