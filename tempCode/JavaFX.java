import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.MAX_VALUE;

public class JavaFX extends Application {

    private Stage stage;
    private Scene mainPage;
    private Scene detailPage;
    private Text userText;
    private List<Post> posts = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    private void fetchPosts() {
        posts.clear();

        RedditClone redditClone = new RedditClone();
        User user1 = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");

        posts.add(redditClone.createPost("Test Post", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor ..."));
        posts.add(redditClone.createPost("Indulgence Confinde", "May indulgence difficulty ham can put especially. Bringing remember for supplied her why was confined. ..."));
        posts.add(redditClone.createPost("Elderly rest", "Or kind rest bred with am shed then. In raptures building an bringing be. Elderly is detract tedious assured private so to visited. ..."));
        posts.add(redditClone.createPost("My Friend no full", "Full he none no side. Uncommonly surrounded considered for him are its. It we is read good soon. My to considered delightful invitation ..."));

        redditClone.logout();
    }

    private VBox createPostVisual(Post post) {
        VBox appContent = new VBox();
        appContent.setPadding(new Insets(20));
        appContent.setSpacing(7);

        Text title = new Text(post.getTitle());
        appContent.getChildren().add(title);
        String username = post.getAuthor().getUsername();
        Text author = new Text(username);
        appContent.getChildren().add(author);
        Text content = new Text(post.getContent());
        content.setWrappingWidth(350);

        VBox.setMargin(content, new Insets(0, 0, 0, 0));
        appContent.getChildren().add(content);

        appContent.setStyle("-fx-background-color: white");

        return appContent;
    }

    private VBox createKarmaContainer(Post currentPost) {
        VBox containerVote = new VBox();
        containerVote.setSpacing(7);
        containerVote.setPadding(new Insets(15));
        Button upvote = new Button("+");
        upvote.setStyle("-fx-text-fill: black;" +
                "    -fx-background-color: white;" +
                "    -fx-border-radius: 50;" +
                "    -fx-border-color: black;" +
                "    -fx-background-radius: 2;" +
                "    -fx-paddingy: 10;");

        Button downvote = new Button("-");
        downvote.setStyle("-fx-text-fill: black;" +
                "    -fx-background-color: white;" +
                "    -fx-border-radius: 50;" +
                "    -fx-border-color: black;" +
                "    -fx-background-radius: 2;" +
                "    -fx-paddingy: 10;");
        downvote.setMaxWidth(MAX_VALUE);
        Text karma = new Text(String.valueOf(currentPost.getAuthor().getKarma()));
        karma.setTextAlignment(TextAlignment.CENTER);
        karma.setStyle("-fx-text-color: white");

        containerVote.setAlignment(Pos.TOP_CENTER);
        containerVote.setPadding(new Insets(35, 0, 0, 15));
        containerVote.setStyle("-fx-background-color: white");

        containerVote.getChildren().addAll(upvote, karma, downvote);
        return containerVote;
    }

    public void switchScenes(Scene changeToScene) {
        this.stage.setScene(changeToScene);
    }

    private Scene createSceneHome() {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        fetchPosts(); // Fetch posts dynamically

        HBox menuContainer = new HBox();
        Menu subscribed = new Menu("Subscribed");
        MenuItem subreddit1 = new MenuItem("Subreddit 1");
        MenuItem subreddit2 = new MenuItem("Subreddit 2");
        MenuItem subreddit3 = new MenuItem("Subreddit 3");
        subscribed.getItems().addAll(subreddit1, subreddit2, subreddit3);
        menuContainer.setPadding(new Insets(0, 0, 0, 0));

        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(subscribed);

        Text redditText = new Text();
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);

        this.userText = new Text();
        RedditClone redditClone = new RedditClone();
        User auser = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");

        userText.setText(auser.getUsername());

        TextField search = new TextField();
        toolBar.getItems().addAll(redditText, menuBarVar, search, userText);
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 30px");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < posts.size(); i++) {
            Post currentPost = posts.get(i);
            VBox visualPost = createPostVisual(currentPost);
            VBox containerVote = createKarmaContainer(currentPost);

            gridPane.add(containerVote, 0, i);
            gridPane.add(visualPost, 1, i);

            visualPost.setOnMouseClicked(event -> {
                System.out.println("Entering Post");
                detailPage = createSceneDetailed(currentPost);
                switchScenes(detailPage);
                event.consume();
            });
        }

        layout.setCenter(gridPane);
        layout.setTop(toolBar);
        gridPane.setStyle("-fx-background-color: Gray");

        return new Scene(layout, 600, 500);
    }

    private Scene createSceneDetailed(Post currentPost) {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        HBox menuContainer = new HBox();
        Menu subscribed = new Menu("Subscribed");
        MenuItem subreddit1 = new MenuItem("Subreddit 1");
        MenuItem subreddit2 = new MenuItem("Subreddit 2");
        MenuItem subreddit3 = new MenuItem("Subreddit 3");
        subscribed.getItems().addAll(subreddit1, subreddit2, subreddit3);
        menuContainer.setPadding(new Insets(0, 0, 0, 0));

        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(subscribed);

        Text redditText = new Text();
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);

        TextField search = new TextField();
        toolBar.getItems().addAll(redditText, menuBarVar, search, userText);
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 30px");

        VBox appContent = new VBox();
        appContent.setPadding(new Insets(20));
        appContent.setSpacing(7);

        Text title = new Text(currentPost.getTitle());
        appContent.getChildren().add(title);
        String username = currentPost.getAuthor().getUsername();
        Text author = new Text(username);
        appContent.getChildren().add(author);
        Text content = new Text(currentPost.getContent());
        content.setWrappingWidth(350);

        VBox.setMargin(content, new Insets(0, 0, 0, 0));
        appContent.getChildren().add(content);

        appContent.setStyle("-fx-background-color: white");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.add(appContent, 1, 0);
        gridPane.setAlignment(Pos.TOP_CENTER);

        layout.setCenter(gridPane);
        layout.setTop(toolBar);

        VBox containerVote = createKarmaContainer(currentPost);
        gridPane.add(containerVote, 0, 0);

        HBox containerInteraction = new HBox();
        Button sortTime = new Button("Sort: Date");
        Button sortUser = new Button("Sort: Karma");
        Button commentBtn = new Button("Comment");
        TextField commentBox = new TextField();
        containerInteraction.getChildren().addAll(sortTime, sortUser, commentBox, commentBtn);
        gridPane.add(containerInteraction, 1, 1);

        Button button2 = new Button("Reddit");

        redditText.setOnMouseClicked(event -> {
            System.out.println("Back to Main page ");
            switchScenes(mainPage);
            event.consume();
        });

        gridPane.setStyle("-fx-background-color: Gray");
        detailPage = new Scene(layout, 600, 500);
        return detailPage;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        mainPage = createSceneHome();
        stage.setScene(mainPage);
        stage.setTitle("Reddit Clone");
        stage.show();
    }
}
