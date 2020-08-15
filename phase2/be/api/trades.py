from api import db, app
from api.entities import Trades
from flask import request, jsonify

def _find_trade(trade: Trades):
    try:
        trade_id = trade.trade_id
        traders_ids = trade.traders_ids
        if traders_ids == "":
            traders_ids = " "
        item_ids = trade.item_ids
        if item_ids == "":
            item_ids = " " #otherwise the client side would crash

        time = trade.time
        location = trade.location
        edit_counter = trade.edit_counter
        trade_status = trade.trade_status
        is_permanent = trade.is_permanent
        trade_completions = trade.trade_completions
        return {
            "trade_id": trade_id,
            "traders_ids": traders_ids,
            "item_ids": item_ids,
            "time": time,
            "location": location,
            "edit_counter": edit_counter,
            "trade_status": trade_status,
            "is_permanent": is_permanent,
            "trade_completions": trade_completions
        }
    except:
        return {"msg": "wrong format"}


@app.route('/trades/find_by_trade_id/<id>')
def find_trade_by_trade_id(id: str):
    try:
        trade = Trades.query.filter_by(trade_id=int(id)).first()

    except:
        return jsonify({"msg": "not a valid id"})
    if trade:
        returner = _find_trade(trade)
        return jsonify(returner) if not "msg" in returner else jsonify(returner), 502

    return jsonify({"msg": "couldn't find the trade associated with the id"}), 502

@app.route('/trades/create_trade', methods=['POST'])
def create_trade():

    try:
        data = request.get_json()
        trade_id = data["trade_id"]


        traders_ids = data["traders_ids"]
        item_ids = data["item_ids"]
        time = data["time"]
        location = data["location"]
        edit_counter = data["edit_counter"]
        trade_status = data["trade_status"]
        is_permanent = data["is_permanent"]
        trade_completions = data["trade_completions"]

        old_trade = Trades.query.filter_by(trade_id=trade_id).first()

        if old_trade:
            return jsonify({"msg": "trade id already exists"}), 502


        new_trade = Trades(
            trade_id=trade_id,
            traders_ids=traders_ids,
            item_ids=item_ids,
            time=time,
            location=location,
            edit_counter=edit_counter,
            trade_status=trade_status,
            is_permanent=is_permanent,
            trade_completions=trade_completions
        )
    except:
        return jsonify({"msg": "wrong format given"}), 502
    try:
        db.session.add(new_trade)
        db.session.commit()
        return jsonify({"msg": "update trade success"})
    except:
        return jsonify({"msg": "couldn't update the database."}), 502

@app.route('/trades/update_trade', methods=['POST'])
def update_trade():

    try:
        data = request.get_json()
        trade_id = data["trade_id"]
        trade = Trades.query.filter_by(trade_id=trade_id).first()

        if not trade:
            return jsonify({"msg": "trade with trade id doens't exist"}), 502

        trade.traders_ids = data["traders_ids"]
        trade.item_ids = data["item_ids"]
        trade.time = data["time"]
        trade.location = data["location"]
        trade.edit_counter = data["edit_counter"]
        trade.trade_status = data["trade_status"]
        trade.is_permanent = data["is_permanent"]
        trade.trade_completions = data["trade_completions"]

    except:
        return jsonify({"msg": "wrong format given"}), 502

    try:
        db.session.commit()
        return jsonify({"msg": "update trade success"})
    except:
        return jsonify({"msg": "couldn't update the database."}), 502

@app.route('/trades/get_all_trades')
def get_all_trades():
    try:
        trades = Trades.query.order_by(Trades.trade_id.asc()).all()
    except:
        return jsonify({"msg": "couldn't load the trades"}), 502
    return jsonify({"trades": [_find_trade(trade) for trade in trades]})

@app.route('/trades/generate_valid_trade_id')
def generate_valid_trade_id():
    try:
        return jsonify({"id": len(Trades.query.all())})
    except:
        return jsonify({ "msg": "something went wrong"}), 502

@app.route('/trades/InxSKedRosRjmIGxBvoQy4SScfnDZ9ZM62jSsY02/<id>', methods=['DELETE'])
def delete_trade(id: str):
    try:
        trade = Trades.query.filter_by(trade_id=int(id)).first()
        if trade:
            db.session.delete(trade)
            db.session.commit()
            return jsonify({"msg": "deletion successful"})
        return jsonify({"msg": "no trade found"}), 502
    except:
        return jsonify({"msg": "couldn't delete the trade"}), 502
