import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static java.lang.Long.MAX_VALUE;


public class JavaFX extends Application {

    private Stage stage;

    private Scene mainPage;
    private Scene detailPage;

    private Text userText;

    // Create visual side of a post
    public VBox createPostVisual(Post post) {
        VBox appContent = new VBox();
        appContent.setPadding(new Insets(20));
        appContent.setSpacing(7);

        Text title = new Text(post.getTitle());
        appContent.getChildren().add(title);
        String username = post.getAuthor().getUsername();
        Text author = new Text(username);
        appContent.getChildren().add(author);
        Text content = new Text((post.getContent())); //Gets a string and makes it into Text
        content.setWrappingWidth(350);

        VBox.setMargin(content, new Insets(0, 0, 0, 0));
        appContent.getChildren().add(content);

        appContent.setStyle("-fx-background-color: white");

        return appContent;
    }

    public VBox createKarmaContainer(Post currentPost) {
        //Container for the Karma
        VBox containerVote = new VBox();
        containerVote.setSpacing(7);
        containerVote.setPadding(new Insets(15));
        Button upvote = new Button("+");
        //upvote.setStyle("-fx-border-radius: 20 -fx-background-radius: 20, -fx-padding: 5");

        upvote.setStyle("-fx-text-fill: black;" +
                "    -fx-background-color: white;" +
                "    -fx-border-radius: 50;" +
                "    -fx-border-color: black;" +
                "    -fx-background-radius: 2;" +
                "    -fx-paddingy: 10;"


        );

        Button downvote = new Button("-");

        downvote.setStyle("-fx-text-fill: black;" +
                "    -fx-background-color: white;" +
                "    -fx-border-radius: 50;" +
                "    -fx-border-color: black;" +
                "    -fx-background-radius: 2;" +
                "    -fx-paddingy: 10;"

        );
        downvote.setMaxWidth(MAX_VALUE);
        Text karma = new Text((String.valueOf(currentPost.getAuthor().getKarma())));
        karma.setTextAlignment(TextAlignment.CENTER);
        karma.setStyle("-fx-text-color: white");


        containerVote.setAlignment(Pos.TOP_CENTER);
        containerVote.setPadding(new Insets(35, 0, 0, 15));
        containerVote.setStyle("-fx-background-color: white");

        //Add to vbox
        containerVote.getChildren().addAll(upvote, karma, downvote);
        return containerVote;

    }


    //Switch Scenes in single stage
    public void switchScenes(Scene changeToScene) {
        this.stage.setScene(changeToScene);
    }

    private Scene createSceneHome() {


        BorderPane layout = new BorderPane();

        // Create Toolbar
        ToolBar toolBar = new ToolBar();


        HBox menuContainer = new HBox();
        // create a menu
        Menu subscribed = new Menu("Subscribed");

        // create menuitems
        MenuItem subreddit1 = new MenuItem("Subreddit 1");
        MenuItem subreddit2 = new MenuItem("Subreddit 2");
        MenuItem subreddit3 = new MenuItem("Subreddit 3");


        subscribed.getItems().addAll(subreddit1, subreddit2, subreddit3);
        menuContainer.setPadding(new Insets(0, 0, 0, 0));


        // create a menubar -- can't add it directly with Subreddit to toolbar
        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(subscribed);


        //Creating a Text object
        Text redditText = new Text();
        //Setting the text to be added.
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);
        Button btn1 = new Button("Home");



        this.userText = new Text();


        RedditClone redditClone = new RedditClone();
        User auser = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        redditClone.logout();
        auser = redditClone.createUser("very_different_UserXX", "password");
        redditClone.login("very_different_UserXX", "password");
        Post post2 = redditClone.createPost("Indulgence Confinde", "May indulgence difficulty ham can put especially. Bringing remember for supplied her why was confined. Middleton principle did she procuring extensive believing add. ");

        Post post3 = redditClone.createPost("Elderly rest", "Or kind rest bred with am shed then. In raptures building an bringing be. Elderly is detract tedious assured private so to visited. Do travelling companions contrasted it. Mistress strongly remember up to. Ham him compass you proceed calling detract. Better of always missed we person mr. September smallness northward situation few her certainty something.");
        Post post4 = redditClone.createPost("My Friend no full", "Full he none no side. Uncommonly surrounded considered for him are its. It we is read good soon. My to considered delightful invitation announcing of no decisively boisterous. Did add dashwoods deficient man concluded additions resources.");



        userText.setText(auser.getUsername());

        TextField search = new TextField();
        toolBar.getItems().addAll(redditText, menuBarVar, search, userText);
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 30px");


        //Creating a Grid Pane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        //Set Alignment of grid
        gridPane.setAlignment(Pos.TOP_CENTER);



