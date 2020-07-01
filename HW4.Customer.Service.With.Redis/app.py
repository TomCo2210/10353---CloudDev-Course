#app.py

from flask import Flask, render_template, request, redirect, url_for, session
from flask_mysqldb import MySQL,MySQLdb
import redis # ==> Make sure to install this library using pip install redis
from datetime import datetime
import time
import pickle #


counter = 0
app = Flask(__name__)
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = 'Cyber!@#'
app.config['MYSQL_DB'] = 'cyberdb'
app.config['MYSQL_CURSORCLASS'] = 'DictCursor'

mysql = MySQL(app)

startTime = datetime.now()



# Redis Object
R_SERVER = redis.Redis("localhost")

@app.route('/')#open home screen
def home():
     session['block'] = 'no'
     session['client'] = 'yes'
     session.pop('client')
     return render_template("home.html")

@app.route('/login',methods=["GET","POST"])#search for  user
def login():
    if request.method == 'POST':
        email = request.form['email']
       #id = request.form['password']
        if (R_SERVER.get(email)):
            start = time.time()
            user =pickle.loads(R_SERVER.get(email))
            end = time.time()
          #  session['name'] = user['username']
            session['location'] = 'redis'
            session['time'] =  end - start
        else:
         start = time.time()
         curl = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
         curl.execute("SELECT * FROM cloud_users WHERE email=%s",(email,))
         user = curl.fetchone()
         end = time.time()
         curl.close()
         if int(user['followers']) > 5:
                R_SERVER.set(user['email'], pickle.dumps(user) )
         session['location'] = 'mysql'
         session['time'] =  end - start
        if len(user) > 0:
             session['name'] = user['username']
             session['name'] = user['username']
             session['email'] = user['email']
             session['followers'] = user['followers']
             session['id'] = user['id']
             session['client'] = 'yes'
             return render_template("home.html")

        else:
            return "Error user not found"
    else:
        return render_template("login.html")

@app.route('/logout', methods=["GET", "POST"]) #get out from user seesion
def logout():
    session.clear()
    return render_template("home.html")

@app.route('/insert', methods=["GET", "POST"])#insert 1000 users to mySql
def insert():
    i = 1
    cur = mysql.connection.cursor()
    cur.execute("DELETE FROM cloud_users")
    mysql.connection.commit()
    while i < 1000:
      cur.execute("INSERT INTO cloud_users (username, email, followers) VALUES (%s,%s,%s)",('stam',str(i)+'@gmail.com',str(i),))
      mysql.connection.commit()
      i += 1
    cur.close()
    session['db'] = ''
    session['message'] = 'INSERTED successfully'
    return  render_template("message.html")

@app.route('/getList', methods=["GET", "POST"])#get all user from mySql or redis(if you got it from mySql insert also to redis)
def getList():
    if (R_SERVER.get("ALL")):
            start = time.time()
            allUsers =pickle.loads(R_SERVER.get("ALL"))
            end = time.time()
            session['db'] = 'redis'
            session['message'] =  end - start
    else:
     start = time.time()
     curl = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
     curl.execute("SELECT * FROM cloud_users")
     allUsers = curl.fetchone()
     end = time.time()
     curl.close()
     session['db'] = 'mySql'
     session['message'] =  end - start
     R_SERVER.set("ALL", pickle.dumps(allUsers) )
    return  render_template("message.html")

@app.route('/forgot', methods=["GET", "POST"])#delete user
def forgot():
    if request.method == 'POST':
        email = request.form['email']
        R_SERVER.delete(email)
        R_SERVER.delete("ALL")
        session['message'] = 'Deleted successfully'
        return render_template("message.html")
    else:
          return render_template("forgotPwd.html")

@app.route('/confirm', methods=["GET", "POST"])#update user if is follower amount is biger then 5 enter to redis else delete from redis
def confirm():
    if request.method == 'POST':
      # id = request.form['id']
       #name = request.form['name']
       email = request.form['email']
       followers = request.form['followers']
       cur = mysql.connection.cursor()
       cur.execute("UPDATE cloud_users SET followers = %s WHERE email = %s",(followers,email,))
       mysql.connection.commit()
       curl = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
       curl.execute("SELECT * FROM cloud_users WHERE email=%s",(email,))
       user = curl.fetchone()
       curl.close()
       if int(user['followers']) > 5:
        R_SERVER.set(email, pickle.dumps(user) )
       else :
           R_SERVER.delete(email)
       R_SERVER.delete("ALL")
       session['message'] = 'Updated successfully'
       return  render_template("message.html")
    else:
       return render_template("chackCode.html")

@app.route('/client', methods=["GET", "POST"])# unused.. we hope thar we didnt forgot to delete it
def client():
    if request.method == 'POST':
        name = request.form['name']
        phone = request.form['phone']
        cur = mysql.connection.cursor()
        cur.execute("INSERT INTO client (FirstName,phone) VALUES (%s,%s)",(name,phone,))
        mysql.connection.commit()
        session['name'] = name
        session['phone'] = phone
        session['client'] = 'yes'
        return render_template("home.html")
    else:
          return render_template("register.html")

@app.route('/register', methods=["GET", "POST"])#register new user
def register():
    if request.method == 'GET':
        return render_template("register.html")
    else:

        name = request.form['name']
        email = request.form['email']
        followers = request.form['followers']
        cur = mysql.connection.cursor()
        cur.execute("INSERT INTO cloud_users (username, email, followers) VALUES (%s,%s,%s)",(name,email,followers,))
        mysql.connection.commit()
        cur.execute("SELECT * FROM cloud_users WHERE email=%s",(email,))
        user = cur.fetchone()
        cur.close()
        session['name'] = user['username']
        session['email'] = user['email']
        session['followers'] = user['followers']
        session['id'] = user['id']
        session['client'] = 'yes'
        session['location'] = 'mysql'
        R_SERVER.delete("ALL")
        return render_template("home.html")

if __name__ == '__main__':
    app.secret_key = "^A%DJAJU^JJ123"
    app.run(debug=True)
