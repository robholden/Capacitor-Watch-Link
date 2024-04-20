// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorPluginWatchLink",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "WatchLinkPlugin",
            targets: ["WatchLinkPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "WatchLinkPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/WatchLinkPlugin"),
        .testTarget(
            name: "WatchLinkPluginTests",
            dependencies: ["WatchLinkPlugin"],
            path: "ios/Tests/WatchLinkPluginTests")
    ]
)