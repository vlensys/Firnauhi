import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion

apply(plugin = "firnauhi.base")
apply(plugin = "firnauhi.repositories")

plugins.withId("java") {
	extensions.configure(JavaPluginExtension::class.java) {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(21))
		}
	}
}
