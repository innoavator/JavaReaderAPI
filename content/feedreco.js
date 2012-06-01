
Components.utils.import("resource://feedreco/common.js");
Components.utils.import("resource://gre/modules/Services.jsm");

if ("undefined" == typeof(FeedReco)) {
  var FeedReco = {};
};

//Event listeners
window.addEventListener("load", function() { FeedReco.init(); }, false);
window.addEventListener("unload",function() { FeedReco.uninit() }, false);

FeedReco = {
    
    observerService : null,
    init : function()
    {
        window.dump("FeedReco initialising");
        FeedReco.observerService = Components.classes["@mozilla.org/observer-service;1"].
                              getService(Components.interfaces.nsIObserverService);
        FeedReco.observerService.addObserver(ActionObserver,"http-on-modify-request",false);
        ActionObserver.init();
    },
    uninit : function()
    {
        ActionObserver.uninit();
        window.dump("FeedReco stopped");
    },
    showPreferences : function(aEvent)
    {
        window.dump("This is from XUL dump");
        window.open("chrome://feedreco/content/options.xul","Privacy Preferences",
                    "chrome,centerscreen");
    }
    
    
}