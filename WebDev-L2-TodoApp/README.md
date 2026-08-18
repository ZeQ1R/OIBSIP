# ✅ TaskFlow — To-Do Web App

## OIBSIP Web Development Internship — Level 2 Task 3

TaskFlow is a responsive browser-based to-do application built with HTML5, CSS3, and Vanilla JavaScript.

The application allows users to create, manage, edit, complete, and delete daily tasks.

## 📌 Project Overview

The application separates tasks into two categories:

- Pending Tasks
- Completed Tasks

Users can easily manage their tasks through a simple and responsive interface.

## 🛠️ Technologies Used

- HTML5
- CSS3
- JavaScript
- DOM Manipulation
- localStorage
- Google Fonts

## ✨ Features

### Add Tasks

Users can enter a task using the input field and click the "Add Task" button.

New tasks immediately appear in the Pending Tasks section.

### Complete Tasks

Each pending task has a completion button.

When a task is marked complete:

- It moves to the Completed Tasks section.
- The completed counter increases.
- The completion timestamp is displayed.

### Edit Tasks

Users can click the Edit button to modify a task directly inside the task item.

Pressing Enter saves the changes.

Pressing Escape cancels the edit.

### Delete Tasks

The Delete button permanently removes a task from the application.

### Task Counters

The application displays:

- Number of pending tasks
- Number of completed tasks
- Total number of tasks

### Timestamps

Each task displays the time it was added.

Completed tasks display the time they were completed.

### Local Storage

Tasks are saved using the browser's `localStorage`.

This means tasks remain available after refreshing or reopening the page.

### Empty States

Friendly messages are displayed when there are no pending or completed tasks.

## 📱 Responsive Design

The application works across:

- Desktop
- Laptop
- Tablet
- Mobile

CSS media queries adjust the task layout, form, navigation, and buttons for smaller screens.

## 🎨 Design

The application uses a clean productivity-focused interface.

The primary design elements include:

- White cards
- Light gray background
- Purple primary color
- Green completed status
- Orange pending status
- Rounded components
- Subtle shadows
- Responsive spacing

## 📂 Project Structure

```text
WebDev-L2-ToDoApp/
│
├── index.html
├── style.css
├── script.js
└── README.md