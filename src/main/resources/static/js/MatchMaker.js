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

    this.token = null;
};

MatchMaker.prototype.getSessionId = function () {
    let name = "name=" + Math.floor((1 + Math.random()) * 0x10000)
        .toString(16)
        .substring(1);

    var token = this.token;
    this.settings.data = {token};
    let sessionId = -1;
    $.ajax(this.settings).done(function(id) {
        sessionId = id;
    }).fail(function() {
        alert("Matchmaker request failed");
    });
    return sessionId;
};

MatchMaker.prototype.getToken = function (name) {
    this.register.data = {name};
    const self = this;
    $.ajax(this.register).done(function(responseToken) {
         self.token = responseToken.token;
    }).fail(function() {
        alert('try again...');
    });
    return this.token !== null;
}

gMatchMaker = new MatchMaker(gClusterSettings);