/*
 * July 25, 2017
 * Stephan Motha
 * Hangman Summative
 * make a GUI that allows a user to play a game of hangman. 
 * have requirements like arrays, sorting writing to a file etc
 */
package hangman.summative;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Stephan
 */
public class MainPage extends javax.swing.JFrame {
    double startTime;
    int choice; //specifies the choice of the themes
    //this function will take the constructor from the start page and set the current theme equal to that theme
    public MainPage(int n) {
        startTime = System.currentTimeMillis();
        this.choice = n;
        initComponents();
        //there are 6 themes in total. Depending on which button was pressed, that theme will beocme the current theme
        switch(n){
            case 1:
                currentTheme=foodsTheme;
                break;
            case 2:
                currentTheme=moviesTheme;
                break;
            case 3:
                currentTheme=scienceTheme;
                break;
            case 4:
                currentTheme=sportsTheme;
                break;
            case 5:
                currentTheme=tvShowsTheme;
                break;
            case 6:
                currentTheme=animalsTheme;
                break;         
            
        }
        generateWord();//calls the function to generate random word
        initializeHiddenWord();//creates the hidden word
    }
    int hangmanStrikes = 0;
    //this will initialize the array lists needed to hold the guessed letters and letters not int the word
    ArrayList<String> notInWord = new ArrayList<String>();
    ArrayList<String> guessedLetters = new ArrayList<String>();
    //creates the output strings to be displayed
    String outputGuessedLetters = "Guessed Letters"+"\n";
    String outputLettersNotInWord = "Letters Not In Word"+"\n";
    String hiddenWord = "";
    String word;
    //creates the arrays of size 10 that will hold all of the possible words for each theme
    String[] currentTheme;
    String[] sportsTheme = {"baseball", "basketball", "golf", "tennis", "volleyball", "hockey", "softball", "curling", "badminton", "soccer"};
    String[] moviesTheme = {"minions","cars", "superman", "frozen", "spiderman", "zootopia", "shrek", "batman", "aladdin", "ratatouille"};
    String[] tvShowsTheme = {"arthur", "barny", "cailou", "dora", "rugrats", "teletubbies", "spongebob", "pokemon", "flintstones", "zoboomafoo"};
    String[] animalsTheme = {"frog", "cheetah", "crocodile", "monkey", "bear", "spider", "dinosaur", "whale", "turtle", "scorpion"};
    String[] scienceTheme = {"biology", "matter", "gravity", "chemistry", "physics", "particles", "telescope", "fossil", "climate", "elements"};
    String[] foodsTheme = {"pizza", "pasta", "broccoli", "cereal", "peaches", "steak", "popsicle", "chocolate", "meatballs", "soup"};
    
    //this function will generate a random word, based on the theme from the splash page
    private void generateWord(){
        int randomWordNumber = (int)Math.floor(Math.random()*(10));//chooses a random index number to generate a word
        
                
        word = currentTheme[randomWordNumber];//chooses the random word from the theme
        
        
    }
    //this function will create a hidde word with dashes, the size of the actual word and it will be displayed
    private void initializeHiddenWord(){
        for(int i = 0;i<word.length();i++){//fills hidden word with dashes for the size of actual word
            hiddenWord+="-";
        }
        this.txtAreaWord.setText(hiddenWord);
    }
    
