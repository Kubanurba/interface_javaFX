package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller{
    private int index;
    public ListView<BusDriversID> listV;
    private FileCh filesCh;
    private BusPark Arr;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /*-----------------------------------------------------Buttons------------------------------------------------------*/
    @FXML
    private Button readButt;

    @FXML
    private Button logButt;

    @FXML
    private Button firstButt;

    @FXML
    private Button lastButt;

    @FXML
    private Button nextButt;

    @FXML
    private Button prevButt;

    @FXML
    private Button saveJsonButt;

    @FXML
    private Button changeButt;

    @FXML
    private Button saveActionButt;

    /*--------------------------------------------Text Areas and Fields-------------------------------------------------*/
    ///!!!!!
    @FXML
    private TextArea forAction;

    @FXML
    private TextField pathField;

    @FXML
    private TextField forID;

    @FXML
    private TextField forName;

    @FXML
    private TextField forBusNumber;

    @FXML
    private TextField forRoute;

    @FXML
    private TextField forMark;

    @FXML
    private TextField forYear;

    @FXML
    private TextField forMile;



    public void inject(FileCh fileConfigurator, BusPark Arr) {
        this.filesCh = fileConfigurator;
        this.Arr = Arr;
    }

    /*-------------------------------------------Functions for buttons--------------------------------------------------*/
    @FXML
    private void readFile(ActionEvent event) throws MyException {
        try {
            MyException.forAction = forAction;
            filesCh.chooseFile();
            Arr.Reader(filesCh.getFilePath());
            pathField.setText(filesCh.getFilePath());
            ObservableList<BusDriversID> drivers = FXCollections.observableArrayList(Arr.list); //сделать полем для дальнейшего использования
            listV.setItems(drivers);
            textAreaActions("File:  " + filesCh.getFilePath() + "  is opened.\n----------------------------------------------------------------\n");
            saveJsonButt.setDisable(false);
            saveActionButt.setDisable(false);
            firstButt.setDisable(false);
            lastButt.setDisable(false);
            nextButt.setDisable(false);
            prevButt.setDisable(false);
            changeButt.setDisable(false);
            changeFields(0);
        } catch (MyException exc) {
            exc.Log("");
        }
    }
    @FXML
    public void logOpen(ActionEvent actionEvent) throws MyException {
        filesCh.chooseFile();
        MyException.LogFileName = filesCh.getFilePath();
        readButt.setDisable(false);
        textAreaActions(".log file \"" + filesCh.getFilePath() + "\"" + " has been successfully opened!\n");

    }

    @FXML
    void firstEl(ActionEvent event) {
        changeFields(0);
        textAreaActions("First element has been displayed.\n");
    }

    @FXML
    void lastEl(ActionEvent event) {
        changeFields(Arr.list.size()-1);
        textAreaActions("Last element has been displayed.\n");
    }

    @FXML
    void nextEl(ActionEvent event) {
        if(get_index() < Arr.list.size() - 1)
        {
            changeFields(get_index()+ 1);
            textAreaActions("Next element has been displayed.\n");
        }
    }

    @FXML
    void prevEl(ActionEvent event) {
        if(get_index() > 0)
        {
            changeFields(get_index() - 1);
            textAreaActions("Previous element has been displayed.\n");
        }
    }

    @FXML
    void saveJson(ActionEvent event) throws MyException {
        filesCh.chooseFile();
        Arr.JSONwriter(filesCh.getFilePath());
        textAreaActions("Information has been saved to \"" + filesCh.getFilePath() + "\" in JSON format.\n");
    }

    @FXML
    public void saveActionFile(ActionEvent actionEvent) throws MyException {
        filesCh.chooseFile();
        filesCh.ActionWriter(forAction.getText(), filesCh.getFilePath());
        textAreaActions("Actions information has been saved to \"" + filesCh.getFilePath() + "\".\n");
    }

    @FXML
    public void changeAttr(ActionEvent actionEvent) throws MyException {
        try {
            Arr.list.get(get_index()).set_name(forName.getText());
            Arr.list.get(get_index()).set_bus_number(forBusNumber.getText());
            Arr.list.get(get_index()).set_route_number(forRoute.getText());
            Arr.list.get(get_index()).set_mark(forMark.getText());
            Arr.list.get(get_index()).set_start_year(forYear.getText());
            Arr.list.get(get_index()).set_run(forMile.getText());
            textAreaActions("Item with ID " + Arr.list.get(get_index()).get_ID() + " has been changed!\n");
        } catch (MyException exc)
        {
            exc.Log("\nCant't save invalid data!");
        }
    }

    /*-----------------------------------------------List's function----------------------------------------------------*/
    public void changeFields(int index)
    {
        set_index(index);
        forName.setText(Arr.list.get(index).get_name());
        forBusNumber.setText(Arr.list.get(index).get_bus_number());
        forID.setText(String.valueOf(Arr.list.get(index).get_ID()));
        forMark.setText(Arr.list.get(index).get_mark());
        forRoute.setText(Arr.list.get(index).get_route_number());
        forYear.setText(String.valueOf(Arr.list.get(index).get_start_year()));
        forMile.setText(String.valueOf(Arr.list.get(index).get_run()));
    }

    /*---------------------------------------------------------Other--------------------------------------------------------*/
    public void set_index(int in) {
        index = in;
    }

    private int get_index() {
        return index;
    }

    public void textAreaActions(String str)
    {
        forAction.appendText(str);
    }

    public void changeID(MouseEvent mouseEvent) {
        MultipleSelectionModel<BusDriversID> listVSelectionModel = listV.getSelectionModel();
        index = listVSelectionModel.getSelectedIndex();
        changeFields(index);
    }

    @FXML
    void initialize() {
    }

}
