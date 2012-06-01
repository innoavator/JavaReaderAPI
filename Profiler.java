

public class Profiler {

	private ReaderManager reader;
	//Constructor
	public Profiler(String username,String password)
	{
		reader = new ReaderManager(username,password);
	}
	
	public void buildProfile()
	{
		//reader.getLabelItems(Tags.DISLIKE);
		//System.out.println("Like count : " + reader.getItemsCount(Tags.LIKE));
		readRecommendedItems();
	}
	
	private void readRecommendedItems()
	{
		//String[] items = reader.getRecommendedItems(); 
		/*for(int j=0;j<items.length;j++)
		{	
			System.out.println("Marking Read" + items[j]);
			//reader.markAsRead(feedids[i],items[j]);
			reader.addItemToTag(,items[j],Tags.LIKE);
			break;
		}*/
	}
	
	private void addSubscriptions()
	{
		reader.addSubscription("http://newsrss.bbc.co.uk/rss/sportonline_uk_edition/latest_published_stories/rss.xml","BBC Sports",false);
		reader.addSubscription("http://www.espncricinfo.com/rss/livescores.xml","ESPN Cricket",false);
		reader.addSubscription("http://sports.espn.go.com/espn/rss/news","ESPN Sports",false);
		reader.addSubscription("http://www.fifa.com/rss/index.xml","FIFA news",false);
		reader.addSubscription("http://www.goal.com/en-india/feeds/news?fmt=rss","Goal News",false);
		reader.addSubscription("http://feeds1.nytimes.com/nyt/rss/Sports","NyTimes Sports",false);
		reader.addSubscription("http://sports.yahoo.com/top/rss.xml","Yahoo Sports",false);
	}
	
	private void readFeeds()
	{
		String[] feedids = reader.getSubscriptionList();
		for(int i=0;i<feedids.length;i++)
		{	
			String[] items = reader.getFeedItems(feedids[i]); 
			for(int j=0;j<items.length;j++)
			{	
				System.out.println("Marking Read" + feedids[i]+" : " + items[j]);
				//reader.markAsRead(feedids[i],items[j]);
				reader.addItemToTag(feedids[i], items[j],Tags.LIKE);
				break;
			}
			break;
		}
	}
}