    //this function will check if the letter that the user entered has already been entered
    //it will also check for invalid inputs
    //if the user has never inputted this letter, it will add it to guessedLetters list and then check if it is correct or incorrect
    private boolean checkGuessed(String letter){
        //following makes sure the user enters a letter or the word (no symbols or spaces)
        if (!letter.equals(word) && !letter.equals("a") && !letter.equals("b") && !letter.equals("c") && !letter.equals("d") && !letter.equals("e") && !letter.equals("f") && !letter.equals("g") && !letter.equals("h") && !letter.equals("i") && !letter.equals("i") && !letter.equals("j") && !letter.equals("k") && !letter.equals("l") && !letter.equals("i") && !letter.equals("m") && !letter.equals("n") && !letter.equals("o") && !letter.equals("p") && !letter.equals("q") && !letter.equals("r") && !letter.equals("s") && !letter.equals("t") && !letter.equals("u") && !letter.equals("v") && !letter.equals("w") && !letter.equals("x") && !letter.equals("y") && !letter.equals("z")){
            this.lblFeedback.setText("This is an invalid letter");
            return true;
        }
        if(letter.length()>1){//cant enter more than one letter, unless it is the actual word
            this.lblFeedback.setText("This is an invalid letter");
            return true;
        }
        sortLetters(guessedLetters);//puts the leters in alphabetical order
        
        if(binarySearch1(guessedLetters,letter)){
                this.lblFeedback.setText("You have already guessed this letter");//if you find the letter they guess in the list, print this
                return true;

        }
        else{
            guessedLetters.add(letter);//if letter is not in the list, add it to list
            //if its a wrong letter
            if(!word.contains(letter)) {//if the letter is wrong, add strikes and add it to lettersNotInWord
                    notInWord.add(letter);
                    hangmanStrikes++;
                    updateHangman(hangmanStrikes);
                    if(hangmanStrikes==6){
                        this.dispose();
                        new LoseScreen().setVisible(true);
                    }
                }
            
            return false;
        }
        
        
    }
    
    //this function will print out all of the elements in the guessedLetters list
    public void printGuessed(){
        sortLetters(guessedLetters);//sorts letters alphabetically
        String outputGuessed="Guessed Letters"+"\n";
        
        for (int i =0; i<guessedLetters.size();i++){//iterates thorugh list
            outputGuessed += (guessedLetters.get(i)+", ");
            
        }
        this.txtAreaLettersGuessed.setText(outputGuessed);

            
        }
         
    //this function will perform binary search to find a letter in an array of letters (or words)
    public static boolean binarySearch1(ArrayList<String> students, String search) {
    int first = 0;
    int last = students.size() - 1;
    int mid;
    while (first <= last) {
        mid = ( first + last ) / 2;
        if (search .equals(students.get(mid))) {//if the letter equals mid
            return true;
        } else if (search.compareTo(students.get(mid)) > 0) {//if the letter is after (aphabetically) mid
            first = mid + 1;
        } else {//if the letter is before (aphabetically) mid
            last = mid - 1;
        }
    }

    return false;
}
    
    //this function will print out the element in the LetterNotInWord list
    public void printNotInWord(){
        sortLetters(notInWord);//sorts letters alphabetically
        outputLettersNotInWord+="Letters Not In Word"+"\n";
         for (int i =0; i<notInWord.size();i++){//iterates thorugh list
            outputLettersNotInWord += (notInWord.get(i)+", ");
            this.txtAreaLettersNotInWord.setText(outputLettersNotInWord);
         }
        
    }
    
