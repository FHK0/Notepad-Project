
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Utilities;
import java.util.Scanner;
import java.util.StringTokenizer;


public class FileOperations {
    
    GUI gui;
    String fileName;
    String fileAddress;
    ArrayList<String> wordsArrayList = new ArrayList<>();
    Random random = new Random();
    

    public FileOperations(GUI gui) {
        this.gui = gui;
    }
    //Yeni Dosya Oluşturma
    public void newFile(){
        this.gui.txtArea.setText("");
        this.gui.jFrame.setTitle("Adsız");
        fileName = null;
        fileAddress = null;
    }
    //Dosya açma
    public void openFile(){
        FileDialog fd = new FileDialog(gui.jFrame, "Dosya Aç", FileDialog.LOAD);
        fd.setVisible(true);
        
        if(fd.getFile() != null){
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.jFrame.setTitle(fileName);
        }
        
        try {
            
            BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));
            gui.txtArea.setText(""); // Text sıfırlanır.
            String line = null;
            
            while((line = br.readLine()) != null){
                gui.txtArea.append(line + "\n");
            }
            br.close();
                                 
        } catch (Exception e) {           
        }
    }
    // Dosya kaydetme
    public void saveFile(){
        if(fileName == null)
            saveAsFile();
        else{
            try {
                FileWriter fw = new FileWriter(fileAddress + fileName);
                fw.write(gui.txtArea.getText());
                gui.jFrame.setTitle(fileName); 
                fw.close();
            } catch (Exception e) {
                System.out.println("Bir şeyler ters gitti!");
            }
        }
        
    }
    // Dosyayı farklı kaydetme
    public void saveAsFile(){
        FileDialog fd = new FileDialog(gui.jFrame,"Farklı Kaydet", FileDialog.SAVE);
        fd.setVisible(true);
        
        if(fd.getFile() != null){
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.jFrame.setTitle(fileName); 
        }
        
        try {
            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(gui.txtArea.getText());
            fw.close();
            
        } catch (Exception e) {
            System.out.println("Bir şeyler ters gitti!");
        }
    }
    //Çıkış
    public void exit(){
        System.exit(0);
    }
    //Bir harf geri alma işlemi
    public String formatUndo(String str){
        
        if (str != null && str.length() > 0) 
            str = str.substring(0, str.length() - 1);
        return str;
    }
    //Text'deki Son kelimeyi döndürme metodu
    public void getLastWord(){
           
        try {
                gui.txtArea.getText().trim();
                int start = Utilities.getWordStart(gui.txtArea, gui.txtArea.getCaretPosition());
                int end = Utilities.getWordEnd(gui.txtArea, gui.txtArea.getCaretPosition());
                String text = gui.txtArea.getDocument().getText(start, end - start);
                gui.lastWord = text;               
                } catch (Exception e) {
                }               
    }
    // Yeni kelimeyi insert etme metodu
    public void insertNewWord(){
        String trimmedText = gui.txtArea.getText().trim();
        gui.txtArea.setText(trimmedText);
        getLastWord();     
        String str = formatUndo(gui.lastWord);
        //System.out.println("SON KELİME:" + str);
        String allText = gui.txtArea.getText();
        String[] temp = allText.split("[|!|\\?|.|\\s|\\n]");

        for(String eleman : temp)
            System.out.println(eleman);
        String word = temp[temp.length-1].trim();
        int index = allText.lastIndexOf(word) ;

        String newTextOut = allText.substring(0,index) + str;
        //System.out.println(newTextOut);
        gui.txtArea.setText(newTextOut);
    }
    // Yazım yanlışı olan kelimeden yeni kelime türetme metodu
    public String newWordCreator(String oldWord){
        
        char letters[] = oldWord.toCharArray();
        ArrayList<Integer> harfIndexleri = new ArrayList<>();       
        for (int i = 0; i < letters.length; i++) {
            harfIndexleri.add(i);
        }       
        Collections.shuffle(harfIndexleri); // Hatfler karıştırılır
        char newLetters[] = new char[letters.length];
        for (int i = 0; i < letters.length; i++) {
            newLetters[i] = letters[harfIndexleri.get(i)];
        }
        String newWord = String.valueOf(newLetters);
        
        
        
        return newWord;
    }
   // Yazım hatası kontrolü          
   public void missplControl(){
       
        try {
            BufferedReader br = new BufferedReader(new FileReader("words.txt"));
            String line = null;
            
            while((line = br.readLine()) != null){
                wordsArrayList.add(line);              
            }          
            br.close();
       } catch (Exception e) {
       }
       
       boolean isSame = false; // Kelimeler aynı mı? 
        
       String allText = gui.txtArea.getText();
       String[] result = allText.split("[.,!?:;' ']+\\s*");
       //System.out.println(Arrays.toString(result));
       for (int i = 0; i < result.length; i++) {
           isSame = false;
           for (int j = 0; j < wordsArrayList.size(); j++) {
               if(result[i].equals(wordsArrayList.get(j))){
                   isSame = true;
                   //System.out.println("Bu Kelime Sözlükte Var : " + result[i] );
                   break;
               }
               else{                  
                   String str = null;
                   
                   while (isSame == false) {
                       str = newWordCreator(result[i]);
                       //System.out.println(str);
                       for (int k = 0; k < wordsArrayList.size(); k++) {
                           if(str.equals(wordsArrayList.get(k))){
                               //System.out.println("KELİME BULUNDU");
                               String newText = gui.txtArea.getText().replaceAll(result[i], str);
                               gui.txtArea.setText(newText);
                               isSame = true;                              
                           }                       
                           
                       }
                                                                    
                   }
                                                                       
                   }
                  
                   
               }
           
           }
           
       }       
     
}

