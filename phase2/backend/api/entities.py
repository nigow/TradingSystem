from api import db
from datetime import datetime


class Items(db.Model):
    __tablename__ = "items"
    id = db.Column(db.Integer, primary_key=True)
    item_id = db.Column(db.Integer, unique=True)
    created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    updated_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    name = db.Column(db.String(255), nullable=False)
    description = db.Column(db.String, nullable=False)
    is_approved = db.Column(db.Boolean, nullable=False)
    owner_id = db.Column(db.Integer, nullable=False)

    def __eq__(self, other):
        return self.item_id == other.item_id and self.description == \
               other.description and \
               self.is_approved == other.is_approved and \
               self.owner_id == other.owner_id


class Thresholds(db.Model):
    __tablename__ = "thresholds"
    id = db.Column(db.Integer, primary_key=True)
    created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    updated_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    lend_limit = db.Column(db.Integer, nullable=False, default=1)
    incomplete_limit = db.Column(db.Integer, nullable=False, default=5)
    weekly_limit = db.Column(db.Integer, nullable=False, default=10)
    number_of_days = db.Column(db.Integer, nullable=False, default=30)
    number_of_edits = db.Column(db.Integer, nullable=False, default=3)
    number_of_stats = db.Column(db.Integer, nullable=False, default=6)
    required_trades_for_trusted = db.Column(db.Integer, nullable=False, default=10)


class Accounts(db.Model):
    __tablename__ = "accounts"
    id = db.Column(db.Integer, primary_key=True)
    account_id = db.Column(db.Integer, unique=True)
    created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    updated_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    username = db.Column(db.String(255), nullable=False, unique=True)
    password = db.Column(db.String, nullable=False)
    wishlist = db.Column(db.String, nullable=False)
    permissions = db.Column(db.String, nullable=False)
    city = db.Column(db.String, nullable=False)

    def __eq__(self, other):
        return self.account_id == other.account_id and self.username == other.username and \
               self.password == other.password and self.wishlist == other.wishlist and \
               self.permissions == other.permissions and self.city == other.city


class Trades(db.Model):
    __tablename__ = "trades"
    id = db.Column(db.Integer, primary_key=True)
    trade_id = db.Column(db.Integer, unique=True)
    created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    updated_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    traders_ids = db.Column(db.String, nullable=False)
    item_ids = db.Column(db.String, nullable=False)
    location = db.Column(db.String, nullable=False)
    time = db.Column(db.String, nullable=False)
    edit_counter = db.Column(db.Integer, nullable=False)
    trade_status = db.Column(db.String, nullable=False)
    is_permanent = db.Column(db.Boolean, nullable=False)
    trade_completions = db.Column(db.String, nullable=False)

    def __eq__(self, other):
        return self.trade_id == other.trade_id and self.traders_ids == other.traders_ids and \
               self.location == other.location and self.time == other.time and \
               self.edit_counter == other.edit_counter and self.trade_status == other.trade_status and \
               self.is_permanent == other.is_permanent and self.trade_completions == other.trade_completions


class Cities(db.Model):
    __tablename__ = "cities"
    id = db.Column(db.Integer, primary_key=True)
    created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    updated_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    city_id = db.Column(db.String, unique=True)
    city_name = db.Column(db.String, unique=True)

    def __eq__(self, other):
        return self.city_id == other.city_id and self.city_name == other.city_name
