import java.util.*;
public class JavaGame1{
    static ArrayList<Integer> playerPositions = new ArrayList<Integer>();
    static ArrayList<Integer> cpuPositions = new ArrayList<Integer>();
    public static String message1, message2,message3,message4,message5,message6,message7;
    public static void main(String[] args) {

        char [] [] gameBoard={{' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}};
        //SelectLanguage(gameBoard);
        printGameBoard(gameBoard);
        while(true){
            try{
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter your placement (1 - 9):");
                int playerPos = scan.nextInt();

                if(playerPos>9|| playerPos<1){
                    throw new CaseException("Wrong placement!");
                }
                while (playerPositions.contains(playerPos) || cpuPositions.contains(playerPos)){
                    System.out.println("Placement taken! Choose another one.");
                    playerPos = scan.nextInt();
                }
                placePiece(gameBoard, playerPos, "player");
                String result =checkWinner();
                if(result.length()>0){
                    System.out.println(result);
                    break;
                }
                //DefenceStrategy(gameBoard);
                Random rand = new Random();
                int cpuPos =rand.nextInt(9) + 1;
                while (playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos)){
                    cpuPos =rand.nextInt(9) + 1;
                }
                placePiece(gameBoard, cpuPos, "cpu");
                printGameBoard(gameBoard);
                result =checkWinner();
                if(result.length()>0){
                    System.out.println(result);
                    break;
                }
                System.out.println(result);
            }catch(InputMismatchException e)
            {
                System.out.println("Error! Wrong input!");
                printGameBoard(gameBoard);
            }
            catch (Exception e){
                System.out.println("Error! " +e.getMessage());
                printGameBoard(gameBoard);
            }
        }
    }
    public static void printGameBoard(char [] [] gameBoard){
        for(char [] row: gameBoard){
            System.out.println(row);
        }
    }
    public static void placePiece(char [][] gameBoard, int pos, String user){
        char symbol = ' ';
        if(user.equals("player")){
            symbol = 'X';
            playerPositions.add(pos);
        }else if(user.equals("cpu")){
            symbol ='O';
            cpuPositions.add(pos);
        }
        switch (pos){
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }
    }
    public static String checkWinner(){
        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(7, 5, 3);
        List<List> winning = new ArrayList<List>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(cross1);
        winning.add(cross2);
        for(List l : winning){
            if(playerPositions.containsAll(l)){
                return "VICTORY!";
            } else if(cpuPositions.containsAll(l)){
                return "You lost!";
            }else if(playerPositions.size()+ cpuPositions.size()==9){
                return "Draw.";
            }
        }
        return "";
    }
    public static void SelectLanguage(char[][]gameBoard){
        try {
            System.out.println("Select language./Выберите язык.\n1) English.\n2) Русский.");

            int number=0;
            if(number<1||number>2){
                throw new CaseException("Wrong input!");
            }
            switch (number){
                case 1:
                    message1 = "Enter your placement (1 - 9):";
                    message2 = "Wrong placement!";
                    message3 = "Placement taken! Choose another one.";
                    message4 = "Error! ";
                    message5 = "VICTORY!";
                    message6 = "You lost!";
                    message7 = "Draw.";
                    break;
                case 2:
                    message1 = "Введите вашу позицию (1 - 9):";
                    message2 = "Некорректная позиция!";
                    message3 = "Позиция занята! Выберите другую.";
                    message4 = "Ошибка! ";
                    message5 = "ПОБЕДА!";
                    message6 = "Вы проиграли!";
                    message7 = "Ничья.";
                    break;
            }
        }catch(InputMismatchException e){
            System.out.println("Error! Wrong input!");
        } catch (Exception e){
            System.out.println("Error! " +e.getMessage());
            printGameBoard(gameBoard);
        }
    }
    public static void DefenceStrategy(char[][] gameBoard){
        // Первая строка
        if(gameBoard[0][0] == 'X' || gameBoard[0][2] == 'X' ){
            gameBoard[0][6] = 'O';
        } else if(gameBoard[0][0] == 'X' || gameBoard[0][4] == 'X' ){
            gameBoard[0][2] = 'O';
        } else if(gameBoard[0][2] == 'X' || gameBoard[0][4] == 'X' ){
            gameBoard[0][0] = 'O';
            // Вторая строка
        } else if(gameBoard[2][0] == 'X' || gameBoard[2][2] == 'X' ){
            gameBoard[2][4] = 'O';
        } else if(gameBoard[2][0] == 'X' || gameBoard[2][4] == 'X' ){
            gameBoard[2][2] = 'O';
        } else if(gameBoard[2][2] == 'X' || gameBoard[2][4] == 'X' ){
            gameBoard[2][0] = 'O';
            // Третья строка
        } else if(gameBoard[4][0] == 'X' || gameBoard[4][2] == 'X' ){
            gameBoard[4][4] = 'O';
        } else if(gameBoard[4][0] == 'X' || gameBoard[4][4] == 'X' ){
            gameBoard[4][2] = 'O';
        } else if(gameBoard[4][2] == 'X' || gameBoard[4][4] == 'X' ){
            gameBoard[4][0] = 'O';
            // Первый столбец
        } else if(gameBoard[0][0] == 'X' || gameBoard[2][0] == 'X' ){
            gameBoard[4][0] = 'O';
        } else if(gameBoard[0][0] == 'X' || gameBoard[4][0] == 'X' ){
            gameBoard[2][0] = 'O';
        } else if(gameBoard[2][0] == 'X' || gameBoard[4][0] == 'X' ){
            gameBoard[0][0] = 'O';
            // Второй столбец
        } else if(gameBoard[0][2] == 'X' || gameBoard[2][2] == 'X' ){
            gameBoard[4][2] = 'O';
        } else if(gameBoard[0][2] == 'X' || gameBoard[4][2] == 'X' ){
            gameBoard[2][2] = 'O';
        } else if(gameBoard[2][2] == 'X' || gameBoard[4][2] == 'X' ){
            gameBoard[0][2] = 'O';
            // Третий столбец
        } else if(gameBoard[0][4] == 'X' || gameBoard[2][4] == 'X' ){
            gameBoard[4][4] = 'O';
        } else if(gameBoard[0][4] == 'X' || gameBoard[4][4] == 'X' ){
            gameBoard[2][4] = 'O';
        } else if(gameBoard[2][4] == 'X' || gameBoard[4][4] == 'X' ){
            gameBoard[0][4] = 'O';
            // Левая диагональ
        } else if(gameBoard[0][0] == 'X' || gameBoard[2][2] == 'X' ){
            gameBoard[4][4] = 'O';
        } else if(gameBoard[0][0] == 'X' || gameBoard[4][4] == 'X' ){
            gameBoard[2][2] = 'O';
        } else if(gameBoard[2][2] == 'X' || gameBoard[4][4] == 'X' ){
            gameBoard[0][0] = 'O';
            // Правав диагональ
        } else if(gameBoard[0][4] == 'X' || gameBoard[2][2] == 'X' ){
            gameBoard[4][0] = 'O';
        } else if(gameBoard[0][4] == 'X' || gameBoard[4][0] == 'X' ){
            gameBoard[2][2] = 'O';
        } else if(gameBoard[2][2] == 'X' || gameBoard[4][0] == 'X' ){
            gameBoard[0][4] = 'O';
          }
       }
    }




