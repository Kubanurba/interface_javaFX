package sample;

import java.util.ArrayList;

/**
 * класс ID номеров объектов класса BusDrivers
 * @author nastyabaturkina
 *
 */
public class BusDriversID extends BusDrivers
{
    public ArrayList <Integer> IDs = new ArrayList<>();
    private static int ID = 0;
    private int id = 0;

    /**
     * конструктор с параметрами
     * @param line вторая строка из файла для чтения с данными о первом водителе
     * @param attr массив атрибутов из первой строки файла для чтения
     * @throws MyException если вторая строка файла пустая или содержит не всю необходимую информацию, то выбрасывается собственное исключение MyException
     */
    public BusDriversID(String line, String[] attr) throws MyException
    {
        super(line, attr);
        id = ++ID;
        IDs.add(id);
    }

    public int get_ID() { return id;}

    @Override
    public String toString(){
        return String.valueOf(this.get_ID()) + ":   " + super.toString();
    }
    /**
     * функция добавления ID к строке с данными об объекте в json формате
     */
    public String toJson()
    {
        return "  \"ID\": " + id + "," + super.toJson();
    }
}
