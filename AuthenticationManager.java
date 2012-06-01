import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AuthenticationManager {
	
	//Get google Auth key 
	public static String getGoogleAuthKey(String email,String password)
	{
		try{
			Document doc = Jsoup.connect(Constants.GOOGLE_LOGIN_URL)
								.data("accountType","GOOGLE",
									  "Email",email,
									  "Passwd",password,
									  "service","reader",
									  "source", "PrivateReader-v1")
								.timeout(4000)
								.post();
			
			
		    return doc.body().text();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return "NULL";
		}
	}
	
	//Get token 
	public static String getGoogleToken(String authkey)
	{
		try
		{
			Document doc = Jsoup.connect(Constants.TOKEN_URL)
		    .header("Authorization",Constants.AUTHPARAMS + authkey)
		    .timeout(4000)
		    .get();
		 
		    // Retrieve the response token
			String _TOKEN = doc.body().text();
		    return _TOKEN;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
