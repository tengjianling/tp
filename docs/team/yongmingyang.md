---
layout: page
title: Yong Ming Yang's Project Portfolio Page
---

## Project: Medmoriser

Medmoriser is a desktop app to help medical students organise, memorise and revise their content. The user interacts with it using a CLI, and it has a GUI created with JavaFX and is written in Java.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=yongmingyang)

* **Enhancements to existing features**:
  * Redefined the Find functionality:
    * What it does: Allows the user to find based on 3 modes
        1. `find t/[tagname]` which allows the user to find QAndA based on the tagname(s).
        2. `find q/[keyword]` which allows the user to find QAndA which contains the specified keyword(s) in the question.
        3. `find [keyword]` which allows the user to find QAndA which contains the specified keyword(s) in the question or the answer.
    * Justification: Gives the user more flexibility to find relevant QAndA sets.
    * Added additional test cases to suit the redefined functionality, increased code coverage by 2.56%: [#58](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/58)
    * Pull requests (inclusive of bug fixes):
        1. [#55](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/55)
        2. [#58](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/58)
        3. [#68](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/68)
        4. [#75](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/75)
        5. [#81](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/81)
        6. [#138](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/138)
        7. [#139](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/139)
    * Credits: The find functionality makes use of code from the following links:
        1. https://stackoverflow.com/questions/36793397/check-if-string-contains-word-not-substring.
  * Refactoring instances of `Address` to `Answer`, `UniquePersonList` to `UniqueQuestionSetList`, `AddressBook` to `Medmoriser` and `Person` to `QuestionSet` (later changed to QAndA by Jian Ling): [#36](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/36)
  * Updated GUI, for quizing part in particular: [#46](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/46), [#165](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/165), [#172](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/172)
    
* **Documentation**:
  * User Guide:
    * Minimal base documentation for start of project [#29](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/29)
    * Add documentation for the `find` feature: [#68](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/68)
    * Update documentation for improved `find` feature: [#79](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/79)
    * Did cosmetic tweaks to `find` feature: [#93](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/93)
  * Developer Guide:
    * Added implementation details of the `find` feature: [#68](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/68)
    * Restructured implementation details for all features: [#159](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/159)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [#38](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/38), [#62](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/62)
  * Contributed to forum discussions (examples: [#127](https://github.com/nus-cs2103-AY2021S1/forum/issues/127), [#128](https://github.com/nus-cs2103-AY2021S1/forum/issues/128), [#349](https://github.com/nus-cs2103-AY2021S1/forum/issues/349)
  * Reported bugs for other teams: [#232](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/232), [#234](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/234), [#233](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/233), [#235](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/235), [#236](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/236), [#237](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/237)   
