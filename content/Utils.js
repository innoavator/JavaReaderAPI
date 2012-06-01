
FeedReco.Utils = {
    
    getLocalDirectory : function()
    {
       /* let directoryService = Components.classes["@mozilla.org/file/directory_service;1"].
                                    getService(Components.interfaces.nsIProperties);
        let localDir = directoryService.get("ProfD",Ci.nsIFile);
        localDir.append("Preader");
        if(!localDir.exists() || !localDir.isDirectory())
        {
            localDir.create(Ci.nsIFile.DIRECTORY_TYPE,0774);
        }
        return localDir;
       */
    }
}