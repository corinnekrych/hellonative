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