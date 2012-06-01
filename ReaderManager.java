import java.net.URLEncoder;
import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.Tag;

public class ReaderManager {

	private String token,username,authkey,sid; 
	
	public ReaderManager(String username,String password)
	{
		String authstr = AuthenticationManager.getGoogleAuthKey(username, password);
		if(authstr == null)
			System.exit(0);
		this.authkey = authstr.substring(authstr.indexOf("Auth=")+5, authstr.length());
		this.sid = authstr.substring(authstr.indexOf("SID=")+4,authstr.indexOf("LSID=")).trim();
		this.token = AuthenticationManager.getGoogleToken(authkey);
		this.username = (String)getUserInfo().subSequence(11,31);
	}
	
	private String getUserInfo()
	{
		try{
	      Document doc = Jsoup.connect(Constants.USER_INFO_URL)
	      				.header("Authorization", Constants.AUTHPARAMS + authkey)
	      				.timeout(4000)
	      				.get();
	 
	      // RETRIEVES THE RESPONSE USERINFO
	      String userinfo = doc.body().text();
	      return userinfo;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	 }
	
	/* Get the subscription list of a user */
	 public String[] getSubscriptionList()
	 {
		 ArrayList<String> subsList = new ArrayList<String>();
		 try
		 {
			 Document doc = Jsoup.connect(Constants.SUBSCRIPTION_LIST_URL)
		 					.header("Authorization", Constants.AUTHPARAMS + authkey)
		 					.get();
			 Elements links = doc.select("string");
			 for(Element link : links)
				 if(link.attr("name").equals("id"))
					 subsList.add(link.text());
			 
			 String[] subsArr = new String[subsList.size()];
			 subsList.toArray(subsArr);
			 return subsArr;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 public boolean addSubscription(String feedurl,String title,boolean recommendation)
	 {
		 String source="";
		 if(recommendation)
			source="&source=RECOMMENDATION"; 
		 try{
			 Document doc = Jsoup.connect(Constants.SUBSCRIPTION_EDIT_URL+"?client=PrivateReader-v1"+source)
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("s","feed/"+feedurl,
		 							   "ac","subscribe",
		 							   "t",title,
		 							   "T",token)
		 						.post();
			 if(doc.text().equals("OK"))
			 		return true;
			 	else
			 		return false;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	} 
	 
	 public boolean removeSubscription(String feedurl)
	 {
		 try{
			 Document doc = Jsoup.connect(Constants.SUBSCRIPTION_EDIT_URL+"?client=PrivateReader-v1")
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("s","feed/"+feedurl,
		 							   "ac","unsubscribe",
		 							   "T",token)
		 						.post();
		 	if(doc.text().equals("OK"))
		 		return true;
		 	else
		 		return false;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public boolean addLabelToSubscription(String feedurl, String labelname)
	 {
		 try{
			 Document doc = Jsoup.connect(Constants.SUBSCRIPTION_EDIT_URL+"?client=PrivateReader-v1")
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("s","feed/"+feedurl,
		 							   "ac","edit",
		 							   "a","user/-/label/"+labelname,
		 							   "T",token)
		 						.post();
			 if(doc.text().equals("OK"))
			 		return true;
			 	else
			 		return false;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public boolean removeLabelFromSubscription(String feedurl,String labelname)
	 {
		 try{
			 Document doc = Jsoup.connect(Constants.SUBSCRIPTION_EDIT_URL+"?client=PrivateReader-v1")
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("s","feed/"+feedurl,
		 							   "ac","edit",
		 							   "r","user/-/label/"+labelname,
		 							   "T",token)
		 						.post();
			 if(doc.text().equals("OK"))
			 		return true;
			 	else
			 		return false;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public boolean markAsRead(String feedurl, String item)
	 {
		 try{
			 Document doc = Jsoup.connect(Constants.EDIT_TAG_URL+"?client=scroll")
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("s",feedurl,
			 						   "i",item,
		 							   "ac","edit",
		 							   "a","user/-/state/com.google/read",
		 							   "r","user/-/state/com.google/kept-unread",
		 							   "T",token)
		 						.post();
			 if(doc.text().equals("OK"))
			 		return true;
			 	else
			 		return false;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public void getPreferenceStream()
	 {
		 
	 }
	 
	 public String[] getFeedItems(String feedurl)
	 {
		 ArrayList<String> itemList = new ArrayList<String>();
		 try
		 {
			 Document doc = Jsoup.connect(Constants.ATOM_FEED_URL+feedurl)
		 					.header("Authorization", Constants.AUTHPARAMS + authkey)
		 					.get();
			 Elements links = doc.select("entry");
			 for(Element link : links)
			 {
				 Elements ids = link.select("id");
				 for(Element id : ids)
				 {	 
					 itemList.add(id.text());
					 break;
				 }

			 }
			 String[] items = new String[itemList.size()];
			 itemList.toArray(items);
			 return items; 
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 public boolean addItemToTag(String feedurl,String item,Tags tag)
	 {
		 System.out.println("user/-/state/com.google/"+tag.toString().toLowerCase());
		 try{
			 Document doc = Jsoup.connect(Constants.EDIT_TAG_URL+"?client=scroll")
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("s",feedurl,
			 						   "i",item,
			 						   "async","true",
		 							   "a","user/-/state/com.google/"+tag.toString().toLowerCase(),
		 							   "T",token)
		 						.post();
			 if(doc.text().equals("OK"))
			 		return true;
			 return false;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public boolean removeItemFromTag(String feedurl,String item,Tags tag)
	 {
		 try{
			 Document doc = Jsoup.connect(Constants.EDIT_TAG_URL+"?client=scroll")
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("s",feedurl,
			 						   "i",item,
			 						   "async","true",
		 							   "r","user/-/state/com.google/"+tag.toString().toLowerCase(),
		 							   "T",token)
		 						.post();
			 if(doc.text().equals("OK"))
			 		return true;
			 return false;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public void getLabelItems(Tags tag)
	 {
		 try{
			 Document doc = Jsoup.connect(Constants.LABEL_FEED_URL+tag.toString().toLowerCase() +"?client=scroll")
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .get();
			 System.out.println(doc.text());
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
	 }
	 
	 public int getItemsCount(Tags tag)
	 {
		 try{
			 Document doc = Jsoup.connect(Constants.ITEM_COUNT_URL)
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("s","user/-/state/com.google/"+tag.toString().toLowerCase(),
			 						   "client","scroll")
		 						 .get();
			 return Integer.parseInt(doc.text());
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return -1;
		 }
	 }
	 
	 public String[] getRecommendedItemsIds()
	 {
		 ArrayList<String> itemList = new ArrayList<String>();
		 try{
			 Document doc = Jsoup.connect(Constants.STREAM_IDS_URL)
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("s","pop/topic/top/language/en",
			 						   "client","scroll",
			 						   "r","a",
			 						   "xt","user/11804333714978922939/state/com.google/dislike",
			 						   "n","100",
			 						   "merge","true")
		 						 .get();
			Elements objects = doc.select("object"); 
			for(Element object : objects)
			{
				Elements ids = object.select("number");
				for(Element id : ids)
				{	
					itemList.add(id.text());
					break;
				}
			}
			String[] idarr = new String[itemList.size()];
			itemList.toArray(idarr);
			return idarr;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 //TODO this function is not correct.
	 public String[] getRecommendedItemsContents()
	 {
		 ArrayList<String> itemList = new ArrayList<String>();
		 try{
			 Document doc = Jsoup.connect(Constants.STREAM_CONTENTS_URL)
		 						 .header("Authorization", Constants.AUTHPARAMS + authkey)
			 					 .data("freshness","false",
			 						   "client","scroll"
			 						   )
		 						 .get();
			Elements objects = doc.select("object"); 
			for(Element object : objects)
			{
				Elements ids = object.select("number");
				for(Element id : ids)
				{	
					itemList.add(id.text());
					break;
				}
			}
			String[] idarr = new String[itemList.size()];
			itemList.toArray(idarr);
			return idarr;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
}