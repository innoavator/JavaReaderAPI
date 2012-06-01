Components.utils.import("resource://feedreco/io.js");

var EXPORTED_SYMBOLS = ["ActionObserver"];
const {classes : Cc, interface : Ci} = Components;

var StdUrls = new Array("reader/api/0/subscription",
                        "reader/api/0/edit-tag","g");
var ActionObserver = {
    datafile : null,
    init : function()
    {
        dump("ActionObserver init\n");
        this.datafile = FileIO.open("/home/abhishek/data.txt");
        dump("File creation : " + FileIO.create(this.datafile) + "\n");
        if(!this.datafile)
            dumpmp("File is null");
        dump("File path  :" + FileIO.path(this.datafile) + "\n");
        //DbManager.initDatabase();   
    },
    
    uninit : function()
    {
        dump("Actionobserver uninit\n");
        //FileIO.unlink(this.datafile);
        //DbManager.uninit();    
    },
    
    observe : function(aSubject,aTopic,aData){
        aSubject.QueryInterface(Components.interfaces.nsIHttpChannel);
        url = aSubject.URI.spec;
        for(var i=0;i<StdUrls.length;i++)
        {
            if(url.indexOf(StdUrls[i])!=-1)
            {
                if(aSubject.requestMethod.toLowerCase() == "get")
                {
                    url = url.split("?")[0];
                    //dump("GET :" + url + "\n");
                    var getData = url.split("?")[1];
                    //dump("Getdata : " + getData + "\n");
                    FileIO.write(this.datafile,"GET\n","a");
                    FileIO.write(this.datafile,url+"\n","a");
                    if(!getData || getData =="")
                        getData = "null";
                    FileIO.write(this.datafile,getData+"\n",'a');
                }
                else if(aSubject.requestMethod.toLowerCase() == "post")
                {
                    dump("POST : " + url + "\n");
                    var request = aSubject.QueryInterface(Components.interfaces.nsIRequest);
                    var postData = this.readPostTextFromRequest(request);
                        
                    FileIO.write(this.datafile,"POST\n","a");
                    FileIO.write(this.datafile,url+"\n","a");
                    if(!postData || postData =="")
                        postData = "null";
                    FileIO.write(this.datafile,postData+"\n",'a');
                }
                break;
            }
        }
       
    },
    readPostTextFromRequest : function(request)
    {
        var is = request.QueryInterface(Components.interfaces.nsIUploadChannel).uploadStream;
        if (is)
        {
            var ss = is.QueryInterface(Components.interfaces.nsISeekableStream);
            var prevOffset;
            if (ss)
            {
                prevOffset = ss.tell();
                ss.seek(Components.interfaces.nsISeekableStream.NS_SEEK_SET, 0);
            }

            // Read data from the stream..
            var charset = "UTF-8";
            var text = this.readFromStream(is, charset, true);

            // Seek locks the file so, seek to the beginning only if necko hasn't read it yet,
            // since necko doesn't seek to 0 before reading (at lest not till 459384 is fixed).
            if (ss && prevOffset == 0) 
                ss.seek(Components.interfaces.nsISeekableStream.NS_SEEK_SET, 0);
            return text;
        }
        else
        {
            dump("Failed to Query Interface for upload stream.\n");
            return null;

        }
    },
    readFromStream : function(stream, charset, noClose)    
    {
        var sis = Components.classes["@mozilla.org/binaryinputstream;1"]
                     .getService(Components.interfaces.nsIBinaryInputStream);

        sis.setInputStream(stream);

        var segments = [];
        for (var count = stream.available(); count; count = stream.available())
            segments.push(sis.readBytes(count));

        if (!noClose)	
            sis.close();

        var text = segments.join("");
        return text;
    }
    

}

