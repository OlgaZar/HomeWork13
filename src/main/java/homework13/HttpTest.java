package homework13;

import java.io.IOException;

public class HttpTest {
    static final String BASIC_URL = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) throws IOException, InterruptedException {
      String path1 = "C:\\Users\\imlel\\IdeaProjects\\GoItStudy\\src\\main\\java\\homework13\\user1.json";
        HttpTaskUtil.postToSite(BASIC_URL, path1);

        String path2 = "C:\\Users\\imlel\\IdeaProjects\\GoItStudy\\src\\main\\java\\homework13\\user2.json";
        HttpTaskUtil.putToSite(BASIC_URL, 2, path2);

        HttpTaskUtil.deleteFromSite (BASIC_URL, 7);

        HttpTaskUtil.getFromSiteListOfUsers(BASIC_URL);

        HttpTaskUtil.getFromSiteById(BASIC_URL, 8);

        HttpTaskUtil.getFromSiteByUsername(BASIC_URL, "Karianne");

        HttpTaskUtil.getCommentsToLastPostByUserId(BASIC_URL, 1);

        HttpTaskUtil.getOpenTaskByIserId(BASIC_URL, 1);
   }

}
