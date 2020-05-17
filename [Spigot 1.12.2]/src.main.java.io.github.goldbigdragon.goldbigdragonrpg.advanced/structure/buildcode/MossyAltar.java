package structure.buildcode;

public class MossyAltar {
	
	private String prefix = "/summon minecraft:armor_stand ";
	private String mossyStoneSuffix = "\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:mossy_cobblestone,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f]}}";

	public String createMossyAltar(int lineNumber, String structureCode)
	{
		if(lineNumber > 55)
			return "null";
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		if(lineNumber<=19) //제단 다리 4개
		{
			if(lineNumber<=4)
				sb.append("~-0.18 ~"+(0.652+(lineNumber*0.34))+" ~-0.28 {CustomName:\"");
			else if(lineNumber<=9)
				sb.append("~-1.2 ~"+(0.652+((lineNumber-5)*0.34))+" ~-0.28 {CustomName:\"");
			else if(lineNumber<=14)
				sb.append("~-1.2 ~"+(0.652+((lineNumber-10)*0.34))+" ~-1.3 {CustomName:\"");
			else if(lineNumber<=19)
				sb.append("~-0.18 ~"+(0.652+((lineNumber-15)*0.34))+" ~-1.3 {CustomName:\"");
		} else {
			switch(lineNumber)
			{
				case 20: sb.append("~-0.52 ~2.012 ~-0.28 {CustomName:\""); break;
				case 21: sb.append("~-0.86 ~2.012 ~-0.28 {CustomName:\""); break;
				case 22: sb.append("~0.16 ~2.012 ~-0.62 {CustomName:\""); break;
				case 23: sb.append("~0.16 ~2.012 ~-0.96 {CustomName:\""); break;
				case 24: sb.append("~0.16 ~2.012 ~-1.3 {CustomName:\""); break;
				case 25: sb.append("~0.16 ~2.012 ~-0.28 {CustomName:\""); break;
				
				case 26: sb.append("~-0.52 ~2.012 ~-1.3 {CustomName:\""); break;
				case 27: sb.append("~-0.86 ~2.012 ~-1.3 {CustomName:\""); break;
				case 28: sb.append("~-1.54 ~2.012 ~-1.3 {CustomName:\""); break;
				case 29: sb.append("~-1.54 ~2.012 ~-0.96 {CustomName:\""); break;
				case 30: sb.append("~-1.54 ~2.012 ~-0.62 {CustomName:\""); break;
				case 31: sb.append("~-1.54 ~2.012 ~-0.28 {CustomName:\""); break;
				
				case 32: sb.append("~-1.2 ~1.672 ~-0.62 {CustomName:\""); break;
				case 33: sb.append("~-1.2 ~1.672 ~-0.96 {CustomName:\""); break;
				case 34: sb.append("~-0.86 ~1.672 ~-1.3 {CustomName:\""); break;
				case 35: sb.append("~-0.52 ~1.672 ~-1.3 {CustomName:\""); break;
				case 36: sb.append("~-0.86 ~1.672 ~-0.96 {CustomName:\""); break;
				case 37: sb.append("~-0.52 ~1.672 ~-0.96 {CustomName:\""); break;
				case 38: sb.append("~-0.86 ~1.672 ~-0.62 {CustomName:\""); break;
				case 39: sb.append("~-0.52 ~1.672 ~-0.62 {CustomName:\""); break;
				case 40: sb.append("~-0.86 ~1.672 ~-0.28 {CustomName:\""); break;
				case 41: sb.append("~-0.52 ~1.672 ~-0.28 {CustomName:\""); break;
				case 42: sb.append("~-0.16 ~1.672 ~-0.62 {CustomName:\""); break;
				case 43: sb.append("~-0.16 ~1.672 ~-0.96 {CustomName:\""); break;
				case 44: sb.append("~-0.52 ~1.332 ~-0.96 {CustomName:\""); break;
				case 45: sb.append("~-0.86 ~1.332 ~-0.96 {CustomName:\""); break;
				case 46: sb.append("~-0.52 ~1.332 ~-0.62 {CustomName:\""); break;
				case 47: sb.append("~-0.86 ~1.332 ~-0.62 {CustomName:\""); break;
	
				case 48: sb.append("~0.16 ~2.352 ~-1.3 {CustomName:\""); break;
				case 49: sb.append("~0.16 ~2.352 ~-0.28 {CustomName:\""); break;
				case 50: sb.append("~-1.54 ~2.352 ~-1.3 {CustomName:\""); break;
				case 51: sb.append("~-1.54 ~2.352 ~-0.28 {CustomName:\""); break;
				case 52: sb.append("~0.16 ~0.652 ~-1.3 {CustomName:\""); break;
				case 53: sb.append("~0.16 ~0.652 ~-0.28 {CustomName:\""); break;
				case 54: sb.append("~-1.54 ~0.652 ~-1.3 {CustomName:\""); break;
				case 55: sb.append("~-1.54 ~0.652 ~-0.28 {CustomName:\""); break;
				default: return "null";
			}
		}
		sb.append(structureCode);
		sb.append(mossyStoneSuffix);
		return sb.toString();
	}
}
