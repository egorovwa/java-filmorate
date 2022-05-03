package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FilmorateApplicationTests {
	Gson gson = new Gson();
	HttpClient client = HttpClient.newHttpClient();
@BeforeEach
void startApp(){
	FilmorateApplication.main(new String[0]);
}
	@Test
void normalCreateUser() throws IOException, InterruptedException {
		User user = new User("asdf@mail.ru","Login", LocalDate.of(1985,11,11));
		user.setName("Name");
		URI uri = URI.create("http://localhost:8080/users");
		String json = gson.toJson(user);
		HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
		HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(body).build();
		HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

		List<User> userList = getUserList();
		System.out.println(userList);

	}

	private List<User> getUserList() throws IOException, InterruptedException {
		List<User> userList = new ArrayList<>();
		URI uri = URI.create("http://localhost:8080/users");
		HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
		JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
		jsonArray.forEach(r->userList.add(gson.fromJson(r,User.class)));
		return userList;
	}

}
