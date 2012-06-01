
public class Constants {

	public static final String proxyHost = "in.proxy.lucent.com";
	public static final String proxyPort = "8000";
	
	public static final String AUTHPARAMS = "GoogleLogin auth=";
	public static final String GOOGLE_LOGIN_URL = "https://www.google.com/accounts/ClientLogin";
	public static final String READER_BASE_URL  = "http://www.google.com/reader/";
	public static final String API_URL = READER_BASE_URL + "api/0/";
	public static final String TOKEN_URL = API_URL + "token";
	public static final String USER_INFO_URL = API_URL + "user-info";
	public static final String USER_LABEL = "user/-/label/";
	public static final String TAG_LIST_URL = API_URL + "tag/list";
	public static final String EDIT_TAG_URL = API_URL + "edit-tag";
	public static final String RENAME_TAG_URL = API_URL + "rename-tag";
	public static final String DISABLE_TAG_URL = API_URL + "disable-tag";
	public static final String SUBSCRIPTION_EDIT_URL = API_URL + "subscription/edit";
	public static final String SUBSCRIPTION_LIST_URL = API_URL + "subscription/list";
	public static final String ATOM_FEED_URL = READER_BASE_URL + "atom/";
	public static final String LABEL_FEED_URL = API_URL + "stream/contents/user/-/state/com.google/";
	public static final String ITEM_COUNT_URL = API_URL+ "stream/items/count";
	public static final String STREAM_IDS_URL = API_URL+ "stream/items/ids";
	public static final String STREAM_CONTENTS_URL = API_URL+ "stream/items/contents";
}
