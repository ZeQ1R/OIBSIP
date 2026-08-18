from flask import Flask, render_template, request, redirect, url_for, session
from werkzeug.security import generate_password_hash, check_password_hash
import json
import os
import re

app = Flask(__name__)
app.secret_key = os.environ.get("SECRET_KEY", "development-secret-key-change-me")

USERS_FILE = "users.json"


def load_users():
    if not os.path.exists(USERS_FILE):
        return []

    try:
        with open(USERS_FILE, "r") as file:
            return json.load(file)
    except (json.JSONDecodeError, FileNotFoundError):
        return []


def save_users(users):
    with open(USERS_FILE, "w") as file:
        json.dump(users, file, indent=4)


def valid_password(password):
    return len(password) >= 8 and bool(re.search(r"\d", password))


@app.route("/")
def index():
    if "user_id" in session:
        return redirect(url_for("dashboard"))

    return redirect(url_for("login"))


@app.route("/login", methods=["GET", "POST"])
def login():
    if "user_id" in session:
        return redirect(url_for("dashboard"))

    error = None

    if request.method == "POST":
        identifier = request.form.get("identifier", "").strip().lower()
        password = request.form.get("password", "")

        if not identifier or not password:
            error = "Please enter your username/email and password."
            return render_template("login.html", error=error)

        users = load_users()

        user = next(
            (
                user for user in users
                if user["username"] == identifier or user["email"] == identifier
            ),
            None
        )

        if not user or not check_password_hash(user["password"], password):
            error = "Invalid username/email or password."
            return render_template("login.html", error=error)

        session["user_id"] = user["id"]
        session["username"] = user["username"]
        session["email"] = user["email"]

        return redirect(url_for("dashboard"))

    return render_template("login.html", error=error)


@app.route("/register", methods=["GET", "POST"])
def register():
    if "user_id" in session:
        return redirect(url_for("dashboard"))

    error = None

    if request.method == "POST":
        username = request.form.get("username", "").strip().lower()
        email = request.form.get("email", "").strip().lower()
        password = request.form.get("password", "")
        confirm_password = request.form.get("confirm_password", "")

        if not username or not email or not password or not confirm_password:
            error = "Please fill in all fields."
            return render_template("register.html", error=error)

        if len(username) < 3:
            error = "Username must contain at least 3 characters."
            return render_template("register.html", error=error)

        if not re.match(r"^[^\s@]+@[^\s@]+\.[^\s@]+$", email):
            error = "Please enter a valid email address."
            return render_template("register.html", error=error)

        if not valid_password(password):
            error = "Password must contain at least 8 characters and 1 number."
            return render_template("register.html", error=error)

        if password != confirm_password:
            error = "Passwords do not match."
            return render_template("register.html", error=error)

        users = load_users()

        duplicate = any(
            user["username"] == username or user["email"] == email
            for user in users
        )

        if duplicate:
            error = "An account with that username or email already exists."
            return render_template("register.html", error=error)

        new_id = max([user["id"] for user in users], default=0) + 1

        new_user = {
            "id": new_id,
            "username": username,
            "email": email,
            "password": generate_password_hash(password)
        }

        users.append(new_user)
        save_users(users)

        return redirect(url_for("login", registered="true"))

    return render_template("register.html", error=error)


@app.route("/dashboard")
def dashboard():
    if "user_id" not in session:
        return redirect(url_for("login"))

    return render_template(
        "dashboard.html",
        username=session["username"],
        email=session["email"]
    )


@app.route("/logout")
def logout():
    session.clear()
    return redirect(url_for("login"))


if __name__ == "__main__":
    app.run(debug=True)