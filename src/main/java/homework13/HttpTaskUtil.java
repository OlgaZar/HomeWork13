package homework13;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class HttpTaskUtil {
    final  static HttpClient CLIENT = HttpClient.newHttpClient();
    final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void postToSite(String basicUrl, String path) throws IOException, InterruptedException {
        String createdUrl = basicUrl+"/users";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createdUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(path)))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        print(response);
    }


    public  static void putToSite (String basicUrl, int id,  String path) throws IOException, InterruptedException {
        String createdUrl = basicUrl+"/users/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createdUrl))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Paths.get(path)))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        print(response);
    }


        public static void getFromSiteListOfUsers (String basicUrl) throws IOException, InterruptedException {
        String createdUrl = basicUrl+"/users";
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(createdUrl))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        print(response);

}


    public static void deleteFromSite (String basicUrl, int id) throws IOException, InterruptedException {
        String createdUrl= basicUrl+"/users/" + id;
         HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(createdUrl))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();
         HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
         print(response);
}

    public static HttpResponse<String> getFromSiteWithoutPrint (String createdUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createdUrl))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }


    public static void getFromSiteById (String basicUrl, int id) throws IOException, InterruptedException {
        String createdUrl = basicUrl+"/users/"+id;
        print(getFromSiteWithoutPrint(createdUrl));
    }

    public static void getFromSiteByUsername (String basicUrl, String username) throws IOException, InterruptedException {
        String createdUrl =  basicUrl+"/users?username="+username;
        print(getFromSiteWithoutPrint(createdUrl));


    }
    public static void getCommentsToLastPostByUserId (String basicUrl, int id) throws IOException, InterruptedException {
        String createdUrl = basicUrl + "/users/" + id + "/posts";
        HttpResponse<String> response = getFromSiteWithoutPrint(createdUrl);
        int max = getLastPostId(response);
        createdUrl = basicUrl + "/posts/" + max + "/comments";
        response = getFromSiteWithoutPrint(createdUrl);
        List<String> commentsResult =  getComment(response);
        System.out.println("comments = " + commentsResult);
        String result = new GsonBuilder().setPrettyPrinting().create().toJson(commentsResult);
        String nameOfJsonFile = "user-" + id + "-post-" + max + "-comments.json";
        recordToJson(result, nameOfJsonFile);

    }
    public static List<String> getComment(HttpResponse<String> response) throws JsonProcessingException {

        List<Comment> comments = OBJECT_MAPPER.readValue(response.body(), new TypeReference<>() { });
        return comments.stream()
            .map(comment -> comment.getBody())
            .collect(Collectors.toList());

    }

    public static int getLastPostId(HttpResponse <String> response) throws JsonProcessingException {

        List<Post> posts = OBJECT_MAPPER.readValue(response.body(), new TypeReference<>()  {});
        Optional<Integer> max = posts.stream()
            .map(post -> post.getId())
            .max(Comparator.naturalOrder());
        return max.get();
    }


    public static void recordToJson(String forRecord, String nameOfFile ) throws FileNotFoundException {
            OutputStream fos = new FileOutputStream(nameOfFile);
            try {
                fos.write(forRecord.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        public static void getOpenTaskByIserId (String basicUrl, int id) throws IOException, InterruptedException {
            String createdUrl = basicUrl+"/users/" + id+"/todos";
            HttpResponse <String> response = getFromSiteWithoutPrint(createdUrl);
            List<ToDo> todos = OBJECT_MAPPER.readValue(response.body(), new TypeReference<>() {});
            List <String> openTasks = todos.stream()
                    .filter(toDo -> !toDo.isCompleted())
                                    . map(toDo -> toDo.getTitle())
                                            .collect(Collectors.toList());
            System.out.println("openTasks = " + openTasks);

        }



    public static void print(HttpResponse<String> response) {
        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body() = " + response.body());
    }
}


