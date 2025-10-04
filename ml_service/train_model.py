import os
import pickle
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import GradientBoostingRegressor
from sklearn.metrics import r2_score

# Create synthetic but realistic-shaped data for 5 features:
# - age: 18..70
# - income: 10k..1M
# - loanAmount: 0..800k
# - employmentYears: 0..40
# - debt: 0..500k
rng = np.random.default_rng(42)
n = 2000

age = rng.integers(18, 71, size=n)
income = rng.normal(120000, 90000, size=n).clip(5000, 1_000_000)
loanAmount = rng.normal(50000, 120000, size=n).clip(0, 800_000)
employmentYears = rng.integers(0, 41, size=n)
debt = rng.normal(5000, 40000, size=n).clip(0, 500_000)

# Realistic scoring function used to synthesize targets (like an ML target)
y = (
    (age - 18) * 0.5 +                # older (to a point) -> slightly better
    (np.log1p(income) * 10.0) +      # income strongly positive (log scale)
    (-0.0006 * loanAmount) +         # large loans reduce score
    (employmentYears * 1.8) +        # experience helps
    (-0.0009 * debt) +               # debt reduces
    rng.normal(0, 10, size=n)        # noise
)

# Convert to typical credit score range 300 - 850
y = ((y - y.min()) / (y.max() - y.min())) * (850 - 300) + 300

X = np.vstack([age, income, loanAmount, employmentYears, debt]).T

# Split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Scale
scaler = StandardScaler()
X_train_s = scaler.fit_transform(X_train)
X_test_s = scaler.transform(X_test)

# Model - gradient boosting for robust regression
model = GradientBoostingRegressor(n_estimators=250, learning_rate=0.05, max_depth=4, random_state=42)
model.fit(X_train_s, y_train)

preds = model.predict(X_test_s)
print("R^2:", r2_score(y_test, preds))

# Save
MODEL_DIR = os.path.join(os.path.dirname(__file__), "model")
os.makedirs(MODEL_DIR, exist_ok=True)
with open(os.path.join(MODEL_DIR, "model.pkl"), "wb") as f:
    pickle.dump(model, f)
with open(os.path.join(MODEL_DIR, "scaler.pkl"), "wb") as f:
    pickle.dump(scaler, f)

print("Saved model & scaler to", MODEL_DIR)
