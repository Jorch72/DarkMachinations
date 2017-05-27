node {
	checkout scm
	sh './gradlew setupDecompWorkspace clean build --stacktrace'
	archive 'build/libs/*jar'
}
