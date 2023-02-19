import com.sample.multiplatform.PlatformConfiguration
import com.sample.multiplatform.PlatformProviderSDK
import com.sample.multiplatform.setup.setupThemedNavigation

import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    PlatformProviderSDK.init(
        configuration = PlatformConfiguration()
    )
    JFrame().setupThemedNavigation()
}