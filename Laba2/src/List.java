package Laba2.src;

import java.util.Iterator;

public class List<T> implements Iterable<T>{
    private Node<T> head;    //Создаем первый узел(первый элемент списка)
    private int length;     //Длина списка
    List(){                 //Конструктор класса List
        length = 0;
        head = null;
    }
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            Node<T> currentNode = head;
            @Override
            public boolean hasNext() {
                return currentNode!=null;
            }

            @Override
            public T next() {
                T data = currentNode.data;
                currentNode = currentNode.nextNode;
                return data;
            }
        };
    }
    private class Node<T> {  //Класс отдельного элемента списка
        Node(){};            //Конструктор без параметров
        T data;              // Переменная хранит информацию элемента
        Node<T> nextNode;    //Создаем новый элемент, чтобы изпользовать его как ссылку на следующий

        Node(T data, Node<T> nextNode) {  //Конструктор с параметрами, принимающий информацию и следующий элемент

            this.data = data;
            this.nextNode = nextNode;
        }
    }
    public void addFront(T data) {      //Метод добавления элемента в начало списка(первым элементом)

        head = new Node<T>(data,head);  //В переменную первого элемента, создаем новый элемент, передаем data,
                                        //и в качестве ссылки на следующий элемент передаем еще не измененный
                                        //узел head

        length++;                       //изменяем длину списка
    }
    public void set(int index, T data) {
        if(index>length || index<=0) {
            System.out.println("Элемент не добавлен на место номер: " + index);
        }else {
            Node<T> currentElement = head;
            int countIndex = 0;
            while(countIndex+1 != index) {               //делаем +1 чтобы вставить элемент на index+1 место
                currentElement = currentElement.nextNode;      //передвигаем указатель
                countIndex++;                            //увеличиваем счетчик индексов
            }
            Node<T> newNode = new Node<>();             //создаем новый узел
            newNode.data = data;
            newNode.nextNode = currentElement.nextNode;    //в новый узел передаем ссылку на след. элемент после
                                                        //элемента который получили ранее
            currentElement.nextNode = newNode;             //в след. элемент после полученного записываем новый узел
        }
    }
    public T getDataIsElem(int index) {   //метод возвращающий элемент по указанному индексу
        Node<T> currentElement = head; //создаем узел указывающий на первый эолемент списка
        T data = null;                  //переменная для запоминания информаации из списка
        if(index > length || index < 0) { //если ввели неверный индекс
            System.out.println("Вы ввели неверный индекс.");
        }else {
            int countIndex = 0;     //переменная для контроля текущего индекса
            while(countIndex != index) {    //пока не указанный индекс
                currentElement = currentElement.nextNode;  //берем следующий элемент списка
                countIndex++;                              //увеличиваем текущий индекс
            }
            data = currentElement.data;  //запоминаем информацию из элемента
        }
        return data;            // возвращаем информацию из элемента
    }
    public void replaceFrontAndBack() {   //Метод который меняет крайние элементы местами
        if(head == null || head.nextNode == null) {       //Если следующий элемент после 1-го пустой
            return;                       //ничего не делаем
        }
        Node<T> currentElement = head;       //Создаем узел для прохода по списку с его начала
        T currentData = null;             //Переменная для запоминания информации элемента
        while(currentElement.nextNode != null) {    //Пока не получили пустой элемент списка
            currentElement = currentElement.nextNode;  //В узел для прохода записываем ссылку на следующий элемент
                                                 //для перемещения дальше

            if(currentElement.nextNode == null){    //Если следующий элемент пустой, значит мы получили последний элемент списка
                currentData = currentElement.data;  //Запоминаем информацию из последнего элемента
            }
        }
        currentElement.data = head.data;  //Записываем информацию в последний элемент из первого
        head.data = currentData;       //Записываем информацию в первый элемент из полученного последнего элемента
    }
    public void addBack(T data) {      //Метод добавления элемента в конец списка
        if(head == null){              //Если 1-й элемент пустой
            head = new Node<T>(data,null);  //Создаем в нем новый узел с информацией
        }
        else{
            Node<T> newNode = head;                 //Создаем узел для прохода по списку
            while(newNode.nextNode != null) {       //Пока не получили последний элемент, после которого пусто
                newNode = newNode.nextNode;         //Меняем элемент для прохода на след. элемент
            }
            newNode.nextNode = new Node<T>(data,null);  //Т.к. получили последний элемент, создаем в нем новый узел
        }
        length++;               //Увеличиваем длину списка
    }
    public int getLength() {    //Метод возвращающий длину списка
        return length;
    }
    public void print() {       //Метод который выводит информацию из элементов списка
        Node<T> currentElement = head;     //Создаем узел для прохода по списку
        System.out.println("Элементы списка: ");
        if(head == null){   //Если 1-й элемент пустой
            System.out.println("Список пуст!");
            return;
        }else {
            System.out.println(head.data + " ");  //Выводим информацию из него
        }
        while (currentElement.nextNode != null) {     //Пока мы не дошли до конца списка(т.е. до элемента после которого идет пустой элемент)
            currentElement = currentElement.nextNode;    //Меняем элемент для прохода на след. элемент
            System.out.println(currentElement.data + " ");   //Выводим информацию из текущего элементе
        }
    }
    public void deleteFront() {     //Метод удаления первого элемента
        head = head.nextNode;       //Первый элемент ссылается на следующий
        length--;                   //Меняем длину списка
    }
    public void deleteNodes() {     //Метод удаления всех элементов списка
        while(length > 0) {         //Пока список не пуст
            this.deleteFront();          //Вызываем метод удаления первго элемента списка
        }
    }
}
