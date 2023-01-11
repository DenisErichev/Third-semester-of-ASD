package Laba1;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args ) throws Exception {
        List<Integer> lst = new List<>();
        addListElementsInFile(lst,"/home/denis/JavaPrograms/src/Laba1/file.txt");
//        inputElements(lst);
        lst.print();
        lst.replaceFrontAndBack();
        lst.print();
    }
    static void addListElementsInFile(List<Integer> lst, String path)  throws Exception {
        try{
            System.out.println("Элементы списка считываются из файла.");
            FileReader fr = new FileReader(path);
            Scanner scanner = new Scanner(fr);
            while(scanner.hasNext()) {
                lst.addBack(scanner.nextInt());
            }
            fr.close();
        } catch (InputMismatchException e) {
            System.out.println("Из файла был считан элемент  не соответствующий типу.");
            System.out.println("Данный элемент был удален.");
        }catch (FileNotFoundException e) {
            System.out.println("Ошибка открытия файла!");

        }
    }
    static int input(int value) {
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextInt()){
            value = scanner.nextInt();
            return value;
        }else{
            System.out.println("Вы ввели значение не соответствующее типу, вводите заново: ");
            return input(value);
        }
    }
    static void inputElements(List<Integer> lst) {
        int value = 0;
        int countValue = 0;
        System.out.print("Введите количество элементов целочисленного списка: ");
        countValue = input(countValue);
        System.out.println("Вводите элементы списка: ");
        for(int i=0;i<countValue;i++) {
            value = input(value);
            lst.addBack(value);
        }

    }
}
