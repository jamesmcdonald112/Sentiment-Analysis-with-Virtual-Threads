# Sentiment Analysis with Virtual Threads
**Author:** James McDonald  
**Version:** Java JDK 21

## Description
This Java application is designed for sentiment analysis of tweets using virtual threads. Developed as part of the H.Dip. in Science (Software Development) - Software Design & Data Structures module, the application processes a set of tweets to determine if the overall attitude is positive, negative, or neutral. It uses a suite of lexicons and virtual threading for efficient and accurate sentiment analysis.

## Running from Source Code
1. **Prerequisites:** Install Java JDK (version 21).
2. **Extract Project:** Download and extract the project's .zip file.
3. **Compile:** Open a terminal or command prompt. Navigate to the project's src folder and run `javac ie/atu/sw/utilities/*.java`.
4. **Run:** Execute `java ie.atu.sw.utilities.Runner` in the same directory.

## Running the JAR File
1. **Prerequisites:** Install Java Runtime Environment (JRE) version 19.0.2 or higher.
2. **Run JAR:** Open a terminal or command prompt, navigate to the directory containing `SentimentAnalyser.jar`, and execute `java -jar SentimentAnalyser.jar`.
   - Note: Ensure file paths for input, lexicons, and output directories are correct and accessible.

## Features
- Virtual Thread Processing: Utilises Java's virtual threads for concurrent processing of tweets and lexicons.
- Menu-Driven User Interface: A console-based, user-friendly interface for easy interaction.
- Flexible Directory Management: Configure paths for tweet data, lexicon files, and output.
- Lexicon-Based Analysis: Supports multiple lexicons for sentiment scoring.
- Comprehensive Sentiment Analysis: Processes tweets to determine overall positive, negative, or neutral sentiment.
- Output Handling: Writes analysis results to a specified output directory.
- Error Handling: Robustly manages file read/write issues and validates directory paths and lexicon configurations.

## Extras
- Lexicon Configuration: Choose from multiple lexicon options for sentiment analysis.
- Real-Time Analysis Feedback: Option to display colour-coded sentiment analysis results in the console.
- Settings Review and Reset: View or reset current settings using the extras menu.
- Concurrency with Virtual Threads: Demonstrates the use of structured concurrency.
- Sentiment Analysis Reporting: Generates a detailed report of the sentiment analysis.
- Scalable and Efficient Processing: Designed to handle large sets of tweets efficiently.
