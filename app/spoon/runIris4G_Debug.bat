java -jar spoon-runner-1.7.0-jar-with-dependencies.jar  --apk ../../app/build/outputs/apk/app-debug.apk --test-apk ../../app/build/outputs/apk/app-debug-androidTest-unaligned.apk --e listener=ckt.listener.TestFailedListener --class-name iris4G.testcase.LiveSaveCase  --sequential --debug

