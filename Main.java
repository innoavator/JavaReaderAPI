import org.jsoup.Jsoup;
public class Main {
	
	public static void main(String[] args)
	{
		//Set proxy settings
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("http.proxyHost", Constants.proxyHost);
		System.getProperties().put("http.proxyPort", Constants.proxyPort);
		System.getProperties().put("https.proxyHost",Constants.proxyHost);
		System.getProperties().put("https.proxyPort", Constants.proxyPort);
		String username = "alcalucent123@gmail.com";
		String password = "alcalucent123";
		Profiler profiler = new Profiler(username,password);
		profiler.buildProfile();
	}
}
