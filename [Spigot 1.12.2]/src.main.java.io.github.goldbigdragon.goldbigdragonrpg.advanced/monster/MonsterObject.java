package monster;

public class MonsterObject
{
	private String realName;
	private String customName;
	private long exp;
	private int hp;
	private int minMoney;
	private int maxMoney;
	private int str;
	private int dex;
	private int intelligence;
	private int will;
	private int luk;
	private int def;
	private int pro;
	private int magicDef;
	private int magicPro;
	
	public MonsterObject(String realName, String customName, long exp, int hp, int minMoney, int maxMoney, int str, int dex, int intelligence, int will, int luk,
			int def, int pro, int magicDef, int magicPro)
	{
		this.realName = realName;
		this.customName = customName;
		this.exp = exp;
		this.hp = hp;
		this.minMoney = minMoney;
		this.maxMoney = maxMoney;
		this.str = str;
		this.dex = dex;
		this.intelligence = intelligence;
		this.will = will;
		this.luk = luk;
		this.def = def;
		this.pro = pro;
		this.magicDef = magicDef;
		this.magicPro = magicPro;
	}

	public String getRealName() {
		return realName;
	}

	public String getCustomName() {
		return customName;
	}

	public int getHP() {
		return hp;
	}

	public int getMinMoney() {
		return minMoney;
	}

	public int getMaxMoney() {
		return maxMoney;
	}

	public int getSTR() {
		return str;
	}

	public int getDEX() {
		return dex;
	}

	public int getINT() {
		return intelligence;
	}

	public int getWILL() {
		return will;
	}

	public int getLUK() {
		return luk;
	}

	public int getDEF() {
		return def;
	}

	public int getPRO() {
		return pro;
	}

	public int getMDEF() {
		return magicDef;
	}

	public int getMPRO() {
		return magicPro;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public void setHP(int hP) {
		this.hp = hP;
	}

	public void setMinMoney(int minMoney) {
		this.minMoney = minMoney;
	}

	public void setMaxMoney(int maxMoney) {
		this.maxMoney = maxMoney;
	}

	public void setSTR(int sTR) {
		this.str = sTR;
	}

	public void setDEX(int dEX) {
		this.dex = dEX;
	}

	public void setINT(int iNT) {
		this.intelligence = iNT;
	}

	public void setWILL(int wILL) {
		this.will = wILL;
	}

	public void setLUK(int lUK) {
		this.luk = lUK;
	}

	public void setDEF(int dEF) {
		this.def = dEF;
	}

	public void setPRO(int pRO) {
		this.pro = pRO;
	}

	public void setMDEF(int mDEF) {
		this.magicDef = mDEF;
	}

	public void setMPRO(int mPRO) {
		this.magicPro = mPRO;
	}

	public long getEXP() {
		return exp;
	}

	public void setEXP(long eXP) {
		this.exp = eXP;
	}
}
