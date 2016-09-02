package GoldBigDragon_RPG.ETC;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Job
{	
	public void AllPlayerFixAllSkillAndJobYML()
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config  = YC.getNewConfig("config.yml");
	  	if(Config.contains("Time.LastSkillChanged")==false)
	  	{
	  		Config.set("Time.LastSkillChanged", -1);
	  		Config.saveConfig();
	  	}
	  	
    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
    	Player[] players = new Player[playerlist.size()];
    	playerlist.toArray(players);
		FixJobList();
		for(short count = 0; count < players.length;count++)
		{
			YamlManager PlayerList  = YC.getNewConfig("Skill/PlayerData/"+players[count].getUniqueId().toString()+".yml");
	  		if(Config.getInt("Time.LastSkillChanged")!=PlayerList.getInt("Update") || PlayerList.contains("Update")==false)
	  		{
	  			PlayerList.set("Update", Config.getInt("Time.LastSkillChanged"));
	  			PlayerList.saveConfig();
				FixPlayerJobList(players[count]);
				FixPlayerSkillList(players[count]);
	  		}
		}
		return;
	}

	public void PlayerFixAllSkillAndJobYML(Player player)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config  = YC.getNewConfig("config.yml");
	  	if(Config.contains("Time.LastSkillChanged")==false)
	  	{
	  		Config.set("Time.LastSkillChanged", -1);
	  		Config.saveConfig();
	  	}
		YamlManager PlayerList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
  		if(Config.getInt("Time.LastSkillChanged")!=PlayerList.getInt("Update") || PlayerList.contains("Update")==false)
  		{
  			PlayerList.set("Update", Config.getInt("Time.LastSkillChanged"));
  			PlayerList.saveConfig();
  			FixJobList();
			FixPlayerJobList(player);
			FixPlayerSkillList(player);
  		}
		return;
	}
	
	public void AllPlayerSkillRankFix()
	{
    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
    	Player[] players = new Player[playerlist.size()];
    	playerlist.toArray(players);
		FixJobList();
		for(int count = 0; count < players.length;count++)
			SkillRankFix(players[count]);
		return;
	}
	
	public void FixJobList()
	//직업 스킬에서 스킬 목록에 등록되지 않은 스킬을 삭제 해 주는 메소드
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager JobList = YC.getNewConfig("Skill/JobList.yml");
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		
		if(YC.getNewConfig("config.yml").getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			Object[] Categori = JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
			for(short counter = 0; counter < Categori.length; counter++)
			{
				if(Categori[counter].toString().compareTo("Added")!=0)
				{
					Object[] Skills = JobList.getConfigurationSection("Mabinogi."+Categori[counter].toString()).getKeys(false).toArray();
					for(short countta = 0; countta < Skills.length; countta++)
					{
						if(SkillList.contains(Skills[countta].toString())==false)
							JobList.removeKey("Mabinogi."+Categori[counter].toString()+"."+Skills[countta].toString());
					}
				}
			}
			JobList.saveConfig();
		}
		else
		{
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(short counter = 0; counter < Job.length; counter++)
			{
				Object[] SubJob = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()).getKeys(false).toArray();
				for(short count = 0; count < SubJob.length; count++)
				{
					Object[] SubJobSkills = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()+"."+SubJob[count]+".Skill").getKeys(false).toArray();
					for(short countta = 0; countta < SubJobSkills.length; countta++)
					{
						if(SkillList.contains(SubJobSkills[countta].toString())==false)
						{
							JobList.removeKey("MapleStory."+Job[counter].toString()+"."+SubJob[count].toString()+".Skill."+SubJobSkills[countta].toString());
							JobList.saveConfig();
						}
					}
				}
			}
		}
	}
	
	public void FixPlayerJobList(Player player)
	//직업 중에서 직업 목록에 등록되지 않은 직업을 가진 플레이어를 변경 해 주는 메소드
	//마비노기 버전에서는 삭제된 카테고리를 플레이어 스킬 목록에서 제거해 주며, 새로 나온 카테고리를 플레이어에게 등록해 준다.
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");

	  	if(YC.isExit("Skill/PlayerData/"+player.getUniqueId().toString()+".yml") == false)
	  		new GoldBigDragon_RPG.Skill.SkillConfig().CreateNewPlayerSkill(player);

		YamlManager PlayerList = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
		
		if(YC.getNewConfig("config.yml").getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			ArrayList<String> Categori = new ArrayList<String>();
			ArrayList<String> PlayerCategori = new ArrayList<String>();
			for(short count=0;count<PlayerList.getConfigurationSection("Mabinogi").getKeys(false).toArray().length;count++)
				PlayerCategori.add(PlayerList.getConfigurationSection("Mabinogi").getKeys(false).toArray()[count].toString());
			for(short count=0;count<JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray().length;count++)
				Categori.add(JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray()[count].toString());

			for(short count = 0; count < PlayerCategori.size(); count++)
				if(Categori.contains(PlayerCategori.get(count)) == false)
					PlayerList.removeKey("Mabinogi."+PlayerCategori.get(count).toString());

			for(short count = 0; count < Categori.size(); count++)
				if(Categori.get(count).compareTo("Added") != 0)
					if(PlayerCategori.contains(Categori.get(count)) == false)
						PlayerList.createSection("Mabinogi."+Categori.get(count));

			PlayerList.saveConfig();
		}
		else
		{
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(short counter = 0; counter < Job.length; counter++)
				for(short count =0; count < JobList.getConfigurationSection("MapleStory."+Job[counter].toString()).getKeys(false).size(); count++)
					if(JobList.getConfigurationSection("MapleStory."+Job[counter].toString()).getKeys(false).toArray()[count].toString().compareTo(PlayerList.getString("Job.Type"))==0)
						return;
			YamlManager Config  = YC.getNewConfig("config.yml");
			String ServerDefaultJob = Config.getString("Server.DefaultJob");
			//플레이어 콘피그 수정이 안됨
			PlayerList = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
			PlayerList.set("Job.Type", ServerDefaultJob);
			PlayerList.set("Job.LV", 1);
			Object[] Skills = JobList.getConfigurationSection("MapleStory."+ServerDefaultJob+"."+ServerDefaultJob+".Skill").getKeys(false).toArray();
			for(short count = 0; count < Skills.length;count++)
				if(PlayerList.contains("MapleStory."+ServerDefaultJob+".Skill."+Skills[count].toString())==false)
					PlayerList.set("MapleStory."+ServerDefaultJob+".Skill."+Skills[count].toString(),1);
			PlayerList.saveConfig();
			return;
		}
	}
	
	public void FixPlayerSkillList(Player player)
	//플레이어 스킬에서 직업 스킬 목록에 없는 스킬을 삭제 해 주고, 플레이어가 없는 스킬을 추가시켜 주는 메소드
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
		YamlManager PlayerList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");

		if(YC.getNewConfig("config.yml").getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			//직업 스킬과 플레이어 스킬을 비교하여 직업에 없는 스킬 삭제
			//직업 스킬과 플레이어 스킬을 비교하여 플레이어에게 없는 스킬 등록
			Object[] CategoriList = JobList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
			//마비노기 카테고리에 등록된 모든 스킬을 문자열로 나열한 뒤, 플레이어가 가진 스킬을 대입하여
			//만일 카테고리에는 없지만 플레이어에게 스킬이 있다면 삭제해 주는 구문.
			for(short count = 0; count < CategoriList.length; count ++)
			{
				if(CategoriList[count].toString().compareTo("Added") != 0)
				{
					ArrayList<String> JobSkillList = new ArrayList<String>();
					ArrayList<String> PlayerSkillList = new ArrayList<String>();
					for(short countta = 0; countta < JobSkillList.size(); countta ++)
						JobSkillList.add(JobList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray()[countta].toString());
					for(short countta = 0; countta < JobSkillList.size(); countta ++)
						PlayerSkillList.add(PlayerList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray()[countta].toString());
					for(short countta = 0; countta < PlayerSkillList.size(); countta++)
						if(JobSkillList.contains(PlayerSkillList.get(countta))==false)
							PlayerList.removeKey("Mabinogi."+CategoriList[count]+"."+PlayerSkillList.get(countta));
				}
			}
			
			//히든 스킬 외의 일반 스킬들을 추려내어, 일반 스킬이 없는 플레이어에게
			//스킬을 전수해 주는 구문.
			for(short count = 0; count < CategoriList.length; count ++)
			{
				if(CategoriList[count].toString().compareTo("Added") != 0)
				{
					Object[] JSkillList = JobList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray();
					for(short countta = 0; countta < JSkillList.length; countta++)
					{
						if(JobList.getBoolean("Mabinogi."+CategoriList[count] + "."+JSkillList[countta].toString()) == true)
						{
							if(PlayerList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray().toString().contains(JSkillList[countta].toString())==false)
							{
								PlayerList.set("Mabinogi."+CategoriList[count]+"."+JSkillList[countta].toString(), 1);
							}
						}
					}
				}
			}
			PlayerList.saveConfig();
		}
		else
		{
			PlayerList.createSection("MapleStory."+PlayerList.getString("Job.Type")+".Skill");
			PlayerList.saveConfig();
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(short counter = 0; counter < Job.length; counter++)
			{
				Object[] SubJob = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()).getKeys(false).toArray();
				for(short count = 0; count < SubJob.length; count++)
				{
					Object[] SubJobSkills = JobList.getConfigurationSection("MapleStory."+Job[counter].toString()+"."+SubJob[count]+".Skill").getKeys(false).toArray();
					for(short countta = 0; countta < SubJobSkills.length; countta++)
					{
						if(PlayerList.contains("MapleStory."+SubJob[count])==true)
						{
							PlayerList.createSection("MapleStory."+SubJob[count]+".Skill");
							PlayerList.saveConfig();
							if(PlayerList.getConfigurationSection("MapleStory."+SubJob[count]+".Skill").getKeys(false).size() == 0)
							{
								PlayerList.set("MapleStory."+SubJob[count]+".Skill."+SubJobSkills[countta].toString(),1);
								PlayerList.saveConfig();
							}
							else
							{
								if(PlayerList.getConfigurationSection("MapleStory."+SubJob[count]+".Skill").getKeys(false).contains(SubJobSkills[countta].toString())==false)
								{
									PlayerList.set("MapleStory."+SubJob[count]+".Skill."+SubJobSkills[countta].toString(),1);
									PlayerList.saveConfig();
								}
							}
						}
					}
				}
			}
			Object[] PlayerJob = PlayerList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			Object[] Jobs = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(short counter = 0; counter < Jobs.length; counter++)
			{
				for(short count = 0; count < PlayerJob.length; count++)
				{
					Object[] SubJobs = JobList.getConfigurationSection("MapleStory."+Jobs[counter]).getKeys(false).toArray();
					if(PlayerList.contains("MapleStory."+PlayerJob[count].toString())==true)
					{
						PlayerList.createSection("MapleStory."+PlayerJob[count].toString()+".Skill");
						PlayerList.saveConfig();
						Object[] PlayerJobSkills = PlayerList.getConfigurationSection("MapleStory."+PlayerJob[count].toString()+".Skill").getKeys(false).toArray();
						for(short countta = 0; countta < SubJobs.length; countta++)
						{
							if(SubJobs[countta].equals(PlayerJob[count].toString()))
							{
								for(short countia = 0; countia < PlayerJobSkills.length; countia++)
								{
									boolean isExit = false;
									Object[] JobSkills = JobList.getConfigurationSection("MapleStory."+Jobs[counter]+"."+SubJobs[countta]+".Skill").getKeys(false).toArray();
									for(short cc=0;cc<JobSkills.length;cc++)
									{
										if(JobSkills[cc].toString().compareTo(PlayerJobSkills[countia].toString())==0)
										{
											isExit=true;
											break;
										}
									}
									if(isExit==false)
									{
										PlayerList.removeKey("MapleStory."+PlayerJob[count].toString()+".Skill."+PlayerJobSkills[countia].toString());
										PlayerList.saveConfig();
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void SkillRankFix(Player player)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config  = YC.getNewConfig("config.yml");
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		YamlManager PlayerList  = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");

		//스킬 최대 레벨보다 레벨이 높은 스킬을 최대 레벨치로 수정해 주는 구문
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			Object[] CategoriList = PlayerList.getConfigurationSection("Mabinogi").getKeys(false).toArray();
			PlayerList.saveConfig();
			for(short count = 0; count < CategoriList.length; count ++)
			{
				Object[] PlayerSkills = PlayerList.getConfigurationSection("Mabinogi."+CategoriList[count]).getKeys(false).toArray();
				for(short countta = 0; countta < PlayerSkills.length; countta++)
				{
					short SkillMaxRank = (short) SkillList.getConfigurationSection(PlayerSkills[countta]+".SkillRank").getKeys(false).size();
					if(PlayerList.getInt("Mabinogi."+CategoriList[count]+"."+PlayerSkills[countta]) >  SkillMaxRank)
					{
						PlayerList.set("Mabinogi."+CategoriList[count]+"."+PlayerSkills[countta].toString(), SkillMaxRank);
					}
				}
			}
		}
		else
		{
			if(PlayerList.contains("MapleStory"))
			{
				Object[] PlayerJob = PlayerList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(short counter = 0; counter < PlayerJob.length; counter++)
				{
					Object[] PlayerSkills = PlayerList.getConfigurationSection("MapleStory."+PlayerJob[counter]+".Skill").getKeys(false).toArray();
					for(short countta = 0; countta < PlayerSkills.length;countta++)
					{
						short SkillMaxRank = (short) SkillList.getConfigurationSection(PlayerSkills[countta]+".SkillRank").getKeys(false).size();
						if(PlayerList.getInt("MapleStory."+PlayerJob[counter]+".Skill."+PlayerSkills[countta]) >  SkillMaxRank)
						{
							PlayerList.set("MapleStory."+PlayerJob[counter]+".Skill."+PlayerSkills[countta], SkillMaxRank);
						}
					}
				}
			}
		}
		PlayerList.saveConfig();
	}
}
