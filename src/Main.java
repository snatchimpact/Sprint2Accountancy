import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //Создаем пустые объекты на базе классов: один годовой отчет, и одну матрицу - с тремя месячными отчетами
        YearlyReport report2021 = new YearlyReport();
        MonthlyReport [] monthlyReports = new MonthlyReport[3];
        for (int i = 1; i <= 3; i++){
            monthlyReports[i-1] = new MonthlyReport(i);
        }


       while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 0) {
                System.out.println("Выход.");
                break;
            } else if (command == 1) {
                //импортируем месячные отчеты, они у нас в виде матрицы с объктами на базе класса для месяца
                for (int i = 1; i <= 3; i++){
                    monthlyReports[i-1].readMonth();
                }
            } else if (command == 2) {
                //импортируем годовой отчет
                report2021.readYear();
            } else if (command == 3) {
                //Создаем переменную с информацией об успешном прохождении проверки
                boolean successfulCheck = true;
                //Запускаем бесконечный цикл с возможностью прервать его после одного из условий, указывая что он внешний
                // (это нужно для разрыва из вложенного помесячного цикла
                outerloop:
                while (true) {
                    //Проверяем наличие загруженных данных по всем месяцам
                    for (int i = 0; i < 3; i++) {
                        if (!monthlyReports[i].readConfirmation) {
                            System.out.println("Данные за месяц " + (i + 1) + " не загружены, проводить сверку нельзя!");
                            break outerloop;
                        }
                    }
                    //Проверяем наличие загруженных данных по году
                    if (!report2021.readConfirmation) {
                        System.out.println("Данные за год не загружены, проводить сверку нельзя!");
                        break;
                    }

                        for (int i = 0; i < 3; i++) {
                            int j = i + 1;

                            if (!(report2021.yearsIncome.get(j) == monthlyReports[i].getTotalIncomeMonth())) {
                                System.out.println("Сумма доходов за месяц " + j + " расходится 8(((");
                                System.out.println("Доходы в отчете за месяц " + j + " равны " + monthlyReports[i].getTotalIncomeMonth());
                                System.out.println("В годовом отчете в месяце " + j + " доход " + report2021.yearsIncome.get(j));
                                successfulCheck = false;
                                                            }

                            if (!(report2021.yearsExpenses.get(j) == monthlyReports[i].getTotalExpensesMonth())) {
                                System.out.println("Сумма расходов за месяц " + j + " расходится 8(((");
                                System.out.println("Расходы в отчете за месяц " + j + " равны " + monthlyReports[i].getTotalExpensesMonth());
                                System.out.println("В годовом отчете в месяце " + j + " расходы " + report2021.yearsExpenses.get(j));

                                successfulCheck = false;
                            }
                        }

                    if (successfulCheck) {
                        System.out.println("Операция сверки завершена успешно");
                        }
                    break;
                }
            } else if (command == 4) {
                System.out.println("Печать всех месячных отчетов.");
                for (int i = 0; i < 3; i++) {
                    int j = i + 1;
                    System.out.println("Месяц номер " + j + ":");
                    System.out.println(monthlyReports[i].highestRevenueItemMonth());
                    System.out.println(monthlyReports[i].highestExpenseItemMonth());
                }
            } else if (command == 5) {
                report2021.printYearsResults();
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }



    public static void printMenu() {
            System.out.println("");
            System.out.println("Что вы хотите сделать?");
            System.out.println("1 - Считать все месячные отчёты.");
            System.out.println("2 - Считать годовой отчёт.");
            System.out.println("3 - Сверить отчёты.");
            System.out.println("4 - Вывести информацию о всех месячных отчётах.");
            System.out.println("5 - Вывести информацию о годовом отчёте.");
            System.out.println("0 - Выход.");
        }
    }