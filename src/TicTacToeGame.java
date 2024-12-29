import java.util.*;

public class TicTacToeGame {
    static ArrayList<Integer> playerPositions = new ArrayList<Integer>();
    static ArrayList<Integer> cpuPositions = new ArrayList<Integer>();
    public static String message1, message2, message3, message4, message5, message6, message7;

    public static void main(String[] args) {
        char[][] gameBoard = { { ' ', '|', ' ', '|', ' ' }, { '-', '+', '-', '+', '-' }, { ' ', '|', ' ', '|', ' ' },
                { '-', '+', '-', '+', '-' }, { ' ', '|', ' ', '|', ' ' } };

        selectLanguage(gameBoard);

        printGameBoard(gameBoard);

        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println(message1);
                int playerPos = scan.nextInt();

                if (playerPos < 1 || playerPos > 9) {
                    throw new Exception(message2);
                }

                while (playerPositions.contains(playerPos) || cpuPositions.contains(playerPos)) {
                    System.out.println(message3);
                    playerPos = scan.nextInt();
                }

                placePiece(gameBoard, playerPos, "player");

                String result = checkWinner();
                if (!result.isEmpty()) {
                    System.out.println(result);
                    break;
                }

                int cpuPos = findBestMove(gameBoard);
                placePiece(gameBoard, cpuPos, "cpu");

                printGameBoard(gameBoard);

                result = checkWinner();
                if (!result.isEmpty()) {
                    System.out.println(result);
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println(message4 + "Wrong input!");
            } catch (Exception e) {
                System.out.println(message4 + e.getMessage());
            }
        }
    }

    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            System.out.println(row);
        }
    }

    public static void placePiece(char[][] gameBoard, int pos, String user) {
        char symbol = ' ';
        if (user.equals("player")) {
            symbol = 'X';
            playerPositions.add(pos);
        } else if (user.equals("cpu")) {
            symbol = 'O';
            cpuPositions.add(pos);
        }
        switch (pos) {
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

    public static String checkWinner() {
        List<List<Integer>> winning = Arrays.asList(
                Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9),
                Arrays.asList(1, 4, 7), Arrays.asList(2, 5, 8), Arrays.asList(3, 6, 9),
                Arrays.asList(1, 5, 9), Arrays.asList(3, 5, 7)
        );

        for (List<Integer> l : winning) {
            if (playerPositions.containsAll(l)) {
                return message5;
            } else if (cpuPositions.containsAll(l)) {
                return message6;
            } else if (playerPositions.size() + cpuPositions.size() == 9) {
                return message7;
            }
        }
        return "";
    }

//    public static int findDefensiveMove(char[][] gameBoard) {
//        List<List<Integer>> winningMoves = Arrays.asList(
//                Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9),
//                Arrays.asList(1, 4, 7), Arrays.asList(2, 5, 8), Arrays.asList(3, 6, 9),
//                Arrays.asList(1, 5, 9), Arrays.asList(3, 5, 7)
//        );
//
//        for (List<Integer> move : winningMoves) {
//            int countPlayer = 0;
//            int countCPU = 0;
//            int emptyPos = 0;
//            for (int pos : move) {
//                if (playerPositions.contains(pos)) {
//                    countPlayer++;
//                } else if (cpuPositions.contains(pos)) {
//                    countCPU++;
//                } else {
//                    emptyPos = pos;
//                }
//            }
//            if (countPlayer == 2 && countCPU == 0) {
//                return emptyPos;
//            }
//        }
//
//        for (List<Integer> move : winningMoves) {
//            int countPlayer = 0;
//            int countCPU = 0;
//            int emptyPos1 = 0;
//            int emptyPos2 = 0;
//            for (int pos : move) {
//                if (playerPositions.contains(pos)) {
//                    countPlayer++;
//                } else if (cpuPositions.contains(pos)) {
//                    countCPU++;
//                } else {
//                    if (emptyPos1 == 0) {
//                        emptyPos1 = pos;
//                    } else {
//                        emptyPos2 = pos;
//                    }
//                }
//            }
//            if (countPlayer == 0 && countCPU == 1 && emptyPos1 != 0 && emptyPos2 != 0) {
//                return emptyPos1;
//            }
//        }
//
//        return findBestMove(gameBoard);
//    }

//    public static int findBestMove(char[][] gameBoard) {
//        Random rand = new Random();
//        int cpuPos = rand.nextInt(9) + 1;
//        while (playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos)) {
//            cpuPos = rand.nextInt(9) + 1;
//        }
//        return cpuPos;
//    }

    public static int findBestMove(char[][] gameBoard) {
        List<List<Integer>> winningMoves = Arrays.asList(
                Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9),
                Arrays.asList(1, 4, 7), Arrays.asList(2, 5, 8), Arrays.asList(3, 6, 9),
                Arrays.asList(1, 5, 9), Arrays.asList(3, 5, 7)
        );

        int winningMove = findStrategicMove(winningMoves, cpuPositions, playerPositions);
        if (winningMove != -1) {
            return winningMove;
        }

        int blockingMove = findStrategicMove(winningMoves, playerPositions, cpuPositions);
        if (blockingMove != -1) {
            return blockingMove;
        }
        // мод при котором никогда не получится выиграть тк занимается центр

//        if (!playerPositions.contains(5) && !cpuPositions.contains(5)) {
//            return 5;
//        }

        return getRandomFreePosition();
    }

    public static int findStrategicMove(List<List<Integer>> winningMoves, List<Integer> currentPlayerPositions, List<Integer> opponentPositions) {
        for (List<Integer> move : winningMoves) {
            int countCurrent = 0;
            int countEmpty = 0;
            int emptyPos = 0;

            for (int pos : move) {
                if (currentPlayerPositions.contains(pos)) {
                    countCurrent++;
                } else if (!opponentPositions.contains(pos)) {
                    countEmpty++;
                    emptyPos = pos;
                }
            }

            if (countCurrent == 2 && countEmpty == 1) {
                return emptyPos;
            }
        }
        return -1;
    }

    public static int getRandomFreePosition() {
        Random rand = new Random();
        int pos = rand.nextInt(9) + 1;
        while (playerPositions.contains(pos) || cpuPositions.contains(pos)) {
            pos = rand.nextInt(9) + 1;
        }
        return pos;
    }


    public static void selectLanguage(char[][] gameBoard) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select language" +
                    "\n1) English." +
                    "\n2) Русский." +
                    "\n3) Español." +
                    "\n4) Français." +
                    "\n5) Deutsch.");
            int number = scanner.nextInt();
            switch (number) {
                case 1:
                    message1 = "Enter your placement (1 - 9):";
                    message2 = "Wrong placement!";
                    message3 = "Placement taken! Choose another one.";
                    message4 = "Error! ";
                    message5 = "You won!";
                    message6 = "You lost!";
                    message7 = "Draw.";
                    break;
                case 2:
                    message1 = "Введите вашу позицию (1 - 9):";
                    message2 = "Некорректная позиция!";
                    message3 = "Позиция занята! Выберите другую.";
                    message4 = "Ошибка! ";
                    message5 = "Вы выиграли!";
                    message6 = "Вы проиграли!";
                    message7 = "Ничья.";
                    break;
                case 3:
                    message1 = "Introduzca su posición (1 - 9):";
                    message2 = "¡Posición incorrecta!";
                    message3 = "Puesto ocupado! Elige otro.";
                    message4 = "¡Error! ";
                    message5 = "¡Tú ganas!";
                    message6 = "¡Perdiste!";
                    message7 = "Dibujar.";
                    break;
                case 4:
                    message1 = "Entrez votre position (1 - 9) :";
                    message2 = "Mauvaise position!";
                    message3 = "Poste pourvu ! Choisissez-en un autre.";
                    message4 = "Erreur! ";
                    message5 = "Vous gagnez !";
                    message6 = "Vous avez perdu !";
                    message7 = "Dessiner.";
                    break;
                case 5:
                    message1 = "Geben Sie Ihre Position ein (1 - 9):";
                    message2 = "Falsche Position!";
                    message3 = "Stelle besetzt! Wählen Sie ein anderes.";
                    message4 = "Fehler! ";
                    message5 = "Du gewinnst!";
                    message6 = "Du hast verloren!";
                    message7 = "Ничья.";
                    break;
                default:
                    throw new CaseException("Wrong input!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error! Wrong input!");
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
            printGameBoard(gameBoard);
        }
    }
}




