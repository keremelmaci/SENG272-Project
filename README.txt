ISO 15939 Measurement Process Simulator
=======================================

Student Name: WRITE YOUR NAME HERE
Student ID: WRITE YOUR STUDENT ID HERE
Course: Software Project II

Project Summary
---------------
This project is a Java Swing desktop application that simulates the simplified ISO/IEC 15939 measurement process.
It implements these five steps with a wizard structure:
1. Profile
2. Define
3. Plan
4. Collect
5. Analyse

Interface Design Note
---------------------
Main visual choices:
- Dark gradient top step indicator
- Card-based content sections
- Orange/blue accent palette
- Styled tables and score cells
- Custom radar chart panel
- Split-screen profile layout


Main Features
-------------
- CardLayout based wizard screen flow
- Separate model and GUI classes
- Profile validation with user-friendly warnings
- Product/Process quality type selection
- Education and Health modes
- Two scenarios for each mode
- Read-only measurement plan tables
- Automatic score calculation between 1.0 and 5.0
- Score clamping and nearest 0.5 rounding
- Dimension-based weighted average calculation
- Gap analysis
- Java 2D radar chart bonus feature

How to Compile and Run
----------------------------
1. Open IntelliJ IDEA.
2. Click File > Open.
3. Select the ISO15939Simulator folder.
4. Open src/com/iso15939/Main.java.
5. Click the green Run button near the main method.

