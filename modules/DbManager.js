Components.utils.import("resource://gre/modules/Services.jsm");
Components.utils.import("resource://gre/modules/FileUtils.jsm");

var EXPORTED_SYMBOLS = ["DbManager"];
var DbManager = {
    
    initDatabase : function()
    {
        dump("Database initialising\n");
        let file = FileUtils.getFile("ProfD",["feedreco.sqlite"]);
        let dbconn = Services.storage.openDatabase(file);
        dbconn.executeSimpleSQL("CREATE TEMP TABLE requests(id INTEGER PRIMARY KEY AUTOINCREMENT,url TEXT, method TEXT, data TEXT )");
        var stmt = dbconn.createStatement("INSERT INTO requests('http://www.google.com','get','name=Abhishek')");
        stmt.executeAsync({
            handleResult : function(result){dump("Values inserted : " + result+"\n");},
            handleError : function(error){dumo("Error : " + error + "\n")},
            handleCompletion : function(aReason){
                if (aReason != Components.interfaces.mozIStorageStatementCallback.REASON_FINISHED)
                    dumo("Query canceled or aborted!\n");
            }
        });
    },
    
    uninit : function()
    {
        dump("Database uninit\n");
         mozIStorageConnection.asyncClose();
    }
    
}