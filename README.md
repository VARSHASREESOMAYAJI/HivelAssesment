Polynomial Constant Term via Interpolation

This project computes the constant term of a polynomial by using the interpolation interpretation of the given JSON test cases.

Each JSON entry contains a "base" and a "value".
In this solution, every entry is interpreted as a point on the polynomial:

x = base (read as an integer)

y = value decoded in that base

Using the first k distinct x-values, a unique polynomial of degree k − 1 is constructed.
The constant term 
𝐶
=
𝑃
(
0
)
C=P(0) is computed using Lagrange interpolation, evaluated exactly at zero using rational arithmetic.

📘 Testcase 1 — Why the Output is -281/6
Input JSON
{
  "keys": { "n": 4, "k": 3 },
  "1": { "base": "10", "value": "4" },
  "2": { "base": "2", "value": "111" },
  "3": { "base": "10", "value": "12" },
  "6": { "base": "4", "value": "213" }
}

Step 1 — Convert entries into (x, y) points
JSON key	x = base	value	decoded y
1	10	"4"	4
2	2	"111"	7
3	10	"12"	12 (ignored — duplicate x = 10)
6	4	"213"	39

We need k = 3 distinct x-values, so the points are:

(
10
,
4
)
,
 
(
2
,
7
)
,
 
(
4
,
39
)
(10,4), (2,7), (4,39)
Step 2 — Compute 
𝐶
=
𝑃
(
0
)
C=P(0) using Lagrange interpolation

General formula:

𝑃
(
0
)
=
∑
𝑖
=
1
3
𝑦
𝑖
⋅
ℓ
𝑖
(
0
)
P(0)=
i=1
∑
3
	​

y
i
	​

⋅ℓ
i
	​

(0)
ℓ
𝑖
(
0
)
=
∏
𝑗
≠
𝑖
0
−
𝑥
𝑗
𝑥
𝑖
−
𝑥
𝑗
ℓ
i
	​

(0)=
j

=i
∏
	​

x
i
	​

−x
j
	​

0−x
j
	​

	​

Term 1: 
(
10
,
4
)
(10,4)
ℓ
1
(
0
)
=
−
2
10
−
2
⋅
−
4
10
−
4
=
1
6
ℓ
1
	​

(0)=
10−2
−2
	​

⋅
10−4
−4
	​

=
6
1
	​

4
⋅
1
6
=
4
6
4⋅
6
1
	​

=
6
4
	​

Term 2: 
(
2
,
7
)
(2,7)
ℓ
2
(
0
)
=
−
10
2
−
10
⋅
−
4
2
−
4
=
5
2
ℓ
2
	​

(0)=
2−10
−10
	​

⋅
2−4
−4
	​

=
2
5
	​

7
⋅
5
2
=
35
2
=
105
6
7⋅
2
5
	​

=
2
35
	​

=
6
105
	​

Term 3: 
(
4
,
39
)
(4,39)
ℓ
3
(
0
)
=
−
10
4
−
10
⋅
−
2
4
−
2
=
−
5
3
ℓ
3
	​

(0)=
4−10
−10
	​

⋅
4−2
−2
	​

=−
3
5
	​

39
⋅
(
−
5
3
)
=
−
65
=
−
390
6
39⋅(−
3
5
	​

)=−65=−
6
390
	​

Final Calculation
𝐶
=
4
6
+
105
6
−
390
6
=
−
281
6
C=
6
4
	​

+
6
105
	​

−
6
390
	​

=
6
−281
	​

✔ Final Output for Testcase 1:
−
281
/
6
−281/6
	​

🧪 Testcase 2

The same interpolation method is applied:

decode all base-encoded values,

select the first k distinct x-values,

apply Lagrange interpolation,

evaluate 
𝑃
(
0
)
P(0).

This produces another rational constant term (large number), consistent with this interpretation.

📝 Note on Interpretation

The original problem can also be interpreted as a root-based reconstruction, which leads to completely different results (e.g., 41 for testcase 1).
However, this implementation intentionally uses:

x = base, y = decoded value
⟹
Interpolation
x = base, y = decoded value⟹Interpolation

This is why the answer differs from solutions using the “roots + control point” formula.