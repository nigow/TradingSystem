from flask import jsonify, request
from api import db, app
from api.entities import Items
from datetime import datetime

@app.route('/items/find_by_item_id/<id>', methods=['GET'])
def find_by_item_id(id: str):

    try:
        id = int(id)
    except:
        return jsonify({"msg": "not a valid id"}), 502
    item = Items.query.filter_by(item_id=id).first()
    if item:
        return jsonify({
            "item_id": item.item_id,
            "name": item.name,
            "description": item.description,
            "is_approved": item.is_approved,
            "owner_id": item.owner_id
        })
    return jsonify({"msg": "not a valid id"}), 502

@app.route('/items/create_item', methods=['POST'])
def create_item():
    try:
        data = request.get_json()
        item_id = data["item_id"]

        item = Items.query.filter_by(item_id=item_id).first()

        if item:
            return jsonify({"msg": "item already exists"}), 502

        name = data["name"]
        description = data["description"]
        is_approved = data["is_approved"]
        owner_id = data["owner_id"]
        new_item = Items(item_id=item_id, name=name, description=description,
                     is_approved=is_approved, owner_id=owner_id)
    except:
        return jsonify({"msg": "wrong format"}), 502

    try:
        db.session.add(new_item)
        db.session.commit()
        return jsonify({"msg": "item registration successful"})
    except:
        return jsonify({"msg": "item registration unsuccessful"}), 502

@app.route('/items/update_item', methods=['POST'])
def update_item():
    try:
        data = request.get_json()
        item_id = data["item_id"]

        item = Items.query.filter_by(item_id=item_id).first()

        if not item:
            return jsonify({"msg": "item does not exist"}), 502

    except:
        return jsonify({"msg": "failed changing the item"}), 502

    try:

        item.name = data["name"]
        item.description = data["description"]
        item.is_approved = data["is_approved"]
        item.owner_id = data["owner_id"]
        item.updated_at = datetime.utcnow()

    except:
        return jsonify({"msg": "wrong format"}), 502

    try:
        db.session.commit()
        return jsonify({"msg": "item update successful"})
    except:
        return jsonify({"msg": "item update unsuccessful"}), 502

@app.route('/items/get_all_items', methods=['GET'])
def get_all_items():
    try:
        items = Items.query.order_by(Items.item_id.asc()).all()
    except:
        return jsonify({"msg": "something went wrong"}), 502
    return jsonify({"items": [{"item_id": items[i].item_id,
                    "name": items[i].name,
                    "description": items[i].description,
                    "is_approved": items[i].is_approved,
                    "owner_id": items[i].owner_id} for i in range(len(items))]})

@app.route('/items/generate_valid_item_id', methods=['GET'])
def generate_valid_id():
    try:
        return jsonify({"id": len(Items.query.all())})
    except:
        return jsonify({"msg": "something went wrong"}), 502


@app.route('/items/uigAXA40ztKeHKQshU3BJRRLBb4RooftJAvF7RpI/<id>', methods=['DELETE'])
def delete_item(id: str):
    try:
        item = Items.query.filter_by(item_id=int(id)).first()
        if item:
            db.session.delete(item)
            db.session.commit()
            return jsonify({"msg": "deletion successful"})
        return jsonify({"msg": "no item found"}), 502
    except:
        return jsonify({"msg": "couldn't delete the item"}), 502
