from flask import jsonify, request
from api import db, app
from api.entities import Accounts
from datetime import datetime

def _find_user(account: Accounts):
    wishlist = account.wishlist
    if wishlist == "":
        wishlist = " "
    return jsonify({
        "account_id": account.account_id,
        "username": account.username,
        "password": account.password,
        "wishlist": wishlist,
        "permissions": account.permissions,
        "city": account.city
    })


@app.route('/accounts/find_by_account_id/<id>')
def find_by_account_id(id: str):
    try:
        account = Accounts.query.filter_by(account_id=int(id)).first()

    except:
        return jsonify({"msg": "please pass in an valid id"}), 502
    if account:
        return _find_user(account)
    return jsonify({"msg": "no user found"}), 502

@app.route('/accounts/find_by_username/<username>')
def find_by_username(username: str):
    try:
        account = Accounts.query.filter_by(username=username).first()
        return _find_user(account)
    except:
        return jsonify({"msg": "please pass in an valid id"}), 502

@app.route('/accounts/create_account', methods=['POST'])
def create_account():
    try:
        data = request.get_json()
        account_id = data["account_id"]
        username = data["username"]
        password = data["password"]
        wishlist = data["wishlist"]
        permissions = data["permissions"]
        city = data["city"]


        account_with_id = Accounts.query.filter_by(account_id=account_id).first()
        account_with_username = Accounts.query.filter_by(username=username).first()
        new_account = Accounts(account_id=account_id, username=username, password=password,
                               wishlist=wishlist, permissions=permissions, city=city)
    except:
        return jsonify({"msg": "wrong format"}), 502
    if account_with_id:
        return jsonify({"msg": "account already exists with the same id"}), 502
    elif account_with_username:
        return jsonify({"msg": "account already exists with the same username"})
    try:
        db.session.add(new_account)
        db.session.commit()
        return jsonify({"msg": "account registration successful"})
    except:
        return jsonify({"msg": "account registration unsuccessful"}), 502

@app.route('/accounts/update_account', methods=['POST'])
def update_account():
    try:
        data = request.get_json()
        account_id = data["account_id"]
        account = Accounts.query.filter_by(account_id=account_id).first()
        if not account:
            return jsonify({"msg": "couldn't find an account with the id given"}), 502
        account.username = data["username"]
        account.password = data["password"]
        account.wishlist = data["wishlist"]
        account.permissions = data["permissions"]
        account.city = data["city"]
        account.updated_at = datetime.utcnow()

    except:
        return jsonify({"msg": "wrong format"}), 502
    try:
        db.session.commit()
        return jsonify({"msg": "account update successful"})
    except:
        return jsonify({"msg": "account update unsuccessful"}), 502


@app.route('/accounts/get_all_accounts', methods=['GET'])
def get_all_accounts():
    try:
        accounts = Accounts.query.order_by(Accounts.account_id.asc()).all()
        return jsonify({"accounts": [{
                "account_id": account.account_id,
                "username": account.username,
                "password": account.password,
                "wishlist": account.wishlist,
                "permissions": account.permissions,
                "city": account.city
            } for account in accounts]})
    except:
        return jsonify({"msg": "couldn't load the data"}), 502

@app.route('/accounts/generate_valid_account_id')
def generate_valid_account_id():
    try:
        length = len(Accounts.query.all())
        return jsonify({"id": length})
    except:
        return jsonify({"msg": "something went wrong"}), 502

@app.route('/accounts/lCCXIuiFi7HTxKogfD7je2OBMBa9uwWzy06S4oig/<id>', methods=['DELETE'])
def delete_account(id: str):
    try:
        account = Accounts.query.filter_by(account_id=int(id)).first()
        if account:
            db.session.delete(account)
            db.session.commit()
            return jsonify({"msg": "deletion successful"})
        return jsonify({"msg": "no account found"}), 502
    except:
        return jsonify({"msg": "couldn't delete the account"}), 502
