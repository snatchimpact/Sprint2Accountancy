import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {

    HashMap<String, ArrayList<Integer>> monthsExpenses = new HashMap<>();
    HashMap<String, ArrayList<Integer>> monthsIncome = new HashMap<>();
    int readMonth;
    boolean readConfirmation;
    int totalExpensesMonth;
    int totalIncomeMonth;

    MonthlyReport(int month){
        readMonth = month;
        readConfirmation = false;
    }

    void readMonth() {
        String[] lines = readFileContentsOrNull("c:/dev/m.20210" + readMonth + ".csv").split(System.lineSeparator());
        for (int i = 1; i < lines.length; i++) {
            String[] lineContents = lines[i].split(",");
            ArrayList<Integer> transactions = new ArrayList<>();
            transactions.add(Integer.parseInt(lineContents[2]));
            transactions.add(Integer.parseInt(lineContents[3]));
            if ("TRUE".equals(lineContents[1])){
                monthsExpenses.put(lineContents[0], transactions);
            } else if ("FALSE".equals(lineContents[1])){
                monthsIncome.put(lineContents[0], transactions);
            }
        }
        readConfirmation = true;
    }

    String highestRevenueItemMonth () {
        String bestItem = null;
        int bestRevenue = 0;
        int currentItemsRevenue = 0;
        for (String item : monthsIncome.keySet()){
            currentItemsRevenue = monthsIncome.get(item).get(0) * monthsIncome.get(item).get(1);
            if (currentItemsRevenue > bestRevenue) {
                bestRevenue = currentItemsRevenue;
                bestItem = item;
            }
        }
        return ("Самый прибыльный товар: " + bestItem + ". Сумма дохода: " + bestRevenue);
    }

    String highestExpenseItemMonth () {
        String mostExpensiveItem = null;
        int highestExpense = 0;
        int currentItemsExpense = 0;
        for (String item : monthsExpenses.keySet()){
            currentItemsExpense = monthsExpenses.get(item).get(0) * monthsExpenses.get(item).get(1);
            if (currentItemsExpense > highestExpense) {
                highestExpense = currentItemsExpense;
                mostExpensiveItem = item;
            }
        }
        return ("Больше всего потрачено на: " + mostExpensiveItem + ". Сумма расходов: " + highestExpense);
    }

    int getTotalExpensesMonth() {
        totalExpensesMonth = 0;
        for (ArrayList<Integer> transactions : monthsExpenses.values()) {
                totalExpensesMonth = totalExpensesMonth + transactions.get(0) * transactions.get(1);
        }
        return totalExpensesMonth;
    }

    int getTotalIncomeMonth(){
        totalIncomeMonth = 0;
        for (ArrayList<Integer> transactions : monthsIncome.values()) {
            totalIncomeMonth = totalIncomeMonth + transactions.get(0) * transactions.get(1);
        }
        return totalIncomeMonth;
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
