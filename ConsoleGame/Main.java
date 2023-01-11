package ConsoleGame;

import java.util.Scanner;
//класс Main
public class Main extends FuncMinimax { //наследуем класс с минимаксом
    static int size = 0;   //размер поля
    static String[][] field; //двумерный массив игрового поля
    static String computer = "К"; //фигура компьютера
    static String human = "Ч";  //фигура человека
    static int alpha = -1000;  //переменная альфа
    static int beta = 1000;     //переменная бета
    static boolean currPlayer = true;   //переменная для контроля - чей ход(true-ход человека, false-ход компьютера)
    public static void main(String[] args) {
        System.out.print("Введите значение m, чтобы создать игровое поле m*m: ");
        size = checkInputValue();  //ввод размера поля
        field = new String[size][size]; //создание поля
        //перебираем все ячейки поля
        for(int i=0;i<size;i++) {
            for(int j=0;j<size;j++) {
                //делаем пустые все ячейки(заполняем нулями)
                field[i][j] = "0";
            }
        }
        //пока кто-то не выиграл/проиграл/ничья, или поле не стало пустым
        while(!checkFieldFullness(field)) {
            showPlayField(field);  //вывод игрового поля
            //если ход человека
            if(currPlayer) {
                int[] coordinates;  //массив для координат
                System.out.println("Делайте ход.");
                System.out.print("Вводите координаты клетки:");
                coordinates = inputCoordinates();  //ввод координат
                field[coordinates[0]][coordinates[1]] = human;  //записываем в эту ячейку фигуру человека
                currPlayer = !currPlayer;  //меняем переменную для контроля хода
            }
            //иначе ход бота
            else{
                System.out.print("Ходит бот: ");
                boolean flag = true;
                while (flag) {
                    int[] compCoordinates = computerChoice(field,alpha,beta);   //получаем координаты полученные алгоритмом Минимакс
                    System.out.println(compCoordinates[0]+" "+compCoordinates[1]);
                    if(field[compCoordinates[0]][compCoordinates[1]] == "0") {  //если ячейка пустая
                        field[compCoordinates[0]][compCoordinates[1]] = computer;   //записываем в эту ячейку фигуру компьютера
                        currPlayer = !currPlayer;  //меняем переменную для контроля хода
                        flag = false;
                    }
                }
            }
            removePlayers(human,computer,field);//удаляем фигуры обоих игроков после хода
            setFigureIfEmptyThree(computer, field);//ставим фигуру компьютера если три его фигуры вокруг какой-либо пустой клетки
            setFigureIfEmptyThree(human, field);//ставим фигуру человека если три его фигуры вокруг какой-либо пустой клетки
            System.out.println();//перенос строки
        }
        removePlayers(human,computer,field);//удаляем фигуры обоих игроков после хода
        setFigureIfEmptyThree(computer, field);//ставим фигуру компьютера если три его фигуры вокруг какой-либо пустой клетки
        setFigureIfEmptyThree(human, field);//ставим фигуру человека если три его фигуры вокруг какой-либо пустой клетки
        showPlayField(field);  //вывод игрового поля
        checkWhoWin(field);
    }
    //функция вывода игрового поля
    static void showPlayField(String[][] board) {
        //вывод индексов для удоства интерфейса
        int coordinate = 0;
        System.out.print("  _");
        for(int i=0;i<board.length;i++) {
            if(i<=9) {
                System.out.print(i+"_ ");
            }
            else{
                System.out.print(i+"_");
            }
        }
        System.out.println();
        for(int i=0;i<board.length;i++) {
            for(int j=0;j<board.length;j++) {
                if(j == 0) {
                    if(coordinate<=9) {
                        System.out.print(coordinate+" |");
                    }
                    else{
                        System.out.print(coordinate+"|");
                    }
                    coordinate++;
                }
                System.out.print(field[i][j]+"  ");
            }
            System.out.println();
        }
        /////////////////////////////////////////
    }
    //проверка на ввод значения типа int
    static int checkInputValue() {
        Scanner scanner = new Scanner(System.in);//класс ввода
        int value = 0;  //вспомогательная переменная типа int
        //если произошел ввод числа
        if(scanner.hasNextInt()) {
            value = scanner.nextInt();
            return value;   //возвращаем значение
        }
        //иначе требуем ввод заново(рекурсия)
        else{
            System.out.print("Вы ввели значение не соответствующее типу, вводите заново: ");
            return checkInputValue(); //рекурсивный вызов функции
        }
    }
    //ввод координат хода человека
    static public int[] inputCoordinates() {
        Scanner scanner = new Scanner(System.in);//класс ввода
        int row = 0;    //вспомогательная переменная типа int
        int col = 0;    //вспомогательная переменная типа int
        int[] coordinates = new int[2]; //хранение координат
        //если было введено значение строки типа int
        if(scanner.hasNextInt()) {
            row = scanner.nextInt();    //запоминаем
        }
        //иначе требуем ввод заново(рекурсия)
        else{
            System.out.print("Вы ввели значение не соответствующее типу, вводите заново: ");
            return inputCoordinates(); //рекурсивный вызов функции
        }
        //если было введено значение столбца типа int
        if(scanner.hasNextInt()) {
            col = scanner.nextInt();    //запоминаем
        }
        //иначе требуем ввод заново(рекурсия)
        else{
            System.out.print("Вы ввели значение не соответствующее типу, вводите заново: ");
            return inputCoordinates();  //рекурсивный вызов функции
        }

        //проверка индексов строки и столбца, исходя из размеров игрового поля
        if(row >= 0 && row < field.length && col >= 0 && col < field.length) {
            //если ячейка пустая
            if(field[row][col] == "0"){
                //запоминаем координаты
                coordinates[0] = row;
                coordinates[1] = col;
            }
            //иначе если позиция занята
            else{
                System.out.print("Данная позиция занята, вводите заново: ");
                return inputCoordinates(); //рекурсивный вызов функции
            }
            return coordinates;
        }
        //иначе если значения не попадают под рамки игрового поля
        else{
            System.out.print("Вы ввели значение не соответствующее, вводите заново: ");
            return inputCoordinates();  //рекурсивный вызов функции
        }
    }
}
