# Polynomial Constant Term — Interpolation Method

This project computes the constant term of a polynomial using **polynomial interpolation** based on the (base, value) pairs given in the JSON testcases.

Each JSON entry is interpreted as a point (x, y):

- **x = base**
- **y = value decoded in the given base**

The program:
1. Reads the first **k distinct x-values**.
2. Constructs a **degree (k − 1)** polynomial passing through those points using **Lagrange interpolation**.
3. Computes and prints the **constant term C = P(0)** exactly using rational arithmetic.

---

## 🔍 Testcase 1 Example

Input entries:
(10, 4), (2, 7), (4, 39)


Using Lagrange interpolation and evaluating at zero:

**Final constant term:**
-281/6


---

## 📂 Files Included

- `InterpConst.java` — main Java source code
- `testcase1.json` — sample JSON input
- `testcase2.json` — second problem input
- `.gitignore` — Java ignore rules
- `README.md` — documentation

---

## ▶️ Running the Program

```sh
javac InterpConst.java
java InterpConst testcase1.json


Note on Interpretation

This solution uses the interpolation interpretation.
Other interpretations (e.g., root + control point reconstruction) give different results, but this repository intentionally implements the interpolation approach.