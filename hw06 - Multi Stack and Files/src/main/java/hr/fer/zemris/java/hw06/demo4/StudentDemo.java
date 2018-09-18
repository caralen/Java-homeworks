package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that contains private methods for stream operations on a list of StudentRecords.
 * This is also the main class of the program.
 * @author Alen Carin
 *
 */
public class StudentDemo {

	/**
	 * Main method which is called upon the start of the program. 
	 * Reads lines from the file studenti.txt converts it to a list of StudentRecords
	 * then calls methods for stream operations and prints out the result.
	 * @param args command line arguments, not used here.
	 */
	public static void main(String[] args) {
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Cannot read from the file");
			System.exit(-1);
		}
		List<StudentRecord> records = convert(lines);
		
		/* 1. Broj studenata koji u sumi MI+ZI+LAB imaju vise od 25 bodova */
		long broj = vratiBodovaViseOd25(records);
		System.out.println("1) Broj studenata koji imaju vise od 25 bodova:");
		System.out.println(broj);
		System.out.println();
		
		/* 2. Broj studenata koji su dobili ocjenu 5 */
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println("2) Broj odlikaša:");
		System.out.println(broj5);
		System.out.println();
		
		/* 3. Lista studenata koji su dobili ocjenu 5 */
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		System.out.printf("3) Lista odlikasa:");
		odlikasi.stream().forEach(s -> System.out.printf("%s", s));
		System.out.println();
		System.out.println();
		
		/* 4. Silazno po bodovima sortirana lista studenata koji su dobili ocjenu 5 */
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		System.out.printf("4) Lista odlikasa sortirano");
		odlikasiSortirano.stream().forEach(s -> System.out.printf("%s", s));
		System.out.println();
		System.out.println();
		
		/* 5. Lista JMBAG-ova studenata koji nisu položili kolegij */
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.println("5) Popis jmbaga koji nisu polozili");
		nepolozeniJMBAGovi.stream().forEach(s -> System.out.println(s));
		System.out.println();
		
		/* 6. Mapa <ocjena, lista studenata s tom ocjenom> */
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.println("6) Razvrstani studenti po ocjenama");
		mapaPoOcjenama.forEach((t,u) -> System.out.format("%s => %s%n", t, u));
		System.out.println();
		
		/* 7. Mapa <ocjena, broj studenata s tom ocjenom> */
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.println("7) Broj studenata po ocjenama");
		mapaPoOcjenama2.forEach((t,u) -> System.out.format("%s => %s%n", t, u));
		System.out.println();
		
		/* 8. Mapa <prolaz ili pad, lista studenata s tom ocjenom> */
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("8) Razvrstani studenti na prolaz i pad");
		prolazNeprolaz.forEach((t,u) -> 
			System.out.format("%s => %s%n", t == true ? 
					"Prosli (" + u.size() + ")" 
					: "Pali (" + u.size() + ")", u));
	}

	/**
	 * Converts list of Strings to list of StudentRecords.
	 * @param lines list of strings from the file which will be converted into list of StudentRecords
	 * @return list of StudentRecords
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		for(String line : lines) {
			String[] lineParts = line.split("\\s+");
			records.add(new StudentRecord(lineParts[0], 
					lineParts[1], 
					lineParts[2], 
					Double.parseDouble(lineParts[3]), 
					Double.parseDouble(lineParts[4]), 
					Double.parseDouble(lineParts[5]), 
					Integer.parseInt(lineParts[6])
					));
		}
		return records;
	}
	
	/**
	 * Returns number of students that have more than 25 points on first exam,
	 * final exam and labs altogether.
	 * @param records list of StudentRecords.
	 * @return number of students that satisfy the criteria.
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		long broj = records.stream()
				.filter(s -> (s.getFirstExamPoints() + s.getLastExamPoints() + s.getLabsPoints() > 25))
				.count();
		return broj;
	}
	
	/**
	 * Returns the number of students with final grade equal to 5.
	 * @param records list of StudentRecords.
	 * @return number of students that satisfy the criteria.
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		long broj5 = records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.count();
		return broj5;
	}
	
	/**
	 * Returns list of StudentRecords with final grade equal to 5.
	 * @param records list of StudentRecords.
	 * @return list of students that satisfy the criteria.
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records){
		List<StudentRecord> odlikasi = records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.collect(Collectors.toList());
		return odlikasi;
	}
	
	/**
	 * Returns list of StudentRecords with final grade equal to 5, 
	 * sorted so that those with higher amount of points are at the top.
	 * @param records list of StudentRecords.
	 * @return list of students that satisfy the criteria.
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records){
		
		Comparator<StudentRecord> comparator = new Comparator<>() {
			@Override
			public int compare(StudentRecord o1, StudentRecord o2) {
				double firstStudentPoints = o1.getFirstExamPoints() + o1.getLastExamPoints() + o1.getLabsPoints();
				double secondStudentPoints = o2.getFirstExamPoints() + o2.getLastExamPoints() + o2.getLabsPoints();
				if(firstStudentPoints < secondStudentPoints) return 1;
				if(firstStudentPoints > secondStudentPoints) return -1;
				return 0;
			}
		};
		
		List<StudentRecord> odlikasiSortirano = records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.sorted(comparator)
				.collect(Collectors.toList());
		
		return odlikasiSortirano;
	}
	
	/**
	 * Returns list of student JMBAGs for students which didn't pass the subject,
	 * sorted so that those students with higher JMBAG are at the bottom.
	 * @param records list of StudentRecords.
	 * @return list of students that satisfy the criteria.
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records){
		
		Comparator<StudentRecord> comparator = new Comparator<>() {
			@Override
			public int compare(StudentRecord o1, StudentRecord o2) {
				return o1.getJmbag().compareTo(o2.getJmbag());
			}
		};
		
		List<String> nepolozeniJMBAGovi = records.stream()
				.filter(s -> s.getFinalGrade() == 1)
				.sorted(comparator)
				.map(s -> s.getJmbag())
				.collect(Collectors.toList());
		
		return nepolozeniJMBAGovi;
	}
	
	/**
	 * Returns a map where keys are grades from 1 to 5 and values are lists of StudentRecords
	 * for students which received that grade.
	 * @param records list of StudentRecords.
	 * @return map with Integer key and a list of StudentRecords as value.
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records){
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
		
		return mapaPoOcjenama;
	}
	
	/**
	 * Returns map where keys are grades from 1 to 5 and values are number of
	 * students which acquired that grade.
	 * @param records list of StudentRecords.
	 * @return map where both key and value are Integers.
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records){
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
				.collect(Collectors.toMap(StudentRecord::getFinalGrade, s -> 1, Integer::sum));
		return mapaPoOcjenama2;
	}
	
	/**
	 * Returns map where key can be true or false. Under the "true" key is stored
	 * a list of StudentRecords for students which passed the subject, 
	 * i.e. acquired grade greater than 1, and under the key false are stored those students
	 * which failed to pass the subject.
	 * @param records list of StudentRecords.
	 * @return map with Boolean key, and a list of StudentRecords value.
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records){
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
				.collect(Collectors.partitioningBy(s -> s.getFinalGrade() > 1));
		return prolazNeprolaz;
	}
}
