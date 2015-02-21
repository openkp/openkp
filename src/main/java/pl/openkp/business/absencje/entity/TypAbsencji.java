/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.openkp.business.absencje.entity;

import javax.json.JsonValue;

public enum TypAbsencji {
	BEZPLATNA("bezpłatna"), URLOP("urlop"), CHOROBA("choroba"), SZPITAL("szpital");
	
	private String opis;
	
	private TypAbsencji(String opis) {
		this.opis = opis;
	}
	
	public String getOpis() {
		return opis;
	}
	
	@Override
	public String toString() {
		return opis;
	}

	public static TypAbsencji fromOpis(String opis) {
		for (TypAbsencji typ : TypAbsencji.values()) {
			if (typ.getOpis().equals(opis)) {
				return typ;
			}
		}
		return null;
	}
}
