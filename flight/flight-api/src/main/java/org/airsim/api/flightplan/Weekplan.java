package org.airsim.api.flightplan;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Weekplan {

	private boolean monday;
	private boolean tuesday;
	private boolean wedneyday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;

	public static Weekplan daily() {
		return new Weekplan(true, true, true, true, true, true, true);
	}
	
	public static Weekplan weekdays() {
		return new Weekplan(true, true, true, true, true, false, false);
	}

}
