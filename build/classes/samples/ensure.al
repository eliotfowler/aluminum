print("starting ensures")

// Assignment
a = 1
b = 2
c = 3
d = - 4
e = 5
f = true
g = false

// Test equality
ensure(a == 1)
ensure(a < 2)
ensure(b > a)
ensure(b >= a)
ensure(1 >= a)

ensure(-1 <= a)
ensure(a != b)

// Operations
ensure(((a ^ 4)) == 1)
ensure(b ^ c == 8)
ensure(4 * 4 == 16)
ensure(0 * 54732548 == 0)
ensure(2 * 2 * 2 == 8)

ensure(2*2*2 == 2 ^ 3)
ensure(10 / 2 == 5)
ensure(10 % 3 == 1)
ensure(1 + 1 == 2)
ensure(-1 - -1 == 0)

ensure(-1 - 1 == -2)
ensure(true and true)
ensure(true or false)
ensure(!(true and false))
ensure(!(false and false))

// Ternary
eIsFive = e == 5 ? true : false
dIsFive = d == 5 ? true : false
ensure(eIsFive)
ensure(!dIsFive) 

// Test functions
def five do
  return 5
end

def true_func do
  return true
end

def inNOut(in) do
  out = in
  return out
end

five = five()
ensure(five > 4 and five < 6)
ensure(five() == 5)

ensure(true_func())

ensure(inNOut(1) == 1)
ensure(inNOut(2^3) == 8) 

// Test while loops
i = 0
while i < 10 do
  i = i + 1
end
ensure(i == 10)

// Test predefined functions
append = "a".append("p").append("p").append("e").append("n").append("d")
ensure(append == "append")

print("done")
