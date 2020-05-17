package job;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import util.YamlLoader;


public class JobMain
{	
	public void AllPlayerFixAllSkillAndJobYML()
	{
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
	  	if(!configYaml.contains("Time.LastSkillChanged"))
	  	{
	  		configYaml.set("Time.LastSkillChanged", -1);
	  		configYaml.saveConfig();
	  	}
	  	ArrayList<Player> players = new ArrayList<>();
	  	players.addAll(Bukkit.getServer().getOnlinePlayers());
		FixJobList();
	  	YamlLoader playerSkillYaml = new YamlLoader();
		for(int count = 0; count < players.size();count++)
		{
			playerSkillYaml.getConfig("Skill/PlayerData/"+players.get(count).getUniqueId().toString()+".yml");
	  		if(configYaml.getInt("Time.LastSkillChanged")!=playerSkillYaml.getInt("Update") || ! playerSkillYaml.contains("Update"))
	  		{
	  			playerSkillYaml.set("Update", configYaml.getInt("Time.LastSkillChanged"));
	  			playerSkillYaml.saveConfig();
	  			FixJobList();
				FixPlayerJobList(players.get(count));
	  		}
		}
	}

	public void PlayerFixAllSkillAndJobYML(Player player)
	{
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
	  	if(!configYaml.contains("Time.LastSkillChanged"))
	  	{
	  		configYaml.set("Time.LastSkillChanged", -1);
	  		configYaml.saveConfig();
	  	}
	  	YamlLoader playerSkillYaml = new YamlLoader();
		playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
  		if(configYaml.getInt("Time.LastSkillChanged")!=playerSkillYaml.getInt("Update") || ! playerSkillYaml.contains("Update"))
  		{
  			playerSkillYaml.set("Update", configYaml.getInt("Time.LastSkillChanged"));
  			playerSkillYaml.saveConfig();
  			FixJobList();
			FixPlayerJobList(player);
  		}
	}
	
	public void AllPlayerSkillRankFix()
	{
	  	ArrayList<Player> players = new ArrayList<>();
	  	players.addAll(Bukkit.getServer().getOnlinePlayers());
		FixJobList();
		for(int count = 0; count < players.size();count++)
			SkillRankFix(players.get(count));
	}
	
