import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class YearlyReport {

    HashMap<Integer, Integer> yearsExpenses = new HashMap<>();
    HashMap<Integer, Integer> yearsIncome = new HashMap<>();
    boolean readConfirmation;

    YearlyReport(){
        readConfirmation = false;
    }

    void readYear() {
        String[] lines = readFileContentsOrNull("c:/dev/y.2021.csv").split(System.lineSeparator());
        for (int i = 1; i < lines.length; i++) {
            String[] lineContents = lines[i].split(",");
            if ("true".equals(lineContents[2])){
                yearsExpenses.put(Integer.parseInt(lineContents[0]), Integer.parseInt(lineContents[1]));
            } else if ("false".equals(lineContents[2])){
                yearsIncome.put(Integer.parseInt(lineContents[0]), Integer.parseInt(lineContents[1]));
            }
        }
        readConfirmation = true;
    }

    void printYearsResults (){
        System.out.println("Печать отчета за 2021 год:");

        for (Integer month : yearsExpenses.keySet()){
            System.out.println("Месяц номер " + (month + 1) + ":");
            System.out.println("Прибыль: " + (yearsIncome.get(month) - yearsExpenses.get(month)));
            }
int totalExpense = 0;
int averageExpense;
        for (Integer month : yearsExpenses.keySet()){
            totalExpense = yearsExpenses.get(month) + totalExpense;
        }
        averageExpense = totalExpense / yearsExpenses.size();
        System.out.println("Средний расход за все месяцы в году: " + averageExpense);

        int totalIncome = 0;
        int averageIncome;
        for (Integer month : yearsIncome.keySet()){
            totalIncome = yearsIncome.get(month) + totalIncome;
        }
        averageIncome = totalIncome / yearsIncome.size();
        System.out.println("Средний доход за все месяцы в году: " + averageIncome);
    }

    private String readFileContentsOrNull(String path)
    {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}
