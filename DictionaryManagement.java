package sample;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sun.rmi.transport.Connection;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

public class DictionaryManagement implements Initializable {
    private Dictionary Dict;

    @FXML
    private ListView<String> listView;
    @FXML
    private TextField searchInput;
    @FXML
    private TextArea wordDetail;
    @FXML
    private TextField Word;
    @FXML
    private TextField Translate;
    @FXML
    private  TextField Delete;
    @FXML
    private TextField Trans;
    @FXML
    private  TextField class1;
    @FXML
    private  TextField class2;
    @FXML
    Button btnSpeaker;
    @FXML
    Button btnBack;

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ObservableList<String> _word = FXCollections.observableArrayList();
    private ObservableList<String> _class = FXCollections.observableArrayList();
    private ObservableList<String> _def = FXCollections.observableArrayList();

/*
 --------------------------------------------------------------------------------------------------------------
 */

    public DictionaryManagement() {
        Dict = new Dictionary();
        listView = new ListView<String>();
    }

    public Dictionary getDict() {
        return Dict;
    }

    private void getWordList() {
        for (int i = 0; i < Dict.getLength(); i++) {
            String word = Dict.getWord(i).getWord_target();
            _word.add(word);
            _class.add(Dict.getWord(i).getWord_class());
            _def.add(Dict.getWord(i).getWord_explain());
        }
    }

    public void insertFromFile(String path) throws FileNotFoundException {
        File text = new File(path);
        Scanner sc = new Scanner(text);

        try {
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] parts = line.split("\t",3);
                String word = parts[0];
                String classes = parts[1];
                String def  = parts[2];
                Dict.addWord(new Word(word, classes, def));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
    }

    public void dictionaryLookup(String word) {
        int index = _word.indexOf(word);
        String details = _class.get(index) + "\n" + _def.get(index);
        wordDetail.setText(details);
    }

    public void addNewWord(ActionEvent event ) {
        String word=Word.getText();
        String translate=Translate.getText();
        String cl ="("+class1.getText()+")";
        Word newWord=new Word(word,cl,translate);
        Dict.addWord(newWord);
        try{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("ADD SUCCESS");
            alert.show();
            File file=new File("dictionary.txt");
            FileWriter fw= new FileWriter(file);
            for(int i=0;i<Dict.getLength();i++){
                fw.write(Dict.getWord(i).getWord_target()+ "\t"
                        +Dict.getWord(i).getWord_class()+ "\t"
                        + Dict.getWord(i).getWord_explain()+ "\n");
            }
            fw.close();
        }
        catch(Exception e){
            System.out.println("Error: " +e);
        }
    }
//    @FXML
//    public void delete(ActionEvent event) {
//        String wordCheck = TextRCW.getText();
//        boolean isFound = false;
//        int index = 0;
//        for (int i = 0; i < Dict.size(); i++) {
//            if (wordV.get(i).getWord().contentEquals(wordCheck)) {
//                isFound = true;
//                index = i;
//                break;
//            }
//        }
//        if(!isFound) {
//            showAlertNull();
//            return;
//        }
//        else {
//            wordV.remove(index);
//            keywordV.remove(wordCheck);
//            text = new autoSuggest(TextV);
//            text.keyW().addAll(getKeywordV());
//            showAlertRemove();
//        }
//    }
    public void deleteWord(ActionEvent event) {
        String word=Delete.getText();
        String translate=Trans.getText();
        String cl="("+class2.getText()+")";
        Word x=new Word(word,cl,translate);
        Dict.removeWord(x);
        try{
            File file = new File("dictionary.txt");
            FileWriter fw= new FileWriter(file);
            for(int i=0;i<Dict.getLength()-1;i++){
                if(Dict.getWord(i).getWord_target()==word){
                    Dict.getWord(i).setWord_target("");
                    Dict.getWord(i).setWord_class("");
                    Dict.getWord(i).setWord_explain("");
                }
                else
                    fw.write(Dict.getWord(i).getWord_target()+ "\t" +Dict.getWord(i).getWord_class()+ "\t" + Dict.getWord(i).getWord_explain()+ "\n");
            }
            fw.close();
        }
        catch(Exception e){
            System.out.println("Error: " +e);
        }

    }

    public int dictionarySearcher(String x) {
        for (int i = 0; i < _word.size(); i++) {
            String word = _word.get(i);
            if (word.length() < x.length()) continue;
            String subWord = word.substring(0, x.length());
            if (x.equals(subWord)) {
                listView.getSelectionModel().select(i);
                listView.scrollTo(i);
                return i;
            }
        }
        return -1;
    }
    public void speak(ActionEvent event) {
        String text = searchInput.getText().trim();
        Speaker speaker = new Speaker();
        speaker.say(text);
    }
    public void initButton() {
        Image imgSpeaker = new Image("speak.png");
        ImageView ivSpeaker = new ImageView(imgSpeaker);

        ivSpeaker.setPreserveRatio(true);

        btnSpeaker.setGraphic(ivSpeaker);

        btnBack.setDisable(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            insertFromFile("dictionary.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listView.setItems(_word);
        getWordList();

        /**
         * Wath for input from SearchBar and scroll to the searched word.
         */

        searchInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                int tmp = dictionarySearcher(t1);
            }
        });

        /**
         * If any word is selected, show details.
         */
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                dictionaryLookup(t1);
            }
        });
    }
}