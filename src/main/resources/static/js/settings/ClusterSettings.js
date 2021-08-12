var ClusterSetting = function () {
    this.gameServer = {
        protocol: 'ws',
        host: '192.168.1.79',
        port: '8081',
        path: '/ws/action'
    };

    this.matchMaker = {
        protocol: 'http',
        host: '192.168.1.79',
        port: '8081',
        path: '/matchmaker/join'
    };

    this.register = {
        protocol: 'http',
        host: '192.168.1.79',
        port: '8081',
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
