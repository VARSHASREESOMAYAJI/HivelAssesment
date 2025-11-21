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




How I Solved the Problem (Explained with Example)

The idea is simple:
Turn every JSON entry into a point (x, y) → build a polynomial → find its constant term.

I’ll explain using Testcase 1, step by step.

Step 1 — Convert JSON entries into points

The JSON gives:

"1": { "base": "10", "value": "4" }
"2": { "base": "2",  "value": "111" }
"3": { "base": "10", "value": "12" }
"6": { "base": "4",  "value": "213" }

For each entry:

x = base

y = decoded value in that base

So we compute:

base = 10, value "4"
→ y = 4
→ point = (10, 4)

base = 2, value "111"
→ y = 7
→ point = (2, 7)

base = 10, value "12"
→ y = 12
BUT x = 10 again → duplicate
→ skip

base = 4, value "213"
→ y = 2·16 + 1·4 + 3 = 39
→ point = (4, 39)

We need k = 3 distinct x-values.
So final points are:

(10, 4), (2, 7), (4, 39)

Step 2 — Build the polynomial from these points

With 3 points → polynomial is degree 2.

We use Lagrange interpolation to evaluate it only at x=0.

This gives:
C=P(0)

Step 3 — Apply the formula at x = 0

For points (10,4), (2,7), (4,39):

C= 4/6 + 105/6 - 390/6
	​
C=−281/6
✔ Final Answer for Testcase 1

-281/6

This constant term comes directly from the polynomial formed by the decoded (x, y) points.

Why your answer might differ from others

Some people interpret the JSON differently:

They treat "value" as a root, not a y-value

They treat "index" as x instead of "base"

They use a control-point formula, not interpolation

Those interpretations give different answers (like 41), but in this method:

We use (x = base, y = decoded value) and pure interpolation.

So –281/6 is correct for this interpretation.
java InterpConst testcase1.json


Note on Interpretation

This solution uses the interpolation interpretation.
Other interpretations (e.g., root + control point reconstruction) give different results, but this repository intentionally implements the interpolation approach.