	public void FixJobList()
	//���� ��ų���� ��ų ��Ͽ� ��ϵ��� ���� ��ų�� ���� �� �ִ� �޼ҵ�
	{
	  	YamlLoader jobYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");
	  	YamlLoader skillYaml = new YamlLoader();
		skillYaml.getConfig("Skill/SkillList.yml");
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
		
		if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			String[] categoriArray = jobYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray(new String[0]);
			for(int counter = 0; counter < categoriArray.length; counter++)
			{
				if(!categoriArray[counter].equals("Added"))
				{
					String[] skillArray = jobYaml.getConfigurationSection("Mabinogi."+categoriArray[counter]).getKeys(false).toArray(new String[0]);
					for(int countta = 0; countta < skillArray.length; countta++)
					{
						if(!skillYaml.contains(skillArray[countta]))
							jobYaml.removeKey("Mabinogi."+categoriArray[counter]+"."+skillArray[countta]);
					}
				}
			}
			jobYaml.saveConfig();
		}
		else
		{
			String[] jobArray = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
			for(int counter = 0; counter < jobArray.length; counter++)
			{
				String[] subJobArray = jobYaml.getConfigurationSection("MapleStory."+jobArray[counter].toString()).getKeys(false).toArray(new String[0]);
				for(int count = 0; count < subJobArray.length; count++)
				{
					String[] subJobSkillArray = jobYaml.getConfigurationSection("MapleStory."+jobArray[counter].toString()+"."+subJobArray[count]+".Skill").getKeys(false).toArray(new String[0]);
					for(int countta = 0; countta < subJobSkillArray.length; countta++)
					{
						if(!skillYaml.contains(subJobSkillArray[countta]))
						{
							jobYaml.removeKey("MapleStory."+jobArray[counter]+"."+subJobArray[count]+".Skill."+subJobSkillArray[countta]);
							jobYaml.saveConfig();
						}
					}
				}
			}
		}
	}
	
	public void FixPlayerJobList(Player player)
	//���� �߿��� ���� ��Ͽ� ��ϵ��� ���� ������ ���� �÷��̾ ���� �� �ִ� �޼ҵ�
	//������ ���������� ������ ī�װ��� �÷��̾� ��ų ��Ͽ��� ������ �ָ�, ���� ���� ī�װ��� �÷��̾�� ����� �ش�.
	{
	  	YamlLoader playerSkillYaml = new YamlLoader();
	  	if(!playerSkillYaml.isExit("Skill/PlayerData/"+player.getUniqueId().toString()+".yml"))
	  		new skill.SkillConfig().CreateNewPlayerSkill(player);

		playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
	  	YamlLoader jobYaml = new YamlLoader();
	  	YamlLoader skillYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");
		skillYaml.getConfig("Skill/SkillList.yml");
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
		
		if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			ArrayList<String> categori = new ArrayList<>();
			ArrayList<String> playerCategori = new ArrayList<>();
			if(playerSkillYaml.getConfigurationSection("Mabinogi") != null) {
				playerCategori.addAll(playerSkillYaml.getConfigurationSection("Mabinogi").getKeys(false));
			}
			if(jobYaml.getConfigurationSection("Mabinogi") != null) {
				categori.addAll(jobYaml.getConfigurationSection("Mabinogi").getKeys(false));
			}
			
			for(int count = 0; count < playerCategori.size(); count++)
				if(! categori.contains(playerCategori.get(count)))
					playerSkillYaml.removeKey("Mabinogi."+playerCategori.get(count).toString());

			for(int count = 0; count < categori.size(); count++)
				if(!categori.get(count).equals("Added") && ! playerCategori.contains(categori.get(count)))
					playerSkillYaml.createSection("Mabinogi."+categori.get(count));
			

			//���� ��ų�� �÷��̾� ��ų�� ���Ͽ� ������ ���� ��ų ����
			//���� ��ų�� �÷��̾� ��ų�� ���Ͽ� �÷��̾�� ���� ��ų ���
			//������ ī�װ��� ��ϵ� ��� ��ų�� ���ڿ��� ������ ��, �÷��̾ ���� ��ų�� �����Ͽ�
			//���� ī�װ����� ������ �÷��̾�� ��ų�� �ִٸ� ������ �ִ� ����.
			for(int count = 0; count < categori.size(); count ++)
			{
				if(!categori.get(count).equals("Added"))
				{
					ArrayList<String> jobSkillList = new ArrayList<>();
					ArrayList<String> playerSkillList = new ArrayList<>();
					jobSkillList.addAll(jobYaml.getConfigurationSection("Mabinogi."+categori.get(count)).getKeys(false));
					playerSkillList.addAll(playerSkillYaml.getConfigurationSection("Mabinogi."+categori.get(count)).getKeys(false));
					
					for(int countta = 0; countta < playerSkillList.size(); countta++)
						if( ! jobSkillList.contains(playerSkillList.get(countta)))
							playerSkillYaml.removeKey("Mabinogi."+categori.get(count)+"."+playerSkillList.get(countta));
					
					//���� ��ų ���� �Ϲ� ��ų���� �߷�����, �Ϲ� ��ų�� ���� �÷��̾��
					//��ų�� ������ �ִ� ����.
					for(int countta = 0; countta < jobSkillList.size(); countta++)
					{
						if(jobYaml.getBoolean("Mabinogi."+categori.get(count) + "."+jobSkillList.get(countta)))
							if(!playerSkillList.contains(jobSkillList.get(countta)))
								playerSkillYaml.set("Mabinogi."+categori.get(count)+"."+jobSkillList.get(countta), 1);
					}
					
					//��ų �ִ� ���� �ѱ�͵��� �ִ� ������ ���� �� �ֱ�.
					for(int countta = 0; countta < playerSkillList.size(); countta++)
					{
						short skillMaxRank = (short) skillYaml.getConfigurationSection(playerSkillList.get(countta)+".SkillRank").getKeys(false).size();
						if(playerSkillYaml.getInt("Mabinogi."+categori.get(count)+"."+playerSkillList.get(countta)) >  skillMaxRank)
							playerSkillYaml.set("Mabinogi."+categori.get(count)+"."+playerSkillList.get(countta), skillMaxRank);
					}
				}
			}
			playerSkillYaml.saveConfig();
			
		}
		else//������ ���丮
		{
			String[] jobs = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
			boolean isJobExit = false;
			for(int counter = 0; counter < jobs.length; counter++)
				for(int count =0; count < jobYaml.getConfigurationSection("MapleStory."+jobs[counter]).getKeys(false).size(); count++)
					if(jobYaml.getConfigurationSection("MapleStory."+jobs[counter]).getKeys(false).toArray()[count].toString().equals(playerSkillYaml.getString("Job.Type")))
					{
						isJobExit = true;
						break;
					}
			if(!isJobExit)
			{
				String serverDefaultJob = configYaml.getString("Server.DefaultJob");
				playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
				playerSkillYaml.set("Job.Type", serverDefaultJob);
				playerSkillYaml.set("Job.LV", 1);
				if(jobYaml.getConfigurationSection("MapleStory."+serverDefaultJob+"."+serverDefaultJob+".Skill") != null)
				{
					String[] skills = jobYaml.getConfigurationSection("MapleStory."+serverDefaultJob+"."+serverDefaultJob+".Skill").getKeys(false).toArray(new String[0]);
					for(int count = 0; count < skills.length;count++)
						if(!playerSkillYaml.contains("MapleStory."+serverDefaultJob+".Skill."+skills[count]))
							playerSkillYaml.set("MapleStory."+serverDefaultJob+".Skill."+skills[count],1);
				}
				playerSkillYaml.saveConfig();
			}

			//������ ��ų �����ְ�, ����� ��ų �����ִ� ����
			if(playerSkillYaml.getConfigurationSection("MapleStory") != null)
			{
				String[] playerJob = playerSkillYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
				for(int jobCounter = 0; jobCounter < jobs.length; jobCounter++)
				{
					if(jobYaml.getConfigurationSection("MapleStory."+jobs[jobCounter]) != null) {
						String[] subJobs = jobYaml.getConfigurationSection("MapleStory."+jobs[jobCounter]).getKeys(false).toArray(new String[0]);
						for(int playerJobCounter = 0; playerJobCounter < playerJob.length; playerJobCounter++)
						{
							if(!playerSkillYaml.contains("MapleStory."+playerJob[playerJobCounter]+".Skill"))
							{
								playerSkillYaml.createSection("MapleStory."+playerJob[playerJobCounter]+".Skill");
								playerSkillYaml.saveConfig();
							}
							for(int subJobCounter = 0; subJobCounter < subJobs.length; subJobCounter++)
							{
								if(subJobs[subJobCounter].equals(playerJob[playerJobCounter]))
								{
									ArrayList<String> subJobSkills = new ArrayList<>();
									ArrayList<String> playerJobSkills = new ArrayList<>();
									subJobSkills.addAll(jobYaml.getConfigurationSection("MapleStory."+jobs[jobCounter]+"."+subJobs[subJobCounter]+".Skill").getKeys(false));
									playerJobSkills.addAll(playerSkillYaml.getConfigurationSection("MapleStory."+playerJob[playerJobCounter]+".Skill").getKeys(false));
									
									for(int cc=0;cc<playerJobSkills.size();cc++)
										if(!subJobSkills.contains(playerJobSkills.get(cc)))
											playerSkillYaml.removeKey("MapleStory."+playerJob[playerJobCounter]+".Skill."+playerJobSkills.get(cc));

									for(int cc=0;cc<subJobSkills.size();cc++)
										if(!playerJobSkills.contains(subJobSkills.get(cc)))
											playerSkillYaml.set("MapleStory."+subJobs[subJobCounter]+".Skill."+subJobSkills.get(cc),1);

									//��ų �ִ� ���� �ѱ�͵��� �ִ� ������ ���� �� �ֱ�.
									for(int cc = 0; cc < playerJobSkills.size();cc++)
									{
										if(skillYaml.contains(playerJobSkills.get(cc)+".SkillRank"))
										{
											short skillMaxRank = (short) skillYaml.getConfigurationSection(playerJobSkills.get(cc)+".SkillRank").getKeys(false).size();
											if(playerSkillYaml.getInt("MapleStory."+playerJob[jobCounter]+".Skill."+playerJobSkills.get(cc)) >  skillMaxRank)
												playerSkillYaml.set("MapleStory."+playerJob[jobCounter]+".Skill."+playerJobSkills.get(cc), skillMaxRank);
										}
									}
									playerSkillYaml.saveConfig();
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
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
	  	YamlLoader skillYaml = new YamlLoader();
		skillYaml.getConfig("Skill/SkillList.yml");
	  	YamlLoader playerSkillYaml = new YamlLoader();
		playerSkillYaml.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");

		//��ų �ִ� �������� ������ ���� ��ų�� �ִ� ����ġ�� ������ �ִ� ����
		if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			String[] categoriList = playerSkillYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray(new String[0]);
			playerSkillYaml.saveConfig();
			for(int count = 0; count < categoriList.length; count ++)
			{
				String[] playerSkills = playerSkillYaml.getConfigurationSection("Mabinogi."+categoriList[count]).getKeys(false).toArray(new String[0]);
				for(int countta = 0; countta < playerSkills.length; countta++)
				{
					short skillMaxRank = (short) skillYaml.getConfigurationSection(playerSkills[countta]+".SkillRank").getKeys(false).size();
					if(playerSkillYaml.getInt("Mabinogi."+categoriList[count]+"."+playerSkills[countta]) >  skillMaxRank)
					{
						playerSkillYaml.set("Mabinogi."+categoriList[count]+"."+playerSkills[countta], skillMaxRank);
					}
				}
			}
		}
		else
		{
			if(playerSkillYaml.contains("MapleStory"))
			{
				int skillMaxRank = 0;
				String[] playerJob = playerSkillYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
				for(int counter = 0; counter < playerJob.length; counter++)
				{
					String[] playerSkills = playerSkillYaml.getConfigurationSection("MapleStory."+playerJob[counter]+".Skill").getKeys(false).toArray(new String[0]);
					for(int countta = 0; countta < playerSkills.length;countta++)
					{
						skillMaxRank = skillYaml.getConfigurationSection(playerSkills[countta]+".SkillRank").getKeys(false).size();
						if(playerSkillYaml.getInt("MapleStory."+playerJob[counter]+".Skill."+playerSkills[countta]) >  skillMaxRank)
						{
							playerSkillYaml.set("MapleStory."+playerJob[counter]+".Skill."+playerSkills[countta], skillMaxRank);
						}
					}
				}
			}
		}
		playerSkillYaml.saveConfig();
	}
}
