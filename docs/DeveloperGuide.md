---
layout: page
title: Developer Guide
---
* Table of Contents:
{:toc}
  
--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2021S1-CS2103T-W15-1/tp/tree/master/docs/diagrams) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-W15-1/tp/blob/master/src/main/java/seedu/medmoriser/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-W15-1/tp/blob/master/src/main/java/seedu/medmoriser/MainApp.java). It is responsible for:
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2021S1-CS2103T-W15-1/tp/blob/master/src/main/java/seedu/medmoriser/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `QAndAListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2021S1-CS2103T-W15-1/tp/blob/master/src/main/java/seedu/medmoriser/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2021S1-CS2103T-W15-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-W15-1/tp/blob/master/src/main/java/seedu/medmoriser/logic/Logic.java)

1. `Logic` uses the `MedmoriserParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a qAndA).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/AY2021S1-CS2103T-W15-1/tp/blob/master/src/main/java/seedu/medmoriser/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the answer book data.
* exposes an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.


<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `Medmoriser`, which `QAndA` references. This allows `Medmoriser` to only require one `Tag` object per unique `Tag`, instead of each `QAndA` needing their own `Tag` object.<br>
![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>


### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-W15-1/tp/blob/master/src/main/java/seedu/medmoriser/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the answer book data in json format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.medmoriser.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add Feature (Cheng Jiyuqing)

#### Implementation

This portion describes the implementation of the add feature which allows the user to add new QAndAs with the question, answer and tags(optional).

Explanation of the diagrams:

1. As the user parses the add command through the `UI`, the `MedmoriserParser` identifies this as a `AddCommand` and passes the user input to the `AddCommandParser`.
2. The `AddCommandParser` ensures the input is of correct format and identifies the input for question, answer and tag(s).
3. The `AddCommandParser` then creates a new QAndA object, and passes it to a new `AddCommand` object to handle the rest of the job.
4. The `AddCommand` object will then add the new QAndA to model, and return a `CommandResult` containing a message to users indicating success in adding a new QAndA.
5. `UI` will also be updated accordingly.

The following sequence diagram (Fig 1.1) shows how the add operation works:

(Figure 1.1: Add Command Sequence Diagram)
![AddSequenceDiagram](images/AddSequenceDiagram.png)

### Find feature (Ming Yang)

#### Implementation

This portion describes the implementation of the find feature which allows the user to find QAndA sets based on the question, answer or tags.

Explanation of the diagrams:
1. As the user parses the find command through the `UI`, the `MedmoriserParser` identifies this as a `FindCommand` and passes the user input to the `FindCommandParser`.
2. The `FindCommandParser` identifies the type of find command, such as find tag/question/answer.
3. A `FindCommand` with the respective predicate such as `TagContainsKeywordPredicate` is created and returned to the `LogicManager`.
4. The filtered list of question will be displayed on the `UI`.

The following sequence diagram (Fig 1.2) shows how the find operation works:

(Figure 1.2: Find Command Sequence Diagram)
![UndoSequenceDiagram](images/FindSequenceDiagram.png)

The activity diagram below (Fig 1.3) shows the activity flow of the find feature.

(Figure 1.3: Find Command Activity Diagram)
![FindActivityDiagram](images/FindActivityDiagram.png)

### Quiz Feature (Joshua Tan)

#### Implementation

This feature allows the user to quiz himself/herself.

The flow of the execution is as follows:

1. User enters the input for quiz (eg. `quiz t/Immune System`)

2. The `LogicManager` executes the command, the command is then parsed by `MedmoriserParser` and the command is
identified as a `QuizCommand`. The command is then parsed by `QuizCommandParser`.

3. `QuizCommandParser` identifies the type of quiz command. (eg. quiz t/)

4. A `QuizCommand` with the respective predicate, in this case `TagContainsKeywordPredicate` is created and returned to `LogicManager`

5. The `QuizCard` will be displayed on the `UI`.

The sequence diagram below (Fig 1.4) shows the interactions between the Logic and model components for the quiz command.

(Figure 1.4: Quiz Command Sequence Diagram)
![QuizSequenceDiagram](images/QuizSequenceDiagram.png)

The diagram below (Fig 1.5) shows the activity diagram flow for the quiz command

(Figure 1.5: Quiz Command Activity Diagram)
![QuizActivityDiagram](images/QuizActivityDiagram.png)


### Delete Feature (Jian Ling)

#### Implementation

This feature allows the user to delete a QAndA from the answer book. The flow of the execution of the delete command is shown below.

1. User enters the input to delete a QAndA (eg. `delete 1`)

2. The `LogicManager`  executes this command, leading to the command being parsed by `MedmoriserParser` and `DeleteCommandParser`

3. After successful parsing, `DeleteCommand#execute(Model model)` is called. This method checks for the following scenarios:

   - A quiz is currently in progress
   - The index entered by the user is out of bounds

   If any of the scenarios above occur, a `CommandException` will be thrown with their respective error messages. Othewise, a new `CommandResult` is returned, along with a success message that the specified QAndA has been successfully deleted

The sequence diagram (Fig. 1.6) below shows the flow of the delete feature.

(Figure 1.6: Delete Command Sequence Diagram)

![DeleteSequenceDiagram](images/DeleteSequenceDiagram.png)

The activity diagram (Fig 1.7) below further shows the flow of the delete feature.

(Figure 1.7: Delete Command Activity Diagram)

![DeleteActivityDiagram](images/DeleteActivityDiagram.png)


### List Feature (Jonathan Foo)

#### Implementation

This section elaborates on the implementation of the list feature.

1. The user enters input to list all QAndAs.

2. The `Ui` component calls the `Logic` component to execute this. `MedmoriserParser` parses the command, resulting in a `ListCommand` object which is executed by  the `LogicManager`.

3. This updates the `Model` to show all QAndAs in the answer book.

4. The result of this command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

5. `Ui` checks the `CommandResult` object and modifies the visibility of the `Answer` label. In this case, the visibilty is set to `hidden`

The sequence diagram below (Fig 1.8) shows the interactions between the Ui and Logic components for the list command.

(Figure 1.8: List Command Sequence Diagram)
![ListSequenceDiagram](images/ListSequenceDiagram.png)

The following activity diagram (Fig 1.9) summarises what happens when a user executes the `list` command.

(Figure 1.9: List Command Activity Diagram)
![ListActivityDiagram](images/ListActivityDiagram.png)

#### Design considerations:
* The `Ui` component was selected to perform the showing/hiding of answers as it already had access to the various components in the GUI, hence preventing further coupling between the different components.
* We decided to use `list` rather than `list all` to show all questions and answers as we wanted to maintain the original functionality of the `list` command.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* medical students with heavy content to memorise
* has a need to manage and frequently revisit a significant number of questions and answers
* has a need to refer to images and diagrams
* prefer desktop apps over other types
* finds typing with a keyboard faster than typing with smartphones or handwriting.
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:
* Aggregate and organises contents and much faster than traditional methods.
* Allows students to memorise contents more efficiently by continuously testing them. This way they can practice active recall.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                           |
| -------- | ------------------------------------------ | ------------------------------ | -------------------------------------------------------------------------|
| `* * *`  | new user                                   | see usage instructions         | refer to instructions when I forget how to use the App                   |
| `* * *`  | user                                       | key in questions and answers   | revise on the questions I keyed in                                       |
| `* * *`  | user                                       | delete a Q&A                   | remove entries that I no longer need                                     |
| `* * *`  | user                                       | edit existing questions and answers   | make changes to the questions and answers                         |
| `* * *`  | user                                       | see all the questions          | have an overview of all the Q&As I have                                  |
| `* *`  | user                                       | find questions by keywords     | locate the Q&A without having to go through the entire list of questions |
| `* * `   | user                                       | tag questions to its topics    | organise the questions properly by topic                                 |
| `*`   | organised user                             | archive questions I no longer need | keep my question book organised and updated                          |

### Use cases

(For all use cases below, the **System** is the `Medmoriser` and the **Actor** is the `user`, unless specified otherwise. Q&A stands for a question and answer pair)

**Use case: UC01 - Add a Q&A**

**MSS**

1. User adds a new QAndA into the database

2. Medmoriser adds the new QAndA and displays a success message

   Use case ends.

**Extensions**

- 1a. The entered data is invalid

  - 1a1. Medmoriser displays an error message

    Use case ends.



**Use case: UC02 - View all QAndAs**

**MSS**

1. User requests to view all QAndAs

2. Medmoriser shows a list of QAndAs in the database

   Use case ends.



**Use case: UC03 - Delete a QAndA**

**MSS**

1. User requests to view all QAndAs

2. Medmoriser shows a list of QAndAs in the database

3. User requests to delete a QAndA at a specific index in the list

4. Medmoriser deletes the Q&A and displays success message

   Use case ends

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. Medmoriser shows an error message.

    Use case resumes at step 2.

**Use case: UC04 - Add a tag to a QAndA**

**MSS**

1. User requests to view all QAndAs

2. Medmoriser shows a list of QAndAs in the database

3. User requests to add a tag to a QAndA at a specific index in the list

4. Medmoriser adds the specified tag to the QAndA and displays success message

   Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. Medmoriser shows an error message.

    Use case resumes at step 2.

- 3b. The given tag is invalid.

  - 3b1. Medmoriser shows an error message.

    Use case resumes at step 2.

**Use case: UC05 - Edit a QAndA**

**MSS**

1. User requests to view all QAndAs

2. Medmoriser shows a list of QAndAs in the database

3. User requests to edit a QAndA at a specific index in the list

4. Medmoriser edits the QAndA at the specified index according to the user's new input and displays success message

   Use case ends

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. Medmoriser shows an error message.

    Use case resumes at step 2.

- 3b. The edit request is invalid

  - 3b1. Medmoriser shows an error message

    Use case resumes at step 2.

**Use case: UC06 - Find a QAndA**

**MSS**

1. User searches for a key word or phrase

2. Medmoriser shows a list of QAndAs with that specified word or phrase

   Use case ends

**Extensions**

- 2a. There are no QAndAs that contain the specified word or phrase

  - 2a1. Medmoriser displays message that no QAndAs found

    Use case ends

*{More to be added}*

### Non-Functional Requirements

1.  Data bank should be able to hold large number (500+) pairs of QAndAs without any sluggish performance
    for typical usage.
2.  Users should be able to retrieve answers to a question with a single command
3.  User should be able to add a QAndA easily and efficiently through a user-friendly GUI
4.  Have a clean user interface when it comes to reviewing questions and answers

*{More to be added}*

### Glossary

* **QAndA**: A set containing a question and answer
* **Question**: A question belonging to a QAndA
* **Answer**: An answer to a question, both of which belong to the same QAndA

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample QAndAs. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Finding a QAndA

1. Find QAndAs based on keywords in the question

    1. Prerequisites: QAndA(s) with the word `heart` in the question

    1. Test case: `find q/heart`
       Expected: QAndA(s) with `heart` in the question are shown.
   
1. Find QAndAs based on keywords in the answer
    
    1. Prerequisites: 2 different QAndAs, 1 with the word `blood` in the answer
        and the other with `respiratory function` in the answer
    
    1. Test case: `find a/blood, respiratory function`<br>
       Expected: Both QAndAs with the corresponding keywords are shown. 
       (i.e. the QAndA with `blood` in the answer and the QAndA with `respiratory function` in the answer are both shown)

1. Find QAndAs based on keywords in the tag

    1. Prerequisites: QAndA(s) with the tag `immune system`
    
    1. Test case: `find t/immune system`<br>
       Expected: QAndA(s) with the tag `immune system` are shown.
       
### Quizzing

1. Starting a quiz

    1. Prerequisites: QAndA(s) with the `heart` tag. **No** QAndA with the `missing` tag.
    
    1. Test case: `quiz t/heart`<br>
       Expected: A card showing a QAndA with the `heart` tag appears.
       
    1. Test case: `quiz t/missing`<br>
       Expected: No QAndA is shown. Error message shown in the result box.
       
1. Attempting a quiz

    1. Prerequisites: QAndA(s) with the `immune system` tag. Start the quiz by following step 2 in the previous
        test case (i.e. `quiz t/immune system`)
    
    1. Test case: `answer white blood cell`<br>
       Expected: The card displaying the quizzed QAndA now shows the correct answer to the question.
                 The input answer is displayed in the result box.
                 
    1. End the quiz with `endquiz`. Repeat step 1. Test case: `white blood cell`<br>
       Expected: The card displaying the quizzed QAndA remains unchanged. Error message shown in result box.

<div markdown="span" class="alert alert-info">:information_source: 
**Note:** Remember to `endquiz` and achieve the prerequisites when starting a new test case.
</div>

### Deleting a QAndA

1. Deleting a QAndA while all QAndAs are being shown

   1. Prerequisites: List all QAndAs using the `list` command. Multiple QAndAs in the list.

   1. Test case: `delete 1`<br>
      Expected: First QAndA is deleted from the list. Details of the deleted QAndA shown in the result box.

   1. Test case: `delete 0`<br>
      Expected: No QAndA is deleted. Error details shown in the result box.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. Deleting QAndA while only certain QAndAs are being shown

    1. Prerequisites: Multiple QAndAs with the word `heart` in the question. Find all QAndAs with `heart` in the 
        question using the `find q/heart` command.
        
    1. Test case: `delete 2`<br>
       Expected: Second QAndA is deleted from the list. Details of the deleted QAndA shown in the result box.
       The window continues to display the remaining QAndAs with `heart` in the question.
       
### Saving data

1. Initial setup of data files

   1. Download the jar file and move it into an empty folder

   1. Double-click the jar file.<br>
   Expected: Shows the GUI with a set of sample QAndAs.
   
   1. Exit the app with the `exit` command.

1. Simulating a missing data file

   1. Locate the `data` folder and delete the `medmoriser.json` file

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The app shows the GUI with the same set of sample QAndAs. A new `medmoriser.json` file is created in
                the `data` folder.

1. Simulating a corrupted data file

   1. Locate the `data` folder and open the `medmoriser.json` file with an editor of your choice. Delete all the data
      and replace it with random characters (e.g. `abcdef123456`), then save the file.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: Shows the GUI with no QAndAs.
       
   1. Add a QAndA into the app. Exit the app with the `exit` command.
      Expected: The random characters in `medmoriser.json`  are replaced with the json data of the new QAndA.

## Appendix: Effort

This section explains the difficulty level, challenges faced, effort required, and achievements of Medmoriser.

<div markdown="span" class="alert alert-info">:information_source: 
**Note:** The examples used below are explained in comparison to AB3.

</div>


Making Medmoriser was particularly difficult as there were many challenges we faced along the way:

1. There was a need to change a large portion of the AB3 code, as we had a completely different set of variables for Medmoriser. We had to do a lot of refactoring in the form of renaming class and attributes. The restrictions on fields in AB3 were also different from those in Medmoriser (eg. Person in AB3 cannot have special characters, but Question in Medmoriser can), and we had to edit those restrictions respectively.
2. Implementing a quizzing feature required a deep understanding of the design of AB3. Merely manipulating variables in existing code was not sufficient here, and we had to create many new classes such as `QuizCommand`, `AnswerCommand`, `NextCommand`and `EndQuizCommand`. We also had to come up with ways to keep track of the state of the application, such as whether the user is in an ongoing quiz, whether the user has entered an answer for a quiz question, and whether a quiz question has been asked before when choosing the next question to show the user in a quiz. There was not a need for this in AB3.
3. The numerous new classes created and enhancements to existing commands meant that a lot more effort was required to implement testing for Medmoriser. This is evident in our implementation of new test classes (eg. `QuizCommandTest`, `QuizCommandParserTest`, `AnswerCommandTest`, `EndQuizTest` , etc) and additional test cases in existing test classes (eg `FindCommandTest`, `ListCommandTest`, etc).

Despite the difficulties mentioned above, we managed to overcome them and come up with a fully functional product that we are proud of.
