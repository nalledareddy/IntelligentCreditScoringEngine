from flask import Flask, request, jsonify
import pickle, os, numpy as np

BASE = os.path.dirname(__file__)
MODEL_DIR = os.path.join(BASE, "model")

# load model and scaler (train first if not present)
if not (os.path.exists(os.path.join(MODEL_DIR, "model.pkl")) and os.path.exists(os.path.join(MODEL_DIR, "scaler.pkl"))):
    raise RuntimeError("Model not found. Run `python train_model.py` in ml_service/ to create model files.")

with open(os.path.join(MODEL_DIR, "scaler.pkl"), "rb") as f:
    scaler = pickle.load(f)
with open(os.path.join(MODEL_DIR, "model.pkl"), "rb") as f:
    model = pickle.load(f)

app = Flask(__name__)

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json(force=True)
    # required features
    try:
        age = float(data.get("age", 0))
        income = float(data.get("income", 0))
        loanAmount = float(data.get("loanAmount", 0))
        employmentYears = float(data.get("employmentYears", 0))
        debt = float(data.get("debt", 0))
    except Exception as e:
        return jsonify({"error":"invalid_input", "detail": str(e)}), 400

    X = np.array([[age, income, loanAmount, employmentYears, debt]])
    try:
        Xs = scaler.transform(X)
    except Exception as e:
        return jsonify({"error":"scaler_error", "detail": str(e)}), 500

    pred = model.predict(Xs)[0]
    pred = int(max(300, min(850, round(pred))))
    return jsonify({"creditScore": pred})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