    //this function will sort the letters alphabetically in a given list (guessedletters and lettersNotInWOrd list)
    //uses selection sort
    private void sortLetters(ArrayList<String> letters){
        //selection sort algorithm with array list
        for(int i =0;i<letters.size();i++){
            for(int j =i+1;j<letters.size();j++){
                if(letters.get(i).compareTo(letters.get(j))>0){
                    String temp = letters.get(j);
                    letters.set(j,letters.get(i));
                    letters.set(i,temp);
                }
            }
        }
    }
    //this function will update the hangman image, depending on how many strikes the player has
    private void updateHangman(int hangmanStrikes){
        //switch statement to update hangman easily
        switch(hangmanStrikes){
            case 1:
                this.lblHangman.setIcon(new ImageIcon("head.png"));//sets this speceific icon to be visible
                break;
            case 2:
                this.lblHangman.setIcon(new ImageIcon("body.png"));
                break;
            case 3:
                this.lblHangman.setIcon(new ImageIcon("rightleg.png"));
                break;
            case 4:
                this.lblHangman.setIcon(new ImageIcon("leftleg.png"));
                break;
            case 5:
                this.lblHangman.setIcon(new ImageIcon("rightarm.png"));
                break;
            case 6:
                this.lblHangman.setIcon(new ImageIcon("leftarm.png"));
                break;
            
            
        }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblScoreDisplay = new javax.swing.JLabel();
        lblLetter = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaLettersGuessed = new javax.swing.JTextArea();
        txtLetterInput = new javax.swing.JTextField();
        btnEnter = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaWord = new javax.swing.JTextArea();
        lblFeedback = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaLettersNotInWord = new javax.swing.JTextArea();
        lblHangman = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblLetter.setText("Enter a Letter or the Whole Word");

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtAreaLettersGuessed.setColumns(20);
        txtAreaLettersGuessed.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        txtAreaLettersGuessed.setRows(5);
        txtAreaLettersGuessed.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane1.setViewportView(txtAreaLettersGuessed);

        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtAreaWord.setEditable(false);
        txtAreaWord.setColumns(20);
        txtAreaWord.setFont(new java.awt.Font("Monospaced", 0, 48)); // NOI18N
        txtAreaWord.setRows(5);
        jScrollPane2.setViewportView(txtAreaWord);

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtAreaLettersNotInWord.setColumns(20);
        txtAreaLettersNotInWord.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        txtAreaLettersNotInWord.setRows(5);
        jScrollPane3.setViewportView(txtAreaLettersNotInWord);

        lblHangman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/summative/blank.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblScoreDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHangman)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(135, 135, 135)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFeedback, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(lblLetter, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLetterInput)))
                        .addGap(0, 35, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblScoreDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLetterInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLetter, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEnter)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblHangman))
                .addGap(10, 10, 10)
                .addComponent(lblFeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //this function will occur when the enter button is clicked
    //it will check to see if the letter they entered is a new one
    //if it is and it is in the word, insert it where necessary
    //it will update the hidde word and the guessed letters/lettersNotIntWord  
    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
        //input text into the text areas
        this.txtAreaLettersGuessed.setText("Guessed Letters");
        this.txtAreaLettersNotInWord.setText("Letters Not In Word");
        // the output strings reset each time button is pressed
        outputLettersNotInWord = "";
        outputGuessedLetters="";
        this.lblFeedback.setText("");
        
        String letter = this.txtLetterInput.getText().toLowerCase();//enables user to input upper or lower case
        
        //if they enter the entire word, display the whole word 
        if(letter.equals(word)){
            hiddenWord=word;
            this.txtAreaWord.setText(word);
        }
        //if they have never entered this letter before, check where it needs to inserted in the word, and insert it.
        else if(checkGuessed(letter) == false){
            
            int wordLength = word.length();

            for(int i =0;i<wordLength;i++){
                if(letter.equals(Character.toString(word.charAt(i)))){//if letter is in word
                    hiddenWord = hiddenWord.substring(0, i)+word.charAt(i)+hiddenWord.substring(i+1, wordLength);//remake hidden word with the letter added

                }
                
            }
            this.txtAreaWord.setText(hiddenWord);
        }
        //if the hidden word no longer has dashes, they have won,
        //dispose this screen and stop time
        if(!hiddenWord.contains("-")){
            this.dispose();
            double endTime = System.currentTimeMillis();
            try {
                new WinScreen(endTime-startTime).setVisible(true);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }    
        
        
        
      
          
        //calls the functions
        printGuessed();
        printNotInWord();
        this.txtLetterInput.setText("");//resets text field
        
       
                
    }//GEN-LAST:event_btnEnterActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        
        
        

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnter;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblFeedback;
    private javax.swing.JLabel lblHangman;
    private javax.swing.JLabel lblLetter;
    private javax.swing.JLabel lblScoreDisplay;
    private javax.swing.JTextArea txtAreaLettersGuessed;
    private javax.swing.JTextArea txtAreaLettersNotInWord;
    private javax.swing.JTextArea txtAreaWord;
    private javax.swing.JTextField txtLetterInput;
    // End of variables declaration//GEN-END:variables
}
