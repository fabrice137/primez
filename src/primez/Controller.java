package primez;

import fabFileLib.fabIO;
import hagumafab.PrimeNumber;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


public class Controller implements Initializable {

    @FXML private TextField inputField;
    @FXML private ListView<Long> listView;
    @FXML private RadioButton radioButton;
    @FXML private Label label;
    
    fabIO myIO = new fabIO();
    PrimeNumber prime = new PrimeNumber();
    ObservableList<Long> observableList = FXCollections.observableArrayList();
    
    long onDisplay = 1000;
    String initialFolder;
    
    @FXML
    public void checkPrimity(Event event){
        String txt = inputField.getText();
        if (!radioButton.isSelected()) {
            
            if(!isLong(txt)){
                label.setText("Invalid Number Error");
                return;
            }
            long numLong = Long.valueOf(txt);
            onDisplay = numLong;
            if (prime.isNumberPrime(numLong)) {
                label.setText(String.valueOf(numLong) + " is Prime");
            } else label.setText(String.valueOf(numLong) + " is NOT Prime");
            
        }
        else{
            long[] boundary = parseField(txt);
            onDisplay = boundary[0];
            observableList.clear();
            observableList.addAll(prime.listOfPrimes(boundary[0], boundary[1]));
            label.setText("size: " + observableList.size()); 
        }
    }
    
    @FXML
    public void makeTXT(ActionEvent event) throws IOException{
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("E_yyyy-MM-dd_hh-mm-ss");
        String nameOfFile = dateFormat.format(new java.util.Date());
        String onDesktop = initialFolder + "\\Desktop\\";
        myIO.writeToTxt(arrStringFromObserv(observableList), onDesktop, nameOfFile);
        
        Runtime.getRuntime().exec("cmd /c start " + onDesktop + nameOfFile + ".txt");
        
    }
    
    @FXML
    public void nextPrime(ActionEvent event){
        long next = onDisplay + 1;
        if (next >= Long.MAX_VALUE) {
            label.setText("Can't go up !!");
            return;
        }
        while (!prime.isNumberPrime(next)) next++;
        label.setText("next: " + String.valueOf(next));
        onDisplay = next;
    }
    
    @FXML
    public void previousPrime(ActionEvent event){
        long prev = onDisplay - 1;
        if (prev < 2) {
                label.setText("Can't go down !!");
                return;
            }
        while (!prime.isNumberPrime(prev)){
            prev--;
            if (prev < 2) {
                label.setText("Can't go down !!");
                break;
            }
        }
        label.setText("prev: " + String.valueOf(prev));
        onDisplay = prev;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setItems(observableList);
        initialFolder = defaultDirectory();
        inputField.setOnKeyPressed(event ->{
            if (event.getCode() == KeyCode.ENTER) checkPrimity(event);
        });
    }    
    
    private static boolean isLong(String x){
        try{Long.parseLong(x); return true; }
        catch(java.lang.NumberFormatException ex){ return false; }
    }
    
    private static long[] parseField(String txt){
        long[] deux = {0, 0};
        int at = 0;
        String oktxt = avoidMistake(txt);
        String[] parsed = line2Arr(oktxt);
        for (String parsed1 : parsed) {
            if (isLong(parsed1)) {
                deux[at] = Long.parseLong(parsed1);
                at++;
            }
            if (at == 2) break;
        }
        if (deux[0] > deux[1]) {
            long temp = deux[0];
            deux[0] = deux[1];
            deux[1] = temp;
        }
        return deux;
    }
    private static String defaultDirectory(){
        return javax.swing.filechooser.FileSystemView.
                getFileSystemView().getDefaultDirectory().getParent();
    }
    
    public static String avoidMistake(String line){
        StringBuilder sb = new StringBuilder(line);
        for (int i=0 ; i<line.length() ; i++){
            if (!Character.isDigit(sb.charAt(i)))
                sb.replace(i, i+1, " ");
        } return sb.toString();
    }
    public static String[] line2Arr(String phrase){
        // you enter a phrase and it turns it into an arrayList of individual words.
        phrase += " "; 
        int szP = phrase.length();   // szP = size of the phrase
        
        java.util.ArrayList<String> list = new java.util.ArrayList<>(); 
        StringBuilder builder = new StringBuilder();

        for(int i=0 ; i<szP ; i++) {
            int intKar = (int)phrase.charAt(i);
            if (intKar > 32) builder.append(phrase.charAt(i));
            else if (intKar == 32 && !"".equals(builder.toString())){
                list.add(builder.toString());
                builder.delete(0, builder.length());
            }
            else builder.delete(0, builder.length());
        }
        int newsz = list.size();
        String[] arr = new String[newsz];
        for (int i=0; i<newsz; i++) arr[i] = list.get(i);
        
        return arr;
    }
    
    public static String[] arrStringFromObserv(ObservableList<Long> observ){
        String[] outArr = new String[observ.size()];
        int i = 0;
        java.util.Iterator<Long> iterator = observ.iterator();
        while (iterator.hasNext()) {
            outArr[i] = String.valueOf(iterator.next()); 
            i++;
        }
        return outArr;
    }
    
}
