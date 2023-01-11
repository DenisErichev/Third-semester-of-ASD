package Laba3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        BST<Integer> tree = new BST<>();
        readElementsInFile(tree, "/home/denis/JavaPrograms/src/Laba3/file.txt");
        System.out.println("Бинарное дерево:");
        tree.print();
        System.out.println();
        tree.find();
    }

    static void readElementsInFile(BST<Integer> tree, String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        while(scanner.hasNextInt()) {
            tree.add(scanner.nextInt());
        }
    }
}