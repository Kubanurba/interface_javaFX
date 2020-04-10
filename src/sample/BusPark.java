package sample;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * класс контейнера для хранения и обработки информации о водителях автобусов
 * @author nastyabaturkina
 *
 */
public class BusPark
{
    public ArrayList<BusDriversID> list = new ArrayList<BusDriversID>();
    /**
     * функция для чтения данных из файла
     * @param fileName название файла для чтения
     * @throws MyException собственное исключение
     */
    public void Reader(String fileName) throws MyException
    {
        try
        {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();

            String[] attributs = line.split("\\t");


            for ( int rowNo = 1; (line = reader.readLine()) != null; rowNo++ )
            {
                try {

                    list.add(new BusDriversID(line, attributs));
                }
                catch ( MyException exc )
                {
                    exc.Log(", rowNo : " + rowNo);
                }
            }

            reader.close();

        }
        catch (FileNotFoundException exc)
        {
            exc.printStackTrace();
            throw new MyException(exc.getLocalizedMessage());
        }
        catch (IOException exc)
        {
            exc.printStackTrace();
            throw new MyException(exc.getLocalizedMessage());
        }

    }


    /**
     * функция для записи данных в json файл
     * @param fileName название файла для записи
     * @throws MyException собственное исключение
     */
    public void JSONwriter(String fileName) throws MyException
    {

        try(FileWriter writer = new FileWriter(fileName))
        {
            String text = "[\n";
            for(int i = 0; i < list.size(); i++)
            {
                text = text + " {\n" + (list.get(i).toJson()) + "\n }";
                if(i != (list.size() - 1) )
                    text = text + ",";
                text = text + "\n";
            }
            text += "]";

            writer.write(text);
            writer.flush();
        }
        catch(IOException exc)
        {
            throw new MyException(exc.getLocalizedMessage());
        }
    }
}