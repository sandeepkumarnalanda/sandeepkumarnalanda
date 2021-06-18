
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Tennis {
	// function to generate the next date in proper format dd/MM/yyyy
	public static String getNextDate(String date) {
		int day = Integer.valueOf(date.split("/")[0]);
		int month = Integer.valueOf(date.split("/")[1]);
		int year = Integer.valueOf(date.split("/")[2]);
		LocalDateTime ldt = LocalDateTime.of(year, month, day, 0, 0, 0, 0);
		ldt = ldt.plusDays(1);

		// custom date formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// Format LocalDateTime
		String formattedDateTime = ldt.format(formatter);

		return formattedDateTime;
	}

	// function to check if input date is valid or not in dd/MM/yyyy format
	static boolean checkDate(String date) {
		String pattern = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";
		boolean flag = false;
		if (date.matches(pattern)) {
			flag = true;
		}
		return flag;
	}

	// function to generate the schedule
	public static void generateSchedule(String startDate, int noOfPpl, int noOfGrnd, String[] slots) {
		// 1. check the parameters
		if (startDate != null && !"".equals(startDate) && noOfPpl > 1 && noOfGrnd > 0 && slots.length > 0) {
			// 2. check the date format if valid or not
			if (checkDate(startDate)) {
				// 3. now generate the proper schedule logic starts here
				int totalNoOfMatches = noOfPpl * (noOfPpl - 1) / 2;

				int groundInitialVal = 1;
				int slotInitialVal = 1;
				String date = startDate;

				List<String> historyOfMatchBetween = new ArrayList<>();
				int person1 = 1;
				int person2 = 2;
				int prefixPerson = 0;
				
				for (int i = 0; i < totalNoOfMatches; i++) {
					prefixPerson  = person1;
					System.out.println("Person " + person1 + " vs Person " + person2 + " " + "at Ground "
							+ groundInitialVal + " on " + date + " at " + slots[slotInitialVal - 1]);
					// ground value changes
					if (groundInitialVal == noOfGrnd) {
						groundInitialVal = 1;
						// date calculation as per slot
						if (slotInitialVal == slots.length) {
							date = getNextDate(date);
							slotInitialVal = 1;
						} else {
							slotInitialVal++;
						}
					} else {
						groundInitialVal++;
					}
					
					
					// save the previously used person
					historyOfMatchBetween.add(person1 + "," + person2);
					// logic to print the next persons
					if (person2 + 1 < noOfPpl) {
						person1 = person2 + 1;
						person2 = person1 + 1;
					}else {
						boolean flag = true;
						for (int k = 1;flag && k < noOfPpl; k++) {
							if(prefixPerson == k) {
								continue;
							}
							for (int l = k + 1; l <= noOfPpl; l++) {
								person1 = k;
								person2 = l;	
								if (!historyOfMatchBetween.contains(person1 + "," + person2)) {
									flag = false;
									break;
								}
							}
						}
					}
				}
			} else {
				System.out.println("date format is invalid. date format must be dd/MM/yyyy");
			}
		} else {
			System.out.println("Please pass the correct input parameter in function.");
		}
	}

	public static void main(String[] args) {
		// testing program
		generateSchedule("15/11/2020", 4, 2, new String[] { "9:00 AM", "02:00 PM" });
	}
}
