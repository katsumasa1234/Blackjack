@echo off
javac -sourcepath src -d output src/com/blackjack/*.java
javac -sourcepath src -d output src/com/blackjack/trump/*.java
cd output
java com.blackjack.Main