const taskForm = document.getElementById("taskForm");
const taskInput = document.getElementById("taskInput");
const pendingList = document.getElementById("pendingList");
const completedList = document.getElementById("completedList");
const pendingCount = document.getElementById("pendingCount");
const completedCount = document.getElementById("completedCount");
const pendingEmpty = document.getElementById("pendingEmpty");
const completedEmpty = document.getElementById("completedEmpty");
const inputError = document.getElementById("inputError");
const totalTasks = document.getElementById("totalTasks");
const dateDisplay = document.getElementById("dateDisplay");

let tasks = JSON.parse(localStorage.getItem("taskflow-tasks")) || [];

function saveTasks() {
    localStorage.setItem("taskflow-tasks", JSON.stringify(tasks));
}

function generateId() {
    return Date.now().toString() + Math.random().toString(36).slice(2);
}

function formatDate(date) {
    return new Intl.DateTimeFormat("en", {
        month: "short",
        day: "numeric",
        year: "numeric",
        hour: "numeric",
        minute: "2-digit"
    }).format(new Date(date));
}

function updateDate() {
    dateDisplay.textContent = new Intl.DateTimeFormat("en", {
        weekday: "long",
        month: "long",
        day: "numeric",
        year: "numeric"
    }).format(new Date());
}

function renderTasks() {
    pendingList.innerHTML = "";
    completedList.innerHTML = "";

    const pendingTasks = tasks.filter(task => !task.completed);
    const completedTasks = tasks.filter(task => task.completed);

    pendingTasks.forEach(task => {
        pendingList.appendChild(createTaskElement(task));
    });

    completedTasks.forEach(task => {
        completedList.appendChild(createTaskElement(task));
    });

    pendingCount.textContent = pendingTasks.length;
    completedCount.textContent = completedTasks.length;

    totalTasks.textContent = `${tasks.length} ${tasks.length === 1 ? "task" : "tasks"}`;

    pendingEmpty.style.display = pendingTasks.length === 0 ? "flex" : "none";
    completedEmpty.style.display = completedTasks.length === 0 ? "flex" : "none";
}

function createTaskElement(task) {
    const item = document.createElement("article");
    item.className = `task-item ${task.completed ? "completed" : ""}`;
    item.dataset.id = task.id;

    const checkButton = document.createElement("button");
    checkButton.className = "task-check";
    checkButton.type = "button";
    checkButton.setAttribute(
        "aria-label",
        task.completed ? "Mark task as pending" : "Mark task as complete"
    );
    checkButton.textContent = task.completed ? "✓" : "";

    checkButton.addEventListener("click", () => {
        toggleTask(task.id);
    });

    const content = document.createElement("div");
    content.className = "task-content";

    const text = document.createElement("div");
    text.className = "task-text";
    text.textContent = task.text;

    const time = document.createElement("div");
    time.className = "task-time";

    if (task.completed && task.completedAt) {
        time.textContent = `Completed ${formatDate(task.completedAt)}`;
    } else {
        time.textContent = `Added ${formatDate(task.createdAt)}`;
    }

    content.appendChild(text);
    content.appendChild(time);

    const actions = document.createElement("div");
    actions.className = "task-actions";

    const editButton = document.createElement("button");
    editButton.className = "action-button";
    editButton.type = "button";
    editButton.textContent = "Edit";
    editButton.setAttribute("aria-label", "Edit task");

    editButton.addEventListener("click", () => {
        startEditing(item, task);
    });

    const deleteButton = document.createElement("button");
    deleteButton.className = "action-button delete-button";
    deleteButton.type = "button";
    deleteButton.textContent = "Delete";
    deleteButton.setAttribute("aria-label", "Delete task");

    deleteButton.addEventListener("click", () => {
        deleteTask(task.id);
    });

    actions.appendChild(editButton);
    actions.appendChild(deleteButton);

    item.appendChild(checkButton);
    item.appendChild(content);
    item.appendChild(actions);

    return item;
}

function addTask(text) {
    const task = {
        id: generateId(),
        text: text,
        completed: false,
        createdAt: new Date().toISOString(),
        completedAt: null
    };

    tasks.unshift(task);
    saveTasks();
    renderTasks();
}

function toggleTask(id) {
    tasks = tasks.map(task => {
        if (task.id === id) {
            return {
                ...task,
                completed: !task.completed,
                completedAt: !task.completed
                    ? new Date().toISOString()
                    : null
            };
        }

        return task;
    });

    saveTasks();
    renderTasks();
}

function deleteTask(id) {
    tasks = tasks.filter(task => task.id !== id);

    saveTasks();
    renderTasks();
}

function startEditing(item, task) {
    const content = item.querySelector(".task-content");
    const actions = item.querySelector(".task-actions");

    const input = document.createElement("input");
    input.className = "edit-input";
    input.type = "text";
    input.value = task.text;
    input.maxLength = 150;

    content.innerHTML = "";
    content.appendChild(input);

    actions.innerHTML = "";

    const saveButton = document.createElement("button");
    saveButton.className = "action-button";
    saveButton.type = "button";
    saveButton.textContent = "Save";

    const cancelButton = document.createElement("button");
    cancelButton.className = "action-button";
    cancelButton.type = "button";
    cancelButton.textContent = "Cancel";

    actions.appendChild(saveButton);
    actions.appendChild(cancelButton);

    input.focus();
    input.select();

    function saveEdit() {
        const newText = input.value.trim();

        if (!newText) {
            input.focus();
            return;
        }

        tasks = tasks.map(currentTask => {
            if (currentTask.id === task.id) {
                return {
                    ...currentTask,
                    text: newText
                };
            }

            return currentTask;
        });

        saveTasks();
        renderTasks();
    }

    saveButton.addEventListener("click", saveEdit);

    cancelButton.addEventListener("click", () => {
        renderTasks();
    });

    input.addEventListener("keydown", event => {
        if (event.key === "Enter") {
            saveEdit();
        }

        if (event.key === "Escape") {
            renderTasks();
        }
    });
}

taskForm.addEventListener("submit", event => {
    event.preventDefault();

    const text = taskInput.value.trim();

    if (!text) {
        inputError.textContent = "Please enter a task before adding it.";
        taskInput.focus();
        return;
    }

    inputError.textContent = "";

    addTask(text);

    taskInput.value = "";
    taskInput.focus();
});

taskInput.addEventListener("input", () => {
    inputError.textContent = "";
});

updateDate();
renderTasks();