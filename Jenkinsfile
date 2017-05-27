node {
	checkout scm
	sh 'git submodule update --init' 
	sh './gradlew setupDecompWorkspace clean build --stacktrace'
	archive 'build/libs/*jar'
}
