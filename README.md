
#### Jetpack支持库CameraX

`CameraX` 是一个 `Jetpack` 支持库，旨在帮助您简化相机应用的开发工作。它提供一致且易于使用的 `API` 界面，适用于大多数 `Android` 设备，并可向后兼容至 `Android 5.0`（API 级别 21）。

虽然它利用的是 `camera2` 的功能，但使用的是更为简单且基于用例的方法，该方法具有生命周期感知能力。它还解决了设备兼容性问题，因此您无需在代码库中包含设备专属代码。这些功能减少了将相机功能添加到应用时需要编写的代码量。

最后，借助 `CameraX`，开发者只需两行代码就能利用与预安装的相机应用相同的相机体验和功能。 [CameraX Extensions](https://developer.android.com/training/camerax/vendor-extensions) 是可选插件，通过该插件，您可以在[支持的设备](https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-master-dev/camera/extensions/ExtensionsSupportedDevices.md)上向自己的应用中添加人像、HDR、夜间模式和美颜等效果。


> CameraX 库目前处于 Alpha 版测试阶段，因为其 API 界面尚未最终确定。我们不建议在生产环境中使用 Alpha 库。CameraX 库应在生产环境中严格避免依赖 Alpha 库，因为其 API 界面可能会以与源代码和二进制文件不兼容的方式发生变化。