import Foundation

@objc public class WatchLink: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
