package ConsoleGame;

import java.util.Random;
import java.util.Scanner;

public class FuncMinimax {
    static String computer = "К";
    static String human = "Ч";
    static String[][] copyBoard;
    static void makeCopyBoard(String[][] arr) {
        copyBoard = new String[arr.length][arr.length];
        for(int i=0;i<arr.length;i++) {
            for(int j=0;j<arr.length;j++) {
                copyBoard[i][j] = arr[i][j];
            }
        }
    }
    //функция минимакс
     static int minimax(int depth, String[][] arr, boolean currPlayer,int alpha, int beta) {
         //проверяем клетку на выигрыш компьютера
         int computerScore = putIfAroundTree(human, computer,arr);
         //если компьютер вокруг фигуры человека
         if (computerScore == 3)  {
             //возвращем наибольшую оценку
             return computerScore;
         }
         //проверяем клетку на выигрыш человека
         int humanScore = putIfAroundTree(computer, human,arr);
         //если человек вокруг фигуры компьютера
         if (humanScore == 3)  {
             //возвращем наименьшую оценку
            return -humanScore;
         }
         //проверяем поле на заполненность
         if(checkEmptyField(arr)) {
            return 0;
         }
         if(arr.length>=5 && depth == 1 && boardLen(arr)>=1 && boardLen(arr) <=15){
             return 0;
         }
         //ограничиваем глубину просмотра ходов(оптимизация)
         if(depth == arr.length) {
            return 0;
         }
         //если текущий ход компьютера
         if(currPlayer) {
             //вспомогательная переменная для поиска наиб. значения
            int bestScore = -1000;
             //перебираем ходы
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr.length; j++) {
                    //если клетка пустая
                    if (arr[i][j] == "0") {
                        //делаем ход компьютера
                        arr[i][j] = computer;
                        //вызываем функцию минимакс, для получения значения
                        int currScore = minimax(++depth, arr, false, alpha, beta);
                        //очищаем оцениваемую клетку
                        arr[i][j] = "0";
                        //находим максимальное значение
                        if (currScore > bestScore) {
                            bestScore = currScore;
                        }
                        if((bestScore == 3 || bestScore == -3) && arr.length >=5 && boardLen(copyBoard) >=1 && boardLen(copyBoard) <= 10 ) {
                            return bestScore;
                        }
                        //находим максимальное alpha
                        if (bestScore > alpha) {
                            alpha = bestScore;
                        }
                        //если уже нашли выигрышную позицию
                        if (alpha >= beta) {
                            //возвращаем лучшую оценку
                            return alpha;
                        }
                    }
                }
            }
             //возвращем оценку
            return bestScore;
         }
         //если текущий ход человека
         else {
             //вспомогательная переменная для поиска наим. значения
            int bestScore = 1000;
             //перебираем ходы
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr.length; j++) {
                    //если клетка пустая
                    if (arr[i][j] == "0") {
                        //делаем ход человека
                        arr[i][j] = human;
                        //вызываем функцию минимакс, для получения значения
                        int currScore = minimax(++depth, arr, true, alpha, beta);
                        //очищаем оцениваемую клетку
                        arr[i][j] = "0";
                        //находим минимальное значение
                        if (currScore < bestScore) {
                            bestScore = currScore;
                        }
                        if((bestScore == 3 || bestScore == -3) && arr.length >=5 && boardLen(copyBoard) >=1 && boardLen(copyBoard) <= 6) {
                            return bestScore;
                        }
                        //находим минимальное beta
                        if (bestScore < beta) {
                            beta = bestScore;
                        }
                        //если уже нашли выигрышную позицию
                        if (alpha >= beta) {
                            //возвращаем худшую оценку
                            return beta;
                        }
                    }
                }
            }
             //возвращем оценку
            return bestScore;
         }
    }
    static int[] randIndexes(String[][] arr) {
        int[] indexes = new int[2];
        Random random = new Random();
        boolean flag = true;
        while (flag) {
            int row = random.nextInt((arr.length - 1) +1);
            int col = random.nextInt((arr.length - 1) +1);
            if(arr[row][col] == "0" ){
                indexes[0] = row;
                indexes[1] = col;
                return indexes;
            }
        }
        return indexes;
    }
    static int boardLen(String[][] arr) {
         int counter = 0;
         for(int i=0;i<arr.length;i++) {
             for(int j=0;j<arr.length; j++) {
                 if(arr[i][j] != "0") {
                     counter++;
                 }
             }
         }
         return counter;
    }
    //функия, которая совершает первый ход бота(корень игрового дерева)
     static int[] computerChoice(String[][] arr, int alpha, int beta) {
        makeCopyBoard(arr);
         //массив для столбца и строки лучшего хода
         int[] indexes = new int[2];
//         if(arr.length >= 5 && arr.length <= 10 && (boardLen(arr) >= 1 && boardLen(arr) <= 3)) {
//             return randIndexes(arr);
//         }
         //глубина рекурсии
        int depth = 0;
         //вспомогательная переменная для поиска наиб. значения
        int bestScore = -1000;
         //перебираем ходы
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                //если клетка пустая
                if (arr[i][j] == "0") {
                    //делаем ход компьютера
                    arr[i][j] = computer;
                    //вызываем функцию минимакс, для получения значения
                    int currScore = minimax(++depth, arr, false, alpha, beta);
                    //очищаем оцениваемую клетку
                    arr[i][j] = "0";
                    //находим максимальное значение
                    if(currScore >= bestScore) {
                        bestScore = currScore;//запоминаем наибольший счет
                        indexes[0] = i; //запоминаем строку
                        indexes[1] = j; //запоминаем столбец
                    }
                }
            }
        }
        //возвращаем массив с индексами хода
        return indexes;
    }

    //функция оценки, если вокруг player с 3-х сторон находится opponent
     static int putIfAroundTree(String player, String opponent, String[][] playerBoard) {
        for(int i=0;i<playerBoard.length;i++) {
            for(int j=0;j<playerBoard.length;j++) {
                //если ячейка равна игроку
                if (playerBoard[i][j] == player) {
                    //слева справа сверху opponent
                    if ((j - 1 >= 0 && playerBoard[i][j - 1] == opponent) && (j + 1 < playerBoard.length && playerBoard[i][j + 1] == opponent) && (i - 1 >= 0 && playerBoard[i - 1][j] == opponent)) {
                        return 3;
                    }
                    //слева справа снизу opponent
                    else if ((j - 1 >= 0 && playerBoard[i][j - 1] == opponent) && (j + 1 < playerBoard.length && playerBoard[i][j + 1] == opponent) && (i + 1 < playerBoard.length && playerBoard[i + 1][j] == opponent)) {
                        return 3;
                    }
                    //слева сверху снизу opponent
                    else if ((j - 1 >= 0 && playerBoard[i][j - 1] == opponent) && (i - 1 >= 0 && playerBoard[i - 1][j] == opponent) && (i + 1 < playerBoard.length && playerBoard[i + 1][j] == opponent)) {
                        return 3;
                    }
                    //справа сверху снизу opponent
                    else if ((j + 1 < playerBoard.length && playerBoard[i][j + 1] == opponent) && (i - 1 >= 0 && playerBoard[i - 1][j] == opponent) && (i + 1 < playerBoard.length && playerBoard[i + 1][j] == opponent)) {
                        return 3;
                    }
                    //слева сверху, сверху слева
                    else if((j-1 >= 0 && playerBoard[i][j - 1] == opponent)&&(i-1 >= 0 && playerBoard[i - 1][j] == opponent) && (i-1 >= 0 && j-1>=0 && playerBoard[i-1][j-1] == opponent)) {
                        return 3;
                    }
                    //справа сверху, справа сверху
                    else if((j+1 < playerBoard.length && playerBoard[i][j+1] == opponent) && (j+1 <playerBoard.length && i-1>=0  && playerBoard[i-1][j+1] == opponent) && (i-1>=0 && playerBoard[i-1][j]==opponent)) {
                        return 3;
                    }
                    //справа снизу, справа снизу
                    else if((i+1 <playerBoard.length && playerBoard[i+1][j] ==opponent) && (j+1<playerBoard.length && playerBoard[i][j+1] == opponent) && (i+1<playerBoard.length && j+1 < playerBoard.length && playerBoard[i+1][j+1] == opponent)) {
                        return 3;
                    }
                    //слева снизу, слева снизу
                    else if((i+1 <playerBoard.length && playerBoard[i+1][j] == opponent) && (j-1>=0 && playerBoard[i][j-1] == opponent) && (i+1<playerBoard.length && j-1>=0 && playerBoard[i+1][j-1] == opponent)) {
                        return 3;
                    }
                }
            }
        }
        return 0;
    }

    //проверка поля на заполненность
    static boolean checkEmptyField(String[][] arr) {
        int count = 0;//счетчик для заполненных ячеек
        for(int i=0;i<arr.length;i++) {
            for(int j=0;j<arr.length;j++) {
                //если ячейка не пуста
                if(arr[i][j] != "0") {
                    //увеличиваем счетчик
                    count++;
                }
            }
        }
        //если на поле еще есть место-true, иначе false
        return (count==0)? true : false;
    }

    //функция проверки на выигрыш/проигрыш
    static boolean checkWhoWin(String[][] board) {
         int countHumanChecker = 0;
         int countComputerChecker = 0;
         for(int i=0;i<board.length;i++) {
             for(int j=0;j<board.length;j++) {
                if(board[i][j] == computer) {
                    ++countComputerChecker;
                }
                if(board[i][j] == human){
                    ++countHumanChecker;
                }
             }
         }
         if(countComputerChecker == board.length*board.length) {
             System.out.println("Выиграл бот!");
             return false;
         }else if(countHumanChecker == board.length*board.length) {
             System.out.println("Выиграл человек!");
             return false;
         }
         System.out.println("Ничья!");
         return true;
    }

    //функция проверки на ничью
    static boolean checkFieldFullness(String[][] field) {
         int counter = 0;//счетчик для заполненных ячеек
         for(int i=0;i<field.length;i++) {
             for(int j=0;j<field.length;j++) {
                 //если ячейка заполнена
                 if(field[i][j] != "0") {
                     counter++;
                 }
             }
         }
         //если количество заполненых ячеек равно размеру поля
         if(counter == field.length*field.length){
             //возвращаем значение true
             return true;
         }
        //возвращаем значение false
         return false;
    }

     //функция удаления, если оппонент с 3-х сторон
     static void removePlayers(String currentPlayer, String opponent, String[][] playerBoard) {
        for(int i=0;i<playerBoard.length;i++) {
            for(int j=0;j<playerBoard.length;j++) {
                //если в ячейке оппонент и вокруг него текущий игрок
                if(playerBoard[i][j] == opponent && checkPosition(currentPlayer,playerBoard,i,j) == 3) {
                    //зануляем ячейку
                    playerBoard[i][j] = "0";
                }
                //если в ячейке текущий игрок и вокруг него оппонент
                if(playerBoard[i][j] == currentPlayer && checkPosition(opponent,playerBoard,i,j) == 3){
                    //зануляем ячейку
                    playerBoard[i][j] = "0";
                }
            }
        }
    }

    //проверка если по текущим индексам с 3-х сторон оппонент
    static int checkPosition(String opponent, String[][] playerBoard, int i, int j) {
         //слева справа сверху
        if ((j - 1 >= 0 && playerBoard[i][j - 1] == opponent) && (j + 1 < playerBoard.length && playerBoard[i][j + 1] == opponent) && (i - 1 >= 0 && playerBoard[i - 1][j] == opponent)) {
            return 3;
        }
        //слева справа снизу
        else if ((j - 1 >= 0 && playerBoard[i][j - 1] == opponent) && (j + 1 < playerBoard.length && playerBoard[i][j + 1] == opponent) && (i + 1 < playerBoard.length && playerBoard[i + 1][j] == opponent)) {
            return 3;
        }
        //слева сверху снизу
        else if ((j - 1 >= 0 && playerBoard[i][j - 1] == opponent) && (i - 1 >= 0 && playerBoard[i - 1][j] == opponent) && (i + 1 < playerBoard.length && playerBoard[i + 1][j] == opponent)) {
            return 3;
        }
        //справа сверху снизу
        else if ((j + 1 < playerBoard.length && playerBoard[i][j + 1] == opponent) && (i - 1 >= 0 && playerBoard[i - 1][j] == opponent) && (i + 1 < playerBoard.length && playerBoard[i + 1][j] == opponent)) {
            return 3;
        }
        //слева сверху, сверху слева
        else if((j-1 >= 0 && playerBoard[i][j - 1] == opponent)&&(i-1 >= 0 && playerBoard[i - 1][j] == opponent) && (i-1 >= 0 && j-1>=0 && playerBoard[i-1][j-1] == opponent)) {
            return 3;
        }
        //справа сверху, справа сверху
        else if((j+1 < playerBoard.length && playerBoard[i][j+1] == opponent) && (j+1 <playerBoard.length && i-1>=0  && playerBoard[i-1][j+1] == opponent) && (i-1>=0 && playerBoard[i-1][j]==opponent)) {
            return 3;
        }
        //справа снизу, справа снизу
        else if((i+1 <playerBoard.length && playerBoard[i+1][j] ==opponent) && (j+1<playerBoard.length && playerBoard[i][j+1] == opponent) && (i+1<playerBoard.length && j+1 < playerBoard.length && playerBoard[i+1][j+1] == opponent)) {
            return 3;
        }
        //слева снизу, слева снизу
        else if((i+1 <playerBoard.length && playerBoard[i+1][j] == opponent) && (j-1>=0 && playerBoard[i][j-1] == opponent) && (i+1<playerBoard.length && j-1>=0 && playerBoard[i+1][j-1] == opponent)) {
            return 3;
        }
        return 0;
    }

    //установка фигуры игрока который с 3-х сторон вокруг пустой ячейки
    static void setFigureIfEmptyThree(String player,String[][] playerBoard) {
        for(int i=0;i<playerBoard.length;i++) {
            for(int j=0;j<playerBoard.length;j++) {
                int count = 0;
                if (playerBoard[i][j] == "0") { //если ячейка пуста
                    //слева справа сверху
                    if ((j - 1 >= 0 && playerBoard[i][j - 1] == player) && (j + 1 < playerBoard.length && playerBoard[i][j + 1] == player) && (i - 1 >= 0 && playerBoard[i - 1][j] == player)) {
                        playerBoard[i][j] = player;  //записываем фигуру игрока
                    }
                    //слева справа снизу
                    else if ((j - 1 >= 0 && playerBoard[i][j - 1] == player) && (j + 1 < playerBoard.length && playerBoard[i][j + 1] == player) && (i + 1 < playerBoard.length && playerBoard[i + 1][j] == player)) {
                        playerBoard[i][j] = player; //записываем фигуру игрока
                    }
                    //слева сверху снизу
                    else if ((j - 1 >= 0 && playerBoard[i][j - 1] == player) && (i - 1 >= 0 && playerBoard[i - 1][j] == player) && (i + 1 < playerBoard.length && playerBoard[i + 1][j] == player)) {
                        playerBoard[i][j] = player; //записываем фигуру игрока
                    }
                    //справа сверху снизу
                    else if((j + 1 < playerBoard.length && playerBoard[i][j + 1] == player) && (i - 1 >= 0 && playerBoard[i - 1][j] == player) && (i + 1 < playerBoard.length && playerBoard[i + 1][j] == player)){
                        playerBoard[i][j] = player; //записываем фигуру игрока
                    }
                    //слева сверху, сверху слева
                    else if((j-1 >= 0 && playerBoard[i][j - 1] == player)&&(i-1 >= 0 && playerBoard[i - 1][j] == player) && (i-1 >= 0 && j-1>=0 && playerBoard[i-1][j-1] == player)) {
                        playerBoard[i][j] = player;
                    }
                    //справа сверху, справа сверху
                    else if((j+1 < playerBoard.length && playerBoard[i][j+1] == player) && (j+1 <playerBoard.length && i-1>=0  && playerBoard[i-1][j+1] == player) && (i-1>=0 && playerBoard[i-1][j]==player)) {
                        playerBoard[i][j] = player;
                    }
                    //справа снизу, справа снизу
                    else if((i+1 <playerBoard.length && playerBoard[i+1][j] ==player) && (j+1<playerBoard.length && playerBoard[i][j+1] == player) && (i+1<playerBoard.length && j+1 < playerBoard.length && playerBoard[i+1][j+1] == player)) {
                        playerBoard[i][j] = player;
                    }
                    //слева снизу, слева снизу
                    else if((i+1 <playerBoard.length && playerBoard[i+1][j] == player) && (j-1>=0 && playerBoard[i][j-1] == player) && (i+1<playerBoard.length && j-1>=0 && playerBoard[i+1][j-1] == player)) {
                        playerBoard[i][j] = player;
                    }
                }
            }
        }
    }
}
