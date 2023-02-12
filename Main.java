//PROG2 VT2020, Inlämmning 2
//Mohammed Tahmid Chowdhury moch8386

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.*;
import java.util.Map;
import java.util.Optional;

///ber om ursäkt i förväg med språket på kommentarerna, har inte orkat fixa det
public class Main extends Application {
    private Stage window;
    private BorderPane assignmentTwo;
    private Pane insertImage;
    private Button ny;
    private Label categ;
    private TextField searchfield;
    private Button search;
    private Button hide;
    private Button remove;
    private Button coordinates;
    private Button hidecateg;
    private ToggleGroup val;
    private RadioButton named;
    private RadioButton descrip;
    private HBox uppredel;
    private VBox radval;
    private Scene scene;
    private VBox flik;
    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem loadItem;
    private MenuItem loadPlaces;
    private MenuItem savewhatevz;
    private MenuItem exitExe;
    private VBox upup;
    private Image image;
    private ImageView imageView = new ImageView();
    private String valditem;
    private VBox left;
    private boolean changed;


    private ListView<Category> list;
    private final ObservableList<Category> categoryList = FXCollections.observableArrayList(Category.Bus, Category.Underground, Category.Train);


    //Sparar saker
    private Map<String, Set<Place>> byName = new HashMap<>(); //Sparar alla samma namn i en samling
    private Map<Position, Place> positionlocater = new HashMap<>(); //nanmnet säger allt
    private Map<String, Set<Place>> byCateg = new HashMap<>(); //sparar alla i samma kategori i en samling
    private LinkedList<Place> marked = new LinkedList<>(); //Markerad = Gul markerade


    public static void main(String[] args) {
        launch(args);
    }//main static void args

    @Override
    public void start(Stage primaryStage) {

        //Göra det simpel för mig
        window = primaryStage;
        window.setTitle("Inlämmning 2");
        //Border pane skapas
        assignmentTwo = new BorderPane();
        //Kategori i en lista
        list = new ListView<>(categoryList);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.setMaxSize(300, 100);


        //Vbox för uppredel med flik och edit saker
        upup = new VBox();
        upup.getChildren().addAll(uppreDelFil(), uppreDelButton());

        categ = new Label("Category");
        hidecateg = new Button("Hide Category");
        hidecateg.setOnAction(new HideHandler());

        //VBox för Kategori
        left = new VBox();
        left.setAlignment(Pos.CENTER);
        left.setPadding(new Insets(20, 20, 20, 20));
        left.getChildren().addAll(categ, list, hidecateg);

        ///Pane för image view
        insertImage = new Pane();
        insertImage.getChildren().add(imageView);


        //Border pane
        assignmentTwo.setTop(upup);
        assignmentTwo.setLeft(insertImage);
        assignmentTwo.setRight(left);


        //Scene
        scene = new Scene(assignmentTwo);
        window.setScene(scene);
        window.setOnCloseRequest(new ExitHandler());
        window.show();


    }//Start

