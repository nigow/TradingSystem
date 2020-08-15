from flask import Flask
from flask_sqlalchemy import SQLAlchemy
import os

app = Flask(__name__)

#OR Mapper for SQLite
# app.config['SECRET_KEY'] = 'secretkey'
# app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///database.db'
# db = SQLAlchemy(app)

#OR Mapper for PostgresSQL
app.config['SECRET_KEY'] = 'secretkey'
app.config['SQLALCHEMY_DATABASE_URI'] = "postgres://jbfcvqjnudscxa:ecd9d8890ecfc2cac83009d43cdd728c86dec95e4e173bc1d7ac5ecbc9c81c7c@ec2-50-17-21-170.compute-1.amazonaws.com:5432/d2sn9156s9rvn4"
db = SQLAlchemy(app)

from api import items, thresholds, trades, accounts, general, cities
