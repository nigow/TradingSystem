from flask import jsonify, request
from api import db, app
from api.entities import Cities

# {"cities": [
#     "Toronto",
#     "New York",
#     "Tokyo"
# ]}

# {"city": "Toronto"}

@app.route('/cities/get_all_cities', methods=['GET'])
def get_all_cities():
    try:
        return jsonify(
            {"cities": [{
                "city_id": city.city_id,
                "city_name": city.city_name
            } for city in Cities.query.all()]})
    except:
        return jsonify({"msg": "error occurred in query search"}), 502

@app.route('/cities/update_city', methods=['POST'])
def update_city():
    try:
        data = request.get_json()
        city = Cities(city_id=data["city_id"], city_name=data["city_name"])

    except:
        return jsonify({"msg": "no city found"}), 502

    try:
        db.session.add(city)
        db.session.commit()
        return jsonify({"msg": "city updated"})

    except:
        return jsonify({"msg": "city not updated"}), 502


