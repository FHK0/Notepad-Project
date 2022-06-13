import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.*;

public class GUI implements ActionListener{
    
    public JFrame jFrame;
    public static JTextArea txtArea;
    public JMenuBar menuBar;
    public JMenu File, Format, Find, About;
    public JMenuItem fNew, fOpen, fSave, fSaveAs, fExit, fUndo, fFind, fFindAndChange, fMispellingControl, fAbout; 
    public String lastWord;
    
    FileOperations fileOperations = new FileOperations(this);
    
    
    findGUI fGUI = new findGUI();
    FindAndChangeClass fac = new FindAndChangeClass();
    
    public GUI(){
        
        jFrame = new JFrame();
        jFrame.setSize(800, 800);
        txtArea = new JTextArea();
        jFrame.add(txtArea);
        JScrollPane scrollPane = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jFrame.add(scrollPane);
        createMenuBarAndMenuItems();
        jFrame.setVisible(true);
        
        
        //createMenuBarAndMenuItems();
        
    }
    
    public void createMenuBarAndMenuItems(){
        
        // Menü Oluşturma & Menü Başlıkları Oluşturma
        
        menuBar = new JMenuBar();
        jFrame.setJMenuBar(menuBar);
        
        File = new JMenu("Dosya");
        menuBar.add(File);
        
        Format = new JMenu("Düzen");
        menuBar.add(Format);
        
        Find = new JMenu("Ara");
        menuBar.add(Find);
        
        About = new JMenu("Hakkında");
        menuBar.add(About);
        
        // Menü Item Oluşturma
        
        fNew = new JMenuItem("Yeni Dosya Oluştur");
        fNew.addActionListener(this);
        fNew.setActionCommand("Yeni Dosya Oluştur");
        File.add(fNew);
        /////////////////////////////////////////////
        fOpen = new JMenuItem("Dosya Aç");
        fOpen.addActionListener(this);
        fOpen.setActionCommand("Dosya Aç");
        File.add(fOpen);
        /////////////////////////////////////////////
        fSave = new JMenuItem("Kaydet");
        fSave.addActionListener(this);
        fSave.setActionCommand("Kaydet");
        File.add(fSave);
        /////////////////////////////////////////////
        fSaveAs = new JMenuItem("Farklı Kaydet");
        fSaveAs.addActionListener(this);
        fSaveAs.setActionCommand("Farklı Kaydet");
        File.add(fSaveAs);
        /////////////////////////////////////////////
        fExit = new JMenuItem("Çıkış");
        fExit.addActionListener(this);
        fExit.setActionCommand("Çıkış");
        File.add(fExit);
        /////////////////////////////////////////////
        fUndo = new JMenuItem("Geri Al");
        fUndo.addActionListener(this);
        fUndo.setActionCommand("Geri Al");
        Format.add(fUndo);
        /////////////////////////////////////////////
        fFind = new JMenuItem("Kelime Ara");
        fFind.addActionListener(this);
        fFind.setActionCommand("Kelime Ara");
        Find.add(fFind);
        /////////////////////////////////////////////
        fFindAndChange = new JMenuItem("Kelimeyi Değiştir");
        fFindAndChange.addActionListener(this);
        fFindAndChange.setActionCommand("Kelimeyi Değiştir");
        Find.add(fFindAndChange);
        /////////////////////////////////////////////
        fMispellingControl = new JMenuItem("Yazım Hatası Kontrolü");
        fMispellingControl.addActionListener(this);
        fMispellingControl.setActionCommand("Yazım Hatası Kontrolü");
        Format.add(fMispellingControl);
        /////////////////////////////////////////////
        fAbout = new JMenuItem("Bilgi");
        fAbout.addActionListener(this);
        fAbout.setActionCommand("Bilgi");
        About.add(fAbout);
        
    }
    
    public static void main(String[] args) {
        
        new GUI();
        
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String cmd = ae.getActionCommand();
        
        switch(cmd){
            case ("Yeni Dosya Oluştur"): fileOperations.newFile();
            break;
            case ("Dosya Aç"): fileOperations.openFile();
            break;
            case("Kaydet"): fileOperations.saveFile();
            break;
            case("Farklı Kaydet"): fileOperations.saveAsFile();
            break;
            case("Çıkış"): fileOperations.exit();
            break;
            case("Geri Al"): fileOperations.insertNewWord();
            break;
            case("Kelime Ara"): fGUI.setVisible(true);
            fGUI.setDefaultCloseOperation(fGUI.DISPOSE_ON_CLOSE);
            break;
            case("Kelimeyi Değiştir"): fac.setVisible(true);
            fac.setDefaultCloseOperation(fGUI.DISPOSE_ON_CLOSE);
            break;
            case("Yazım Hatası Kontrolü"): fileOperations.missplControl();               
            break;
            case("Bilgi"): JOptionPane.showMessageDialog(jFrame, "Fırat Hilmi KAPLAN - 05170000082");
            break;
        }
    }
    
    
  
    
}
