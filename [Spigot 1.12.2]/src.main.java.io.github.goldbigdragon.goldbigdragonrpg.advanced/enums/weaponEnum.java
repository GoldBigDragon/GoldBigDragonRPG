/*
 * Copyright 2018 GoldBigDragon
 * 
 * GoldBigDragonRPG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License.

 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package enums;

public enum weaponEnum {
	_267("한손 검"),
	_268("한손 검"),
	_272("한손 검"),
	_276("한손 검"),
	_283("한손 검"),
	_271("도끼"),
	_275("도끼"),
	_258("도끼"),
	_279("도끼"),
	_286("도끼"),
	_290("낫"),
	_291("낫"),
	_292("낫"),
	_293("낫"),
	_294("낫"),
	_261("활"),
	_23("석궁"),
	_280("원드"),
	_352("원드"),
	_369("스태프"),
	UNKNOWN("기타");
	
	public final String itemType;
	
	weaponEnum(String itemType)
	{
		this.itemType = itemType;
	}
}