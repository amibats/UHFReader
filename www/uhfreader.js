var uhfreader = {
    read: function(successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, 'UHFReader', 'read', []);
    },
    write: function(successCallback, errorCallback, tags) {
        cordova.exec(successCallback, errorCallback, 'UHFReader', 'write', tags);
    }
};
module.exports = uhfreader;