package org.airsim.api.flightplan;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;

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

	public static Weekplan fromString(String input) {
		if (input.startsWith("X")) {
			return Weekplan.builder()
					.monday(!input.contains("1"))
					.tuesday(!input.contains("2"))
					.wedneyday(!input.contains("3"))
					.thursday(!input.contains("4"))
					.friday(!input.contains("5"))
					.saturday(!input.contains("6"))
					.sunday(!input.contains("7"))
					.build();
		} else {
			return Weekplan.builder()
					.monday(input.contains("1"))
					.tuesday(input.contains("2"))
					.wedneyday(input.contains("3"))
					.thursday(input.contains("4"))
					.friday(input.contains("5"))
					.saturday(input.contains("6"))
					.sunday(input.contains("7"))
					.build();
		}
	}

	public boolean appliesTo(LocalDate localDate) {
		switch (localDate.getDayOfWeek()) {
			case MONDAY: return isMonday();
			case TUESDAY: return isTuesday();
			case WEDNESDAY: return isWedneyday();
			case THURSDAY: return isThursday();
			case FRIDAY: return isFriday();
			case SATURDAY: return isSaturday();
			case SUNDAY: return isSunday();
			default: throw new RuntimeException();
		}
	}
}
