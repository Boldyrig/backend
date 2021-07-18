var ClusterSetting = function () {
    this.gameServer = {
        protocol: 'ws',
        host: 'localhost',
        port: '8090',
        path: '/events/connect'
    };

    this.matchMaker = {
        protocol: 'http',
        host: 'localhost',
        port: '8080',
        path: '/matchmaker/join'
    };

    this.register = {
        protocol: 'http',
        host: 'localhost',
        port: '8080',
        path: '/authentication'
    }
};

ClusterSetting.prototype.gameServerUrl = function() {
    return makeUrl(this.gameServer)
};

ClusterSetting.prototype.matchMakerUrl = function() {
    return makeUrl(this.matchMaker)
};

ClusterSetting.prototype.registerUrl = function () {
    return makeUrl(this.register)
}

function makeUrl(data) {
    return data['protocol'] + "://" + data['host'] + ":" + data['port'] + data['path']
}

var gClusterSettings = new ClusterSetting();
