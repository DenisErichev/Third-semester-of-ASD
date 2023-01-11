package Laba6;

import java.util.Scanner;

public class Lab6 {
    static int firstArraySize = 0;
    static int secondArraySize = 0;
    static int[] arr1;
    static int[] arr2;
    static int[] resultArray;
    public static void main(String[] args) {
        System.out.print("Введите размер 1-го массива: ");
        firstArraySize = checkValue(firstArraySize);
        System.out.print("Введите размер 2-го массива: ");
        secondArraySize = checkValue(secondArraySize);
        arr1 = new int[firstArraySize];
        arr2 = new int[secondArraySize];
        inputValue(arr1);
        inputValue(arr2);
        System.out.println("1-й отсортированный массив:");
        sort(arr1);
        printArray(arr1);
        System.out.println("2-й отсортированный массив:");
        sort(arr2);
        printArray(arr2);
        System.out.println("Результирующий массив: ");
        resultArray = bindMethod(arr1, arr2);
        printArray(resultArray);
    }
    public static void printArray(int[] arr) {
        for(int i=0;i<arr.length;i++) {
            System.out.print(arr[i] +" ");
        }
        System.out.println();
    }
    public static int[] bindMethod(int[] firstArray, int[] secondArray) {
        int currArraySize = firstArray.length+secondArray.length;
        int[] newArray = new int[currArraySize];
        for(int i=0;i<firstArray.length;i++) {
            newArray[i] = firstArray[i];
            if(i == firstArray.length-1) {
                int index = firstArray.length;
                for(int j = 0;j<secondArray.length;j++) {
                    newArray[index] = secondArray[j];
                    index++;
                }
            }
        }
        sort(newArray);
        return newArray;
    }
    public static void sort(int[] arr) {
        for(int i=0;i<arr.length;i++) {
            for(int j=i+1;j<arr.length;j++) {
                if(arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
    public static int checkValue(int value) {
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextInt()) {
            value = scanner.nextInt();
            return value;
        }else{
            System.out.print("Нужно ввести число: ");
            return checkValue(value);
        }
    }
    public static void inputValue(int[] array) {
        System.out.println("Вводите элементы массива: ");
        for(int i=0;i<array.length;i++) {
            array[i] = checkValue(array[i]);
        }
    }
}
