fun execString(vararg args: String): String {
	val pb = ProcessBuilder(*args)
		.redirectOutput(ProcessBuilder.Redirect.PIPE)
		.start()
	pb.waitFor()
	return pb.inputStream.readAllBytes().decodeToString().trim()
}

private val describeTag = "^v?([0-9]+(?:\\.[0-9]+){1,2})-([0-9]+)-g[0-9a-fA-F]+$".toRegex()

inline fun <T> Regex.useMatcher(string: String, block: (MatchResult) -> T): T? {
	return matchEntire(string)?.let(block)
}

fun getGitTagInfo(mcVersion: String): String {
	val str = runCatching {
		execString("git", "describe", "--tags", "--long", "HEAD")
	}.getOrDefault("")
	describeTag.useMatcher(str) {
		val base = it.groupValues[1]
		val commitsAhead = it.groupValues[2]
		if (commitsAhead == "0") return "$base+mc$mcVersion"
		return "$base-dev.$commitsAhead+mc$mcVersion"
	}
	val commits = runCatching {
		execString("git", "rev-list", "--count", "HEAD")
	}.getOrDefault("0")
	return "0.0.0-dev.$commits+mc$mcVersion"
}
