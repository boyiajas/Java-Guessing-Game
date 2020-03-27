package logicgame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class LogicGame implements ActionListener{
    
    JFrame gameFrame = new JFrame();
    JPanel mainPanel, panel1, panel2;
    
    JButton startButton = new JButton("START");
    JButton stopButton = new JButton("STOP");
    
    private volatile int colNums = 3, rowNums = 3;
    JButton buttons[][];
    List<Integer> storedGeneratedNums = new ArrayList<Integer>();
    List<Integer> storedUserGuessNums = new ArrayList<Integer>();
    //int storedGeneratedNums[] = new int[colNums];  //this is to avoid the same number been generated or been used
    //int storedUserGuessNums[] = new int[colNums];
    
    int gameLevel = 0;
    int index = 0;
    private boolean stopGame = false;
    
    //Logic Game construct
    public LogicGame(){
        //setting up the JFrame
        gameFrame.setTitle("A Simple Login Game !!!");
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(850, 650);
        
        mainPanel = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        
        panel1.setBackground(Color.getHSBColor(0.1f, 0.2f, 0.9f));
        javax.swing.border.TitledBorder border1 = new javax.swing.border.TitledBorder("Logic Game");
        javax.swing.border.TitledBorder border2 = new javax.swing.border.TitledBorder("");
        
        panel1.setLayout(null);
        panel2.setLayout(null);
        mainPanel.setLayout(null);
                
        panel2.setLayout(new GridLayout(colNums, rowNums));
        panel1.setBorder(border1);
    
        
        creatingButton(colNums, rowNums);
        
        
        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        
        //here we set the location on the panel and the size of each button
        startButton.setBounds(400, 50, 120, 30);
        stopButton.setBounds(600, 50, 120, 30);
        
        panel1.add(startButton);
        panel1.add(stopButton);
        
        panel1.setBounds(5, 0, 820, 100);
        panel2.setBounds(5, 110, 820, 480);
         
        mainPanel.add(panel1);
        mainPanel.add(panel2);
                 
        gameFrame.add(mainPanel);
      
    }

    public static void main(String[] args) {
        new LogicGame();
    }

    final Thread startProgram = new Thread(new Runnable(){
        @Override
        public void run(){
             new startMyGame().startGame();
             gameTimer.start();
             
           
        }
    });
    
    final Thread gameTimer = new Thread(new Runnable()
            {
               @Override 
                public void run()
                {  
                    System.out.println("Timer executed ------******");
                    countDownTimer(15); //time to guess the selected boxes 
                   // new startMyGame().end(); //here we are interrupting the program starting thread   
                   
                    while(!stopGame){
                        if(checkUserInput() == true){
                            JOptionPane.showMessageDialog(null, "You got it right! -- Go to the next level ");
                            clearAllBoxes();
                            storedGeneratedNums.clear(); //clearing the stored generated numbers
                            storedUserGuessNums.clear();//clearing the previous user guess numbers
                            index = 0; //reset
                            countDownTimer(2);
                            colNums++;
                            rowNums++;

                            restructButton(colNums, rowNums);
                            countDownTimer(2); //time to guess the selected boxes

                            new startMyGame().startGame();
                            countDownTimer(15); //time to guess the selected boxes 
                            //checkingRunningThread.start();
                        }else{
                            JOptionPane.showMessageDialog(null, "Sorry Time is OK try again! ");
                            clearAllBoxes();
                            storedGeneratedNums.clear(); //clearing the stored generated numbers
                            storedUserGuessNums.clear();//clearing the previous user guess numbers
                            index = 0; //reset

                            countDownTimer(2); //time to guess the selected boxes
                            new startMyGame().startGame();
                            countDownTimer(20); //time to guess the selected boxes 
                        }
                    }
                }
    });
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getSource() == startButton){
           
            startProgram.start();
            
        }else if(ae.getSource() == stopButton){
            
        }else{
               
            int boxNumbering2 = 0;
          

            for(int row = 0; row < colNums; row++){
                for(int col = 0; col < colNums; col++){
                    boxNumbering2++;

                    if(ae.getSource() == buttons[row][col]){ //checking if this button is the clicked button

                        for(int eachNum : storedGeneratedNums){ 

                            //checking if the clicked button is was part of the computer selected button 
                            if(boxNumbering2 == eachNum){
                                buttons[row][col].setBackground(Color.GREEN); //here we changing the color of a box
                                storedUserGuessNums.add(boxNumbering2);
                                index++;
                                gameFrame.revalidate();
                                mainPanel.revalidate();
                                panel2.revalidate();

                                panel2.repaint();
                                gameFrame.repaint();
                                mainPanel.repaint();
                            }
                        }
                    }
                }
            }
                
           
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public boolean checkUserInput(){
        return (index == colNums) ? true : false;    
    }
    
    public void countDownTimer(long seconds){
        try{
            Thread.sleep(1000 * seconds);
        }catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
               
        }
    }
     
    /*final Thread checkingRunningThread = new Thread(new Runnable(){
        @Override
        public void run(){
            for(;;){
                 if (startGame.isAlive()) {
                    System.out.format("%s is alive.%n", startGame.getName());
                    System.out.println("Size of stored Generated Number is : "+storedGeneratedNums.length);
                } else {
                    System.out.format("%s is not alive.%n", startGame.getName());
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LogicGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });*/
    
    public void clearAllBoxes(){
        for(int row = 0; row < colNums; row++){
            for(int col = 0; col < colNums; col++){

                buttons[row][col].setBackground(null); //here we changing the color of a box
            }
        }
        gameFrame.revalidate();
        mainPanel.revalidate();
        panel2.revalidate();

        panel2.repaint();
        gameFrame.repaint();
        mainPanel.repaint();
    }
    
    public class startMyGame {
        
        public void startGame(){
             System.out.println("game start ------******");
            startGame.start();
        }
        
        final Thread startGame = new Thread(new Runnable(){

            int pickFromNum = colNums * rowNums;
            int lower = 1;
            int upper = pickFromNum;
            
            int counter = 0;
            int randomPickNum = 0;

            @Override
            public void run(){
                System.out.println("size of Col nums : "+colNums);
                System.out.println("size of stored generated nums array "+storedGeneratedNums.size());
                System.out.println("size of random Pick Num "+randomPickNum);
                
                //here we generated all the number to be picked
               for(int i = 0; i < colNums; i++){
                   randomPickNum = (int) (Math.random() * (upper - lower)) + lower; //here we generate a number

                   if(i == 0){ 
                       storedGeneratedNums.add(randomPickNum); //here we storing the first number we dont have to check cos its the first number
                       continue;
                   }
                   //we checking if the number have been generated before to avoid generating the same number again
                   for(int num : storedGeneratedNums){
                       while(num == randomPickNum){
                           randomPickNum = (int) (Math.random() * (upper - lower)) + lower;//generate a different number
                       }

                   }
                   storedGeneratedNums.add(randomPickNum); //here we store the number generated haven checked that its unique
               }



                int boxNumbering = 0;
                boolean play = true;

                while(play){

                    for(int eachNum : storedGeneratedNums){

                        for(int row = 0; row < colNums; row++){
                            for(int col = 0; col < colNums; col++){
                                boxNumbering++;
                                if(boxNumbering == eachNum){
                                    buttons[row][col].setBackground(Color.BLUE); //here we changing the color of a box


                                    gameFrame.revalidate();
                                    mainPanel.revalidate();
                                    panel2.revalidate();

                                    panel2.repaint();
                                    gameFrame.repaint();
                                    mainPanel.repaint();

                                    try {
                                        Thread.sleep(1000); //waiting time
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(LogicGame.class.getName()).log(Level.SEVERE, null, ex);
                                    }


                                    buttons[row][col].setBackground(null); //here we changing the color of a box
                                }
                            }
                        }  
                        boxNumbering = 0; //here we reset the box number to zero again
                    }
                    play = false;

                } 
            }

        }); 
       
    }  
    
    public void restructButton(int colNum, int rowNum){
        
        this.buttons = new JButton[colNum][rowNum];
        panel2.removeAll(); 
        panel2.updateUI();
        panel2.setLayout(new GridLayout(colNum, rowNum));
        System.out.println("size of col is :"+colNum +" and the size of row is :"+rowNum);
        
         //here we initiallizing all the Jbutton
        for(int row = 0; row < colNum; row++){ System.out.println("row > "+row);
            for(int col = 0; col < colNum; col++){ System.out.println("col > "+col);
                 buttons[row][col] = new JButton("");

                 buttons[row][col].addActionListener(this);
                 
                 panel2.add(buttons[row][col]);
            }
             
        } 
       
        
        gameFrame.revalidate();
        mainPanel.revalidate();
        panel2.revalidate();

        panel2.repaint();
        gameFrame.repaint();
        mainPanel.repaint();
    }
    
    public void creatingButton(int colNum, int rowNum){
          
        this.buttons = new JButton[colNum][rowNum];
            //here we initiallizing all the Jbutton
            for(int row = 0; row < colNums; row++){
                for(int col = 0; col < colNums; col++){
                     buttons[row][col] = new JButton("");

                     buttons[row][col].addActionListener(this);
                     panel2.add(buttons[row][col]);
                }
            }        
        }
}
