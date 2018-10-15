/*
    Trent Church
    Program to test the number of ms for different sort alg
 */
package project4_church;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author trent_000
 */
public class Project4_Church extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    public boolean checkValid(ToggleGroup alg, ToggleGroup type, String arr, String block) {
        if (alg.getSelectedToggle() == null) {
            Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
            notSelected.setTitle("Not Selected");
            notSelected.setHeaderText("You must select a type of sort");
            notSelected.showAndWait();
            return false;
        } else if (type.getSelectedToggle() == null) {
            Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
            notSelected.setTitle("Not Selected");
            notSelected.setHeaderText("You must select an type of Array");
            notSelected.showAndWait();
            return false;
        }
        try {

            if (Integer.parseInt(arr) < 1) {
                Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
                notSelected.setTitle("Not Valid");
                notSelected.setHeaderText("The array size must be greater than 0");
                notSelected.showAndWait();
                return false;
            }
        } catch (NumberFormatException e) {
            Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
            notSelected.setTitle("Not Valid");
            notSelected.setHeaderText("You must enter a valid integer for a size of an array");
            notSelected.showAndWait();
            return false;
        }
        try {
            if (Integer.parseInt(block) < 1) {
                Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
                notSelected.setTitle("Not Valid");
                notSelected.setHeaderText("The array size must be greater than 0");
                notSelected.showAndWait();
                return false;
            }
        } catch (NumberFormatException e) {
            Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
            notSelected.setTitle("Not Valid");
            notSelected.setHeaderText("You must enter a valid integer for a size of a block");
            notSelected.showAndWait();
            return false;
        }
        if ((Integer.parseInt(arr) / Integer.parseInt(block)) > 1000) {
            Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
            notSelected.setTitle("Not Valid");
            notSelected.setHeaderText("There can not be over 1000 blocks");
            notSelected.showAndWait();
            return false;
        }