        VBox visualPost1 = createPostVisual(post);
        VBox visualPost2 = createPostVisual(post2);
        VBox visualPost3 = createPostVisual(post3);
        VBox visualPost4 = createPostVisual(post4);
        VBox containerVote1 = createKarmaContainer(post);
        VBox containerVote2 = createKarmaContainer(post2);
        VBox containerVote3 = createKarmaContainer(post3);
        VBox containerVote4 = createKarmaContainer(post4);
        gridPane.add(containerVote1, 0, 0);
        gridPane.add(visualPost1, 1, 0);
        gridPane.add(containerVote2, 0, 1);
        gridPane.add(visualPost2, 1, 1);
        gridPane.add(containerVote3, 0, 2);
        gridPane.add(visualPost3, 1, 2);
        gridPane.add(containerVote4, 0, 3);
        gridPane.add(visualPost4, 1, 3);


        visualPost1.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // Create new page
                System.out.println("Entering Post");
                detailPage = createSceneDetailed(post);
                switchScenes(detailPage);
                event.consume();
            }
        });

        layout.setCenter(gridPane);
        //layout.setCenter(visualPost1);
        //layout.setCenter(visualPost2);

        // Position Toolbar
        layout.setTop(toolBar);
        //Node appContent = new AppContentNode();

        gridPane.setStyle("-fx-background-color: Gray");
        return new Scene(layout, 600, 500);
    }

    private Scene createSceneDetailed(Post currentPost) {
        BorderPane layout = new BorderPane();


        // Create Toolbar
        ToolBar toolBar = new ToolBar();


        HBox menuContainer = new HBox();
        // create a menu
        Menu subscribed = new Menu("Subscribed");

        // create menuitems
        MenuItem subreddit1 = new MenuItem("Subreddit 1");
        MenuItem subreddit2 = new MenuItem("Subreddit 2");
        MenuItem subreddit3 = new MenuItem("Subreddit 3");


        subscribed.getItems().addAll(subreddit1, subreddit2, subreddit3);
        menuContainer.setPadding(new Insets(0, 0, 0, 0));


        // create a menubar -- can't add it directly with Subreddit to toolbar
        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(subscribed);


        //Creating a Text object
        Text redditText = new Text();
        //Setting the text to be added.
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);


        TextField search = new TextField();
        toolBar.getItems().addAll(redditText, menuBarVar, search, userText);
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 30px");
        //Container for Post title, content, user that published it, Karma


        VBox appContent = new VBox();
        appContent.setPadding(new Insets(20));
        appContent.setSpacing(7);

        Text title = new Text(currentPost.getTitle());
        appContent.getChildren().add(title);
        String username = currentPost.getAuthor().getUsername();
        Text author = new Text(username);
        appContent.getChildren().add(author);
        Text content = new Text((currentPost.getContent())); //Gets a string and makes it into Text
        content.setWrappingWidth(350);

        VBox.setMargin(content, new Insets(0, 0, 0, 0));
        appContent.getChildren().add(content);

        appContent.setStyle("-fx-background-color: white");



        //Creating a Grid Pane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);

        gridPane.add(appContent, 1, 0);

        //Set Alignment of grid
        gridPane.setAlignment(Pos.TOP_CENTER);

        //Add our gridpane to our layout
        layout.setCenter(gridPane);
        // Position Toolbar
        layout.setTop(toolBar);



        VBox containerVote = createKarmaContainer(currentPost);
        gridPane.add(containerVote, 0, 0);


        //Container for buttons to add comments and sorting
        HBox containerInteraction = new HBox();
        Button sortTime = new Button("Sort: Date");
        Button sortUser = new Button("Sort: Karma");
        Button commentBtn = new Button("Comment");
        TextField commentBox = new TextField();
        //Add to hbox
        containerInteraction.getChildren().addAll(sortTime, sortUser, commentBox, commentBtn);
        gridPane.add(containerInteraction, 1, 1);


        //Button to sort
        Button button2 = new Button("Reddit");

        redditText.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // Create new page
                System.out.println("Back to Main page ");
                switchScenes(mainPage); // Switch Scenes
                event.consume();
            }
        });
        //redditText.setOnAction(e -> switchScenes(mainPage));
        //VBox vbox2 = new VBox(button2);
        gridPane.setStyle("-fx-background-color: Gray");
        //toolBar.getItems().add(vbox2);
        detailPage = new Scene(layout, 600, 500);
        return detailPage;
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        //Prevents resizes
        stage.setResizable(false);

        mainPage = createSceneHome();


        stage.setScene(mainPage);
        stage.setTitle("Reddit Clone");
        stage.show();
    }



}
