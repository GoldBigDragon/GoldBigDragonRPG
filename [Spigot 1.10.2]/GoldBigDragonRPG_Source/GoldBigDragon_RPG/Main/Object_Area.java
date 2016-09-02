package GoldBigDragon_RPG.Main;

public class Object_Area
{
	private String AreaName;
	private int minX;
	private int minY;
	private int minZ;
	private int maxX;
	private int maxY;
	private int maxZ;
	
	public String getAreaName() {
		return AreaName;
	}
	public int getMinX() {
		return minX;
	}
	public int getMinY() {
		return minY;
	}
	public int getMinZ() {
		return minZ;
	}
	public int getMaxX() {
		return maxX;
	}
	public int getMaxY() {
		return maxY;
	}
	public int getMaxZ() {
		return maxZ;
	}
	public void setAreaName(String areaName) {
		AreaName = areaName;
	}
	public void setMinX(int minX) {
		this.minX = minX;
	}
	public void setMinY(int minY) {
		this.minY = minY;
	}
	public void setMinZ(int minZ) {
		this.minZ = minZ;
	}
	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	public void setMaxZ(int maxZ) {
		this.maxZ = maxZ;
	}
}