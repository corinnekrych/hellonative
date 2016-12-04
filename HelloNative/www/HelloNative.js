var exec = require('cordova/exec');

exports.hello = function(arg0, success, error) {
    exec(success, error, "HelloNative", "hello", [arg0]);
};
