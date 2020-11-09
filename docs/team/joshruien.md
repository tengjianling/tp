---
layout: page
title: Joshua Tan's Project Portfolio Page
---

## Project: Medmoriser

Medmoriser is a desktop app to help medical students organise, memorise and revise their content. The user interacts with it using a CLI, and it has a GUI created with JavaFX and is written in Java.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=joshruien)

* **New Feature**: Added a quiz feature for users to test themselves.
  * What it does: Allows users to quiz themselves to test their memory on the answers to the questions. Medmoriser selects a random question based on the keywords that users input and quizzes them. After answering the question, the answer to the question will show up so that users can check their answers.
  * Justification: This feature improves the product significantly because our app utilizes active recall to help users to memorise their content. Active recall is a proven study technique to help improve memory.
  * Highlights:
    * It required an in-depth analysis of design alternatives challenging as it was an entirely new feature on its own.
    * It required commands to be created. Other commands created for this feature are `answer` and `endquiz`.
    * New command classes and their parser classes had to be created.
    * New test classes to test our features.
    * This feature was rather challenging to implement. We had to keep track of the state, whether there was an ongoing quiz or not. Furthermore, we had to ensure a flow (ie. answer command comes after quiz command), whereas for the existing commands, they were one off commands. Hence it was challenging to implement the carrying over of the state across different commands.

* **New Feature**: Added an answer feature for users to answer the quiz question.
  * What it does: Allows users to answer the quiz question.
  * Justification: This feature improves the product significantly as users are able to check their input answer against the answer that was keyed into Medmoriser for that particular question. This way they will know whether they could remember what they learnt.
  * Highlights:
    * It required a lot of error checking, to ensure that this command could only be called when there was an ongoing quiz and when the question has not yet been answered.
    
* **New Feature**: Added an end quiz feature when users are done with the quiz.
  * What it does: Allows users to end an ongoing quiz. When there is an ongoing quiz, users have to first end the quiz before being able to run other commands other than `answer` and `next`.

* **Enhancements to existing features**:
  * Updated the list command to show QAndAs (Pull requests [\#40](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/40))
  * Updated the edit and add command examples (Pull requests [\#137](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/137))
  * Redefined add and edit functions such that there can only be a limit of 1 prefix in the user input. (Pull requests [\#154](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/154))
  * Wrote additional tests: `AddCommandParserTest`, `EditCommandParserTest` , `NextCommandTest`, `QuizCommandParserTest`. (Pull requests [\#154](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/154) ,[\#160](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/160), [\#171](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/171))
  * Refactoring: Removed space between prefix and parameters for quiz and find command. (Pull Requests [#65](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/65))
  * Refactoring: Removed AB3 phone and email instances. (Pull Requests [\#71](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/71))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `quiz` and `answer` and `endquiz`.
      * Pull Requests: [\#85](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/85), [\#141](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/141), [\#155](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/155)
    * Added documentation for the command summary.
      * Pull Requests: [\#85](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/85), [\#141](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/141)
  * Developer Guide:
    * Added implementation details of the `quiz` feature.
       * Pull Requests: [\#85](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/85), [\#94](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/94), [\#166](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/166)
    * Added user stories.
       * Pull Requests: [\#19](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/19)
    * Edited the architecture diagrams to suit our application.
       * Pull Requests: [\#176](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/176)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#138](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/138)
  * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2021S1-CS2103T-W13-1/tp/issues/174), [3](https://github.com/AY2021S1-CS2103T-W13-1/tp/issues/176), [6](https://github.com/AY2021S1-CS2103T-W13-1/tp/issues/179))

