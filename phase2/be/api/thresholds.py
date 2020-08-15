from flask import jsonify, request
from api import db, app
from api.entities import Thresholds

@app.route('/thresholds', methods=['GET', 'POST'])
def thresholds():
    if request.method == 'GET':
        try:
            thresholds = Thresholds.query.first()
            returner = {
                "lend_limit": thresholds.lend_limit,
                "incomplete_limit": thresholds.incomplete_limit,
                "weekly_limit": thresholds.weekly_limit,
                "number_of_days": thresholds.number_of_days,
                "number_of_edits": thresholds.number_of_edits,
                "number_of_stats": thresholds.number_of_stats,
                "required_trades_for_trusted": thresholds.required_trades_for_trusted
            }
        except:
            return jsonify({"msg": "no thresholds found"}), 502
        return jsonify(returner)

    if request.method == 'POST':
        try:
            for thresholds in Thresholds.query.all():
                db.session.delete(thresholds)
        except:
            return jsonify({"msg": "something went wrong."}), 502

        try:
            data = request.get_json()
            lend_limit = data["lend_limit"]
            incomplete_limit = data["incomplete_limit"]
            weekly_limit = data["weekly_limit"]
            number_of_days = data["number_of_days"]
            number_of_edits = data["number_of_edits"]
            number_of_stats = data["number_of_stats"]
            required_trades_for_trusted = data["required_trades_for_trusted"]
        except:
            return jsonify({"msg": "wrong JSON format"}), 502

        try:
            db.session.add(Thresholds(weekly_limit=weekly_limit,
                                        incomplete_limit=incomplete_limit,
                                        lend_limit=lend_limit,
                                        number_of_days=number_of_days,
                                        number_of_edits=number_of_edits,
                                        number_of_stats=number_of_stats,
                                        required_trades_for_trusted=required_trades_for_trusted))
            db.session.commit()
        except:
            return jsonify({"msg": "couldn't update"}), 502

        return jsonify({"msg": "update successful."})
