var MatchMaker = function (clusterSetting) {
    this.settings = {
        url: clusterSetting.matchMakerUrl(),
        method: "POST",
        crossDomain: true,
        async: false
    };

    this.register = {
        url: clusterSetting.registerUrl(),
        method: "POST",
        crossDomain: true,
        async: false
    };
};

MatchMaker.prototype.getSessionId = function () {
    let name = "name=" + Math.floor((1 + Math.random()) * 0x10000)
        .toString(16)
        .substring(1);

    this.settings.data = name;
    let sessionId = -1;
    $.ajax(this.settings).done(function(id) {
        sessionId = id;
    }).fail(function() {
        alert("Matchmaker request failed");
    });

    return sessionId;
};

//TODO
MatchMaker.prototype.getToken = function (name) {
    this.register.data = name;

    let userToken = null;
    $.ajax(this.register).done(function(token) {
        userToken = token
    }).fail(function() {
        //TODO
    });
    return userToken;
}

gMatchMaker = new MatchMaker(gClusterSettings);