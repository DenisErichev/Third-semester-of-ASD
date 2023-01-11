package Laba2.src;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Задача 10.\nДана разреженная матрицa (CCS). Зеркальное отображение относительно\n" +
                "диагонали, проходящей с левого нижнего угла к правому верхнему углу.");
        Scanner scanner1 = new Scanner(new File("/home/denis/JavaPrograms/src/Laba2/src/matrix.txt"));
        int row = scanner1.nextInt();
        int col = scanner1.nextInt();
        int n=0;
        int count = 1;   //переменная для подсчета ненул. элементов
        int length = 0;  //количество элементов для массивов : A, LI
        int[] LI;        //массив номеров строк
        int[] LJ;        //массив местоположения первого элемента каждого столбца
        int[] A;         //массив для ненул. элементов
        int index = 0;
        int[][] matrix = readMatrixInFile("/home/denis/JavaPrograms/src/Laba2/src/matrix.txt");  //считываем матрицу из файла
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                if(matrix[i][j] != 0){
                    length++;           //подсчитываем количество ненул. элементов
                }
            }
        }
        A = new int[length];            //создаем динамический массив
        LI = new int[length];           //создаем динамический массив
        for(int j=0;j<col;j++) {
            for(int i=0;i<row;i++) {
                if(matrix[i][j] != 0){
                    A[index] = matrix[i][j];    //запоминаем ненул. элементы
                    LI[index] = i+1;            //запоминаем строки+1
                    index++;
                }
            }
        }

        int[] LJ_copy = new int[length];       //копия массива LJ, чтобы потом избавиться от нулей
        for(int j=0;j<col;j++) {
            for(int i=0;i<row;i++) {
                if(matrix[i][j] != 0) {        //если встрели столбец где есть ненул. элемент.
                    for(int k=0;k<row;k++) {   //заново проходим по этому столбцу с 0 индекса
                        if (matrix[k][j] != 0) {
                            count++;            //запоминаем количество ненул. элементов в столбце
                        }
                    }
                    LJ_copy[n+1] = count;       //запоминаем в n+1 количество элементов
                    break;                      //выходим из цикла и переходим к след.столбцу
                }
            }
            if(LJ_copy[n] == 0 && n>0){         //если не зашли в цикл выше
                LJ_copy[n] = LJ_copy[n - 1];    //в пустую ячейк LJ запоминаем n-1 элемент
            }
            else if(LJ_copy[n] == 0) {          //если LJ элемент = 0
                LJ_copy[n] = n+1;               //записываем в массив n+1, т.к. в первой ячейке LJ всегда будет значение 1
            }
            n++;
        }
        int LJ_length = 0;                  //переменная длины LJ массива, без нулей
        for(int i=0;i<LJ_copy.length;i++) {
            if(LJ_copy[i] != 0) {
                LJ_length++;                //длина LJ массива, без нулей
            }
        }
        LJ = new int[LJ_length];            //создаем массив LJ без нулей
        for(int i=0;i<LJ.length;i++) {
            if(LJ_copy[i] != 0) {
                LJ[i] = LJ_copy[i];         //запоминаем ненул. элементы массива LJ
            }
        }

        System.out.println("Разреженнная матрица:");
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                System.out.print(matrix[i][j]+"\t");    //вывод начальной матрицы
            }
            System.out.println();
        }
        System.out.println("\nНенулевые элементы разреженной матрицы:");
        for(Integer elem: A) {
            System.out.print(elem+" | ");                                //вывод массива A
        }
        System.out.println("\nНомера строк:");
        for(Integer li: LI){
            System.out.print(li+" | ");                                  //вывод массива LI
        }
        System.out.println("\nМестоположение элемента каждого столбца:");
        for(Integer lj: LJ){
            System.out.print(lj+" | ");                                 //вывод массива LJ
        }
        int temp;                                       //переменная для запоминания промежуточного элемента массива
        int newMatrix[][] = new int[row][col];          //создаем динамич. массив для записи в него новой матрицы
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                temp = matrix[i][j];                    //запоминаем элемент нач. матрицы
                newMatrix[i][j] = matrix[row - j - 1][col - i - 1];//идем по столбцам с конца матрицы, меняем элемент в начале матрицы
                newMatrix[row - j - 1][col - i - 1] = temp;        //меняем элемент с конца матрицы
            }
        }
        System.out.println("\nЗеркальное отображение относительно диагонали," +
                "проходящей с левого нижнего угла к правому верхнему углу.:");
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                System.out.print(newMatrix[i][j]+"\t");     //вывод новой матрицы
            }
            System.out.println();
        }
    }
    static int[][] readMatrixInFile(String path) throws FileNotFoundException{      //метод считывания матрицы из файла
        Scanner scanner = new Scanner(new File(path));
        int row=scanner.nextInt();     //считываем количество строк из файла
        int col=scanner.nextInt();     //считываем количество столбцов из файла
        int[][] matrix = new int[row][col]; //создаем двум. динамич. массив
        try {
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    matrix[i][j]=scanner.nextInt(); //запоминаем элементы по-символьно

                }
            }
        }catch (InputMismatchException e){      //проверка на некорректные значения в файле
            System.out.println("В файле присутствует элемент не соответствующий типу!");
            System.out.println("Данный элемент заменен на 0.");
        }
        return matrix;
    }
}
//6
//6
//2 0 0 1 0 0
//0 0 0 0 0 0
//0 5 0 0 0 1
//0 0 0 0 9 1
//0 0 0 0 0 0
//0 8 0 4 0 3