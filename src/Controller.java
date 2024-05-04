import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import javafx.event.ActionEvent;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class Controller {
    private String chosenWord;
    private StringBuilder currentWord = new StringBuilder();
    private int currentLine = 0;
    private int WinLine = 0;
    private int currentStreak = 0;
    private int maxStreak = 0;
    private int played = 0;
    private int win = 0;
    private Word Word;
    private FileUsing file; 
    private FileUsing file_log; 
    private int line1Value = 0;
    private int line2Value = 0;
    private int line3Value = 0;
    private int line4Value = 0;
    private int line5Value = 0;
    private int line6Value = 0;

    public Controller() {
        Word = new Word();
        chosenWord = Word.getRandomWord().toUpperCase();
        System.out.println(chosenWord); // rememeber to comment this out
        this.file_log = new FileUsing("/Users/javibetancourt/CSCE314/Wordle/src/log.txt");
        this.file = new FileUsing("/Users/javibetancourt/CSCE314/Wordle/src/stats.txt");
    }

    @FXML
    private Button A;
    @FXML
    private Button B;
    @FXML
    private Button C;
    @FXML
    private Button D;
    @FXML
    private Button DELETE;
    @FXML
    private Button E;
    @FXML
    private Button ENTER;
    @FXML
    private Button F;
    @FXML
    private Button G;
    @FXML
    private Button H;
    @FXML
    private Button I;
    @FXML
    private Button J;
    @FXML
    private Button K;
    @FXML
    private Button L;
    @FXML
    private Label Line0_0;
    @FXML
    private Label Line0_1;
    @FXML
    private Label Line0_2;
    @FXML
    private Label Line0_3;
    @FXML
    private Label Line0_4;
    @FXML
    private Label Line1_0;
    @FXML
    private Label Line1_1;
    @FXML
    private Label Line1_2;
    @FXML
    private Label Line1_3;
    @FXML
    private Label Line1_4;
    @FXML
    private Label Line2_0;
    @FXML
    private Label Line2_1;
    @FXML
    private Label Line2_2;
    @FXML
    private Label Line2_3;
    @FXML
    private Label Line2_4;
    @FXML
    private Label Line3_0;
    @FXML
    private Label Line3_1;
    @FXML
    private Label Line3_2;
    @FXML
    private Label Line3_3;
    @FXML
    private Label Line3_4;
    @FXML
    private Label Line4_0;
    @FXML
    private Label Line4_1;
    @FXML
    private Label Line4_2;
    @FXML
    private Label Line4_3;
    @FXML
    private Label Line4_4;
    @FXML
    private Label Line5_0;
    @FXML
    private Label Line5_1;
    @FXML
    private Label Line5_2;
    @FXML
    private Label Line5_3;
    @FXML
    private Label Line5_4;
    @FXML
    private Button M;
    @FXML
    private Button N;
    @FXML
    private Button O;
    @FXML
    private Button P;
    @FXML
    private Button Q;
    @FXML
    private Button R;
    @FXML
    private Button S;
    @FXML
    private Button T;
    @FXML
    private Button U;
    @FXML
    private Button V;
    @FXML
    private Button W;
    @FXML
    private Button X;
    @FXML
    private Button Y;
    @FXML
    private Button Z;
    @FXML
    private Label WrongWord;
    @FXML
    private Label Game_Name;
    @FXML
    private HBox H1;
    @FXML
    private HBox H2;
    @FXML
    private HBox H3;
    @FXML
    private GridPane WholeGrid;
    @FXML
    private Pane Results;


    // This is for the graph
    @FXML
    private Label Current_Streak;
    @FXML
    private Label Max_Streak;
    @FXML
    private Label Played;
    @FXML
    private Label Win;
    @FXML
    private BarChart<Number, String> Graph;
    
    @FXML
    public void initialize() {
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        addDataToSeries(series, "6");
        addDataToSeries(series, "5");
        addDataToSeries(series, "4");
        addDataToSeries(series, "3");
        addDataToSeries(series, "2");
        addDataToSeries(series, "1");
        
        Graph.lookup(".chart-plot-background").setStyle("-fx-background-color: black;");
        Graph.getYAxis().setStyle("-fx-background-color: black;");
        Lines.setStyle("-fx-background-color: black; -fx-tick-label-fill: white;");

        List<XYChart.Series<Number, String>> seriesList = new ArrayList<>();
        seriesList.add(series);

        Graph.getData().add(series);
        Graph.setBarGap(0.0);
        Graph.setCategoryGap(0.0);
        Graph.setVerticalGridLinesVisible(false);
        Graph.setHorizontalGridLinesVisible(false);
        Graph.getXAxis().setTickLabelsVisible(false);
        Graph.getXAxis().setTickMarkVisible(false);
    }
    // reminder to set the value of all of them to 1 so that it even shows the bar, just update the label
    private void addDataToSeries(XYChart.Series<Number, String> series, String yValue) {
        XYChart.Data<Number, String> data = new XYChart.Data<>(1, yValue);
        Label label = new Label("0");
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setPadding(new Insets(0, 10, 0, 0));
        data.setNode(label);
        series.getData().add(data);
    }
    void updateGraph() {
        for (int i = 0; i <= 5; ++i) {
            final int index = i;
            getGraphData(index).ifPresent(data -> {
                Label label = (Label) data.getNode();
                if (WinLine == index) {
                    int newValue = data.getXValue().intValue() + 1;
                    data.setXValue(newValue);
                    label.setText(Integer.toString(newValue-1));
                    label.setStyle("-fx-background-color: green; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
                    data.getNode().setStyle("-fx-background-color: green;");
                    updateLineValue(index, newValue);
                } else {
                    label.setStyle("-fx-background-color: grey; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
                    data.getNode().setStyle("-fx-background-color: grey;");
                }
            });
        }
    }
    void updateGraphValues() {
        for (int i = 0; i <= 5; ++i) {
            final int index = i;
            getGraphData(index).ifPresent(data -> {
                int newValue = getLineValue(index);
                Label label = (Label) data.getNode();
                if (newValue == 0) {
                    label.setText("0");
                } else {
                    data.setXValue(newValue);
                    label.setText(Integer.toString(newValue - 1));
                    updateLineValue(index, newValue);
                }
            });
        }
    }

    private Optional<XYChart.Data<Number, String>> getGraphData(int index) {
        if (Graph.getData().size() > 0) {
            return Graph.getData().get(0).getData().stream()
                .filter(data -> data.getYValue().equals(Integer.toString(index + 1)))
                .findFirst();
        }
        return Optional.empty();
    }

    private int getLineValue(int index) {
        switch (index) {
            case 0: return line1Value;
            case 1: return line2Value;
            case 2: return line3Value;
            case 3: return line4Value;
            case 4: return line5Value;
            case 5: return line6Value;
            default: return 0;
        }
    }

    private void updateLineValue(int index, int newValue) {
        switch (index) {
            case 0: line1Value = newValue; break;
            case 1: line2Value = newValue; break;
            case 2: line3Value = newValue; break;
            case 3: line4Value = newValue; break;
            case 4: line5Value = newValue; break;
            case 5: line6Value = newValue; break;
        }
    }

    @FXML
    private NumberAxis Times;
    @FXML
    private CategoryAxis Lines;
    @FXML
    private Button Load;
    @FXML
    private Button Save2;
    @FXML
    private Button PlayAgain;
    @FXML
    private Button PlayAgain2;
    @FXML
    private Label WrongWord2;
    @FXML
    private Label noFile;

    @FXML
    void OnClick(ActionEvent event) {
        if (event.getSource() == Save2){
            saveStats();
        } else if (event.getSource() == Load) {
            if (file.readFromFile().isEmpty()) {
                noFile.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> noFile.setVisible(false));
                pause.play();
                return;
            }
            loadStats();
            updateGraphValues();
        }
    }
    void saveStats() {
        String stats = String.valueOf(currentStreak) + "\n" +
                    String.valueOf(maxStreak) + "\n"+
                    String.valueOf(played) + "\n" +
                    String.valueOf(win)+ "\n"+
                    String.valueOf(line1Value) + "\n"+
                    String.valueOf(line2Value) + "\n"+
                    String.valueOf(line3Value) + "\n"+
                    String.valueOf(line4Value) + "\n"+
                    String.valueOf(line5Value) + "\n"+
                    String.valueOf(line6Value);

        file.writeToFile(stats);
    }
    void loadStats() {

        String stats = file.readFromFile();
        LinkedList<String> statsList = new LinkedList<>(Arrays.asList(stats.split("\n")));
        currentStreak = Integer.parseInt(statsList.get(0));
        maxStreak = Integer.parseInt(statsList.get(1));
        played = Integer.parseInt(statsList.get(2));
        win = Integer.parseInt(statsList.get(3));
        double winPercentage = (double) win / played * 100;
        Current_Streak.setText(String.valueOf(currentStreak));
        Max_Streak.setText(String.valueOf(maxStreak));
        Played.setText(String.valueOf(played));
        Win.setText(String.format("%.2f", winPercentage));
        line1Value = Integer.parseInt(statsList.get(4));
        line2Value = Integer.parseInt(statsList.get(5));
        line3Value = Integer.parseInt(statsList.get(6));
        line4Value = Integer.parseInt(statsList.get(7));
        line5Value = Integer.parseInt(statsList.get(8));
        line6Value = Integer.parseInt(statsList.get(9));
        // System.out.println("line1Value: " + line1Value);
        // System.out.println("line2Value: " + line2Value);
        // System.out.println("line3Value: " + line3Value);
        // System.out.println("line4Value: " + line4Value);
        // System.out.println("line5Value: " + line5Value);
        // System.out.println("line6Value: " + line6Value);
    }


    void WinGame() {
        PlayAgain.setVisible(false);
        PlayAgain2.setVisible(true);
        Load.setVisible(false);
        Save2.setVisible(true);
        WholeGrid.setVisible(false);
        H1.setVisible(false);
        H2.setVisible(false);
        H3.setVisible(false);
        Results.setVisible(true);
        currentStreak++;
        played++;
        win++;
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }
        else if (maxStreak == 0) {
            maxStreak += 1;
        }
        double winPercentage = (double) win / played * 100;

        Current_Streak.setText(String.valueOf(currentStreak));
        Max_Streak.setText(String.valueOf(maxStreak));
        Played.setText(String.valueOf(played));
        Win.setText(String.format("%.2f", winPercentage));
    }
    void LoseGame() {
        PlayAgain.setVisible(false);
        PlayAgain2.setVisible(true);
        Load.setVisible(false);
        Save2.setVisible(true);
        WholeGrid.setVisible(false);
        H1.setVisible(false);
        H2.setVisible(false);
        H3.setVisible(false);
        Results.setVisible(true);
        currentStreak = 0;
        played++;
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }
        double winPercentage = (double) win / played * 100;
        Current_Streak.setText(String.valueOf(currentStreak));
        Max_Streak.setText(String.valueOf(maxStreak));
        Played.setText(String.valueOf(played));
        Win.setText(String.format("%.2f", winPercentage));
    }



    @FXML
    void playAgain(ActionEvent event) {
        // Reset the game state
        WinLine = 0;
        PlayAgain.setDisable(false);
        Load.setDisable(false);
        PlayAgain.setVisible(true);
        PlayAgain2.setVisible(false);
        Load.setVisible(true);
        Save2.setVisible(false);
        WholeGrid.setVisible(true);
        H1.setVisible(true);
        H2.setVisible(true);
        H3.setVisible(true);
        Results.setVisible(false);
        Word = new Word();
        chosenWord = Word.getRandomWord().toUpperCase();
        System.out.println(chosenWord);
        currentWord.setLength(0);
        currentLine = 0;

        // Reset the UI
        HashMap<Pair<Integer, Integer>, Label> grid = new HashMap<>();
        grid.put(new Pair<>(0, 0), Line0_0);
        grid.put(new Pair<>(0, 1), Line0_1);
        grid.put(new Pair<>(0, 2), Line0_2);
        grid.put(new Pair<>(0, 3), Line0_3);
        grid.put(new Pair<>(0, 4), Line0_4);
        grid.put(new Pair<>(1, 0), Line1_0);
        grid.put(new Pair<>(1, 1), Line1_1);
        grid.put(new Pair<>(1, 2), Line1_2);
        grid.put(new Pair<>(1, 3), Line1_3);
        grid.put(new Pair<>(1, 4), Line1_4);
        grid.put(new Pair<>(2, 0), Line2_0);
        grid.put(new Pair<>(2, 1), Line2_1);
        grid.put(new Pair<>(2, 2), Line2_2);
        grid.put(new Pair<>(2, 3), Line2_3);
        grid.put(new Pair<>(2, 4), Line2_4);
        grid.put(new Pair<>(3, 0), Line3_0);
        grid.put(new Pair<>(3, 1), Line3_1);
        grid.put(new Pair<>(3, 2), Line3_2);
        grid.put(new Pair<>(3, 3), Line3_3);
        grid.put(new Pair<>(3, 4), Line3_4);
        grid.put(new Pair<>(4, 0), Line4_0);
        grid.put(new Pair<>(4, 1), Line4_1);
        grid.put(new Pair<>(4, 2), Line4_2);
        grid.put(new Pair<>(4, 3), Line4_3);
        grid.put(new Pair<>(4, 4), Line4_4);
        grid.put(new Pair<>(5, 0), Line5_0);
        grid.put(new Pair<>(5, 1), Line5_1);
        grid.put(new Pair<>(5, 2), Line5_2);
        grid.put(new Pair<>(5, 3), Line5_3);
        grid.put(new Pair<>(5, 4), Line5_4);
        for (Map.Entry<Pair<Integer, Integer>, Label> entry : grid.entrySet()) {
            Label label = entry.getValue();
            label.setText("");
            label.setStyle("");
            label.setStyle("-fx-border-color: black;");
            label.setStyle("-fx-background-color: white;");
        }
        Map<Character, Button> allButtons = new HashMap<>();
        allButtons.put('A', A);
        allButtons.put('B', B);
        allButtons.put('C', C);
        allButtons.put('D', D);
        allButtons.put('E', E);
        allButtons.put('F', F);
        allButtons.put('G', G);
        allButtons.put('H', H);
        allButtons.put('I', I);
        allButtons.put('J', J);
        allButtons.put('K', K);
        allButtons.put('L', L);
        allButtons.put('M', M);
        allButtons.put('N', N);
        allButtons.put('O', O);
        allButtons.put('P', P);
        allButtons.put('Q', Q);
        allButtons.put('R', R);
        allButtons.put('S', S);
        allButtons.put('T', T);
        allButtons.put('U', U);
        allButtons.put('V', V);
        allButtons.put('W', W);
        allButtons.put('X', X);
        allButtons.put('Y', Y);
        allButtons.put('Z', Z);
        allButtons.put(' ', DELETE);
        allButtons.put(' ', ENTER);
        for (Button button : allButtons.values()) {
            button.setDisable(false);
            DELETE.setDisable(false);
            button.setStyle("");
        }
    }

    @FXML
    void OnAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        //LinkedList<Button> allButtons = new LinkedList<>(Arrays.asList(Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M));
        // copy and paste into restart and play again
        HashMap<Pair<Integer, Integer>, Label> grid = new HashMap<>();
        grid.put(new Pair<>(0, 0), Line0_0);
        grid.put(new Pair<>(0, 1), Line0_1);
        grid.put(new Pair<>(0, 2), Line0_2);
        grid.put(new Pair<>(0, 3), Line0_3);
        grid.put(new Pair<>(0, 4), Line0_4);
        grid.put(new Pair<>(1, 0), Line1_0);
        grid.put(new Pair<>(1, 1), Line1_1);
        grid.put(new Pair<>(1, 2), Line1_2);
        grid.put(new Pair<>(1, 3), Line1_3);
        grid.put(new Pair<>(1, 4), Line1_4);
        grid.put(new Pair<>(2, 0), Line2_0);
        grid.put(new Pair<>(2, 1), Line2_1);
        grid.put(new Pair<>(2, 2), Line2_2);
        grid.put(new Pair<>(2, 3), Line2_3);
        grid.put(new Pair<>(2, 4), Line2_4);
        grid.put(new Pair<>(3, 0), Line3_0);
        grid.put(new Pair<>(3, 1), Line3_1);
        grid.put(new Pair<>(3, 2), Line3_2);
        grid.put(new Pair<>(3, 3), Line3_3);
        grid.put(new Pair<>(3, 4), Line3_4);
        grid.put(new Pair<>(4, 0), Line4_0);
        grid.put(new Pair<>(4, 1), Line4_1);
        grid.put(new Pair<>(4, 2), Line4_2);
        grid.put(new Pair<>(4, 3), Line4_3);
        grid.put(new Pair<>(4, 4), Line4_4);
        grid.put(new Pair<>(5, 0), Line5_0);
        grid.put(new Pair<>(5, 1), Line5_1);
        grid.put(new Pair<>(5, 2), Line5_2);
        grid.put(new Pair<>(5, 3), Line5_3);
        grid.put(new Pair<>(5, 4), Line5_4);

        if (clickedButton == DELETE) {
            for (int i = 4; i >= 0; i--) {
            Label label = grid.get(new Pair<>(currentLine, i));
            if (!label.getText().isEmpty()) {
                label.setText("");
                currentWord.deleteCharAt(currentWord.length() - 1);
                return;
            }
        }
        }
        else if (clickedButton == ENTER) {
            file_log.writeToFile2(currentWord.toString());
            if (currentWord.length() != 5) {
                WrongWord2.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> WrongWord2.setVisible(false));
                pause.play();
                return;
            }
            if (!Word.isWordInFile(currentWord.toString().toLowerCase())) {
                WrongWord.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> WrongWord.setVisible(false));
                pause.play();
                return;
            }
            String tempChosenWord = new String(chosenWord);
            Map<Character, Button> letterButtons = new HashMap<>();
            letterButtons.put('A', A);
            letterButtons.put('B', B);
            letterButtons.put('C', C);
            letterButtons.put('D', D);
            letterButtons.put('E', E);
            letterButtons.put('F', F);
            letterButtons.put('G', G);
            letterButtons.put('H', H);
            letterButtons.put('I', I);
            letterButtons.put('J', J);
            letterButtons.put('K', K);
            letterButtons.put('L', L);
            letterButtons.put('M', M);
            letterButtons.put('N', N);
            letterButtons.put('O', O);
            letterButtons.put('P', P);
            letterButtons.put('Q', Q);
            letterButtons.put('R', R);
            letterButtons.put('S', S);
            letterButtons.put('T', T);
            letterButtons.put('U', U);
            letterButtons.put('V', V);
            letterButtons.put('W', W);
            letterButtons.put('X', X);
            letterButtons.put('Y', Y);
            letterButtons.put('Z', Z);

            Map<Character, Button> allButtons = new HashMap<>();
            allButtons.put('A', A);
            allButtons.put('B', B);
            allButtons.put('C', C);
            allButtons.put('D', D);
            allButtons.put('E', E);
            allButtons.put('F', F);
            allButtons.put('G', G);
            allButtons.put('H', H);
            allButtons.put('I', I);
            allButtons.put('J', J);
            allButtons.put('K', K);
            allButtons.put('L', L);
            allButtons.put('M', M);
            allButtons.put('N', N);
            allButtons.put('O', O);
            allButtons.put('P', P);
            allButtons.put('Q', Q);
            allButtons.put('R', R);
            allButtons.put('S', S);
            allButtons.put('T', T);
            allButtons.put('U', U);
            allButtons.put('V', V);
            allButtons.put('W', W);
            allButtons.put('X', X);
            allButtons.put('Y', Y);
            allButtons.put('Z', Z);
            allButtons.put(' ', DELETE);
            allButtons.put(' ', ENTER);

            for (int i = 0; i < currentWord.length(); ++i) {
                char currentChar = currentWord.charAt(i);
                Button change_colorButton = letterButtons.get(currentChar);
    
                if (currentChar == chosenWord.charAt(i)) {
                    change_colorButton.setStyle("-fx-background-color: green;");
                    grid.get(new Pair<>(currentLine, i)).setStyle("-fx-background-color: green;");
                    tempChosenWord = tempChosenWord.replaceFirst(Character.toString(currentChar), "");
                }
            }
            // logic for color
            for (int i = 0; i < currentWord.length(); ++i) {
                char currentChar = currentWord.charAt(i);
                Button change_colorButton = letterButtons.get(currentChar);
    
                if (grid.get(new Pair<>(currentLine, i)).getStyle().contains("green")) {
                    continue;
                }
                if (tempChosenWord.indexOf(currentChar) != -1) {
                    change_colorButton.setStyle("-fx-background-color: yellow;");
                    grid.get(new Pair<>(currentLine, i)).setStyle("-fx-background-color: yellow;");
                    tempChosenWord = tempChosenWord.replaceFirst(Character.toString(currentChar), "");
                } else {
                    grid.get(new Pair<>(currentLine, i)).setStyle("-fx-background-color: grey;");
                    if (change_colorButton.getStyle() == "-fx-background-color: green;" || change_colorButton.getStyle() == "-fx-background-color: yellow;"){
                        change_colorButton.setDisable(false);
                    }
                    else {
                        change_colorButton.setDisable(true);
                        grid.get(new Pair<>(currentLine, i)).setStyle("-fx-background-color: grey;");
                        change_colorButton.setStyle("-fx-background-color: grey;");
                    }
                }
            }
            if (chosenWord.equals(currentWord.toString())) {
                WinLine = currentLine;
                updateGraph();
                for (Button button : allButtons.values()) {
                    DELETE.setDisable(true);
                    button.setDisable(true);
                }
                PlayAgain.setDisable(true);
                Load.setDisable(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> WinGame());
                pause.play();
            }
            if (currentLine == 5 && !chosenWord.equals(currentWord.toString())) {
                for (Button button : allButtons.values()) {
                    DELETE.setDisable(true);
                    button.setDisable(true);
                }
                PlayAgain.setDisable(true);
                Load.setDisable(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> LoseGame());
                pause.play();
            }
            currentWord.setLength(0);
            if (currentLine < 5) {
                currentLine++;
            }
        }
        else {
            if (currentWord.length() < 5) {
                currentWord.append(buttonText);
                for (int i = 0; i < 5; i++) {
                    Label label = grid.get(new Pair<>(currentLine, i));
                    if (label.getText().isEmpty()) {
                        label.setText(buttonText);
                        return;
                    }
                }
            }
        }
    }
}