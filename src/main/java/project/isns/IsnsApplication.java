package project.isns;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutionException;


@Controller
//@RestController
@SpringBootApplication
public class IsnsApplication {
	@Autowired
	private SmtpMailSender smtpMailSenders;
	@RequestMapping("/")
	@ResponseBody
	String home() throws MessagingException {
		return "Hello World!!";
	}

	public static void main(String[] args) {
		SpringApplication.run(IsnsApplication.class, args);
	}

}