        return true;
    }

    public int[] makeArray(int size, char type) {
        int[] arr = new int[size];
        switch (type) {
            case 'o':
                for (int i = 0; i < size; i++) {
                    arr[i] = i;
                }
                break;
            case 'b':
                for (int i = 0; i < size; i++) {
                    arr[i] = size - (i + 1);
                }
                break;
            case 'r':
                for (int i = 0; i < size; i++) {
                    arr[i] = (int) (Math.random() * 99);
                }
                break;
        }
        return arr;
    }

    public void merge(Queue<int[]> mergeq) {
        ArrayList<Merge> marr = new ArrayList<>();
        if (mergeq.size() < 2) {
            return;
        }
        while (mergeq.size() > 1) {
            marr.add(new Merge(mergeq.poll(), mergeq.poll()));
        }
        for (int i = 0; i < marr.size(); i++) {
            marr.get(i).start();

        }
        for (int i = 0; i < marr.size(); i++) {
            try {
                marr.get(i).join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Project4_Church.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Merge marr1 : marr) {
            mergeq.offer(marr1.getFinalArray());
        }

        merge(mergeq);

    }

    public long sort(int[] arr, int bSize, char selection) {
        ArrayList<int[]> a1 = new ArrayList();
        ArrayList<sortAlg> sortArray = new ArrayList();
        int count = 0;
        for (int j = 0; j < (int) arr.length / bSize; j++) {
            a1.add(Arrays.copyOfRange(arr, bSize * count, bSize * (count + 1)));
            count++;
        }
        if (!(arr.length % bSize == 0)) {
            a1.add(Arrays.copyOfRange(arr, bSize * count, arr.length));
        }
        Queue<int[]> mergeq = new LinkedList();

        //creatinf object to be multithreaded
        for (int i = 0; i < a1.size(); i++) {
            sortArray.add(new sortAlg(selection, a1.get(i)));
        }
        long start = System.currentTimeMillis();
        //starting sorting
        for (int i = 0; i < sortArray.size(); i++) {
            sortArray.get(i).start();
        }
        //joining all sorting
        for (int i = 0; i < sortArray.size(); i++) {
            try {
                sortArray.get(i).join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Project4_Church.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //putting the array in a queue
        for (int i = 0; i < sortArray.size(); i++) {
            mergeq.offer(sortArray.get(i).getsA());
        }

        //merging the result
        merge(mergeq);
        long end = System.currentTimeMillis();
        System.out.println(Arrays.toString(mergeq.peek()));
        return end - start;
    }

    //checking the array type
    public char typeList(ToggleGroup type) {
        Object selected = type.getSelectedToggle();
        RadioButton rb = (RadioButton) selected;
        if (rb.getText().equals("Reverse Order")) {
            return 'b';
        }
        if (rb.getText().equals("Random Order")) {
            return 'r';
        }
        return 'o';

    }
    //check the sorting algorithm
    public char sortList(ToggleGroup type) {
        Object selected = type.getSelectedToggle();
        RadioButton rb = (RadioButton) selected;
        if (rb.getText().equals("Selection")) {
            return 's';
        }
        if (rb.getText().equals("Bubble")) {
            return 'b';
        }
        if (rb.getText().equals("Insertion")) {
            return 'i';
        }
        return 'q';

    }

    public void start(Stage stage) throws Exception {
        VBox base = new VBox(10);
        VBox sortSelection = new VBox(5);
        Label sortAlg = new Label("Sorting Algorithm:");
        sortSelection.setStyle("-fx-border-style: solid inside;");
        sortSelection.setPadding(new Insets(10));

        RadioButton selection = new RadioButton("Selection");
        RadioButton bubble = new RadioButton("Bubble");
        RadioButton insertion = new RadioButton("Insertion");
        RadioButton quick = new RadioButton("Quick");
        ToggleGroup alg = new ToggleGroup();

        selection.setToggleGroup(alg);
        bubble.setToggleGroup(alg);
        insertion.setToggleGroup(alg);
        quick.setToggleGroup(alg);
        sortSelection.getChildren().addAll(sortAlg, selection, bubble, insertion, quick);

        VBox inputType = new VBox(5);
        inputType.setStyle("-fx-border-style: solid inside;");
        inputType.setPadding(new Insets(10));
        Label inType = new Label("Input Type:");
        RadioButton alreadySort = new RadioButton("Already Sorted");
        RadioButton reverseOrder = new RadioButton("Reverse Order");
        RadioButton randomOrder = new RadioButton("Random Order");
        ToggleGroup type = new ToggleGroup();
        alreadySort.setToggleGroup(type);
        reverseOrder.setToggleGroup(type);
        randomOrder.setToggleGroup(type);
        inputType.getChildren().addAll(inType, alreadySort, reverseOrder, randomOrder);

        HBox arraySize = new HBox();
        HBox blockSize = new HBox();

        Label arrayL = new Label("Size of Array: ");
        Label blockL = new Label("Size of Block: ");
        TextField arrayTF = new TextField();
        TextField blockTF = new TextField();

        arraySize.getChildren().addAll(arrayL, arrayTF);
        blockSize.getChildren().addAll(blockL, blockTF);

        Button go = new Button("GO!");
        go.setStyle("-fx-alignment: CENTER;");
        go.setPrefWidth(200);

        base.getChildren().addAll(sortSelection, inputType, arraySize, blockSize, go);
        base.setPadding(new Insets(10));
        base.setStyle("-fx-alignment: CENTER;");

        go.setOnAction(e -> {
            //
            if (checkValid(alg, type, arrayTF.getText(), blockTF.getText())) {
                Long time = sort(makeArray(Integer.parseInt(arrayTF.getText()), typeList(type)), Integer.parseInt(blockTF.getText()), sortList(alg));
                Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
                notSelected.setTitle("Time of Sort");
                notSelected.setHeaderText("Finished");
                notSelected.setContentText("Sort completed in "+time.toString() + " ms");
                notSelected.showAndWait();
            }
        });
        Scene scene = new Scene(base);
        stage.setScene(scene);
        stage.show();
    }
}
