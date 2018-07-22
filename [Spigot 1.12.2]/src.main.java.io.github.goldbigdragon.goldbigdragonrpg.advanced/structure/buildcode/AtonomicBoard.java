package structure.buildcode;

public class AtonomicBoard {

	private String prefix = "/summon minecraft:armor_stand ";

	private String stoneSuffix = "\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stone,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
	private String stoneSuffix2 = "\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stone,Damage:6,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
	
	public String createAtonomicBoard(int lineNumber, String structureCode)
	{
		if(lineNumber > 33)
			return "null";
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		switch(lineNumber)
		{
		  case 0: sb.append("~-0.18 ~0.652 ~-0.28 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 1: sb.append("~-0.18 ~0.652 ~0.4 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 2: sb.append("~-0.18 ~1.6720000000000002 ~-0.28 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 3: sb.append("~-0.18 ~1.6720000000000002 ~0.06 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 4: sb.append("~-0.18 ~1.6720000000000002 ~0.4 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 5: sb.append("~0.16000000000000003 ~0.652 ~-0.28 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 6: sb.append("~0.16000000000000003 ~0.652 ~0.4 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 7: sb.append("~0.16000000000000003 ~0.992 ~-0.28 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 8: sb.append("~0.16000000000000003 ~0.992 ~0.4 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 9: sb.append("~0.16000000000000003 ~1.6720000000000002 ~-0.28 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 10: sb.append("~0.16000000000000003 ~1.6720000000000002 ~0.06 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 11: sb.append("~0.16000000000000003 ~1.6720000000000002 ~0.4 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 12: sb.append("~0.5 ~0.992 ~0.06 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 13: sb.append("~0.5 ~1.332 ~0.06 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 14: sb.append("~0.5 ~1.6720000000000002 ~-0.28 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 15: sb.append("~0.5 ~1.6720000000000002 ~0.06 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 16: sb.append("~0.5 ~1.6720000000000002 ~0.4 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 17: sb.append("~0.8400000000000001 ~0.992 ~0.06 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 18: sb.append("~0.8400000000000001 ~1.332 ~0.06 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 19: sb.append("~0.8400000000000001 ~1.6720000000000002 ~-0.28 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 20: sb.append("~0.8400000000000001 ~1.6720000000000002 ~0.06 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 21: sb.append("~0.8400000000000001 ~1.6720000000000002 ~0.4 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 22: sb.append("~1.1800000000000002 ~0.652 ~-0.28 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 23: sb.append("~1.1800000000000002 ~0.652 ~0.4 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 24: sb.append("~1.1800000000000002 ~0.992 ~-0.28 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 25: sb.append("~1.1800000000000002 ~0.992 ~0.4 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 26: sb.append("~1.1800000000000002 ~1.6720000000000002 ~-0.28 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 27: sb.append("~1.1800000000000002 ~1.6720000000000002 ~0.06 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 28: sb.append("~1.1800000000000002 ~1.6720000000000002 ~0.4 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 29: sb.append("~1.5200000000000002 ~0.652 ~-0.28 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 30: sb.append("~1.5200000000000002 ~0.652 ~0.4 {CustomName:\""+structureCode+stoneSuffix); break;
		  case 31: sb.append("~1.5200000000000002 ~1.6720000000000002 ~-0.28 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 32: sb.append("~1.5200000000000002 ~1.6720000000000002 ~0.06 {CustomName:\""+structureCode+stoneSuffix2); break;
		  case 33: sb.append("~1.5200000000000002 ~1.6720000000000002 ~0.4 {CustomName:\""+structureCode+stoneSuffix2); break;
		}
		return sb.toString();
	}
}
