Writing a Swift 3.0 Cordova plugin
==================================

Demo sample written following [Modus Create's Blog](http://moduscreate.com/writing-a-cordova-plugin-in-swift-for-ios/). this is a simple `HelloWorld` example. The plugin display in a toast/alert view a `Hello YourName` for 3 seconds.

Thanks @simon_prickett for the great article.


Here are the differents steps:

### Create plugin

```bash
plugman create --name HelloNative --plugin_id org-aerogear-hello --plugin_version 0.0.1 --path hellonative
```

this command generates the [HelloNative](HelloNative/) folder.

### Implement the Common JavaScript Interface

In [HelloNative/www/HelloNative.js](HelloNative/www/HelloNative.js), create the `hello` method

### Implement the Swift Native Code

In [HelloNative/plugin.xml](HelloNative/plugin.xml#L17) add platform ios section.

Create [HelloNative/src/ios/HelloNative.swift](HelloNative/src/ios/HelloNative.swift).

```swift
@objc(HelloNative) class HelloNative : CDVPlugin {
  @objc(hello:) func hello(command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR)

    let name = command.arguments[0] as? String ?? ""

    if name.characters.count > 0 {
      /* UIAlertController is iOS 8 or newer only. */
      let toastController = UIAlertController(title: "Hello...", message: name, preferredStyle: .alert)

      self.viewController?.present(toastController, animated: true, completion: nil)

        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(3)) {
          toastController.dismiss(animated: true, completion: nil)
        }

      pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: name)
    }

    self.commandDelegate!.send(pluginResult, callbackId: command.callbackId)
  }
}
```

> Note the importance of prefixing class and function name by `@objc(HelloNative)` and `@objc(hello:)`. Corodva uses the dynamic feature of Objective-C (Swift3 is still lacking a dynamic API) there fore your Swift code will be translated in Objective-C. As per [Apple doc](https://developer.apple.com/library/content/documentation/Swift/Conceptual/BuildingCocoaApps/MixandMatch.html): The @objc attribute makes your Swift API available in Objective-C and the Objective-C runtime.

To be able to create a bridging header, use [cordova-plugin-add-swift-support](https://github.com/akofman/cordova-plugin-add-swift-support), in [HelloNative/plugin.xml](HelloNative/plugin.xml#L25) add:
```xml
  <dependency id="cordova-plugin-add-swift-support" version="1.6.0"/>
```

### Test the plugin
Use [MyApp](MyApp) to test the plugin, run:

```bash
cd MyApp
cordova platform add ios
cordova run ios
```