    private Node uppreDelFil() {
//upredel fil
        flik = new VBox();
        menuBar = new MenuBar();
        flik.getChildren().add(menuBar);
        fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);
        loadItem = new MenuItem("Load Map");
        loadPlaces = new MenuItem("Load Places");
        savewhatevz = new MenuItem("Save");
        exitExe = new MenuItem("Exit");
        fileMenu.getItems().addAll(loadItem, loadPlaces, savewhatevz, exitExe);
        loadItem.setOnAction(new LoadHandler());
        exitExe.setOnAction(new ExitClicked());
        loadPlaces.setOnAction(new LoadPlacesHandler());
        savewhatevz.setOnAction(new SavePlaceHandler());
        return flik;
    }

    private Node uppreDelButton() {
        //upredel button etc
        searchfield = new TextField();
        searchfield.setPromptText("Search");
        search = new Button("Search");
        hide = new Button("Hide");
        remove = new Button("Remove");
        coordinates = new Button("Coordinates");
        ny = new Button("New");
        ny.setOnAction(new NyHandler());
        remove.setOnAction(e -> removeEvent());
        search.setOnAction(new SearchHandler());

        list.getSelectionModel().selectedItemProperty().addListener(new ShowHandler());
        coordinates.setOnAction(new SearchCoordinateHandler());
        hide.setOnAction(new HideMarker());
        //radio knappar
        val = new ToggleGroup();
        named = new RadioButton("Named");
        descrip = new RadioButton("Described");
        named.setSelected(true);
        val.getToggles().addAll(named, descrip);
        //Lägger in in den i vbox
        radval = new VBox(10);
        radval.getChildren().addAll(named, descrip);
        //Uppre delen i en hbox
        uppredel = new HBox();
        uppredel.setPadding(new Insets(10, 10, 10, 10));
        uppredel.setAlignment(Pos.CENTER);
        uppredel.setSpacing(10);
        uppredel.getChildren().addAll(ny, radval, searchfield, search, hide, remove, coordinates);
        return uppredel;
    }

    class ExitHandler implements EventHandler<WindowEvent> {


        @Override
        public void handle(WindowEvent windowEvent) {
            if(byCateg.isEmpty() && byName.isEmpty() && positionlocater.isEmpty()){//Om man har skapat något men har tagit bort det och datan är tomt
                setChanged(false);//sett false om alla data är tomma så den inte reagerar på en tom data som inte har något att spara
            }//Koden är skrivet på ett sätt att om ett ställe tar bort något tar alla bort den liknade objektet från sig också
            if (changed) {//om det har skett någon förändring och någon av datastrukturen har något
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved Places, still want to exist?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.CANCEL)
                    windowEvent.consume(); //Tar bort händelse, så den inte avlustar
            }


        }
    }// Exist handler


    class ExitClicked implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST)); //Fire at will to ExitHandler för att kolla om något har förändrats
        }
    }



    class LoadHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            //La inte till den för har mina bilder sparade överrallt, men kodens finns med om man vill lägga till den, två olika varianter om det svenska eller engelska
            //fileChooser.setInitialDirectory(new File ("C:/Bilder"));
            // fileChooser.setInitialDirectory(new File ("C:/Images"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images", "*.bmp", "*.jpg", "*.gif", "*.png"),
                    new FileChooser.ExtensionFilter("All files", "*.*")
            );
            File file = fileChooser.showOpenDialog(window);
            //Om den inte hittar någon bild
            if (file == null)
                return;
            //path way for file to stream
            String filname = file.getAbsolutePath();
            image = new Image("File:" + filname);
            imageView.setImage(image);
            //Resize window så allt syns
            window.sizeToScene();


        }//LoadHandler ActionEvent event
    }//Loadhandler

    class NyHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            insertImage.setOnMouseClicked(new NyCommute());
            insertImage.setCursor(Cursor.CROSSHAIR);
        }//Ändra om musen till kors när man klickar på new
    }//NyHandler


    private void onPlacesClicked(MouseEvent event, Place place) {
        if (event.getButton() == MouseButton.PRIMARY) {// om man klickar på vänstra musklick
            place.setVisible();
            if (place.getClicked()) { //När man klickar på den ska den spara det i marked datastrukturen
                marked.add(place);
            } else { //Ta bort markerad vi klickning från gul till dens bas färg
                marked.remove(place);
            }
        }
        if (event.getButton() == MouseButton.SECONDARY) {//Om man klickar på högra musklick
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, place.getInfo());
            alert.setTitle("Info");
            alert.setHeaderText(null);
            alert.showAndWait();
        }//Secondary clicked
    }//handle Mousevent




    private void removeEvent() {
        for (Place p : marked) {
            //hämta in om den finns i varje samling
            Set<Place> sammaNamn = byName.get(p.getName());
            Set<Place> sammaCat = byCateg.get(p.getcommuteType());
            //fixa till att den också hämtar in för position locater
            Position posit = new Position(p.getCoordianteX(), p.getCoordinateY());
            //Ta bort - nedre koden
            insertImage.getChildren().remove(p);
            sammaCat.remove(p);
            sammaNamn.remove(p);
            positionlocater.remove(posit);
            if(sammaNamn.isEmpty() && sammaCat.isEmpty()){
                marked.remove(p);
                byCateg.remove(p.getcommuteType());
                byName.remove(p.getName());
            } //Ta bort objekt från makred så den inte har saker som inte finns på kartan

        }


    }//remove handler
    class HideMarker implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            for (Place p : marked) {
                p.setVisibleSpecific(false); //Lägger den false för den ska inte vara markerad
                p.hideTriangle(); //Gömmer triangel

            }
            marked.removeIf(place -> !place.getClicked()); //Tar bort de som är gömda, skriven på det sättet så att den inte for concurrent
        }
    }//Hide markerd


    class SearchHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String name = searchfield.getText();//Hämtar stringen från searchfield
            Set<Place> sammaNamn = byName.get(name);//Check om namnet finns i en samling
            if (sammaNamn == null || name.isEmpty()) {
                errorDialog("finns ej ", "fel");
            } else {//Klicka om de/den finns
                for (Place place : sammaNamn) {
                    place.setVisible();
                    marked.add(place);
                }
            }
        }
    }//Search handler

    class
    SearchCoordinateHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            AlertPosition dialog = new AlertPosition();
            dialog.setTitle("Coordiante");
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int x = dialog.getXAlert();
                    int y = dialog.getYAlert();
                    Position posi = new Position(x, y); //skapar en objekt för jag vet inte hur man fixar det på ett bättre sätt
                    boolean p = positionlocater.containsKey(posi); //return true or false
                    if (!p) {
                        errorDialog("finns ej ", "fel");
                    } else { //if true hämta value och markera den
                        Place pp = positionlocater.get(posi);
                        pp.setVisible();
                        marked.add(pp);
                    }
                } catch (NumberFormatException e) {
                    errorDialog("Ange siffror", "fel");

                }
            }
        }
    }//search coordinates

    class HideHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            if (list.getSelectionModel().getSelectedItem() != null) { //Den reagerar bara på olika kategorierna som finns i listview, NONE har ingen hide funktion från Listview

                valditem = list.getSelectionModel().getSelectedItem().getTypeName();
                Set<Place> sammaTyp = byCateg.get(valditem);
                for (Place place : sammaTyp) {
                    place.hideTriangle();
                }
                list.getSelectionModel().clearSelection();

            }

        }
    }// hide handler

    class ShowHandler implements ChangeListener<Category> {


        @Override
        public void changed(ObservableValue<? extends Category> observableValue, Category category, Category t1) {
            if(t1 == null || category == null){ //om den är tom reagera inte alls så den inte får nullPointException
                return;
            }else {//Visa tillbaka de som är gömda

                valditem = t1.getTypeName();
                Set<Place> sammaTyp = byCateg.get(valditem);
                for (Place place : sammaTyp) {
                    place.triangleColour();
                }

            }
        }
    }//Listner for listview = list


    class SavePlaceHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            try {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showSaveDialog(window);
                if (file == null)
                    return;
                String fileName = file.getAbsolutePath();
                FileWriter outfile = new FileWriter(fileName);
                PrintWriter out = new PrintWriter(outfile);
                for (Place p : positionlocater.values()) { //Vlade postionlocator för den var lättast att hämta datan
                    if (p.getBelong().equals("Named")) {
                        out.println(p.getBelong() + "," + p.getcommuteType() + "," + p.getCoordianteX() + "," + p.getCoordinateY() + "," + p.getName());
                    } else if (p.getBelong().equals("Described")) {
                        out.println(p.getBelong() + "," + p.getcommuteType() + "," + p.getCoordianteX() + "," + p.getCoordinateY() + "," + p.getName() + "," + p.getDescrip());
                    } //Sparar allt i olika sätt såsom uppgiften ville
                }
                out.close();
                outfile.close();//Stänger alla saker som behöver stängas
                setChanged(false); //Lägger det false så att den inte reagerar på close
            } catch (FileNotFoundException e) {
                errorDialog("Could not find file", "Error");

            } catch (IOException e) {
                errorDialog("Error with IO ", "Error");
            }
        }
    }//Save places



    class LoadPlacesHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            try {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(window);
                if (file == null)
                    return;
                String fileName = file.getAbsolutePath();
                FileReader infile = new FileReader(fileName);
                BufferedReader in = new BufferedReader(infile);
                String line;
                while ((line = in.readLine()) != null) {
                    String[] tokens = line.split(",");
                    String belong = tokens[0];
                    valditem = tokens[1];
                    double x = Double.parseDouble(tokens[2]);
                    double y = Double.parseDouble(tokens[3]);
                    String named = tokens[4];
                    if (belong.equals("Named")) {
                        NamedPlace place = new NamedPlace(valditem, x, y, named);
                        add(x, y, place);
                    } else if (belong.equals("Described")) {
                        String desrib = tokens[5];
                        DescribedPlace place = new DescribedPlace(valditem, x, y, named, desrib);
                        add(x, y, place);
                    }
                }
                in.close();
                infile.close();
                setChanged(false);
            } catch (FileNotFoundException e) {
                errorDialog("Could not find file", "fel");
            } catch (IOException e) {
                errorDialog("Error with IO ", "fel");
            }
        }
    } //Load Places

    class NyCommute implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent mouseEvent) {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (named.isSelected()) {
                    AlertNamedPlace dialog = new AlertNamedPlace();
                    Optional<ButtonType> result = dialog.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        String named = dialog.getInputName();
                        if (named.isEmpty()) {
                            errorDialog("Must give a name", "Error");
                        } else {
                            if (list.getSelectionModel().getSelectedItem() != null) {
                                valditem = list.getSelectionModel().getSelectedItem().getTypeName();
                            } else {
                                valditem = "None";
                            }
                            //kanske lägga högre upp i koden, orkar inte göra det nu
                            NamedPlace place = new NamedPlace(valditem, x, y, named);
                            add(x, y, place);
                        }//else none
                    }//Else för alert named
                }
            }// if named is selected//
            if (descrip.isSelected()) {
                AlertDescribedPlace dialog = new AlertDescribedPlace();
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    String named = dialog.getInputName();
                    String desrib = dialog.getInputDesrib();
                    if (named.isEmpty() || desrib.isEmpty()) {
                        errorDialog("Must give both name and desription", "Error");
                    } else {
                        if (list.getSelectionModel().getSelectedItem() != null) {

                            valditem = list.getSelectionModel().getSelectedItem().getTypeName();
                        } else {
                            valditem = "None";
                        }
                        DescribedPlace place = new DescribedPlace(valditem, x, y, named, desrib);
                        add(x, y, place);

                    }
                }//Else för alert boxen
            }//if descrip clicked
            setDefault(); //Noll ställer musen till default lägae
        }//Spawn handle
    }//NyCommute handle

    private void setDefault(){
        insertImage.setOnMouseClicked(null);
        insertImage.setCursor(Cursor.DEFAULT);
        list.getSelectionModel().clearSelection();// om man har klickar på list view så skan den nollställas
    }

    private void errorDialog(String s, String title) { ///Tar in vad dialog rutan ska innehålla med s och title vad setTitle ska vara
        Alert alert = new Alert(Alert.AlertType.ERROR, s);
        alert.setTitle(title);
        alert.setHeaderText(null);//Tar bort den fula övre rutan
        alert.showAndWait();
    }


    private void add(double x, double y, Place place) {
        Position pos = new Position((int) x, (int) y);
        if (positionlocater.containsKey(pos)){
            errorDialog("Can only have one in one location", "Error");

        }else {
            positionlocater.put(pos, place);


            Set<Place> sammaNamn = byName.get(place.getName());
            if (sammaNamn == null) {
                sammaNamn = new HashSet<>();
                byName.put(place.getName(), sammaNamn);
            }
            sammaNamn.add(place);

            Set<Place> sammaTyp = byCateg.get(place.getcommuteType());
            if (sammaTyp == null) { //Om samma typ har inte sparats in i samlingen innan skapa en ny
                sammaTyp = new HashSet<>();
                byCateg.put(place.getcommuteType(), sammaTyp);
            }
            sammaTyp.add(place);//Om det finns spara direkt

            insertImage.getChildren().add(place);
            place.setOnMouseClicked((MouseEvent event) -> onPlacesClicked(event, place));
            setChanged(true);
        }

    }

    private void setChanged(boolean b) {
        changed = b;
    }






}//Main
