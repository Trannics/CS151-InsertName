package com.example.cs151finalcode;

import com.example.cs151finalcode.Post;
import com.example.cs151finalcode.Comment;
import com.example.cs151finalcode.Login;
import com.example.cs151finalcode.User;
import com.example.cs151finalcode.Vote;
import com.example.cs151finalcode.RedditClone;
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
    private Scene detailPage;
    private Text userText;
    private RedditClone redditClone;

    public static void main(String[] args) {
        launch(args);
    }

    private Scene createSceneLogin() {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        Text redditText = new Text();
        redditText.setText("RedditClone");
        toolBar.getItems().addAll(redditText);
        toolBar.setStyle("-fx-spacing: 30px");

        //Create Gridpane for gray area / main body
        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);


        //Create the two text fields
        Text username = new Text("Username:");
        TextField enterUsername = new TextField();
        Text password = new Text("Password:");
        TextField enterPassword = new TextField();

        //Create a container for buttons
        HBox loginBtnsContainer = new HBox();

        //Create two buttons - login and make account
        Button loginBtn = new Button("Login");

        Button newUser = new Button("Create Account");

        loginBtnsContainer.getChildren().addAll(loginBtn, newUser);

        loginBtnsContainer.setSpacing(15);

        Text welcomeText = new Text("Welcome to RedditClone");

        gridPane.add(welcomeText, 0, 0);
        gridPane.add(username, 0, 1);
        gridPane.add(enterUsername, 0, 2);
        gridPane.add(password, 0, 3);
        gridPane.add(enterPassword, 0, 4);
        gridPane.add(loginBtnsContainer, 0, 5);

        //Place our gridpane in our scene
        layout.setCenter(gridPane);
        layout.setTop(toolBar);
        gridPane.setStyle("-fx-background-color: Gray");

        // If user clicks on RedditClone - go to main page
        redditText.setOnMouseClicked(event -> {
            stage.setScene(createSceneHome());
        });

        loginBtn.setOnMouseClicked(event -> {
            if (redditClone.login(enterUsername.getText(), enterPassword.getText()) != null) {
                stage.setScene(createSceneHome());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("No existing user found. Please try again.");
                a.show();
            }
        });

        newUser.setOnMouseClicked(event -> {
            if (redditClone.login(enterUsername.getText(), enterPassword.getText()) == null) {
                if (!redditClone.checksUsername(enterUsername.getText())) {
                    redditClone.createUser(enterUsername.getText(), enterPassword.getText());
                    stage.setScene(createSceneHome());
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Username has been taken. Try another one.");
                    a.show();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Existing user found. Login instead.");
                a.show();
            }
        });

        return new Scene(layout, 600, 500);
    }

    private Scene createScenePostCreation() {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        Text redditText = new Text();
        redditText.setText("RedditClone");

        this.userText = new Text();
        if(redditClone.getLoggedInUser() != null) {
            userText.setText("Hi, " + redditClone.getLoggedInUser().getUsername());
        }

        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.getItems().addAll(redditText, userText);

        toolBar.setStyle("-fx-spacing: 25px");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(7);
        //Container for items relating to the title of a new post
        VBox containerTitle = new VBox();
        Text titleText = new Text("Post Title");
        TextField titleBox = new TextField();
        containerTitle.getChildren().addAll(titleText, titleBox);
        containerTitle.setSpacing(5);
        containerTitle.setPadding(new Insets(50, 0, 20, 0));

        //Container for items relating to the content / body of a new post
        VBox containerContent = new VBox();
        Text contentText = new Text("Post Content");
        TextArea contentBox = new TextArea();
        containerContent.getChildren().addAll(contentText, contentBox);
        containerContent.setSpacing(5);
        containerContent.setPadding(new Insets(0, 0, 20, 0));


        //container for  the buttons
        HBox containerButtons = new HBox();
        Button cancelPostCreation = new Button("Cancel");
        Button createPost = new Button("Create Post");
        containerButtons.getChildren().addAll(cancelPostCreation, createPost);
        containerButtons.setSpacing(15);
        containerButtons.setPadding(new Insets(0, 0, 20, 0));
        containerButtons.setAlignment(Pos.CENTER_RIGHT);


        //Add containers to gridpane
        gridPane.add(containerTitle, 0, 0);
        gridPane.add(containerContent, 0, 1);
        gridPane.add(containerButtons, 0, 2);
        gridPane.setAlignment(Pos.TOP_CENTER);

        layout.setCenter(gridPane);
        layout.setTop(toolBar);
        gridPane.setStyle("-fx-background-color: Gray");

        cancelPostCreation.setOnMouseClicked(event -> {
            stage.setScene(createSceneHome());
        });

        createPost.setOnMouseClicked(event -> {
            if (!titleBox.getText().isEmpty() && !contentBox.getText().isEmpty()) {
                stage.setScene(createSceneDetailed(redditClone.createPost(titleBox.getText(), contentBox.getText())));
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please put a title and/or write out your content for your post!");
                a.show();
            }
        });

        return new Scene(layout, 600, 500);
    }

    private Scene createSceneHome() {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        HBox menuContainer = new HBox();
        Menu postList = new Menu("All Posts");
        for(int i = 0; i < redditClone.getPostsByDate().size(); i++) {
            Post post = redditClone.getPostsByDate().get(i);
            MenuItem redditPost = new MenuItem(post.getTitle().substring(0, Math.min(10, post.getTitle().length())) + "...");
            postList.getItems().addAll(redditPost);

            redditPost.setOnAction(event -> {
                stage.setScene(createSceneDetailed(post));
            });
        }
        menuContainer.setPadding(new Insets(0, 0, 0, 0));

        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(postList);

        Text redditText = new Text();
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);

        this.userText = new Text();
        if(redditClone.getLoggedInUser() != null) {
            userText.setText("Hi, " + redditClone.getLoggedInUser().getUsername());
        } else {
            userText.setText("Guest");
        }

        Button createPost = new Button("Create Post");
        Button viewUsers = new Button("View All Users");
        Button sortTime = new Button("Sort: Date");
        Button sortUser = new Button("Sort: Karma");
        Button login = new Button("Login");
        Button logoff = new Button("Log off");

        toolBar.getItems().addAll(redditText, menuBarVar, userText, createPost, viewUsers, sortTime, sortUser);
        if (redditClone.getLoggedInUser() != null) {
            toolBar.getItems().add(logoff);
        } else {
            toolBar.getItems().add(login);
        }
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 40px");

        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < redditClone.getPostsByDate().size(); i++) {
            Post currentPost = redditClone.getPostsByDate().get(i);
            VBox visualPost = createPostVisual(currentPost);
            VBox containerVote = createPostKarmaContainer(currentPost);

            gridPane.add(containerVote, 0, i);
            gridPane.add(visualPost, 1, i);

            visualPost.setOnMouseClicked(event -> {
                detailPage = createSceneDetailed(currentPost);
                switchScenes(detailPage);
                event.consume();
            });
        }

        createPost.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                stage.setScene(createScenePostCreation());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });

        login.setOnMouseClicked(event -> {
            stage.setScene(createSceneLogin());
        });

        logoff.setOnMouseClicked(event -> {
            redditClone.logout();
            stage.setScene(createSceneHome());
        });

        sortUser.setOnMouseClicked(event -> {
            stage.setScene(createSceneHomeByKarma());
        });

        viewUsers.setOnMouseClicked(event -> {
            stage.setScene(createSceneUsers());
        });

        scrollPane.setContent(gridPane);
        layout.setCenter(scrollPane);
        layout.setTop(toolBar);
        gridPane.setStyle("-fx-background-color: Gray");

        return new Scene(layout, 600, 500);
    }

    private Scene createSceneHomeByKarma() {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        HBox menuContainer = new HBox();
        Menu postList = new Menu("All Posts");
        for(int i = 0; i < redditClone.getPostsByDate().size(); i++) {
            Post post = redditClone.getPostsByDate().get(i);
            MenuItem redditPost = new MenuItem(post.getTitle().substring(0, Math.min(10, post.getTitle().length())) + "...");
            postList.getItems().addAll(redditPost);

            redditPost.setOnAction(event -> {
                stage.setScene(createSceneDetailed(post));
            });
        }
        menuContainer.setPadding(new Insets(0, 0, 0, 0));

        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(postList);

        Text redditText = new Text();
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);

        this.userText = new Text();
        if(redditClone.getLoggedInUser() != null) {
            userText.setText("Hi, " + redditClone.getLoggedInUser().getUsername());
        } else {
            userText.setText("Guest");
        }

        Button createPost = new Button("Create Post");
        Button viewUsers = new Button("View All Users");
        Button sortTime = new Button("Sort: Date");
        Button sortUser = new Button("Sort: Karma");
        Button login = new Button("Login");
        Button logoff = new Button("Log off");

        toolBar.getItems().addAll(redditText, menuBarVar, userText, createPost, viewUsers, sortTime, sortUser);
        if (redditClone.getLoggedInUser() != null) {
            toolBar.getItems().add(logoff);
        } else {
            toolBar.getItems().add(login);
        }
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 40px");

        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < redditClone.getPostsByKarma().size(); i++) {
            Post currentPost = redditClone.getPostsByKarma().get(i);
            VBox visualPost = createPostVisual(currentPost);
            VBox containerVote = createPostKarmaContainer(currentPost);

            gridPane.add(containerVote, 0, i);
            gridPane.add(visualPost, 1, i);

            visualPost.setOnMouseClicked(event -> {
                detailPage = createSceneDetailed(currentPost);
                switchScenes(detailPage);
                event.consume();
            });
        }

        createPost.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                stage.setScene(createScenePostCreation());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });

        login.setOnMouseClicked(event -> {
            stage.setScene(createSceneLogin());
        });

        logoff.setOnMouseClicked(event -> {
            redditClone.logout();
            stage.setScene(createSceneHome());
        });

        sortTime.setOnMouseClicked(event -> {
            stage.setScene(createSceneHome());
        });

        viewUsers.setOnMouseClicked(event -> {
            stage.setScene(createSceneUsers());
        });

        scrollPane.setContent(gridPane);
        layout.setCenter(scrollPane);
        layout.setTop(toolBar);
        gridPane.setStyle("-fx-background-color: Gray");

        return new Scene(layout, 600, 500);
    }

    private Scene createSceneUsers() {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        HBox menuContainer = new HBox();
        Menu postList = new Menu("All Posts");
        for(int i = 0; i < redditClone.getPostsByDate().size(); i++) {
            Post post = redditClone.getPostsByDate().get(i);
            MenuItem redditPost = new MenuItem(post.getTitle().substring(0, Math.min(10, post.getTitle().length())) + "...");
            postList.getItems().addAll(redditPost);

            redditPost.setOnAction(event -> {
                stage.setScene(createSceneDetailed(post));
            });
        }
        menuContainer.setPadding(new Insets(0, 0, 0, 0));

        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(postList);

        Text redditText = new Text();
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);

        this.userText = new Text();
        if(redditClone.getLoggedInUser() != null) {
            userText.setText("Hi, " + redditClone.getLoggedInUser().getUsername());
        } else {
            userText.setText("Guest");
        }

        Button createPost = new Button("Create Post");
        Button sortTime = new Button("Sort: Date");
        Button sortUser = new Button("Sort: Karma");
        Button login = new Button("Login");
        Button logoff = new Button("Log off");

        toolBar.getItems().addAll(redditText, menuBarVar, userText, createPost, sortTime, sortUser);
        if (redditClone.getLoggedInUser() != null) {
            toolBar.getItems().add(logoff);
        } else {
            toolBar.getItems().add(login);
        }
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 20px");

        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < redditClone.getUsersByDate().size(); i++) {
            User currentUser = redditClone.getUsersByDate().get(i);
            VBox visualPost = createUserVisuals(currentUser);
            VBox containerVote = createUserKarmaContainer(currentUser);

            gridPane.add(containerVote, 0, i);
            gridPane.add(visualPost, 1, i);
        }

        createPost.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                stage.setScene(createScenePostCreation());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });

        login.setOnMouseClicked(event -> {
            stage.setScene(createSceneLogin());
        });

        logoff.setOnMouseClicked(event -> {
            redditClone.logout();
            stage.setScene(createSceneHome());
        });

        sortUser.setOnMouseClicked(event -> {
            stage.setScene(createSceneUsersByKarma());
        });

        scrollPane.setContent(gridPane);
        layout.setCenter(scrollPane);
        layout.setTop(toolBar);
        gridPane.setStyle("-fx-background-color: Gray");

        return new Scene(layout, 600, 500);
    }

    private Scene createSceneUsersByKarma() {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        HBox menuContainer = new HBox();
        Menu postList = new Menu("All Posts");
        for(int i = 0; i < redditClone.getPostsByDate().size(); i++) {
            Post post = redditClone.getPostsByDate().get(i);
            MenuItem redditPost = new MenuItem(post.getTitle().substring(0, Math.min(10, post.getTitle().length())) + "...");
            postList.getItems().addAll(redditPost);

            redditPost.setOnAction(event -> {
                stage.setScene(createSceneDetailed(post));
            });
        }
        menuContainer.setPadding(new Insets(0, 0, 0, 0));

        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(postList);

        Text redditText = new Text();
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);

        this.userText = new Text();
        if(redditClone.getLoggedInUser() != null) {
            userText.setText("Hi, " + redditClone.getLoggedInUser().getUsername());
        } else {
            userText.setText("Guest");
        }

        Button createPost = new Button("Create Post");
        Button sortTime = new Button("Sort: Date");
        Button sortUser = new Button("Sort: Karma");
        Button login = new Button("Login");
        Button logoff = new Button("Log off");

        toolBar.getItems().addAll(redditText, menuBarVar, userText, createPost, sortTime, sortUser);
        if (redditClone.getLoggedInUser() != null) {
            toolBar.getItems().add(logoff);
        } else {
            toolBar.getItems().add(login);
        }
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 20px");

        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < redditClone.getUsersByKarma().size(); i++) {
            User currentUser = redditClone.getUsersByKarma().get(i);
            VBox visualPost = createUserVisuals(currentUser);
            VBox containerVote = createUserKarmaContainer(currentUser);

            gridPane.add(containerVote, 0, i);
            gridPane.add(visualPost, 1, i);
        }

        createPost.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                stage.setScene(createScenePostCreation());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });

        login.setOnMouseClicked(event -> {
            stage.setScene(createSceneLogin());
        });

        logoff.setOnMouseClicked(event -> {
            redditClone.logout();
            stage.setScene(createSceneHome());
        });

        sortTime.setOnMouseClicked(event -> {
            stage.setScene(createSceneUsers());
        });

        scrollPane.setContent(gridPane);
        layout.setCenter(scrollPane);
        layout.setTop(toolBar);
        gridPane.setStyle("-fx-background-color: Gray");

        return new Scene(layout, 600, 500);
    }

    private Scene createSceneDetailed(Post currentPost) {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        HBox menuContainer = new HBox();
        Menu postList = new Menu("All Posts");
        for(int i = 0; i < redditClone.getPostsByDate().size(); i++) {
            Post post = redditClone.getPostsByDate().get(i);
            MenuItem redditPost = new MenuItem(post.getTitle().substring(0, Math.min(10, post.getTitle().length())) + "...");
            postList.getItems().addAll(redditPost);

            redditPost.setOnAction(event -> {
                stage.setScene(createSceneDetailed(post));
            });
        }
        menuContainer.setPadding(new Insets(0, 0, 0, 0));

        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(postList);

        Text redditText = new Text();
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);

        this.userText = new Text();
        if(redditClone.getLoggedInUser() != null) {
            userText.setText("Hi, " + redditClone.getLoggedInUser().getUsername());
        } else {
            userText.setText("Guest");
        }

        Button createPost = new Button("Create Post");

        Button login = new Button("Login");
        Button logoff = new Button("Log off");

        toolBar.getItems().addAll(redditText, menuBarVar, userText, createPost);
        if (redditClone.getLoggedInUser() != null) {
            toolBar.getItems().add(logoff);
        } else {
            toolBar.getItems().add(login);
        }
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 60px");

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

        VBox containerVote = createPostKarmaContainer(currentPost);
        gridPane.add(containerVote, 0, 0);

        HBox containerInteraction = new HBox();
        Button sortTime = new Button("Sort: Date");
        Button sortUser = new Button("Sort: Karma");
        Button commentBtn = new Button("Comment");
        TextField commentBox = new TextField();
        containerInteraction.getChildren().addAll(sortTime, sortUser, commentBox, commentBtn);
        gridPane.add(containerInteraction, 1, 1);

        ScrollPane scrollPane = new ScrollPane();
        GridPane commentGridPane = new GridPane();
        scrollPane.setPrefSize(120, 360);
        for (int i = 0; i < currentPost.getCommentsByDate().size(); i++) {
            Comment currentComment = currentPost.getCommentsByDate().get(i);
            VBox visualComment = createCommentVisual(currentComment);
            VBox commentContainerVote = createCommentKarmaContainer(currentPost, currentComment);

            commentGridPane.add(commentContainerVote, 0, i);
            commentGridPane.add(visualComment, 1, i);
        }
        scrollPane.setContent(commentGridPane);
        layout.setBottom(scrollPane);

        redditText.setOnMouseClicked(event -> {
            stage.setScene(createSceneHome());
        });

        commentBtn.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                if(!commentBox.getText().isEmpty()) {
                    redditClone.createComment(redditClone.getLoggedInUser(), commentBox.getText(), currentPost, null);
                    stage.setScene(createSceneDetailedCommentByKarma(currentPost));
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Please write in a comment.");
                    a.show();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });

        sortUser.setOnMouseClicked(event -> {
            stage.setScene(createSceneDetailedCommentByKarma(currentPost));
        });

        createPost.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                stage.setScene(createScenePostCreation());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });

        login.setOnMouseClicked(event -> {
            stage.setScene(createSceneLogin());
        });

        logoff.setOnMouseClicked(event -> {
            redditClone.logout();
            stage.setScene(createSceneHome());
        });

        gridPane.setStyle("-fx-background-color: Gray");
        detailPage = new Scene(layout, 600, 500);
        return detailPage;
    }

    private Scene createSceneDetailedCommentByKarma(Post currentPost) {
        BorderPane layout = new BorderPane();
        ToolBar toolBar = new ToolBar();

        HBox menuContainer = new HBox();
        Menu postList = new Menu("All Posts");
        for(int i = 0; i < redditClone.getPostsByDate().size(); i++) {
            Post post = redditClone.getPostsByDate().get(i);
            MenuItem redditPost = new MenuItem(post.getTitle().substring(0, Math.min(10, post.getTitle().length())) + "...");
            postList.getItems().addAll(redditPost);

            redditPost.setOnAction(event -> {
                stage.setScene(createSceneDetailed(post));
            });
        }
        menuContainer.setPadding(new Insets(0, 0, 0, 0));

        MenuBar menuBarVar = new MenuBar();
        menuBarVar.getMenus().add(postList);

        Text redditText = new Text();
        redditText.setText("RedditClone");
        menuContainer.setTranslateX(5);

        this.userText = new Text();
        if(redditClone.getLoggedInUser() != null) {
            userText.setText("Hi, " + redditClone.getLoggedInUser().getUsername());
        } else {
            userText.setText("Guest");
        }

        Button createPost = new Button("Create Post");

        Button login = new Button("Login");
        Button logoff = new Button("Log off");
        toolBar.getItems().addAll(redditText, menuBarVar, userText, createPost);
        if (redditClone.getLoggedInUser() != null) {
            toolBar.getItems().add(logoff);
        } else {
            toolBar.getItems().add(login);
        }
        userText.setTextAlignment(TextAlignment.RIGHT);
        toolBar.setStyle("-fx-spacing: 60px");

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

        VBox containerVote = createPostKarmaContainer(currentPost);
        gridPane.add(containerVote, 0, 0);

        HBox containerInteraction = new HBox();
        Button sortTime = new Button("Sort: Date");
        Button sortUser = new Button("Sort: Karma");
        Button commentBtn = new Button("Comment");
        TextField commentBox = new TextField();
        containerInteraction.getChildren().addAll(sortTime, sortUser, commentBox, commentBtn);
        gridPane.add(containerInteraction, 1, 1);

        ScrollPane scrollPane = new ScrollPane();
        GridPane commentGridPane = new GridPane();
        scrollPane.setPrefSize(120, 360);
        for (int i = 0; i < currentPost.getCommentsByDate().size(); i++) {
            Comment currentComment = currentPost.getCommentsByKarma().get(i);
            VBox visualComment = createCommentVisual(currentComment);
            VBox commentContainerVote = createCommentKarmaContainer(currentPost, currentComment);

            commentGridPane.add(commentContainerVote, 0, i);
            commentGridPane.add(visualComment, 1, i);
        }
        scrollPane.setContent(commentGridPane);
        layout.setBottom(scrollPane);

        redditText.setOnMouseClicked(event -> {
            stage.setScene(createSceneHome());
        });

        commentBtn.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                if(!commentBox.getText().isEmpty()) {
                    redditClone.createComment(redditClone.getLoggedInUser(), commentBox.getText(), currentPost, null);
                    stage.setScene(createSceneDetailedCommentByKarma(currentPost));
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Please write in a comment.");
                    a.show();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });

        sortTime.setOnMouseClicked(event -> {
            stage.setScene(createSceneDetailed(currentPost));
        });

        createPost.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                stage.setScene(createScenePostCreation());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });

        login.setOnMouseClicked(event -> {
            stage.setScene(createSceneLogin());
        });

        logoff.setOnMouseClicked(event -> {
            redditClone.logout();
            stage.setScene(createSceneHome());
        });

        gridPane.setStyle("-fx-background-color: Gray");
        detailPage = new Scene(layout, 600, 500);
        return detailPage;
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

    private VBox createCommentVisual(Comment comment) {
        VBox appContent = new VBox();
        appContent.setPadding(new Insets(20));
        appContent.setSpacing(7);
        String username = comment.getAuthor().getUsername();
        Text author = new Text(username);
        appContent.getChildren().add(author);
        Text content = new Text(comment.getText());
        content.setWrappingWidth(350);
        VBox.setMargin(content, new Insets(0, 0, 0, 0));
        appContent.getChildren().add(content);
        appContent.setStyle("-fx-background-color: white");
        return appContent;
    }

    private VBox createUserVisuals(User user) {
        VBox appContent = new VBox();
        appContent.setPadding(new Insets(20));
        appContent.setSpacing(7);
        String username = user.getUsername();
        Text author = new Text(username);
        appContent.getChildren().add(author);
        appContent.setStyle("-fx-background-color: white");
        return appContent;
    }

    private VBox createPostKarmaContainer(Post currentPost) {
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
        Text karma = new Text(String.valueOf(currentPost.getKarma()));
        karma.setTextAlignment(TextAlignment.CENTER);
        karma.setStyle("-fx-text-color: white");

        containerVote.setAlignment(Pos.TOP_CENTER);
        containerVote.setPadding(new Insets(35, 0, 0, 15));
        containerVote.setStyle("-fx-background-color: white");

        containerVote.getChildren().addAll(upvote, karma, downvote);

        upvote.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                redditClone.upvotePost(currentPost);
                stage.setScene(createSceneDetailed(currentPost));
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });

        downvote.setOnMouseClicked(event -> {
            if(redditClone.getLoggedInUser() != null) {
                redditClone.downvotePost(currentPost);
                stage.setScene(createSceneDetailed(currentPost));
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please log in first before continuing.");
                a.show();
            }
        });
        return containerVote;
    }

    private VBox createCommentKarmaContainer(Post currentPost, Comment currentComment) {
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
        Text karma = new Text(String.valueOf(currentComment.getKarma()));
        karma.setTextAlignment(TextAlignment.CENTER);
        karma.setStyle("-fx-text-color: white");

        containerVote.setAlignment(Pos.TOP_CENTER);
        containerVote.setPadding(new Insets(35, 0, 0, 15));
        containerVote.setStyle("-fx-background-color: white");

        containerVote.getChildren().addAll(upvote, karma, downvote);

        upvote.setOnMouseClicked(event -> {
            redditClone.upvoteComment(currentComment);
            stage.setScene(createSceneDetailed(currentPost));
        });

        downvote.setOnMouseClicked(event -> {
            redditClone.downvoteComment(currentComment);
            stage.setScene(createSceneDetailed(currentPost));
        });
        return containerVote;
    }

    private VBox createUserKarmaContainer(User user) {
        VBox containerVote = new VBox();
        containerVote.setSpacing(7);
        containerVote.setPadding(new Insets(15));
        Text karma = new Text(String.valueOf(user.getKarma()));
        karma.setTextAlignment(TextAlignment.CENTER);
        karma.setStyle("-fx-text-color: white");

        containerVote.setAlignment(Pos.TOP_CENTER);
        containerVote.setPadding(new Insets(35, 0, 0, 15));
        containerVote.setStyle("-fx-background-color: white");

        containerVote.getChildren().addAll(karma);
        return containerVote;
    }

    public void switchScenes(Scene changeToScene) {
        this.stage.setScene(changeToScene);
    }

    @Override
    public void start(Stage stage) {
        redditClone = new RedditClone();
        this.stage = stage;
        stage.setResizable(false);
        stage.setScene(createSceneHome());
        stage.setTitle("Reddit Clone");
        stage.show();
    }
}