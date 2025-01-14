@echo off
javac -sourcepath src -d output -encoding UTF-8 src/com/blackjack/*.java src/com/blackjack/trump/*.java
cd output
java com.blackjack.Main